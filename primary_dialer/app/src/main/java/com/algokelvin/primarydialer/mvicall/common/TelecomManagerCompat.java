package com.algokelvin.primarydialer.mvicall.common;

import android.annotation.SuppressLint;
import android.telecom.TelecomManager;

import androidx.annotation.Nullable;

/**
 * Compatibility class for {@link TelecomManager}.
 */
public class TelecomManagerCompat {
    public static final String TELECOM_MANAGER_CLASS = "android.telecom.TelecomManager";

    /**
     * Used to determine the currently selected default dialer package.
     *
     * @param telecomManager the {@link TelecomManager} used for method calls, if possible.
     * @return package name for the default dialer package or null if no package has been
     *         selected as the default dialer.
     */
    @Nullable
    public static String getDefaultDialerPackage(@Nullable TelecomManager telecomManager) {
        if (telecomManager != null && CompatUtils.isDefaultDialerCompatible()) {
            return telecomManager.getDefaultDialerPackage();
        }
        return null;
    }


    /**
     * Silences the ringer if a ringing call exists. Noop if {@link TelecomManager#silenceRinger()}
     * is unavailable.
     *
     * @param telecomManager the TelecomManager to use to silence the ringer.
     */
    @SuppressLint("MissingPermission")
    public static void silenceRinger(@Nullable TelecomManager telecomManager) {
        if (telecomManager != null && (CompatUtils.isMarshmallowCompatible() || CompatUtils
                .isMethodAvailable(TELECOM_MANAGER_CLASS, "silenceRinger"))) {
            telecomManager.silenceRinger();
        }
    }
}
