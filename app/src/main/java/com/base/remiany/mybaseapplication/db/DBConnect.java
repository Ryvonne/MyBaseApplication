package com.base.remiany.mybaseapplication.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.base.remiany.mybaseapplication.utils.Log;

import java.util.ArrayList;
import java.util.List;


public class DBConnect extends SQLiteOpenHelper {
    public static final String DATEBASE_NAME = "remiany";
    public static final int DATEBASE_VERSION = 1;

    private static DBConnect mInstance;

    public static DBConnect getInstance(Context context) {
        if (mInstance == null) {
            Log.i("DBConnect is new");
            mInstance = new DBConnect(context, DATEBASE_NAME, null, 1);
        }
        return mInstance;
    }

    private DBConnect(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        this(context, name, factory, version, null);
    }

    private DBConnect(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("DBConnect onCreate");
        createTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void createTable(SQLiteDatabase db) {
        String sql = getCreateSQLString(createTableInfo());
        Log.i(sql);
        db.execSQL(sql);
    }

    private TableInfo createTableInfo() {
        TableInfo tableInfo = new TableInfo("myinfo");

        ColInfo info1 = new ColInfo("NAME", "VARCHAR");
        ColInfo info2 = new ColInfo("NUMBER", "INTEGER", true);
        ColInfo info3 = new ColInfo("SEX", "VARCHAR");

        tableInfo.add(info1);
        tableInfo.add(info2);
        tableInfo.add(info3);

        return tableInfo;
    }

    private String getCreateSQLString(TableInfo tableInfo) {
        String sql = "create table if not exists " + tableInfo.tableName + "(";
        List<ColInfo> colInfos = tableInfo.colInfos;
        for (int i = 0; i < colInfos.size(); i++) {
            ColInfo info = colInfos.get(i);
            sql += info.ColName + " " + info.ColType;
            if (info.isprimarykey) {
                sql += " primary key";
            }
            sql += ",";
        }
        if (sql.contains(",")) {
            sql = sql.substring(0, sql.length() - 1);
        }
        sql += ")";
        return sql;
    }


    private class TableInfo {
        String tableName;
        List<ColInfo> colInfos = new ArrayList<ColInfo>();

        public TableInfo(String tableName) {
            this.tableName = tableName;
        }

        public List<ColInfo> getColInfos() {
            return colInfos;
        }

        public void add(ColInfo info) {
            colInfos.add(info);
        }
    }

    private class ColInfo {
        String ColName;
        String ColType;
        boolean isprimarykey;

        public ColInfo(String colName, String colType) {
            ColName = colName;
            ColType = colType;
            this.isprimarykey = false;
        }

        public ColInfo(String colName, String colType, boolean isprimarykey) {
            ColName = colName;
            ColType = colType;
            this.isprimarykey = isprimarykey;
        }
    }

}
