package com.algokelvin.primarydialer.mvicall;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

public abstract class BaseFragmentModifier<T extends PresenterModifier<U>, U extends UiModifier> extends Fragment {

    private static final String KEY_FRAGMENT_HIDDEN = "key_fragment_hidden";

    private final T mPresenter;

    public abstract T createPresenter();

    public abstract U getUi();

    protected BaseFragmentModifier() {
        mPresenter = createPresenter();
    }

    public T getPresenter() {
        return mPresenter;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter.onUiReady(getUi());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mPresenter.onRestoreInstanceState(savedInstanceState);
            if (savedInstanceState.getBoolean(KEY_FRAGMENT_HIDDEN)) {
                getFragmentManager().beginTransaction().hide(this).commit();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.onUiDestroy(getUi());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mPresenter.onSaveInstanceState(outState);
        outState.putBoolean(KEY_FRAGMENT_HIDDEN, isHidden());
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((FragmentDisplayManager) activity).onFragmentAttached(this);
    }
}
