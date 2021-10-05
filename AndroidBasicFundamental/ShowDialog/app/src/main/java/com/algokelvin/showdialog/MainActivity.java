package com.algokelvin.showdialog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView btnDialogBottom = findViewById(R.id.btn_dialog_bottom);

        btnDialogBottom.setOnClickListener(v -> setDialogBottom());
    }

    private void setDialogBottom() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.dialog_bottom_layout);
        bottomSheetDialog.show();
    }

}