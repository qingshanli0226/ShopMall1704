package com.example.framework.base;

import android.util.Log;

import com.example.common.SignUtil;
import com.example.net.RetrofitCreator;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

//网络下载
public abstract class BasePresenter<T> implements IBasePresenter<T> {

    private IGetBaseView<T> iGetBaseView;
    private IPostBaseView<T> iPostBaseView;
    private ILoadView iLoadView;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    public void getGetData() {
        RetrofitCreator.getNetGetSerivice().getGetData(getPath(), getHeader())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if (iLoadView != null)
                            iLoadView.onLoadingPage();
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            T data = new Gson().fromJson(responseBody.string(), getBeanType());
                            if (iGetBaseView != null)
                                iGetBaseView.onGetDataSucess(data);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (iGetBaseView != null)
                            iGetBaseView.onGetDataFailed(ErrorUtil.handleError(e));
                    }

                    @Override
                    public void onComplete() {
                        if (iLoadView != null)
                            iLoadView.onStopLoadingPage();
                    }
                });
    }

    @Override
    public void getCipherTextData() {
        RetrofitCreator.getNetPostService().getFormData(getPath(), getHeader(), getSign())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                        if (iLoadView != null)
                            iLoadView.onLoadingPage();
                    }

                    @Override
                    public void onNext(ResponseBody body) {
                        try {
                            T cipherTextData = new Gson().fromJson(body.string(), getBeanType());
                            if (iPostBaseView != null)
                                iPostBaseView.onPostDataSucess(cipherTextData);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("####", e.getMessage());
                        if (iPostBaseView != null)
                            iPostBaseView.onPostDataFailed(ErrorUtil.handleError(e));
                    }

                    @Override
                    public void onComplete() {
                        if (iLoadView != null)
                            iLoadView.onStopLoadingPage();
                    }
                });
    }

    @Override
    public void getPostFormData() {
        RetrofitCreator.getNetPostService().getFormData(getPath(), getHeader(), getParam())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                        if (iLoadView != null)
                            iLoadView.onLoadingPage();
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            T data = new Gson().fromJson(responseBody.string(), getBeanType());
                            if (iPostBaseView != null)
                                iPostBaseView.onPostDataSucess(data);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("####", "" + e.getMessage());
                        if (iPostBaseView != null)
                            iPostBaseView.onPostDataFailed(ErrorUtil.handleError(e));
                    }

                    @Override
                    public void onComplete() {
                        if (iLoadView != null)
                            iLoadView.onStopLoadingPage();
                    }
                });
    }

    @Override
    public void getPostJsonData() {
        RetrofitCreator.getNetPostService().getJsonData(getHeader(), getPath(), getRequestBody())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                        if (iLoadView != null)
                            iLoadView.onLoadingPage();
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            T data = new Gson().fromJson(responseBody.string(), getBeanType());
                            if (iPostBaseView != null)
                                iPostBaseView.onPostDataSucess(data);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("####", e.getMessage());
                        if (iPostBaseView != null)
                            iPostBaseView.onPostDataFailed(ErrorUtil.handleError(e));
                    }

                    @Override
                    public void onComplete() {
                        if (iLoadView != null)
                            iLoadView.onStopLoadingPage();
                    }
                });
    }

    @Override
    public void getPostFile() {
        RetrofitCreator.getNetPostService().getFileData(getPath(), getHeader(), getFile())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                        if (iLoadView != null)
                            iLoadView.onLoadingPage();
                    }

                    @Override
                    public void onNext(ResponseBody body) {
                        try {
                            T data = new Gson().fromJson(body.string(), getBeanType());
                            if (iPostBaseView != null)
                                iPostBaseView.onPostDataSucess(data);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("####", e.getMessage());
                        if (iPostBaseView != null)
                            iPostBaseView.onPostDataFailed(ErrorUtil.handleError(e));
                    }

                    @Override
                    public void onComplete() {
                        if (iLoadView != null)
                            iLoadView.onStopLoadingPage();
                    }
                });

    }

    public MultipartBody.Part getFile() {
        return null;
    }

    protected RequestBody getRequestBody() {
        return null;
    }

    //返回数据类型
    protected abstract Type getBeanType();

    //返回必传参数
    protected abstract String getPath();

    //返回请求头
    protected abstract HashMap<String, String> getHeader();

    //返回请求参数
    protected abstract Map<String, String> getParam();

    private Map<String, String> getSign() {
        TreeMap<String, String> emptyTreeMap = SignUtil.getEmptyTreeMap();
        Map<String, String> query = getParam();
        emptyTreeMap.putAll(query);
        String sign = SignUtil.generateSign(emptyTreeMap);
        emptyTreeMap.put("sign", sign);
        return SignUtil.encryptParamsByBase64(emptyTreeMap);
    }

    //解绑
    @Override
    public void detachView() {
        this.iGetBaseView = null;
        this.iPostBaseView = null;
        this.iLoadView = null;
        compositeDisposable.dispose();
    }

    @Override
    public void attachGetView(IGetBaseView<T> iGetBaseView) {
        this.iGetBaseView = iGetBaseView;
    }

    @Override
    public void attachPostView(IPostBaseView<T> iPostBaseView) {
        this.iPostBaseView = iPostBaseView;
    }


    @Override
    public void attachLoadView(ILoadView iLoadView) {
        this.iLoadView = iLoadView;
    }
}
