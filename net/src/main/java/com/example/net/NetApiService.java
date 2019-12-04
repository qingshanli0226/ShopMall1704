package com.example.net;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface NetApiService {
    /**
     * ResponseBody 是一个通用的返回类型，如果不清楚服务端返回的json数据，可以通过它来获取
     *
     * @param headers
     * @param path    (value = "path", encoded = true)避免被转义
     * @param params
     * @return
     */
    @GET("{path}")
    Observable<ResponseBody> getData(@HeaderMap HashMap<String, String> headers, @Path(value = "path", encoded = true) String path, @QueryMap HashMap<String, String> params);

    /**
     * @param header
     * @param url    @url可以避免被转义
     * @param params
     * @return
     */
    @POST()
    @FormUrlEncoded
    Observable<ResponseBody> postData(@HeaderMap HashMap<String, String> header, @Url() String url, @FieldMap HashMap<String, String> params);

    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    @Multipart
    @POST
    Observable<ResponseBody> upload(@Part MultipartBody.Part file);

    /**
     * 使用Steaming,为了避免下载大文件时，出现OOM异常
     *
     * @param avatarUrl
     * @return
     */
    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(@Url String avatarUrl);

}
