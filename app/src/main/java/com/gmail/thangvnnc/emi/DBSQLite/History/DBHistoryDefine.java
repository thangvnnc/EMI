package com.gmail.thangvnnc.emi.DBSQLite.History;

import android.provider.BaseColumns;

public final class DBHistoryDefine {
    private DBHistoryDefine() {}

    public static class HistoryEntry implements BaseColumns {
        public static final String TABLE_NAME = "history";
        public static final String COLUMN_NAME_ID = "ID";
        public static final String COLUMN_NAME_LOAN_MOUNT = "LoanMount";
        public static final String COLUMN_NAME_EMI = "EMI";
        public static final String COLUMN_NAME_NPAYMENTS = "NPayments";
    }
}