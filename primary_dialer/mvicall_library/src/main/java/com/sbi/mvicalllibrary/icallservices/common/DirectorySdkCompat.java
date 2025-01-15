package com.sbi.mvicalllibrary.icallservices.common;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract.Directory;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.N)
public class DirectorySdkCompat {

    private static final String TAG = "DirectorySdkCompat";

    @SuppressLint("NewApi")
    public static final Uri ENTERPRISE_CONTENT_URI = Directory.ENTERPRISE_CONTENT_URI;
    public static final long ENTERPRISE_LOCAL_DEFAULT = Directory.ENTERPRISE_DEFAULT;
    public static final long ENTERPRISE_LOCAL_INVISIBLE = Directory.ENTERPRISE_LOCAL_INVISIBLE;

    @SuppressLint("NewApi")
    public static boolean isRemoteDirectoryId(long directoryId) {
        return Directory.isRemoteDirectoryId(directoryId);
    }

    @SuppressLint("NewApi")
    public static boolean isEnterpriseDirectoryId(long directoryId) {
        return Directory.isEnterpriseDirectoryId(directoryId);
    }
}
