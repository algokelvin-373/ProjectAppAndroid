package com.sbi.mvicalllibrary;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.role.RoleManager;
import android.content.Context;
import android.content.Intent;
import android.telecom.TelecomManager;
import androidx.annotation.NonNull;

public class MviCallUtils {

    static MviCallUtils instance;
    Context applicationContext;

    @NonNull
    public static MviCallUtils getInstance(@NonNull Context appContext) {
        if (instance == null) instance = new MviCallUtils(appContext);
        return instance;
    }

    private MviCallUtils(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    /// Set Permission....
    @SuppressLint("QueryPermissionsNeeded")
    public void setPermission(@NonNull Activity mActivity){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            @SuppressLint("WrongConstant") RoleManager roleManager = (RoleManager) mActivity.getSystemService(Context.ROLE_SERVICE);
            if (roleManager != null){
                Intent intent = roleManager.createRequestRoleIntent(RoleManager.ROLE_DIALER);
                mActivity.startActivityForResult(intent, 1);
            }
        }else{
            Intent intent = new Intent(TelecomManager.ACTION_CHANGE_DEFAULT_DIALER);
            intent.putExtra(TelecomManager.EXTRA_CHANGE_DEFAULT_DIALER_PACKAGE_NAME, mActivity.getPackageName());
            if (intent.resolveActivity(mActivity.getPackageManager()) != null) {
                mActivity.startActivity(intent);
            }
        }
    }

}
