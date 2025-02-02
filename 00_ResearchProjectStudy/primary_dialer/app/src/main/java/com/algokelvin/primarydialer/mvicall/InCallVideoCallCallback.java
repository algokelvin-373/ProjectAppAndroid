package com.algokelvin.primarydialer.mvicall;

import android.telecom.Connection.VideoProvider;
import android.telecom.InCallService.VideoCall;
import android.telecom.VideoProfile;
import android.telecom.VideoProfile.CameraCapabilities;
import android.util.Log;

/**
 * Implements the InCallUI VideoCall Callback.
 */
public class InCallVideoCallCallback extends VideoCall.Callback {
    private final String TAG = "InCallVideoCallCallback";

    /**
     * The call associated with this {@link InCallVideoCallCallback}.
     */
    private final Call mCall;

    /**
     * Creates an instance of the call video client, specifying the call it is related to.
     *
     * @param call The call.
     */
    public InCallVideoCallCallback(Call call) {
        mCall = call;
    }

    /**
     * Handles an incoming session modification request.
     *
     * @param videoProfile The requested video call profile.
     */
    @Override
    public void onSessionModifyRequestReceived(VideoProfile videoProfile) {
        Log.d(TAG, " onSessionModifyRequestReceived videoProfile=" + videoProfile);
        int previousVideoState = CallUtils.getUnPausedVideoState(mCall.getVideoState());
        int newVideoState = CallUtils.getUnPausedVideoState(videoProfile.getVideoState());

        boolean wasVideoCall = CallUtils.isVideoCall(previousVideoState);
        boolean isVideoCall = CallUtils.isVideoCall(newVideoState);

        // Check for upgrades to video and downgrades to audio.
        if (wasVideoCall && !isVideoCall) {
            InCallVideoCallCallbackNotifier.getInstance().downgradeToAudio(mCall);
        } else if (previousVideoState != newVideoState) {
            InCallVideoCallCallbackNotifier.getInstance().upgradeToVideoRequest(mCall,
                newVideoState);
        }
    }

    /**
     * Handles a session modification response.
     *
     * @param status Status of the session modify request. Valid values are
     *            {@link VideoProvider#SESSION_MODIFY_REQUEST_SUCCESS},
     *            {@link VideoProvider#SESSION_MODIFY_REQUEST_FAIL},
     *            {@link VideoProvider#SESSION_MODIFY_REQUEST_INVALID}
     * @param requestedProfile
     * @param responseProfile The actual profile changes made by the peer device.
     */
    @Override
    public void onSessionModifyResponseReceived(int status, VideoProfile requestedProfile,
            VideoProfile responseProfile) {
        Log.d(TAG, "onSessionModifyResponseReceived status=" + status + " requestedProfile="
                + requestedProfile + " responseProfile=" + responseProfile);
        if (status != VideoProvider.SESSION_MODIFY_REQUEST_SUCCESS) {
            // Report the reason the upgrade failed as the new session modification state.
            if (status == VideoProvider.SESSION_MODIFY_REQUEST_TIMED_OUT) {
                mCall.setSessionModificationState(
                        Call.SessionModificationState.UPGRADE_TO_VIDEO_REQUEST_TIMED_OUT);
            } else {
                if (status == VideoProvider.SESSION_MODIFY_REQUEST_REJECTED_BY_REMOTE) {
                    mCall.setSessionModificationState(
                            Call.SessionModificationState.REQUEST_REJECTED);
                } else {
                    mCall.setSessionModificationState(
                            Call.SessionModificationState.REQUEST_FAILED);
                }
            }
            InCallVideoCallCallbackNotifier.getInstance().upgradeToVideoFail(status, mCall);
        } else if (requestedProfile != null && responseProfile != null) {
            boolean modifySucceeded = requestedProfile.getVideoState() ==
                    responseProfile.getVideoState();
            boolean isVideoCall = CallUtils.isVideoCall(responseProfile.getVideoState());
            if (modifySucceeded && isVideoCall) {
                InCallVideoCallCallbackNotifier.getInstance().upgradeToVideoSuccess(mCall);
            } else if (!modifySucceeded && isVideoCall) {
                InCallVideoCallCallbackNotifier.getInstance().upgradeToVideoFail(status, mCall);
            } else if (modifySucceeded && !isVideoCall) {
                InCallVideoCallCallbackNotifier.getInstance().downgradeToAudio(mCall);
            }
        } else {
            Log.d(TAG, "onSessionModifyResponseReceived request and response Profiles are null");
        }
        // Finally clear the outstanding request.
        mCall.setSessionModificationState(Call.SessionModificationState.NO_REQUEST);
    }

    /**
     * Handles a call session event.
     *
     * @param event The event.
     */
    @Override
    public void onCallSessionEvent(int event) {
        InCallVideoCallCallbackNotifier.getInstance().callSessionEvent(event);
    }

    /**
     * Handles a change to the peer video dimensions.
     *
     * @param width  The updated peer video width.
     * @param height The updated peer video height.
     */
    @Override
    public void onPeerDimensionsChanged(int width, int height) {
        InCallVideoCallCallbackNotifier.getInstance().peerDimensionsChanged(mCall, width, height);
    }

    /**
     * Handles a change to the video quality of the call.
     *
     * @param videoQuality The updated video call quality.
     */
    @Override
    public void onVideoQualityChanged(int videoQuality) {
        InCallVideoCallCallbackNotifier.getInstance().videoQualityChanged(mCall, videoQuality);
    }

    /**
     * Handles a change to the call data usage.  No implementation as the in-call UI does not
     * display data usage.
     *
     * @param dataUsage The updated data usage.
     */
    @Override
    public void onCallDataUsageChanged(long dataUsage) {
        Log.d("onCallDataUsageChanged", "onCallDataUsageChanged: dataUsage = " + dataUsage);
        InCallVideoCallCallbackNotifier.getInstance().callDataUsageChanged(dataUsage);
    }

    /**
     * Handles changes to the camera capabilities.  No implementation as the in-call UI does not
     * make use of camera capabilities.
     *
     * @param cameraCapabilities The changed camera capabilities.
     */
    @Override
    public void onCameraCapabilitiesChanged(CameraCapabilities cameraCapabilities) {
        if (cameraCapabilities != null) {
            InCallVideoCallCallbackNotifier.getInstance().cameraDimensionsChanged(
                    mCall, cameraCapabilities.getWidth(), cameraCapabilities.getHeight());
        }
    }
}
