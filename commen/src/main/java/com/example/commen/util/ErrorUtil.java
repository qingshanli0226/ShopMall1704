package com.example.commen.util;

import com.example.commen.ShopMailError;

import org.json.JSONException;

import java.net.HttpRetryException;
import java.net.SocketTimeoutException;

public class ErrorUtil {
    public static ShopMailError handlerError(Throwable e) {

        if (e instanceof SocketTimeoutException) { //网络请求协议出错
            return ShopMailError.HTTP_SOCKET_TIME_OUT_ERROR;

        } else if (e instanceof HttpRetryException) { //网络错误
            return ShopMailError.HTTP_ERROR;

        } else if (e instanceof JSONException) { //JSON异常

            return ShopMailError.JSON_ERROR;
        } else { //其它错误
            return ShopMailError.OTHER_ERROR;

        }
    }
}
