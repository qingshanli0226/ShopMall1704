package com.example.framework.manager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.framework.greendao.DaoMaster;
import com.example.framework.greendao.DaoSession;
import com.example.framework.greendao.UserBeanDao;

import java.util.List;

public class UserManager {

    private static UserManager userManager;
    private UserBeanDao userBeanDao;

    public static UserManager getInstance() {
        if (userManager == null) {
            return userManager = new UserManager();
        }
        return userManager;
    }

    public void addUser(UserBean userBean) {
        if (userBeanDao != null) {
            userBeanDao.insert(userBean);
        }
    }

    public UserBean fendUser(String userName) {
        List<UserBean> list = userBeanDao.queryBuilder().where(UserBeanDao.Properties.Name.eq(userName)).list();
        return list.get(0);
    }

    public void init(Context context) {
        SQLiteDatabase sqLiteDatabase = new DaoMaster.DevOpenHelper(context, "user.db").getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(sqLiteDatabase);
        DaoSession daoSession = daoMaster.newSession();
        userBeanDao = daoSession.getUserBeanDao();
    }

}
