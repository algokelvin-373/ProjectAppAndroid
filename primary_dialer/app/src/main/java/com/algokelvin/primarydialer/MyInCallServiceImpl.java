package com.algokelvin.primarydialer;

import android.content.Intent;
import android.telecom.Call;
import android.telecom.InCallService;
import android.util.Log;

public class MyInCallServiceImpl extends InCallService {
    private static Call currentCall;
    @Override
    public void onCallAdded(Call call) {
        super.onCallAdded(call);
        Log.d("InCallService", "Call added: " + call);
        currentCall = call;

        call.registerCallback(new Call.Callback() {
            @Override
            public void onStateChanged(Call call, int state) {
                Log.d("InCallService", "Call state changed: " + state);
            }
        });
        Intent intent = new Intent(this, InCallActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onCallRemoved(Call call) {
        super.onCallRemoved(call);
        Log.d("InCallService", "Call removed: " + call);
        currentCall = null;
    }

    public static Call getCall() {
        return currentCall;
    }
}