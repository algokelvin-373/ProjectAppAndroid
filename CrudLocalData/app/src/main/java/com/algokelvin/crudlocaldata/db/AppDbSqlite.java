package com.algokelvin.crudlocaldata.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;


import com.algokelvin.crudlocaldata.db.entity.UserSqlite;

import java.util.List;

public class AppDbSqlite extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "mydatabase.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "myusers";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESCRIPTION = "description";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_DESCRIPTION + " TEXT);";

    public AppDbSqlite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void insertData(UserSqlite user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, user.getName());
        values.put(COLUMN_DESCRIPTION, user.getDescription());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void insertMultipleData(List<UserSqlite> users) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "INSERT INTO " + TABLE_NAME + " (" + COLUMN_NAME + ", " + COLUMN_DESCRIPTION + ") VALUES (?, ?);";
        SQLiteStatement statement = db.compileStatement(sql);

        db.beginTransaction();
        try {
            for (UserSqlite person : users) {
                statement.clearBindings();
                statement.bindString(1, person.getName());
                statement.bindString(2, person.getDescription());
                statement.execute();
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        db.close();
    }
}
