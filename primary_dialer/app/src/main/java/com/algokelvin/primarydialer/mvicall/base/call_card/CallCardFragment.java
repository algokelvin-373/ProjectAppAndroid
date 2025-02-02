package com.algokelvin.primarydialer.mvicall.base.call_card;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.Bundle;

import android.os.Handler;
import android.os.Looper;
import android.os.Trace;
import android.telecom.DisconnectCause;
import android.telecom.VideoProfile;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.ViewTreeObserver;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.Toast;

import com.algokelvin.primarydialer.R;
import com.algokelvin.primarydialer.mvicall.BaseFragment;
import com.algokelvin.primarydialer.mvicall.Call;
import com.algokelvin.primarydialer.mvicall.CallUtils;
import com.algokelvin.primarydialer.mvicall.base.in_call.InCallPresenter;
import com.algokelvin.primarydialer.mvicall.base.incoming.IncomingPresenter;
import com.algokelvin.primarydialer.mvicall.common.MaterialColorMapUtils;
import com.algokelvin.primarydialer.mvicall.widgetDialpad.animation.AnimUtils;

import java.util.List;

public class CallCardFragment extends BaseFragment<CallCardPresenter, CallCardPresenter.CallCardUi> implements CallCardPresenter.CallCardUi {
    private static final String TAG = "CallCardFragmentLogger";
    private AudioManager mAudioManager;

    private static int mCurrentRingerVolume;
    private static int mCurrentMusicVolume;
    private int mRequiredVolume = 1;

    private int mMarginStart = 0;
    private int mMarginEnd = 0;

    private static final long CALL_STATE_LABEL_RESET_DELAY_MS = 3000;
    private static final long ACCESSIBILITY_ANNOUNCEMENT_DELAY_MS = 500;

    private AnimatorSet mAnimatorSet;
    private int mShrinkAnimationDuration;
    private int mFabNormalDiameter;
    private int mFabSmallDiameter;
    private boolean mIsLandscape;
    private boolean mIsDialpadShowing;

    private View mPrimaryCallCardContainer;
    private int mFloatingActionButtonVerticalOffset;

    private float mTranslationOffset;
    private Animation mPulseAnimation;

    private int mVideoAnimationDuration;
    private boolean mIsAnimating;

    private MaterialColorMapUtils.MaterialPalette mCurrentThemeColors;
    private CharSequence mPostResetCallStateLabel;
    private boolean mCallStateLabelResetPending = false;
    private Handler mHandler;


    //used
    @Override
    public CallCardPresenter.CallCardUi getUi() {
        Log.i(TAG, "1. getUi");
        return this;
    }

    //used
    @Override
    public CallCardPresenter createPresenter() {
        Log.i(TAG, "2. createPresenter");
        return new CallCardPresenter();
    }

    //used
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(TAG, "3. onCreate");
        mHandler = new Handler(Looper.getMainLooper());
        mShrinkAnimationDuration = getResources().getInteger(R.integer.shrink_animation_duration);
        mVideoAnimationDuration = getResources().getInteger(R.integer.video_animation_duration);
        mFloatingActionButtonVerticalOffset = getResources().getDimensionPixelOffset(R.dimen.floating_action_button_vertical_offset);
        mFabNormalDiameter = getResources().getDimensionPixelOffset(R.dimen.end_call_floating_action_button_diameter);
        mFabSmallDiameter = getResources().getDimensionPixelOffset(R.dimen.end_call_floating_action_button_small_diameter);
    }

    //used
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "4. onActivityCreated");
    }

    //used
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "5. onCreateView");
        Trace.beginSection(TAG + " onCreate");
        mTranslationOffset = getResources().getDimensionPixelSize(R.dimen.call_card_anim_translate_y_offset);
        final View view = inflater.inflate(R.layout.fragment_call_card_modifier, container, false);
        Trace.endSection();
        return view;
    }

    //used
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.i(TAG, "6. onViewCreated");
        mPulseAnimation = AnimationUtils.loadAnimation(view.getContext(), R.anim.dialpad_slide_in_bottom);
        mPrimaryCallCardContainer = view.findViewById(R.id.primary_call_info_container);

        View mCallStateButton = view.findViewById(R.id.callStateButton);
        mCallStateButton.setOnLongClickListener(v -> {
            getPresenter().onCallStateButtonTouched();
            return false;
        });

        mAudioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);

        new IncomingPresenter(new IncomingPresenter.OnInCommingNumber() {
            @Override
            public void onGetNumber(String msisdn) {
            }

            @Override
            public void onAnswareCall() {

            }

            @Override
            public void onDeclineCall() throws Exception {
            }
        });

        ImageButton floatingEndCall = view.findViewById(R.id.floating_end_call);
        floatingEndCall.setOnClickListener(v -> {
            getPresenter().onDecline(getContext());
            onDestroy();
        });
    }
    //used
    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "7. onStart");
    }

    //used
    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "8. onStop");
        try {
            muteRingerMode();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "9. onDestroy");
        try {
            resetCurrentRingerMode();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //used
    private void muteRingerMode() throws SecurityException{
        Log.i(TAG, "10. muteRingerMode");
        if (mCurrentMusicVolume > 0){
            if (mAudioManager != null) {
                mAudioManager.setStreamVolume(AudioManager.STREAM_RING, mCurrentRingerVolume, 0);
                mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
            }
        }
    }

    //used
    private void resetCurrentRingerMode() throws SecurityException{
        Log.i(TAG, "11. resetCurrentRingerMode");
        if (mAudioManager != null) {
            mAudioManager.setStreamVolume(AudioManager.STREAM_RING, mCurrentRingerVolume, 0);
            mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mCurrentMusicVolume, 0);
        }
    }


    @Override
    public void setVisible(boolean on) {
        Log.i(TAG, "12. setVisible");
        if (on) {
            getView().setVisibility(View.VISIBLE);
        } else {
            getView().setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void setProgressSpinnerVisible(boolean visible) {
        Log.i(TAG, "13. setProgressSpinnerVisible");
//        mProgressSpinner.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setCallCardVisible(final boolean visible) {
        Log.i(TAG, "14. setCallCardVisible");
        final boolean isLayoutRtl = InCallPresenter.isRtl();

        final View videoView = getView().findViewById(R.id.incomingVideo);
        if (videoView == null) {
            return;
        }

        final float spaceBesideCallCard = getSpaceBesideCallCard();

        final ViewTreeObserver observer = getView().getViewTreeObserver();
        observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                if (observer.isAlive()) {
                    observer.removeOnPreDrawListener(this);
                }

                float videoViewTranslation = 0f;

                if (!mIsLandscape) {
                    mPrimaryCallCardContainer.setTranslationY(visible ? -mPrimaryCallCardContainer.getHeight() : 0);

                    if (visible) {
                        videoViewTranslation = videoView.getHeight() / 2 - spaceBesideCallCard / 2;
                    }
                }

                ViewPropertyAnimator videoViewAnimator = videoView.animate().setInterpolator(AnimUtils.EASE_OUT_EASE_IN).setDuration(mVideoAnimationDuration);
                if (mIsLandscape) {
                    videoViewAnimator.translationX(videoViewTranslation).start();
                } else {
                    videoViewAnimator.translationY(videoViewTranslation).start();
                }
                videoViewAnimator.start();

                ViewPropertyAnimator callCardAnimator = mPrimaryCallCardContainer.animate().setInterpolator(AnimUtils.EASE_OUT_EASE_IN).setDuration(mVideoAnimationDuration).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        if (!visible) {
                            mPrimaryCallCardContainer.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        if (visible) {
                            mPrimaryCallCardContainer.setVisibility(View.VISIBLE);
                        }
                    }
                });

                if (mIsLandscape) {
                    float translationX = mPrimaryCallCardContainer.getWidth();
                    translationX *= isLayoutRtl ? 1 : -1;
                    callCardAnimator.translationX(visible ? 0 : translationX).start();
                } else {
                    callCardAnimator.translationY(visible ? 0 : -mPrimaryCallCardContainer.getHeight()).start();
                }

                return true;
            }
        });
    }


    public float getSpaceBesideCallCard() {
        Log.i(TAG, "15. getSpaceBesideCallCard");
        if (mIsLandscape) {
            return getView().getWidth() - mPrimaryCallCardContainer.getWidth();
        } else {
            final int callCardHeight;
            if (mPrimaryCallCardContainer.getTag(R.id.view_tag_callcard_actual_height) != null) {
                callCardHeight = (int) mPrimaryCallCardContainer.getTag(R.id.view_tag_callcard_actual_height);
            } else {
                callCardHeight = mPrimaryCallCardContainer.getHeight();
            }
            return getView().getHeight() - callCardHeight;
        }
    }

    @Override
    public void setPrimaryName(String name, boolean nameIsNumber) {
        Log.i(TAG, "16. setPrimaryName");
//        if (TextUtils.isEmpty(name)) {
////            mPrimaryName.setText(null);
//        } else {
//            mPrimaryName.setText(nameIsNumber ? PhoneNumberUtils.createTtsSpannable(name) : name);
//            int nameDirection = View.TEXT_DIRECTION_INHERIT;
//            if (nameIsNumber) {
//                nameDirection = View.TEXT_DIRECTION_LTR;
//                try {
//                    showCallerProfile(name);
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//            mPrimaryName.setTextDirection(nameDirection);
//        }
    }

//    @SuppressLint("CheckResult")
//    private void showCallerProfile(String name){
//        if (!TextUtils.isEmpty(name)) {
//            String msisdn = String.valueOf(PhoneNumberUtils.createTtsSpannable(name));
//            if (TextUtils.isEmpty(msisdn))
//                return;
//        }
//    }

    @Override
    public void setPrimaryImage(Drawable image) {
        Log.i(TAG, "17. setPrimaryImage");
        if (image != null) {
        }
    }

    @Override
    public void setPrimaryPhoneNumber(String number) {
        Log.i(TAG, "18. setPrimaryPhoneNumber");
//        if (TextUtils.isEmpty(number)) {
//            mPhoneNumber.setText(null);
//            mPhoneNumber.setVisibility(View.GONE);
//            mPrimaryMsisdn.setVisibility(View.VISIBLE);
//        } else {
//            mPhoneNumber.setText(PhoneNumberUtils.createTtsSpannable(number));
//            mPhoneNumber.setVisibility(View.VISIBLE);
//            mPhoneNumber.setTextDirection(View.TEXT_DIRECTION_LTR);
//            mPrimaryMsisdn.setText(PhoneNumberUtils.createTtsSpannable(number));
//            mPrimaryMsisdn.setVisibility(View.GONE);
//            mPrimaryMsisdn.setTextDirection(View.TEXT_DIRECTION_LTR);
//        }
    }

    @Override
    public void setPrimaryLabel(String label) {
        Log.i(TAG, "19. setPrimaryLabel");
//        if (!TextUtils.isEmpty(label)) {
//            mNumberLabel.setText(label);
//            mNumberLabel.setVisibility(View.VISIBLE);
//        } else {
//            mNumberLabel.setVisibility(View.GONE);
//        }
    }

    @Override
    public void setPrimary(String number, String name, boolean nameIsNumber, String label, Drawable photo, boolean isSipCall) {
        Log.i(TAG, "20. setPrimary");
        setPrimaryName(name, nameIsNumber);

//        if (TextUtils.isEmpty(number) && TextUtils.isEmpty(label)) {
//            mCallNumberAndLabel.setVisibility(View.GONE);
//            mElapsedTime.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
//        } else {
//            mCallNumberAndLabel.setVisibility(View.VISIBLE);
//            mElapsedTime.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
//        }

        setPrimaryPhoneNumber(number);

        setPrimaryLabel(label);

        showInternetCallLabel(isSipCall);

    }

    @Override
    public void setSecondary(boolean show, String name, boolean nameIsNumber, String label, String providerLabel, boolean isConference, boolean isVideoCall) {
        Log.i(TAG, "21. setSecondary");
//        if (show != mSecondaryCallInfo.isShown()) {
//            updateFabPositionForSecondaryCallInfo();
//        }
//
//        if (show) {
//            boolean hasProvider = !TextUtils.isEmpty(providerLabel);
//            showAndInitializeSecondaryCallInfo(hasProvider);
//
//            mSecondaryCallConferenceCallIcon.setVisibility(isConference ? View.VISIBLE : View.GONE);
//            mSecondaryCallVideoCallIcon.setVisibility(isVideoCall ? View.VISIBLE : View.GONE);
//
//            mSecondaryCallName.setText(nameIsNumber ? PhoneNumberUtils.createTtsSpannable(name) : name);
//            if (hasProvider) {
//                mSecondaryCallProviderLabel.setText(providerLabel);
//            }
//
//            int nameDirection = View.TEXT_DIRECTION_INHERIT;
//            if (nameIsNumber) {
//                nameDirection = View.TEXT_DIRECTION_LTR;
//            }
//            mSecondaryCallName.setTextDirection(nameDirection);
//        } else {
//            mSecondaryCallInfo.setVisibility(View.GONE);
//        }
    }

    @Override
    public void setCallState(int state, int videoState, int sessionModificationState, DisconnectCause disconnectCause, String connectionLabel, Drawable callStateIcon, String gatewayNumber, boolean isWifi, boolean isConference) {
        Log.i(TAG, "22. setCallState");
        boolean isGatewayCall = !TextUtils.isEmpty(gatewayNumber);
        CallStateLabel callStateLabel = getCallStateLabelFromState(state, videoState, sessionModificationState, disconnectCause, connectionLabel, isGatewayCall, isWifi, isConference);

        Log.v(TAG, "setCallState " + callStateLabel.getCallStateLabel());
        Log.v(TAG, "AutoDismiss " + callStateLabel.isAutoDismissing());
        Log.v(TAG, "DisconnectCause " + disconnectCause.toString());
        Log.v(TAG, "gateway " + connectionLabel + gatewayNumber);

//        boolean isSubjectShowing = mCallSubject.getVisibility() == View.VISIBLE;

//        if (TextUtils.equals(callStateLabel.getCallStateLabel(), mCallStateLabel.getText()) && !isSubjectShowing) {
////            if (state == Call.State.ACTIVE || state == Call.State.CONFERENCED) {
////                mCallStateLabel.clearAnimation();
////                mCallStateIcon.clearAnimation();
////            }
////            return;
//        }

//        if (isSubjectShowing) {
//            changeCallStateLabel(null);
//            callStateIcon = null;
//        } else {
//            setCallStateLabel(callStateLabel);
//        }
//
//        if (!TextUtils.isEmpty(callStateLabel.getCallStateLabel())) {
//            if (state == Call.State.ACTIVE || state == Call.State.CONFERENCED) {
//                mCallStateLabel.clearAnimation();
//            } else {
//                mCallStateLabel.startAnimation(mPulseAnimation);
//            }
//        } else {
//            mCallStateLabel.clearAnimation();
//        }
//
//        if (callStateIcon != null) {
//            mCallStateIcon.setVisibility(View.VISIBLE);
//            mCallStateIcon.setAlpha(1.0f);
//            mCallStateIcon.setImageDrawable(callStateIcon);
//
//            if (state == Call.State.ACTIVE || state == Call.State.CONFERENCED || TextUtils.isEmpty(callStateLabel.getCallStateLabel())) {
//                mCallStateIcon.clearAnimation();
//            } else {
//                mCallStateIcon.startAnimation(mPulseAnimation);
//            }
//
//            if (callStateIcon instanceof AnimationDrawable) {
//                ((AnimationDrawable) callStateIcon).start();
//            }
//        } else {
//            mCallStateIcon.clearAnimation();
//
//            mCallStateIcon.setAlpha(0.0f);
//            mCallStateIcon.setVisibility(View.GONE);
//        }
//
//        if (CallUtils.isVideoCall(videoState) || (state == Call.State.ACTIVE && sessionModificationState == Call.SessionModificationState.WAITING_FOR_RESPONSE)) {
//            mCallStateVideoCallIcon.setVisibility(View.VISIBLE);
//        } else {
//            mCallStateVideoCallIcon.setVisibility(View.GONE);
//        }
    }

    /*private void setCallStateLabel(CallStateLabel callStateLabel) {
        Log.v(TAG, "setCallStateLabel : label = " + callStateLabel.getCallStateLabel());

        if (callStateLabel.isAutoDismissing()) {
            mCallStateLabelResetPending = true;
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.v(TAG, "restoringCallStateLabel : label = " + mPostResetCallStateLabel);
                    changeCallStateLabel(mPostResetCallStateLabel);
                    mCallStateLabelResetPending = false;
                }
            }, CALL_STATE_LABEL_RESET_DELAY_MS);

            changeCallStateLabel(callStateLabel.getCallStateLabel());
        } else {
            mPostResetCallStateLabel = callStateLabel.getCallStateLabel();

            if (!mCallStateLabelResetPending) {
                changeCallStateLabel(callStateLabel.getCallStateLabel());
            }
        }
    }*/

    /*private void changeCallStateLabel(CharSequence callStateLabel) {
//        Log.v(this, "changeCallStateLabel : label = " + callStateLabel);
//        if (!TextUtils.isEmpty(callStateLabel)) {
//            mCallStateLabel.setText(callStateLabel);
//            mCallStateLabel.setAlpha(1);
//            mCallStateLabel.setVisibility(View.VISIBLE);
//        } else {
//            Animation callStateLabelAnimation = mCallStateLabel.getAnimation();
//            if (callStateLabelAnimation != null) {
//                callStateLabelAnimation.cancel();
//            }
//            mCallStateLabel.setText(null);
//            mCallStateLabel.setAlpha(0);
//            mCallStateLabel.setVisibility(View.GONE);
//        }
    }*/

    @Override
    public void setCallbackNumber(String callbackNumber, boolean isEmergencyCall) {
        Log.i(TAG, "23. setCallbackNumber");
//        if (mInCallMessageLabel == null) {
//            return;
//        }
//
//        if (TextUtils.isEmpty(callbackNumber)) {
//            mInCallMessageLabel.setVisibility(View.GONE);
//            return;
//        }
//
//        callbackNumber = PhoneNumberUtils.formatNumber(callbackNumber);
//
//        int stringResourceId = isEmergencyCall ? R.string.card_title_callback_number_emergency : R.string.card_title_callback_number;
//
//        String text = getString(stringResourceId, callbackNumber);
//        mInCallMessageLabel.setText(text);
//
//        mInCallMessageLabel.setVisibility(View.VISIBLE);
    }

    @Override
    public void setCallSubject(String callSubject) {
        Log.i(TAG, "24. setCallSubject");
        boolean showSubject = !TextUtils.isEmpty(callSubject);

//        mCallSubject.setVisibility(showSubject ? View.VISIBLE : View.GONE);
//        if (showSubject) {
//            mCallSubject.setText(callSubject);
//        } else {
//            mCallSubject.setText(null);
//        }
    }

    //used
    public boolean isAnimating() {
        Log.i(TAG, "25. isAnimating");
        return mIsAnimating;
    }

    private void showInternetCallLabel(boolean show) {
        Log.i(TAG, "26. showInternetCallLabel");
//        if (show) {
//            final String label = getView().getContext().getString(R.string.incall_call_type_label_sip);
//            mCallTypeLabel.setVisibility(View.VISIBLE);
//            mCallTypeLabel.setText(label);
//        } else {
//            mCallTypeLabel.setVisibility(View.GONE);
//        }
    }

    @Override
    public void setPrimaryCallElapsedTime(boolean show, long duration) {
        Log.i(TAG, "27. setPrimaryCallElapsedTime");
//        if (show) {
//            if (mElapsedTime.getVisibility() != View.VISIBLE) {
//                AnimUtils.fadeIn(mElapsedTime, AnimUtils.DEFAULT_DURATION);
//            }
//            String callTimeElapsed = DateUtils.formatElapsedTime(duration / 1000);
//            mElapsedTime.setText(callTimeElapsed);
//
//            String durationDescription = InCallDateUtils.formatDuration(getView().getContext(), duration);
//            mElapsedTime.setContentDescription(!TextUtils.isEmpty(durationDescription) ? durationDescription : null);
//        } else {
//            AnimUtils.fadeOut(mElapsedTime, AnimUtils.DEFAULT_DURATION);
//        }
    }

    private CallStateLabel getCallStateLabelFromState(int state, int videoState, int sessionModificationState, DisconnectCause disconnectCause, String label, boolean isGatewayCall, boolean isWifi, boolean isConference) {
        Log.i(TAG, "28. getCallStateLabelFromState");
        final Context context = getView().getContext();
        CharSequence callStateLabel = null;

        boolean hasSuggestedLabel = label != null;
        boolean isAccount = hasSuggestedLabel && !isGatewayCall;
        boolean isAutoDismissing = false;

        switch (state) {
            case Call.State.IDLE:
                break;
            case Call.State.ACTIVE:
                if ((isAccount || isWifi || isConference) && hasSuggestedLabel) {
                    callStateLabel = label;
                } else if (sessionModificationState == Call.SessionModificationState.REQUEST_REJECTED) {
                    callStateLabel = context.getString(R.string.card_title_video_call_rejected);
                    isAutoDismissing = true;
                } else if (sessionModificationState == Call.SessionModificationState.REQUEST_FAILED) {
                    callStateLabel = context.getString(R.string.card_title_video_call_error);
                    isAutoDismissing = true;
                } else if (sessionModificationState == Call.SessionModificationState.WAITING_FOR_RESPONSE) {
                    callStateLabel = context.getString(R.string.card_title_video_call_requesting);
                } else if (sessionModificationState == Call.SessionModificationState.RECEIVED_UPGRADE_TO_VIDEO_REQUEST) {
                    callStateLabel = context.getString(R.string.card_title_video_call_requesting);
                } else if (CallUtils.isVideoCall(videoState)) {
                    callStateLabel = context.getString(R.string.card_title_video_call);
                }
                break;
            case Call.State.ONHOLD:
                callStateLabel = context.getString(R.string.card_title_on_hold);
                break;
            case Call.State.CONNECTING:
            case Call.State.DIALING:
                if (hasSuggestedLabel && !isWifi) {
                    callStateLabel = context.getString(R.string.calling_via_template, label);
                } else {
                    callStateLabel = context.getString(R.string.card_title_dialing);
                }
                break;
            case Call.State.REDIALING:
                callStateLabel = context.getString(R.string.card_title_redialing);
                break;
            case Call.State.INCOMING:
            case Call.State.CALL_WAITING:
//                try{mRelaBottomBg.setBackground(getResources().getDrawable(R.drawable.ic_bg_friend_detail));}catch (Exception e){e.printStackTrace();}
                if (isWifi && hasSuggestedLabel) {
                    callStateLabel = label;
                } else if (isAccount) {
                    callStateLabel = context.getString(R.string.incoming_via_template, label);
                } else if (VideoProfile.isTransmissionEnabled(videoState) || VideoProfile.isReceptionEnabled(videoState)) {
                    callStateLabel = context.getString(R.string.notification_incoming_video_call);
                } else {
                    callStateLabel = context.getString(R.string.card_title_incoming_call);
                }
                break;
            case Call.State.DISCONNECTING:
                callStateLabel = context.getString(R.string.card_title_hanging_up);
                break;
            case Call.State.DISCONNECTED:
                callStateLabel = disconnectCause.getLabel();
                if (TextUtils.isEmpty(callStateLabel)) {
                    callStateLabel = context.getString(R.string.card_title_call_ended);
                }
                break;
            case Call.State.CONFERENCED:
                callStateLabel = context.getString(R.string.card_title_conf_call);
                break;
            default:
                Log.wtf(TAG, "updateCallStateWidgets: unexpected call: " + state);
        }
        return new CallStateLabel(callStateLabel, isAutoDismissing);
    }

    /*private void showAndInitializeSecondaryCallInfo(boolean hasProvider) {
//        mSecondaryCallInfo.setVisibility(View.VISIBLE);
//
//        if (mSecondaryCallName == null) {
//            mSecondaryCallName = getView().findViewById(R.id.secondaryCallName);
//            mSecondaryCallConferenceCallIcon = getView().findViewById(R.id.secondaryCallConferenceCallIcon);
//            mSecondaryCallConferenceCallIcon = getView().findViewById(R.id.secondaryCallConferenceCallIcon);
//            mSecondaryCallVideoCallIcon = getView().findViewById(R.id.secondaryCallVideoCallIcon);
//        }
//
//        if (mSecondaryCallProviderLabel == null && hasProvider) {
//            mSecondaryCallProviderInfo.setVisibility(View.VISIBLE);
//            mSecondaryCallProviderLabel = getView().findViewById(R.id.secondaryCallProviderLabel);
//        }
    }*/

    public void dispatchPopulateAccessibilityEvent(AccessibilityEvent event) {
        Log.i(TAG, "29. dispatchPopulateAccessibilityEvent");
//        if (event.getEventType() == AccessibilityEvent.TYPE_ANNOUNCEMENT) {
//            dispatchPopulateAccessibilityEvent(event, mCallStateLabel);
//            dispatchPopulateAccessibilityEvent(event, mPrimaryName);
//            dispatchPopulateAccessibilityEvent(event, mCallTypeLabel);
//            dispatchPopulateAccessibilityEvent(event, mPhoneNumber);
//            return;
//        }
//        dispatchPopulateAccessibilityEvent(event, mCallStateLabel);
//        dispatchPopulateAccessibilityEvent(event, mPrimaryName);
//        dispatchPopulateAccessibilityEvent(event, mPhoneNumber);
//        dispatchPopulateAccessibilityEvent(event, mCallTypeLabel);
//        dispatchPopulateAccessibilityEvent(event, mSecondaryCallName);
//        dispatchPopulateAccessibilityEvent(event, mSecondaryCallProviderLabel);

//        return;
    }

    @Override
    public void sendAccessibilityAnnouncement() {
        Log.i(TAG, "30. sendAccessibilityAnnouncement");
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    if (getView() != null && getView().getParent() != null) {
                        AccessibilityEvent event = AccessibilityEvent.obtain(AccessibilityEvent.TYPE_ANNOUNCEMENT);
                        dispatchPopulateAccessibilityEvent(event);
                        getView().getParent().requestSendAccessibilityEvent(getView(), event);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, ACCESSIBILITY_ANNOUNCEMENT_DELAY_MS);
    }

    @Override
    public void setEndCallButtonEnabled(boolean enabled, boolean animate) {
        Log.i(TAG, "31. setEndCallButtonEnabled");
//        if (enabled != mFloatingActionButton.isEnabled()) {
//            if (animate) {
//                if (enabled) {
//                    mFloatingActionButtonController.scaleIn(AnimUtils.NO_DELAY);
//                } else {
//                    mFloatingActionButtonController.scaleOut();
//                }
//            } else {
//                if (enabled) {
//                    mFloatingActionButtonContainer.setScaleX(1);
//                    mFloatingActionButtonContainer.setScaleY(1);
//                    mFloatingActionButtonContainer.setVisibility(View.VISIBLE);
//                } else {
//                    mFloatingActionButtonContainer.setVisibility(View.GONE);
//                }
//            }
//            mFloatingActionButton.setEnabled(enabled);
//            updateFabPosition();
//        }
    }

    @Override
    public void showHdAudioIndicator(boolean visible) {
        Log.i(TAG, "32. showHdAudioIndicator");
//        mHdAudioIcon.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showForwardIndicator(boolean visible) {
        Log.i(TAG, "33. showForwardIndicator");
//        mForwardIcon.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showManageConferenceCallButton(boolean visible) {
        Log.i(TAG, "34. showManageConferenceCallButton");
//        mManageConferenceCallButton.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    public boolean isManageConferenceVisible() {
        Log.i(TAG, "35. isManageConferenceVisible");
        return false;
//        return mManageConferenceCallButton.getVisibility() == View.VISIBLE;
    }

    @Override
    public boolean isCallSubjectVisible() {
        Log.i(TAG, "36. isCallSubjectVisible");
        return false;
//        return mCallSubject.getVisibility() == View.VISIBLE;
    }

    /*public void updateColors() {
        MaterialColorMapUtils.MaterialPalette themeColors = InCallPresenter.getInstance().getThemeColors();

        if (mCurrentThemeColors != null && mCurrentThemeColors.equals(themeColors)) {
            return;
        }

        if (getResources().getBoolean(R.bool.is_layout_landscape)) {
            final GradientDrawable drawable = (GradientDrawable) mPrimaryCallCardContainer.getBackground();
            drawable.setColor(themeColors.mPrimaryColor);
        } else {
            mPrimaryCallCardContainer.setBackgroundColor(themeColors.mPrimaryColor);
        }
//        mCallButtonsContainer.setBackgroundColor(themeColors.mPrimaryColor);
//        mCallSubject.setTextColor(themeColors.mPrimaryColor);

        mCurrentThemeColors = themeColors;
    }*/

    /*private void dispatchPopulateAccessibilityEvent(AccessibilityEvent event, View view) {
        if (view == null) return;
        final List<CharSequence> eventText = event.getText();
        int size = eventText.size();
        view.dispatchPopulateAccessibilityEvent(event);
        if (size == eventText.size()) {
            eventText.add(null);
        }
    }*/
    //debug59
    @Override
    public void animateForNewOutgoingCall() {
        Log.i(TAG, "37. animateForNewOutgoingCall");
        final ViewGroup parent = (ViewGroup) mPrimaryCallCardContainer.getParent();

        final ViewTreeObserver observer = getView().getViewTreeObserver();

        mIsAnimating = true;

        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                final ViewTreeObserver observer = getView().getViewTreeObserver();
                if (!observer.isAlive()) {
                    return;
                }
                observer.removeOnGlobalLayoutListener(this);

                final CallCardFragment.LayoutIgnoringListener listener = new CallCardFragment.LayoutIgnoringListener();
                mPrimaryCallCardContainer.addOnLayoutChangeListener(listener);

                final int originalHeight = mPrimaryCallCardContainer.getHeight();
                mPrimaryCallCardContainer.setTag(R.id.view_tag_callcard_actual_height, originalHeight);
                mPrimaryCallCardContainer.setBottom(parent.getHeight());

//                mFloatingActionButtonContainer.setVisibility(View.GONE);
//                mFloatingActionButtonController.setScreenWidth(parent.getWidth());

//                mCallButtonsContainer.setAlpha(0);
//                mCallStateLabel.setAlpha(0);
//                mPrimaryName.setAlpha(0);
//                mCallTypeLabel.setAlpha(0);
//                mCallNumberAndLabel.setAlpha(0);

//                assignTranslateAnimation(mCallStateLabel, 1);
//                assignTranslateAnimation(mCallStateIcon, 1);
//                assignTranslateAnimation(mPrimaryName, 2);
//                assignTranslateAnimation(mCallNumberAndLabel, 3);
//                assignTranslateAnimation(mCallTypeLabel, 4);
//                assignTranslateAnimation(mCallButtonsContainer, 5);

                final Animator animator = getShrinkAnimator(parent.getHeight(), originalHeight);

                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mPrimaryCallCardContainer.setTag(R.id.view_tag_callcard_actual_height, null);
                        setViewStatePostAnimation(listener);
                        mIsAnimating = false;
                        InCallPresenter.getInstance().onShrinkAnimationComplete();
                        if (animator != null) {
                            animator.removeListener(this);
                        }
                    }
                });
                animator.start();
            }
        });
    }

    @Override
    public void showNoteSentToast() {
        Log.i(TAG, "38. showNoteSentToast");
        Toast.makeText(getContext(), R.string.note_sent, Toast.LENGTH_LONG).show();
    }

    public void onDialpadVisibilityChange(boolean isShown) {
        Log.i(TAG, "39. onDialpadVisibilityChange");
        mIsDialpadShowing = isShown;
        updateFabPosition();
    }
    //used
    private void updateFabPosition() {
        Log.i(TAG, "40. updateFabPosition");
        int offsetY = 0;
        if (!mIsDialpadShowing) {
            offsetY = mFloatingActionButtonVerticalOffset;
//            if (mSecondaryCallInfo.isShown()) {
//                offsetY -= mSecondaryCallInfo.getHeight();
//            }
        }

//        mFloatingActionButtonController.align(mIsLandscape ? FloatingActionButtonController.ALIGN_QUARTER_END : FloatingActionButtonController.ALIGN_MIDDLE, 0 /* offsetX */, offsetY, true);
//
//        mFloatingActionButtonController.resize(mIsDialpadShowing ? mFabSmallDiameter : mFabNormalDiameter, true);
    }

    //used
    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "41. onResume");
        if (mAnimatorSet != null && mAnimatorSet.isRunning()) {
            mAnimatorSet.cancel();
        }

        mIsLandscape = getResources().getBoolean(R.bool.is_layout_landscape);

        final ViewGroup parent = ((ViewGroup) mPrimaryCallCardContainer.getParent());
        final ViewTreeObserver observer = parent.getViewTreeObserver();
        parent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ViewTreeObserver viewTreeObserver = observer;
                if (!viewTreeObserver.isAlive()) {
                    viewTreeObserver = parent.getViewTreeObserver();
                }
                viewTreeObserver.removeOnGlobalLayoutListener(this);
//                mFloatingActionButtonController.setScreenWidth(parent.getWidth());
                updateFabPosition();
            }
        });
    }

    /*private void updateFabPositionForSecondaryCallInfo() {
//        mSecondaryCallInfo.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                final ViewTreeObserver observer = mSecondaryCallInfo.getViewTreeObserver();
//                if (!observer.isAlive()) {
//                    return;
//                }
//                observer.removeOnGlobalLayoutListener(this);
//                onDialpadVisibilityChange(mIsDialpadShowing);
//            }
//        });
    }*/
    //debug61
    private Animator getShrinkAnimator(int startHeight, int endHeight) {
        Log.i(TAG, "42. getShrinkAnimator");
        final ObjectAnimator shrinkAnimator = ObjectAnimator.ofInt(mPrimaryCallCardContainer, "bottom", startHeight, endHeight);
        shrinkAnimator.setDuration(mShrinkAnimationDuration);
        shrinkAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
//                mFloatingActionButton.setEnabled(true);
            }
        });
        shrinkAnimator.setInterpolator(AnimUtils.EASE_IN);
        return shrinkAnimator;
    }

    /*private void assignTranslateAnimation(View view, int offset) {
        view.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        view.buildLayer();
        view.setTranslationY(mTranslationOffset * offset);
        view.animate().translationY(0).alpha(1).withLayer().setDuration(mShrinkAnimationDuration).setInterpolator(AnimUtils.EASE_IN);
    }

    private void setViewStatePostAnimation(View view) {
        view.setTranslationY(0);
        view.setAlpha(1);
    }*/

    //debug62
    private void setViewStatePostAnimation(View.OnLayoutChangeListener layoutChangeListener) {
        Log.i(TAG, "43. setViewStatePostAnimation");
//        setViewStatePostAnimation(mCallButtonsContainer);
//        setViewStatePostAnimation(mCallStateLabel);
//        setViewStatePostAnimation(mPrimaryName);
//        setViewStatePostAnimation(mCallTypeLabel);
//        setViewStatePostAnimation(mCallNumberAndLabel);
//        setViewStatePostAnimation(mCallStateIcon);

        mPrimaryCallCardContainer.removeOnLayoutChangeListener(layoutChangeListener);

//        mFloatingActionButtonController.scaleIn(AnimUtils.NO_DELAY);
    }

    private final class LayoutIgnoringListener implements View.OnLayoutChangeListener {
        @Override
        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
            v.setLeft(oldLeft);
            v.setRight(oldRight);
            v.setTop(oldTop);
            v.setBottom(oldBottom);
        }
    }

    /*private boolean isSafe() {
        return !(this.isRemoving() || this.getActivity() == null || this.isDetached() || !this.isAdded() || this.getView() == null);
    }*/
}