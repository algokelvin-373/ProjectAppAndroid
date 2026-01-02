package com.algokelvin.videoprocessing;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.OptIn;
import androidx.media3.common.MediaItem;
import androidx.media3.common.MimeTypes;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.effect.Presentation;
import androidx.media3.transformer.Composition;
import androidx.media3.transformer.DefaultEncoderFactory;
import androidx.media3.transformer.EditedMediaItem;
import androidx.media3.transformer.EditedMediaItemSequence;
import androidx.media3.transformer.Effects;
import androidx.media3.transformer.ExportException;
import androidx.media3.transformer.ExportResult;
import androidx.media3.transformer.TransformationRequest;
import androidx.media3.transformer.Transformer;
import androidx.media3.transformer.VideoEncoderSettings;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.Collections;

@OptIn(markerClass = UnstableApi.class)
public class VideoProcessingUtils {

    public interface VideoTrimListener {
        void onCompleted(String outputPath);
        void onError(Exception e);
    }

    public interface VideoCompressListener {
        void onSuccess(Uri outputUri);
        void onError(Throwable error);
    }

    public VideoProcessingUtils() {}

    public void trim(
            Context context,
            Uri inputUri,
            long startMs,
            long endMs,
            VideoTrimListener listener
    ) {
        if (endMs <= startMs) {
            if (listener != null)
                listener.onError(new IllegalArgumentException("endMs must be > startMs"));
        }

        long startPlay = startMs - (startMs % 1000);
        long endPlay = endMs - (endMs % 1000);
        String outputPath = UriUtils.getPathOutputAbsolute(context, "trim_");

        // 1. Build Clipping
        MediaItem.ClippingConfiguration clipping = new MediaItem.ClippingConfiguration.Builder()
                .setStartPositionMs(startPlay)
                .setEndPositionMs(endPlay)
                .build();

        // 2. Create clipping
        MediaItem mediaItem = new MediaItem.Builder()
                .setUri(inputUri)
                .setClippingConfiguration(clipping)
                .build();
        EditedMediaItem edited = new EditedMediaItem.Builder(mediaItem)
                .build();

        // 3. Wrap into a composition
        Composition composition = new Composition.Builder(
                new EditedMediaItemSequence(edited)
        ).build();

        Transformer transformer = new Transformer.Builder(context)
                .setVideoMimeType(MimeTypes.VIDEO_H264)
                .setAudioMimeType(MimeTypes.AUDIO_AAC)
                .addListener(new Transformer.Listener() {
                    @Override
                    public void onCompleted(
                            Composition composition,
                            ExportResult exportResult
                    ) {
                        Log.i("VideoTrimCompleted", "Run onCompleted");
                        Log.i("VideoTrimCompleted", "Run onCompleted - "+ exportResult.fileSizeBytes);
                        if (listener != null) {
                            listener.onCompleted(outputPath);
                        }
                    }

                    @Override
                    public void onError(
                            Composition composition,
                            ExportResult exportResult,
                            ExportException exportException
                    ) {
                        Log.i("VideoTrimError", "Run onError: "+ exportException.getMessage());
                        if (listener != null) {
                            listener.onError(exportException);
                        }
                    }

                    @Override
                    public void onFallbackApplied(
                            Composition composition,
                            TransformationRequest originalTransformationRequest,
                            TransformationRequest fallbackTransformationRequest) { }
                })
                .build();

        transformer.start(composition, outputPath);
    }

    public void compress(
            Context context,
            Uri inputUri,
            int targetHeightPx,
            int targetBitrateBps,
            VideoCompressListener callback
    ) {
        // Video resize effect
        Presentation presentation = Presentation.createForHeight(targetHeightPx);
        Effects effects = new Effects(
                Collections.emptyList(),
                Collections.singletonList(presentation)
        );

        // Build EditedMediaItem with effects
        EditedMediaItem edited = new EditedMediaItem.Builder(MediaItem.fromUri(inputUri))
                .setEffects(effects)
                .build();

        // Encoder settings: request target bitrate
        VideoEncoderSettings videoEncoderSettings = new VideoEncoderSettings.Builder()
                .setBitrate(targetBitrateBps)
                .build();

        DefaultEncoderFactory encoderFactory = new DefaultEncoderFactory.Builder(context)
                .setRequestedVideoEncoderSettings(videoEncoderSettings)
                .build();

        File tempOutFile = new File(context.getCacheDir(),
                "transcoded_" + System.currentTimeMillis() + ".mp4");

        Uri destContentUri = UriUtils.createPendingMp4(context, "compressed_" + System.currentTimeMillis() + ".mp4");

        // Build Transformer: force AVC + AAC output
        Transformer transformer = new Transformer.Builder(context)
                .setVideoMimeType(MimeTypes.VIDEO_H264)
                .setAudioMimeType(MimeTypes.AUDIO_AAC)
                .setEncoderFactory(encoderFactory)
                .addListener(new Transformer.Listener() {
                    @Override
                    public void onCompleted(Composition composition, ExportResult exportResult) {
                        Log.i("VideoCompress", "Run onCompleted");
                        Log.i("VideoCompress", "Run onCompleted - "+ exportResult.fileSizeBytes);
                        try {
                            long size = tempOutFile.length();
                            if (size <= 0) throw new IllegalStateException("Temp MP4 size=0");

                            // Tulis ke content Uri
                            copyFileToContentUri(context.getContentResolver(), tempOutFile, destContentUri);

                            // Publish: IS_PENDING=0 + SIZE (supaya galeri langsung lihat)
                            ContentValues publish = new ContentValues();
                            publish.put(MediaStore.MediaColumns.IS_PENDING, 0);
                            publish.put(MediaStore.MediaColumns.SIZE, size);
                            context.getContentResolver().update(destContentUri, publish, null, null);

                            tempOutFile.delete(); // delete temp
                            callback.onSuccess(destContentUri); // <-- result video compress
                        } catch (Exception e) {
                            //noinspection ResultOfMethodCallIgnored
                            tempOutFile.delete();
                            callback.onError(e);
                        }
                    }

                    @Override
                    public void onError(Composition composition, ExportResult exportResult, ExportException e) {
                        Log.i("VideoCompress", "Run onError");
                        tempOutFile.delete();
                        callback.onError(e);
                    }
                }).build();
        transformer.start(edited, tempOutFile.getAbsolutePath());
    }

    private void copyFileToContentUri(ContentResolver cr, File src, Uri dest) throws Exception {
        try (FileInputStream in = new FileInputStream(src);
             OutputStream out = cr.openOutputStream(dest, "w")) {
            if (out == null) throw new IllegalStateException("openOutputStream returned null for " + dest);
            byte[] buf = new byte[8192];
            int n;
            while ((n = in.read(buf)) > 0) out.write(buf, 0, n);
            out.flush();
        }
    }
}
