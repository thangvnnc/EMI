package com.gmail.thangvnnc.emi.DBSQLite.History;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.gmail.thangvnnc.emi.DBSQLite.History.DBHistoryDefine.HistoryEntry.COLUMN_NAME_EMI;
import static com.gmail.thangvnnc.emi.DBSQLite.History.DBHistoryDefine.HistoryEntry.COLUMN_NAME_ID;
import static com.gmail.thangvnnc.emi.DBSQLite.History.DBHistoryDefine.HistoryEntry.COLUMN_NAME_LOAN_MOUNT;
import static com.gmail.thangvnnc.emi.DBSQLite.History.DBHistoryDefine.HistoryEntry.COLUMN_NAME_NPAYMENTS;
import static com.gmail.thangvnnc.emi.DBSQLite.History.DBHistoryDefine.HistoryEntry.TABLE_NAME;

public class DBHistoryHelper extends SQLiteOpenHelper {

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME_LOAN_MOUNT + " NUMERIC," +
                    COLUMN_NAME_EMI + " NUMERIC, " +
                    COLUMN_NAME_NPAYMENTS + " INTEGER)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedReader.db";

    public DBHistoryHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}