package com.example.net;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface NetApiService {
    //ResponseBody 是一个通用的返回类型，如果不清楚服务端返回的json数据，可以通过它来获取
//    @GET("{path}")
//    Observable<ResponseBody> getData(@HeaderMap HashMap<String, String> headers, @Path("path") String path, @QueryMap HashMap<String, String> params);
 @GET("atguigu/json/{path}")
    Observable<ResponseBody> getData(@HeaderMap HashMap<String, String> headers, @Path("path") String path, @QueryMap HashMap<String, String> params);

    /**
     *post请求
     */
    @POST
    Observable<ResponseBody> postData(@HeaderMap HashMap<String,String> header, @Path("path")String path, @FieldMap HashMap<String,String> params);






}
