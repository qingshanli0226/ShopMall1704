package com.example.framework.manager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.framework.bean.ResultBean;
import com.example.framework.greendao.DaoMaster;
import com.example.framework.greendao.DaoSession;
import com.example.framework.greendao.ResultBeanDao;

import java.util.List;

public class UserManager {

    private static UserManager userManager;
    private ResultBeanDao resultBeanDao;
    private ResultBean resultBean;

    public static UserManager getInstance() {
        if (userManager == null) {
            return userManager = new UserManager();
        }
        return userManager;
    }

    public void addUser(ResultBean resultBean) {
        resultBeanDao.insert(resultBean);
    }

    public ResultBean fendUser(String userName) {
        List<ResultBean> list = resultBeanDao.queryBuilder().where(ResultBeanDao.Properties.Name.eq(userName)).list();
        return list.get(0);
    }

    public void setActiveUser(ResultBean resultBean) {
        this.resultBean = resultBean;
    }

    public ResultBean getUser() {
        return resultBean;
    }

    public void init(Context context) {
        SQLiteDatabase writableDatabase = new DaoMaster.DevOpenHelper(context, "user.db").getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(writableDatabase);
        DaoSession daoSession = daoMaster.newSession();
        resultBeanDao = daoSession.getResultBeanDao();
    }
}
