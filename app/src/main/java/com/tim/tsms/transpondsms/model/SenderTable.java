package com.tim.tsms.transpondsms.model;

import android.provider.BaseColumns;

public final class SenderTable {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private SenderTable() {}

    /* Inner class that defines the table contents */
    public static class SenderEntry implements BaseColumns {
        public static final String TABLE_NAME = "sender";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_IMAGE_ID = "image_id";
        public static final String COLUMN_NAME_TIME = "time";
    }
}
