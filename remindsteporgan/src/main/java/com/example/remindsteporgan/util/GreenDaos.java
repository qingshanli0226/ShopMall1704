package com.example.remindsteporgan.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.remindsteporgan.base.Bean;
import com.example.view.demogreendao.BeanDao;
import com.example.view.demogreendao.DaoMaster;
import com.example.view.demogreendao.DaoSession;

import java.util.List;

public class GreenDaos {
    public Context context;
    private BeanDao beanDao;
    @SuppressLint("StaticFieldLeak")
    private static GreenDaos greenDaos;

    public static GreenDaos getGreenDaos() {
        if (greenDaos==null){
            synchronized (GreenDaos.class){
                if (greenDaos==null){
                    greenDaos=new GreenDaos();
                }
            }
        }
        return greenDaos;
    }
    public void init(Context context){
        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(context, "ssh");
        SQLiteDatabase writableDatabase = openHelper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(writableDatabase);
        DaoSession daoSession = daoMaster.newSession();
        beanDao = daoSession.getBeanDao();

    }
    public Long inserte(Bean bean){

        long insert = beanDao.insert(bean);
        return insert;
    }
    public void inserteList(List<Bean> beans){
        beanDao.insertInTx(beans);

    }

    public List<Bean> queryRaw(String where, String... selectionArg){
        List<Bean> beans = beanDao.queryRaw(where, selectionArg);
        return beans;
    }

    public void delete(Bean bean){
        beanDao.delete(bean);
    }
}

