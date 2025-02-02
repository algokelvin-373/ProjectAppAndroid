package com.algokelvin.primarydialer.mvicall;

import static com.algokelvin.primarydialer.mvicall.NotificationBroadcastReceiver.ACTION_ACCEPT_VIDEO_UPGRADE_REQUEST;
import static com.algokelvin.primarydialer.mvicall.NotificationBroadcastReceiver.ACTION_ANSWER_VIDEO_INCOMING_CALL;
import static com.algokelvin.primarydialer.mvicall.NotificationBroadcastReceiver.ACTION_ANSWER_VOICE_INCOMING_CALL;
import static com.algokelvin.primarydialer.mvicall.NotificationBroadcastReceiver.ACTION_DECLINE_INCOMING_CALL;
import static com.algokelvin.primarydialer.mvicall.NotificationBroadcastReceiver.ACTION_DECLINE_VIDEO_UPGRADE_REQUEST;
import static com.algokelvin.primarydialer.mvicall.NotificationBroadcastReceiver.ACTION_HANG_UP_ONGOING_CALL;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.telecom.Call.Details;
import android.telecom.PhoneAccount;
import android.telecom.TelecomManager;
import android.text.BidiFormatter;
import android.text.TextDirectionHeuristics;
import android.text.TextUtils;

import com.algokelvin.primarydialer.R;
import com.algokelvin.primarydialer.mvicall.base.in_call.InCallPresenter;
import com.algokelvin.primarydialer.mvicall.common.BitmapUtil;
import com.google.common.base.Preconditions;
import com.algokelvin.primarydialer.mvicall.base.in_call.InCallPresenter.InCallState;
import com.algokelvin.primarydialer.mvicall.ContactInfoCache.ContactInfoCacheCallback;
import com.algokelvin.primarydialer.mvicall.ContactInfoCache.ContactCacheEntry;

import java.util.Objects;

/**
 * This class adds Notifications to the status bar for the in-call experience.
 */
public class StatusBarNotifier implements InCallPresenter.InCallStateListener, CallList.CallUpdateListener {

    private static final int NOTIFICATION_NONE = 0;
    private static final int NOTIFICATION_IN_CALL = 1;
    private static final int NOTIFICATION_INCOMING_CALL = 2;

    private final Context mContext;
    private final ContactInfoCache mContactInfoCache;
    private final NotificationManager mNotificationManager;
    private int mCurrentNotification = NOTIFICATION_NONE;
    private int mCallState = Call.State.INVALID;
    private int mSavedIcon = 0;
    private String mSavedContent = null;
    private Bitmap mSavedLargeIcon;
    private String mSavedContentTitle;
    private String mCallId = null;
    private InCallPresenter.InCallState mInCallState;

    public StatusBarNotifier(Context context, ContactInfoCache contactInfoCache) {
        Preconditions.checkNotNull(context);
        mContext = context;
        mContactInfoCache = contactInfoCache;
        mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        mCurrentNotification = NOTIFICATION_NONE;
    }

    /**
     * Creates notifications according to the state we receive from {@link InCallPresenter}.
     */
    @Override
    public void onStateChange(InCallState oldState, InCallState newState, CallList callList) {
        mInCallState = newState;
        updateNotification(newState, callList);
    }

    public void updateNotification(InCallState state, CallList callList) {
        updateInCallNotification(state, callList);
    }

    private void cancelNotification() {
        if (!TextUtils.isEmpty(mCallId)) {
            CallList.getInstance().removeCallUpdateListener(mCallId, this);
            mCallId = null;
        }
        if (mCurrentNotification != NOTIFICATION_NONE) {
            mNotificationManager.cancel(mCurrentNotification);
        }
        mCurrentNotification = NOTIFICATION_NONE;
    }

    public static void clearAllCallNotifications(Context backupContext) {
        NotificationManager notificationManager = (NotificationManager) backupContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(NOTIFICATION_IN_CALL);
        notificationManager.cancel(NOTIFICATION_INCOMING_CALL);
    }

    private void updateInCallNotification(final InCallState state, CallList callList) {
        final Call call = getCallToShow(callList);
        if (call != null) {
            showNotification(call);
        } else {
            cancelNotification();
        }
    }

    private void showNotification(final Call call) {
        final boolean isIncoming = (call.getState() == Call.State.INCOMING || call.getState() == Call.State.CALL_WAITING);
        if (!TextUtils.isEmpty(mCallId)) {
            CallList.getInstance().removeCallUpdateListener(mCallId, this);
        }
        mCallId = call.getId();
        CallList.getInstance().addCallUpdateListener(call.getId(), this);
        mContactInfoCache.findInfo(call, isIncoming, new ContactInfoCacheCallback() {
            @Override
            public void onContactInfoComplete(String callId, ContactCacheEntry entry) {
                Call call = CallList.getInstance().getCallById(callId);
                if (call != null) {
                    buildAndSendNotification(call, entry);
                }
            }

            @Override
            public void onImageLoadComplete(String callId, ContactCacheEntry entry) {
                Call call = CallList.getInstance().getCallById(callId);
                if (call != null) {
                    buildAndSendNotification(call, entry);
                }
            }
        });
    }

    private void buildAndSendNotification(Call originalCall, ContactCacheEntry contactInfo) {

        final Call call = getCallToShow(CallList.getInstance());
        if (call == null || !call.getId().equals(originalCall.getId())) {
            return;
        }

        final int state = call.getState();
        final int iconResId = getIconToDisplay(call);
        Bitmap largeIcon = getLargeIconToDisplay(contactInfo, call);
        final String content = getContentString(call);
        final String contentTitle = getContentTitle(contactInfo, call);

        final int notificationType;
        if ((state == Call.State.INCOMING || state == Call.State.CALL_WAITING) && !InCallPresenter.getInstance().isShowingInCallUi()) {
            notificationType = NOTIFICATION_INCOMING_CALL;
        } else {
            notificationType = NOTIFICATION_IN_CALL;
        }

        if (!checkForChangeAndSaveData(iconResId, content, largeIcon, contentTitle, state, notificationType)) {
            return;
        }

        if (largeIcon != null) {
            largeIcon = getRoundedIcon(largeIcon);
        }

        final Notification.Builder builder = getNotificationBuilder();
        final PendingIntent inCallPendingIntent = createLaunchPendingIntent();
        builder.setContentIntent(inCallPendingIntent);

        if (notificationType == NOTIFICATION_INCOMING_CALL) {
            configureFullScreenIntent(builder, inCallPendingIntent, call);
            builder.setCategory(Notification.CATEGORY_CALL);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel("", "mvicall_notif", importance);
            builder.setAutoCancel(false);
            builder.setContentText(content);
            builder.setSmallIcon(iconResId);
            builder.setContentTitle(contentTitle);
            builder.setLargeIcon(largeIcon);
            builder.setChannelId("");
            builder.setColor(mContext.getResources().getColor(R.color.dialer_theme_color));
            mNotificationManager.createNotificationChannel(mChannel);
        }else {
            builder.setContentText(content);
            builder.setSmallIcon(iconResId);
            builder.setContentTitle(contentTitle);
            builder.setLargeIcon(largeIcon);
            builder.setColor(mContext.getResources().getColor(R.color.dialer_theme_color));
        }

//        builder.setContentText(content);
//        builder.setSmallIcon(iconResId);
//        builder.setContentTitle(contentTitle);
//        builder.setLargeIcon(largeIcon);
//        builder.setColor(mContext.getResources().getColor(R.color.dialer_theme_color));

        final boolean isVideoUpgradeRequest = call.getSessionModificationState() == Call.SessionModificationState.RECEIVED_UPGRADE_TO_VIDEO_REQUEST;
        if (isVideoUpgradeRequest) {
            builder.setUsesChronometer(false);
            addDismissUpgradeRequestAction(builder);
            addAcceptUpgradeRequestAction(builder);
        } else {
            createIncomingCallNotification(call, state, builder);
        }

        addPersonReference(builder, contactInfo, call);

        /*
         * Fire off the notification
         */
        Notification notification = builder.build();
        if (mCurrentNotification != notificationType) {
            mNotificationManager.cancel(mCurrentNotification);
        }
        mNotificationManager.notify(notificationType, notification);
        mCurrentNotification = notificationType;
    }

    private void createIncomingCallNotification(Call call, int state, Notification.Builder builder) {
        if (state == Call.State.ACTIVE) {
            builder.setUsesChronometer(true);
            builder.setWhen(call.getConnectTimeMillis());
        } else {
            builder.setUsesChronometer(false);
        }

        if (state == Call.State.ACTIVE || state == Call.State.ONHOLD || Call.State.isDialing(state)) {
            addHangupAction(builder);
        } else if (state == Call.State.INCOMING || state == Call.State.CALL_WAITING) {
            addDismissAction(builder);
            if (call.isVideoCall(mContext)) {
                addVoiceAction(builder);
                addVideoCallAction(builder);
            } else {
                addAnswerAction(builder);
            }
        }
    }

    private boolean checkForChangeAndSaveData(int icon, String content, Bitmap largeIcon, String contentTitle, int state, int notificationType) {

        final boolean contentTitleChanged = (contentTitle != null && !contentTitle.equals(mSavedContentTitle)) || (contentTitle == null && mSavedContentTitle != null);
        boolean retval = (mSavedIcon != icon) || !Objects.equals(mSavedContent, content) || (mCallState != state) || (mSavedLargeIcon != largeIcon) || contentTitleChanged;

        if (mCurrentNotification != notificationType) {
            if (mCurrentNotification == NOTIFICATION_NONE) {
            }
            retval = true;
        }

        mSavedIcon = icon;
        mSavedContent = content;
        mCallState = state;
        mSavedLargeIcon = largeIcon;
        mSavedContentTitle = contentTitle;

        if (retval) {
        }

        return retval;
    }

    private String getContentTitle(ContactCacheEntry contactInfo, Call call) {
        if (call.isConferenceCall() && !call.hasProperty(Details.PROPERTY_GENERIC_CONFERENCE)) {
            return mContext.getResources().getString(R.string.card_title_conf_call);
        }
        if (TextUtils.isEmpty(contactInfo.name)) {
            return TextUtils.isEmpty(contactInfo.number) ? null : BidiFormatter.getInstance().unicodeWrap(contactInfo.number, TextDirectionHeuristics.LTR);
        }

        return contactInfo.name;
    }

    private void addPersonReference(Notification.Builder builder, ContactCacheEntry contactInfo, Call call) {
        if (contactInfo.lookupUri != null) {
            builder.addPerson(contactInfo.lookupUri.toString());
        } else if (!TextUtils.isEmpty(call.getNumber())) {
            builder.addPerson(Uri.fromParts(PhoneAccount.SCHEME_TEL, call.getNumber(), null).toString());
        }
    }

    private Bitmap getLargeIconToDisplay(ContactCacheEntry contactInfo, Call call) {
        Bitmap largeIcon = null;
        if (call.isConferenceCall() && !call.hasProperty(Details.PROPERTY_GENERIC_CONFERENCE)) {
            largeIcon = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.img_conference);
        }
        if (contactInfo.photo != null && (contactInfo.photo instanceof BitmapDrawable)) {
            largeIcon = ((BitmapDrawable) contactInfo.photo).getBitmap();
        }
        return largeIcon;
    }

    private Bitmap getRoundedIcon(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        final int height = (int) mContext.getResources().getDimension(android.R.dimen.notification_large_icon_height);
        final int width = (int) mContext.getResources().getDimension(android.R.dimen.notification_large_icon_width);
        return BitmapUtil.getRoundedBitmap(bitmap, width, height);
    }

    /**
     * Returns the appropriate icon res Id to display based on the call for which
     * we want to display information.
     */
    private int getIconToDisplay(Call call) {
        if (call.getState() == Call.State.ONHOLD) {
            return R.drawable.ic_phone_paused_white_24dp;
        } else if (call.getSessionModificationState() == Call.SessionModificationState.RECEIVED_UPGRADE_TO_VIDEO_REQUEST) {
            return R.drawable.ic_play_arrow;
        }
        return R.drawable.ic_call_white_24dp;
    }

    private String getContentString(Call call) {
        boolean isIncomingOrWaiting = call.getState() == Call.State.INCOMING || call.getState() == Call.State.CALL_WAITING;
        if (isIncomingOrWaiting && call.getNumberPresentation() == TelecomManager.PRESENTATION_ALLOWED) {
            if (!TextUtils.isEmpty(call.getChildNumber())) {
                return mContext.getString(R.string.child_number, call.getChildNumber());
            } else if (!TextUtils.isEmpty(call.getCallSubject()) && call.isCallSubjectSupported()) {
                return call.getCallSubject();
            }
        }

        int resId = R.string.notification_ongoing_call;
        if (call.hasProperty(Details.PROPERTY_WIFI)) {
            resId = R.string.notification_ongoing_call_wifi;
        }

        if (isIncomingOrWaiting) {
            if (call.hasProperty(Details.PROPERTY_WIFI)) {
                resId = R.string.notification_incoming_call_wifi;
            } else {
                resId = R.string.notification_incoming_call;
            }
        } else if (call.getState() == Call.State.ONHOLD) {
            resId = R.string.notification_on_hold;
        } else if (Call.State.isDialing(call.getState())) {
            resId = R.string.notification_dialing;
        } else if (call.getSessionModificationState() == Call.SessionModificationState.RECEIVED_UPGRADE_TO_VIDEO_REQUEST) {
            resId = R.string.notification_requesting_video_call;
        }

        return mContext.getString(resId);
    }

    private Call getCallToShow(CallList callList) {
        if (callList == null) {
            return null;
        }
        Call call = callList.getIncomingCall();
        if (call == null) {
            call = callList.getOutgoingCall();
        }
        if (call == null) {
            call = callList.getVideoUpgradeRequestCall();
        }
        if (call == null) {
            call = callList.getActiveOrBackgroundCall();
        }
        return call;
    }

    private void addAnswerAction(Notification.Builder builder) {
        PendingIntent answerVoicePendingIntent = createNotificationPendingIntent(mContext, ACTION_ANSWER_VOICE_INCOMING_CALL);
        builder.addAction(R.drawable.ic_call_white_24dp, mContext.getText(R.string.notification_action_answer), answerVoicePendingIntent);
    }

    private void addDismissAction(Notification.Builder builder) {
        PendingIntent declinePendingIntent = createNotificationPendingIntent(mContext, ACTION_DECLINE_INCOMING_CALL);
        builder.addAction(R.drawable.ic_close, mContext.getText(R.string.notification_action_dismiss), declinePendingIntent);
    }

    private void addHangupAction(Notification.Builder builder) {
        PendingIntent hangupPendingIntent = createNotificationPendingIntent(mContext, ACTION_HANG_UP_ONGOING_CALL);
        builder.addAction(R.drawable.ic_call_end_white_24dp, mContext.getText(R.string.notification_action_end_call), hangupPendingIntent);
    }

    private void addVideoCallAction(Notification.Builder builder) {
        PendingIntent answerVideoPendingIntent = createNotificationPendingIntent(mContext, ACTION_ANSWER_VIDEO_INCOMING_CALL);
        builder.addAction(R.drawable.ic_play_arrow, mContext.getText(R.string.notification_action_answer_video), answerVideoPendingIntent);
    }

    private void addVoiceAction(Notification.Builder builder) {
        PendingIntent answerVoicePendingIntent = createNotificationPendingIntent(mContext, ACTION_ANSWER_VOICE_INCOMING_CALL);
        builder.addAction(R.drawable.ic_call_white_24dp, mContext.getText(R.string.notification_action_answer_voice), answerVoicePendingIntent);
    }

    private void addAcceptUpgradeRequestAction(Notification.Builder builder) {
        PendingIntent acceptVideoPendingIntent = createNotificationPendingIntent(mContext, ACTION_ACCEPT_VIDEO_UPGRADE_REQUEST);
        builder.addAction(0, mContext.getText(R.string.notification_action_accept), acceptVideoPendingIntent);
    }

    private void addDismissUpgradeRequestAction(Notification.Builder builder) {
        PendingIntent declineVideoPendingIntent = createNotificationPendingIntent(mContext, ACTION_DECLINE_VIDEO_UPGRADE_REQUEST);
        builder.addAction(0, mContext.getText(R.string.notification_action_dismiss), declineVideoPendingIntent);
    }

    private void configureFullScreenIntent(Notification.Builder builder, PendingIntent intent, Call call) {
        builder.setFullScreenIntent(intent, true);
        boolean isCallWaiting = (call.getState() == Call.State.CALL_WAITING || (call.getState() == Call.State.INCOMING && CallList.getInstance().getBackgroundCall() != null));
        if (isCallWaiting) {
            mNotificationManager.cancel(NOTIFICATION_IN_CALL);
        }
    }

    private Notification.Builder getNotificationBuilder() {
        final Notification.Builder builder = new Notification.Builder(mContext);
        builder.setOngoing(true);
        builder.setPriority(Notification.PRIORITY_HIGH);
        return builder;
    }

    private PendingIntent createLaunchPendingIntent() {
        final Intent intent = InCallPresenter.getInstance().getInCallIntent(false, false);
        PendingIntent inCallPendingIntent = PendingIntent.getActivity(mContext, 0, intent, 0);
        return inCallPendingIntent;
    }

    private static PendingIntent createNotificationPendingIntent(Context context, String action) {
        final Intent intent = new Intent(action, null, context, NotificationBroadcastReceiver.class);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }

    @Override
    public void onCallChanged(Call call) {

    }

    @Override
    public void onSessionModificationStateChange(int sessionModificationState) {
        if (sessionModificationState == Call.SessionModificationState.NO_REQUEST) {
            if (mCallId != null) {
                CallList.getInstance().removeCallUpdateListener(mCallId, this);
            }
            updateNotification(mInCallState, CallList.getInstance());
        }
    }

    @Override
    public void onLastForwardedNumberChange() {

    }

    @Override
    public void onChildNumberChange() {

    }
}
