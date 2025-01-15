package com.sbi.mvicalllibrary.icallservices.common;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.telecom.PhoneAccount;

public class PhoneAccountSdkCompat {

    private static final String TAG = "PhoneAccountSdkCompat";

    public static final String EXTRA_CALL_SUBJECT_MAX_LENGTH =
            PhoneAccount.EXTRA_CALL_SUBJECT_MAX_LENGTH;
    public static final String EXTRA_CALL_SUBJECT_CHARACTER_ENCODING =
            PhoneAccount.EXTRA_CALL_SUBJECT_CHARACTER_ENCODING;

    public static final int CAPABILITY_VIDEO_CALLING_RELIES_ON_PRESENCE =
            PhoneAccount.CAPABILITY_VIDEO_CALLING_RELIES_ON_PRESENCE;

    @SuppressLint("NewApi")
    public static Bundle getExtras(PhoneAccount account) {
        return account.getExtras();
    }
}
