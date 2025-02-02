package com.algokelvin.primarydialer.mvicall;

import android.os.Handler;
import android.os.Message;
import android.os.Trace;
import android.telecom.DisconnectCause;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class CallList {

    private static final int DISCONNECTED_CALL_SHORT_TIMEOUT_MS = 200;
    private static final int DISCONNECTED_CALL_MEDIUM_TIMEOUT_MS = 2000;
    private static final int DISCONNECTED_CALL_LONG_TIMEOUT_MS = 5000;

    private static final int EVENT_DISCONNECTED_TIMEOUT = 1;

    private static final CallList sInstance = new CallList();

    private final HashMap<String, Call> mCallById = new HashMap<>();
    private final HashMap<android.telecom.Call, Call> mCallByTelecommCall = new HashMap<>();
    private final HashMap<String, List<String>> mCallTextReponsesMap = com.google.common.collect.Maps.newHashMap();
    private final Set<Listener> mListeners = Collections.newSetFromMap(new ConcurrentHashMap<Listener, Boolean>(8, 0.9f, 1));
    private final HashMap<String, List<CallUpdateListener>> mCallUpdateListenerMap = Maps.newHashMap();
    private final Set<Call> mPendingDisconnectCalls = Collections.newSetFromMap(new ConcurrentHashMap<Call, Boolean>(8, 0.9f, 1));

    public static CallList getInstance() {
        return sInstance;
    }

    public CallList() {
    }

    public void onCallAdded(android.telecom.Call telecommCall) {
        Trace.beginSection("onCallAdded");
        Call call = new Call(telecommCall);
        if (call.getState() == Call.State.INCOMING || call.getState() == Call.State.CALL_WAITING) {
            onIncoming(call, call.getCannedSmsResponses());
        } else {
            onUpdate(call);
        }
        Trace.endSection();
    }

    public void onCallRemoved(android.telecom.Call telecommCall) {
        if (mCallByTelecommCall.containsKey(telecommCall)) {
            Call call = mCallByTelecommCall.get(telecommCall);
            if (updateCallInMap(call)) {
            }
            updateCallTextMap(call, null);
        }
    }

    public void onDisconnect(Call call) {
        if (updateCallInMap(call)) {
            notifyCallUpdateListeners(call);
            notifyListenersOfDisconnect(call);
        }
    }

    public void onIncoming(Call call, List<String> textMessages) {
        if (updateCallInMap(call)) {
        }
        updateCallTextMap(call, textMessages);

        for (Listener listener : mListeners) {
            listener.onIncomingCall(call);
        }
    }

    public void onUpgradeToVideo(Call call) {
        for (Listener listener : mListeners) {
            listener.onUpgradeToVideo(call);
        }
    }

    public void onUpdate(Call call) {
        Trace.beginSection("onUpdate");
        onUpdateCall(call);
//        notifyGenericListeners();
        Trace.endSection();
    }

    public void onSessionModificationStateChange(Call call, int sessionModificationState) {
        final List<CallUpdateListener> listeners = mCallUpdateListenerMap.get(call.getId());
        if (listeners != null) {
            for (CallUpdateListener listener : listeners) {
                listener.onSessionModificationStateChange(sessionModificationState);
            }
        }
    }

    public void onLastForwardedNumberChange(Call call) {
        final List<CallUpdateListener> listeners = mCallUpdateListenerMap.get(call.getId());
        if (listeners != null) {
            for (CallUpdateListener listener : listeners) {
                listener.onLastForwardedNumberChange();
            }
        }
    }

    public void onChildNumberChange(Call call) {
        final List<CallUpdateListener> listeners = mCallUpdateListenerMap.get(call.getId());
        if (listeners != null) {
            for (CallUpdateListener listener : listeners) {
                listener.onChildNumberChange();
            }
        }
    }

    public void notifyCallUpdateListeners(Call call) {
        final List<CallUpdateListener> listeners = mCallUpdateListenerMap.get(call.getId());
        if (listeners != null) {
            for (CallUpdateListener listener : listeners) {
                listener.onCallChanged(call);
            }
        }
    }

    public void addCallUpdateListener(String callId, CallUpdateListener listener) {
        List<CallUpdateListener> listeners = mCallUpdateListenerMap.get(callId);
        if (listeners == null) {
            listeners = new CopyOnWriteArrayList<CallUpdateListener>();
            mCallUpdateListenerMap.put(callId, listeners);
        }
        listeners.add(listener);
    }

    public void removeCallUpdateListener(String callId, CallUpdateListener listener) {
        List<CallUpdateListener> listeners = mCallUpdateListenerMap.get(callId);
        if (listeners != null) {
            listeners.remove(listener);
        }
    }

    public void addListener(Listener listener) {
        Preconditions.checkNotNull(listener);

        mListeners.add(listener);

        listener.onCallListChange(this);
    }

    public void removeListener(Listener listener) {
        if (listener != null) {
            mListeners.remove(listener);
        }
    }

    public Call getIncomingOrActive() {
        Call retval = getIncomingCall();
        if (retval == null) {
            retval = getActiveCall();
        }
        return retval;
    }

    public Call getOutgoingOrActive() {
        Call retval = getOutgoingCall();
        if (retval == null) {
            retval = getActiveCall();
        }
        return retval;
    }


    public Call getWaitingForAccountCall() {
        return getFirstCallWithState(Call.State.SELECT_PHONE_ACCOUNT);
    }

    public Call getPendingOutgoingCall() {
        return getFirstCallWithState(Call.State.CONNECTING);
    }

    public Call getOutgoingCall() {
        Call call = getFirstCallWithState(Call.State.DIALING);
        if (call == null) {
            call = getFirstCallWithState(Call.State.REDIALING);
        }
        return call;
    }

    public Call getActiveCall() {
        return getFirstCallWithState(Call.State.ACTIVE);
    }

    public Call getBackgroundCall() {
        return getFirstCallWithState(Call.State.ONHOLD);
    }

    public Call getDisconnectedCall() {
        return getFirstCallWithState(Call.State.DISCONNECTED);
    }

    public Call getDisconnectingCall() {
        return getFirstCallWithState(Call.State.DISCONNECTING);
    }

    public Call getSecondBackgroundCall() {
        return getCallWithState(Call.State.ONHOLD, 1);
    }

    public Call getActiveOrBackgroundCall() {
        Call call = getActiveCall();
        if (call == null) {
            call = getBackgroundCall();
        }
        return call;
    }

    public Call getIncomingCall() {
        Call call = getFirstCallWithState(Call.State.INCOMING);
        if (call == null) {
            call = getFirstCallWithState(Call.State.CALL_WAITING);
        }

        return call;
    }

    public Call getFirstCall() {
        Call result = getIncomingCall();
        if (result == null) {
            result = getPendingOutgoingCall();
        }
        if (result == null) {
            result = getOutgoingCall();
        }
        if (result == null) {
            result = getFirstCallWithState(Call.State.ACTIVE);
        }
        if (result == null) {
            result = getDisconnectingCall();
        }
        if (result == null) {
            result = getDisconnectedCall();
        }
        return result;
    }

    public boolean hasLiveCall() {
        Call call = getFirstCall();
        if (call == null) {
            return false;
        }
        return call != getDisconnectingCall() && call != getDisconnectedCall();
    }


    public Call getVideoUpgradeRequestCall() {
        for (Call call : mCallById.values()) {
            if (call.getSessionModificationState() == Call.SessionModificationState.RECEIVED_UPGRADE_TO_VIDEO_REQUEST) {
                return call;
            }
        }
        return null;
    }

    public Call getCallById(String callId) {
        return mCallById.get(callId);
    }

    public Call getCallByTelecommCall(android.telecom.Call telecommCall) {
        return mCallByTelecommCall.get(telecommCall);
    }

    public List<String> getTextResponses(String callId) {
        return mCallTextReponsesMap.get(callId);
    }

    public Call getFirstCallWithState(int state) {
        return getCallWithState(state, 0);
    }

    public Call getCallWithState(int state, int positionToFind) {
        Call retval = null;
        int position = 0;
        for (Call call : mCallById.values()) {
            if (call.getState() == state) {
                if (position >= positionToFind) {
                    retval = call;
                    break;
                } else {
                    position++;
                }
            }
        }

        return retval;
    }

    public void clearOnDisconnect() {
        for (Call call : mCallById.values()) {
            final int state = call.getState();
            if (state != Call.State.IDLE && state != Call.State.INVALID && state != Call.State.DISCONNECTED) {

                call.setState(Call.State.DISCONNECTED);
                call.setDisconnectCause(new DisconnectCause(DisconnectCause.UNKNOWN));
                updateCallInMap(call);
            }
        }
        notifyGenericListeners();
    }

    public void onErrorDialogDismissed() {
        final Iterator<Call> iterator = mPendingDisconnectCalls.iterator();
        while (iterator.hasNext()) {
            Call call = iterator.next();
            iterator.remove();
            finishDisconnectedCall(call);
        }
    }


    private void onUpdateCall(Call call) {
        if (updateCallInMap(call)) {
        }
        updateCallTextMap(call, call.getCannedSmsResponses());
        notifyCallUpdateListeners(call);
    }

    private void notifyGenericListeners() {
//        for (Listener listener : mListeners) {
//            listener.onCallListChange(this);
//        }
    }

    private void notifyListenersOfDisconnect(Call call) {
        for (Listener listener : mListeners) {
            listener.onDisconnect(call);
        }
    }

    private boolean updateCallInMap(Call call) {
        Preconditions.checkNotNull(call);

        boolean updated = false;

        if (call.getState() == Call.State.DISCONNECTED) {
            if (mCallById.containsKey(call.getId())) {

                final Message msg = mHandler.obtainMessage(EVENT_DISCONNECTED_TIMEOUT, call);
                mHandler.sendMessageDelayed(msg, getDelayForDisconnect(call));
                mPendingDisconnectCalls.add(call);

                mCallById.put(call.getId(), call);
                mCallByTelecommCall.put(call.getTelecommCall(), call);
                updated = true;
            }
        } else if (!isCallDead(call)) {
            mCallById.put(call.getId(), call);
            mCallByTelecommCall.put(call.getTelecommCall(), call);
            updated = true;
        } else if (mCallById.containsKey(call.getId())) {
            mCallById.remove(call.getId());
            mCallByTelecommCall.remove(call.getTelecommCall());
            updated = true;
        }

        return updated;
    }

    private int getDelayForDisconnect(Call call) {
        Preconditions.checkState(call.getState() == Call.State.DISCONNECTED);

        final int cause = call.getDisconnectCause().getCode();
        final int delay;
        switch (cause) {
            case DisconnectCause.LOCAL:
                delay = DISCONNECTED_CALL_SHORT_TIMEOUT_MS;
                break;
            case DisconnectCause.REMOTE:
            case DisconnectCause.ERROR:
                delay = DISCONNECTED_CALL_MEDIUM_TIMEOUT_MS;
                break;
            case DisconnectCause.REJECTED:
            case DisconnectCause.MISSED:
            case DisconnectCause.CANCELED:
                delay = 0;
                break;
            default:
                delay = DISCONNECTED_CALL_LONG_TIMEOUT_MS;
                break;
        }

        return delay;
    }

    private void updateCallTextMap(Call call, List<String> textResponses) {
        Preconditions.checkNotNull(call);

        if (!isCallDead(call)) {
            if (textResponses != null) {
                mCallTextReponsesMap.put(call.getId(), textResponses);
            }
        } else if (mCallById.containsKey(call.getId())) {
            mCallTextReponsesMap.remove(call.getId());
        }
    }

    private boolean isCallDead(Call call) {
        final int state = call.getState();
        return Call.State.IDLE == state || Call.State.INVALID == state;
    }

    private void finishDisconnectedCall(Call call) {
        mPendingDisconnectCalls.remove(call);
        call.setState(Call.State.IDLE);
        updateCallInMap(call);
        notifyGenericListeners();
    }

    public void notifyCallsOfDeviceRotation(int rotation) {
        for (Call call : mCallById.values()) {
            if (call.getVideoCall() != null && CallUtils.isVideoCall(call)) {
                call.getVideoCall().setDeviceOrientation(rotation);
            }
        }
    }

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case EVENT_DISCONNECTED_TIMEOUT:
                    finishDisconnectedCall((Call) msg.obj);
                    break;
                default:
                    break;
            }
        }
    };

    public interface Listener {
        void onIncomingCall(Call call);

        void onUpgradeToVideo(Call call);

        void onCallListChange(CallList callList);

        void onDisconnect(Call call);
    }

    public interface CallUpdateListener {
        void onCallChanged(Call call);

        void onSessionModificationStateChange(int sessionModificationState);

        void onLastForwardedNumberChange();

        void onChildNumberChange();
    }
}
