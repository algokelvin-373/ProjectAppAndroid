package com.algokelvin.primarydialer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.role.RoleManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.telecom.TelecomManager;
import android.util.Log;

import androidx.annotation.NonNull;

public class PermissionHelper {

    private static final String TAG = "PermissionHelper";

    @SuppressLint("QueryPermissionsNeeded")
    public void setPermission(@NonNull Activity mActivity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            RoleManager roleManager = (RoleManager) mActivity.getSystemService(Context.ROLE_SERVICE);
            if (roleManager != null) {
                // Cek apakah aplikasi sudah memiliki peran dialer
                boolean isRoleAvailable = roleManager.isRoleAvailable(RoleManager.ROLE_DIALER);
                if (isRoleAvailable) {
                    Log.d(TAG, "Role Dialer Available: " + isRoleAvailable);
                    if (!roleManager.isRoleHeld(RoleManager.ROLE_DIALER)) {
                        Log.d(TAG, "Requesting role to be default dialer...");
                        // Tampilkan dialog pilihan aplikasi dialer
                        Intent intent = roleManager.createRequestRoleIntent(RoleManager.ROLE_DIALER);
                        mActivity.startActivityForResult(intent, 1);
                    } else {
                        Log.d(TAG, "Already default dialer app");
                    }
                }

                /*if (!roleManager.isRoleHeld(RoleManager.ROLE_DIALER)) {
                    Intent intent = roleManager.createRequestRoleIntent(RoleManager.ROLE_DIALER);
                    mActivity.startActivityForResult(intent, 1);
                } else {
                    Log.d(TAG, "Already default dialer app");
                }*/
            }
        } else {
            // Untuk versi Android sebelum Q
            TelecomManager telecomManager = (TelecomManager) mActivity.getSystemService(Context.TELECOM_SERVICE);
            if (telecomManager != null) {
                String defaultDialerPackage = telecomManager.getDefaultDialerPackage();
                if (!mActivity.getPackageName().equals(defaultDialerPackage)) {
                    Intent intent = new Intent(TelecomManager.ACTION_CHANGE_DEFAULT_DIALER);
                    intent.putExtra(TelecomManager.EXTRA_CHANGE_DEFAULT_DIALER_PACKAGE_NAME, mActivity.getPackageName());
                    if (intent.resolveActivity(mActivity.getPackageManager()) != null) {
                        mActivity.startActivityForResult(intent, 1);
                    }
                } else {
                    Log.d(TAG, "Already default dialer app");
                }
            }
        }
    }
}
