package com.example.net;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 通过Retrofit进行网络请求
 */
public class RetrofitCreator {
    private static NetApiService netApiService = null;

    /**
     * 懒汉式(线程安全)
     *
     * @return
     */
    public static NetApiService getNetApiService() {
        if (netApiService == null) {
            synchronized (RetrofitCreator.class) {
                if (netApiService == null) {
                    createApiService();
                }
            }
        }
        return netApiService;
    }

    private static void createApiService() {
        //通过拦截器打印网络请求log
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        //判断是否进行打印log
        if (AppNetConfig.PRINT_LOG){
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        }else {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }


        //定制OkHttpClient，添加拦截器
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(new Tokenlnterceptor())  //可以进行多次拦截，将添加token的拦截器添加上.
                .connectTimeout(50, TimeUnit.SECONDS)
                .build();

        //Retrofit的使用
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(AppNetConfig.BASE_URL)
                .client(okHttpClient)
                .build();

        netApiService = retrofit.create(NetApiService.class);
    }
}
