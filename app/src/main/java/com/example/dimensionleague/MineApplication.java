package com.example.dimensionleague;

import android.app.Application;
import android.util.Log;

import com.example.common.utils.SPUtil;
import com.example.framework.manager.ErrorHandler;
import com.example.framework.manager.NetConnectManager;
import com.example.point.StepIsSupport;
import com.example.point.stepmanager.StepPointManager;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;

/**
 * author:李浩帆
 */
public class MineApplication extends Application {
    private Application applicationContext;
    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this;
        //TODO 网络
        NetConnectManager.getInstance().init(applicationContext);
        //TODO SP存储工具类
        SPUtil.init(applicationContext);
        //TODO 异常捕获
//        ErrorHandler.getInstance().initErrorHandler(applicationContext);
        //支持计步的话就查找历史记录-否则就什么也不做
        if (new StepIsSupport().isSupportStepCountSensor(this)) {
            StepPointManager.getInstance(this).init();
        }
        //友盟推送
        // 在此处调用基础组件包提供的初始化函数 相应信息可在应用管理 -> 应用信息 中找到 http://message.umeng.com/list/apps
// 参数一：当前上下文context；
// 参数二：应用申请的Appkey（需替换）；
// 参数三：渠道名称；
// 参数四：设备类型，必须参数，传参数为UMConfigure.DEVICE_TYPE_PHONE则表示手机；传参数为UMConfigure.DEVICE_TYPE_BOX则表示盒子；默认为手机；
// 参数五：Push推送业务的secret 填充Umeng Message Secret对应信息（需替换）
        UMConfigure.init(this, "5def1638570df396480008ec", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "261fae3d46309422f7a86ba0df58c796");
        /**
         * 设置组件化的Log开关       * 参数: boolean 默认为false，如需查看LOG设置为true
         */
        UMConfigure.setLogEnabled(true);
        // 选用AUTO页面采集模式
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);


//获取消息推送代理示例        PushAgent mPushAgent = PushAgent.getInstance(this);
//////注册推送服务，每次调用register方法都会回调该接口
////        mPushAgent.register(new IUmengRegisterCallback() {
////            @Override
////            public void onSuccess(String deviceToken) {
////                //注册成功会返回deviceToken deviceToken是推送消息的唯一标志
////                Log.i("wzy","注册成功：deviceToken：-------->  " + deviceToken);
////            }
////            @Override
////            public void onFailure(String s, String s1) {
////                Log.e("wzy","注册失败：-------->  " + "s:" + s + ",s1:" + s1);
////            }
////        });

    }
}
