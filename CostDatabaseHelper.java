package com.example.kowshick.bazarcost.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CostDatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="Cost_db";
    public static final int DATABASE_VERSION=1;

    public static final String TABLE_COST="tbl_cost";
    public static final String TBL_COST_ID="_id";
    public static final String TBL_COST_DATE="cost_date";
    public static final String TBL_COST_CATEGORY="cost_category";
    public static final String TBL_COST_PRUDUCT="cost_product";
    public static final String TBL_COST_QUANTITY="cost_quantity";
    public static final String TBL_COST_PRICE="cost_price";
    public static final String TBL_DROP="DROP TABLE CREATE_TABLE_COST";
    //List
    public static final String TABLE_LIST="tbl_list";
    public static final String TBL_LIST_ID="_id";
    public static final String TBL_LIST_Name="list_name";

    public static final String CREATE_TABLE_COST =
            "create table "+TABLE_COST+"("+
                    TBL_COST_ID+" integer primary key, "+
                    TBL_COST_DATE+" , "+
                    TBL_COST_CATEGORY+" text, "+
                    TBL_COST_PRUDUCT+" text, "+
                    TBL_COST_QUANTITY+" text, "+
                    TBL_COST_PRICE+" NUMERIC);";


    //List Table
    public static final String CREATE_TABLE_LIST=
            "create table "+TABLE_LIST+"("+
                    TBL_LIST_ID+" integer primary key, "+
                    TBL_LIST_Name+" text);";

    public CostDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_COST);
        db.execSQL(CREATE_TABLE_LIST);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
