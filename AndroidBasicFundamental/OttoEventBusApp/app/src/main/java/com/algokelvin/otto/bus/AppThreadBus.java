package com.algokelvin.otto.bus;

import android.app.Application;

public class AppThreadBus extends Application {
    public static final MainThreadBus mMainThreadBus = new MainThreadBus();
    public static AppThreadBus appThreadBus;

    @Override
    public void onCreate() {
        super.onCreate();
        appThreadBus = this;
    }

    public static MainThreadBus getMainThreadBusInstance() {
        return mMainThreadBus;
    }
}
