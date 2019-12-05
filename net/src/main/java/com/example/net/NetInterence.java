package com.example.net;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface NetInterence {

    //GET请求
    @GET("{path}")
    Observable<ResponseBody> getData(@HeaderMap HashMap<String, String> headers,@Path(value = "path", encoded = true) String path, @QueryMap HashMap<String, String> params);

    //POST请求
   @POST("{path}")
   @FormUrlEncoded
   Observable<ResponseBody> postData(@HeaderMap HashMap<String, String> headers, @Path("path") String path, @FieldMap HashMap<String, String> params);

   //下载文件
   @Streaming
   @GET
   Observable<ResponseBody> downloadFileWithDynamicUrlSync(@Url String fileUrl);
}
