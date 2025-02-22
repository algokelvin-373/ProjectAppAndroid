package com.algokelvin.primarydialer.mvicall.base.incoming;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.telecom.VideoProfile;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.algokelvin.primarydialer.R;
import com.algokelvin.primarydialer.mvicall.BaseFragment;
import com.algokelvin.primarydialer.mvicall.base.in_call.InCallPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class IncomingFragment extends BaseFragment<IncomingPresenter, IncomingPresenter.AnswerUi> implements IncomingPresenter.AnswerUi{
    public final String TAG = "IncomingFragmentLogger";
    public static final int TARGET_SET_FOR_AUDIO_WITHOUT_SMS = 0;
    public static final int TARGET_SET_FOR_AUDIO_WITH_SMS = 1;
    public static final int TARGET_SET_FOR_VIDEO_WITHOUT_SMS = 2;
    public static final int TARGET_SET_FOR_VIDEO_WITH_SMS = 3;
    public static final int TARGET_SET_FOR_VIDEO_ACCEPT_REJECT_REQUEST = 4;
    private Dialog mCannedResponsePopup = null;
    private AlertDialog mCustomMessagePopup = null;
    private ArrayAdapter<String> mSmsResponsesAdapter;
    private final List<String> mSmsResponses = new ArrayList<>();

    public IncomingFragment() {
    }

    //used
    @Override
    public IncomingPresenter createPresenter() {
        Log.i(TAG, "1. createPresenter");
        return InCallPresenter.getInstance().getAnswerPresenter();
    }

    //used
    @Override
    public IncomingPresenter.AnswerUi getUi() {
        Log.i(TAG, "2. getUi");
        return this;
    }

    //used
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "3. onCreateView");
        final View view = inflater.inflate(R.layout.incoming_new_design, container, false);

        ImageButton mFloatingAnswerButton = view.findViewById(R.id.floating_answer_call);
        ImageButton mFloatingRejectButton = view.findViewById(R.id.floating_end_call);

        mFloatingAnswerButton.setOnClickListener(view1 -> getPresenter().onAnswer(VideoProfile.STATE_AUDIO_ONLY, getContext()));
        mFloatingRejectButton.setOnClickListener(view12 -> {
            getPresenter().onDecline(getContext());
            getActivity().finish();
        });

        return view;
    }

    //used
    @Override
    public void onShowAnswerUi(boolean shown) {
        Log.i(TAG, "4. onShowAnswerUi");
    }

    //used
    public void showTargets(int targetSet) {
        Log.i(TAG, "5. showTargets");
        showTargets(targetSet, VideoProfile.STATE_BIDIRECTIONAL);
    }

    //used
    @Override
    public void showTargets(int targetSet, int videoState) {
        Log.i(TAG, "6. showTargets");
        final int targetResourceId;
        final int targetDescriptionsResourceId;
        final int directionDescriptionsResourceId;
        final int handleDrawableResourceId;

        switch (targetSet) {
            case TARGET_SET_FOR_AUDIO_WITH_SMS:
                targetResourceId = R.array.incoming_call_widget_audio_with_sms_targets;
                targetDescriptionsResourceId = R.array.incoming_call_widget_audio_with_sms_target_descriptions;
                directionDescriptionsResourceId = R.array.incoming_call_widget_audio_with_sms_direction_descriptions;
                handleDrawableResourceId = R.drawable.ic_incall_audio_handle;
                break;
            case TARGET_SET_FOR_VIDEO_WITHOUT_SMS:
                targetResourceId = R.array.incoming_call_widget_video_without_sms_targets;
                targetDescriptionsResourceId = R.array.incoming_call_widget_video_without_sms_target_descriptions;
                directionDescriptionsResourceId = R.array.incoming_call_widget_video_without_sms_direction_descriptions;
                handleDrawableResourceId = R.drawable.ic_incall_video_handle;
                break;
            case TARGET_SET_FOR_VIDEO_WITH_SMS:
                targetResourceId = R.array.incoming_call_widget_video_with_sms_targets;
                targetDescriptionsResourceId = R.array.incoming_call_widget_video_with_sms_target_descriptions;
                directionDescriptionsResourceId = R.array.incoming_call_widget_video_with_sms_direction_descriptions;
                handleDrawableResourceId = R.drawable.ic_incall_video_handle;
                break;
            case TARGET_SET_FOR_VIDEO_ACCEPT_REJECT_REQUEST:
                targetResourceId = R.array.incoming_call_widget_video_request_targets;
                targetDescriptionsResourceId = R.array.incoming_call_widget_video_request_target_descriptions;
                directionDescriptionsResourceId = R.array.incoming_call_widget_video_request_target_direction_descriptions;
                handleDrawableResourceId = R.drawable.ic_incall_video_handle;
                break;
            case TARGET_SET_FOR_AUDIO_WITHOUT_SMS:
            default:
                targetResourceId = R.array.incoming_call_widget_audio_without_sms_targets;
                targetDescriptionsResourceId = R.array.incoming_call_widget_audio_without_sms_target_descriptions;
                directionDescriptionsResourceId = R.array.incoming_call_widget_audio_without_sms_direction_descriptions;
                handleDrawableResourceId = R.drawable.ic_incall_audio_handle;
                break;
        }

    }

    @Override
    public void showMessageDialog() {
        Log.i(TAG, "7. showMessageDialog");
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        mSmsResponsesAdapter = new ArrayAdapter<>(builder.getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, mSmsResponses);

        final ListView lv = new ListView(getActivity());
        lv.setAdapter(mSmsResponsesAdapter);
        lv.setOnItemClickListener(new RespondViaSmsItemClickListener());

        builder.setCancelable(true).setView(lv).setOnCancelListener(dialogInterface -> {
            dismissCannedResponsePopup();
            getPresenter().onDismissDialog();
        });
        mCannedResponsePopup = builder.create();
        Objects.requireNonNull(mCannedResponsePopup.getWindow()).addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        mCannedResponsePopup.show();
    }

    private boolean isCannedResponsePopupShowing() {
        Log.i(TAG, "8. isCannedResponsePopupShowing");
        if (mCannedResponsePopup != null) {
            return mCannedResponsePopup.isShowing();
        }
        return false;
    }

    private boolean isCustomMessagePopupShowing() {
        Log.i(TAG, "9. isCustomMessagePopupShowing");
        if (mCustomMessagePopup != null) {
            return mCustomMessagePopup.isShowing();
        }
        return false;
    }

    private void dismissCannedResponsePopup() {
        Log.i(TAG, "10. dismissCannedResponsePopup");
        if (mCannedResponsePopup != null) {
            mCannedResponsePopup.dismiss();
            mCannedResponsePopup = null;
        }
    }

    private void dismissCustomMessagePopup() {
        Log.i(TAG, "11. dismissCustomMessagePopup");
        if (mCustomMessagePopup != null) {
            mCustomMessagePopup.dismiss();
            mCustomMessagePopup = null;
        }
    }

    public void dismissPendingDialogs() {
        Log.i(TAG, "12. dismissPendingDialogs");
        if (isCannedResponsePopupShowing()) {
            dismissCannedResponsePopup();
        }

        if (isCustomMessagePopupShowing()) {
            dismissCustomMessagePopup();
        }
    }

    //used
    public boolean hasPendingDialogs() {
        Log.i(TAG, "13. hasPendingDialogs");
        return !(mCannedResponsePopup == null && mCustomMessagePopup == null);
    }


    public void showCustomMessageDialog() {
        Log.i(TAG, "14. showCustomMessageDialog");
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final EditText et = new EditText(builder.getContext());
        builder.setCancelable(true).setView(et).setPositiveButton(R.string.custom_message_send, (dialog, which) -> {
            final String textMessage = et.getText().toString().trim();
            dismissCustomMessagePopup();
            getPresenter().rejectCallWithMessage(textMessage);
        }).setNegativeButton(R.string.custom_message_cancel, (dialog, which) -> {
            dismissCustomMessagePopup();
            getPresenter().onDismissDialog();
        }).setOnCancelListener(dialogInterface -> {
            dismissCustomMessagePopup();
            getPresenter().onDismissDialog();
        }).setTitle(R.string.respond_via_sms_custom_message);
        mCustomMessagePopup = builder.create();

        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                final Button sendButton = mCustomMessagePopup.getButton(DialogInterface.BUTTON_POSITIVE);
                sendButton.setEnabled(s != null && s.toString().trim().length() != 0);
            }
        });

        Objects.requireNonNull(mCustomMessagePopup.getWindow()).setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        mCustomMessagePopup.getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        mCustomMessagePopup.show();

        final Button sendButton = mCustomMessagePopup.getButton(DialogInterface.BUTTON_POSITIVE);
        sendButton.setEnabled(false);
    }

    //used
    @Override
    public void configureMessageDialog(List<String> textResponses) {
        Log.i(TAG, "15. configureMessageDialog");
        mSmsResponses.clear();
        mSmsResponses.addAll(textResponses);
        mSmsResponses.add(getResources().getString(R.string.respond_via_sms_custom_message));
        if (mSmsResponsesAdapter != null) {
            mSmsResponsesAdapter.notifyDataSetChanged();
        }
    }

    //used
    @Override
    public Context getContext() {
        Log.i(TAG, "16. getContext");
        return getActivity();
    }

    public class RespondViaSmsItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.i(TAG, "17. onItemClick");
            final String message = (String) parent.getItemAtPosition(position);
            dismissCannedResponsePopup();
            if (position == (parent.getCount() - 1)) {
                showCustomMessageDialog();
            } else {
                getPresenter().rejectCallWithMessage(message);
            }
        }
    }
}
