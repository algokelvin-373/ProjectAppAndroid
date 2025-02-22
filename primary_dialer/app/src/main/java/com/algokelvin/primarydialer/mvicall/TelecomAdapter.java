package com.algokelvin.primarydialer.mvicall;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Looper;
import android.telecom.InCallService;
import android.telecom.PhoneAccountHandle;
import android.telecom.TelecomManager;

import androidx.core.app.ActivityCompat;

import com.google.common.base.Preconditions;

import java.util.List;

public final class TelecomAdapter implements InCallServiceListener {
    private static final String ADD_CALL_MODE_KEY = "add_call_mode";

    @SuppressLint("StaticFieldLeak")
    private static TelecomAdapter sInstance;
    private InCallService mInCallService;

    public static TelecomAdapter getInstance() {
        Preconditions.checkState(Looper.getMainLooper().getThread() == Thread.currentThread());
        if (sInstance == null) {
            sInstance = new TelecomAdapter();
        }
        return sInstance;
    }

    public TelecomAdapter() {
    }

    @Override
    public void setInCallService(InCallService inCallService) {
        mInCallService = inCallService;
    }

    @Override
    public void clearInCallService() {
        mInCallService = null;
    }

    private android.telecom.Call getTelecommCallById(String callId) {
        Call call = CallList.getInstance().getCallById(callId);
        return call == null ? null : call.getTelecommCall();
    }

    public void cancelCall(Context context) {
        TelecomManager telecomManager = (TelecomManager) context.getSystemService(Context.TELECOM_SERVICE);
        if (telecomManager != null) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ANSWER_PHONE_CALLS) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            telecomManager.endCall(); // Mengakhiri panggilan aktif
        }
    }

    public void answerCall(String callId, int videoState) {
        android.telecom.Call call = getTelecommCallById(callId);
        if (call != null) {
            call.answer(videoState);
        }
    }

    public void rejectCall(String callId, boolean rejectWithMessage, String message) {
        android.telecom.Call call = getTelecommCallById(callId);
        if (call != null) {
            call.reject(rejectWithMessage, message);
        }
    }

    public void disconnectCall(String callId) {
        android.telecom.Call call = getTelecommCallById(callId);
        if (call != null) {
            call.disconnect();
        }
    }

    void holdCall(String callId) {
        android.telecom.Call call = getTelecommCallById(callId);
        if (call != null) {
            call.hold();
        }
    }

    public void unholdCall(String callId) {
        android.telecom.Call call = getTelecommCallById(callId);
        if (call != null) {
            call.unhold();
        }
    }

    public void mute(boolean shouldMute) {
        if (mInCallService != null) {
            mInCallService.setMuted(shouldMute);
        }
    }

    void setAudioRoute(int route) {
        if (mInCallService != null) {
            mInCallService.setAudioRoute(route);
        }
    }

    void separateCall(String callId) {
        android.telecom.Call call = getTelecommCallById(callId);
        if (call != null) {
            call.splitFromConference();
        }
    }

    public void merge(String callId) {
        android.telecom.Call call = getTelecommCallById(callId);
        if (call != null) {
            List<android.telecom.Call> conferenceable = call.getConferenceableCalls();
            if (!conferenceable.isEmpty()) {
                call.conference(conferenceable.get(0));
            } else {
                if (call.getDetails().can(
                        android.telecom.Call.Details.CAPABILITY_MERGE_CONFERENCE)) {
                    call.mergeConference();
                }
            }
        }
    }

    public void swap(String callId) {
        android.telecom.Call call = getTelecommCallById(callId);
        if (call != null) {
            if (call.getDetails().can(
                    android.telecom.Call.Details.CAPABILITY_SWAP_CONFERENCE)) {
                call.swapConference();
            }
        }
    }

    void addCall() {
        if (mInCallService != null) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // when we request the dialer come up, we also want to inform
            // it that we're going through the "add call" option from the
            // InCallScreen / PhoneUtils.
            intent.putExtra(ADD_CALL_MODE_KEY, true);
            try {
                mInCallService.startActivity(intent);
            } catch (ActivityNotFoundException e) {
                // This is rather rare but possible.
                // Note: this method is used even when the phone is encrypted. At that moment
                // the system may not find any Activity which can accept this Intent.

            }
        }
    }

    void playDtmfTone(String callId, char digit) {
        android.telecom.Call call = getTelecommCallById(callId);
        if (call != null) {
            call.playDtmfTone(digit);
        }
    }

    void stopDtmfTone(String callId) {
        android.telecom.Call call = getTelecommCallById(callId);
        if (call != null) {
            call.stopDtmfTone();
        }
    }

    void postDialContinue(String callId, boolean proceed) {
        android.telecom.Call call = getTelecommCallById(callId);
        if (call != null) {
            call.postDialContinue(proceed);
        }
    }

    public void phoneAccountSelected(String callId, PhoneAccountHandle accountHandle, boolean setDefault) {
        if (accountHandle == null) {

            // TODO: Do we really want to send null accountHandle?
        }

        android.telecom.Call call = getTelecommCallById(callId);
        if (call != null) {
            call.phoneAccountSelected(accountHandle, setDefault);
        }
    }

    boolean canAddCall() {
        // Default to true if we are not connected to telecom.
        return mInCallService == null || mInCallService.canAddCall();
    }
}
