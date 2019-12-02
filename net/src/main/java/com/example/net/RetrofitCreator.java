package com.example.net;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitCreator {
    public  static NetInterence netInterence;
    public  static NetInterence getNetInterence(){
        if (netInterence==null){
            createNet();
        }
        return  netInterence;
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
                .build();
        Retrofit build = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder)
                .baseUrl(AppNetConfig.BASE_URL)
                .build();


        netInterence=build.create(NetInterence.class);
    }
}
