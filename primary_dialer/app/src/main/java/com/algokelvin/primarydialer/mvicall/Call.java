package com.algokelvin.primarydialer.mvicall;

import android.content.Context;
import android.hardware.camera2.CameraCharacteristics;
import android.net.Uri;
import android.os.Bundle;
import android.os.Trace;
import android.telecom.Connection;
import android.telecom.DisconnectCause;
import android.telecom.GatewayInfo;
import android.telecom.InCallService.VideoCall;
import android.telecom.PhoneAccount;
import android.telecom.PhoneAccountHandle;
import android.telecom.TelecomManager;
import android.telecom.VideoProfile;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;

import com.algokelvin.primarydialer.mvicall.base.in_call.InCallPresenter;
import com.algokelvin.primarydialer.mvicall.common.CallUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class Call {

    public static class State {
        public static final int INVALID = 0;
        public static final int NEW = 1;            /* The call is new. */
        public static final int IDLE = 2;           /* The call is idle.  Nothing active */
        public static final int ACTIVE = 3;         /* There is an active call */
        public static final int INCOMING = 4;       /* A normal incoming phone call */
        public static final int CALL_WAITING = 5;   /* Incoming call while another is active */
        public static final int DIALING = 6;        /* An outgoing call during dial phase */
        public static final int REDIALING = 7;      /* Subsequent dialing attempt after a failure */
        public static final int ONHOLD = 8;         /* An active phone call placed on hold */
        public static final int DISCONNECTING = 9;  /* A call is being ended. */
        public static final int DISCONNECTED = 10;  /* State after a call disconnects */
        public static final int CONFERENCED = 11;   /* Call part of a conference call */
        public static final int SELECT_PHONE_ACCOUNT = 12; /* Waiting for account selection */
        public static final int CONNECTING = 13;    /* Waiting for Telecomm broadcast to finish */


        public static boolean isConnectingOrConnected(int state) {
            switch (state) {
                case ACTIVE:
                case INCOMING:
                case CALL_WAITING:
                case CONNECTING:
                case DIALING:
                case REDIALING:
                case ONHOLD:
                case CONFERENCED:
                    return true;
                default:
            }
            return false;
        }

        public static boolean isDialing(int state) {
            return state == DIALING || state == REDIALING;
        }

        public static String toString(int state) {
            switch (state) {
                case INVALID:
                    return "INVALID";
                case NEW:
                    return "NEW";
                case IDLE:
                    return "IDLE";
                case ACTIVE:
                    return "ACTIVE";
                case INCOMING:
                    return "INCOMING";
                case CALL_WAITING:
                    return "CALL_WAITING";
                case DIALING:
                    return "DIALING";
                case REDIALING:
                    return "REDIALING";
                case ONHOLD:
                    return "ONHOLD";
                case DISCONNECTING:
                    return "DISCONNECTING";
                case DISCONNECTED:
                    return "DISCONNECTED";
                case CONFERENCED:
                    return "CONFERENCED";
                case SELECT_PHONE_ACCOUNT:
                    return "SELECT_PHONE_ACCOUNT";
                case CONNECTING:
                    return "CONNECTING";
                default:
                    return "UNKNOWN";
            }
        }
    }

    public static class SessionModificationState {
        public static final int NO_REQUEST = 0;
        public static final int WAITING_FOR_RESPONSE = 1;
        public static final int REQUEST_FAILED = 2;
        public static final int RECEIVED_UPGRADE_TO_VIDEO_REQUEST = 3;
        public static final int UPGRADE_TO_VIDEO_REQUEST_TIMED_OUT = 4;
        public static final int REQUEST_REJECTED = 5;
    }

    public static class VideoSettings {
        public static final int CAMERA_DIRECTION_UNKNOWN = -1;
        public static final int CAMERA_DIRECTION_FRONT_FACING = CameraCharacteristics.LENS_FACING_FRONT;
        public static final int CAMERA_DIRECTION_BACK_FACING = CameraCharacteristics.LENS_FACING_BACK;

        private int mCameraDirection = CAMERA_DIRECTION_UNKNOWN;

        public void setCameraDir(int cameraDirection) {
            if (cameraDirection == CAMERA_DIRECTION_FRONT_FACING || cameraDirection == CAMERA_DIRECTION_BACK_FACING) {
                mCameraDirection = cameraDirection;
            } else {
                mCameraDirection = CAMERA_DIRECTION_UNKNOWN;
            }
        }

        public int getCameraDir() {
            return mCameraDirection;
        }

        public String toString() {
            return "(CameraDir:" + getCameraDir() + ")";
        }
    }

    private static final String ID_PREFIX = Call.class.getSimpleName() + "_";
    private static int sIdCounter = 0;

    private final android.telecom.Call.Callback mTelecomCallCallback = new android.telecom.Call.Callback() {
        @Override
        public void onStateChanged(android.telecom.Call call, int newState) {
            update();
        }

        @Override
        public void onParentChanged(android.telecom.Call call, android.telecom.Call newParent) {
            update();
        }

        @Override
        public void onChildrenChanged(android.telecom.Call call, List<android.telecom.Call> children) {
            update();
        }

        @Override
        public void onDetailsChanged(android.telecom.Call call, android.telecom.Call.Details details) {
            update();
        }

        @Override
        public void onCannedTextResponsesLoaded(android.telecom.Call call, List<String> cannedTextResponses) {
            update();
        }

        @Override
        public void onPostDialWait(android.telecom.Call call, String remainingPostDialSequence) {
            update();
        }

        @Override
        public void onVideoCallChanged(android.telecom.Call call, VideoCall videoCall) {
            update();
        }

        @Override
        public void onCallDestroyed(android.telecom.Call call) {
            call.unregisterCallback(mTelecomCallCallback);
        }

        @Override
        public void onConferenceableCallsChanged(android.telecom.Call call, List<android.telecom.Call> conferenceableCalls) {
            update();
        }
    };

    private final android.telecom.Call mTelecommCall;
    private boolean mIsEmergencyCall;
    private Uri mHandle;
    private final String mId;
    private int mState = State.INVALID;
    private DisconnectCause mDisconnectCause;
    private int mSessionModificationState;
    private final List<String> mChildCallIds = new ArrayList<>();
    private final VideoSettings mVideoSettings = new VideoSettings();
    private int mModifyToVideoState = VideoProfile.STATE_AUDIO_ONLY;

    private InCallVideoCallCallback mVideoCallCallback;
    private String mChildNumber;
    private String mLastForwardedNumber;
    private String mCallSubject;
    private PhoneAccountHandle mPhoneAccountHandle;
    private boolean mIsCallSubjectSupported;

    Call(int state) {
        mTelecommCall = null;
        mId = ID_PREFIX + sIdCounter++;
        setState(state);
    }

    public Call(android.telecom.Call telecommCall) {
        mTelecommCall = telecommCall;
        mId = ID_PREFIX + sIdCounter++;

        updateFromTelecommCall();
        mTelecommCall.registerCallback(mTelecomCallCallback);
    }

    public android.telecom.Call getTelecommCall() {
        return mTelecommCall;
    }

    public VideoSettings getVideoSettings() {
        return mVideoSettings;
    }

    private void update() {
        Trace.beginSection("Update");
        int oldState = getState();
        updateFromTelecommCall();
        if (oldState != getState() && getState() == State.DISCONNECTED) {
            CallList.getInstance().onDisconnect(this);
        } else {
            CallList.getInstance().onUpdate(this);
        }
        Trace.endSection();
    }

    private void updateFromTelecommCall() {

        setState(translateState(mTelecommCall.getState()));
        setDisconnectCause(mTelecommCall.getDetails().getDisconnectCause());

        if (mTelecommCall.getVideoCall() != null) {
            if (mVideoCallCallback == null) {
                mVideoCallCallback = new InCallVideoCallCallback(this);
            }
            mTelecommCall.getVideoCall().registerCallback(mVideoCallCallback);
        }

        mChildCallIds.clear();
        for (int i = 0; i < mTelecommCall.getChildren().size(); i++) {
            mChildCallIds.add(CallList.getInstance().getCallByTelecommCall(mTelecommCall.getChildren().get(i)).getId());
        }

        updateFromCallExtras(mTelecommCall.getDetails().getExtras());

        Uri newHandle = mTelecommCall.getDetails().getHandle();
        if (!Objects.equals(mHandle, newHandle)) {
            mHandle = newHandle;
            updateEmergencyCallState();
        }

        PhoneAccountHandle newPhoneAccountHandle = mTelecommCall.getDetails().getAccountHandle();
        if (!Objects.equals(mPhoneAccountHandle, newPhoneAccountHandle)) {
            mPhoneAccountHandle = newPhoneAccountHandle;

            if (mPhoneAccountHandle != null) {
                TelecomManager mgr = InCallPresenter.getInstance().getTelecomManager();
                PhoneAccount phoneAccount = mgr.getPhoneAccount(mPhoneAccountHandle);
                if (phoneAccount != null) {
                    mIsCallSubjectSupported = phoneAccount.hasCapabilities(PhoneAccount.CAPABILITY_CALL_SUBJECT);
                }
            }
        }
    }


    protected boolean areCallExtrasCorrupted(Bundle callExtras) {
        try {
            callExtras.containsKey(Connection.EXTRA_CHILD_ADDRESS);
            return false;
        } catch (IllegalArgumentException e) {

            return true;
        }
    }

    protected void updateFromCallExtras(Bundle callExtras) {
        if (callExtras == null || areCallExtrasCorrupted(callExtras)) {
            return;
        }
        if (callExtras.containsKey(Connection.EXTRA_CHILD_ADDRESS)) {
            String childNumber = callExtras.getString(Connection.EXTRA_CHILD_ADDRESS);
            if (!Objects.equals(childNumber, mChildNumber)) {
                mChildNumber = childNumber;
                CallList.getInstance().onChildNumberChange(this);
            }
        }

        if (callExtras.containsKey(Connection.EXTRA_LAST_FORWARDED_NUMBER)) {
            ArrayList<String> lastForwardedNumbers = callExtras.getStringArrayList(Connection.EXTRA_LAST_FORWARDED_NUMBER);

            if (lastForwardedNumbers != null) {
                String lastForwardedNumber = null;
                if (!lastForwardedNumbers.isEmpty()) {
                    lastForwardedNumber = lastForwardedNumbers.get(lastForwardedNumbers.size() - 1);
                }

                if (!Objects.equals(lastForwardedNumber, mLastForwardedNumber)) {
                    mLastForwardedNumber = lastForwardedNumber;
                    CallList.getInstance().onLastForwardedNumberChange(this);
                }
            }
        }

        if (callExtras.containsKey(Connection.EXTRA_CALL_SUBJECT)) {
            String callSubject = callExtras.getString(Connection.EXTRA_CALL_SUBJECT);
            if (!Objects.equals(mCallSubject, callSubject)) {
                mCallSubject = callSubject;
            }
        }
    }

    private static int translateState(int state) {
        switch (state) {
            case android.telecom.Call.STATE_NEW:
            case android.telecom.Call.STATE_CONNECTING:
                return State.CONNECTING;
            case android.telecom.Call.STATE_SELECT_PHONE_ACCOUNT:
                return State.SELECT_PHONE_ACCOUNT;
            case android.telecom.Call.STATE_DIALING:
                return State.DIALING;
            case android.telecom.Call.STATE_RINGING:
                return State.INCOMING;
            case android.telecom.Call.STATE_ACTIVE:
                return State.ACTIVE;
            case android.telecom.Call.STATE_HOLDING:
                return State.ONHOLD;
            case android.telecom.Call.STATE_DISCONNECTED:
                return State.DISCONNECTED;
            case android.telecom.Call.STATE_DISCONNECTING:
                return State.DISCONNECTING;
            default:
                return State.INVALID;
        }
    }

    public String getId() {
        return mId;
    }

    public String getNumber() {
        if (mTelecommCall == null) {
            return null;
        }
        if (mTelecommCall.getDetails().getGatewayInfo() != null) {
            return mTelecommCall.getDetails().getGatewayInfo().getOriginalAddress().getSchemeSpecificPart();
        }
        return getHandle() == null ? null : getHandle().getSchemeSpecificPart();
    }

    public Uri getHandle() {
        return mTelecommCall == null ? null : mTelecommCall.getDetails().getHandle();
    }

    public boolean isEmergencyCall() {
        return mIsEmergencyCall;
    }

    public int getState() {
        if (mTelecommCall != null && mTelecommCall.getParent() != null) {
            return State.CONFERENCED;
        } else {
            return mState;
        }
    }

    public void setState(int state) {
        mState = state;
    }

    public int getNumberPresentation() {
        return mTelecommCall == null ? null : mTelecommCall.getDetails().getHandlePresentation();
    }

    public int getCnapNamePresentation() {
        return mTelecommCall == null ? null : mTelecommCall.getDetails().getCallerDisplayNamePresentation();
    }

    public String getCnapName() {
        return mTelecommCall == null ? null : getTelecommCall().getDetails().getCallerDisplayName();
    }

    public Bundle getIntentExtras() {
        return mTelecommCall == null ? null : mTelecommCall.getDetails().getIntentExtras();
    }

    public Bundle getExtras() {
        return mTelecommCall == null ? null : mTelecommCall.getDetails().getExtras();
    }

    public String getChildNumber() {
        return mChildNumber;
    }

    public String getLastForwardedNumber() {
        return mLastForwardedNumber;
    }

    public String getCallSubject() {
        return mCallSubject;
    }

    public boolean isCallSubjectSupported() {
        return mIsCallSubjectSupported;
    }

    public DisconnectCause getDisconnectCause() {
        if (mState == State.DISCONNECTED || mState == State.IDLE) {
            return mDisconnectCause;
        }

        return new DisconnectCause(DisconnectCause.UNKNOWN);
    }

    public void setDisconnectCause(DisconnectCause disconnectCause) {
        mDisconnectCause = disconnectCause;
    }

    public List<String> getCannedSmsResponses() {
        return mTelecommCall.getCannedTextResponses();
    }

    public boolean can(int capabilities) {
        int supportedCapabilities = mTelecommCall.getDetails().getCallCapabilities();

        if ((capabilities & android.telecom.Call.Details.CAPABILITY_MERGE_CONFERENCE) != 0) {
            if (mTelecommCall.getConferenceableCalls().isEmpty() && ((android.telecom.Call.Details.CAPABILITY_MERGE_CONFERENCE & supportedCapabilities) == 0)) {
                return false;
            }
            capabilities &= ~android.telecom.Call.Details.CAPABILITY_MERGE_CONFERENCE;
        }
        return (capabilities == (capabilities & mTelecommCall.getDetails().getCallCapabilities()));
    }

    public boolean hasProperty(int property) {
        return mTelecommCall.getDetails().hasProperty(property);
    }

    public long getConnectTimeMillis() {
        return mTelecommCall.getDetails().getConnectTimeMillis();
    }

    public boolean isConferenceCall() {
        return mTelecommCall.getDetails().hasProperty(android.telecom.Call.Details.PROPERTY_CONFERENCE);
    }

    public GatewayInfo getGatewayInfo() {
        return mTelecommCall == null ? null : mTelecommCall.getDetails().getGatewayInfo();
    }

    public PhoneAccountHandle getAccountHandle() {
        return mTelecommCall == null ? null : mTelecommCall.getDetails().getAccountHandle();
    }

    public VideoCall getVideoCall() {
        return mTelecommCall == null ? null : mTelecommCall.getVideoCall();
    }

    public List<String> getChildCallIds() {
        return mChildCallIds;
    }

    public String getParentId() {
        android.telecom.Call parentCall = mTelecommCall.getParent();
        if (parentCall != null) {
            return CallList.getInstance().getCallByTelecommCall(parentCall).getId();
        }
        return null;
    }

    public int getVideoState() {
        return mTelecommCall.getDetails().getVideoState();
    }

    public boolean isVideoCall(Context context) {
        return CallUtil.isVideoEnabled(context) && CallUtils.isVideoCall(getVideoState());
    }

    public void setSessionModificationTo(int videoState) {

        if (videoState == getVideoState()) {
            mSessionModificationState = SessionModificationState.NO_REQUEST;

        } else {
            mSessionModificationState = SessionModificationState.RECEIVED_UPGRADE_TO_VIDEO_REQUEST;
            setModifyToVideoState(videoState);
            CallList.getInstance().onUpgradeToVideo(this);
        }

        update();
    }

    public void setSessionModificationState(int state) {
        if (state == SessionModificationState.RECEIVED_UPGRADE_TO_VIDEO_REQUEST) {
            return;
        }

        boolean hasChanged = mSessionModificationState != state;
        mSessionModificationState = state;
        if (hasChanged) {
            CallList.getInstance().onSessionModificationStateChange(this, state);
        }
    }


    private void updateEmergencyCallState() {
        Uri handle = mTelecommCall.getDetails().getHandle();
        mIsEmergencyCall = PhoneNumberUtils.isEmergencyNumber(handle == null ? "" : handle.getSchemeSpecificPart());
    }

    private void setModifyToVideoState(int newVideoState) {
        mModifyToVideoState = newVideoState;
    }

    public int getModifyToVideoState() {
        return mModifyToVideoState;
    }

    public static boolean areSame(Call call1, Call call2) {
        if (call1 == null && call2 == null) {
            return true;
        } else if (call1 == null || call2 == null) {
            return false;
        }
        return call1.getId().equals(call2.getId());
    }

    public static boolean areSameNumber(Call call1, Call call2) {
        if (call1 == null && call2 == null) {
            return true;
        } else if (call1 == null || call2 == null) {
            return false;
        }

        return TextUtils.equals(call1.getNumber(), call2.getNumber());
    }

    public int getSessionModificationState() {
        return mSessionModificationState;
    }

    @Override
    public String toString() {
        if (mTelecommCall == null) {
            return String.valueOf(mId);
        }

        return String.format(Locale.US, "[%s, %s, %s, children:%s, parent:%s, conferenceable:%s, " + "videoState:%s, mSessionModificationState:%d, VideoSettings:%s]", mId, State.toString(getState()), android.telecom.Call.Details.capabilitiesToString(mTelecommCall.getDetails().getCallCapabilities()), mChildCallIds, getParentId(), this.mTelecommCall.getConferenceableCalls(), VideoProfile.videoStateToString(mTelecommCall.getDetails().getVideoState()), mSessionModificationState, getVideoSettings());
    }

    public String toSimpleString() {
        return super.toString();
    }
}
