package com.shaomall.framework.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.gyf.immersionbar.ImmersionBar;
import com.shaomall.framework.manager.ActivityInstanceManager;

public abstract class BaseActivity extends AppCompatActivity {

    /**
     * 获取TAG的activity名称
     */
    protected Activity     mActivity;
    private   ImmersionBar immersionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //activity 管理类
        ActivityInstanceManager.addActivity(this);

        initView();
        initData();
    }

    protected abstract void initView();

    @LayoutRes
    protected abstract int setLayoutId();

    protected abstract void initData();

    /**
     * 简化findViewById()
     *
     * @param resId
     * @param <T>
     * @return
     */
    protected <T extends View> T findViewByMe(@IdRes int resId) {
        return (T) findViewById(resId);
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
     * Intent带值跳转
     *
     * @param clazz
     * @param bundle
     */
    protected void toClass(Class<? extends Activity> clazz, Bundle bundle) {
        Intent intent = new Intent(mActivity, clazz);
        if (bundle != null && bundle.size() != 0) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }


    /**
     * 带返回值的跳转
     *
     * @param clazz
     * @param bundle
     * @param requestCode
     */
    protected void toClass(Class<? extends Activity> clazz, Bundle bundle, int requestCode) {
        Intent intent = new Intent(mActivity, clazz);
        if (bundle != null && bundle.size() != 0) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }


    /**
     * 吐司提示
     *
     * @param text   文本信息
     * @param isLong 显示时长
     */
    public void toast(String text, boolean isLong) {
        Toast.makeText(mActivity, text, isLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show();
    }

    /**
     * 吐司提示
     *
     * @param resId  文本资源id信息
     * @param isLong 显示时长
     */
    public void toast(int resId, boolean isLong) {
        Toast.makeText(mActivity, resId, isLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (immersionBar != null)
            ImmersionBar.destroy(this, null);
    }
}
