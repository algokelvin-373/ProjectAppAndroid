package com.algokelvin.primarydialer.mvicall.base.in_call;

import android.app.ActionBar;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Point;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Trace;
import android.telecom.DisconnectCause;
import android.telecom.PhoneAccountHandle;
import android.telecom.TelecomManager;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.algokelvin.primarydialer.R;
import com.algokelvin.primarydialer.mvicall.AudioModeProvider;
import com.algokelvin.primarydialer.mvicall.Call;
import com.algokelvin.primarydialer.mvicall.CallButtonFragment;
import com.algokelvin.primarydialer.mvicall.CallList;
import com.algokelvin.primarydialer.mvicall.CircularRevealFragment;
import com.algokelvin.primarydialer.mvicall.ConferenceManagerFragment;
import com.algokelvin.primarydialer.mvicall.DialpadFragment;
import com.algokelvin.primarydialer.mvicall.FragmentDisplayManager;
import com.algokelvin.primarydialer.mvicall.base.incoming.IncomingFragment;
import com.algokelvin.primarydialer.mvicall.ProximitySensor;
import com.algokelvin.primarydialer.mvicall.TelecomAdapter;
import com.algokelvin.primarydialer.mvicall.audio.AudioProcessing;
import com.algokelvin.primarydialer.mvicall.base.call_card.CallCardFragment;
import com.algokelvin.primarydialer.mvicall.common.SelectPhoneAccountDialogFragment;
import com.algokelvin.primarydialer.mvicall.common.TouchPointManager;
import com.algokelvin.primarydialer.mvicall.widgetDialpad.animation.AnimUtils;
import com.algokelvin.primarydialer.mvicall.widgetDialpad.animation.AnimationListenerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class InCallActivity extends AppCompatActivity implements FragmentDisplayManager {
    public static final String TAG = "InCallActivityLogger";

    public static final String SHOW_DIALPAD_EXTRA = "InCallActivity.show_dialpad";
    public static final String DIALPAD_TEXT_EXTRA = "InCallActivity.dialpad_text";
    public static final String NEW_OUTGOING_CALL_EXTRA = "InCallActivity.new_outgoing_call";

    private static final String TAG_DIALPAD_FRAGMENT = "tag_dialpad_fragment";
    private static final String TAG_CONFERENCE_FRAGMENT = "tag_conference_manager_fragment";
    private static final String TAG_CALLCARD_FRAGMENT = "tag_callcard_fragment";
    private static final String TAG_ANSWER_FRAGMENT = "tag_answer_fragment";
    private static final String TAG_SELECT_ACCT_FRAGMENT = "tag_select_acct_fragment";

    private CallButtonFragment mCallButtonFragment;
    private CallCardFragment mCallCardFragment;
    private IncomingFragment mIncomingFragment;
    private DialpadFragment mDialpadFragment;
    private ConferenceManagerFragment mConferenceManagerFragment;
    private FragmentManager mChildFragmentManager;

    private android.telecom.Call currentCall;

    private AudioManager audioManager;

    //private AudioProcessing audioProcessing;
    private TelephonyManager telephonyManager;
    private MyTelephonyCallback telephonyCallback;

    private boolean mIsVisible;
    private AlertDialog mDialog;
    private boolean mShowDialpadRequested;
    private boolean mAnimateDialpadOnShow;
    private String mDtmfText;
    private boolean mShowPostCharWaitDialogOnResume;
    private String mShowPostCharWaitDialogCallId;
    private String mShowPostCharWaitDialogChars;

    private boolean mIsLandscape;
    private Animation mSlideIn;
    private Animation mSlideOut;
    private boolean mDismissKeyguard = false;

    AnimationListenerAdapter mSlideOutListener = new AnimationListenerAdapter() {
        @Override
        public void onAnimationEnd(Animation animation) {
            showFragment(TAG_DIALPAD_FRAGMENT, false, true);
        }
    };

    private SelectPhoneAccountDialogFragment.SelectPhoneAccountListener mSelectAcctListener = new SelectPhoneAccountDialogFragment.SelectPhoneAccountListener() {
        @Override
        public void onPhoneAccountSelected(PhoneAccountHandle selectedAccountHandle, boolean setDefault) {
            InCallPresenter.getInstance().handleAccountSelection(selectedAccountHandle, setDefault);
        }

        @Override
        public void onDialogDismissed() {
            InCallPresenter.getInstance().cancelAccountSelection();
        }
    };

    private OrientationEventListener mOrientationEventListener;
    private static int sPreviousRotation = -1;

    //used
    @RequiresApi(api = Build.VERSION_CODES.S)
    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        Log.i(TAG, "1. onCreate");
        toBeShownOnLockScreen();

        int flags = WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_IGNORE_CHEEK_PRESSES;
        getWindow().addFlags(flags);
        requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);

        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.hide();
        }

        Log.i("MviCallLibrary", "20. I'm Here onCreate");
        setContentView(R.layout.incall_screen);
        internalResolveIntent(getIntent());

        setupAudioManager();
        setupCall();

        mIsLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        final boolean isRtl = TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == View.LAYOUT_DIRECTION_RTL;
        if (mIsLandscape) {
            mSlideIn = AnimationUtils.loadAnimation(this, isRtl ? R.anim.dialpad_slide_in_left : R.anim.dialpad_slide_in_right);
            mSlideOut = AnimationUtils.loadAnimation(this, isRtl ? R.anim.dialpad_slide_out_left : R.anim.dialpad_slide_out_right);
        } else {
            mSlideIn = AnimationUtils.loadAnimation(this, R.anim.dialpad_slide_in_bottom);
            mSlideOut = AnimationUtils.loadAnimation(this, R.anim.dialpad_slide_out_bottom);
        }

        mSlideIn.setInterpolator(AnimUtils.EASE_IN);
        mSlideOut.setInterpolator(AnimUtils.EASE_OUT);

        mSlideOut.setAnimationListener(mSlideOutListener);

        if (icicle != null) {
            mShowDialpadRequested = icicle.getBoolean(SHOW_DIALPAD_EXTRA);
            mAnimateDialpadOnShow = false;
            mDtmfText = icicle.getString(DIALPAD_TEXT_EXTRA);

            SelectPhoneAccountDialogFragment dialogFragment = (SelectPhoneAccountDialogFragment) getFragmentManager().findFragmentByTag(TAG_SELECT_ACCT_FRAGMENT);
            if (dialogFragment != null) {
                dialogFragment.setListener(mSelectAcctListener);
            }
        }

        mOrientationEventListener = new OrientationEventListener(this, SensorManager.SENSOR_DELAY_NORMAL) {
            @Override
            public void onOrientationChanged(int orientation) {

                if (orientation == OrientationEventListener.ORIENTATION_UNKNOWN) {
                    return;
                }

                int newRotation = Surface.ROTATION_0;
                if (orientation >= 337 || orientation <= 23) {
                    newRotation = Surface.ROTATION_0;
                } else if (orientation >= 67 && orientation <= 113) {
                    newRotation = Surface.ROTATION_270;
                } else if (orientation >= 157 && orientation <= 203) {
                    newRotation = Surface.ROTATION_180;
                } else if (orientation >= 247 && orientation <= 293) {
                    newRotation = Surface.ROTATION_90;
                }
                if (newRotation != sPreviousRotation) {
                    doOrientationChanged(newRotation);
                }
            }
        };

        //audioProcessing = new AudioProcessing();

        // Set up PhoneStateListener
        // Set up TelephonyManager and TelephonyCallback
        telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        telephonyCallback = new MyTelephonyCallback();
        telephonyManager.registerTelephonyCallback(getMainExecutor(), telephonyCallback);
    }

    //used
    private void setupAudioManager() {
        Log.i(TAG, "2. setupAudioManager");
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        //audioProcessing = new AudioProcessing();
    }

    //used
    private void setupCall() {
        Log.i(TAG, "3. setupCall");
        String callId = getIntent().getStringExtra("call_id");
        TelecomManager telecomManager = (TelecomManager) getSystemService(Context.TELECOM_SERVICE);

        if (currentCall != null) {
            // Setup call details

            updateCallInfo();

            // Register callback
            currentCall.registerCallback(new android.telecom.Call.Callback() {
                @Override
                public void onStateChanged(android.telecom.Call call, int state) {
                    super.onStateChanged(call, state);
//                    runOnUiThread(() -> handleCallStateChanged(state));
                }
            });

            // Start audio processing
            startAudioProcessing();
        }
    }

    private void updateCallInfo() {
        Log.i(TAG, "4. updateCallInfo");
        Uri handle = currentCall.getDetails().getHandle();
    }

    //debug39
    private void startAudioProcessing() {
        Log.i(TAG, "5. startAudioProcessing");
        try {
            Log.i("START AUDIO PROCESSING", "START AUDIO PROCESSING");
            //System.loadLibrary("native-lib");
            //audioProcessing.startProcessing();
        } catch (UnsatisfiedLinkError e) {
            Log.e("ERROR AUDIO PROCESSING", "ERROR AUDIO PROCESSING");
            e.printStackTrace();
        }
    }

    //used
    private void toBeShownOnLockScreen(){
        Log.i(TAG, "6. toBeShownOnLockScreen ");
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1){
            setTurnScreenOn(true);
            setShowWhenLocked(true);
        }else{
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle out) {
        Log.i(TAG, "7. onSaveInstanceState");
        out.putBoolean(SHOW_DIALPAD_EXTRA, mCallButtonFragment != null && mCallButtonFragment.isDialpadVisible());
        if (mDialpadFragment != null) {
            out.putString(DIALPAD_TEXT_EXTRA, mDialpadFragment.getDtmfText());
        }
        super.onSaveInstanceState(out);
    }

    //used
    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "8. onStart");
        mIsVisible = true;
        InCallPresenter.getInstance().setActivity(this);
        InCallPresenter.getInstance().onActivityStarted();
    }

    //used
    @Override
    protected void onResume() {
        super.onResume();

        Log.i(TAG, "9. onResume");
        InCallPresenter.getInstance().setThemeColors();
        InCallPresenter.getInstance().onUiShowing(true);

        if (mShowDialpadRequested) {
            mCallButtonFragment.displayDialpad(true, mAnimateDialpadOnShow);
            mShowDialpadRequested = false;
            mAnimateDialpadOnShow = false;

            if (mDialpadFragment != null) {
                mDialpadFragment.setDtmfText(mDtmfText);
                mDtmfText = null;
            }
        }

        if (mShowPostCharWaitDialogOnResume) {
            showPostCharWaitDialog(mShowPostCharWaitDialogCallId, mShowPostCharWaitDialogChars);
        }
    }

    //used
    @Override
    protected void onPause() {
        Log.i(TAG, "10. onPause");
        if (mDialpadFragment != null) {
            mDialpadFragment.onDialerKeyUp(null);
        }

        InCallPresenter.getInstance().onUiShowing(false);
        if (isFinishing()) {
            InCallPresenter.getInstance().unsetActivity(this);
        }
        super.onPause();
    }

    //used
    @Override
    protected void onStop() {
        Log.i(TAG, "11. onStop");
        mIsVisible = false;
        InCallPresenter.getInstance().updateIsChangingConfigurations();
        InCallPresenter.getInstance().onActivityStopped();
        super.onStop();
    }

    //used
    @RequiresApi(api = Build.VERSION_CODES.S)
    @Override
    protected void onDestroy() {
        Log.i(TAG, "12. onDestroy");
        InCallPresenter.getInstance().unsetActivity(this);
        InCallPresenter.getInstance().updateIsChangingConfigurations();

        if (telephonyManager != null) {
            telephonyManager.unregisterTelephonyCallback(telephonyCallback);
        }

        super.onDestroy();
    }


    //used
    @Override
    public void onFragmentAttached(Fragment fragment) {
        Log.i(TAG, "13. onFragmentAttached");
        if (fragment instanceof DialpadFragment) {
            mDialpadFragment = (DialpadFragment) fragment;
        } else if (fragment instanceof IncomingFragment) {
            mIncomingFragment = (IncomingFragment) fragment;
        } else if (fragment instanceof CallCardFragment) {
            mCallCardFragment = (CallCardFragment) fragment;
            mChildFragmentManager = mCallCardFragment.getChildFragmentManager();
        } else if (fragment instanceof ConferenceManagerFragment) {
            mConferenceManagerFragment = (ConferenceManagerFragment) fragment;
        } else if (fragment instanceof CallButtonFragment) {
            mCallButtonFragment = (CallButtonFragment) fragment;
        }
    }

    //used
    boolean isVisible() {
        Log.i(TAG, "14. isVisible");
        return mIsVisible;
    }

    //used
    private boolean hasPendingDialogs() {
        Log.i(TAG, "15. hasPendingDialogs");
        return mDialog != null || (mIncomingFragment != null && mIncomingFragment.hasPendingDialogs());
    }

    //used
    @Override
    public void finish() {
        Log.i(TAG, "16. finish");
        if (!hasPendingDialogs()) {
            super.finish();
        }
    }

    //used
    @Override
    public void onBackPressed() {
        Log.i(TAG, "17. onBackPressed");
        if ((mConferenceManagerFragment == null || !mConferenceManagerFragment.isVisible()) && (mCallCardFragment == null || !mCallCardFragment.isVisible())) {
            return;
        }

        if (mDialpadFragment != null && mDialpadFragment.isVisible()) {
            mCallButtonFragment.displayDialpad(false /* show */, true /* animate */);
            return;
        } else if (mConferenceManagerFragment != null && mConferenceManagerFragment.isVisible()) {
            showConferenceFragment(false);
            return;
        }

        final Call call = CallList.getInstance().getIncomingCall();
        if (call != null) {
            return;
        }

        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(TAG, "18. onOptionsItemSelected");
        final int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        Log.i(TAG, "19. onKeyUp");
        if (mDialpadFragment != null && (mDialpadFragment.isVisible()) && (mDialpadFragment.onDialerKeyUp(event))) {
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_CALL) {
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.i(TAG, "20. onKeyDown");
        switch (keyCode) {
            case KeyEvent.KEYCODE_CALL:
                boolean handled = InCallPresenter.getInstance().handleCallKey();
                if (!handled) {
                }
                return true;

            case KeyEvent.KEYCODE_CAMERA:
                return true;

            case KeyEvent.KEYCODE_VOLUME_UP:
            case KeyEvent.KEYCODE_VOLUME_DOWN:
            case KeyEvent.KEYCODE_VOLUME_MUTE:
                break;

            case KeyEvent.KEYCODE_MUTE:
                TelecomAdapter.getInstance().mute(!AudioModeProvider.getInstance().getMute());
                return true;

            case KeyEvent.KEYCODE_SLASH:

                break;
            case KeyEvent.KEYCODE_EQUALS:
                break;
        }

        if (event.getRepeatCount() == 0 && handleDialerKeyDown(keyCode, event)) {
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    private boolean handleDialerKeyDown(int keyCode, KeyEvent event) {
        Log.i(TAG, "21. handleDialerKeyDown");
        if (mDialpadFragment != null && mDialpadFragment.isVisible()) {
            return mDialpadFragment.onDialerKeyDown(event);
        }

        return false;
    }

    private void doOrientationChanged(int rotation) {
        Log.i(TAG, "22. doOrientationChanged");
        if (rotation != sPreviousRotation) {
            sPreviousRotation = rotation;
            InCallPresenter.getInstance().onDeviceRotationChange(rotation);
            InCallPresenter.getInstance().onDeviceOrientationChange(sPreviousRotation);
        }
    }

    //used
    public CallCardFragment getCallCardFragment() {
        Log.i(TAG, "23. getCallCardFragment");
        Log.i("MviCallLibrary", "3. I'm Here getCallCardFragment");
        return mCallCardFragment;
    }

    //used
    private void internalResolveIntent(Intent intent) {
        Log.i(TAG, "24. internalResolveIntent");
        final String action = intent.getAction();
        if (action.equals(Intent.ACTION_MAIN)) {
            if (intent.hasExtra(SHOW_DIALPAD_EXTRA)) {
                final boolean showDialpad = intent.getBooleanExtra(SHOW_DIALPAD_EXTRA, false);
                relaunchedFromDialer(showDialpad);
            }

            boolean newOutgoingCall = false;
            if (intent.getBooleanExtra(NEW_OUTGOING_CALL_EXTRA, false)) {
                intent.removeExtra(NEW_OUTGOING_CALL_EXTRA);
                Call call = CallList.getInstance().getOutgoingCall();
                if (call == null) {
                    call = CallList.getInstance().getPendingOutgoingCall();
                }

                Bundle extras = null;
                if (call != null) {
                    extras = call.getTelecommCall().getDetails().getIntentExtras();
                }
                if (extras == null) {
                    extras = new Bundle();
                }

                Point touchPoint = null;
                if (TouchPointManager.getInstance().hasValidPoint()) {
                    touchPoint = TouchPointManager.getInstance().getPoint();
                } else {
                    if (call != null) {
                        touchPoint = extras.getParcelable(TouchPointManager.TOUCH_POINT);
                    }
                }
                CircularRevealFragment.startCircularReveal(getFragmentManager(), touchPoint, InCallPresenter.getInstance());
                if (InCallPresenter.isCallWithNoValidAccounts(call)) {
                    TelecomAdapter.getInstance().disconnectCall(call.getId());
                }

                dismissKeyguard(true);
                newOutgoingCall = true;
            }

            Call pendingAccountSelectionCall = CallList.getInstance().getWaitingForAccountCall();
            if (pendingAccountSelectionCall != null) {
                showCallCardFragment(false);
                Bundle extras = pendingAccountSelectionCall.getTelecommCall().getDetails().getIntentExtras();

                final List<PhoneAccountHandle> phoneAccountHandles;
                if (extras != null) {
                    phoneAccountHandles = extras.getParcelableArrayList(android.telecom.Call.AVAILABLE_PHONE_ACCOUNTS);
                } else {
                    phoneAccountHandles = new ArrayList<>();
                }

                DialogFragment dialogFragment = SelectPhoneAccountDialogFragment.newInstance(R.string.select_phone_account_for_calls, true, phoneAccountHandles, mSelectAcctListener);
                dialogFragment.show(getFragmentManager(), TAG_SELECT_ACCT_FRAGMENT);
            } else if (!newOutgoingCall) {
                showCallCardFragment(true);
            }

            return;
        }
    }

    private void relaunchedFromDialer(boolean showDialpad) {
        Log.i(TAG, "25. relaunchedFromDialer");
        mShowDialpadRequested = showDialpad;
        mAnimateDialpadOnShow = true;

        if (mShowDialpadRequested) {
            final Call call = CallList.getInstance().getActiveOrBackgroundCall();
            if (call != null && call.getState() == Call.State.ONHOLD) {
                TelecomAdapter.getInstance().unholdCall(call.getId());
            }
        }
    }

    //used
    public void dismissKeyguard(boolean dismiss) {
        Log.i(TAG, "26. dismissKeyguard");
        if (mDismissKeyguard == dismiss) {
            return;
        }
        mDismissKeyguard = dismiss;
        if (dismiss) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        }
    }

    //used
    private void showFragment(String tag, boolean show, boolean executeImmediately) {
        Log.i(TAG, "27. showFragment");
        Trace.beginSection("showFragment - " + tag);
        final FragmentManager fm = getFragmentManagerForTag(tag);

        if (fm == null) {
            return;
        }

        Fragment fragment = fm.findFragmentByTag(tag);
        if (!show && fragment == null) {
            return;
        }

        final FragmentTransaction transaction = fm.beginTransaction();
        if (show) {
            if (fragment == null) {
                fragment = createNewFragmentForTag(tag);
                transaction.add(getContainerIdForFragment(tag), fragment, tag);
            } else {
                transaction.show(fragment);
            }
        } else {
            transaction.hide(fragment);
        }

        transaction.commitAllowingStateLoss();
        if (executeImmediately) {
            fm.executePendingTransactions();
        }
        Trace.endSection();
    }
    //used
    private Fragment createNewFragmentForTag(String tag) {
        Log.i(TAG, "28. createNewFragmentForTag");
        if (TAG_DIALPAD_FRAGMENT.equals(tag)) {
            mDialpadFragment = new DialpadFragment();
            return mDialpadFragment;
        } else if (TAG_ANSWER_FRAGMENT.equals(tag)) {
            mIncomingFragment = new IncomingFragment();
            return mIncomingFragment;
        } else if (TAG_CONFERENCE_FRAGMENT.equals(tag)) {
            mConferenceManagerFragment = new ConferenceManagerFragment();
            return mConferenceManagerFragment;
        } else if (TAG_CALLCARD_FRAGMENT.equals(tag)) {
            mCallCardFragment = new CallCardFragment();
            return mCallCardFragment;
        }
        throw new IllegalStateException("Unexpected fragment: " + tag);
    }
    //used
    private FragmentManager getFragmentManagerForTag(String tag) {
        Log.i(TAG, "29. getFragmentManagerForTag");
        if (TAG_DIALPAD_FRAGMENT.equals(tag)) {
            return mChildFragmentManager;
        } else if (TAG_ANSWER_FRAGMENT.equals(tag)) {
            return mChildFragmentManager;
        } else if (TAG_CONFERENCE_FRAGMENT.equals(tag)) {
            return getFragmentManager();
        } else if (TAG_CALLCARD_FRAGMENT.equals(tag)) {
            return getFragmentManager();
        }
        throw new IllegalStateException("Unexpected fragment: " + tag);
    }

    //used
    private int getContainerIdForFragment(String tag) {
        Log.i(TAG, "30. getContainerIdForFragment");
        if (TAG_DIALPAD_FRAGMENT.equals(tag)) {
            return R.id.answer_and_dialpad_container;
        } else if (TAG_ANSWER_FRAGMENT.equals(tag)) {
            return R.id.answer_and_dialpad_container;
        } else if (TAG_CONFERENCE_FRAGMENT.equals(tag)) {
            return R.id.main;
        } else if (TAG_CALLCARD_FRAGMENT.equals(tag)) {
            return R.id.main;
        }
        throw new IllegalStateException("Unexpected fragment: " + tag);
    }

    public void showDialpadFragment(boolean show, boolean animate) {
        Log.i(TAG, "31. showDialpadFragment");
        if ((show && isDialpadVisible()) || (!show && !isDialpadVisible())) {
            return;
        }

        if (!animate) {
            showFragment(TAG_DIALPAD_FRAGMENT, show, true);
        } else {
            if (show) {
                showFragment(TAG_DIALPAD_FRAGMENT, true, true);
                mDialpadFragment.animateShowDialpad();
            }
            mCallCardFragment.onDialpadVisibilityChange(show);
            mDialpadFragment.getView().startAnimation(show ? mSlideIn : mSlideOut);
        }

        final ProximitySensor sensor = InCallPresenter.getInstance().getProximitySensor();
        if (sensor != null) {
            sensor.onDialpadVisible(show);
        }
    }

    public boolean isDialpadVisible() {
        Log.i(TAG, "32. isDialpadVisible");
        return mDialpadFragment != null && mDialpadFragment.isVisible();
    }

    //used
    public void showCallCardFragment(boolean show) {
        Log.i(TAG, "33. showCallCardFragment");
        showFragment(TAG_CALLCARD_FRAGMENT, show, true);
    }

    public void showConferenceFragment(boolean show) {
        Log.i(TAG, "34. showConferenceFragment");
        showFragment(TAG_CONFERENCE_FRAGMENT, show, true);
        mConferenceManagerFragment.onVisibilityChanged(show);
        mCallCardFragment.getView().setVisibility(show ? View.GONE : View.VISIBLE);
    }

    //used
    public void showAnswerFragment(boolean show) {
        Log.i(TAG, "35. showAnswerFragment");
        showFragment(TAG_ANSWER_FRAGMENT, show, true);
    }

    public void showPostCharWaitDialog(String callId, String chars) {
        Log.i(TAG, "36. showPostCharWaitDialog");
        if (isVisible()) {
            mShowPostCharWaitDialogOnResume = false;
            mShowPostCharWaitDialogCallId = null;
            mShowPostCharWaitDialogChars = null;
        } else {
            mShowPostCharWaitDialogOnResume = true;
            mShowPostCharWaitDialogCallId = callId;
            mShowPostCharWaitDialogChars = chars;
        }
    }

    @Override
    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent event) {
        Log.i(TAG, "37. dispatchPopulateAccessibilityEvent");
        if (mCallCardFragment != null) {
            mCallCardFragment.dispatchPopulateAccessibilityEvent(event);
        }
        return super.dispatchPopulateAccessibilityEvent(event);
    }

    //used
    public void maybeShowErrorDialogOnDisconnect(DisconnectCause disconnectCause) {
        Log.i(TAG, "38. maybeShowErrorDialogOnDisconnect");
        if (!isFinishing() && !TextUtils.isEmpty(disconnectCause.getDescription()) && (disconnectCause.getCode() == DisconnectCause.ERROR || disconnectCause.getCode() == DisconnectCause.RESTRICTED)) {
            showErrorDialog(disconnectCause.getDescription());
        }
    }

    public void dismissPendingDialogs() {
        Log.i(TAG, "39. dismissPendingDialogs");
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
        if (mIncomingFragment != null) {
            mIncomingFragment.dismissPendingDialogs();
        }
    }


    private void showErrorDialog(CharSequence msg) {
        Log.i(TAG, "40. showErrorDialog");
        dismissPendingDialogs();
        mDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        mDialog.show();
    }

    /*private void onDialogDismissed() {
        mDialog = null;
        CallList.getInstance().onErrorDialogDismissed();
        InCallPresenter.getInstance().onDismissDialog();
    }*/

    //used
    public void setExcludeFromRecents(boolean exclude) {
        Log.i(TAG, "41. setExcludeFromRecents");
        try {
            ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
            if (am != null) {
                List<ActivityManager.AppTask> tasks = am.getAppTasks();
                int taskId = getTaskId();
                for (int i = 0; i < tasks.size(); i++) {
                    ActivityManager.AppTask task = tasks.get(i);
                    if (task.getTaskInfo().id == taskId) {
                        try {
                            task.setExcludeFromRecents(exclude);
                        } catch (RuntimeException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //used
    private void stopAudioProcessing() {
        Log.i(TAG, "42. stopAudioProcessing");
        /*if (audioProcessing != null) {
            audioProcessing.stopProcessing();
        }*/
    }

    //used
    @RequiresApi(api = Build.VERSION_CODES.S)
    private class MyTelephonyCallback extends TelephonyCallback implements TelephonyCallback.CallStateListener {
        @Override
        public void onCallStateChanged(int state) {
            Log.i(TAG, "43. MyTelephonyCallback");
            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE:
                    // Call ended or no active calls
                    stopAudioProcessing();
                    onBackPressed();
                    break;

                case TelephonyManager.CALL_STATE_OFFHOOK:
                    // Call answered or outgoing call connected
                    startAudioProcessing();
                    break;

                case TelephonyManager.CALL_STATE_RINGING:
                    // Incoming call ringing
                    // Do nothing
                    break;
            }
        }
    }
}
