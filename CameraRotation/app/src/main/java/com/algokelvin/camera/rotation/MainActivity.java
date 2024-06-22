package com.algokelvin.camera.rotation;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.algokelvin.camera.rotation.utils.CameraSurfaceHolder;

public class MainActivity extends CameraSurfaceHolder {
    private final String[] permissions = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    SurfaceView surfaceView;
    TextView txtDegree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        txtDegree = findViewById(R.id.txt_degree);
        surfaceView = findViewById(R.id.camerapreview);
        setCameraSurface(txtDegree, surfaceView);

        if (hasNoPermissions()) {
            requestPermission();
        } else {
            initCamera();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!hasNoPermissions()) {
            initCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0) {
            boolean allGranted = true;
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    allGranted = false;
                    break;
                }
            }
            if (allGranted) { // Initialize the camera if all permissions are granted
                initCamera();
            } else { // Handle the case where some permissions are not granted
                Toast.makeText(this, "Some permissions are not granted", Toast.LENGTH_SHORT).show();
            }
        }
    }

}