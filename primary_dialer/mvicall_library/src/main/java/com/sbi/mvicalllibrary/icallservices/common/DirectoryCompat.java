package com.sbi.mvicalllibrary.icallservices.common;

import android.net.Uri;
import android.provider.ContactsContract.Directory;

public class DirectoryCompat {

    public static Uri getContentUri() {
        if (ContactsUtils.FLAG_N_FEATURE) {
            return DirectorySdkCompat.ENTERPRISE_CONTENT_URI;
        }
        return Directory.CONTENT_URI;
    }

    public static boolean isInvisibleDirectory(long directoryId) {
        if (ContactsUtils.FLAG_N_FEATURE) {
            return (directoryId == Directory.LOCAL_INVISIBLE
                    || directoryId == DirectorySdkCompat.ENTERPRISE_LOCAL_INVISIBLE);
        }
        return directoryId == Directory.LOCAL_INVISIBLE;
    }

    public static boolean isRemoteDirectoryId(long directoryId) {
        if (ContactsUtils.FLAG_N_FEATURE) {
            return DirectorySdkCompat.isRemoteDirectoryId(directoryId);
        }
        return !(directoryId == Directory.DEFAULT || directoryId == Directory.LOCAL_INVISIBLE);
    }

    public static boolean isEnterpriseDirectoryId(long directoryId) {
        return ContactsUtils.FLAG_N_FEATURE && DirectorySdkCompat.isEnterpriseDirectoryId(directoryId);
    }
}
