package com.example.commen;

public enum ShopMailError {
    //网络相关错误
    HTTP_ERROR(1001, "网络错误"),
    HTTP_SOCKET_TIME_OUT_ERROR(1002, "网络连接超时错误"),

    //解析相关错误, 从2000开始
    JSON_ERROR(2001, "数据格式不对"),


    //文件相关错误
    FILE_OPEN_ERROR(3001, "打开文件错误"),

    //业务错误
    BUSINESS_ERROR(4001, "获取数据失败"),


    //内存错误,
    MEM_ERROR(5001, "内存处理错误"),
    OTHER_ERROR(0000, "其它错误");


    private int    errorCode;
    private String errorMessage;


    ShopMailError(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }


    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
