package com.algokelvin.insertdb.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.algokelvin.insertdb.db.dao.UserDao;
import com.algokelvin.insertdb.db.entity.User;

@Database(
        entities = {User.class},
        version = 1
)
public abstract class AppDb extends RoomDatabase {
    public abstract UserDao userDao();

    private static volatile AppDb INSTANCE;

    public static AppDb getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDb.class) {
                if (INSTANCE == null) {
                    String DATABASE_NAME = "user_database";
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDb.class,
                            DATABASE_NAME
                    ).build();
                }
            }
        }
        return INSTANCE;
    }
}
