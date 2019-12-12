package com.example.net;

import com.example.common.Constant;
import com.example.common.utils.SPUtil;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * author:李浩帆
 * token 拦截器
 */
public class TokenInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();//TODO 拿到请求对象
        Request newRequest=null;
        if(SPUtil.getToken()!=null){
            //TODO 在请求头部添加一个key  Value形式的参数，将token值添加进去.
            newRequest = request.newBuilder().addHeader(Constant.TOKEN,SPUtil.getToken()).build();
            //TODO 将生成带token的newRequest做为请求参数进行网络请求
            return chain.proceed(newRequest);
        }else{
            //TODO 如果没有token,使用老的不带token参数的request，去进行网络请求.
            return chain.proceed(request);
        }
    }
}
