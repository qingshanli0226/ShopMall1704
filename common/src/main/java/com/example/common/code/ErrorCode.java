package com.example.common.code;

/**
 * author:李浩帆
 */
public enum ErrorCode {
    //TODO 用户登录注册错误信息
    ERROR_USER_NOT_REGISTERED(1001, "用户没有注册,无法登录"),
    ERROR_USER_HAS_REGISTERED(1002, "用户已经注册,请直接登录"),
    ERROR_NOT_LOGIN_REGISTERED(1003, "请先登录"),
    ERROR_UPLOAD_FILE_REGISTERED(1004, "上传文件失败"),
    ERROR_AUTO_LOGIN_REGISTERED(1005, "token失效自动登录失败"),
    ERROR_TOKEN_EXPIRE_REGISTERED(1006, "token失效"),
    ERROR_GET_ORDER_REGISTERED(1007, "下订单失败"),
    ERROR_CHECK_INVENTORY_REGISTERED(1008, "检查产品数量出现错误"),
    ERROR_GET_SHORTCART_PRODUCTS_REGISTERED(1009, "获取购物车产品出现错误"),
    ERROR_ADD_ONE_PRODUCT_REGISTERED(1010, "将一个产品添加到购物车失败"),
    ERROR_UPDATE_PRODUCT_NUM_REGISTERED(1011, "更新产品数量出现错误"),
    ERROR_VERIFY_SIGN_REGISTERED(1012, "参数签名验证不过"),
    //解析相关错误，从2000开始
    JSON_ERROR(2001, "数据格式不对"),
    //文件相关错误，从3000开始
    FILE_OPEN_ERROR(3001, "打开文件错误"),
    //业务错误，从4000开始
    BUSINESS_ERROR(4001, "获取数据失败"),
    //内存错误，从5000开始
    MEM_ERROR(5001, "内存处理错误"),
    //网络相关错误,从6000开始
    HTTP_ERROR(6001,"网络错误"),
    HTTP_SOCKET_TIME_OUT(6002,"网络链接超时错误"),
    OTHER_ERROR(10000, "其他错误");

    private String errorMessage;
    private int errorCode;

    ErrorCode(int errorCode, String errorMessage) {
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

