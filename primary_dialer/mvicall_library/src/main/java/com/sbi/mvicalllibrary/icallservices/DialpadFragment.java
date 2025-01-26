package com.sbi.mvicalllibrary.icallservices;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.telephony.PhoneNumberUtils;
import android.text.Editable;
import android.text.method.DialerKeyListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.sbi.mvicalllibrary.R;
import com.sbi.mvicalllibrary.icallservices.widgetDialpad.dialpad.DialpadKeyButton;
import com.sbi.mvicalllibrary.icallservices.widgetDialpad.dialpad.DialpadView;

import java.util.HashMap;


public class DialpadFragment extends BaseFragment<DialpadPresenter, DialpadPresenter.DialpadUi> implements
        DialpadPresenter.DialpadUi, View.OnTouchListener, View.OnKeyListener, View.OnHoverListener, View.OnClickListener {

    private static final int ACCESSIBILITY_DTMF_STOP_DELAY_MILLIS = 50;

    private final int[] mButtonIds = new int[] {R.id.zero, R.id.one, R.id.two, R.id.three,
            R.id.four, R.id.five, R.id.six, R.id.seven, R.id.eight, R.id.nine, R.id.star,
            R.id.pound};

    private EditText mDtmfDialerField;
    private static final HashMap<Integer, Character> mDisplayMap = new HashMap<>();
    private static final Handler sHandler = new Handler(Looper.getMainLooper());

    static {
        mDisplayMap.put(R.id.one, '1');
        mDisplayMap.put(R.id.two, '2');
        mDisplayMap.put(R.id.three, '3');
        mDisplayMap.put(R.id.four, '4');
        mDisplayMap.put(R.id.five, '5');
        mDisplayMap.put(R.id.six, '6');
        mDisplayMap.put(R.id.seven, '7');
        mDisplayMap.put(R.id.eight, '8');
        mDisplayMap.put(R.id.nine, '9');
        mDisplayMap.put(R.id.zero, '0');
        mDisplayMap.put(R.id.pound, '#');
        mDisplayMap.put(R.id.star, '*');
    }

    private DTMFKeyListener mDialerKeyListener;
    private DialpadView mDialpadView;
    private int mCurrentTextColor;
    private ImageButton mDeleteButton;

    private class DTMFKeyListener extends DialerKeyListener {

        private DTMFKeyListener() {
            super();
        }
        @Override
        protected char[] getAcceptedChars(){
            return DTMF_CHARACTERS;
        }

        @Override
        public boolean backspace(View view, Editable content, int keyCode, KeyEvent event) {
            return false;
        }


        @Override
        public boolean onKeyDown(View view, Editable content, int keyCode, KeyEvent event) {
            char c = (char) lookup(event, content);
            if (event.getRepeatCount() == 0 && super.onKeyDown(view, content, keyCode, event)) {
                boolean keyOK = ok(getAcceptedChars(), c);
                if (keyOK) {
                    getPresenter().processDtmf(c);
                }
                return true;
            }
            return false;
        }

        @Override
        public boolean onKeyUp(View view, Editable content,
                               int keyCode, KeyEvent event) {

            super.onKeyUp(view, content, keyCode, event);

            char c = (char) lookup(event, content);

            boolean keyOK = ok(getAcceptedChars(), c);

            if (keyOK) {
                Log.d(this, "Stopping the tone for '" + c + "'");
                getPresenter().stopDtmf();
                return true;
            }

            return false;
        }

        public boolean onKeyDown(KeyEvent event) {
            char c = lookup(event);
            Log.d(this, "DTMFKeyListener.onKeyDown: event '" + c + "'");

            if (event.getRepeatCount() == 0 && c != 0) {
                if (ok(getAcceptedChars(), c)) {
                    Log.d(this, "DTMFKeyListener reading '" + c + "' from input.");
                    getPresenter().processDtmf(c);
                    return true;
                } else {
                    Log.d(this, "DTMFKeyListener rejecting '" + c + "' from input.");
                }
            }
            return false;
        }


        public boolean onKeyUp(KeyEvent event) {
            if (event == null) {
                /*if (DBG) log("Stopping the last played tone.");
                stopTone();*/
                return true;
            }

            char c = lookup(event);
            Log.d(this, "DTMFKeyListener.onKeyUp: event '" + c + "'");

            if (ok(getAcceptedChars(), c)) {
                Log.d(this, "Stopping the tone for '" + c + "'");
                getPresenter().stopDtmf();
                return true;
            }

            return false;
        }


        private char lookup(KeyEvent event) {
            int meta = event.getMetaState();
            int number = event.getNumber();

            if (!((meta & (KeyEvent.META_ALT_ON | KeyEvent.META_SHIFT_ON)) == 0) || (number == 0)) {
                int match = event.getMatch(getAcceptedChars(), meta);
                number = (match != 0) ? match : number;
            }

            return (char) number;
        }

        public final char[] DTMF_CHARACTERS = new char[] {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '#', '*'
        };
    }

    @Override
    public void onClick(View v) {
        final AccessibilityManager accessibilityManager = (AccessibilityManager)
            v.getContext().getSystemService(Context.ACCESSIBILITY_SERVICE);
        if (accessibilityManager.isEnabled()) {
            final int id = v.getId();
            if (!v.isPressed() && mDisplayMap.containsKey(id)) {
                getPresenter().processDtmf(mDisplayMap.get(id));
                sHandler.postDelayed(() -> getPresenter().stopDtmf(), ACCESSIBILITY_DTMF_STOP_DELAY_MILLIS);
            }
        }
    }

    @Override
    public boolean onHover(View v, MotionEvent event) {
        final AccessibilityManager accessibilityManager = (AccessibilityManager)
            v.getContext().getSystemService(Context.ACCESSIBILITY_SERVICE);

        if (accessibilityManager.isEnabled()
                && accessibilityManager.isTouchExplorationEnabled()) {
            final int left = v.getPaddingLeft();
            final int right = (v.getWidth() - v.getPaddingRight());
            final int top = v.getPaddingTop();
            final int bottom = (v.getHeight() - v.getPaddingBottom());

            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_HOVER_ENTER:
                    v.setClickable(false);
                    break;
                case MotionEvent.ACTION_HOVER_EXIT:
                    final int x = (int) event.getX();
                    final int y = (int) event.getY();
                    if ((x > left) && (x < right) && (y > top) && (y < bottom)) {
                        v.performClick();
                    }
                    v.setClickable(true);
                    break;
            }
        }

        return false;
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        Log.d(this, "onKey:  keyCode " + keyCode + ", view " + v);

        if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
            int viewId = v.getId();
            if (mDisplayMap.containsKey(viewId)) {
                switch (event.getAction()) {
                case KeyEvent.ACTION_DOWN:
                    if (event.getRepeatCount() == 0) {
                        getPresenter().processDtmf(mDisplayMap.get(viewId));
                    }
                    break;
                case KeyEvent.ACTION_UP:
                    getPresenter().stopDtmf();
                    break;
                }
            }
        }
        return false;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.d(this, "onTouch");
        int viewId = v.getId();

        if (mDisplayMap.containsKey(viewId)) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    char c = mDisplayMap.get(viewId);
                    getPresenter().processDtmf(c);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    getPresenter().stopDtmf();
                    break;
            }
        }
        return false;
    }


    @Override
    public DialpadPresenter createPresenter() {
        return new DialpadPresenter();
    }

    @Override
    public DialpadPresenter.DialpadUi getUi() {
        return this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View parent = inflater.inflate(R.layout.incall_dialpad_fragment, container, false);
        mDialpadView = parent.findViewById(R.id.dialpad_view);
        mDialpadView.setCanDigitsBeEdited(false);
        mDialpadView.setBackgroundResource(R.color.incall_dialpad_background);
        mDeleteButton = parent.findViewById(R.id.deleteButton);
        mDtmfDialerField = parent.findViewById(R.id.digits);
        if (mDtmfDialerField != null) {
            mDialerKeyListener = new DTMFKeyListener();
            mDtmfDialerField.setKeyListener(mDialerKeyListener);
            mDtmfDialerField.setLongClickable(false);
            mDtmfDialerField.setElegantTextHeight(false);
            configureKeypadListeners();
        }

        mDeleteButton.setOnClickListener(v -> mDtmfDialerField.setText(""));

        return parent;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateColors();
    }

    public void updateColors() {
        int textColor = InCallPresenter.getInstance().getThemeColors().mPrimaryColor;
        if (getContext().getResources().getBoolean(R.bool.config_dialpadDigitsStaticColor)) {
            textColor = getContext().getResources().getColor(R.color.dialpad_digits_color);
        }

        if (mCurrentTextColor == textColor) {
            return;
        }

        DialpadKeyButton dialpadKey;
        for (int i = 0; i < mButtonIds.length; i++) {
            dialpadKey = mDialpadView.findViewById(mButtonIds[i]);
            ((TextView) dialpadKey.findViewById(R.id.dialpad_key_number)).setTextColor(textColor);
        }

        mCurrentTextColor = textColor;
    }

    @Override
    public void onDestroyView() {
        mDialerKeyListener = null;
        super.onDestroyView();
    }

    public String getDtmfText() {
        if(mDtmfDialerField != null) {
            return mDtmfDialerField.getText().toString();
        }
        return "";
    }

    public void setDtmfText(String text) {
        if(mDtmfDialerField != null) {
            mDtmfDialerField.setText(PhoneNumberUtils.createTtsSpannable(text));
        }
    }

    @Override
    public void setVisible(boolean on) {
        if (on) {
            getView().setVisibility(View.VISIBLE);
        } else {
            getView().setVisibility(View.INVISIBLE);
        }
    }

    public void animateShowDialpad() {
        final DialpadView dialpadView = getView().findViewById(R.id.dialpad_view);
        dialpadView.animateShow();
    }

    @Override
    public void appendDigitsToField(char digit) {
        if (mDtmfDialerField != null) {
            mDtmfDialerField.getText().append(digit);
        }
    }

    boolean onDialerKeyDown(KeyEvent event) {
        Log.d(this, "Notifying dtmf key down.");
        if (mDialerKeyListener != null) {
            return mDialerKeyListener.onKeyDown(event);
        } else {
            return false;
        }
    }


    public boolean onDialerKeyUp(KeyEvent event) {
        Log.d(this, "Notifying dtmf key up.");
        if (mDialerKeyListener != null) {
            return mDialerKeyListener.onKeyUp(event);
        } else {
            return false;
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void configureKeypadListeners() {
        DialpadKeyButton dialpadKey;
        for (int i = 0; i < mButtonIds.length; i++) {
            dialpadKey = mDialpadView.findViewById(mButtonIds[i]);
            dialpadKey.setOnTouchListener(this);
            dialpadKey.setOnKeyListener(this);
            dialpadKey.setOnHoverListener(this);
            dialpadKey.setOnClickListener(this);
        }
    }
}
