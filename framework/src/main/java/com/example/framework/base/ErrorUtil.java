package com.example.framework.base;

import org.json.JSONException;

import java.net.SocketTimeoutException;

public class ErrorUtil {

    public static String handleError(Throwable t) {
        if (t instanceof ClassCastException)
            return "类型转换异常";
        else if (t instanceof IndexOutOfBoundsException)
            return "下标越界异常";
        else if (t instanceof NullPointerException)
            return "空指针异常";
        else if (t instanceof IllegalArgumentException)
            return "参数异常";
        else if (t instanceof SocketTimeoutException)
            return "连接超时异常";
        else if (t instanceof JSONException)
            return "读取（或写入）格式错误的JSON元素时";
        else {
            return "其他错误";
        }
    }


}
