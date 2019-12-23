package com.example.framework.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.common.ACache;
import com.example.framework.bean.ResultBean;
import com.example.framework.greendao.DaoMaster;
import com.example.framework.greendao.DaoSession;
import com.example.framework.greendao.ResultBeanDao;

import java.util.List;

public class UserManager {

    private static UserManager userManager;
    private ResultBeanDao resultBeanDao;
    private ResultBean resultBean;
    private Context mContext;

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

    public void setActiveUser(Context context, ResultBean resultBean) {
        ACache aCache = ACache.get(context);
        aCache.put("CurrentUser", resultBean);
        if (iUserInterface != null) {
            iUserInterface.setUserDesc(resultBean);
        }
    }

    public boolean getLoginStatus() {
        SharedPreferences login = mContext.getSharedPreferences("login", Context.MODE_PRIVATE);
        boolean login1 = login.getBoolean("isLogin", false);
        return login1;
    }

    public void savaToken(String token) {
        SharedPreferences token1 = mContext.getSharedPreferences("login", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = token1.edit();
        edit.putString("getToken", token);
        edit.apply();
    }

    public String getToken() {
        SharedPreferences token1 = mContext.getSharedPreferences("login", Context.MODE_PRIVATE);
        String getToken = token1.getString("getToken", "");
        return getToken;
    }

    public ResultBean getUser() {
        ACache aCache = ACache.get(mContext);
        ResultBean resultBean = (ResultBean) aCache.getAsObject("CurrentUser");
        Log.e("####", "" + resultBean.toString());
        return resultBean;
    }

    IUserInterface iUserInterface;

    public void setiUserInterface(IUserInterface iUserInterface) {
        this.iUserInterface = iUserInterface;
    }

    public interface IUserInterface {
        void setUserDesc(ResultBean resultBean);
    }


    public void init(Context context) {
        this.mContext = context;
        SQLiteDatabase writableDatabase = new DaoMaster.DevOpenHelper(context, "user.db").getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(writableDatabase);
        DaoSession daoSession = daoMaster.newSession();
        resultBeanDao = daoSession.getResultBeanDao();
    }
}
