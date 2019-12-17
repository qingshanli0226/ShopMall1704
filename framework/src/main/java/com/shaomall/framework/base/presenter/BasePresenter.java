package com.shaomall.framework.base.presenter;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.example.commen.util.ErrorUtil;
import com.example.commen.LoadingPageConfig;
import com.example.commen.util.ShopMailError;
import com.example.net.MVPObserver;
import com.example.net.ResEntity;
import com.example.net.RetrofitCreator;
import com.example.net.sign.SignUtil;
import com.google.gson.Gson;
import com.shaomall.framework.base.view.IBaseView;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public abstract class BasePresenter<T> implements IBasePresenter<T> {
    private IBaseView<T> iBaseView;

    @Override
    public void doGetHttpRequest() {
        getData(RetrofitCreator.getNetApiService().getData(getHeaderParams(), getPath(), getParams()));
    }

    /**
     * Get请求
     *
     * @param requestCode
     */
    @Override
    public void doGetHttpRequest(int requestCode) {
        getData(requestCode, RetrofitCreator.getNetApiService().getData(getHeaderParams(), getPath(), getParams()));
    }


    /**
     * post请求
     */
    @Override
    public void doPostHttpRequest() {
        //加密请求
        getData(RetrofitCreator.getNetApiService().postData(getHeaderParams(), getPath(), getEncryptParamMap()));
    }

    @Override
    public void doPostHttpRequest(int requestCode) {
        //加密请求
        getData(requestCode, RetrofitCreator.getNetApiService().postData(getHeaderParams(), getPath(), getEncryptParamMap()));
    }


    @Override
    public void doJsonPostHttpRequest() {
        getData(RetrofitCreator.getNetApiService().jsonPostData(getHeaderParams(), getPath(), getEncryptJsonParam()));
    }

    @Override
    public void doJsonPostHttpRequest(int requestCode) {
        getData(requestCode, RetrofitCreator.getNetApiService().jsonPostData(getHeaderParams(), getPath(), getEncryptJsonParam()));
    }


    //设置加载页状态
    private void setLoadingPager(int requestCode,int type) {
        if (iBaseView != null) {
            iBaseView.loadingPage(requestCode,type);
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
     * 请求路径
     *
     * @return
     */
    public abstract String getPath();

    /**
     * 请求参数
     *
     * @return
     */
    public Map<String, String> getParams() {

        return new HashMap<>();
    }

    /**
     * 获取加密后的Params
     *
     * @return
     */
    public Map<String, String> getEncryptParamMap() {
        Map<String, String> params = getParams();

        if (!params.isEmpty()) {
            //转化成treeMap
            TreeMap<String, String> emptyTreeMap = SignUtil.getEmptyTreeMap();
            Set<Map.Entry<String, String>> entries = params.entrySet();
            for (Map.Entry<String, String> entry : entries) {
                emptyTreeMap.put(entry.getKey(), entry.getValue());
            }

            //根据getParams()返回的参数, 生成对应的签名
            String sign = SignUtil.generateSign(emptyTreeMap);
            params.put("sign", sign);
            Log.i("Wang", "getEncryptParamMap: " + sign);
            //进行加密, 利用TreeMap
            Map<String, String> encryptParamMap = SignUtil.encryptParamsByBase64(params);

            return encryptParamMap;
        }

        return params;
    }


    protected JSONObject getJsonParam() {

        return new JSONObject();
    }

    /**
     * json请求
     *
     * @return
     */
    private RequestBody getEncryptJsonParam() {
        JSONObject jsonParam = getJsonParam();
        RequestBody requestBody = null;
        if (jsonParam != null) {
            jsonParam.put("sign", SignUtil.generateJsonSign(jsonParam));
            SignUtil.encryptJsonParamsByBase64(jsonParam);
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonParam.toString());
        }

        return requestBody;
    }


    private void getData(Observable<ResponseBody> data) {
        data.subscribeOn(Schedulers.io()) //订阅
                .observeOn(AndroidSchedulers.mainThread()) //观察
                .subscribe(new MVPObserver<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        //提示用户正在加载, 显示加载页
                        setLoadingPager(-1,LoadingPageConfig.STATE_LOADING_CODE);
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        //判断iBaseView是否为null
                        if (iBaseView == null) {
                            return;
                        }
                        try {
                            //数据请求成功
                            setLoadingPager(-1,LoadingPageConfig.STATE_SUCCESS_CODE);
                            String string = responseBody.string();

                            //判断数据是否是列表
                            if (isList()) {
                                ResEntity<List<T>> resEntityList = new Gson().fromJson(string, getBeanType());
                                int code = resEntityList.getCode();
                                //获取数据列表成功
                                if (code == ShopMailError.SUCCESS.getErrorCode()) { //数据请求成功
                                    iBaseView.onRequestHttpDataListSuccess(resEntityList.getMessage(), resEntityList.getResult());
                                } else {
                                    //获取列表数据失败
                                    iBaseView.onRequestHttpDataFailed(dataProcessing(code));
                                }

                            } else { //不是列表
                                ResEntity<T> resEntity = new Gson().fromJson(string, getBeanType());
                                int code = resEntity.getCode();
                                if (code == ShopMailError.SUCCESS.getErrorCode()) { //数据请求成功
                                    iBaseView.onRequestHttpDataSuccess(resEntity.getMessage(), resEntity.getResult());
                                } else {
                                    //获取数据失败
                                    iBaseView.onRequestHttpDataFailed(dataProcessing(code));
                                }
                            }

                        } catch (IOException e) {
                            //e.printStackTrace();
                            //数据为空
                            setLoadingPager(-1,LoadingPageConfig.STATE_EMPTY_CODE);
                            throw new RuntimeException("获取数据为空"); //扔出异常, 让onError函数统一管理
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        setLoadingPager(-1,LoadingPageConfig.STATE_ERROR_CODE);
                        //获取数据失败
                        if (iBaseView != null) {
                            iBaseView.onRequestHttpDataFailed(ErrorUtil.handlerError(e));
                        }
                    }
                });
    }

    private void getData(final int requestCode, Observable<ResponseBody> data) {
        data.subscribeOn(Schedulers.io()) //订阅
                .observeOn(AndroidSchedulers.mainThread()) //观察
                .subscribe(new MVPObserver<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        //提示用户正在加载, 显示加载页
                        setLoadingPager(requestCode,LoadingPageConfig.STATE_LOADING_CODE);
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        //判断iBaseView是否为null
                        if (iBaseView == null) {
                            return;
                        }
                        try {
                            //数据请求成功
                            setLoadingPager(requestCode,LoadingPageConfig.STATE_SUCCESS_CODE);
                            String string = responseBody.string();

                            //判断数据是否是列表
                            if (isList()) {
                                ResEntity<List<T>> resEntityList = new Gson().fromJson(string, getBeanType());
                                int code = resEntityList.getCode();
                                //获取数据列表成功
                                if (code == ShopMailError.SUCCESS.getErrorCode()) { //数据请求成功
                                    iBaseView.onRequestHttpDataListSuccess(requestCode, resEntityList.getMessage(), resEntityList.getResult());
                                } else {
                                    //获取列表数据失败
                                    iBaseView.onRequestHttpDataFailed(requestCode, dataProcessing(code));
                                }

                            } else { //不是列表
                                ResEntity<T> resEntity = new Gson().fromJson(string, getBeanType());
                                int code = resEntity.getCode();
                                if (code == ShopMailError.SUCCESS.getErrorCode()) { //数据请求成功
                                    iBaseView.onRequestHttpDataSuccess(requestCode, resEntity.getMessage(), resEntity.getResult());
                                } else {
                                    //获取数据失败
                                    iBaseView.onRequestHttpDataFailed(requestCode, dataProcessing(code));
                                }
                            }

                        } catch (IOException e) {
                            //e.printStackTrace();
                            //数据为空
                            setLoadingPager(requestCode,LoadingPageConfig.STATE_EMPTY_CODE);
                            throw new RuntimeException("获取数据为空"); //扔出异常, 让onError函数统一管理
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        setLoadingPager(requestCode,LoadingPageConfig.STATE_ERROR_CODE);
                        //获取数据失败
                        if (iBaseView != null) {
                            iBaseView.onRequestHttpDataFailed(requestCode, ErrorUtil.handlerError(e));
                        }
                    }
                });
    }

    /**
     * 数据处理
     *
     * @param code
     * @return
     */
    private ShopMailError dataProcessing(int code) {
        if (code == ShopMailError.SUCCESS.getErrorCode()) { //登录成功
            return ShopMailError.SUCCESS;

        } else if (code == ShopMailError.ERROR_USER_NOT_REGISTERED.getErrorCode()) { //用户没有注册，无法登录
            return ShopMailError.ERROR_USER_NOT_REGISTERED;

        } else if (code == ShopMailError.ERROR_USER_HAS_REGISTERED.getErrorCode()) { //用户已经注册, 请直接登录
            return ShopMailError.ERROR_USER_NOT_REGISTERED;

        } else if (code == ShopMailError.ERROR_NOT_LOGIN.getErrorCode()) { //请先登录
            return ShopMailError.ERROR_NOT_LOGIN;

        } else if (code == ShopMailError.ERROR_UPLOAD_FILE.getErrorCode()) { //上传文件失败
            return ShopMailError.ERROR_UPLOAD_FILE;

        } else if (code == ShopMailError.ERROR_AUTO_LOGIN.getErrorCode()) { //token失效自动登录失败
            return ShopMailError.ERROR_AUTO_LOGIN;

        } else if (code == ShopMailError.ERROR_TOKEN_EXPIRE_CODE.getErrorCode()) { //token失效
            return ShopMailError.ERROR_TOKEN_EXPIRE_CODE;

        } else if (code == ShopMailError.ERROR_CHECK_INVENTORY.getErrorCode()) { //检查产品数量出现错误
            return ShopMailError.ERROR_CHECK_INVENTORY;

        } else if (code == ShopMailError.ERROR_GET_SHORTCART_PRODUCTS.getErrorCode()) { //获取购物车产品出现错误
            return ShopMailError.ERROR_GET_SHORTCART_PRODUCTS;

        } else if (code == ShopMailError.ERROR_ADD_ONE_PRODUCT.getErrorCode()) { //将一个产品添加到购物车失败
            return ShopMailError.ERROR_ADD_ONE_PRODUCT;

        } else if (code == ShopMailError.ERROR_UPDATE_PRODUCT_NUM.getErrorCode()) { //更新产品数量出现错误
            return ShopMailError.ERROR_UPDATE_PRODUCT_NUM;

        } else if (code == ShopMailError.ERROR_VERIFY_SIGN.getErrorCode()) { //参数签名验证不过
            return ShopMailError.ERROR_VERIFY_SIGN;

        } else {
            return ShopMailError.OTHER_ERROR;

        }
    }

    @Override
    public void detachView() {
        if (iBaseView != null) {
            this.iBaseView = null;
        }
    }
}