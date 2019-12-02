package com.shaomall.framework.base.presenter;

import com.example.net.ResEntity;
import com.example.net.RetrofitCreator;
import com.google.gson.Gson;
import com.shaomall.framework.base.view.IBaseView;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public abstract class BasePresenter<T> implements IBasePresenter<T> {
    private IBaseView<T> iBaseView;

    /**
     * Get请求
     *
     * @param requestCode
     */
    @Override
    public void doGetHttpRequest(int requestCode) {
        RetrofitCreator.getNetApiService().getData(getHeaderParams(), getPath(), getParams())
                .subscribeOn(Schedulers.io()) //订阅
                .observeOn(AndroidSchedulers.mainThread()) //观察
                .subscribe(new Observer<ResponseBody>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String string = responseBody.string();

                            //判断数据是否是列表
                            if (isList()) {
                                List<ResEntity<T>> resEntityList = new Gson().fromJson(string, getBeanType());



                            }


                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    @Override
    public void doPostHttpRequest(int requestCode) {

    }


    protected abstract Type getBeanType();

    protected boolean isList() {
        return false;
    }


    @Override
    public void attachView(IBaseView<T> iBaseView) {
        this.iBaseView = iBaseView;
    }

    @Override
    public void detachView() {
        if (iBaseView != null) {
            this.iBaseView = null;
        }
    }


    /**
     * 请求头
     *
     * @return
     */
    public HashMap<String, String> getHeaderParams() {
        return new HashMap<>();
    }


    /**
     * 请求参数
     *
     * @return
     */
    public HashMap<String, String> getParams() {
        return new HashMap<>();
    }

    /**
     * 请求路径
     *
     * @return
     */
    public abstract String getPath();
}