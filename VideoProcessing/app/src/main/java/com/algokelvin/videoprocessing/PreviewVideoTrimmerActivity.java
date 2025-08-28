package com.algokelvin.videoprocessing;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.algokelvin.videoprocessing.databinding.ActivityPreviewVideoTrimmerBinding;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;

public class PreviewVideoTrimmerActivity extends AppCompatActivity {
    private static final String VIDEO_URI_TRIMMER = "video_uri_trimmer";

    private ActivityPreviewVideoTrimmerBinding binding;
    private ExoPlayer player;
    private String ratio_video;
    private Uri uri;
    private VideoUtils videoUtils;
    private VideoProcessingUtils videoProcessing;
    private boolean userWantsPlay = false;

    private void bindListeners()  {
        binding.trimmerVideoBack.setOnClickListener(v -> onBackPressed());
        binding.btnBack.setOnClickListener(v -> onBackPressed());
        binding.btnPlayCenter.setOnClickListener( v -> togglePlay());
        binding.postVideo.setOnClickListener(v -> compressVideo());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPreviewVideoTrimmerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        videoProcessing = new VideoProcessingUtils();
        videoUtils = new VideoUtils();
        uri = Uri.parse(getIntent().getStringExtra(VIDEO_URI_TRIMMER));
        ratio_video = videoUtils.getRatio(this, uri);
        bindListeners();
        playVideo();
    }

    private void playVideo() {
        player = new ExoPlayer.Builder(this).build();
        binding.playerView.setPlayer((Player) player);
        player.setMediaItem(MediaItem.fromUri(uri));
        player.prepare();
        player.addListener(new com.google.android.exoplayer2.Player.Listener() {
            @Override
            public void onPlaybackStateChanged(int state) {
                if (state == Player.STATE_ENDED) {
                    setPlayIcon(false);
                }
                if (state == Player.STATE_ENDED && userWantsPlay && player != null) {
                    player.seekTo(0);
                    player.play();
                }
            }

            @Override
            public void onIsPlayingChanged(boolean isPlaying) {
                setPlayIcon(isPlaying);
            }
        });
        player.play();
        setPlayIcon(true);
    }

    private void togglePlay() {
        if (player == null) return;
        int state = player.getPlaybackState();
        if (state == Player.STATE_ENDED) {
            player.seekTo(0);
            player.play();
            userWantsPlay = false;
            return;
        }
        if (player.isPlaying()) {
            userWantsPlay = false;
            player.pause();
        } else {
            userWantsPlay = true;
            player.play();
        }
    }

    private void compressVideo() {
        videoProcessing.compress(
                /* context */ this,
                /* input   */ uri,
                /* height  */ 720,
                /* bitrate */ 2_000_000,
                /* cb      */ new VideoProcessingUtils.VideoCompressListener() {
                    @Override
                    public void onSuccess(Uri outputUri) {
                        long fileSize = UriUtils.getFileSize(PreviewVideoTrimmerActivity.this, outputUri);
                        Log.i("VideoCompress", String.valueOf(fileSize));
                        UriUtils.saveVideoToMoviesFolder(PreviewVideoTrimmerActivity.this, outputUri);
                    }

                    @Override
                    public void onError(Throwable error) {

                    }
                });
    }

    private void setPlayIcon(boolean isPlaying) {
        binding.btnPlayCenter.setImageResource(isPlaying ? R.drawable.ic_video_pause : R.drawable.ic_video_play);
        binding.btnPlayCenter.setVisibility(View.VISIBLE);
    }
}