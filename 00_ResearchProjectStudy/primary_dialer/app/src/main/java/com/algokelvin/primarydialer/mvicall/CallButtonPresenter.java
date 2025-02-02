package com.algokelvin.primarydialer.mvicall;

import static com.algokelvin.primarydialer.mvicall.CallButtonFragment.Buttons.BUTTON_ADD_CALL;
import static com.algokelvin.primarydialer.mvicall.CallButtonFragment.Buttons.BUTTON_AUDIO;
import static com.algokelvin.primarydialer.mvicall.CallButtonFragment.Buttons.BUTTON_DIALPAD;
import static com.algokelvin.primarydialer.mvicall.CallButtonFragment.Buttons.BUTTON_HOLD;
import static com.algokelvin.primarydialer.mvicall.CallButtonFragment.Buttons.BUTTON_MERGE;
import static com.algokelvin.primarydialer.mvicall.CallButtonFragment.Buttons.BUTTON_MUTE;
import static com.algokelvin.primarydialer.mvicall.CallButtonFragment.Buttons.BUTTON_PAUSE_VIDEO;
import static com.algokelvin.primarydialer.mvicall.CallButtonFragment.Buttons.BUTTON_SWAP;
import static com.algokelvin.primarydialer.mvicall.CallButtonFragment.Buttons.BUTTON_SWITCH_CAMERA;
import static com.algokelvin.primarydialer.mvicall.CallButtonFragment.Buttons.BUTTON_UPGRADE_TO_VIDEO;

import android.content.Context;
import android.os.Bundle;
import android.telecom.CallAudioState;
import android.telecom.InCallService.VideoCall;
import android.telecom.VideoProfile;

import com.algokelvin.primarydialer.mvicall.AudioModeProvider.AudioModeListener;
import com.algokelvin.primarydialer.mvicall.InCallCameraManager.Listener;
import com.algokelvin.primarydialer.mvicall.base.in_call.InCallPresenter;
import com.algokelvin.primarydialer.mvicall.base.in_call.InCallPresenter.CanAddCallListener;
import com.algokelvin.primarydialer.mvicall.base.in_call.InCallPresenter.InCallDetailsListener;
import com.algokelvin.primarydialer.mvicall.base.in_call.InCallPresenter.InCallState;
import com.algokelvin.primarydialer.mvicall.base.in_call.InCallPresenter.InCallStateListener;
import com.algokelvin.primarydialer.mvicall.base.in_call.InCallPresenter.IncomingCallListener;

public class CallButtonPresenter extends Presenter<CallButtonPresenter.CallButtonUi> implements InCallStateListener, AudioModeListener, IncomingCallListener, InCallDetailsListener, CanAddCallListener, Listener {

    private static final String KEY_AUTOMATICALLY_MUTED = "incall_key_automatically_muted";
    private static final String KEY_PREVIOUS_MUTE_STATE = "incall_key_previous_mute_state";

    private Call mCall;
    private boolean mAutomaticallyMuted = false;
    private boolean mPreviousMuteState = false;

    public CallButtonPresenter() {
    }

    @Override
    public void onUiReady(CallButtonUi ui) {
        super.onUiReady(ui);

        AudioModeProvider.getInstance().addListener(this);
        final InCallPresenter inCallPresenter = InCallPresenter.getInstance();
        inCallPresenter.addListener(this);
        inCallPresenter.addIncomingCallListener(this);
        inCallPresenter.addDetailsListener(this);
        inCallPresenter.addCanAddCallListener(this);
        inCallPresenter.getInCallCameraManager().addCameraSelectionListener(this);
        onStateChange(InCallState.NO_CALLS, inCallPresenter.getInCallState(), CallList.getInstance());
    }

    @Override
    public void onUiUnready(CallButtonUi ui) {
        super.onUiUnready(ui);

        InCallPresenter.getInstance().removeListener(this);
        AudioModeProvider.getInstance().removeListener(this);
        InCallPresenter.getInstance().removeIncomingCallListener(this);
        InCallPresenter.getInstance().removeDetailsListener(this);
        InCallPresenter.getInstance().getInCallCameraManager().removeCameraSelectionListener(this);
        InCallPresenter.getInstance().removeCanAddCallListener(this);
    }

    @Override
    public void onStateChange(InCallState oldState, InCallState newState, CallList callList) {
        CallButtonUi ui = getUi();

        if (newState == InCallState.OUTGOING) {
            mCall = callList.getOutgoingCall();
        } else if (newState == InCallState.INCALL) {
            mCall = callList.getActiveOrBackgroundCall();

            if (ui != null) {
                if (oldState == InCallState.OUTGOING && mCall != null) {
                    if (CallerInfoUtils.isVoiceMailNumber(ui.getContext(), mCall)) {
                        ui.displayDialpad(true /* show */, true /* animate */);
                    }
                }
            }
        } else if (newState == InCallState.INCOMING) {
            if (ui != null) {
                ui.displayDialpad(false /* show */, true /* animate */);
            }
            mCall = callList.getIncomingCall();
        } else {
            mCall = null;
        }
        updateUi(newState, mCall);
    }

    @Override
    public void onDetailsChanged(Call call, android.telecom.Call.Details details) {
        if (getUi() != null && call != null && call.equals(mCall)) {
            updateButtonsState(call);
        }
    }

    @Override
    public void onIncomingCall(InCallState oldState, InCallState newState, Call call) {
        onStateChange(oldState, newState, CallList.getInstance());
    }

    @Override
    public void onCanAddCallChanged(boolean canAddCall) {
        if (getUi() != null && mCall != null) {
            updateButtonsState(mCall);
        }
    }

    @Override
    public void onAudioMode(int mode) {
        if (getUi() != null) {
            getUi().setAudio(mode);
        }
    }

    @Override
    public void onSupportedAudioMode(int mask) {
        if (getUi() != null) {
            getUi().setSupportedAudio(mask);
        }
    }

    @Override
    public void onMute(boolean muted) {
        if (getUi() != null && !mAutomaticallyMuted) {
            getUi().setMute(muted);
        }
    }

    public int getAudioMode() {
        return AudioModeProvider.getInstance().getAudioMode();
    }

    public int getSupportedAudio() {
        return AudioModeProvider.getInstance().getSupportedModes();
    }

    public void setAudioMode(int mode) {


        TelecomAdapter.getInstance().setAudioRoute(mode);
    }

    public void toggleSpeakerphone() {
        if (0 != (CallAudioState.ROUTE_BLUETOOTH & getSupportedAudio())) {

            getUi().setSupportedAudio(getSupportedAudio());
            return;
        }

        int newMode = CallAudioState.ROUTE_SPEAKER;

        if (getAudioMode() == CallAudioState.ROUTE_SPEAKER) {
            newMode = CallAudioState.ROUTE_WIRED_OR_EARPIECE;
        }

        setAudioMode(newMode);
    }

    public void muteClicked(boolean checked) {
        TelecomAdapter.getInstance().mute(checked);
    }

    public void holdClicked(boolean checked) {
        if (mCall == null) {
            return;
        }
        if (checked) {
            TelecomAdapter.getInstance().holdCall(mCall.getId());
        } else {
            TelecomAdapter.getInstance().unholdCall(mCall.getId());
        }
    }

    public void swapClicked() {
        if (mCall == null) {
            return;
        }

        TelecomAdapter.getInstance().swap(mCall.getId());
    }

    public void mergeClicked() {
        TelecomAdapter.getInstance().merge(mCall.getId());
    }

    public void addCallClicked() {
        mAutomaticallyMuted = true;
        mPreviousMuteState = AudioModeProvider.getInstance().getMute();
        muteClicked(true);
        TelecomAdapter.getInstance().addCall();
    }

    public void changeToVoiceClicked() {
        VideoCall videoCall = mCall.getVideoCall();
        if (videoCall == null) {
            return;
        }

        VideoProfile videoProfile = new VideoProfile(VideoProfile.STATE_AUDIO_ONLY, VideoProfile.QUALITY_DEFAULT);
        videoCall.sendSessionModifyRequest(videoProfile);
    }

    public void showDialpadClicked(boolean checked) {
        getUi().displayDialpad(checked /* show */, true /* animate */);
    }

    public void changeToVideoClicked() {
        VideoCall videoCall = mCall.getVideoCall();
        if (videoCall == null) {
            return;
        }
        int currVideoState = mCall.getVideoState();
        int currUnpausedVideoState = CallUtils.getUnPausedVideoState(currVideoState);
        currUnpausedVideoState |= VideoProfile.STATE_BIDIRECTIONAL;

        VideoProfile videoProfile = new VideoProfile(currUnpausedVideoState);
        videoCall.sendSessionModifyRequest(videoProfile);
        mCall.setSessionModificationState(Call.SessionModificationState.WAITING_FOR_RESPONSE);
    }

    public void switchCameraClicked(boolean useFrontFacingCamera) {
        InCallCameraManager cameraManager = InCallPresenter.getInstance().getInCallCameraManager();
        cameraManager.setUseFrontFacingCamera(useFrontFacingCamera);

        VideoCall videoCall = mCall.getVideoCall();
        if (videoCall == null) {
            return;
        }

        String cameraId = cameraManager.getActiveCameraId();
        if (cameraId != null) {
            final int cameraDir = cameraManager.isUsingFrontFacingCamera() ? Call.VideoSettings.CAMERA_DIRECTION_FRONT_FACING : Call.VideoSettings.CAMERA_DIRECTION_BACK_FACING;
            mCall.getVideoSettings().setCameraDir(cameraDir);
            videoCall.setCamera(cameraId);
            videoCall.requestCameraCapabilities();
        }
    }

    public void pauseVideoClicked(boolean pause) {
        VideoCall videoCall = mCall.getVideoCall();
        if (videoCall == null) {
            return;
        }

        if (pause) {
            videoCall.setCamera(null);
            VideoProfile videoProfile = new VideoProfile(mCall.getVideoState() & ~VideoProfile.STATE_TX_ENABLED);
            videoCall.sendSessionModifyRequest(videoProfile);
        } else {
            InCallCameraManager cameraManager = InCallPresenter.getInstance().
                    getInCallCameraManager();
            videoCall.setCamera(cameraManager.getActiveCameraId());
            VideoProfile videoProfile = new VideoProfile(mCall.getVideoState() | VideoProfile.STATE_TX_ENABLED);
            videoCall.sendSessionModifyRequest(videoProfile);
            mCall.setSessionModificationState(Call.SessionModificationState.WAITING_FOR_RESPONSE);
        }
        getUi().setVideoPaused(pause);
    }

    private void updateUi(InCallState state, Call call) {

        final CallButtonUi ui = getUi();
        if (ui == null) {
            return;
        }

        final boolean isEnabled = state.isConnectingOrConnected() && !state.isIncoming() && call != null;
        ui.setEnabled(isEnabled);

        if (call == null) {
            return;
        }

        updateButtonsState(call);
    }

    private void updateButtonsState(Call call) {
        final CallButtonUi ui = getUi();

        final boolean isVideo = CallUtils.isVideoCall(call);

        final boolean showSwap = call.can(android.telecom.Call.Details.CAPABILITY_SWAP_CONFERENCE);
        final boolean showHold = !showSwap && call.can(android.telecom.Call.Details.CAPABILITY_SUPPORT_HOLD) && call.can(android.telecom.Call.Details.CAPABILITY_HOLD);
        final boolean isCallOnHold = call.getState() == Call.State.ONHOLD;

        final boolean showAddCall = TelecomAdapter.getInstance().canAddCall();
        final boolean showMerge = call.can(android.telecom.Call.Details.CAPABILITY_MERGE_CONFERENCE);
        final boolean showUpgradeToVideo = !isVideo && (call.can(android.telecom.Call.Details.CAPABILITY_SUPPORTS_VT_LOCAL_TX) && call.can(android.telecom.Call.Details.CAPABILITY_SUPPORTS_VT_REMOTE_RX));

        final boolean showMute = call.can(android.telecom.Call.Details.CAPABILITY_MUTE);

        ui.showButton(BUTTON_AUDIO, true);
        ui.showButton(BUTTON_SWAP, showSwap);
        ui.showButton(BUTTON_HOLD, showHold);
        ui.setHold(isCallOnHold);
        ui.showButton(BUTTON_MUTE, showMute);
        ui.showButton(BUTTON_ADD_CALL, showAddCall);
        ui.showButton(BUTTON_UPGRADE_TO_VIDEO, showUpgradeToVideo);
        ui.showButton(BUTTON_SWITCH_CAMERA, isVideo);
        ui.showButton(BUTTON_PAUSE_VIDEO, isVideo);
        ui.showButton(BUTTON_DIALPAD, !isVideo);
        ui.showButton(BUTTON_MERGE, showMerge);

        ui.updateButtonStates();
    }

    public void refreshMuteState() {
        if (mAutomaticallyMuted && AudioModeProvider.getInstance().getMute() != mPreviousMuteState) {
            if (getUi() == null) {
                return;
            }
            muteClicked(mPreviousMuteState);
        }
        mAutomaticallyMuted = false;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_AUTOMATICALLY_MUTED, mAutomaticallyMuted);
        outState.putBoolean(KEY_PREVIOUS_MUTE_STATE, mPreviousMuteState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        mAutomaticallyMuted = savedInstanceState.getBoolean(KEY_AUTOMATICALLY_MUTED, mAutomaticallyMuted);
        mPreviousMuteState = savedInstanceState.getBoolean(KEY_PREVIOUS_MUTE_STATE, mPreviousMuteState);
        super.onRestoreInstanceState(savedInstanceState);
    }

    public interface CallButtonUi extends Ui {
        void showButton(int buttonId, boolean show);

        void enableButton(int buttonId, boolean enable);

        void setEnabled(boolean on);

        void setMute(boolean on);

        void setHold(boolean on);

        void setCameraSwitched(boolean isBackFacingCamera);

        void setVideoPaused(boolean isPaused);

        void setAudio(int mode);

        void setSupportedAudio(int mask);

        void displayDialpad(boolean on, boolean animate);

        boolean isDialpadVisible();

        void updateButtonStates();

        Context getContext();
    }

    @Override
    public void onActiveCameraSelectionChanged(boolean isUsingFrontFacingCamera) {
        if (getUi() == null) {
            return;
        }
        getUi().setCameraSwitched(!isUsingFrontFacingCamera);
    }
}
