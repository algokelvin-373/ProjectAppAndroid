package com.algokelvin.primarydialer.mvicall;

import android.telecom.VideoProfile;

import com.google.common.base.Preconditions;

public class CallUtils {

    public static boolean isVideoCall(Call call) {
        return call != null && isVideoCall(call.getVideoState());
    }

    public static boolean isVideoCall(int videoState) {
        return VideoProfile.isTransmissionEnabled(videoState)
                || VideoProfile.isReceptionEnabled(videoState);
    }

    public static boolean isIncomingVideoCall(Call call) {
        if (!CallUtils.isVideoCall(call)) {
            return false;
        }
        final int state = call.getState();
        return (state == Call.State.INCOMING) || (state == Call.State.CALL_WAITING);
    }

    public static boolean isActiveVideoCall(Call call) {
        return CallUtils.isVideoCall(call) && call.getState() == Call.State.ACTIVE;
    }

    public static boolean isOutgoingVideoCall(Call call) {
        if (!CallUtils.isVideoCall(call)) {
            return false;
        }
        final int state = call.getState();
        return Call.State.isDialing(state) || state == Call.State.CONNECTING
                || state == Call.State.SELECT_PHONE_ACCOUNT;
    }

    public static boolean isAudioCall(Call call) {
        return call != null && VideoProfile.isAudioOnly(call.getVideoState());
    }

    // TODO (ims-vt) Check if special handling is needed for CONF calls.
    public static boolean canVideoPause(Call call) {
        return isVideoCall(call) && call.getState() == Call.State.ACTIVE;
    }

    public static VideoProfile makeVideoPauseProfile(Call call) {
        Preconditions.checkNotNull(call);
        Preconditions.checkState(!VideoProfile.isAudioOnly(call.getVideoState()));
        return new VideoProfile(getPausedVideoState(call.getVideoState()));
    }

    public static VideoProfile makeVideoUnPauseProfile(Call call) {
        Preconditions.checkNotNull(call);
        return new VideoProfile(getUnPausedVideoState(call.getVideoState()));
    }

    public static int getUnPausedVideoState(int videoState) {
        return videoState & (~VideoProfile.STATE_PAUSED);
    }

    public static int getPausedVideoState(int videoState) {
        return videoState | VideoProfile.STATE_PAUSED;
    }

}
