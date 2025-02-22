package com.algokelvin.primarydialer.mvicall;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.IBinder;
import android.telecom.Call;
import android.telecom.CallAudioState;
import android.telecom.InCallService;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.algokelvin.primarydialer.mvicall.base.in_call.InCallPresenter;


@RequiresApi(api = Build.VERSION_CODES.M)
public class InCallServiceImpl extends InCallService {
    int mCurrentMusicVolume;
    int mCurrentRingerVolume;

    @Override
    public void onCallAudioStateChanged(CallAudioState audioState) {
        AudioModeProvider.getInstance().onAudioStateChanged(audioState);
    }

    @Override
    public void onBringToForeground(boolean showDialpad) {
        InCallPresenter.getInstance().onBringToForeground(showDialpad);
    }

    @Override
    public void onCallAdded(@NonNull Call call) {
        CallList.getInstance().onCallAdded(call);
        InCallPresenter inCallPresenter = InCallPresenter.getInstance();
        inCallPresenter.onCallAdded(call);
    }

    @Override
    public void onCallRemoved(@NonNull Call call) {

        InCallPresenter inCallPresenter = InCallPresenter.getInstance();
        currentRingMode(true, false);
        CallList.getInstance().onCallRemoved(call);
        InCallPresenter.getInstance().onCallRemoved(call);
    }

    @Override
    public void onCanAddCallChanged(boolean canAddCall) {
        InCallPresenter.getInstance().onCanAddCallChanged(canAddCall);
    }

    @NonNull
    @Override
    public IBinder onBind(@NonNull Intent intent) {
        final Context context = getApplicationContext();
        final ContactInfoCache contactInfoCache = ContactInfoCache.getInstance(context);
        InCallPresenter.getInstance().setUp(
                getApplicationContext(),
                CallList.getInstance(),
                AudioModeProvider.getInstance(),
                new StatusBarNotifier(context, contactInfoCache),
                contactInfoCache,
                new ProximitySensor(context, AudioModeProvider.getInstance(), new AccelerometerListener(context)));
        InCallPresenter.getInstance().onServiceBind();
        InCallPresenter.getInstance().maybeStartRevealAnimation(intent);
        TelecomAdapter.getInstance().setInCallService(this);

        return super.onBind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        super.onUnbind(intent);
        InCallPresenter.getInstance().onServiceUnbind();
        tearDown();

        return false;
    }

    private void tearDown() {
        TelecomAdapter.getInstance().clearInCallService();
        CallList.getInstance().clearOnDisconnect();
        InCallPresenter.getInstance().tearDown();
    }

    /*
     * turn on music volume to play videotone
     * and mute ring phone volume to mute ringtone default phone
     * */
    private void currentRingMode(boolean isTurnOnMusic, boolean isCallAdded){
        try {
            final Context mContext = getApplicationContext();
            AudioManager mAudioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
            int mCurrentRingerMode = mAudioManager.getRingerMode();

            if (isCallAdded) {
                mCurrentMusicVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                mCurrentRingerVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_RING);
            }

            //mAudioManager.setRingerMode(mCurrentRingerMode);
            if (isTurnOnMusic && isCallAdded) {
                //check if phone is silent
                if (mCurrentRingerVolume > 0) {
                    mAudioManager.setStreamVolume(AudioManager.STREAM_RING, 0, 0);
                } else {
                    mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
                }
            }

            // when call ended reset the volume phone
            if (!isCallAdded && mCurrentRingerVolume > 0) {
                mAudioManager.setStreamVolume(AudioManager.STREAM_RING, mCurrentRingerVolume, 0);
                mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mCurrentMusicVolume, 0);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
