package com.shaomall.framework.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.commen.database.DataBaseHelper;
import com.shaomall.framework.bean.MessageBean;

import java.util.ArrayList;
import java.util.List;

public class MessageManager {
    private Context context;
    private static MessageManager instache;
    private DataBaseHelper mHelper;
    private SQLiteDatabase db;
    private MessageListener messageListener;

    public MessageManager(Context context) {
        this.context = context;
        mHelper = new DataBaseHelper(context, "message.db", null, 1);
        db = mHelper.getWritableDatabase();
    }

    public static MessageManager getInstance(Context context) {
        if (instache == null) {
            instache = new MessageManager(context);
        }
        return instache;
    }

    //添加数据
    public void insertData(MessageBean messageBean) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("messageId", messageBean.getMessageId());
        contentValues.put("message", messageBean.getMessage());
        contentValues.put("isRead", messageBean.getIsRead());
        db.insert("usermessage", null, contentValues);
        List<MessageBean> messageBeans = qurayNotReadData();
        int size = messageBeans.size();
        this.messageListener.getMessage(messageBean, size);
    }

    //更新数据
    public void updateData(String messageId) {
        String sql = "update usermessage set isRead = 'yes' where messageId = " + messageId;
        db.execSQL(sql);
    }

public List<MessageBean> selectAll(){
    Cursor usermessage = db.query("usermessage", null, null, null, null, null, null);
    ArrayList<MessageBean> messages = new ArrayList<>();
    while (usermessage.moveToNext())
    {
        String messageId = usermessage.getString(0);
        String message = usermessage.getString(1);
        String isRead = usermessage.getString(2);
        messages.add(new MessageBean(messageId, message, isRead));
    }
    return messages;
}
    public List<MessageBean> qurayIsReadData() {
        Cursor cursor = db.query("usermessage", null, "isRead = ?", new String[]{"yes"}, null, null, null);
        List<MessageBean> messages = new ArrayList<>();
        while (cursor.moveToNext()) {
            String messageId = cursor.getString(0);
            String message = cursor.getString(1);
            String isRead = cursor.getString(2);
            messages.add(new MessageBean(messageId, message, isRead));
        }
        return messages;
    }

    public List<MessageBean> qurayNotReadData() {
        Cursor cursor = db.query("usermessage", null, "isRead = ?", new String[]{"no"}, null, null, null);
        List<MessageBean> messages = new ArrayList<>();
        while (cursor.moveToNext()) {
            String messageId = cursor.getString(0);
            String message = cursor.getString(1);
            String isRead = cursor.getString(2);
            messages.add(new MessageBean(messageId, message, isRead));
        }
        return messages;
    }

    public int gitNotReadNum() {
        List<MessageBean> messageBeans = qurayNotReadData();
        int size = messageBeans.size();
        return size;
    }

    //接收新消息接口
    public interface MessageListener {
        void getMessage(MessageBean messageBean, int messageNum);
    }

    public void registerMessageListener(MessageListener messageListener) {
        this.messageListener = messageListener;
    }

    public void unRegisterMessageListener(MessageListener messageListener) {
        if (messageListener != null)
            messageListener = null;
    }
}
