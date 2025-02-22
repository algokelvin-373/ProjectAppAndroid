package com.algokelvin.primarydialer.mvicall.widgetDialpad.animation;

import android.view.animation.Interpolator;

import com.algokelvin.primarydialer.mvicall.widgetDialpad.compat.PathInterpolatorCompat;

public class AnimUtils {
    public static final Interpolator EASE_IN = PathInterpolatorCompat.create(
            0.0f, 0.0f, 0.2f, 1.0f);
    public static final Interpolator EASE_OUT = PathInterpolatorCompat.create(
            0.4f, 0.0f, 1.0f, 1.0f);
    public static final Interpolator EASE_OUT_EASE_IN = PathInterpolatorCompat.create(
            0.4f, 0, 0.2f, 1);
}
