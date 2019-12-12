package com.example.net;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Path;

//post网络请求
public interface NetPostService {
    @FormUrlEncoded
    @POST("{path}")
    Observable<ResponseBody> getFormData(@Path("path") String path, @HeaderMap HashMap<String, String> headMap, @FieldMap Map<String, String> fieldMap);

    @POST("{path}")
    Observable<ResponseBody> getJsonData(@Path("path") String path, @HeaderMap HashMap<String, String> header, @Body RequestBody body);

    @FormUrlEncoded
    @POST("{path}")
    Observable<ResponseBody> register(@Path("path") String path, @FieldMap Map<String, String> fieldMap);

    @FormUrlEncoded
    @POST("{path}")
    Observable<ResponseBody> login(@Path("path") String path, @FieldMap Map<String, String> fieldMap);

    Observable<ResponseBody> getJsonData(@HeaderMap HashMap<String, String> headMap, @Path("path") String path, @Body RequestBody body);


}
