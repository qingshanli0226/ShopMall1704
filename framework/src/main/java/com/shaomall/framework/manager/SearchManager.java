package com.shaomall.framework.manager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.commen.database.SearchSQLiteHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class SearchManager {
    // 用于存放历史搜索记录
    private SearchSQLiteHelper helper ;
    private SQLiteDatabase db;
    private Context context;
    private static SearchManager instance;

    //创建单例
    public static SearchManager getInstance(){
        if (instance == null)
            synchronized (SearchManager.class){
            if (instance == null)
                instance = new SearchManager();
            }
        return instance;
    }
        public void init(final  Context context){
        this.context = context;
        helper = new SearchSQLiteHelper(context);
            db = helper.getReadableDatabase();

        }

        //清空搜索历史
        public void deleteData(){
            db.execSQL("delete from records");
        }
    //插入搜索历史
    public void insertData(String tempName) {
        db = helper.getWritableDatabase();
        db.execSQL("insert into records(name) values('" + tempName + "')");
    }
    //检查是否有该历史
    public boolean hasData(String tempName) {
        // 从数据库中Record表里找到name=tempName的id
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name =?", new String[]{tempName});
        //  判断是否有下一个
        return cursor.moveToNext();
    }
    //查询所有记录
    public List<String> selectAll(){
        Cursor records = db.query("records", null, null, null, null, null, null);
        List<String> list = new ArrayList<>();
        while (records.moveToNext()){
            String name = records.getString(records.getColumnIndex("name"));
            list.add(name);
        }
        return list;
    }
}
