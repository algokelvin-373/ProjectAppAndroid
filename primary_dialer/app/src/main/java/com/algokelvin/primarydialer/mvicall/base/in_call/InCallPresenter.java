package com.algokelvin.primarydialer.mvicall.base.in_call;

import android.annotation.SuppressLint;
import android.app.ActivityManager.TaskDescription;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.telecom.DisconnectCause;
import android.telecom.PhoneAccount;
import android.telecom.PhoneAccountHandle;
import android.telecom.TelecomManager;
import android.telecom.VideoProfile;
import android.text.TextUtils;
import android.view.Surface;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.algokelvin.primarydialer.R;
import com.algokelvin.primarydialer.mvicall.AudioModeProvider;
import com.algokelvin.primarydialer.mvicall.Call;
import com.algokelvin.primarydialer.mvicall.CallList;
import com.algokelvin.primarydialer.mvicall.CircularRevealFragment;
import com.algokelvin.primarydialer.mvicall.ContactInfoCache;
import com.algokelvin.primarydialer.mvicall.InCallCameraManager;
import com.algokelvin.primarydialer.mvicall.InCallUIMaterialColorMapUtils;
import com.algokelvin.primarydialer.mvicall.base.incoming.IncomingPresenter;
import com.algokelvin.primarydialer.mvicall.ProximitySensor;
import com.algokelvin.primarydialer.mvicall.StatusBarNotifier;
import com.algokelvin.primarydialer.mvicall.TelecomAdapter;
import com.algokelvin.primarydialer.mvicall.VideoPauseController;
import com.algokelvin.primarydialer.mvicall.common.MaterialColorMapUtils;
import com.algokelvin.primarydialer.mvicall.common.TouchPointManager;
import com.algokelvin.primarydialer.mvicall.incalluibind.ObjectFactory;
import com.google.common.base.Preconditions;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;


public class InCallPresenter implements CallList.Listener, CircularRevealFragment.OnCircularRevealCompleteListener {

    private static final String EXTRA_FIRST_TIME_SHOWN = "com.android.incallui.intent.extra.FIRST_TIME_SHOWN";
    private static final Bundle EMPTY_EXTRAS = new Bundle();

    @SuppressLint("StaticFieldLeak")
    private static InCallPresenter sInCallPresenter;
    private final Set<InCallStateListener> mListeners = Collections.newSetFromMap(new ConcurrentHashMap<>(8, 0.9f, 1));
    private final List<IncomingCallListener> mIncomingCallListeners = new CopyOnWriteArrayList<>();
    private final Set<InCallDetailsListener> mDetailsListeners = Collections.newSetFromMap(new ConcurrentHashMap<>(8, 0.9f, 1));
    private final Set<CanAddCallListener> mCanAddCallListeners = Collections.newSetFromMap(new ConcurrentHashMap<>(8, 0.9f, 1));
    private final Set<InCallUiListener> mInCallUiListeners = Collections.newSetFromMap(new ConcurrentHashMap<>(8, 0.9f, 1));
    private final Set<InCallOrientationListener> mOrientationListeners = Collections.newSetFromMap(new ConcurrentHashMap<>(8, 0.9f, 1));
    private final Set<InCallEventListener> mInCallEventListeners = Collections.newSetFromMap(new ConcurrentHashMap<>(8, 0.9f, 1));

    private AudioModeProvider mAudioModeProvider;
    private StatusBarNotifier mStatusBarNotifier;
    private ContactInfoCache mContactInfoCache;
    private Context mContext;
    private CallList mCallList;
    private InCallActivity mInCallActivity;
    private InCallState mInCallState = InCallState.NO_CALLS;
    private ProximitySensor mProximitySensor;
    private boolean mServiceConnected = false;
    private boolean mAccountSelectionCancelled = false;
    private InCallCameraManager mInCallCameraManager = null;
    private final IncomingPresenter mIncomingPresenter = new IncomingPresenter();
    private boolean mBoundAndWaitingForOutgoingCall;
    private PhoneAccountHandle mPendingPhoneAccountHandle;
    private boolean mIsFullScreen = false;

    private boolean mIsActivityPreviouslyStarted = false;
    private boolean mServiceBound = false;
    private boolean mIsChangingConfigurations = false;
    private MaterialColorMapUtils.MaterialPalette mThemeColors;
    private TelecomManager mTelecomManager;
    private boolean mAwaitingCallListUpdate = false;
    private boolean isConference = false; // is conference

    private final android.telecom.Call.Callback mCallCallback = new android.telecom.Call.Callback() {

        @Override
        public void onPostDialWait(android.telecom.Call telecomCall, String remainingPostDialSequence) {
            final Call call = mCallList.getCallByTelecommCall(telecomCall);
            if (call == null) {
                return;
            }
            onPostDialCharWait(call.getId(), remainingPostDialSequence);
        }
        //debug64 //debug77
        @Override
        public void onDetailsChanged(android.telecom.Call telecomCall, android.telecom.Call.Details details) {
            final Call call = mCallList.getCallByTelecommCall(telecomCall);
            if (call == null) {
                return;
            }
            for (InCallDetailsListener listener : mDetailsListeners) {
                listener.onDetailsChanged(call, details);
            }
        }

        @Override
        public void onConferenceableCallsChanged(android.telecom.Call telecomCall, List<android.telecom.Call> conferenceableCalls) {
            onDetailsChanged(telecomCall, telecomCall.getDetails());
        }

    };

    //debug1
    public static synchronized InCallPresenter getInstance() {
        if (sInCallPresenter == null) {
            sInCallPresenter = new InCallPresenter();
        }
        return sInCallPresenter;
    }

    static synchronized void setInstance(InCallPresenter inCallPresenter) {
        sInCallPresenter = inCallPresenter;
    }

    //debug31
    public InCallState getInCallState() {
        return mInCallState;
    }

    //debug16
    public void setUp(Context context, CallList callList, AudioModeProvider audioModeProvider, StatusBarNotifier statusBarNotifier, ContactInfoCache contactInfoCache, ProximitySensor proximitySensor) {
        if (mServiceConnected) {
            Preconditions.checkState(context == mContext);
            Preconditions.checkState(callList == mCallList);
            Preconditions.checkState(audioModeProvider == mAudioModeProvider);
            return;
        }

        Preconditions.checkNotNull(context);
        mContext = context;

        mContactInfoCache = contactInfoCache;

        mStatusBarNotifier = statusBarNotifier;
        addListener(mStatusBarNotifier);

        mAudioModeProvider = audioModeProvider;

        mProximitySensor = proximitySensor;
        addListener(mProximitySensor);

        addIncomingCallListener(mIncomingPresenter);
        addInCallUiListener(mIncomingPresenter);

        mCallList = callList;
        mServiceConnected = true;
        mCallList.addListener(this);

        VideoPauseController.getInstance().setUp(this);
    }

    //debug11
    public void tearDown() {
        mServiceConnected = false;
        attemptCleanup();
        VideoPauseController.getInstance().tearDown();
    }


    private void attemptFinishActivity() {
        final boolean doFinish = (mInCallActivity != null && isActivityStarted());
        if (doFinish) {
            mInCallActivity.setExcludeFromRecents(true);
            mInCallActivity.finish();

            if (mAccountSelectionCancelled) {
                mInCallActivity.overridePendingTransition(0, 0);
            }
        }
    }

    //debug25
    public void setActivity(InCallActivity inCallActivity) {
        if (inCallActivity == null) {
            throw new IllegalArgumentException("registerActivity cannot be called with null");
        }
        if (mInCallActivity != null && mInCallActivity != inCallActivity) {
        }
        updateActivity(inCallActivity);
    }

    public void unsetActivity(InCallActivity inCallActivity) {
        if (inCallActivity == null) {
            throw new IllegalArgumentException("unregisterActivity cannot be called with null");
        }
        if (mInCallActivity == null) {
            return;
        }
        if (mInCallActivity != inCallActivity) {
            return;
        }
        updateActivity(null);
    }

    //debug26
    private void updateActivity(InCallActivity inCallActivity) {
        boolean updateListeners = false;
        boolean doAttemptCleanup = false;

        if (inCallActivity != null) {
            if (mInCallActivity == null) {
                updateListeners = true;
            }

            mInCallActivity = inCallActivity;
            mInCallActivity.setExcludeFromRecents(false);
            if (mCallList != null && mCallList.getDisconnectedCall() != null) {
                maybeShowErrorDialogOnDisconnect(mCallList.getDisconnectedCall());
            }
            if (mInCallState == InCallState.NO_CALLS) {
                attemptFinishActivity();
                return;
            }
        } else {
            updateListeners = true;
            mInCallActivity = null;
            doAttemptCleanup = true;

            if (mInCallState == InCallState.INCALL){
                if (mContext != null) {
                }
            }else if (mInCallState == InCallState.NO_CALLS){
                if (mContext != null) {
                }
            }

        }

        if (updateListeners) {
            onCallListChange(mCallList);
        }

        if (doAttemptCleanup) {
            attemptCleanup();
        }
    }

    public void onBringToForeground(boolean showDialpad) {
        bringToForeground(showDialpad);
    }

    //debug19
    public void onCallAdded(@NonNull android.telecom.Call call) {
        setBoundAndWaitingForOutgoingCall(false, null);
        call.registerCallback(mCallCallback);

        String phoneNumber = infoPhoneNumber(call);
        if (!TextUtils.isEmpty(phoneNumber)) {
            int getState = call.getState();
            int state = android.telecom.Call.STATE_RINGING;
            if (getState == state) {
                if (!isConference) {
                    IncomingPresenter.getInstance().onIncommingCallAdded(call);
                    return;
                }
            }
        }
        
    }

    //debug14
    public String infoPhoneNumber(android.telecom.Call details){
        String subscribeInfo = "";
        Uri uri = details.getDetails().getHandle();
        String decode = Uri.decode(uri != null ? uri.getSchemeSpecificPart() : null);
        CharSequence charSequence = decode;
        if (charSequence != null){
            subscribeInfo = charSequence.toString();
        }else {
            subscribeInfo = "";
        }
        return subscribeInfo;
    }

    //debug73 //debug86
    public void onCallRemoved(android.telecom.Call call) {
        call.unregisterCallback(mCallCallback);
        isConference = false;
    }

    //debug15
    public void onCanAddCallChanged(boolean canAddCall) {
        for (CanAddCallListener listener : mCanAddCallListeners) {
            listener.onCanAddCallChanged(canAddCall);
        }
        isConference = canAddCall;
    }

    //debug5
    @Override
    public void onCallListChange(CallList callList) {
        if (mInCallActivity != null && mInCallActivity.getCallCardFragment() != null && mInCallActivity.getCallCardFragment().isAnimating()) {
            mAwaitingCallListUpdate = true;
            return;
        }
        if (callList == null) {
            return;
        }

        mAwaitingCallListUpdate = false;

        InCallState newState = getPotentialStateFromCallList(callList);
        InCallState oldState = mInCallState;
        newState = startOrFinishUi(newState);
        mInCallState = newState;

//        for (InCallStateListener listener : mListeners) {
//            listener.onStateChange(oldState, mInCallState, callList);
//        }

        if (isActivityStarted()) {
            final boolean hasCall = callList.getActiveOrBackgroundCall() != null || callList.getOutgoingCall() != null;
            mInCallActivity.dismissKeyguard(hasCall);
        }
    }

    @Override
    public void onIncomingCall(Call call) {
        InCallState newState = startOrFinishUi(InCallState.INCOMING);
        InCallState oldState = mInCallState;
        mInCallState = newState;

        for (IncomingCallListener listener : mIncomingCallListeners) {
            listener.onIncomingCall(oldState, mInCallState, call);
        }
    }

    @Override
    public void onUpgradeToVideo(Call call) {
    }

    //debug67 //debug80
    @Override
    public void onDisconnect(Call call) {
        maybeShowErrorDialogOnDisconnect(call);
        onCallListChange(mCallList);
        isConference = false;
        if (isActivityStarted()) {
            mInCallActivity.dismissKeyguard(false);
        }
    }

    //debug6
    public InCallState getPotentialStateFromCallList(CallList callList) {

        InCallState newState = InCallState.NO_CALLS;

        if (callList == null) {
            return newState;
        }
        if (callList.getIncomingCall() != null) {
            newState = InCallState.INCOMING;
        } else if (callList.getWaitingForAccountCall() != null) {
            newState = InCallState.WAITING_FOR_ACCOUNT;
        } else if (callList.getPendingOutgoingCall() != null) {
            newState = InCallState.PENDING_OUTGOING;
        } else if (callList.getOutgoingCall() != null) {
            newState = InCallState.OUTGOING;
        } else if (callList.getActiveCall() != null || callList.getBackgroundCall() != null || callList.getDisconnectedCall() != null || callList.getDisconnectingCall() != null) {
            newState = InCallState.INCALL;
        }

        if (newState == InCallState.NO_CALLS) {
            if (mBoundAndWaitingForOutgoingCall) {
                return InCallState.OUTGOING;
            }
        }

        return newState;
    }

    //debug17
    public void setBoundAndWaitingForOutgoingCall(boolean isBound, PhoneAccountHandle handle) {
        mBoundAndWaitingForOutgoingCall = isBound;
        mPendingPhoneAccountHandle = handle;
        if (isBound && mInCallState == InCallState.NO_CALLS) {
            mInCallState = InCallState.OUTGOING;
        }
    }

    //debug40
    @Override
    public void onCircularRevealComplete(FragmentManager fm) {
        if (mInCallActivity != null) {
            mInCallActivity.showCallCardFragment(true);
            mInCallActivity.getCallCardFragment().animateForNewOutgoingCall();
            CircularRevealFragment.endCircularReveal(mInCallActivity.getFragmentManager());
        }
    }
    //debug63
    public void onShrinkAnimationComplete() {
        if (mAwaitingCallListUpdate) {
            onCallListChange(mCallList);
        }
    }

    //debug3
    public void addIncomingCallListener(IncomingCallListener listener) {
        Preconditions.checkNotNull(listener);
        mIncomingCallListeners.add(listener);
    }
    //debug75 //debug89
    public void removeIncomingCallListener(IncomingCallListener listener) {
        if (listener != null) {
            mIncomingCallListeners.remove(listener);
        }
    }

    //debug2
    public void addListener(InCallStateListener listener) {
        Preconditions.checkNotNull(listener);
        mListeners.add(listener);
    }

    //debug74 //debug88
    public void removeListener(InCallStateListener listener) {
        if (listener != null) {
            mListeners.remove(listener);
        }
    }

    //debug55
    public void addDetailsListener(InCallDetailsListener listener) {
        Preconditions.checkNotNull(listener);
        mDetailsListeners.add(listener);
    }

    public void removeDetailsListener(InCallDetailsListener listener) {
        if (listener != null) {
            mDetailsListeners.remove(listener);
        }
    }

    public void addCanAddCallListener(CanAddCallListener listener) {
        Preconditions.checkNotNull(listener);
        mCanAddCallListeners.add(listener);
    }

    public void removeCanAddCallListener(CanAddCallListener listener) {
        if (listener != null) {
            mCanAddCallListeners.remove(listener);
        }
    }

    public void addOrientationListener(InCallOrientationListener listener) {
        Preconditions.checkNotNull(listener);
        mOrientationListeners.add(listener);
    }

    public void removeOrientationListener(InCallOrientationListener listener) {
        if (listener != null) {
            mOrientationListeners.remove(listener);
        }
    }

    //debug56
    public void addInCallEventListener(InCallEventListener listener) {
        Preconditions.checkNotNull(listener);
        mInCallEventListeners.add(listener);
    }

    public void removeInCallEventListener(InCallEventListener listener) {
        if (listener != null) {
            mInCallEventListeners.remove(listener);
        }
    }

    public ProximitySensor getProximitySensor() {
        return mProximitySensor;
    }

    public void handleAccountSelection(PhoneAccountHandle accountHandle, boolean setDefault) {
        if (mCallList != null) {
            Call call = mCallList.getWaitingForAccountCall();
            if (call != null) {
                String callId = call.getId();
                TelecomAdapter.getInstance().phoneAccountSelected(callId, accountHandle, setDefault);
            }
        }
    }

    public void cancelAccountSelection() {
        mAccountSelectionCancelled = true;
        if (mCallList != null) {
            Call call = mCallList.getWaitingForAccountCall();
            if (call != null) {
                String callId = call.getId();
                TelecomAdapter.getInstance().disconnectCall(callId);
            }
        }
    }

    public void hangUpOngoingCall(Context context) {
        if (mCallList == null) {
            if (mStatusBarNotifier == null) {
                StatusBarNotifier.clearAllCallNotifications(context);
            }
            return;
        }

        Call call = mCallList.getOutgoingCall();
        if (call == null) {
            call = mCallList.getActiveOrBackgroundCall();
        }

        if (call != null) {
            TelecomAdapter.getInstance().disconnectCall(call.getId());
            call.setState(Call.State.DISCONNECTING);
            mCallList.onUpdate(call);
        }
    }

    public void answerIncomingCall(Context context, int videoState) {
        if (mCallList == null) {
            StatusBarNotifier.clearAllCallNotifications(context);
            return;
        }

        Call call = mCallList.getIncomingCall();
        if (call != null) {
            TelecomAdapter.getInstance().answerCall(call.getId(), videoState);
            showInCall(false, false/* newOutgoingCall */);
        }
    }

    public void declineIncomingCall(Context context) {
        if (mCallList == null) {
            StatusBarNotifier.clearAllCallNotifications(context);
            return;
        }

        Call call = mCallList.getIncomingCall();
        if (call != null) {
            TelecomAdapter.getInstance().rejectCall(call.getId(), false, null);
        }
    }

    public void acceptUpgradeRequest(int videoState, Context context) {
        if (mCallList == null) {
            StatusBarNotifier.clearAllCallNotifications(context);
            return;
        }

        Call call = mCallList.getVideoUpgradeRequestCall();
        if (call != null) {
            VideoProfile videoProfile = new VideoProfile(videoState);
            call.getVideoCall().sendSessionModifyResponse(videoProfile);
            call.setSessionModificationState(Call.SessionModificationState.NO_REQUEST);
        }
    }

    public void declineUpgradeRequest(Context context) {
        if (mCallList == null) {
            StatusBarNotifier.clearAllCallNotifications(context);
            return;
        }

        Call call = mCallList.getVideoUpgradeRequestCall();
        if (call != null) {
            VideoProfile videoProfile = new VideoProfile(call.getVideoState());
            call.getVideoCall().sendSessionModifyResponse(videoProfile);
            call.setSessionModificationState(Call.SessionModificationState.NO_REQUEST);
        }
    }

    //debug70 //debug83
    public boolean isShowingInCallUi() {
        return (isActivityStarted() && mInCallActivity.isVisible());
    }

    //debug8
    public boolean isActivityStarted() {
        return (mInCallActivity != null && !mInCallActivity.isDestroyed() && !mInCallActivity.isFinishing());
    }

    public boolean isChangingConfigurations() {
        return mIsChangingConfigurations;
    }

    void updateIsChangingConfigurations() {
        mIsChangingConfigurations = false;
        if (mInCallActivity != null) {
            mIsChangingConfigurations = mInCallActivity.isChangingConfigurations();
        }
    }

    //debug36
    public void onUiShowing(boolean showing) {
        if (mProximitySensor != null) {
            mProximitySensor.onInCallShowing(showing);
        }

        Intent broadcastIntent = ObjectFactory.getUiReadyBroadcastIntent(mContext);
        if (broadcastIntent != null) {
            broadcastIntent.putExtra(EXTRA_FIRST_TIME_SHOWN, !mIsActivityPreviouslyStarted);

//            if (showing) {
//                mContext.sendStickyBroadcast(broadcastIntent);
//            } else {
//                mContext.removeStickyBroadcast(broadcastIntent);
//            }
        }

        if (showing) {
            mIsActivityPreviouslyStarted = true;
        } else {
            updateIsChangingConfigurations();
        }

        for (InCallUiListener listener : mInCallUiListeners) {
            listener.onUiShowing(showing);
        }
    }

    //debug4
    public void addInCallUiListener(InCallUiListener listener) {
        mInCallUiListeners.add(listener);
    }

    //debug29
    void onActivityStarted() {
        notifyVideoPauseController(true);
    }

    void onActivityStopped() {
        notifyVideoPauseController(false);
    }

    //debug30
    private void notifyVideoPauseController(boolean showing) {
        if (!mIsChangingConfigurations) {
            VideoPauseController.getInstance().onUiShowing(showing);
        }
    }

    public void bringToForeground(boolean showDialpad) {
        if (!isShowingInCallUi() && mInCallState != InCallState.NO_CALLS) {
            showInCall(showDialpad, false /* newOutgoingCall */);
        }
    }

    public void onPostDialCharWait(String callId, String chars) {
        if (isActivityStarted()) {
            mInCallActivity.showPostCharWaitDialog(callId, chars);
        }
    }

    public boolean handleCallKey() {
        final CallList calls = mCallList;
        final Call incomingCall = calls.getIncomingCall();
        if (incomingCall != null) {
            TelecomAdapter.getInstance().answerCall(incomingCall.getId(), VideoProfile.STATE_AUDIO_ONLY);
            return true;
        }

        final Call activeCall = calls.getActiveCall();
        if (activeCall != null) {
            boolean canMerge = activeCall.can(android.telecom.Call.Details.CAPABILITY_MERGE_CONFERENCE);
            boolean canSwap = activeCall.can(android.telecom.Call.Details.CAPABILITY_SWAP_CONFERENCE);
            if (canMerge) {
                TelecomAdapter.getInstance().merge(activeCall.getId());
                return true;
            } else if (canSwap) {
                TelecomAdapter.getInstance().swap(activeCall.getId());
                return true;
            }
        }

        final Call heldCall = calls.getBackgroundCall();
        if (heldCall != null) {
            boolean canHold = heldCall.can(android.telecom.Call.Details.CAPABILITY_HOLD);
            if (heldCall.getState() == Call.State.ONHOLD && canHold) {
                TelecomAdapter.getInstance().unholdCall(heldCall.getId());
                return true;
            }
        }

        return true;
    }

    public void onDismissDialog() {
        if (mInCallState == InCallState.NO_CALLS) {
            attemptFinishActivity();
            attemptCleanup();
        }
    }

    public boolean toggleFullscreenMode() {
        mIsFullScreen = !mIsFullScreen;
        notifyFullscreenModeChange(mIsFullScreen);
        return mIsFullScreen;
    }

    public void setFullScreen(boolean isFullScreen) {
        if (mIsFullScreen == isFullScreen) {
            return;
        }
        mIsFullScreen = isFullScreen;
        notifyFullscreenModeChange(mIsFullScreen);
    }

    public boolean isFullscreen() {
        return mIsFullScreen;
    }

    public void notifyFullscreenModeChange(boolean isFullscreenMode) {
        for (InCallEventListener listener : mInCallEventListeners) {
            listener.onFullscreenModeChanged(isFullscreenMode);
        }
    }

    //debug68 //debug81
    private void maybeShowErrorDialogOnDisconnect(Call call) {
        if (isActivityStarted() && call.getState() == Call.State.DISCONNECTED) {
            if (call.getAccountHandle() == null && !call.isConferenceCall()) {
                setDisconnectCauseForMissingAccounts(call);
            }
            mInCallActivity.maybeShowErrorDialogOnDisconnect(call.getDisconnectCause());
        }
    }


    //debug7
    private InCallState startOrFinishUi(InCallState newState) {
        if (newState == mInCallState) {
            return newState;
        }

        final boolean startIncomingCallSequence = (InCallState.INCOMING == newState);
        final boolean showAccountPicker = (InCallState.WAITING_FOR_ACCOUNT == newState);

        final boolean mainUiNotVisible = !isShowingInCallUi() || !getCallCardFragmentVisible();
        boolean showCallUi = InCallState.OUTGOING == newState && mainUiNotVisible;
        showCallUi |= (InCallState.PENDING_OUTGOING == mInCallState && InCallState.INCALL == newState && !isActivityStarted());
        showCallUi |= InCallState.PENDING_OUTGOING == newState && mainUiNotVisible && isCallWithNoValidAccounts(mCallList.getPendingOutgoingCall());

        boolean activityIsFinishing = mInCallActivity != null && !isActivityStarted();
        if (activityIsFinishing) {
            return mInCallState;
        }

        if (showCallUi || showAccountPicker) {
            showInCall(false, !showAccountPicker);
        } else if (startIncomingCallSequence) {
            showInCall(false, !startIncomingCallSequence);
        } else if (newState == InCallState.NO_CALLS) {
            attemptFinishActivity();
            attemptCleanup();
        }

        return newState;
    }

    //debug22
    public static boolean isCallWithNoValidAccounts(Call call) {
        if (call != null && !call.isEmergencyCall()) {
            Bundle extras = call.getIntentExtras();

            if (extras == null) {
                extras = EMPTY_EXTRAS;
            }

            final List<PhoneAccountHandle> phoneAccountHandles = extras.getParcelableArrayList(android.telecom.Call.AVAILABLE_PHONE_ACCOUNTS);
            return (call.getAccountHandle() == null && (phoneAccountHandles == null || phoneAccountHandles.isEmpty()));
        }
        return false;
    }

    private void setDisconnectCauseForMissingAccounts(Call call) {
        android.telecom.Call telecomCall = call.getTelecommCall();

        Bundle extras = telecomCall.getDetails().getIntentExtras();
        if (extras == null) {
            extras = new Bundle();
        }

        final List<PhoneAccountHandle> phoneAccountHandles = extras.getParcelableArrayList(android.telecom.Call.AVAILABLE_PHONE_ACCOUNTS);

        if (phoneAccountHandles == null || phoneAccountHandles.isEmpty()) {
            String scheme = telecomCall.getDetails().getHandle().getScheme();
            final String errorMsg = PhoneAccount.SCHEME_TEL.equals(scheme) ? mContext.getString(R.string.callFailed_simError) : mContext.getString(R.string.incall_error_supp_service_unknown);
            DisconnectCause disconnectCause = new DisconnectCause(DisconnectCause.ERROR, null, errorMsg, errorMsg);
            call.setDisconnectCause(disconnectCause);
        }
    }

    //debug73 //debug87
    private void attemptCleanup() {
        boolean shouldCleanup = (mInCallActivity == null && !mServiceConnected && mInCallState == InCallState.NO_CALLS);

        if (shouldCleanup) {
            mIsActivityPreviouslyStarted = false;
            mIsChangingConfigurations = false;

            if (mContactInfoCache != null) {
                mContactInfoCache.clearCache();
            }
            mContactInfoCache = null;

            if (mProximitySensor != null) {
                removeListener(mProximitySensor);
                mProximitySensor.tearDown();
            }
            mProximitySensor = null;

            mAudioModeProvider = null;

            if (mStatusBarNotifier != null) {
                removeListener(mStatusBarNotifier);
            }
            mStatusBarNotifier = null;

            if (mCallList != null) {
                mCallList.removeListener(this);
            }
            mCallList = null;

            mContext = null;
            mInCallActivity = null;

            mListeners.clear();
            mIncomingCallListeners.clear();
            mDetailsListeners.clear();
            mCanAddCallListeners.clear();
            mOrientationListeners.clear();
            mInCallEventListeners.clear();

        }
    }

    //debug7
    public void showInCall(final boolean showDialpad, final boolean newOutgoingCall) {
        mContext.startActivity(getInCallIntent(showDialpad, newOutgoingCall));
    }

    //debug9
    public void onServiceBind() {
        mServiceBound = true;
    }

    //debug8
    public void onServiceUnbind() {
        InCallPresenter.getInstance().setBoundAndWaitingForOutgoingCall(false, null);
        mServiceBound = false;
    }

    //debug10
    public void maybeStartRevealAnimation(Intent intent) {
        if (intent == null || mInCallActivity != null) {
            return;
        }
        final Bundle extras = intent.getBundleExtra(TelecomManager.EXTRA_OUTGOING_CALL_EXTRAS);
        if (extras == null) {
            return;
        }

        if (extras.containsKey(android.telecom.Call.AVAILABLE_PHONE_ACCOUNTS)) {
            return;
        }

        final PhoneAccountHandle accountHandle = intent.getParcelableExtra(TelecomManager.EXTRA_PHONE_ACCOUNT_HANDLE);
        final Point touchPoint = extras.getParcelable(TouchPointManager.TOUCH_POINT);

        InCallPresenter.getInstance().setBoundAndWaitingForOutgoingCall(true, accountHandle);

        final Intent incallIntent = getInCallIntent(false, true);
        incallIntent.putExtra(TouchPointManager.TOUCH_POINT, touchPoint);
        mContext.startActivity(incallIntent);
    }

    //debug18
    public Intent getInCallIntent(boolean showDialpad, boolean newOutgoingCall) {
        final Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION | Intent.FLAG_ACTIVITY_NEW_TASK);

        intent.setClass(mContext, InCallActivity.class);
        if (showDialpad) {
            intent.putExtra(InCallActivity.SHOW_DIALPAD_EXTRA, true);
        }
        intent.putExtra(InCallActivity.NEW_OUTGOING_CALL_EXTRA, newOutgoingCall);
        return intent;
    }

    public InCallCameraManager getInCallCameraManager() {
        synchronized (this) {
            if (mInCallCameraManager == null) {
                mInCallCameraManager = new InCallCameraManager(mContext);
            }

            return mInCallCameraManager;
        }
    }

    public void onDeviceRotationChange(int rotation) {
        if (mCallList != null) {
            mCallList.notifyCallsOfDeviceRotation(toRotationAngle(rotation));
        } else {
        }
    }

    public static int toRotationAngle(int rotation) {
        int rotationAngle;
        switch (rotation) {
            case Surface.ROTATION_0:
                rotationAngle = 0;
                break;
            case Surface.ROTATION_90:
                rotationAngle = 90;
                break;
            case Surface.ROTATION_180:
                rotationAngle = 180;
                break;
            case Surface.ROTATION_270:
                rotationAngle = 270;
                break;
            default:
                rotationAngle = 0;
        }
        return rotationAngle;
    }

    public void onDeviceOrientationChange(int orientation) {
        for (InCallOrientationListener listener : mOrientationListeners) {
            listener.onDeviceOrientationChanged(orientation);
        }
    }

    /*public void setInCallAllowsOrientationChange(boolean allowOrientationChange) {
        if (mInCallActivity == null) {
            return;
        }

        if (!allowOrientationChange) {
            mInCallActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        } else {
            mInCallActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        }
    }

    public void enableScreenTimeout(boolean enable) {
        if (mInCallActivity == null) {
            return;
        }

        final Window window = mInCallActivity.getWindow();
        if (enable) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }

    public float getSpaceBesideCallCard() {
        if (mInCallActivity != null && mInCallActivity.getCallCardFragment() != null) {
            return mInCallActivity.getCallCardFragment().getSpaceBesideCallCard();
        }
        return 0;
    }*/

    //debug72 //debug85
    public boolean getCallCardFragmentVisible() {
        if (mInCallActivity != null && mInCallActivity.getCallCardFragment() != null) {
            return mInCallActivity.getCallCardFragment().isVisible();
        }
        return false;
    }

    public void showConferenceCallManager(boolean show) {
        if (mInCallActivity == null) {
            return;
        }

        mInCallActivity.showConferenceFragment(show);
    }

    public static boolean isRtl() {
        return TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == View.LAYOUT_DIRECTION_RTL;
    }

    //debug33
    public void setThemeColors() {
        mThemeColors = getColorsFromCall(mCallList.getFirstCall());

        if (mInCallActivity == null) {
            return;
        }

        final Resources resources = mInCallActivity.getResources();
        final int color;
        if (resources.getBoolean(R.bool.is_layout_landscape)) {
            color = resources.getColor(R.color.statusbar_background_color, null);
        } else {
            color = mThemeColors.mSecondaryColor;
        }

        mInCallActivity.getWindow().setStatusBarColor(color);
        final TaskDescription td = new TaskDescription(resources.getString(R.string.notification_ongoing_call), null, color);
        mInCallActivity.setTaskDescription(td);
    }

    //debug37
    public MaterialColorMapUtils.MaterialPalette getThemeColors() {
        return mThemeColors;
    }

    //debug34
    private MaterialColorMapUtils.MaterialPalette getColorsFromCall(Call call) {
        if (call == null) {
            return getColorsFromPhoneAccountHandle(mPendingPhoneAccountHandle);
        } else {
            return getColorsFromPhoneAccountHandle(call.getAccountHandle());
        }
    }

    //debug35
    private MaterialColorMapUtils.MaterialPalette getColorsFromPhoneAccountHandle(PhoneAccountHandle phoneAccountHandle) {
        int highlightColor = PhoneAccount.NO_HIGHLIGHT_COLOR;
        if (phoneAccountHandle != null) {
            final TelecomManager tm = getTelecomManager();

            if (tm != null) {
                final PhoneAccount account = tm.getPhoneAccount(phoneAccountHandle);
                if (account != null) {
                    highlightColor = account.getHighlightColor();
                }
            }
        }
        return new InCallUIMaterialColorMapUtils(mContext.getResources()).calculatePrimaryAndSecondaryColor(highlightColor);
    }

    //debug12
    public TelecomManager getTelecomManager() {
        if (mTelecomManager == null) {
            mTelecomManager = (TelecomManager) mContext.getSystemService(Context.TELECOM_SERVICE);
        }
        return mTelecomManager;
    }

    public InCallActivity getActivity() {
        return mInCallActivity;
    }

    public IncomingPresenter getAnswerPresenter() {
        return mIncomingPresenter;
    }

    public InCallPresenter() {
    }

    public enum InCallState {
        NO_CALLS, INCOMING, INCALL, WAITING_FOR_ACCOUNT, PENDING_OUTGOING, OUTGOING;

        public boolean isIncoming() {
            return (this == INCOMING);
        }

        public boolean isConnectingOrConnected() {
            return (this == INCOMING || this == OUTGOING || this == INCALL);
        }
    }

    public interface InCallStateListener {
        void onStateChange(InCallState oldState, InCallState newState, CallList callList);
    }

    public interface IncomingCallListener {
        void onIncomingCall(InCallState oldState, InCallState newState, Call call);
    }

    public interface CanAddCallListener {
        void onCanAddCallChanged(boolean canAddCall);
    }

    public interface InCallDetailsListener {
        void onDetailsChanged(Call call, android.telecom.Call.Details details);
    }

    public interface InCallOrientationListener {
        void onDeviceOrientationChanged(int orientation);
    }

    public interface InCallEventListener {
        void onFullscreenModeChanged(boolean isFullscreenMode);
    }

    public interface InCallUiListener {
        void onUiShowing(boolean showing);
    }
}
