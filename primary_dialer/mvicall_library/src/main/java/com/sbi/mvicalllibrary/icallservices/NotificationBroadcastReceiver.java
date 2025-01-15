package com.sbi.mvicalllibrary.icallservices;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telecom.VideoProfile;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class NotificationBroadcastReceiver extends BroadcastReceiver {

    public static final String ACTION_DECLINE_INCOMING_CALL = "com.android.incallui.ACTION_DECLINE_INCOMING_CALL";
    public static final String ACTION_HANG_UP_ONGOING_CALL = "com.android.incallui.ACTION_HANG_UP_ONGOING_CALL";
    public static final String ACTION_ANSWER_VIDEO_INCOMING_CALL = "com.android.incallui.ACTION_ANSWER_VIDEO_INCOMING_CALL";
    public static final String ACTION_ANSWER_VOICE_INCOMING_CALL = "com.android.incallui.ACTION_ANSWER_VOICE_INCOMING_CALL";
    public static final String ACTION_ACCEPT_VIDEO_UPGRADE_REQUEST = "com.android.incallui.ACTION_ACCEPT_VIDEO_UPGRADE_REQUEST";
    public static final String ACTION_DECLINE_VIDEO_UPGRADE_REQUEST = "com.android.incallui.ACTION_DECLINE_VIDEO_UPGRADE_REQUEST";

    // RTC.......
    public static final String ACTION_ANSWER_VOICE_INCOMING_CALL_RTC = "com.android.incallrtc.ACTION_ANSWER_VOICE_INCOMING_CALL";
    public static final String ACTION_DECLINE_INCOMING_CALL_RTC = "com.android.incallrtc.ACTION_DECLINE_INCOMING_CALL";
    
    @Override
    public void onReceive(Context context, Intent intent) {
        if (context == null && intent == null)
            return;

        final String action = intent.getAction();

        switch (action) {
            case ACTION_ANSWER_VIDEO_INCOMING_CALL:
                InCallPresenter.getInstance().answerIncomingCall(context, VideoProfile.STATE_BIDIRECTIONAL);
                break;
            case ACTION_ANSWER_VOICE_INCOMING_CALL:
                InCallPresenter.getInstance().answerIncomingCall(context, VideoProfile.STATE_AUDIO_ONLY);
                break;
            case ACTION_DECLINE_INCOMING_CALL:
                InCallPresenter.getInstance().declineIncomingCall(context);
                break;
            case ACTION_HANG_UP_ONGOING_CALL:
                InCallPresenter.getInstance().hangUpOngoingCall(context);
                break;
            case ACTION_ACCEPT_VIDEO_UPGRADE_REQUEST:
                InCallPresenter.getInstance().acceptUpgradeRequest(VideoProfile.STATE_BIDIRECTIONAL, context);
                break;
            case ACTION_DECLINE_VIDEO_UPGRADE_REQUEST:
                InCallPresenter.getInstance().declineUpgradeRequest(context);
                break;
            case ACTION_ANSWER_VOICE_INCOMING_CALL_RTC: {
                Intent i = new Intent("com.sbi.mvicalllibrary.STATE_INCALL_RTC_CALL");
                i.putExtra("state", "answare");
                LocalBroadcastManager.getInstance(context).sendBroadcast(i);
                break;
            }
            case ACTION_DECLINE_INCOMING_CALL_RTC: {
                Intent i = new Intent("com.sbi.mvicalllibrary.STATE_INCALL_RTC_CALL");
                i.putExtra("state", "decline");
                LocalBroadcastManager.getInstance(context).sendBroadcast(i);
                break;
            }
        }
    }

}