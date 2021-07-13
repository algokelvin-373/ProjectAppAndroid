package com.example.administrator.ottodemo;

import android.app.Application;

/**
 * auther：wzy
 * date：2016/11/17 19 :07
 * desc:
 */

public class App extends Application {
    public static final MainThreadBus mMainThreadBus = new MainThreadBus();
    public static App app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }

    public static MainThreadBus getMainThreadBusInstance() {
        return mMainThreadBus;
    }
}
