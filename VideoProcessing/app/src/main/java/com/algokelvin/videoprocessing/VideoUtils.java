package com.algokelvin.videoprocessing;

import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import java.util.Objects;

public class VideoUtils {
    public VideoUtils() {}

    public String getRatio(Context context, Uri uri) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(context, uri);
            String wStr = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);
            String hStr = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);
            String rStr = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                rStr = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION);
            }

            int width = safeParseInt(wStr, 0);
            int height = safeParseInt(hStr, 0);
            int rotation = safeParseInt(rStr, 0);

            if (rotation == 90 || rotation == 270) {
                int tmp = width;
                width = height;
                height = tmp;
            }

            if (width <= 0 || height <= 0) {
                throw new IllegalStateException("Unable to read video dimensions");
            }

            return simplifyRatio(width, height);
        } finally {
            try {
                retriever.release();
            } catch (Throwable ignore) {}
        }
    }

    private int safeParseInt(String s, int def) {
        try {
            return (s == null) ? def : Integer.parseInt(s);
        } catch (Exception e) {
            Log.e("ErrSafeParseInt", Objects.requireNonNull(e.getMessage()));
            return def;
        }
    }

    // Change 1920x1080 -> "16:9", 1080x1080 -> "1:1"
    private String simplifyRatio(int w, int h) {
        int g = gcd(w, h);
        int rw = w / g;
        int rh = h / g;
        return rw + ":" + rh;
    }

    private int gcd(int a, int b) {
        if (a == 0) return b;
        if (b == 0) return a;
        a = Math.abs(a); b = Math.abs(b);
        while (b != 0) {
            int t = a % b;
            a = b;
            b = t;
        }
        return a;
    }
}
