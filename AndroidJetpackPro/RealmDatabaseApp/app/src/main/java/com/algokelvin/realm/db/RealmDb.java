package com.algokelvin.realm.db;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class RealmDb {
    public static void initRealm(Context context) {
        Realm.init(context);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .allowWritesOnUiThread(true)
                .build();
        Realm.setDefaultConfiguration(config);
    }
}
