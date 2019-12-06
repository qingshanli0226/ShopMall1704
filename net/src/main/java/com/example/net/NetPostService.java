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
    Observable<ResponseBody> getFormData(@Path("path") String path, @HeaderMap HashMap<String, String> headMap, @FieldMap HashMap<String, String> fieldMap);

    @POST("{path}")
    Observable<ResponseBody> getJsonData(@Path("path") String path, @Body RequestBody body);

    @FormUrlEncoded
    @POST("{path}")
    Observable<ResponseBody> register(@FieldMap Map<String, String> fieldMap);
}
