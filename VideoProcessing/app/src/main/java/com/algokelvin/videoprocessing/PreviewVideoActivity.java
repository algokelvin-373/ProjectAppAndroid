package com.algokelvin.videoprocessing;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.OptIn;
import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.util.UnstableApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.algokelvin.videoprocessing.databinding.ActivityPreviewVideoBinding;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class PreviewVideoActivity extends AppCompatActivity {
    private static final String VIDEO_URI = "video_uri";
    private final long max_window_ms = 8_000;
    private ActivityPreviewVideoBinding binding;

    private ExoPlayer player;
    private Uri uri;
    private VideoProcessingUtils videoProcessing;
    private long durationMs = 0L;
    private long selStartMs = 0L;
    private long selEndMs = 0L;
    private boolean userWantsPlay = false; // make auto loop
    private boolean uiReady = false;

    // --- START: overlay resize state for area box play video ---
    private enum DragMode { NONE, RESIZE_LEFT, RESIZE_RIGHT }
    private DragMode overlayDragMode = DragMode.NONE;
    private float overlayDownX;
    private float startFracDown, endFracDown;
    private float dp(float v) {
        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, v, getResources().getDisplayMetrics());
    }
    private float clamp(float val, float min, float max) {
        return Math.max(min, Math.min(max, val));
    }
    // --- END: overlay resize state for area box play video ---

    private final Handler loopHandler = new Handler(Looper.getMainLooper());
    private final Runnable loopCheck = new Runnable() {
        @Override
        public void run() {
            if (player != null) {
                long pos = player.getCurrentPosition();
                // update play head smooth
                if (durationMs > 0) {
                    float frac = Math.max(0f, Math.min(1f, (float) pos / (float) durationMs));
                    binding.selectionOverlay.setPlayheadFraction(frac);
                }
                // loop manual in window when end
                if (player.isPlaying() && pos >= selEndMs) {
                    player.seekTo(selStartMs);
                }
                // interval: 16ms saat play, 80ms saat pause
                long interval = player.isPlaying() ? 16L : 80L;
                loopHandler.postDelayed(this, interval);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPreviewVideoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.rangeSlider.setThumbTintList(android.content.res.ColorStateList.valueOf(android.graphics.Color.WHITE));
        binding.rangeSlider.setHaloTintList(android.content.res.ColorStateList.valueOf(android.graphics.Color.WHITE));
        binding.rangeSlider.setTickInactiveTintList(android.content.res.ColorStateList.valueOf(android.graphics.Color.TRANSPARENT));
        binding.rangeSlider.setTickActiveTintList(android.content.res.ColorStateList.valueOf(android.graphics.Color.TRANSPARENT));

        videoProcessing = new VideoProcessingUtils();
        uri = Uri.parse(getIntent().getStringExtra(VIDEO_URI));
        binding.btnPlayCenter.setImageResource(R.drawable.ic_video_play);
        binding.btnPlayCenter.setEnabled(false);
        binding.btnPlayCenter.setOnClickListener( v -> togglePlay());
        binding.btnTrimVideo.setOnClickListener(v -> trimmerVideo());
        playVideo();
        onSelectionOverlay();
        setDurationSlideThumbnails();
    }

    private void playVideo() {
        player = new ExoPlayer.Builder(this).build();
        binding.playerView.setPlayer((Player) player);
        player.setMediaItem(MediaItem.fromUri(uri));
        player.prepare();
        player.addListener(new com.google.android.exoplayer2.Player.Listener() {
            @Override
            public void onPlaybackStateChanged(int state) {
                if (state == com.google.android.exoplayer2.Player.STATE_ENDED && userWantsPlay) {
                    player.seekTo(selStartMs);
                    player.play();
                }
            }

            @Override
            public void onIsPlayingChanged(boolean isPlaying) {
                userWantsPlay = isPlaying;
                binding.selectionOverlay.setPlaying(isPlaying);
                binding.btnPlayCenter.setImageResource(isPlaying ? R.drawable.ic_video_pause : R.drawable.ic_video_play);
            }
        });
    }

    private void togglePlay() {
        if (!uiReady) return;

        if (player.isPlaying()) {
            userWantsPlay = false;
            player.pause();
        } else {
            userWantsPlay = true;
            long pos = player.getCurrentPosition();
            if (pos < selStartMs || pos >= selEndMs) {
                player.seekTo(selStartMs);
            }
            player.play();
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void onSelectionOverlay()  {
        binding.selectionOverlay.setOnWindowMoveListener((newStartFrac, newEndFrac) -> {
            // sync RangeSlider & variable duration
            float durSec = durationMs / 1000f;
            float sSec = newStartFrac * durSec;
            float eSec = newEndFrac   * durSec;

            // range based on area play (box white)
            binding.rangeSlider.setValues(sSec, eSec);

            selStartMs = (long) (sSec * 1000);
            selEndMs   = (long) (eSec * 1000);

            binding.lblStart.setText(formatMs(selStartMs));
            binding.lblEnd.setText(formatMs(selEndMs));

            if (player.isPlaying()) {
                long pos = player.getCurrentPosition();
                if (pos < selStartMs || pos > selEndMs) player.seekTo(selStartMs);
            }
        });

        // NEW: allow resizing by dragging left/right white handles on the overlay
        binding.selectionOverlay.setOnTouchListener((v, ev) -> {
            if (durationMs <= 0) return false;

            final float width = v.getWidth();
            final float durSec = durationMs / 1000f;
            final float maxWinSec  = max_window_ms / 1000f;
            final float maxWinFrac = Math.min(1f, maxWinSec / durSec);
            final float slop = dp(16);

            // current selection as fractions [0..1]
            float startFrac = (float) selStartMs / (float) durationMs;
            float endFrac   = (float) selEndMs   / (float) durationMs;
            float x = ev.getX();
            float leftX  = startFrac * width;
            float rightX = endFrac   * width;

            switch (ev.getActionMasked()) {
                case MotionEvent.ACTION_DOWN: {
                    overlayDownX   = x;
                    startFracDown  = startFrac;
                    endFracDown    = endFrac;
                    if (Math.abs(x - leftX) <= slop) {
                        overlayDragMode = DragMode.RESIZE_LEFT;
                        return true;
                    } else if (Math.abs(x - rightX) <= slop) {
                        overlayDragMode = DragMode.RESIZE_RIGHT;
                        return true;
                    } else {
                        overlayDragMode = DragMode.NONE;
                        return false;
                    }
                }
                case MotionEvent.ACTION_MOVE: {
                    if (overlayDragMode == DragMode.NONE) return false;
                    float dxFrac = (x - overlayDownX) / width;
                    float newS = startFracDown;
                    float newE = endFracDown;
                    if (overlayDragMode == DragMode.RESIZE_LEFT) {
                        newS = clamp(startFracDown + dxFrac, 0f, newE);
                        if ((newE - newS) > maxWinFrac) newS = newE - maxWinFrac;
                    } else { // RESIZE_RIGHT
                        newE = clamp(endFracDown + dxFrac, newS, 1f);
                        if ((newE - newS) > maxWinFrac) newE = newS + maxWinFrac;
                    }

                    // apply to overlay
                    binding.selectionOverlay.setWindowFractions(newS, newE);

                    // keep slider, labels, and player in sync
                    float sSec = newS * durSec;
                    float eSec = newE * durSec;
                    binding.rangeSlider.setValues(sSec, eSec);
                    selStartMs = (long) (sSec * 1000);
                    selEndMs   = (long) (eSec * 1000);
                    binding.lblStart.setText(formatMs(selStartMs));
                    binding.lblEnd.setText(formatMs(selEndMs));
                    if (player != null && player.isPlaying()) {
                        long pos = player.getCurrentPosition();
                        if (pos < selStartMs || pos > selEndMs) player.seekTo(selStartMs);
                    }
                    return true;
                }
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    overlayDragMode = DragMode.NONE;
                    return false;
            }
            return false;
        });
    }

    // Get duration & ready slider + thumbnail
    private void setDurationSlideThumbnails() {
        Executors.newSingleThreadExecutor().execute(() -> {
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            try {
                retriever.setDataSource(this, uri);
                durationMs = Long.parseLong(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));

                selStartMs = 0L;
                selEndMs = Math.min(durationMs, max_window_ms);

                // generate 8 thumbnail
                final int targetThumbs = 8;
                final List<Bitmap> thumbs = extractThumbnails(retriever, durationMs, targetThumbs);

                runOnUiThread(() -> {
                    binding.timelineContainer.post(() -> {
                        int width = binding.timelineContainer.getWidth();
                        if (width == 0) {
                            binding.timelineContainer.post(this::recreate);
                            return;
                        }
                        int itemWidth = (width / targetThumbs) - 15;

                        binding.thumbStrip.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
                        binding.thumbStrip.setAdapter(new ThumbsAdapter(thumbs, itemWidth));

                        // Slider
                        float durSec = durationMs / 1000f;
                        binding.rangeSlider.setValueFrom(0f);
                        binding.rangeSlider.setValueTo(durSec);
                        float endSec = Math.min(durSec, max_window_ms / 1000f);
                        binding.rangeSlider.setValues(0f, endSec);

                        selStartMs = 0L;
                        selEndMs   = (long) (endSec * 1000f);

                        // Start set overlay
                        binding.selectionOverlay.setWindowFractions(0f, endSec / durSec);
                        binding.lblStart.setText(formatMs(selStartMs));
                        binding.lblEnd.setText(formatMs(selEndMs));

                        binding.rangeSlider.addOnChangeListener((slider, value, fromUser) -> {
                            List<Float> vals = binding.rangeSlider.getValues();
                            float s = vals.get(0);
                            float e = vals.get(1);

                            float maxWin = max_window_ms / 1000f;
                            if ((e - s) > maxWin) {
                                int active = binding.rangeSlider.getActiveThumbIndex();
                                if (active == 0) s = e - maxWin; else e = s + maxWin;
                                binding.rangeSlider.setValues(s, e);
                            }

                            selStartMs = (long) (s * 1000);
                            selEndMs   = (long) (e * 1000);

                            // update overlay (use fraction 0..1) and label start end
                            binding.selectionOverlay.setWindowFractions(s / durSec, e / durSec);
                            binding.lblStart.setText(formatMs(selStartMs));
                            binding.lblEnd.setText(formatMs(selEndMs));

                            if (player.isPlaying()) {
                                long pos = player.getCurrentPosition();
                                if (pos < selStartMs || pos > selEndMs) player.seekTo(selStartMs);
                            }
                        });

                        // UI is ready — allow playing now
                        uiReady = true;
                        binding.btnPlayCenter.setEnabled(true);
                    });
                });
            } catch (Exception ignored) {}
            finally {
                try {
                    retriever.release();
                } catch (Exception ignored) {}
            }
        });
    }

    private void trimmerVideo() {
        videoProcessing.trim(
                this,
                uri,
                selStartMs,
                selEndMs,
                new VideoProcessingUtils.VideoTrimListener() {
                    @Override
                    public void onCompleted(String outputPath) {
                        runOnUiThread(() -> {
                            previewResultVideoTrim(outputPath);
                        });
                    }

                    @Override
                    public void onError(Exception e) {
                    }
                }
        );
    }

    @OptIn(markerClass = UnstableApi.class)
    private void previewResultVideoTrim(String outputPath) {
        Intent toPostVideoPage = new Intent(this, PreviewVideoActivity.class);
        toPostVideoPage.putExtra(VIDEO_URI, outputPath);
        startActivity(toPostVideoPage);
    }

    private List<Bitmap> extractThumbnails(MediaMetadataRetriever retriever, long durationMs, int count) {
        long stepUs = (durationMs * 1000L) / count;
        int targetH = dp(84);
        List<Bitmap> out = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            long tUs = Math.max(0, i * stepUs);
            try {
                Bitmap bmp = retriever.getFrameAtTime(tUs, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
                if (bmp != null) {
                    float ratio = (float) targetH / bmp.getHeight();
                    Bitmap scaled = Bitmap.createScaledBitmap(bmp, (int)(bmp.getWidth()*ratio), targetH, true);
                    out.add(scaled);
                }
            } catch (Throwable ignored) {}
        }
        return out;
    }

    private int dp(int v) {
        return (int) (v * getResources().getDisplayMetrics().density);
    }

    private String formatMs(long ms) {
        long s = ms / 1000;
        long m = s / 60;
        long ss = s % 60;
        return String.format("%02d:%02d", m, ss);
    }

    @Override
    protected void onStart() {
        super.onStart();
        loopHandler.post(loopCheck);
    }

    @Override
    protected void onStop() {
        super.onStop();
        loopHandler.removeCallbacks(loopCheck);
        if (player != null) {
            player.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.release();
            player = null;
        }
        //trimmer.cancel();
    }

    // --- Adapter thumbnail video ---
    static class ThumbsAdapter extends RecyclerView.Adapter<ThumbVH> {
        private final List<Bitmap> data;
        private final int itemW;

        ThumbsAdapter(List<Bitmap> data, int itemW) {
            this.data = data;
            this.itemW = itemW;
        }

        @NonNull
        @Override
        public ThumbVH onCreateViewHolder(android.view.ViewGroup parent, int viewType) {
            android.view.View v = android.view.LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_video_slider, parent, false);
            // set width = containerWidth/8
            RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) v.getLayoutParams();
            lp.width = itemW;
            v.setLayoutParams(lp);
            return new ThumbVH(v);
        }

        @Override
        public void onBindViewHolder(ThumbVH h, int pos) {
            h.img.setImageBitmap(data.get(pos));
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }

    static class ThumbVH extends RecyclerView.ViewHolder {
        ImageView img;

        ThumbVH(android.view.View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
        }
    }
}