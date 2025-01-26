package com.sbi.mvicalllibrary.icallservices;

import android.telephony.PhoneNumberUtils;

/**
 * Logic for call buttons.
 */
public class DialpadPresenter extends Presenter<DialpadPresenter.DialpadUi> implements InCallPresenter.InCallStateListener {

    private Call mCall;
    private static final String ID_PREFIX = Call.class.getSimpleName() + "_";
    private final int sIdCounter = 0;

    @Override
    public void onUiReady(DialpadUi ui) {
        super.onUiReady(ui);
        InCallPresenter.getInstance().addListener(this);
        mCall = CallList.getInstance().getOutgoingOrActive();
    }

    @Override
    public void onUiUnready(DialpadUi ui) {
        super.onUiUnready(ui);
        InCallPresenter.getInstance().removeListener(this);
    }

    @Override
    public void onStateChange(InCallPresenter.InCallState oldState, InCallPresenter.InCallState newState, CallList callList) {
        mCall = callList.getOutgoingOrActive();
    }

    /**
     * Processes the specified digit as a DTMF key, by playing the
     * appropriate DTMF tone, and appending the digit to the EditText
     * field that displays the DTMF digits sent so far.
     *
     */
    public final void processDtmf(char c) {
        if (PhoneNumberUtils.is12Key(c) && mCall != null) {
            getUi().appendDigitsToField(c);
            String fn = mCall.getId();
            TelecomAdapter.getInstance().playDtmfTone(fn, c);
        }
    }

    public void stopDtmf() {
        if (mCall != null) {
            TelecomAdapter.getInstance().stopDtmfTone(mCall.getId());
        }
    }

    public interface DialpadUi extends Ui {
        void setVisible(boolean on);
        void appendDigitsToField(char digit);
    }
}
