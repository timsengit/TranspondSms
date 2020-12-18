package com.tim.tsms.transpondsms.utils;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

import com.tim.tsms.transpondsms.model.LogModel;
import com.tim.tsms.transpondsms.model.RuleTable;
import com.tim.tsms.transpondsms.model.RuleModel;
import com.tim.tsms.transpondsms.model.RuleTable;
import com.tim.tsms.transpondsms.model.RuleVo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RuleUtil {
    static String TAG = "RuleUtil";
    static Context context;
    static DbHelperTLog dbHelper;
    static SQLiteDatabase db;

    public static void init(Context context1) {
        context = context1;
        dbHelper = new DbHelperTLog(context);
        db = dbHelper.getReadableDatabase();
    }

    public static long addRule(RuleModel ruleModel) {
        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(RuleTable.RuleEntry.COLUMN_NAME_MATCH_ID, ruleModel.getMatchId());
        values.put(RuleTable.RuleEntry.COLUMN_NAME_SENDER_ID, ruleModel.getSenderId());
        values.put(RuleTable.RuleEntry.COLUMN_NAME_TIME, ruleModel.getTime());

        // Insert the new row, returning the primary key value of the new row

        return db.insert(RuleTable.RuleEntry.TABLE_NAME, null, values);
    }

    public static int delRule(Long id) {
        // Define 'where' part of query.
        String selection = " 1 ";
        // Specify arguments in placeholder order.
        List<String> selectionArgList = new ArrayList<>();
        if(id!=null){
            // Define 'where' part of query.
            selection +=" and " + RuleTable.RuleEntry._ID + " = ? ";
            // Specify arguments in placeholder order.
            selectionArgList.add(String.valueOf(id));

        }

        String[] selectionArgs = (String[]) selectionArgList.toArray();
        // Issue SQL statement.
        return db.delete(RuleTable.RuleEntry.TABLE_NAME, selection, selectionArgs);

    }

    public static List<RuleVo> getRule(Long id, Long matchId, Long senderId) {
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                RuleTable.RuleEntry.COLUMN_NAME_MATCH_ID,
                RuleTable.RuleEntry.COLUMN_NAME_SENDER_ID,
                RuleTable.RuleEntry.COLUMN_NAME_TIME
        };
        // Define 'where' part of query.
        String selection = " 1 ";
        // Specify arguments in placeholder order.
        List<String> selectionArgList = new ArrayList<>();
        if(id!=null){
            // Define 'where' part of query.
            selection +=" and " + RuleTable.RuleEntry._ID + " = ? ";
            // Specify arguments in placeholder order.
            selectionArgList.add(String.valueOf(id));
        }

        if(matchId!=null){
            // Define 'where' part of query.
            selection =" and " +  RuleTable.RuleEntry.COLUMN_NAME_MATCH_ID + " = ? ";
            // Specify arguments in placeholder order.
            selectionArgList.add(String.valueOf(matchId));
        }

        if(senderId!=null){
            // Define 'where' part of query.
            selection =" and " +  RuleTable.RuleEntry.COLUMN_NAME_SENDER_ID + " = ? ";
            // Specify arguments in placeholder order.
            selectionArgList.add(String.valueOf(senderId));
        }

        String[] selectionArgs = (String[]) selectionArgList.toArray();

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                RuleTable.RuleEntry._ID + " DESC";

        Cursor cursor = db.query(
                RuleTable.RuleEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );
//        List<LogModel> tLogs = new ArrayList<>();
        List<RuleVo> ruleVos = new ArrayList<>();
        while(cursor.moveToNext()) {
            long itemId = cursor.getLong(
                    cursor.getColumnIndexOrThrow(RuleTable.RuleEntry._ID));
//            tLogs.add(itemId);
        }
        cursor.close();
        return ruleVos;
    }

}
