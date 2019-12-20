package com.example.dimensionleague;

import android.app.Application;
import com.example.common.utils.SPUtil;
import com.example.framework.manager.ErrorHandler;
import com.example.framework.manager.NetConnectManager;
import com.example.point.StepIsSupport;
import com.example.point.stepmanager.StepPointManager;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

/**
 * author:李浩帆
 */
public class MineApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Application applicationContext = this;
        //TODO 网络
        NetConnectManager.getInstance().init(applicationContext);
        //TODO SP存储工具类
        SPUtil.init(applicationContext);
        //TODO 异常捕获
//        ErrorHandler.getInstance().initErrorHandler(applicationContext);
        //支持计步的话就查找历史记录-否则就什么也不做

        if (!NetConnectManager.getInstance().isNetConnectStatus() && new StepIsSupport().isSupportStepCountSensor(this)) {
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
        UMConfigure.setLogEnabled(true);
        // 选用AUTO页面采集模式
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
        ZXingLibrary.initDisplayOpinion(this);
    }
}
