package com.algokelvin.primarydialer.mvicall;

import android.util.Log;

import com.google.common.base.Preconditions;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class InCallVideoCallCallbackNotifier {
    /**
     * Singleton instance of this class.
     */
    private static final InCallVideoCallCallbackNotifier sInstance =
            new InCallVideoCallCallbackNotifier();

    /**
     * ConcurrentHashMap constructor params: 8 is initial table size, 0.9f is
     * load factor before resizing, 1 means we only expect a single thread to
     * access the map so make only a single shard
     */
    private final Set<SessionModificationListener> mSessionModificationListeners =
            Collections.newSetFromMap(new ConcurrentHashMap<SessionModificationListener, Boolean>
                    (8, 0.9f, 1));
    private final Set<VideoEventListener> mVideoEventListeners = Collections.newSetFromMap(
            new ConcurrentHashMap<VideoEventListener, Boolean>(8, 0.9f, 1));
    private final Set<SurfaceChangeListener> mSurfaceChangeListeners = Collections.newSetFromMap(
            new ConcurrentHashMap<SurfaceChangeListener, Boolean>(8, 0.9f, 1));

    /**
     * Static singleton accessor method.
     */
    public static InCallVideoCallCallbackNotifier getInstance() {
        return sInstance;
    }

    /**
     * Private constructor.  Instance should only be acquired through getInstance().
     */
    private InCallVideoCallCallbackNotifier() {
    }

    /**
     * Adds a new {@link SessionModificationListener}.
     *
     * @param listener The listener.
     */
    public void addSessionModificationListener(SessionModificationListener listener) {
        Preconditions.checkNotNull(listener);
        mSessionModificationListeners.add(listener);
    }

    /**
     * Remove a {@link SessionModificationListener}.
     *
     * @param listener The listener.
     */
    public void removeSessionModificationListener(SessionModificationListener listener) {
        if (listener != null) {
            mSessionModificationListeners.remove(listener);
        }
    }

    /**
     * Adds a new {@link VideoEventListener}.
     *
     * @param listener The listener.
     */
    public void addVideoEventListener(VideoEventListener listener) {
        Preconditions.checkNotNull(listener);
        mVideoEventListeners.add(listener);
    }

    /**
     * Remove a {@link VideoEventListener}.
     *
     * @param listener The listener.
     */
    public void removeVideoEventListener(VideoEventListener listener) {
        if (listener != null) {
            mVideoEventListeners.remove(listener);
        }
    }

    /**
     * Adds a new {@link SurfaceChangeListener}.
     *
     * @param listener The listener.
     */
    public void addSurfaceChangeListener(SurfaceChangeListener listener) {
        Preconditions.checkNotNull(listener);
        mSurfaceChangeListeners.add(listener);
    }

    /**
     * Remove a {@link SurfaceChangeListener}.
     *
     * @param listener The listener.
     */
    public void removeSurfaceChangeListener(SurfaceChangeListener listener) {
        if (listener != null) {
            mSurfaceChangeListeners.remove(listener);
        }
    }

    /**
     * Inform listeners of an upgrade to video request for a call.
     * @param call The call.
     * @param videoState The video state we want to upgrade to.
     */
    public void upgradeToVideoRequest(Call call, int videoState) {
        Log.d("upgradeToVideoRequest", "upgradeToVideoRequest call = " + call + " new video state = " + videoState);
        for (SessionModificationListener listener : mSessionModificationListeners) {
            listener.onUpgradeToVideoRequest(call, videoState);
        }
    }

    /**
     * Inform listeners of a successful response to a video request for a call.
     *
     * @param call The call.
     */
    public void upgradeToVideoSuccess(Call call) {
        for (SessionModificationListener listener : mSessionModificationListeners) {
            listener.onUpgradeToVideoSuccess(call);
        }
    }

    /**
     * Inform listeners of an unsuccessful response to a video request for a call.
     *
     * @param call The call.
     */
    public void upgradeToVideoFail(int status, Call call) {
        for (SessionModificationListener listener : mSessionModificationListeners) {
            listener.onUpgradeToVideoFail(status, call);
        }
    }

    /**
     * Inform listeners of a downgrade to audio.
     *
     * @param call The call.
     */
    public void downgradeToAudio(Call call) {
        for (SessionModificationListener listener : mSessionModificationListeners) {
            listener.onDowngradeToAudio(call);
        }
    }

    /**
     * Inform listeners of a call session event.
     *
     * @param event The call session event.
     */
    public void callSessionEvent(int event) {
        for (VideoEventListener listener : mVideoEventListeners) {
            listener.onCallSessionEvent(event);
        }
    }

    /**
     * Inform listeners of a downgrade to audio.
     *
     * @param call The call.
     * @param paused The paused state.
     */
    public void peerPausedStateChanged(Call call, boolean paused) {
        for (VideoEventListener listener : mVideoEventListeners) {
            listener.onPeerPauseStateChanged(call, paused);
        }
    }

    /**
     * Inform listeners of any change in the video quality of the call
     *
     * @param call The call.
     * @param videoQuality The updated video quality of the call.
     */
    public void videoQualityChanged(Call call, int videoQuality) {
        for (VideoEventListener listener : mVideoEventListeners) {
            listener.onVideoQualityChanged(call, videoQuality);
        }
    }

    /**
     * Inform listeners of a change to peer dimensions.
     *
     * @param call The call.
     * @param width New peer width.
     * @param height New peer height.
     */
    public void peerDimensionsChanged(Call call, int width, int height) {
        for (SurfaceChangeListener listener : mSurfaceChangeListeners) {
            listener.onUpdatePeerDimensions(call, width, height);
        }
    }

    /**
     * Inform listeners of a change to camera dimensions.
     *
     * @param call The call.
     * @param width The new camera video width.
     * @param height The new camera video height.
     */
    public void cameraDimensionsChanged(Call call, int width, int height) {
        for (SurfaceChangeListener listener : mSurfaceChangeListeners) {
            listener.onCameraDimensionsChange(call, width, height);
        }
    }

    /**
     * Inform listeners of a change to call data usage.
     *
     * @param dataUsage data usage value
     */
    public void callDataUsageChanged(long dataUsage) {
        for (VideoEventListener listener : mVideoEventListeners) {
            listener.onCallDataUsageChange(dataUsage);
        }
    }

    /**
     * Listener interface for any class that wants to be notified of upgrade to video and downgrade
     * to audio session modification requests.
     */
    public interface SessionModificationListener {
        /**
         * Called when a peer request is received to upgrade an audio-only call to a video call.
         *
         * @param call The call the request was received for.
         * @param videoState The video state that the request wants to upgrade to.
         */
        void onUpgradeToVideoRequest(Call call, int videoState);

        /**
         * Called when a request to a peer to upgrade an audio-only call to a video call is
         * successful.
         *
         * @param call The call the request was successful for.
         */
        void onUpgradeToVideoSuccess(Call call);

        /**
         * Called when a request to a peer to upgrade an audio-only call to a video call is
         * NOT successful. This can be if the peer chooses rejects the the video call, or if the
         * peer does not support video calling, or if there is some error in sending the request.
         *
         * @param call The call the request was successful for.
         */
        void onUpgradeToVideoFail(int status, Call call);

        /**
         * Called when a call has been downgraded to audio-only.
         *
         * @param call The call which was downgraded to audio-only.
         */
        void onDowngradeToAudio(Call call);
    }

    /**
     * Listener interface for any class that wants to be notified of video events, including pause
     * and un-pause of peer video, video quality changes.
     */
    public interface VideoEventListener {
        /**
         * Called when the peer pauses or un-pauses video transmission.
         *
         * @param call   The call which paused or un-paused video transmission.
         * @param paused {@code True} when the video transmission is paused, {@code false}
         *               otherwise.
         */
        void onPeerPauseStateChanged(Call call, boolean paused);

        /**
         * Called when the video quality changes.
         *
         * @param call   The call whose video quality changes.
         * @param videoCallQuality - values are QUALITY_HIGH, MEDIUM, LOW and UNKNOWN.
         */
        void onVideoQualityChanged(Call call, int videoCallQuality);

        /*
         * Called when call data usage value is requested or when call data usage value is updated
         * because of a call state change
         *
         * @param dataUsage call data usage value
         */
        void onCallDataUsageChange(long dataUsage);

        /**
         * Called when call session event is raised.
         *
         * @param event The call session event.
         */
        void onCallSessionEvent(int event);
    }

    /**
     * Listener interface for any class that wants to be notified of changes to the video surfaces.
     */
    public interface SurfaceChangeListener {
        /**
         * Called when the peer video feed changes dimensions. This can occur when the peer rotates
         * their device, changing the aspect ratio of the video signal.
         *
         * @param call The call which experienced a peer video
         * @param width
         * @param height
         */
        void onUpdatePeerDimensions(Call call, int width, int height);

        /**
         * Called when the local camera changes dimensions.  This occurs when a change in camera
         * occurs.
         *
         * @param call The call which experienced the camera dimension change.
         * @param width The new camera video width.
         * @param height The new camera video height.
         */
        void onCameraDimensionsChange(Call call, int width, int height);
    }
}
