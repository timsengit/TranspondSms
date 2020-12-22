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
import com.tim.tsms.transpondsms.model.RuleModel;
import com.tim.tsms.transpondsms.model.RuleTable;
import com.tim.tsms.transpondsms.model.SenderModel;
import com.tim.tsms.transpondsms.model.SenderTable;
import com.tim.tsms.transpondsms.model.vo.LogVo;

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

    public static List<LogVo> getLog(Long id, String key) {
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                LogTable.LogEntry.TABLE_NAME+"."+BaseColumns._ID,
                LogTable.LogEntry.TABLE_NAME+"."+LogTable.LogEntry.COLUMN_NAME_FROM,
                LogTable.LogEntry.TABLE_NAME+"."+LogTable.LogEntry.COLUMN_NAME_CONTENT,
                RuleTable.RuleEntry.TABLE_NAME+"."+RuleTable.RuleEntry.COLUMN_NAME_FILED,
                RuleTable.RuleEntry.TABLE_NAME+"."+RuleTable.RuleEntry.COLUMN_NAME_CHECK,
                RuleTable.RuleEntry.TABLE_NAME+"."+RuleTable.RuleEntry.COLUMN_NAME_VALUE,
                SenderTable.SenderEntry.TABLE_NAME+"."+SenderTable.SenderEntry.COLUMN_NAME_NAME,
                SenderTable.SenderEntry.TABLE_NAME+"."+SenderTable.SenderEntry.COLUMN_NAME_TYPE
        };
        // Define 'where' part of query.
        String selection = " 1 ";
        // Specify arguments in placeholder order.
        List<String> selectionArgList = new ArrayList<>();
        if(id!=null){
            // Define 'where' part of query.
            selection +=" and " + LogTable.LogEntry.TABLE_NAME+"."+LogTable.LogEntry._ID + " = ? ";
            // Specify arguments in placeholder order.
            selectionArgList.add(String.valueOf(id));
        }

        if(key!=null){
            // Define 'where' part of query.
            selection =" and (" +  LogTable.LogEntry.TABLE_NAME+"."+LogTable.LogEntry.COLUMN_NAME_FROM + " LIKE ? or "+ LogTable.LogEntry.TABLE_NAME+"."+LogTable.LogEntry.COLUMN_NAME_CONTENT + " LIKE ? ) ";
            // Specify arguments in placeholder order.
            selectionArgList.add(key);
            selectionArgList.add(key);
        }
        String[] selectionArgs = selectionArgList.toArray(new String[selectionArgList.size()]);

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                LogTable.LogEntry.TABLE_NAME+"."+LogTable.LogEntry._ID + " DESC";

        Cursor cursor = db.query(
                // The table to query
                LogTable.LogEntry.TABLE_NAME
                        +" LEFT JOIN "+RuleTable.RuleEntry.TABLE_NAME+" ON "+LogTable.LogEntry.TABLE_NAME+"."+LogTable.LogEntry.COLUMN_NAME_RULE_ID+"="+RuleTable.RuleEntry.TABLE_NAME+"."+RuleTable.RuleEntry._ID
                        +" LEFT JOIN "+ SenderTable.SenderEntry.TABLE_NAME+" ON "+SenderTable.SenderEntry.TABLE_NAME+"."+SenderTable.SenderEntry._ID+"="+RuleTable.RuleEntry.TABLE_NAME+"."+RuleTable.RuleEntry.COLUMN_NAME_SENDER_ID,
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );
        List<LogVo> LogVos = new ArrayList<>();
        while(cursor.moveToNext()) {

            String itemfrom = cursor.getString(
                    cursor.getColumnIndexOrThrow(LogTable.LogEntry.TABLE_NAME+"."+LogTable.LogEntry.COLUMN_NAME_FROM));
            String content = cursor.getString(
                    cursor.getColumnIndexOrThrow(LogTable.LogEntry.TABLE_NAME+"."+LogTable.LogEntry.COLUMN_NAME_CONTENT));
            String ruleFiled = cursor.getString(
                    cursor.getColumnIndexOrThrow(RuleTable.RuleEntry.TABLE_NAME+"."+RuleTable.RuleEntry.COLUMN_NAME_FILED));
            String ruleCheck = cursor.getString(
                    cursor.getColumnIndexOrThrow(RuleTable.RuleEntry.TABLE_NAME+"."+RuleTable.RuleEntry.COLUMN_NAME_CHECK));
            String ruleValue = cursor.getString(
                    cursor.getColumnIndexOrThrow(RuleTable.RuleEntry.TABLE_NAME+"."+RuleTable.RuleEntry.COLUMN_NAME_VALUE));
            String senderName = cursor.getString(
                    cursor.getColumnIndexOrThrow(SenderTable.SenderEntry.TABLE_NAME+"."+SenderTable.SenderEntry.COLUMN_NAME_NAME));
            Integer senderType = cursor.getInt(
                    cursor.getColumnIndexOrThrow(SenderTable.SenderEntry.TABLE_NAME+"."+SenderTable.SenderEntry.COLUMN_NAME_TYPE));

            String rule = RuleModel.getRuleMatch(ruleFiled,ruleCheck,ruleValue)+" 转发到 "+senderName;
            int senderImageId = SenderModel.getImageId(senderType);
            LogVo logVo = new LogVo(itemfrom,content,rule,senderImageId);

            LogVos.add(logVo);
        }
        cursor.close();
        return LogVos;
    }

}
