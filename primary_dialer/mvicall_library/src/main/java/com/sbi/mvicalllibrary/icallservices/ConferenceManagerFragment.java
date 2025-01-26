package com.sbi.mvicalllibrary.icallservices;

import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.sbi.mvicalllibrary.R;
import com.sbi.mvicalllibrary.icallservices.common.ContactPhotoManager;

import java.util.List;

/**
 * Fragment that allows the user to manage a conference call.
 */
public class ConferenceManagerFragment extends BaseFragment<ConferenceManagerPresenter, ConferenceManagerPresenter.ConferenceManagerUi> implements ConferenceManagerPresenter.ConferenceManagerUi {

    private static final String KEY_IS_VISIBLE = "key_conference_is_visible";

    private ListView mConferenceParticipantList;
    private int mActionBarElevation;
    private ContactPhotoManager mContactPhotoManager;
    private LayoutInflater mInflater;
    private ConferenceParticipantListAdapter mConferenceParticipantListAdapter;
    private boolean mIsVisible;
    private boolean mIsRecreating;

    @Override
    public ConferenceManagerPresenter createPresenter() {
        return new ConferenceManagerPresenter();
    }

    @Override
    public ConferenceManagerPresenter.ConferenceManagerUi getUi() {
        return this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mIsRecreating = true;
            mIsVisible = savedInstanceState.getBoolean(KEY_IS_VISIBLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View parent = inflater.inflate(R.layout.conference_manager_fragment, container, false);

        mConferenceParticipantList = parent.findViewById(R.id.participantList);
        mContactPhotoManager = ContactPhotoManager.getInstance(getActivity().getApplicationContext());
        mActionBarElevation = (int) getResources().getDimension(R.dimen.incall_action_bar_elevation);
        mInflater = LayoutInflater.from(getActivity().getApplicationContext());

        return parent;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mIsRecreating) {
            onVisibilityChanged(mIsVisible);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(KEY_IS_VISIBLE, mIsVisible);
        super.onSaveInstanceState(outState);
    }

    public void onVisibilityChanged(boolean isVisible) {
        mIsVisible = isVisible;
        ActionBar actionBar = getActivity().getActionBar();
        if (actionBar != null) {
            if (isVisible) {
                String strTitle = TextUtils.isEmpty(getString(R.string.manageConferenceLabel)) ? "Manage conference call" : getString(R.string.manageConferenceLabel);
                actionBar.setTitle(strTitle);
                actionBar.setElevation(mActionBarElevation);
                actionBar.setHideOffset(0);
                actionBar.show();

                final CallList calls = CallList.getInstance();
                getPresenter().init(getActivity(), calls);
                // Request focus on the list of participants for accessibility purposes.  This ensures
                // that once the list of participants is shown, the first participant is announced.
                mConferenceParticipantList.requestFocus();
            } else {
                actionBar.setElevation(0);
                actionBar.setHideOffset(actionBar.getHeight());
            }
        }
    }

    @Override
    public boolean isFragmentVisible() {
        return isVisible();
    }

    @Override
    public void update(Context context, List<Call> participants, boolean parentCanSeparate) {
        if (mConferenceParticipantListAdapter == null) {
            mConferenceParticipantListAdapter = new ConferenceParticipantListAdapter(mConferenceParticipantList, context, mInflater, mContactPhotoManager);

            mConferenceParticipantList.setAdapter(mConferenceParticipantListAdapter);
        }
        mConferenceParticipantListAdapter.updateParticipants(participants, parentCanSeparate);
    }

    @Override
    public void refreshCall(Call call) {
        mConferenceParticipantListAdapter.refreshCall(call);
    }
}
