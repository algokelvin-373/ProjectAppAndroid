package com.algokelvin.layermapgoogle.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

public class SecretApplication {
    public static String getApiKey(Context context, String keyValue) {
        ApplicationInfo ai = null;
        try {
            ai = context.getApplicationContext()
                    .getPackageManager()
                    .getApplicationInfo(context.getApplicationContext().getPackageName(), PackageManager.GET_META_DATA);
            Object value = ai.metaData.get("com.google.android.geo.API_KEY");
            if (value != null) {
                return value.toString();
                //Toast.makeText(context, key, Toast.LENGTH_LONG).show();
            } else {
                return null;
                //Toast.makeText(context, "Key is null", Toast.LENGTH_LONG).show();
            }
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
