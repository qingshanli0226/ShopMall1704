package com.example.net;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitCreator {

    private static NetInterence netInterence;
    private static TimeInternect timeInternect;

    public  static NetInterence getNetInterence(){
        if (netInterence==null){
            createNet();
        }
        return  netInterence;
    }

    public static TimeInternect getTimeInternect(){
        if(timeInternect==null){
            createTimeNet();
        }
        return timeInternect;
    }

    private static void createNet() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        if (true){
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        }else {
            interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }
        OkHttpClient builder = new OkHttpClient.Builder()
                .connectTimeout(50, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .addInterceptor(new TokenInterceptor())//TODO 添加Token拦截器
                .build();
        Retrofit build = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder)
                .baseUrl(AppNetConfig.BASE_URL)
                .build();

        netInterence=build.create(NetInterence.class);
    }

    private static void createTimeNet(){
        Retrofit builder = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(AppNetConfig.TIME_URL)
                .build();
        timeInternect = builder.create(TimeInternect.class);
    }
}
