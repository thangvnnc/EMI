package com.gmail.thangvnnc.emi.DBSQLite.History;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.gmail.thangvnnc.emi.DBSQLite.History.History.DBHistoryHelper;
import com.gmail.thangvnnc.emi.DBSQLite.History.Support.DBCommentHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "Interest.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DBHistoryHelper.SQL_CREATE_ENTRIES);
        db.execSQL(DBCommentHelper.SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DBHistoryHelper.SQL_DELETE_ENTRIES);
        db.execSQL(DBCommentHelper.SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
