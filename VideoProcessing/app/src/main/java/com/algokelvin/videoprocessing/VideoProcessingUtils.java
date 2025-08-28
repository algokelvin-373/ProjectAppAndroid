package com.algokelvin.videoprocessing;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.OptIn;
import androidx.media3.common.MediaItem;
import androidx.media3.common.MimeTypes;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.transformer.Composition;
import androidx.media3.transformer.EditedMediaItem;
import androidx.media3.transformer.EditedMediaItemSequence;
import androidx.media3.transformer.ExportException;
import androidx.media3.transformer.ExportResult;
import androidx.media3.transformer.TransformationRequest;
import androidx.media3.transformer.Transformer;

@OptIn(markerClass = UnstableApi.class)
public class VideoProcessingUtils {
    private final String PATH_NAME_VIDEO_TRIM = "trim_";
    private Transformer transformer;

    public interface VideoTrimListener {
        void onCompleted(String outputPath);
        void onError(Exception e);
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
        String outputPath = UriUtils.getPathOutputAbsolute(context, PATH_NAME_VIDEO_TRIM);

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

        transformer = new Transformer.Builder(context)
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
}
