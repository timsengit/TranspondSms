package com.tim.tsms.transpondsms.model;

import android.provider.BaseColumns;

public final class RuleTable {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private RuleTable() {}

    /* Inner class that defines the table contents */
    public static class RuleEntry implements BaseColumns {
        public static final String TABLE_NAME = "rule";
        public static final String COLUMN_NAME_MATCH_ID = "match_id";
        public static final String COLUMN_NAME_FROM = "sender_id";
        public static final String COLUMN_NAME_TIME = "time";
    }
}