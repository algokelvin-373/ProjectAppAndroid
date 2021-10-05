package com.algokelvin.showdialog;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView btnDialogBottom = findViewById(R.id.btn_dialog_bottom);
        TextView btnDialog = findViewById(R.id.btn_dialog_general);

        btnDialogBottom.setOnClickListener(v -> setDialogBottom());
        btnDialog.setOnClickListener(v -> setDialog());
    }

    private void setDialogBottom() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.dialog_bottom_layout);
        bottomSheetDialog.show();
    }

    private void setDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_layout);
        dialog.show();
    }

}