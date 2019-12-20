package com.example.framework.manager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.framework.bean.AddressBarBean;
import com.example.framework.bean.MessageBean;
import com.example.framework.greendao.AddressBarBeanDao;
import com.example.framework.greendao.DaoMaster;
import com.example.framework.greendao.DaoSession;

import java.util.List;

/**
 * 地址数据库
 */
public class AddressBarManager {

    private static AddressBarManager addressBarManager;
    private AddressBarBeanDao addressBarBeanDao;

    public static AddressBarManager getAddressBarManager() {

        if (addressBarManager == null){
            addressBarManager = new AddressBarManager();
        }

        return addressBarManager;
    }

    //添加消息数据
    public void addMessage(AddressBarBean addressBarBean){
        if (addressBarBeanDao != null){
            addressBarBeanDao.insert(addressBarBean);
        }
    }

    //修改消息数据
    public boolean updateMessage(AddressBarBean addressBarBean){
        try {
            addressBarBeanDao.update(addressBarBean);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    //查询消息数据
    public List<AddressBarBean> getMessage(){
        List<AddressBarBean> addressBarBeans = addressBarBeanDao.loadAll();
        return addressBarBeans;
    }

    //管理类初始化
    public void init(Context context){
        SQLiteDatabase sqLiteDatabase = new DaoMaster.DevOpenHelper(context, "address.db").getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(sqLiteDatabase);
        DaoSession daoSession = daoMaster.newSession();
        addressBarBeanDao = daoSession.getAddressBarBeanDao();
    }

}
