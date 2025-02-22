package com.algokelvin.primarydialer.mvicall.common;

import android.Manifest.permission;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.content.ContextCompat;

/**
 * Utility class to help with runtime permissions.
 */
public class PermissionsUtil {
    // Each permission in this list is a cherry-picked permission from a particular permission
    // group. Granting a permission group enables access to all permissions in that group so we
    // only need to check a single permission in each group.
    // Note: This assumes that the app has correctly requested for all the relevant permissions
    // in its Manifest file.
    public static final String CONTACTS = permission.READ_CONTACTS;

    public static boolean hasContactsPermissions(Context context) {
        return hasPermission(context, CONTACTS);
    }

    public static boolean hasPermission(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission)
                == PackageManager.PERMISSION_GRANTED;
    }
}
