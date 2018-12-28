package com.gmail.thangvnnc.emi.DBSQLite.History.History;

import android.content.Context;

import com.gmail.thangvnnc.emi.DBSQLite.History.DBHelper;

import static com.gmail.thangvnnc.emi.DBSQLite.History.History.DBHistoryDefine.HistoryEntry.COLUMN_NAME_EMI;
import static com.gmail.thangvnnc.emi.DBSQLite.History.History.DBHistoryDefine.HistoryEntry.COLUMN_NAME_ID;
import static com.gmail.thangvnnc.emi.DBSQLite.History.History.DBHistoryDefine.HistoryEntry.COLUMN_NAME_LOAN_MOUNT;
import static com.gmail.thangvnnc.emi.DBSQLite.History.History.DBHistoryDefine.HistoryEntry.COLUMN_NAME_NPAYMENTS;
import static com.gmail.thangvnnc.emi.DBSQLite.History.History.DBHistoryDefine.HistoryEntry.TABLE_NAME;

public class DBHistoryHelper extends DBHelper {

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME_LOAN_MOUNT + " NUMERIC," +
                    COLUMN_NAME_EMI + " NUMERIC, " +
                    COLUMN_NAME_NPAYMENTS + " INTEGER)";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public DBHistoryHelper(Context context) {
        super(context);
    }
}