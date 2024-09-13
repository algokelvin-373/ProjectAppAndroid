package com.algokelvin.transaction;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.algokelvin.transaction.fragments.CheckoutFragment;
import com.algokelvin.transaction.fragments.SuccessFragment;
import com.algokelvin.transaction.fragments.TransactionFragment;

import vin.algokelvin.simplicity.registration.TabPageLayer;

public class TransactionActivity extends TabPageLayer {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        Fragment[] fragments = {new TransactionFragment(), new CheckoutFragment(), new SuccessFragment()};
        setTabPageLayer(9, R.id.tabTransaction, R.id.viewPager, fragments);
        setPageEnds(fragments.length);

    }
}