package com.algokelvin.primarydialer.mvicall.incalluibind;

import android.content.Context;
import android.content.Intent;

import com.algokelvin.primarydialer.mvicall.CachedNumberLookupService;
import com.algokelvin.primarydialer.mvicall.base.call_card.CallCardPresenter;
import com.algokelvin.primarydialer.mvicall.interfaces.PhoneNumberService;


public class ObjectFactory {

    public static CachedNumberLookupService newCachedNumberLookupService() {
        // no-op
        return null;
    }

    public static PhoneNumberService newPhoneNumberService(Context context) {
        // no phone number service.
        return null;
    }

    public static CallCardPresenter.EmergencyCallListener newEmergencyCallListener() {
        return null;
    }

    /** @return An {@link Intent} to be broadcast when the InCallUI is visible. */
    public static Intent getUiReadyBroadcastIntent(Context context) {
        return null;
    }

    /**
     * @return An {@link Intent} to be broadcast when the call state button in the InCallUI is
     * touched while in a call.
     */
    public static Intent getCallStateButtonBroadcastIntent(Context context) {
        return null;
    }
}
