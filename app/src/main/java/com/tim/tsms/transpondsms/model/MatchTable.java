package com.tim.tsms.transpondsms.model;

import android.provider.BaseColumns;

public final class MatchTable {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private MatchTable() {}

    /* Inner class that defines the table contents */
    public static class MatchEntry implements BaseColumns {
        public static final String TABLE_NAME = "match";
        public static final String COLUMN_NAME_NEXT_ID = "next_id";
        public static final String COLUMN_NAME_FILED = "filed";
        public static final String COLUMN_NAME_CHECK = "tcheck";
        public static final String COLUMN_NAME_VALUE = "value";
        public static final String COLUMN_NAME_TIME = "time";
    }
}
