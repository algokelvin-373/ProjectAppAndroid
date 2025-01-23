package com.algokelvin.readwrite;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivityLogger";
    private static final int REQUEST_PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (checkPermissions()) {
            readFileFromMusicFolder("recording_final.wav");
        } else {
            requestPermissions();
        }
    }

    private boolean checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_MEDIA_AUDIO) == PackageManager.PERMISSION_GRANTED;
        } else {
            return ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        }
    }

    private void requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_MEDIA_AUDIO}, REQUEST_PERMISSION_CODE);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                readFileFromMusicFolder("recording_final.wav");
            } else {
                Log.e(TAG, "Permission denied");
            }
        }
    }

    private void readFileFromMusicFolder(String fileName) {
        ContentResolver contentResolver = getContentResolver();
        Uri collection = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DISPLAY_NAME
        };

        String selection = MediaStore.Audio.Media.DISPLAY_NAME + " = ?";
        String[] selectionArgs = new String[]{fileName};

        try (Cursor cursor = contentResolver.query(collection, projection, selection, selectionArgs, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID);
                long id = cursor.getLong(idColumn);
                Uri contentUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id);

                try (InputStream inputStream = contentResolver.openInputStream(contentUri);
                     BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

                    StringBuilder fileContent = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        fileContent.append(line).append("\n");
                    }

                    Log.i(TAG, "File content: " + fileContent.toString());
                } catch (Exception e) {
                    Log.e(TAG, "Error reading file", e);
                }
            } else {
                Log.e(TAG, "File not found: " + fileName);
                // Fallback: Try reading directly from the absolute path
                readFileFromAbsolutePath("/storage/emulated/0/Music/" + fileName);
            }
        } catch (Exception e) {
            Log.e(TAG, "Query failed", e);
        }
    }

    private void readFileFromAbsolutePath(String filePath) {
        if (isFileExists(filePath)) {
            try (InputStream inputStream = Files.newInputStream(Paths.get(filePath));
                 BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

                StringBuilder fileContent = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    fileContent.append(line).append("\n");
                }

                Log.i(TAG, "File content (from absolute path): ");
            } catch (Exception e) {
                Log.e(TAG, "Error reading file from absolute path", e);
            }
        } else {
            Log.e(TAG, "File not found: " + filePath);
        }
    }

    private boolean isFileExists(String filePath) {
        try {
            return Files.exists(Paths.get(filePath));
        } catch (Exception e) {
            Log.e(TAG, "Error checking file existence", e);
            return false;
        }
    }
}