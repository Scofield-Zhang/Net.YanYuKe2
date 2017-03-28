package net.youlvke.yanyuke.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class SearchRecordDao {
    private int DB_VERSION = 1;
    private static SearchRecordDao instance;
    private SearchRecordSQLiteOpenHelper dbHelper;
    private String dbName = "temp.db";

    private SearchRecordDao(Context context) {
        dbHelper = new SearchRecordSQLiteOpenHelper(context, dbName, null, DB_VERSION);
    }

    public synchronized static SearchRecordDao getInstance(Context context) {
        if (instance == null) {
            instance = new SearchRecordDao(context);
        }
        return instance;
    }

    //添加搜索记录
    public void addRecords(String record) {
        SQLiteDatabase recordsDb = dbHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", record);
        //添加
        recordsDb.insert("records", null, values);
        //关闭
        recordsDb.close();

    }

    //判断是否含有该搜索记录
    public boolean isHasRecord(String record) {
        boolean isHasRecord = false;
        SQLiteDatabase recordsDb = dbHelper.getReadableDatabase();

        Cursor cursor = recordsDb.query("records", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            if (record.equals(cursor.getString(cursor.getColumnIndexOrThrow("name")))) {
                isHasRecord = true;
            }
        }
        //关闭数据库
        recordsDb.close();
        return isHasRecord;
    }

    //获取全部搜索记录
    public List<String> getRecordsList() {
        List<String> recordsList = new ArrayList<>();
        SQLiteDatabase recordsDb = dbHelper.getReadableDatabase();
        Cursor cursor = recordsDb.query("records", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            recordsList.add(name);
        }
        //关闭数据库
        recordsDb.close();
        return recordsList;
    }

    //模糊查询
    public List<String> querySimlarRecord(String record) {
        String queryStr = "select * from records where name like '%" + record + "%' order by name ";
        List<String> similarRecords = new ArrayList<>();
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery(queryStr, null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            similarRecords.add(name);
        }
        return similarRecords;
    }

    //清空搜索记录
    public void deleteAllRecords() {
        SQLiteDatabase recordsDb = dbHelper.getWritableDatabase();
        recordsDb.execSQL("delete from records");
        recordsDb.close();
    }

}
