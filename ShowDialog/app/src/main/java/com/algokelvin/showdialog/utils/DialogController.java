package com.algokelvin.showdialog.utils;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

public class DialogController {
    private Dialog dialog;
    private final Context context;
    private final int layoutDialog;

    public DialogController(Context context, int layoutDialog) {
        this.context = context;
        this.layoutDialog = layoutDialog;
        setDialog();
    }

    private void setDialog() {
        dialog = new Dialog(context);
        dialog.setContentView(layoutDialog);
    }

    public void setTextMsg(String txt, int idTxt) {
        TextView textView = dialog.findViewById(idTxt);
        textView.setText(txt);
    }

    public void setShowDialog() {
        dialog.show();
    }
}
