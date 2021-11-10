package com.algokelvin.showdialog;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.algokelvin.showdialog.utils.DialogBottomController;
import com.algokelvin.showdialog.utils.DialogController;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DialogBottomController dialogBottomController = new DialogBottomController(this, R.layout.dialog_bottom_layout);
        DialogController dialogController = new DialogController(this, R.layout.dialog_layout);
        TextView btnDialogBottom = findViewById(R.id.btn_dialog_bottom);
        TextView btnDialog = findViewById(R.id.btn_dialog_general);

        btnDialogBottom.setOnClickListener(v -> dialogBottomController.setShowDialogBottom());
        btnDialog.setOnClickListener(v -> dialogController.setShowDialog());
    }

}