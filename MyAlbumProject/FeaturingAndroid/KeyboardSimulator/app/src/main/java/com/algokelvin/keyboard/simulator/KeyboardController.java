package com.algokelvin.keyboard.simulator;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class KeyboardController extends AppCompatActivity implements View.OnClickListener {
    private ImageView[] crossData;
    private ConstraintLayout[] clData;
    private TextView[] txtData;
    private ConstraintLayout clBefore , clNow;
    private ImageView crossBefore, crossNow;
    private TextView txtBefore, txtNow;

    public TextView txtInputData;
    public TextView keyboard00, keyboard01, keyboard02, keyboard03, keyboard04, keyboard05;
    public TextView keyboard06, keyboard07, keyboard08, keyboard09;
    public TextView keyboardCancel, keyboardOK;
    public ConstraintLayout keyBtnBack;
    public ImageView imgCrossInput;
    private String txt = "";

    public void setClData(int ...cl) {
        clData = new ConstraintLayout[cl.length];
        for (int x = 0; x < clData.length; x++) {
            clData[x] = findViewById(cl[x]);
        }
    }
    public void setCrossData(int ...cross) {
        crossData = new ImageView[cross.length];
        for (int x = 0; x < crossData.length; x++) {
            crossData[x] = findViewById(cross[x]);
        }
    }
    public void setTxtData(int ...txt) {
        txtData = new TextView[txt.length];
        for (int x = 0; x < txtData.length; x++) {
            txtData[x] = findViewById(txt[x]);
        }
    }
    public void setKeyboardController(int idTxt, int idCross) {
        clBefore = clData[0];
        clNow = clData[0];
        crossBefore = crossData[0];
        crossNow = crossData[0];
        txtBefore = txtData[0];
        txtNow = txtData[0];

        setControllerInputData(idTxt);
        setImgCrossInput(idCross);
        setControllerKeyboard();
        setKeyboardOnClickListener();
    }

    public void setTextAction(int x) {
        txtBefore.setText(txtInputData.getText().toString());
        setActionTextKeyboardSimulator(clData[x], crossData[x], txtData[x]);
        setTextBefore(txtData[x].getText().toString());
    }

    public ConstraintLayout getClData(int x) {
        return clData[x];
    }

    private void setControllerInputData(int id) {
        txtInputData = findViewById(id);
    }

    private void setImgCrossInput(int id) {
        imgCrossInput = findViewById(id);
        imgCrossInput.setOnClickListener(v -> removeAllCharacter());
    }

    private void setControllerKeyboard() {
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
    private void setKeyboardOnClickListener() {
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
    private void setConstrainLayoutTextView(ConstraintLayout cl) {
        clBefore.setBackgroundResource(R.drawable.bg_menu_white);
        clNow = cl;
        clNow.setBackgroundResource(R.drawable.bg_menu_grey);
        clBefore = clNow;
    }
    private void setCrossTextView(ImageView cross) {
        crossBefore.setVisibility(View.GONE);
        crossNow = cross;
        crossNow.setVisibility(View.VISIBLE);
        crossBefore = crossNow;
        setImgCrossInput(crossNow.getId());
    }
    private void setActionTextKeyboardSimulator(ConstraintLayout cl, ImageView cross, TextView txt) {
        setConstrainLayoutTextView(cl);
        setCrossTextView(cross);
        if (txtBefore != null)
            setTextBefore(txtBefore.getText().toString());
        txtNow = txt;
        txtBefore = txtNow;
        setControllerInputData(txt.getId());
    }
    private String removeLastCharacter(String str) {
        return (str == null) ? null : str.replaceAll(".$", "");
    }
    private void removeAllCharacter() {
        txt = "";
        txtInputData.setText(txt);
        if (imgCrossInput != null) {
            imgCrossInput.setVisibility(View.GONE);
        }
    }
    private void setTextBefore(String txt) {
        this.txt = txt;
    }
}
