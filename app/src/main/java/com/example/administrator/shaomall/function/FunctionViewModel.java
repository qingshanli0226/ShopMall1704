package com.example.administrator.shaomall.function;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.net.AppNetConfig;
import com.example.net.MVPObserver;
import com.example.net.ResEntity;
import com.example.net.RetrofitCreator;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shaomall.framework.bean.FunctionBean;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class FunctionViewModel extends ViewModel {
    private MutableLiveData<List<FunctionBean>> liveData = new MutableLiveData<>();

    public MutableLiveData<List<FunctionBean>> getLiveData() {
        return liveData;
    }

    /**
     * 查询待支付的订单
     */
    public void findForPay() {
        getData(AppNetConfig.FIND_FOR_PAY);
    }

    /**
     * 查询待发货的订单
     */
    public void findForSend() {
        getData(AppNetConfig.FIND_FOR_SEND);
    }

    private void getData(String path) {
        RetrofitCreator.getNetApiService().getData(new HashMap<String, String>(), path, new HashMap<String, String>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MVPObserver<ResponseBody>() {
                    @Override
                    public void onNext(ResponseBody responseBody) {
                        super.onNext(responseBody);
                        try {
                            String string = responseBody.string();
                            ResEntity<List<FunctionBean>> listResEntity = new Gson().fromJson(string, new TypeToken<ResEntity<List<FunctionBean>>>() {
                            }.getType());
                            liveData.setValue(listResEntity.getResult());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

}
