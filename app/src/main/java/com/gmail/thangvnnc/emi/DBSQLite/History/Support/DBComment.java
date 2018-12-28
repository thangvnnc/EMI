package com.gmail.thangvnnc.emi.DBSQLite.History.Support;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.gmail.thangvnnc.emi.Model.MSupport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.gmail.thangvnnc.emi.DBSQLite.History.Support.DBCommentDefine.CommentEntry.COLUMN_NAME_CONTENT;
import static com.gmail.thangvnnc.emi.DBSQLite.History.Support.DBCommentDefine.CommentEntry.COLUMN_NAME_CREATED_AT;
import static com.gmail.thangvnnc.emi.DBSQLite.History.Support.DBCommentDefine.CommentEntry.COLUMN_NAME_ID;
import static com.gmail.thangvnnc.emi.DBSQLite.History.Support.DBCommentDefine.CommentEntry.TABLE_NAME;


public class DBComment {
    public Context _context = null;
    public DBCommentHelper _dbCommentHelper = null;

    public DBComment(Context context) {
        _context = context;
        _dbCommentHelper = new DBCommentHelper(_context);
    }

    public boolean delete(int id)
    {
        SQLiteDatabase db = _dbCommentHelper.getWritableDatabase();
        return db.delete(DBCommentDefine.CommentEntry.TABLE_NAME,
                DBCommentDefine.CommentEntry.COLUMN_NAME_ID + "=" + id, null) > 0;
    }

    public long add(MSupport mSupport) {
        SQLiteDatabase db = _dbCommentHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_CONTENT, mSupport.getContent());
        Date date = new Date();
        values.put(COLUMN_NAME_CREATED_AT, date.getTime());
        long newRowId = db.insert(TABLE_NAME, null, values);
        return newRowId;
    }

    public List<MSupport> getAll() {
        List<MSupport> mSupport_reqs = new ArrayList<>();
        SQLiteDatabase db = _dbCommentHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null,
                null, null, null, COLUMN_NAME_ID + " DESC", null);
        if (cursor.moveToFirst()) {
            do {
                Integer id = cursor.getInt(cursor.getColumnIndex(DBCommentDefine.CommentEntry.COLUMN_NAME_ID));
                String content = cursor.getString(cursor.getColumnIndex(DBCommentDefine.CommentEntry.COLUMN_NAME_CONTENT));
                Long createdAt = cursor.getLong(cursor.getColumnIndex(DBCommentDefine.CommentEntry.COLUMN_NAME_CREATED_AT));
                MSupport mSupport_req = new MSupport();
                mSupport_req.setId(id);
                mSupport_req.setContent(content);
                mSupport_req.setCreatedAt(createdAt);
                mSupport_reqs.add(mSupport_req);
            } while (cursor.moveToNext());
        }

        return mSupport_reqs;
    }
}
