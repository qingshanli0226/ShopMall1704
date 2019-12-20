package com.example.framework.sql;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.framework.manager.StepManager;

public class HourSql extends SQLiteOpenHelper {
    public HourSql(@Nullable Context context) {
        super(context, "hour.db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //历史记录
        sqLiteDatabase.execSQL("create table history(_id integer primary key autoincrement,time varchar(50),date varchar(50),currentStep integer)");
        //消息
        sqLiteDatabase.execSQL("create table mess(_id integer primary key autoincrement,time varchar(50),date varchar(50),currentStep integer,integral integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
