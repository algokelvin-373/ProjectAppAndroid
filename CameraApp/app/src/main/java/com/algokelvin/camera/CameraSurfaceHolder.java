package com.algokelvin.camera;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CameraSurfaceHolder extends Activity implements SurfaceHolder.Callback {
    private final String[] permissions = {
            Manifest.permission.CAMERA
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
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED;
    }

    protected void requestPermission(){
        ActivityCompat.requestPermissions(this, permissions,0);
    }

    protected void initCamera() {
        if (surfaceHolder == null || surfaceHolder.getSurface() == null) {
            return;
        }

        if (camera == null) {
            camera = Camera.open();
            camera.setDisplayOrientation(getCameraDisplayOrientation());

            try {
                camera.setPreviewDisplay(surfaceHolder);
                camera.startPreview();
                previewing = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    protected void captureImage() {
        if (camera == null || !previewing) {
            Toast.makeText(this, R.string.camera_not_ready, Toast.LENGTH_SHORT).show();
            return;
        }

        camera.takePicture(null, null, (data, camera) -> {
            File imageFile = createImageFile();

            try (FileOutputStream outputStream = new FileOutputStream(imageFile)) {
                outputStream.write(data);
                Toast.makeText(this, getString(R.string.image_saved, imageFile.getName()), Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Toast.makeText(this, R.string.image_save_failed, Toast.LENGTH_SHORT).show();
            }

            camera.startPreview();
            previewing = true;
        });
    }

    protected void releaseCamera() {
        if (camera != null) {
            if (previewing) {
                camera.stopPreview();
                previewing = false;
            }

            camera.release();
            camera = null;
        }
    }

    private int getCameraDisplayOrientation() {
        int rotation = getWindowManager().getDefaultDisplay().getRotation();

        switch (rotation) {
            case Surface.ROTATION_90:
                return 0;
            case Surface.ROTATION_180:
                return 270;
            case Surface.ROTATION_270:
                return 180;
            default:
                return 90;
        }
    }

    private File createImageFile() {
        File pictureDir = new File(getExternalFilesDir(null), "pictures");
        if (!pictureDir.exists()) {
            pictureDir.mkdirs();
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        return new File(pictureDir, "IMG_" + timeStamp + ".jpg");
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
        if(previewing && camera != null){
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
        releaseCamera();
    }
}
