package com.algokelvin.videoprocessing;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.algokelvin.videoprocessing.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private final ActivityResultLauncher<String[]> permLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), result -> {
                boolean cam = Boolean.TRUE.equals(result.get(Manifest.permission.CAMERA));
                boolean mic = Boolean.TRUE.equals(result.get(Manifest.permission.RECORD_AUDIO));
            });

    private final ActivityResultLauncher<Intent> captureLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), res -> {
            });

    private final ActivityResultLauncher<PickVisualMediaRequest> pickVideoLauncher =
            registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                if (uri != null) {
                    openPreview(uri); // open preview native
                } else {
                    Toast.makeText(this, "No video is chose", Toast.LENGTH_SHORT).show();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.layoutVideoGallery.setOnClickListener(v -> openGalleryVideo());
        binding.layoutVideoCamera.setOnClickListener(v -> openCameraVideo());
    }

    private void openCameraVideo() {
        boolean camOk = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED;
        boolean micOk = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                == PackageManager.PERMISSION_GRANTED;
        if (camOk && micOk) {
            launchVideoCapture();
        } else {
            permLauncher.launch(new String[]{Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO});
        }
    }

    private void openGalleryVideo() {
        PickVisualMediaRequest request = new PickVisualMediaRequest.Builder()
                .setMediaType(ActivityResultContracts.PickVisualMedia.VideoOnly.INSTANCE)
                .build();
        pickVideoLauncher.launch(request);
    }

    private void openPreview(Uri videoUri) {
        Intent toPreviewVideo = new Intent(this, PreviewVideoActivity.class);
        toPreviewVideo.putExtra("video_uri", videoUri.toString());
        startActivity(toPreviewVideo);
    }

    private void launchVideoCapture() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        captureLauncher.launch(intent);
    }
}