package com.algokelvin.keyboard.simulator;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class KeyboardController extends AppCompatActivity implements View.OnClickListener {
    public TextView txtInputData;
    public TextView keyboard00, keyboard01, keyboard02, keyboard03, keyboard04, keyboard05;
    public TextView keyboard06, keyboard07, keyboard08, keyboard09;
    public TextView keyboardCancel, keyboardOK;
    public ConstraintLayout keyBtnBack;
    public ImageView imgCrossInput;
    private String txt = "";

    public void setControllerInputData(int id) {
        txtInputData = findViewById(id);
    }

    public void setImgCrossInput(int id) {
        imgCrossInput = findViewById(id);
        imgCrossInput.setOnClickListener(v -> removeAllCharacter());
    }

    public void setControllerKeyboard() {
         keyboard00 = findViewById(R.id.keyBtn_00);
         keyboard01 = findViewById(R.id.keyBtn_01);
         keyboard02 = findViewById(R.id.keyBtn_02);
         keyboard03 = findViewById(R.id.keyBtn_03);
         keyboard04 = findViewById(R.id.keyBtn_04);
         keyboard05 = findViewById(R.id.keyBtn_05);
         keyboard06 = findViewById(R.id.keyBtn_06);
         keyboard07 = findViewById(R.id.keyBtn_07);
         keyboard08 = findViewById(R.id.keyBtn_08);
         keyboard09 = findViewById(R.id.keyBtn_09);
         keyboardCancel = findViewById(R.id.keyBtn_Cancel);
         keyboardOK = findViewById(R.id.keyBtn_OK);
         keyBtnBack = findViewById(R.id.keyBtn_back);
    }
    public void setKeyboardOnClickListener() {
        keyboard01.setOnClickListener(this);
        keyboard02.setOnClickListener(this);
        keyboard03.setOnClickListener(this);
        keyboard04.setOnClickListener(this);
        keyboard05.setOnClickListener(this);
        keyboard06.setOnClickListener(this);
        keyboard07.setOnClickListener(this);
        keyboard08.setOnClickListener(this);
        keyboard09.setOnClickListener(this);
        keyboard00.setOnClickListener(this);
        keyboardCancel.setOnClickListener(this);
        keyboardOK.setOnClickListener(this);
        keyBtnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.keyBtn_00: addText("0");
                break;
            case R.id.keyBtn_01: addText("1");
                break;
            case R.id.keyBtn_02: addText("2");
                break;
            case R.id.keyBtn_03: addText("3");
                break;
            case R.id.keyBtn_04: addText("4");
                break;
            case R.id.keyBtn_05: addText("5");
                break;
            case R.id.keyBtn_06: addText("6");
                break;
            case R.id.keyBtn_07: addText("7");
                break;
            case R.id.keyBtn_08: addText("8");
                break;
            case R.id.keyBtn_09: addText("9");
                break;
            case R.id.keyBtn_back: removeText(String.valueOf(txtInputData.getText()));
                break;
            case R.id.keyBtn_Cancel:
                finish();
                break;
            default:
                break;
        }
    }
    private void addText(String character) {
        txt += character;
        String txtBefore = String.valueOf(txtInputData.getText());
        txtInputData.setText(txtBefore.replace(txtBefore, txt));
        if (imgCrossInput != null) {
            imgCrossInput.setVisibility(View.VISIBLE);
        }
    }
    private void removeText(String character) {
        txt = removeLastCharacter(character);
        String txtBefore = String.valueOf(txtInputData.getText());
        txtInputData.setText(txtBefore.replace(txtBefore, txt));
        if (txtInputData.getText().toString().equals("")) {
            if (imgCrossInput != null) {
                imgCrossInput.setVisibility(View.GONE);
            }
        }
    }
    public String removeLastCharacter(String str) {
        return (str == null) ? null : str.replaceAll(".$", "");
    }
    public void removeAllCharacter() {
        txt = "";
        txtInputData.setText(txt);
        if (imgCrossInput != null) {
            imgCrossInput.setVisibility(View.GONE);
        }
    }
    public void setTextBefore(String txt) {
        this.txt = txt;
    }
}
