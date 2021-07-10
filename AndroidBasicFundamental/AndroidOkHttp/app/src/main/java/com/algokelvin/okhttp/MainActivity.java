package com.algokelvin.okhttp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import okhttp3.MediaType;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button buttonUpload, buttonDownload, asynchronousGet, synchronousGet, asynchronousPOST;
    private ImageView mImageView;
    TextView txtString;

    public String url = "https://reqres.in/api/users/2";
    public String postUrl = "https://reqres.in/api/users/";

    public String postBody = "{\n" +
            "    \"name\": \"morpheus\",\n" +
            "    \"job\": \"leader\"\n" +
            "}";

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        asynchronousGet = findViewById(R.id.asynchronousGet);
        synchronousGet = findViewById(R.id.synchronousGet);
        asynchronousPOST = findViewById(R.id.asynchronousPost);
        buttonUpload = findViewById(R.id.buttonUpload);
        buttonDownload = findViewById(R.id.buttonDownload);
        mImageView = findViewById(R.id.image_view);
        txtString = findViewById(R.id.txtString);

        // set click event
        asynchronousGet.setOnClickListener(this);
        synchronousGet.setOnClickListener(this);
        asynchronousPOST.setOnClickListener(this);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},100);
                return;
            }
        }

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}