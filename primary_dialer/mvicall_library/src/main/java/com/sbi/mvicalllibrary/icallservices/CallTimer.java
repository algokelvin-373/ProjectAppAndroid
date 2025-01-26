package com.sbi.mvicalllibrary.icallservices;

import android.os.Handler;
import android.os.SystemClock;

import com.google.common.base.Preconditions;

/**
 * Helper class used to keep track of events requiring regular intervals.
 */
public class CallTimer extends Handler {
    private final Runnable mInternalCallback;
    private final Runnable mCallback;
    private long mLastReportedTime;
    private long mInterval;
    private boolean mRunning;

    public CallTimer(Runnable callback) {
        Preconditions.checkNotNull(callback);

        mInterval = 0;
        mLastReportedTime = 0;
        mRunning = false;
        mCallback = callback;
        mInternalCallback = new CallTimerCallback();
    }

    public boolean start(long interval) {
        if (interval <= 0) {
            return false;
        }

        // cancel any previous timer
        cancel();

        mInterval = interval;
        mLastReportedTime = SystemClock.uptimeMillis();

        mRunning = true;
        periodicUpdateTimer();

        return true;
    }

    public void cancel() {
        removeCallbacks(mInternalCallback);
        mRunning = false;
    }

    private void periodicUpdateTimer() {
        if (!mRunning) {
            return;
        }

        final long now = SystemClock.uptimeMillis();
        long nextReport = mLastReportedTime + mInterval;
        while (now >= nextReport) {
            nextReport += mInterval;
        }

        postAtTime(mInternalCallback, nextReport);
        mLastReportedTime = nextReport;

        // Run the callback
        mCallback.run();
    }

    private class CallTimerCallback implements Runnable {
        @Override
        public void run() {
            periodicUpdateTimer();
        }
    }
}
