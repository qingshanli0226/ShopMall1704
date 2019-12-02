package com.example.net;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

//get网络请求
public interface NetGetService {

    @GET("{path}")
    Observable<ResponseBody> getData(@Path("path") String path, @HeaderMap HashMap<String, String> headMap, @QueryMap HashMap<String, String> queryMap);


}
