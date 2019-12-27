package com.shaomall.framework.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.commen.network.NetChangeObserver;
import com.example.commen.network.NetType;
import com.example.commen.network.NetWorkUtils;
import com.example.commen.network.NetworkManager;
import com.gyf.immersionbar.ImmersionBar;
import com.shaomall.framework.R;
import com.shaomall.framework.manager.ActivityInstanceManager;

public abstract class BaseActivity extends AppCompatActivity implements NetChangeObserver {

    /**
     * 获取TAG的activity名称
     */
    protected Context mContext;
    private ImmersionBar immersionBar;
    private AlertDialog alertDialog;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        flagFullScreen();
        setContentView(setLayoutId());
        mContext = this.getApplicationContext();

        immersionBar = ImmersionBar.with(this);
        immersionBar.init();

        //activity 管理类
        ActivityInstanceManager.addActivity(this);
        NetworkManager.getDefault().setListener(this);

        setNetWorkHint();

        initView();
        initData();
    }

    /**
     * 网络提示
     */
    protected void setNetWorkHint() {
        //网络状态提示
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("设置网络");
        builder.setMessage("没有网络是否要打开网络连接？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                NetWorkUtils.openNetSetting(BaseActivity.this);
            }
        });
        builder.setNegativeButton("取消", null);
        alertDialog = builder.create();
    }

    public void flagFullScreen() {
    }

    @LayoutRes
    protected abstract int setLayoutId();

    protected abstract void initView();

    protected abstract void initData();


    /**
     * 获取android手机状态栏的高度
     *
     * @return
     */
    public int getStatusBarHeight() {
        Resources resources = mContext.getResources();
        //获取标志符
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        //获取尺寸像素大小
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }


    protected void animOutActivity() {
        ActivityInstanceManager.removeActivity(this);
        overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
    }

    /**
     * Intent 跳转
     *
     * @param clazz
     */
    protected void toClass(Class<? extends Activity> clazz) {
        toClass(clazz, null);
    }

    /**
     * 可以传送下标
     *
     * @param clazz
     * @param index
     */
    protected void toClass(Class<? extends Activity> clazz, int index) {
        Bundle bundle = new Bundle();
        bundle.putInt("index", index);
        toClass(clazz, bundle);
    }

    /**
     * Intent带值跳转
     *
     * @param clazz
     * @param bundle
     */
    protected void toClass(Class<? extends Activity> clazz, Bundle bundle) {
        Intent intent = new Intent(mContext, clazz);
        if (bundle != null && bundle.size() != 0) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
    }


    /**
     * 带返回值的跳转
     *
     * @param clazz
     * @param bundle
     * @param requestCode
     */
    protected void toClass(Class<? extends Activity> clazz, Bundle bundle, int requestCode) {
        Intent intent = new Intent(mContext, clazz);
        if (bundle != null && bundle.size() != 0) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
    }

    @Override
    public void onConnected(NetType type) {
//        if (alertDialog != null) {
//            //取消显示
//            if (alertDialog.isShowing()) {
//                alertDialog.cancel();
//            }
//        }
        if (type == NetType.CMWAP) {
            toast("当前处于移动网络状态, 请注意流量使用", false);
        }
    }

    @Override
    public void onDisConnected() {
//        if (alertDialog != null) {
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            if (!alertDialog.isShowing()) {
//                alertDialog.show();
//
//            }
//        }
    }

    /**
     * 吐司提示
     *
     * @param text   文本信息
     * @param isLong 显示时长
     */
    public void toast(String text, boolean isLong) {
        Toast.makeText(mContext, text, isLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show();
    }

    /**
     * 吐司提示
     *
     * @param resId  文本资源id信息
     * @param isLong 显示时长
     */
    public void toast(int resId, boolean isLong) {
        Toast.makeText(mContext, resId, isLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onDestroy() {
        if (immersionBar != null) {
            ImmersionBar.destroy(this, null);
        }
        NetworkManager.getDefault().unSetListener(this);
        //内存泄漏检测
        BaseApplication.getRefWatcher().watch(this);
        ActivityInstanceManager.removeActivity(this);
        super.onDestroy();
    }

}
