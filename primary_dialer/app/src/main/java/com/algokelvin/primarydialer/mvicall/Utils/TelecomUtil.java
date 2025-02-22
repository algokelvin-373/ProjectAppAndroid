package com.algokelvin.primarydialer.mvicall.Utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.telecom.TelecomManager;
import android.text.TextUtils;

import androidx.core.content.ContextCompat;

import com.algokelvin.primarydialer.mvicall.common.TelecomManagerCompat;

public class TelecomUtil {
    private static boolean sWarningLogged = false;

    public static void silenceRinger(Context context) {
        if (hasModifyPhoneStatePermission(context)) {
            try {
                TelecomManagerCompat.silenceRinger(getTelecomManager(context));
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
    }
    public static boolean hasModifyPhoneStatePermission(Context context) {
        return isDefaultDialer(context)
                || hasPermission(context, Manifest.permission.MODIFY_PHONE_STATE);
    }

    private static boolean hasPermission(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean isDefaultDialer(Context context) {
        final boolean result = TextUtils.equals(context.getPackageName(), TelecomManagerCompat.getDefaultDialerPackage(getTelecomManager(context)));
        if (result) {
            sWarningLogged = false;
        } else {
            if (!sWarningLogged) {
                sWarningLogged = true;
            }
        }
        return result;
    }

    private static TelecomManager getTelecomManager(Context context) {
        return (TelecomManager) context.getSystemService(Context.TELECOM_SERVICE);
    }
}
