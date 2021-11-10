package com.algokelvin.biometric.utils;

import android.content.Context;

import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import java.util.concurrent.Executor;

public class BiometricFunction {
    private Executor executor;
    private BiometricPrompt.PromptInfo promptInfo;
    private final Context context;

    public BiometricFunction(Context context) {
        this.context = context;

        setExecutor();
        setPromptInfo();
    }

    public BiometricPrompt.PromptInfo getPromptInfo() {
        return promptInfo;
    }

    public Executor getExecutor() {
        return executor;
    }

    private void setExecutor() {
        executor = ContextCompat.getMainExecutor(context);
    }

    private void setPromptInfo() {
        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric login for my app") // Show Title Layout Biometric
                .setSubtitle("Log in using your biometric credential") // Description Layout Biometric
                .setNegativeButtonText("Use account password") // Button if user not use biometric
                .build();
    }
}
