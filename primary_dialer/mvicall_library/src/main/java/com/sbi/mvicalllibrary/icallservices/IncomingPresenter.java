package com.sbi.mvicalllibrary.icallservices;

import android.content.Context;
import android.net.Uri;
import android.telecom.VideoProfile;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.sbi.mvicalllibrary.icallservices.Utils.TelecomUtil;

import java.util.List;

public class IncomingPresenter extends Presenter<IncomingPresenter.AnswerUi> implements CallList.CallUpdateListener, InCallPresenter.InCallUiListener, InCallPresenter.IncomingCallListener, CallList.Listener {

    private String mCallId;
    private Call mCall = null;
    private boolean mHasTextMessages = false;
    private static IncomingPresenter sInCallPresenter;
    private static OnInCommingNumber mOnInCommingNumber;
    private static String _msisdn;
    private static boolean isIncomingCall = false;


    @NonNull
    public static synchronized IncomingPresenter getInstance() {
        if (sInCallPresenter == null) {
            sInCallPresenter = new IncomingPresenter();
        }
        return sInCallPresenter;
    }

    IncomingPresenter(){}

    IncomingPresenter(OnInCommingNumber onInCommingNumber){
        mOnInCommingNumber = onInCommingNumber;
        if (mOnInCommingNumber != null && !TextUtils.isEmpty(_msisdn)){
            if (isIncomingCall) {
                mOnInCommingNumber.onGetNumber(_msisdn);
                isIncomingCall = false;
            }
        }
    }

    void onIncommingCallAdded(android.telecom.Call call) {
        String phoneNumber = infoPhoneNumber(call);
        if (!TextUtils.isEmpty(phoneNumber)) {
            if (call.getState() == android.telecom.Call.STATE_RINGING) {
                _msisdn = phoneNumber;
            }
        }
    }

    private String infoPhoneNumber(android.telecom.Call details){
        String subscribeInfo;
        CharSequence charSequence;
        Uri uri = details.getDetails().getHandle();
        charSequence = Uri.decode(uri != null ? uri.getSchemeSpecificPart() : null);
        if (charSequence != null){
            subscribeInfo = charSequence.toString();
        }else {
            subscribeInfo = "";
        }
        return subscribeInfo;
    }


    @Override
    public void onUiShowing(boolean showing) {
        if (showing) {
            final CallList calls = CallList.getInstance();
            Call call;
            call = calls.getIncomingCall();
            if (call != null) {
                processIncomingCall(call);
            }
            call = calls.getVideoUpgradeRequestCall();

            if (call != null) {
                processVideoUpgradeRequestCall(call);
            }
        } else {
            if (mCallId != null) {
                CallList.getInstance().removeCallUpdateListener(mCallId, this);
            }
        }
    }

    @Override
    public void onIncomingCall(@NonNull Call call) {
        System.out.println(call);
    }

    @Override
    public void onCallListChange(@NonNull CallList list) {
        System.out.println(list);
    }

    @Override
    public void onDisconnect(@NonNull Call call) {
        System.out.println(call);
    }

    public void onSessionModificationStateChange(int sessionModificationState) {
        boolean isUpgradePending = sessionModificationState == Call.SessionModificationState.RECEIVED_UPGRADE_TO_VIDEO_REQUEST;

        if (!isUpgradePending) {
            CallList.getInstance().removeCallUpdateListener(mCallId, this);
            showAnswerUi(false);
        }
    }

    @Override
    public void onLastForwardedNumberChange() {
    }

    @Override
    public void onChildNumberChange() {
    }

    private boolean isVideoUpgradePending(Call call) {
        return call.getSessionModificationState() == Call.SessionModificationState.RECEIVED_UPGRADE_TO_VIDEO_REQUEST;
    }

    @Override
    public void onUpgradeToVideo(@NonNull Call call) {
        if (getUi() == null) {
            return;
        }
        boolean isUpgradePending = isVideoUpgradePending(call);
        InCallPresenter inCallPresenter = InCallPresenter.getInstance();
        if (isUpgradePending && inCallPresenter.getInCallState() == InCallPresenter.InCallState.INCOMING) {


            inCallPresenter.declineUpgradeRequest(getUi().getContext());
        } else if (isUpgradePending) {

            processVideoUpgradeRequestCall(call);
        }
    }

    private void processIncomingCall(Call call) {
        mCallId = call.getId();
        mCall = call;
        CallList.getInstance().addCallUpdateListener(mCallId, this);
        if (showAnswerUi(true)) {
            final List<String> textMsgs = CallList.getInstance().getTextResponses(call.getId());
            configureAnswerTargetsForSms(call, textMsgs);
        }
    }

    private boolean showAnswerUi(boolean show) {
        final InCallActivity activity = InCallPresenter.getInstance().getActivity();
        if (activity != null) {
            activity.showAnswerFragment(show);
            if (getUi() != null) {
                getUi().onShowAnswerUi(show);
            }
            return true;
        } else {
            return false;
        }
    }

    private void processVideoUpgradeRequestCall(Call call) {
        mCallId = call.getId();
        mCall = call;


        CallList.getInstance().addCallUpdateListener(mCallId, this);

        final int currentVideoState = call.getVideoState();
        final int modifyToVideoState = call.getModifyToVideoState();

        if (currentVideoState == modifyToVideoState) {

            return;
        }

        AnswerUi ui = getUi();

        if (ui == null) {

            return;
        }
        showAnswerUi(true);
        ui.showTargets(IncomingFragment.TARGET_SET_FOR_VIDEO_ACCEPT_REJECT_REQUEST, modifyToVideoState);
    }

    private boolean isEnabled(int videoState, int mask) {
        return (videoState & mask) == mask;
    }

    @Override
    public void onCallChanged(@NonNull Call call) {
        if (call.getState() != Call.State.INCOMING) {
            boolean isUpgradePending = isVideoUpgradePending(call);
            if (!isUpgradePending) {
                CallList.getInstance().removeCallUpdateListener(mCallId, this);
            }

            final Call incall = CallList.getInstance().getIncomingCall();
            showAnswerUi(incall != null || isUpgradePending);

            mHasTextMessages = false;
        } else if (!mHasTextMessages) {
            final List<String> textMsgs = CallList.getInstance().getTextResponses(call.getId());
            if (textMsgs != null) {
                configureAnswerTargetsForSms(call, textMsgs);
            }
        }else if(call.getState() != Call.State.ACTIVE){
            try {
                mOnInCommingNumber.onDeclineCall();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void onAnswer(int videoState, @NonNull Context context) {
        if (mCallId == null) {
            return;
        }

        if (mOnInCommingNumber != null) {
            mOnInCommingNumber.onAnswareCall();
        }

        if (mCall.getSessionModificationState() == Call.SessionModificationState.RECEIVED_UPGRADE_TO_VIDEO_REQUEST) {
            InCallPresenter.getInstance().acceptUpgradeRequest(videoState, context);
        } else {
            TelecomAdapter.getInstance().answerCall(mCall.getId(), videoState);
        }
    }

    public void onDecline(@NonNull Context context) {

        if (mOnInCommingNumber != null) {
            try {
                mOnInCommingNumber.onDeclineCall();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (mCall.getSessionModificationState() == Call.SessionModificationState.RECEIVED_UPGRADE_TO_VIDEO_REQUEST) {
            InCallPresenter.getInstance().declineUpgradeRequest(context);
        } else {
            TelecomAdapter.getInstance().rejectCall(mCall.getId(), false, null);
        }
    }

    public void onText() {
        if (getUi() != null) {
            TelecomUtil.silenceRinger(getUi().getContext());
            getUi().showMessageDialog();
        }
    }

    public void rejectCallWithMessage(@NonNull String message) {
        TelecomAdapter.getInstance().rejectCall(mCall.getId(), true, message);
        onDismissDialog();
    }

    public void onDismissDialog() {
        InCallPresenter.getInstance().onDismissDialog();
    }

    private void configureAnswerTargetsForSms(Call call, List<String> textMsgs) {
        if (getUi() == null) {
            return;
        }
        mHasTextMessages = textMsgs != null;
        boolean withSms = call.can(android.telecom.Call.Details.CAPABILITY_RESPOND_VIA_TEXT) && mHasTextMessages;


        if (VideoProfile.isBidirectional((call.getVideoState()))) {
            if (withSms) {
                getUi().showTargets(IncomingFragment.TARGET_SET_FOR_VIDEO_WITH_SMS);
                getUi().configureMessageDialog(textMsgs);
            } else {
                getUi().showTargets(IncomingFragment.TARGET_SET_FOR_VIDEO_WITHOUT_SMS);
            }
        } else {
            if (withSms) {
                getUi().showTargets(IncomingFragment.TARGET_SET_FOR_AUDIO_WITH_SMS);
                getUi().configureMessageDialog(textMsgs);
            } else {
                getUi().showTargets(IncomingFragment.TARGET_SET_FOR_AUDIO_WITHOUT_SMS);
            }
        }
    }

    @Override
    public void onIncomingCall(@NonNull InCallPresenter.InCallState oldState, @NonNull InCallPresenter.InCallState newState, @NonNull Call call) {

        Call modifyCall = CallList.getInstance().getVideoUpgradeRequestCall();
        if (modifyCall != null) {
            showAnswerUi(false);
            CallList.getInstance().removeCallUpdateListener(mCallId, this);
            InCallPresenter.getInstance().declineUpgradeRequest(getUi().getContext());
        }
        if (!call.getId().equals(mCallId)) {
            processIncomingCall(call);
            isIncomingCall = true;
        }
    }

    interface AnswerUi extends Ui {
        void onShowAnswerUi(boolean shown);
        void showTargets(int targetSet);
        void showTargets(int targetSet, int videoState);
        void showMessageDialog();
        void configureMessageDialog(List<String> textResponses);
        Context getContext();
    }

    interface OnInCommingNumber{
        void onGetNumber(String msisdn);
        void onAnswareCall();
        void onDeclineCall() throws Exception;
    }

}
