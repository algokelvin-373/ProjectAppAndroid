package com.algokelvin.otto.bus;

import android.os.Handler;
import android.os.Looper;

import com.squareup.otto.Bus;

public class MainThreadBus extends Bus {
    private final Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    public void post(final Object event) {
        //Determine whether it is the main thread looper, whether this thread is the main thread
        if (Looper.myLooper() == Looper.getMainLooper()) {
            super.post(event);
        } else {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    MainThreadBus.super.post(event);
                }
            });
        }
    }
}
