package com.example.framework.manager;

/**
 * author:李浩帆
 */
public enum ErrorManager {
    //TODO 网络相关错误，从1000开始
    HTTP_ERROR(1001, "网络错误"),
    HTTP_SOCKET_TIME_OUT_ERROR(1002, "网络连接超时错误"),
    //解析相关错误，从2000开始
    JSON_ERROR(2001, "数据格式不对"),
    //文件相关错误，从3000开始
    FILE_OPEN_ERROR(3001, "打开文件错误"),
    //业务错误，从4000开始
    BUSINESS_ERROR(4001, "获取数据失败"),
    //内存错误，从5000开始
    MEM_ERROR(5001, "内存处理错误"),
    OTHER_ERROR(10000, "其他错误");

    private String errorMessage;
    private int errorCode;

    ErrorManager(int errorCode, String errorMessage) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }


}

