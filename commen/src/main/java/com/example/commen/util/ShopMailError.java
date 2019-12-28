package com.example.commen.util;

public enum ShopMailError {
    //网络相关错误
    HTTP_ERROR(1001, "网络错误"),
    HTTP_SOCKET_TIME_OUT_ERROR(1002, "网络连接超时错误"),
    NETWORK_ERROR(1003, "当前无网络连接, 请检查"),

    //解析相关错误, 从2000开始
    JSON_ERROR(2001, "数据格式不对"),
    //文件相关错误
    FILE_OPEN_ERROR(3001, "打开文件错误"),

    //业务错误
    BUSINESS_ERROR(4001, "获取数据失败"),


    //内存错误,
    MEM_ERROR(5001, "内存处理错误"),
    OTHER_ERROR(0000, "其它错误"),

    SUCCESS(200, "请求成功"),
    ERROR_USER_NOT_REGISTERED(1001, "用户没有注册，无法登录"),
    ERROR_USER_HAS_REGISTERED(1002, "用户已经注册，请直接登录"),
    ERROR_NOT_LOGIN(1003, "请先登录"),
    ERROR_UPLOAD_FILE(1004, "上传文件失败"),
    ERROR_AUTO_LOGIN(1005, "token失效自动登录失败"),
    ERROR_TOKEN_EXPIRE_CODE(1006, "token失效"),
    //    ERROR_GET_ORDER = "下订单失败";
    ERROR_CHECK_INVENTORY(1008, "检查产品数量出现错误"),
    ERROR_GET_SHORTCART_PRODUCTS(1009, "获取购物车产品出现错误"),
    ERROR_ADD_ONE_PRODUCT(1010, "将一个产品添加到购物车失败"),
    ERROR_UPDATE_PRODUCT_NUM(1011, "更新产品数量出现错误"),
    ERROR_VERIFY_SIGN(1012, "参数签名验证不过");

    private int errorCode;
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
