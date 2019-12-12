package com.example.framework.manager;

import com.example.common.code.ErrorCode;
import com.google.gson.JsonSyntaxException;

import java.io.FileNotFoundException;
import java.net.SocketTimeoutException;

import retrofit2.HttpException;

public class ErrorDisposeManager {
    public static ErrorCode HandlerError(Throwable e){
        if (e instanceof SocketTimeoutException){
            return ErrorCode.HTTP_SOCKET_TIME_OUT;
        }else if (e instanceof HttpException){
            return ErrorCode.HTTP_ERROR;
        }else if (e instanceof JsonSyntaxException){
            return ErrorCode.JSON_ERROR;
        }else if (e instanceof FileNotFoundException){
            return ErrorCode.FILE_OPEN_ERROR;
        }else{
        return ErrorCode.OTHER_ERROR;
        }
    }
}
