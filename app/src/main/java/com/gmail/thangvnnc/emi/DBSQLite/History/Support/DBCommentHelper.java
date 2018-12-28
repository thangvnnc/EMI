package com.gmail.thangvnnc.emi.DBSQLite.History.Support;

import android.content.Context;

import com.gmail.thangvnnc.emi.DBSQLite.History.DBHelper;

import static com.gmail.thangvnnc.emi.DBSQLite.History.Support.DBCommentDefine.CommentEntry.COLUMN_NAME_CONTENT;
import static com.gmail.thangvnnc.emi.DBSQLite.History.Support.DBCommentDefine.CommentEntry.COLUMN_NAME_CREATED_AT;
import static com.gmail.thangvnnc.emi.DBSQLite.History.Support.DBCommentDefine.CommentEntry.COLUMN_NAME_ID;
import static com.gmail.thangvnnc.emi.DBSQLite.History.Support.DBCommentDefine.CommentEntry.TABLE_NAME;

public class DBCommentHelper extends DBHelper {

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME_CONTENT + " TEXT," +
                    COLUMN_NAME_CREATED_AT + " DATE)";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public DBCommentHelper(Context context) {
        super(context);
    }
}