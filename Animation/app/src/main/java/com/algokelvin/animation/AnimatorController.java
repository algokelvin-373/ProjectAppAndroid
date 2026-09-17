package com.algokelvin.animation;

import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

public class AnimatorController {
    private ObjectAnimator animator;

    public void animate(View view, String animation, float coordinate, int duration) {
        this.animator = ObjectAnimator.ofFloat(view, animation, coordinate);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(duration);
        animator.start();
    }

    public void translateX(View view, float coordinate, int duration) {
        animate(view, "translationX", coordinate, duration);
    }

    public void fade(View view, float alpha, int duration) {
        animate(view, "alpha", alpha, duration);
    }

    public void rotate(View view, float degree, int duration) {
        animate(view, "rotation", degree, duration);
    }

    public void scale(View view, float size, int duration) {
        animate(view, "scaleX", size, duration);
        animate(view, "scaleY", size, duration);
    }

    public void reset(View view) {
        view.setTranslationX(0f);
        view.setAlpha(1f);
        view.setRotation(0f);
        view.setScaleX(1f);
        view.setScaleY(1f);
    }

}
