package com.sbi.mvicalllibrary.icallservices;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Point;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.provider.ContactsContract;
import android.telecom.CallAudioState;
import android.telecom.Connection;
import android.telecom.InCallService.VideoCall;
import android.telecom.VideoProfile;
import android.view.Surface;
import android.widget.ImageView;

import com.sbi.mvicalllibrary.R;
import com.sbi.mvicalllibrary.icallservices.InCallPresenter.InCallDetailsListener;
import com.sbi.mvicalllibrary.icallservices.InCallPresenter.InCallOrientationListener;
import com.sbi.mvicalllibrary.icallservices.InCallPresenter.InCallStateListener;
import com.sbi.mvicalllibrary.icallservices.InCallPresenter.IncomingCallListener;
import com.sbi.mvicalllibrary.icallservices.InCallVideoCallCallbackNotifier.SurfaceChangeListener;
import com.sbi.mvicalllibrary.icallservices.InCallVideoCallCallbackNotifier.VideoEventListener;
import com.sbi.mvicalllibrary.icallservices.common.ContactPhotoManager;

import java.util.Objects;

public class VideoCallPresenter extends Presenter<VideoCallPresenter.VideoCallUi> implements IncomingCallListener, InCallOrientationListener, InCallStateListener, InCallDetailsListener, SurfaceChangeListener, VideoEventListener, InCallVideoCallCallbackNotifier.SessionModificationListener, InCallPresenter.InCallEventListener {

    public static final String TAG = "VideoCallPresenter";
    public static final boolean DEBUG = false;

    private final Runnable mAutoFullscreenRunnable = new Runnable() {
        @Override
        public void run() {
            if (mAutoFullScreenPending) {

                InCallPresenter.getInstance().setFullScreen(true);
                mAutoFullScreenPending = false;
            }
        }
    };


    private class PreviewSurfaceState {
        private static final int NONE = 0;
        private static final int CAMERA_SET = 1;
        private static final int CAPABILITIES_RECEIVED = 2;
        private static final int SURFACE_SET = 3;
    }

    private float mMinimumVideoDimension;
    private Context mContext;
    private Call mPrimaryCall;
    private VideoCall mVideoCall;
    private int mCurrentVideoState;
    private int mCurrentCallState = Call.State.INVALID;
    private int mDeviceOrientation;
    private int mPreviewSurfaceState = PreviewSurfaceState.NONE;
    private static int sPrevVideoAudioMode = AudioModeProvider.AUDIO_MODE_INVALID;
    private static boolean mIsVideoMode = false;
    private ContactPhotoManager mContactPhotoManager = null;
    private ContactInfoCache.ContactCacheEntry mProfileInfo = null;
    private Handler mHandler;
    private boolean mIsAutoFullscreenEnabled = false;
    private int mAutoFullscreenTimeoutMillis = 0;
    private boolean mAutoFullScreenPending = false;

    public void init(Context context) {
        mContext = context;
        mMinimumVideoDimension = mContext.getResources().getDimension(R.dimen.video_preview_small_dimension);
        mHandler = new Handler(Looper.getMainLooper());
        mIsAutoFullscreenEnabled = mContext.getResources().getBoolean(R.bool.video_call_auto_fullscreen);
        mAutoFullscreenTimeoutMillis = mContext.getResources().getInteger(R.integer.video_call_auto_fullscreen_timeout);
    }


    @Override
    public void onUiReady(VideoCallUi ui) {
        super.onUiReady(ui);


        InCallPresenter.getInstance().addListener(this);
        InCallPresenter.getInstance().addDetailsListener(this);
        InCallPresenter.getInstance().addIncomingCallListener(this);
        InCallPresenter.getInstance().addOrientationListener(this);
        InCallPresenter.getInstance().addDetailsListener(this);

        InCallVideoCallCallbackNotifier.getInstance().addSurfaceChangeListener(this);
        InCallVideoCallCallbackNotifier.getInstance().addVideoEventListener(this);
        InCallVideoCallCallbackNotifier.getInstance().addSessionModificationListener(this);
        mCurrentVideoState = VideoProfile.STATE_AUDIO_ONLY;
        mCurrentCallState = Call.State.INVALID;
    }


    @Override
    public void onUiUnready(VideoCallUi ui) {
        super.onUiUnready(ui);


        InCallPresenter.getInstance().removeListener(this);
        InCallPresenter.getInstance().removeDetailsListener(this);
        InCallPresenter.getInstance().removeIncomingCallListener(this);
        InCallPresenter.getInstance().removeOrientationListener(this);

        InCallVideoCallCallbackNotifier.getInstance().removeSurfaceChangeListener(this);
        InCallVideoCallCallbackNotifier.getInstance().removeVideoEventListener(this);
        InCallVideoCallCallbackNotifier.getInstance().removeSessionModificationListener(this);
    }


    public void onSurfaceCreated(int surface) {

        final VideoCallUi ui = getUi();
        if (ui == null || mVideoCall == null) {
            return;
        }

        if (surface == VideoCallFragment.SURFACE_PREVIEW) {
            if (mPreviewSurfaceState == PreviewSurfaceState.CAPABILITIES_RECEIVED) {
                mPreviewSurfaceState = PreviewSurfaceState.SURFACE_SET;
                mVideoCall.setPreviewSurface(ui.getPreviewVideoSurface());
            } else if (mPreviewSurfaceState == PreviewSurfaceState.NONE && isCameraRequired()) {
                enableCamera(mVideoCall, true);
            }
        } else if (surface == VideoCallFragment.SURFACE_DISPLAY) {
            mVideoCall.setDisplaySurface(ui.getDisplayVideoSurface());
        }
    }

    public void onSurfaceChanged(int surface, int format, int width, int height) {
    }

    public void onSurfaceReleased(int surface) {

        if (mVideoCall == null) {
            return;
        }

        if (surface == VideoCallFragment.SURFACE_DISPLAY) {
            mVideoCall.setDisplaySurface(null);
        } else if (surface == VideoCallFragment.SURFACE_PREVIEW) {
            mVideoCall.setPreviewSurface(null);
            enableCamera(mVideoCall, false);
        }
    }

    public void onSurfaceDestroyed(int surface) {

        if (mVideoCall == null) {
            return;
        }

        final boolean isChangingConfigurations = InCallPresenter.getInstance().isChangingConfigurations();


        if (surface == VideoCallFragment.SURFACE_PREVIEW) {
            if (!isChangingConfigurations) {
                enableCamera(mVideoCall, false);
            }
        }
    }

    public void onSurfaceClick(int surfaceId) {
        boolean isFullscreen = InCallPresenter.getInstance().toggleFullscreenMode();

    }

    @Override
    public void onIncomingCall(InCallPresenter.InCallState oldState, InCallPresenter.InCallState newState, Call call) {
        onStateChange(oldState, newState, CallList.getInstance());
    }

    @Override
    public void onStateChange(InCallPresenter.InCallState oldState, InCallPresenter.InCallState newState, CallList callList) {
        if (newState == InCallPresenter.InCallState.NO_CALLS) {
            updateAudioMode(false);

            if (isVideoMode()) {
                exitVideoMode();
            }

            cleanupSurfaces();
        }

        Call primary = null;

        Call currentCall = null;

        if (newState == InCallPresenter.InCallState.INCOMING) {
            primary = callList.getActiveCall();
            currentCall = callList.getIncomingCall();
            if (!CallUtils.isActiveVideoCall(primary)) {
                primary = callList.getIncomingCall();
            }
        } else if (newState == InCallPresenter.InCallState.OUTGOING) {
            currentCall = primary = callList.getOutgoingCall();
        } else if (newState == InCallPresenter.InCallState.PENDING_OUTGOING) {
            currentCall = primary = callList.getPendingOutgoingCall();
        } else if (newState == InCallPresenter.InCallState.INCALL) {
            currentCall = primary = callList.getActiveCall();
        }

        final boolean primaryChanged = !Objects.equals(mPrimaryCall, primary);


        if (primaryChanged) {
            onPrimaryCallChanged(primary);
        } else if (mPrimaryCall != null) {
            updateVideoCall(primary);
        }
        updateCallCache(primary);

        maybeExitFullscreen(currentCall);
        maybeAutoEnterFullscreen(currentCall);
    }

    @Override
    public void onFullscreenModeChanged(boolean isFullscreenMode) {
        cancelAutoFullScreen();
    }

    private void checkForVideoStateChange(Call call) {
        final boolean isVideoCall = CallUtils.isVideoCall(call);
        final boolean hasVideoStateChanged = mCurrentVideoState != call.getVideoState();

        if (!hasVideoStateChanged) {
            return;
        }

        updateCameraSelection(call);

        if (isVideoCall) {
            enterVideoMode(call);
        } else if (isVideoMode()) {
            exitVideoMode();
        }
    }

    private void checkForCallStateChange(Call call) {
        final boolean isVideoCall = CallUtils.isVideoCall(call);
        final boolean hasCallStateChanged = mCurrentCallState != call.getState();
        if (!hasCallStateChanged) {
            return;
        }

        if (isVideoCall) {
            final InCallCameraManager cameraManager = InCallPresenter.getInstance().
                    getInCallCameraManager();

            String prevCameraId = cameraManager.getActiveCameraId();
            updateCameraSelection(call);
            String newCameraId = cameraManager.getActiveCameraId();

            if (!Objects.equals(prevCameraId, newCameraId) && CallUtils.isActiveVideoCall(call)) {
                enableCamera(call.getVideoCall(), true);
            }
        }

        showVideoUi(call.getVideoState(), call.getState());
    }

    private void cleanupSurfaces() {
        final VideoCallUi ui = getUi();
        if (ui == null) {

            return;
        }
        ui.cleanupSurfaces();
    }

    private void onPrimaryCallChanged(Call newPrimaryCall) {
        final boolean isVideoCall = CallUtils.isVideoCall(newPrimaryCall);
        final boolean isVideoMode = isVideoMode();
        if (!isVideoCall && isVideoMode) {

            exitVideoMode();
        } else if (isVideoCall) {


            updateCameraSelection(newPrimaryCall);
            enterVideoMode(newPrimaryCall);
        }
    }

    private boolean isVideoMode() {
        return mIsVideoMode;
    }

    private void updateCallCache(Call call) {
        if (call == null) {
            mCurrentVideoState = VideoProfile.STATE_AUDIO_ONLY;
            mCurrentCallState = Call.State.INVALID;
            mVideoCall = null;
            mPrimaryCall = null;
        } else {
            mCurrentVideoState = call.getVideoState();
            mVideoCall = call.getVideoCall();
            mCurrentCallState = call.getState();
            mPrimaryCall = call;
        }
    }

    @Override
    public void onDetailsChanged(Call call, android.telecom.Call.Details details) {
        if (call == null) {
            return;
        }
        if (!call.equals(mPrimaryCall)) {

            return;
        }

        updateVideoCall(call);

        updateCallCache(call);
    }

    private void updateVideoCall(Call call) {
        checkForVideoCallChange(call);
        checkForVideoStateChange(call);
        checkForCallStateChange(call);
        checkForOrientationAllowedChange(call);
    }

    private void checkForOrientationAllowedChange(Call call) {
        InCallPresenter.getInstance().setInCallAllowsOrientationChange(CallUtils.isVideoCall(call));
    }

    private void checkForVideoCallChange(Call call) {
        final VideoCall videoCall = call.getTelecommCall().getVideoCall();
        if (!Objects.equals(videoCall, mVideoCall)) {
            changeVideoCall(call);
        }
    }

    private void changeVideoCall(Call call) {
        final VideoCall videoCall = call.getTelecommCall().getVideoCall();

        if (mVideoCall != null) {
        }

        final boolean hasChanged = mVideoCall == null && videoCall != null;

        mVideoCall = videoCall;
        if (mVideoCall == null || call == null) {

            return;
        }

        if (CallUtils.isVideoCall(call) && hasChanged) {
            enterVideoMode(call);
        }
    }

    private static boolean isCameraRequired(int videoState) {
        return VideoProfile.isBidirectional(videoState) || VideoProfile.isTransmissionEnabled(videoState);
    }

    private boolean isCameraRequired() {
        return mPrimaryCall != null && isCameraRequired(mPrimaryCall.getVideoState());
    }

    private void enterVideoMode(Call call) {
        VideoCall videoCall = call.getVideoCall();
        int newVideoState = call.getVideoState();


        VideoCallUi ui = getUi();
        if (ui == null) {

            return;
        }

        showVideoUi(newVideoState, call.getState());

        if (videoCall != null) {
            if (ui.isDisplayVideoSurfaceCreated()) {

                videoCall.setDisplaySurface(ui.getDisplayVideoSurface());
            }

            final int rotation = ui.getCurrentRotation();
            if (rotation != VideoCallFragment.ORIENTATION_UNKNOWN) {
                videoCall.setDeviceOrientation(InCallPresenter.toRotationAngle(rotation));
            }

            enableCamera(videoCall, isCameraRequired(newVideoState));
        }
        mCurrentVideoState = newVideoState;
        updateAudioMode(true);

        mIsVideoMode = true;

        maybeAutoEnterFullscreen(call);
    }

    private void updateAudioMode(boolean enableSpeaker) {
        if (!isSpeakerEnabledForVideoCalls()) {

            return;
        }

        final TelecomAdapter telecomAdapter = TelecomAdapter.getInstance();
        final boolean isPrevAudioModeValid = sPrevVideoAudioMode != AudioModeProvider.AUDIO_MODE_INVALID;

        if (isPrevAudioModeValid && !enableSpeaker) {
            telecomAdapter.setAudioRoute(sPrevVideoAudioMode);
            sPrevVideoAudioMode = AudioModeProvider.AUDIO_MODE_INVALID;
            return;
        }

        int currentAudioMode = AudioModeProvider.getInstance().getAudioMode();

        if (!isAudioRouteEnabled(currentAudioMode, CallAudioState.ROUTE_BLUETOOTH | CallAudioState.ROUTE_WIRED_HEADSET) && !isPrevAudioModeValid && enableSpeaker && CallUtils.isVideoCall(mPrimaryCall)) {
            sPrevVideoAudioMode = currentAudioMode;


            telecomAdapter.setAudioRoute(CallAudioState.ROUTE_SPEAKER);
        }
    }

    private static boolean isSpeakerEnabledForVideoCalls() {
        return true;
    }

    private void enableCamera(VideoCall videoCall, boolean isCameraRequired) {

        if (videoCall == null) {

            return;
        }

        if (isCameraRequired) {
            InCallCameraManager cameraManager = InCallPresenter.getInstance().
                    getInCallCameraManager();
            videoCall.setCamera(cameraManager.getActiveCameraId());
            mPreviewSurfaceState = PreviewSurfaceState.CAMERA_SET;

            videoCall.requestCameraCapabilities();
        } else {
            mPreviewSurfaceState = PreviewSurfaceState.NONE;
            videoCall.setCamera(null);
        }
    }

    private void exitVideoMode() {


        showVideoUi(VideoProfile.STATE_AUDIO_ONLY, Call.State.ACTIVE);
        enableCamera(mVideoCall, false);
        InCallPresenter.getInstance().setFullScreen(false);

        mIsVideoMode = false;
    }

    private void showVideoUi(int videoState, int callState) {
        VideoCallUi ui = getUi();
        if (ui == null) {

            return;
        }
        boolean isPaused = VideoProfile.isPaused(videoState);
        boolean isCallActive = callState == Call.State.ACTIVE;
        if (VideoProfile.isBidirectional(videoState)) {
            ui.showVideoViews(true, !isPaused && isCallActive);
        } else if (VideoProfile.isTransmissionEnabled(videoState)) {
            ui.showVideoViews(true, false);
        } else if (VideoProfile.isReceptionEnabled(videoState)) {
            ui.showVideoViews(false, !isPaused && isCallActive);
            loadProfilePhotoAsync();
        } else {
            ui.hideVideoUi();
        }

        InCallPresenter.getInstance().enableScreenTimeout(VideoProfile.isAudioOnly(videoState));
    }

    @Override
    public void onPeerPauseStateChanged(Call call, boolean paused) {
        if (!call.equals(mPrimaryCall)) {
            return;
        }

    }

    @Override
    public void onUpdatePeerDimensions(Call call, int width, int height) {

        VideoCallUi ui = getUi();
        if (ui == null) {

            return;
        }
        if (!call.equals(mPrimaryCall)) {

            return;
        }

        if (width > 0 && height > 0) {
            setDisplayVideoSize(width, height);
        }
    }

    @Override
    public void onVideoQualityChanged(Call call, int videoQuality) {
    }


    @Override
    public void onCameraDimensionsChange(Call call, int width, int height) {
        VideoCallUi ui = getUi();
        if (ui == null) {

            return;
        }

        if (!call.equals(mPrimaryCall)) {

            return;
        }

        mPreviewSurfaceState = PreviewSurfaceState.CAPABILITIES_RECEIVED;
        changePreviewDimensions(width, height);

        if (ui.isPreviewVideoSurfaceCreated()) {
            mPreviewSurfaceState = PreviewSurfaceState.SURFACE_SET;
            mVideoCall.setPreviewSurface(ui.getPreviewVideoSurface());
        }
    }

    private void changePreviewDimensions(int width, int height) {
        VideoCallUi ui = getUi();
        if (ui == null) {
            return;
        }

        ui.setPreviewSurfaceSize(width, height);

        float aspectRatio = 1.0f;
        if (width > 0 && height > 0) {
            aspectRatio = (float) width / (float) height;
        }

        setPreviewSize(mDeviceOrientation, aspectRatio);
    }

    @Override
    public void onCallSessionEvent(int event) {
        StringBuilder sb = new StringBuilder();
        sb.append("onCallSessionEvent = ");

        switch (event) {
            case Connection.VideoProvider.SESSION_EVENT_RX_PAUSE:
                sb.append("rx_pause");
                break;
            case Connection.VideoProvider.SESSION_EVENT_RX_RESUME:
                sb.append("rx_resume");
                break;
            case Connection.VideoProvider.SESSION_EVENT_CAMERA_FAILURE:
                sb.append("camera_failure");
                break;
            case Connection.VideoProvider.SESSION_EVENT_CAMERA_READY:
                sb.append("camera_ready");
                break;
            default:
                sb.append("unknown event = ");
                sb.append(event);
                break;
        }

    }

    @Override
    public void onCallDataUsageChange(long dataUsage) {

    }

    @Override
    public void onDeviceOrientationChanged(int orientation) {
        mDeviceOrientation = orientation;
        Point previewDimensions = getUi().getPreviewSize();
        if (previewDimensions == null) {
            return;
        }

        changePreviewDimensions(previewDimensions.x, previewDimensions.y);
    }

    @Override
    public void onUpgradeToVideoRequest(Call call, int videoState) {

        if (mPrimaryCall == null || !Call.areSame(mPrimaryCall, call)) {

        }

        if (call == null) {
            return;
        }

        call.setSessionModificationTo(videoState);
    }

    @Override
    public void onUpgradeToVideoSuccess(Call call) {

        if (mPrimaryCall == null || !Call.areSame(mPrimaryCall, call)) {

        }

        if (call == null) {
            return;
        }
    }

    @Override
    public void onUpgradeToVideoFail(int status, Call call) {

        if (mPrimaryCall == null || !Call.areSame(mPrimaryCall, call)) {

        }

        if (call == null) {
            return;
        }
    }

    @Override
    public void onDowngradeToAudio(Call call) {
        call.setSessionModificationState(Call.SessionModificationState.NO_REQUEST);
        exitVideoMode();
    }

    private void setPreviewSize(int orientation, float aspectRatio) {
        VideoCallUi ui = getUi();
        if (ui == null) {
            return;
        }

        int height;
        int width;

        if (orientation == Surface.ROTATION_90 || orientation == Surface.ROTATION_270) {
            width = (int) (mMinimumVideoDimension * aspectRatio);
            height = (int) mMinimumVideoDimension;
        } else {
            width = (int) mMinimumVideoDimension;
            height = (int) (mMinimumVideoDimension * aspectRatio);
        }
        ui.setPreviewSize(width, height);
    }

    private void setDisplayVideoSize(int width, int height) {

        VideoCallUi ui = getUi();
        if (ui == null) {
            return;
        }

        Point size = ui.getScreenSize();
        if (size.y * width > size.x * height) {
            size.y = size.x * height / width;
        } else if (size.y * width < size.x * height) {
            size.x = size.y * width / height;
        }
        ui.setDisplayVideoSize(size.x, size.y);
    }

    protected void maybeExitFullscreen(Call call) {
        if (call == null) {
            return;
        }

        if (!CallUtils.isVideoCall(call) || call.getState() == Call.State.INCOMING) {
            InCallPresenter.getInstance().setFullScreen(false);
        }
    }

    protected void maybeAutoEnterFullscreen(Call call) {
        if (!mIsAutoFullscreenEnabled) {
            return;
        }

        if (call == null || (call != null && (call.getState() != Call.State.ACTIVE || !CallUtils.isVideoCall(call)) || InCallPresenter.getInstance().isFullscreen())) {
            cancelAutoFullScreen();
            return;
        }

        if (mAutoFullScreenPending) {

            return;
        }

        mAutoFullScreenPending = true;
        mHandler.postDelayed(mAutoFullscreenRunnable, mAutoFullscreenTimeoutMillis);
    }

    /**
     * Cancels pending auto fullscreen mode.
     */
    public void cancelAutoFullScreen() {
        if (!mAutoFullScreenPending) {

            return;
        }

        mAutoFullScreenPending = false;
    }

    private static boolean isAudioRouteEnabled(int audioRoute, int audioRouteMask) {
        return ((audioRoute & audioRouteMask) != 0);
    }

    private static void updateCameraSelection(Call call) {


        final Call activeCall = CallList.getInstance().getActiveCall();
        int cameraDir = Call.VideoSettings.CAMERA_DIRECTION_UNKNOWN;

        if (call == null) {
            cameraDir = Call.VideoSettings.CAMERA_DIRECTION_UNKNOWN;
        } else if (CallUtils.isAudioCall(call)) {
            cameraDir = Call.VideoSettings.CAMERA_DIRECTION_UNKNOWN;
            call.getVideoSettings().setCameraDir(cameraDir);
        } else if (CallUtils.isVideoCall(activeCall) && CallUtils.isIncomingVideoCall(call)) {
            cameraDir = activeCall.getVideoSettings().getCameraDir();
        } else if (CallUtils.isOutgoingVideoCall(call) && !isCameraDirectionSet(call)) {
            cameraDir = toCameraDirection(call.getVideoState());
            call.getVideoSettings().setCameraDir(cameraDir);
        } else if (CallUtils.isOutgoingVideoCall(call)) {
            cameraDir = call.getVideoSettings().getCameraDir();
        } else if (CallUtils.isActiveVideoCall(call) && !isCameraDirectionSet(call)) {
            cameraDir = toCameraDirection(call.getVideoState());
            call.getVideoSettings().setCameraDir(cameraDir);
        } else if (CallUtils.isActiveVideoCall(call)) {
            cameraDir = call.getVideoSettings().getCameraDir();
        } else {
            cameraDir = toCameraDirection(call.getVideoState());
        }


        final InCallCameraManager cameraManager = InCallPresenter.getInstance().
                getInCallCameraManager();
        cameraManager.setUseFrontFacingCamera(cameraDir == Call.VideoSettings.CAMERA_DIRECTION_FRONT_FACING);
    }

    private static int toCameraDirection(int videoState) {
        return VideoProfile.isTransmissionEnabled(videoState) && !VideoProfile.isBidirectional(videoState) ? Call.VideoSettings.CAMERA_DIRECTION_BACK_FACING : Call.VideoSettings.CAMERA_DIRECTION_FRONT_FACING;
    }

    private static boolean isCameraDirectionSet(Call call) {
        return CallUtils.isVideoCall(call) && call.getVideoSettings().getCameraDir() != Call.VideoSettings.CAMERA_DIRECTION_UNKNOWN;
    }

    private static String toSimpleString(Call call) {
        return call == null ? null : call.toSimpleString();
    }

    public void loadProfilePhotoAsync() {
        final VideoCallUi ui = getUi();
        if (ui == null) {
            return;
        }

        final AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                if (mProfileInfo == null) {
                    mProfileInfo = new ContactInfoCache.ContactCacheEntry();
                    final Cursor cursor = mContext.getContentResolver().query(ContactsContract.Profile.CONTENT_URI, new String[]{ContactsContract.CommonDataKinds.Phone._ID, ContactsContract.CommonDataKinds.Phone.PHOTO_URI, ContactsContract.CommonDataKinds.Phone.LOOKUP_KEY, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,}, null, null, null);
                    if (cursor != null) {
                        try {
                            if (cursor.moveToFirst()) {
                                mProfileInfo.lookupKey = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.LOOKUP_KEY));
                                String photoUri = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));
                                mProfileInfo.displayPhotoUri = photoUri == null ? null : Uri.parse(photoUri);
                                mProfileInfo.name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                            }
                        } finally {
                            cursor.close();
                        }
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                if (mProfileInfo != null) {
                    if (mContactPhotoManager == null) {
                        mContactPhotoManager = ContactPhotoManager.getInstance(mContext);
                    }
                    ContactPhotoManager.DefaultImageRequest imageRequest = (mProfileInfo != null) ? null : new ContactPhotoManager.DefaultImageRequest(mProfileInfo.name, mProfileInfo.lookupKey, false /* isCircularPhoto */);

                    ImageView photoView = ui.getPreviewPhotoView();
                    if (photoView == null) {
                        return;
                    }
                    mContactPhotoManager.loadDirectoryPhoto(photoView, mProfileInfo.displayPhotoUri, false /* darkTheme */, false /* isCircular */, imageRequest);
                }
            }
        };

        task.execute();
    }

    public interface VideoCallUi extends Ui {
        void showVideoViews(boolean showPreview, boolean showIncoming);

        void hideVideoUi();

        boolean isDisplayVideoSurfaceCreated();

        boolean isPreviewVideoSurfaceCreated();

        Surface getDisplayVideoSurface();

        Surface getPreviewVideoSurface();

        int getCurrentRotation();

        void setPreviewSize(int width, int height);

        void setPreviewSurfaceSize(int width, int height);

        void setDisplayVideoSize(int width, int height);

        Point getScreenSize();

        Point getPreviewSize();

        void cleanupSurfaces();

        ImageView getPreviewPhotoView();
    }
}
