package com.androidtutz.anushka.activitystatesdemo;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.util.Log;

public class DemoAppComponent implements LifecycleObserver {

    private final String activityName;
    private static final String TAG = "lifecycle";

    public DemoAppComponent(String activityName) {
        this.activityName = activityName;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    protected void onCreate(){
        Log.i(TAG,"***************    DemoAppComponent onCreate() invoked for "+activityName);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    protected void onStart() {
        Log.i(TAG,"***************    DemoAppComponent onStart() invoked from "+activityName);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    protected void onResume() {
        Log.i(TAG,"***************    DemoAppComponent onResume() invoked from "+activityName);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    protected void onPause() {
        Log.i(TAG,"***************    DemoAppComponent onPause() invoked from "+activityName);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    protected void onStop() {
        Log.i(TAG,"***************    DemoAppComponent onStop() invoked from "+activityName);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    protected void onDestroy() {
        Log.i(TAG,"***************    DemoAppComponent onDestroy() invoked from "+activityName);
    }

}
