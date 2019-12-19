package com.example.administrator.shaomall.message;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.shaomall.framework.bean.MessageBean;
import com.shaomall.framework.manager.MessageManager;

import cn.jpush.android.api.CustomMessage;
import cn.jpush.android.api.JPushMessage;
import cn.jpush.android.api.NotificationMessage;
import cn.jpush.android.service.JPushMessageReceiver;

public class JGReceiver extends JPushMessageReceiver {
    private static final String TAG = "JIGUANG";

    /**
     * TODO 连接极光服务器
     */
    @Override
    public void onConnected(Context context, boolean b) {
        super.onConnected(context, b);
        Log.e(TAG, "onConnected");
    }

    /**
     * TODO 注册极光时的回调
     */
    @Override
    public void onRegister(Context context, String s) {
        super.onRegister(context, s);
        Log.e(TAG, "onRegister" + s);
    }

    /**
     * TODO 注册以及解除注册别名时回调
     */
    @Override
    public void onAliasOperatorResult(Context context, JPushMessage jPushMessage) {
        super.onAliasOperatorResult(context, jPushMessage);
        Log.e(TAG, jPushMessage.toString());
    }

    /**
     * TODO 接收到推送下来的通知
     *      可以利用附加字段（notificationMessage.notificationExtras）来区别Notication,指定不同的动作,附加字段是个json字符串
     *      通知（Notification），指在手机的通知栏（状态栏）上会显示的一条通知信息
     */
    @Override
    public void onNotifyMessageArrived(Context context, NotificationMessage notificationMessage) {
        super.onNotifyMessageArrived(context, notificationMessage);
        Log.e(TAG, notificationMessage.toString());
    }

    /**
     * TODO 打开了通知
     *      notificationMessage.notificationExtras(附加字段)的内容处理代码
     *      比如打开新的Activity， 打开一个网页等..
     */
    @Override
    public void onNotifyMessageOpened(Context context, NotificationMessage notificationMessage) {
        super.onNotifyMessageOpened(context, notificationMessage);
        Log.e(TAG, notificationMessage.notificationExtras);
    }


    /**
     * TODO 接收到推送下来的自定义消息
     *      自定义消息不是通知，默认不会被SDK展示到通知栏上，极光推送仅负责透传给SDK。其内容和展示形式完全由开发者自己定义。
     *      自定义消息主要用于应用的内部业务逻辑和特殊展示需求
     */
    @Override
    public void onMessage(Context context, CustomMessage customMessage) {
        super.onMessage(context, customMessage);
        Log.e(TAG, "onMessage");
        String messageId = customMessage.messageId;
        String message = customMessage.message;
        MessageManager.getInstance(context).insertData(new MessageBean(messageId,message,"no"));
        Toast.makeText(context, "您有新的消息请注意查收", Toast.LENGTH_SHORT).show();
    }
}
