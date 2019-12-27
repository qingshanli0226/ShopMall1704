package com.example.administrator.shaomall.cache;

import android.content.Context;
import android.widget.Toast;

import com.example.commen.network.NetWorkUtils;
import com.example.commen.util.ErrorUtil;
import com.example.commen.util.ShopMailError;
import com.example.net.AppNetConfig;
import com.example.net.MVPObserver;
import com.example.net.RetrofitCreator;
import com.google.gson.Gson;
import com.shaomall.framework.bean.HomeBean;
import com.example.commen.ACache;
import com.example.commen.Constants;


import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

//数据缓存管理类
public class CacheManager {
    private Context mContext;
    private ACache aCache;//缓存
    private static CacheManager instance;
    private List<IHomeReceivedListener> iHomeReceivedListeners = new LinkedList<>();
    private HomeBean.ResultBean acache;

    private CacheManager() {
    }

    public static CacheManager getInstance() {
        if (instance == null)
            instance = new CacheManager();
        return instance;
    }


    public void init(Context context, ACache aCache) {
        this.mContext = context;
        this.aCache = aCache;

    }

    /**
     * 加载数据
     */
    public void getData() {
        if (!NetWorkUtils.isNetWorkAvailable()) {
            return;
        }

        RetrofitCreator.getNetApiService().getData(new HashMap<String, String>(), AppNetConfig.HOME_URL, new HashMap<String, String>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MVPObserver<ResponseBody>() {

                    private Disposable mDisposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        if (!mDisposable.isDisposed()) {
                            try {
                                String string = responseBody.string();
                                HomeBean homeBean = new Gson().fromJson(string, HomeBean.class);
                                int code = homeBean.getCode();

                                if (code == ShopMailError.SUCCESS.getErrorCode()) {//请求成功
                                    saveLocal(homeBean.getResult());
                                } else {
                                    Toast.makeText(mContext, ErrorUtil.dataProcessing(code).getErrorMessage(), Toast.LENGTH_SHORT).show();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            } finally {
                                mDisposable.dispose();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(mContext, ErrorUtil.handlerError(e).getErrorMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    /**
     * 缓存到本地
     *
     * @param bean
     */
    private void saveLocal(HomeBean.ResultBean bean) {
        synchronized (CacheManager.class) {
            for (IHomeReceivedListener listener : iHomeReceivedListeners) {
                listener.onHomeDataReceived(bean);
            }

            //把Bean存储到ACache
            aCache.put(Constants.KEY_HOME_DATA, bean);
        }
    }

    //获取缓存的bean
    public HomeBean.ResultBean getHomeBean() {
        if (aCache != null) {
            acache = (HomeBean.ResultBean) aCache.getAsObject(Constants.KEY_HOME_DATA);
        }
        return acache;
    }


    public void registerListener(IHomeReceivedListener iHomeReceivedListener) {
        synchronized (CacheManager.class) {
            if (!iHomeReceivedListeners.contains(iHomeReceivedListener))
                iHomeReceivedListeners.add(iHomeReceivedListener);
        }
    }

    public void unregisterListener(IHomeReceivedListener iHomeReceivedListener) {
        synchronized (CacheManager.class) {
            if (iHomeReceivedListeners.contains(iHomeReceivedListener)) {
                iHomeReceivedListeners.remove(iHomeReceivedListener);
            }
        }
    }

    public interface IHomeReceivedListener {
        void onHomeDataReceived(HomeBean.ResultBean homeBean);
    }
}
