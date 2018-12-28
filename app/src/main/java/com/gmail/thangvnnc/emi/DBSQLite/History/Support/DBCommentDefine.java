package com.gmail.thangvnnc.emi.DBSQLite.History.Support;

import android.provider.BaseColumns;

public final class DBCommentDefine {
    private DBCommentDefine() {}

    public static class CommentEntry implements BaseColumns {
        public static final String TABLE_NAME = "comment";
        public static final String COLUMN_NAME_ID = "ID";
        public static final String COLUMN_NAME_CONTENT= "Content";
        public static final String COLUMN_NAME_CREATED_AT = "Created_at";
    }
}