package com.example.net;

import android.util.Log;

import com.example.commen.TokenUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class Tokenlnterceptor implements Interceptor {


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request(); //去拿到request请求对象
        Request newRequest;
        Log.d("SSH:", "intercept: " + TokenUtil.getToken());
        if (TokenUtil.getToken() != null) {
            //在请求头部添加一个keyvalue形式的参数，将token值添加进去.
            newRequest = request.newBuilder().addHeader(AppNetConfig.TOKEN, TokenUtil.getToken()).build();
            return chain.proceed(newRequest); //将生成带token的newRequest做为请求参数进行网络请求
        } else {
            //如果没有token,使用老的不带token参数的request，去进行网络请求.
            return chain.proceed(request);
        }
    }
}
