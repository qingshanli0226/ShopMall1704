package com.shaomall.framework.base.presenter;

import com.example.commen.ErrorUtil;
import com.example.commen.LoadingPageConfig;
import com.example.commen.ShopMailError;
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
    public void doGetHttpRequest(final int requestCode) {
        RetrofitCreator.getNetApiService().getData(getHeaderParams(), getPath(), getParams())
                .subscribeOn(Schedulers.io()) //订阅
                .observeOn(AndroidSchedulers.mainThread()) //观察
                .subscribe(new Observer<ResponseBody>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        //提示用户正在加载, 显示加载页
                        setLoadingPager(LoadingPageConfig.STATE_LOADING_CODE);
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String string = responseBody.string();

                            //判断数据是否是列表
                            if (isList()) {
                                ResEntity<List<T>> resEntityList = new Gson().fromJson(string, getBeanType());

                                //获取数据列表成功
                                if (resEntityList.getCode() == 200) { //数据请求成功
                                    if (iBaseView != null) {
                                        iBaseView.onRequestHttpDataListSuccess(requestCode, resEntityList.getMessage(), resEntityList.getResult());
                                    }
                                } else {
                                    //获取列表数据失败
                                    if (iBaseView != null) {
                                        iBaseView.onRequestHttpDataFailed(requestCode, ShopMailError.BUSINESS_ERROR);
                                    }
                                }

                            } else { //不是列表
//                                ResEntity<T> resEntity = new Gson().fromJson(string, getBeanType());
                                T resEntity = new Gson().fromJson(string, getBeanType());

//                                if (resEntity.getCode() == 200) { //数据请求成功
//                                    if (iBaseView != null) {
                                        iBaseView.onRequestHttpDataSuccess(requestCode, "", resEntity);
//                                    } else {
//                                        //获取数据失败
//                                        if (iBaseView != null) {
//                                            iBaseView.onRequestHttpDataFailed(requestCode, ShopMailError.BUSINESS_ERROR);
//                                        }
//                                    }
//                                }
                            }

                            //数据请求成功
                            setLoadingPager(LoadingPageConfig.STATE_SUCCESS_CODE);
                        } catch (IOException e) {
                            //e.printStackTrace();
                            //数据为空
                            setLoadingPager(LoadingPageConfig.STATE_EMPTY_CODE);
                            throw new RuntimeException("获取数据为空"); //扔出异常, 让onError函数统一管理
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        setLoadingPager(LoadingPageConfig.STATE_ERROR_CODE);
                        //获取数据失败
                        if (iBaseView != null) {
                            iBaseView.onRequestHttpDataFailed(requestCode, ErrorUtil.handlerError(e));
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    @Override
    public void doPostHttpRequest(final int requestCode) {
        RetrofitCreator.getNetApiService().postData(getHeaderParams(), getPath(), getParams())
                .subscribeOn(Schedulers.io()) //订阅
                .observeOn(AndroidSchedulers.mainThread()) //观察
                .subscribe(new Observer<ResponseBody>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        //提示用户正在加载, 显示加载页
                        setLoadingPager(LoadingPageConfig.STATE_LOADING_CODE);
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String string = responseBody.string();

                            //判断数据是否是列表
                            if (isList()) {
                                ResEntity<List<T>> resEntityList = new Gson().fromJson(string, getBeanType());

                                //获取数据列表成功
                                if (resEntityList.getCode() == 200) { //数据请求成功
                                    if (iBaseView != null) {
                                        iBaseView.onRequestHttpDataListSuccess(requestCode, resEntityList.getMessage(), resEntityList.getResult());
                                    }
                                } else {
                                    //获取列表数据失败
                                    if (iBaseView != null) {
                                        iBaseView.onRequestHttpDataFailed(requestCode, ShopMailError.BUSINESS_ERROR);
                                    }
                                }

                            } else { //不是列表
                                ResEntity<T> resEntity = new Gson().fromJson(string, getBeanType());

                                if (resEntity.getCode() == 200) { //数据请求成功
                                    if (iBaseView != null) {
                                        iBaseView.onRequestHttpDataSuccess(requestCode, resEntity.getMessage(), resEntity.getResult());
                                    } else {
                                        //获取数据失败
                                        if (iBaseView != null) {
                                            iBaseView.onRequestHttpDataFailed(requestCode, ShopMailError.BUSINESS_ERROR);
                                        }
                                    }
                                }
                            }

                            //数据请求成功
                            setLoadingPager(LoadingPageConfig.STATE_SUCCESS_CODE);
                        } catch (IOException e) {
                            //数据为空
                            setLoadingPager(LoadingPageConfig.STATE_EMPTY_CODE);
                            throw new RuntimeException("获取数据为空"); //扔出异常, 让onError函数统一管理
                            //e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        //获取数据失败
                        setLoadingPager(LoadingPageConfig.STATE_ERROR_CODE);
                        if (iBaseView != null) {
                            iBaseView.onRequestHttpDataFailed(requestCode, ErrorUtil.handlerError(e));
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    //设置加载页状态
    private void setLoadingPager(int type) {
        if (iBaseView != null) {
            iBaseView.loadingPage(type);
        }
    }


    /**
     * @return
     */
    protected abstract Type getBeanType();

    /**
     * 请求的数据是否是集合
     *
     * @return
     */
    protected boolean isList() {
        return false;
    }


    @Override
    public void attachView(IBaseView<T> iBaseView) {
        this.iBaseView = iBaseView;
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

    @Override
    public void detachView() {
        if (iBaseView != null) {
            this.iBaseView = null;
        }
    }
}