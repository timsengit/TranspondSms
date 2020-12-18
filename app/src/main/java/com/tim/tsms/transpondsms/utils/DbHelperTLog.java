package com.tim.tsms.transpondsms.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tim.tsms.transpondsms.model.LogTable;

public class DbHelperTLog extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "transpondsms.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + LogTable.LogEntry.TABLE_NAME + " (" +
                    LogTable.LogEntry._ID + " INTEGER PRIMARY KEY," +
                    LogTable.LogEntry.COLUMN_NAME_FROM + " TEXT," +
                    LogTable.LogEntry.COLUMN_NAME_CONTENT + " TEXT," +
                    LogTable.LogEntry.COLUMN_NAME_TIME + " INTEGER)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + LogTable.LogEntry.TABLE_NAME;


    public DbHelperTLog(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
