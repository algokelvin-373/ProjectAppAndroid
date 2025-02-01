package com.algokelvin.primarydialer.mvicall.base.call_card;

public class CallStateLabel {
    private CharSequence mCallStateLabel;
    private boolean mIsAutoDismissing;

    public CallStateLabel(CharSequence callStateLabel, boolean isAutoDismissing) {
        mCallStateLabel = callStateLabel;
        mIsAutoDismissing = isAutoDismissing;
    }

    public CharSequence getCallStateLabel() {
        return mCallStateLabel;
    }

    public boolean isAutoDismissing() {
        return mIsAutoDismissing;
    }
}