package com.tim.tsms.transpondsms.utils;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

import com.tim.tsms.transpondsms.model.LogModel;
import com.tim.tsms.transpondsms.model.LogTable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LogUtil {
    static String TAG = "LogUtil";
    static Context context;
    static DbHelper dbHelper;
    static SQLiteDatabase db;

    public static void init(Context context1) {
        context = context1;
        dbHelper = new DbHelper(context);
        // Gets the data repository in write mode
        db = dbHelper.getReadableDatabase();
    }

    public static long addLog(LogModel logModel) {
        //不保存转发消息
        if (!SettingUtil.saveMsgHistory()) return 0;

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(LogTable.LogEntry.COLUMN_NAME_FROM, logModel.getFrom());
        values.put(LogTable.LogEntry.COLUMN_NAME_CONTENT, logModel.getContent());

        // Insert the new row, returning the primary key value of the new row

        return db.insert(LogTable.LogEntry.TABLE_NAME, null, values);
    }

    public static int delLog(Long id,String key) {
        // Define 'where' part of query.
        String selection = " 1 ";
        // Specify arguments in placeholder order.
        List<String> selectionArgList = new ArrayList<>();
        if(id!=null){
            // Define 'where' part of query.
            selection +=" and " + LogTable.LogEntry._ID + " = ? ";
            // Specify arguments in placeholder order.
            selectionArgList.add(String.valueOf(id));

        }

        if(key!=null){
            // Define 'where' part of query.
            selection =" and (" +  LogTable.LogEntry.COLUMN_NAME_FROM + " LIKE ? or "+ LogTable.LogEntry.COLUMN_NAME_CONTENT + " LIKE ? ) ";
            // Specify arguments in placeholder order.
            selectionArgList.add(key);
            selectionArgList.add(key);
        }
        String[] selectionArgs = selectionArgList.toArray(new String[selectionArgList.size()]);
        // Issue SQL statement.
        return db.delete(LogTable.LogEntry.TABLE_NAME, selection, selectionArgs);

    }

    public static String getLog(Long id,String key) {
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                LogTable.LogEntry.COLUMN_NAME_FROM,
                LogTable.LogEntry.COLUMN_NAME_CONTENT,
                LogTable.LogEntry.COLUMN_NAME_TIME
        };
        // Define 'where' part of query.
        String selection = " 1 ";
        // Specify arguments in placeholder order.
        List<String> selectionArgList = new ArrayList<>();
        if(id!=null){
            // Define 'where' part of query.
            selection +=" and " + LogTable.LogEntry._ID + " = ? ";
            // Specify arguments in placeholder order.
            selectionArgList.add(String.valueOf(id));
        }

        if(key!=null){
            // Define 'where' part of query.
            selection =" and (" +  LogTable.LogEntry.COLUMN_NAME_FROM + " LIKE ? or "+ LogTable.LogEntry.COLUMN_NAME_CONTENT + " LIKE ? ) ";
            // Specify arguments in placeholder order.
            selectionArgList.add(key);
            selectionArgList.add(key);
        }
        String[] selectionArgs = selectionArgList.toArray(new String[selectionArgList.size()]);

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                LogTable.LogEntry._ID + " DESC";

        Cursor cursor = db.query(
                LogTable.LogEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );
//        List<LogModel> tLogs = new ArrayList<>();
        List<Long> tLogs = new ArrayList<>();
        while(cursor.moveToNext()) {
            long itemId = cursor.getLong(
                    cursor.getColumnIndexOrThrow(LogTable.LogEntry._ID));
            tLogs.add(itemId);
        }
        cursor.close();



        SharedPreferences sp = context.getSharedPreferences(Define.SP_MSG, Context.MODE_PRIVATE);
        Set<String> msg_set = new HashSet<>();
        msg_set = sp.getStringSet(Define.SP_MSG_SET_KEY, msg_set);
        Log.d(TAG, "msg_set.toString()" + msg_set.toString());
        String getMsg = "";
        for (String str : msg_set) {
            getMsg += str + "\n";
        }
        return getMsg;
    }

}
