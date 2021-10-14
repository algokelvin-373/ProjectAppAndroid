package com.algokelvin.showdialog.utils;

import android.app.Dialog;
import android.content.Context;

public class DialogController {
    private Dialog dialog;
    private Context context;
    private int layoutDialog;

    public DialogController(Context context, int layoutDialog) {
        this.context = context;
        this.layoutDialog = layoutDialog;
        setDialog();
    }

    private void setDialog() {
        dialog = new Dialog(context);
        dialog.setContentView(layoutDialog);
    }

    public void setShowDialog() {
        dialog.show();
    }
}
