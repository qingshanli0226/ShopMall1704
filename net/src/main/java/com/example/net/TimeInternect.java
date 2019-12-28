package com.example.net;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;

public interface TimeInternect {

    @GET("getSysTime.do")
    Observable<ResponseBody> getTime();
}
