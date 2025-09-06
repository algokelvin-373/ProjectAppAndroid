package com.algokelvin.videoprocessing;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class UriUtils {
    private UriUtils() {}

    /** Returns a real file path usable by Mp4Composer (copies to cache if needed) */
    public static String ensureFilePathFromUri(Context ctx, Uri src) throws Exception {
        if ("file".equalsIgnoreCase(src.getScheme())) {
            return new File(src.getPath()).getAbsolutePath();
        }
        // Some content Uris can be directly opened by path, but not all — safest is copy.
        File cache = new File(ctx.getCacheDir(), "in_" + System.currentTimeMillis() + ".mp4");
        try (InputStream in = openInputStreamCompat(ctx, src);
             OutputStream out = new FileOutputStream(cache)) {
            byte[] buf = new byte[8192];
            int n;
            while ((n = in.read(buf)) > 0)
                out.write(buf, 0, n);
            out.flush();
        }
        return cache.getAbsolutePath();
    }

    public static String getPathOutputAbsolute(Context ctx, String filename) {
        File outDir = ctx.getExternalFilesDir(Environment.DIRECTORY_MOVIES);
        if (outDir != null && !outDir.exists()) outDir.mkdirs();
        File outFile = new File(outDir, filename + System.currentTimeMillis() + ".mp4");
        return outFile.getAbsolutePath();
    }

    public static Uri createPendingMp4(Context ctx, String displayName) {
        ContentValues v = new ContentValues();
        v.put(MediaStore.MediaColumns.DISPLAY_NAME, displayName); // harus diakhiri .mp4
        v.put(MediaStore.MediaColumns.MIME_TYPE, "video/mp4");
        v.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_MOVIES + "/Compressed");
        v.put(MediaStore.MediaColumns.IS_PENDING, 1);
        return ctx.getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, v);
    }

    private static InputStream openInputStreamCompat(Context ctx, Uri uri) throws Exception {
        ContentResolver cr = ctx.getContentResolver();
        AssetFileDescriptor afd = cr.openAssetFileDescriptor(uri, "r");
        if (afd != null)
            return afd.createInputStream();
        return cr.openInputStream(uri);
    }

    public static String getDisplayName(Context ctx, Uri uri, String fallback) {
        Cursor c = null;
        try {
            c = ctx.getContentResolver().query(uri,
                    new String[]{OpenableColumns.DISPLAY_NAME}, null, null, null);
            if (c != null && c.moveToFirst()) {
                int idx = c.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME);
                String name = c.getString(idx);
                if (name != null && !name.trim().isEmpty()) return name;
            }
        } catch (Throwable ignored) {
        } finally {
            if (c != null) c.close();
        }
        return fallback;
    }

    public static String resolveMime(Context ctx, Uri uri, String def) {
        String m = ctx.getContentResolver().getType(uri);
        return m != null ? m : def;
    }

    public static long getFileSize(Context context, Uri uri) {
        long size = -1;

        if ("content".equalsIgnoreCase(uri.getScheme())) {
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver()
                        .query(uri, null, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    int sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE);
                    if (!cursor.isNull(sizeIndex)) {
                        size = cursor.getLong(sizeIndex);
                    }
                }
            } finally {
                if (cursor != null) cursor.close();
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            File file = new File(uri.getPath());
            size = file.length();
        }

        return size;
    }

    public static void saveVideoToMoviesFolder(Context context, Uri sourceUri) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Video.Media.DISPLAY_NAME, "processed_video_" + System.currentTimeMillis() + ".mp4");
        values.put(MediaStore.Video.Media.MIME_TYPE, "video/mp4");
        values.put(MediaStore.Video.Media.RELATIVE_PATH, "Movies/VideoProcessing");

        Uri outputUri = context.getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values);

        if (outputUri != null) {
            try (InputStream inputStream = context.getContentResolver().openInputStream(sourceUri);
                 OutputStream outputStream = context.getContentResolver().openOutputStream(outputUri)) {

                if (inputStream == null || outputStream == null) {
                    return;
                }

                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("SaveVideo", "Gagal menyalin video", e);
            }
        } else {
            Log.e("SaveVideo", "Gagal membuat URI tujuan");
        }
    }
}
