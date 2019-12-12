package com.example.net;


import org.json.JSONObject;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface NetInterence {

    //GET请求
    @GET("{path}")
    Observable<ResponseBody> getData(@HeaderMap Map<String, String> headers,@Path(value = "path", encoded = true) String path, @QueryMap Map<String, String> params);

    //POST请求
   @POST("{path}")
   @FormUrlEncoded
   Observable<ResponseBody> postData(@HeaderMap Map<String, String> headers, @Path(value = "path", encoded = true) String path, @FieldMap Map<String, String> params);

    //POST JSON
//    @Headers({"Content-type:application/json;charset=UTF-8"})
//    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("{path}")
    Observable<ResponseBody> postJsonData(@Path(value = "path", encoded = true) String path,@Body Object object);
    //下载文件
   @Streaming
   @GET
   Observable<ResponseBody> downloadFileWithDynamicUrlSync(@Url String fileUrl);
}
