package com.example.framework.manager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.framework.bean.MessageBean;
import com.example.framework.greendao.DaoMaster;
import com.example.framework.greendao.DaoSession;
import com.example.framework.greendao.MessageBeanDao;

import java.util.List;

/**
 * 消息数据库
 */
public class MessageManager {

    private static MessageManager messageManager;
    private MessageBeanDao messageBeanDao;

    public static MessageManager getMessageManager() {

        if (messageManager == null){
            messageManager = new MessageManager();
        }

        return messageManager;
    }

    //添加消息数据
    public void addMessage(MessageBean messageBean){
        if (messageBeanDao != null){
            messageBeanDao.insert(messageBean);
        }
    }

    //修改消息数据
    public boolean updateMessage(MessageBean messageBean){
        try {
            messageBeanDao.update(messageBean);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    //查询消息数据
    public List<MessageBean> getMessage(){
        List<MessageBean> messageBeans = messageBeanDao.loadAll();
        return messageBeans;
    }

    //提供接口回调数据库存储
    public void setMessageManager(String nameMessage,String contentMessage){
        MessageBean messageBean = new MessageBean();
        messageBean.setName("");
        messageBean.setIsMessage(false);
        messageBean.setNameMessage(nameMessage);
        messageBean.setContentMessage(contentMessage);
        addMessage(messageBean);
    }

    //管理类初始化
    public void init(Context context){
        SQLiteDatabase sqLiteDatabase = new DaoMaster.DevOpenHelper(context, "message.db").getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(sqLiteDatabase);
        DaoSession daoSession = daoMaster.newSession();
        messageBeanDao = daoSession.getMessageBeanDao();
    }

}
