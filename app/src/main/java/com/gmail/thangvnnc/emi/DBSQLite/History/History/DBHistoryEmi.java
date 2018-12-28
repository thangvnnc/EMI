package com.gmail.thangvnnc.emi.DBSQLite.History.History;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.gmail.thangvnnc.emi.Model.MEmiHistory;

import java.util.ArrayList;
import java.util.List;

import static com.gmail.thangvnnc.emi.DBSQLite.History.History.DBHistoryDefine.HistoryEntry.COLUMN_NAME_EMI;
import static com.gmail.thangvnnc.emi.DBSQLite.History.History.DBHistoryDefine.HistoryEntry.COLUMN_NAME_ID;
import static com.gmail.thangvnnc.emi.DBSQLite.History.History.DBHistoryDefine.HistoryEntry.COLUMN_NAME_LOAN_MOUNT;
import static com.gmail.thangvnnc.emi.DBSQLite.History.History.DBHistoryDefine.HistoryEntry.COLUMN_NAME_NPAYMENTS;
import static com.gmail.thangvnnc.emi.DBSQLite.History.History.DBHistoryDefine.HistoryEntry.TABLE_NAME;

public class DBHistoryEmi {
    public Context _context = null;
    public DBHistoryHelper _dbHistoryHelper = null;

    public DBHistoryEmi(Context context) {
        _context = context;
        _dbHistoryHelper = new DBHistoryHelper(_context);
    }

    public long add(MEmiHistory mEmiHistory) {
        SQLiteDatabase db = _dbHistoryHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_LOAN_MOUNT, mEmiHistory.loanAmount);
        values.put(COLUMN_NAME_EMI, mEmiHistory.emi);
        values.put(COLUMN_NAME_NPAYMENTS, mEmiHistory.nPayment);

        long newRowId = db.insert(TABLE_NAME, null, values);
        return newRowId;
    }

    public List<MEmiHistory> getAll() {
        List<MEmiHistory> mEmiHistories = new ArrayList<>();
        SQLiteDatabase db = _dbHistoryHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null,
                null, null, null, COLUMN_NAME_ID + " DESC", null);
        if (cursor.moveToFirst()) {
            do {
                Integer id = cursor.getInt(cursor.getColumnIndex(DBHistoryDefine.HistoryEntry.COLUMN_NAME_ID));
                Double loanAmout = cursor.getDouble(cursor.getColumnIndex(DBHistoryDefine.HistoryEntry.COLUMN_NAME_LOAN_MOUNT));
                Double emi = cursor.getDouble(cursor.getColumnIndex(DBHistoryDefine.HistoryEntry.COLUMN_NAME_EMI));
                Integer nPayments = cursor.getInt(cursor.getColumnIndex(DBHistoryDefine.HistoryEntry.COLUMN_NAME_NPAYMENTS));
                MEmiHistory mEmiHistory = new MEmiHistory(id, loanAmout, emi, nPayments);
                mEmiHistories.add(mEmiHistory);
            } while (cursor.moveToNext());
        }

        return mEmiHistories;
    }
}
