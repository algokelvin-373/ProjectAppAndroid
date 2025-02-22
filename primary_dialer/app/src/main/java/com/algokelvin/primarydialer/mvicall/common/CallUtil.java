package com.algokelvin.primarydialer.mvicall.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.telecom.PhoneAccount;
import android.telecom.PhoneAccountHandle;
import android.telecom.TelecomManager;

import java.util.List;

/**
 * Utilities related to calls that can be used by non system apps. These
 * use {@link Intent#ACTION_CALL} instead of ACTION_CALL_PRIVILEGED.
 *
 * The privileged version of this util exists inside Dialer.
 */
public class CallUtil {

    /**
     * Indicates that the video calling is not available.
     */
    public static final int VIDEO_CALLING_DISABLED = 0;

    /**
     * Indicates that video calling is enabled, regardless of presence status.
     */
    public static final int VIDEO_CALLING_ENABLED = 1;

    /**
     * Indicates that video calling is enabled, but the availability of video call affordances is
     * determined by the presence status associated with contacts.
     */
    public static final int VIDEO_CALLING_PRESENCE = 2;

    /**
     * Determines if video calling is available, and if so whether presence checking is available
     * as well.
     *
     * Returns a bitmask with {@link #VIDEO_CALLING_ENABLED} to indicate that video calling is
     * available, and {@link #VIDEO_CALLING_PRESENCE} if presence indication is also available.
     *
     * @param context The context
     * @return A bit-mask describing the current video capabilities.
     */
    public static int getVideoCallingAvailability(Context context) {
        if (!PermissionsUtil.hasPermission(context, android.Manifest.permission.READ_PHONE_STATE)
                || !CompatUtils.isVideoCompatible()) {
            return VIDEO_CALLING_DISABLED;
        }
        TelecomManager telecommMgr = (TelecomManager)
                context.getSystemService(Context.TELECOM_SERVICE);
        if (telecommMgr == null) {
            return VIDEO_CALLING_DISABLED;
        }

        @SuppressLint("MissingPermission") List<PhoneAccountHandle> accountHandles = telecommMgr.getCallCapablePhoneAccounts();
        for (PhoneAccountHandle accountHandle : accountHandles) {
            PhoneAccount account = telecommMgr.getPhoneAccount(accountHandle);
            if (account != null) {
                if (account.hasCapabilities(PhoneAccount.CAPABILITY_VIDEO_CALLING)) {
                    // Builds prior to N do not have presence support.
                    if (!CompatUtils.isVideoPresenceCompatible()) {
                        return VIDEO_CALLING_ENABLED;
                    }

                    int videoCapabilities = VIDEO_CALLING_ENABLED;
                    if (account.hasCapabilities(
                            PhoneAccountSdkCompat.CAPABILITY_VIDEO_CALLING_RELIES_ON_PRESENCE)) {
                        videoCapabilities |= VIDEO_CALLING_PRESENCE;
                    }
                    return videoCapabilities;
                }
            }
        }
        return VIDEO_CALLING_DISABLED;
    }

    /**
     * Determines if one of the call capable phone accounts defined supports video calling.
     *
     * @param context The context.
     * @return {@code true} if one of the call capable phone accounts supports video calling,
     *      {@code false} otherwise.
     */
    public static boolean isVideoEnabled(Context context) {
        return (getVideoCallingAvailability(context) & VIDEO_CALLING_ENABLED) != 0;
    }

}
