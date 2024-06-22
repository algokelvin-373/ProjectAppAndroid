package com.algokelvin.camera.rotation.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;

public class CameraSurfaceHolder extends Activity implements SurfaceHolder.Callback {
    private final String[] permissions = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    Camera camera;
    SurfaceView surfaceView;
    TextView txtDegree;
    SurfaceHolder surfaceHolder;
    boolean previewing = false;

    protected void setCameraSurface(TextView txtDegree, SurfaceView surfaceView) {
        this.txtDegree = txtDegree;
        this.surfaceView = surfaceView;
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
    }

    protected boolean hasNoPermissions() {
        return ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED;
    }

    protected void requestPermission(){
        ActivityCompat.requestPermissions(this, permissions,0);
    }

    protected void initCamera() {
        if (surfaceHolder.getSurface() == null) {
            return;
        }

        if (camera == null) {
            camera = Camera.open();
            camera.setDisplayOrientation(90);

            try {
                camera.setPreviewDisplay(surfaceHolder);
                camera.startPreview();
                previewing = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        float r = surfaceView.getRotation();
        txtDegree.setText(String.valueOf(r));

        if (!hasNoPermissions()) {
            initCamera();
        }
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        if(previewing){
            camera.stopPreview();
            previewing = false;
        }

        if (camera != null){
            try {
                camera.setPreviewDisplay(surfaceHolder);
                camera.startPreview();
                previewing = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        camera.stopPreview();
        camera.release();
        camera = null;
        previewing = false;
    }
}
