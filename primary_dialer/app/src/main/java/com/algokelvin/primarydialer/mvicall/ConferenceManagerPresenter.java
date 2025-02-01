package com.algokelvin.primarydialer.mvicall;

import android.content.Context;

import com.algokelvin.primarydialer.mvicall.base.in_call.InCallPresenter;
import com.google.common.base.Preconditions;
import com.algokelvin.primarydialer.mvicall.base.in_call.InCallPresenter.InCallDetailsListener;
import com.algokelvin.primarydialer.mvicall.base.in_call.InCallPresenter.InCallState;
import com.algokelvin.primarydialer.mvicall.base.in_call.InCallPresenter.InCallStateListener;
import com.algokelvin.primarydialer.mvicall.base.in_call.InCallPresenter.IncomingCallListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Logic for call buttons.
 */
public class ConferenceManagerPresenter
        extends Presenter<ConferenceManagerPresenter.ConferenceManagerUi>
        implements InCallStateListener, InCallDetailsListener, IncomingCallListener {

    private Context mContext;

    @Override
    public void onUiReady(ConferenceManagerUi ui) {
        super.onUiReady(ui);

        // register for call state changes last
        InCallPresenter.getInstance().addListener(this);
        InCallPresenter.getInstance().addIncomingCallListener(this);
    }

    @Override
    public void onUiUnready(ConferenceManagerUi ui) {
        super.onUiUnready(ui);

        InCallPresenter.getInstance().removeListener(this);
        InCallPresenter.getInstance().removeIncomingCallListener(this);
    }

    @Override
    public void onStateChange(InCallState oldState, InCallState newState, CallList callList) {
        if (getUi().isFragmentVisible()) {
            //Log.v(this, "onStateChange" + newState);
            if (newState == InCallState.INCALL) {
                final Call call = callList.getActiveOrBackgroundCall();
                if (call != null && call.isConferenceCall()) {
                    //Log.v(this, "Number of existing calls is " + call.getChildCallIds().size());
                    update(callList);
                } else {
                    InCallPresenter.getInstance().showConferenceCallManager(false);
                }
            } else {
                InCallPresenter.getInstance().showConferenceCallManager(false);
            }
        }
    }

    @Override
    public void onDetailsChanged(Call call, android.telecom.Call.Details details) {
        boolean canDisconnect = details.can(
                android.telecom.Call.Details.CAPABILITY_DISCONNECT_FROM_CONFERENCE);
        boolean canSeparate = details.can(
                android.telecom.Call.Details.CAPABILITY_SEPARATE_FROM_CONFERENCE);

        if (call.can(android.telecom.Call.Details.CAPABILITY_DISCONNECT_FROM_CONFERENCE)
                != canDisconnect
            || call.can(android.telecom.Call.Details.CAPABILITY_SEPARATE_FROM_CONFERENCE)
                != canSeparate) {
            getUi().refreshCall(call);
        }

        if (!details.can(
                android.telecom.Call.Details.CAPABILITY_MANAGE_CONFERENCE)) {
            InCallPresenter.getInstance().showConferenceCallManager(false);
        }
    }

    @Override
    public void onIncomingCall(InCallState oldState, InCallState newState, Call call) {
        // When incoming call exists, set conference ui invisible.
        if (getUi().isFragmentVisible()) {
            //Log.d(this, "onIncomingCall()... Conference ui is showing, hide it.");
            InCallPresenter.getInstance().showConferenceCallManager(false);
        }
    }

    public void init(Context context, CallList callList) {
        mContext = Preconditions.checkNotNull(context);
        mContext = context;
        update(callList);
    }

    /**
     * Updates the conference participant adapter.
     *
     * @param callList The callList.
     */
    private void update(CallList callList) {
        // callList is non null, but getActiveOrBackgroundCall() may return null
        final Call currentCall = callList.getActiveOrBackgroundCall();
        if (currentCall == null) {
            return;
        }

        ArrayList<Call> calls = new ArrayList<>(currentCall.getChildCallIds().size());
        for (String callerId : currentCall.getChildCallIds()) {
            calls.add(callList.getCallById(callerId));
        }

        //Log.d(this, "Number of calls is " + calls.size());

        // Users can split out a call from the conference call if either the active call or the
        // holding call is empty. If both are filled, users can not split out another call.
        final boolean hasActiveCall = (callList.getActiveCall() != null);
        final boolean hasHoldingCall = (callList.getBackgroundCall() != null);
        boolean canSeparate = !(hasActiveCall && hasHoldingCall);

        getUi().update(mContext, calls, canSeparate);
    }

    public interface ConferenceManagerUi extends Ui {
        boolean isFragmentVisible();
        void update(Context context, List<Call> participants, boolean parentCanSeparate);
        void refreshCall(Call call);
    }
}
