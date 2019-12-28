package com.shaomall.framework.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.commen.network.NetChangeObserver;
import com.example.commen.network.NetType;
import com.example.commen.network.NetWorkUtils;
import com.example.commen.network.NetworkManager;
import com.shaomall.framework.R;

public abstract class BaseFragment extends Fragment implements NetChangeObserver {
    protected Context mContext;

    /**
     * 当fragment与activity发生关联时调用
     *
     * @param context 与之相关联的activity
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        NetworkManager.getDefault().setListener(this);
        return inflater.inflate(setLayoutId(), container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initView(view, savedInstanceState);
        initData();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @LayoutRes
    public abstract int setLayoutId();

    //组件化
    protected abstract void initView(View view, Bundle savedInstanceState);

    //设置数据等逻辑
    protected abstract void initData();

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
        //        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
        getActivity().overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
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
        getActivity().startActivityForResult(intent, requestCode);
        getActivity().overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
    }

    @Override
    public void onConnected(NetType type) {

    }

    @Override
    public void onDisConnected() {

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
    public void onDestroy() {
        NetworkManager.getDefault().unSetListener(this);
        //内存泄漏检测
        BaseApplication.getRefWatcher().watch(this);
        super.onDestroy();
    }
}
