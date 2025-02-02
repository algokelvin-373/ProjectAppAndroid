package com.algokelvin.primarydialer.mvicall.widgetDialpad.compat;

import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;

public class PathInterpolatorCompat {
    public static Interpolator create(float controlX1, float controlY1,
                                      float controlX2, float controlY2) {
        return new PathInterpolator(controlX1, controlY1, controlX2, controlY2);
    }
}

