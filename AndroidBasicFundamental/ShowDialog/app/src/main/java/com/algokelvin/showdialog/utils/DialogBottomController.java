package com.algokelvin.showdialog.utils;

import android.content.Context;

import com.google.android.material.bottomsheet.BottomSheetDialog;

public class DialogBottomController {
    private BottomSheetDialog bottomSheetDialog;
    private Context context;
    private int layoutDialogBottom;

    public DialogBottomController(Context context, int layoutDialogBottom) {
        this.context = context;
        this.layoutDialogBottom = layoutDialogBottom;
        setDialogBottom();
    }

    private void setDialogBottom() {
        bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(layoutDialogBottom);
    }

    public void setShowDialogBottom() {
        bottomSheetDialog.show();
    }
}
