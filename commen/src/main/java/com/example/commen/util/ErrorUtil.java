package com.example.commen.util;

import android.util.Log;

import org.json.JSONException;

import java.net.ConnectException;
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
        } else if (e instanceof ConnectException) {

            return ShopMailError.NETWORK_ERROR;
        } else { //其它错误
//            Log.i("TAG", "handlerError: " + e.getMessage() + ": " + e.toString());
            return ShopMailError.OTHER_ERROR;

        }
    }


    /**
     * 数据处理
     *
     * @param code
     * @return
     */
    public static ShopMailError dataProcessing(int code) {
        if (code == ShopMailError.SUCCESS.getErrorCode()) { //登录成功
            return ShopMailError.SUCCESS;

        } else if (code == ShopMailError.ERROR_USER_NOT_REGISTERED.getErrorCode()) { //用户没有注册，无法登录
            return ShopMailError.ERROR_USER_NOT_REGISTERED;

        } else if (code == ShopMailError.ERROR_USER_HAS_REGISTERED.getErrorCode()) { //用户已经注册, 请直接登录
            return ShopMailError.ERROR_USER_NOT_REGISTERED;

        } else if (code == ShopMailError.ERROR_NOT_LOGIN.getErrorCode()) { //请先登录
            return ShopMailError.ERROR_NOT_LOGIN;

        } else if (code == ShopMailError.ERROR_UPLOAD_FILE.getErrorCode()) { //上传文件失败
            return ShopMailError.ERROR_UPLOAD_FILE;

        } else if (code == ShopMailError.ERROR_AUTO_LOGIN.getErrorCode()) { //token失效自动登录失败
            return ShopMailError.ERROR_AUTO_LOGIN;

        } else if (code == ShopMailError.ERROR_TOKEN_EXPIRE_CODE.getErrorCode()) { //token失效
            return ShopMailError.ERROR_TOKEN_EXPIRE_CODE;

        } else if (code == ShopMailError.ERROR_CHECK_INVENTORY.getErrorCode()) { //检查产品数量出现错误
            return ShopMailError.ERROR_CHECK_INVENTORY;

        } else if (code == ShopMailError.ERROR_GET_SHORTCART_PRODUCTS.getErrorCode()) { //获取购物车产品出现错误
            return ShopMailError.ERROR_GET_SHORTCART_PRODUCTS;

        } else if (code == ShopMailError.ERROR_ADD_ONE_PRODUCT.getErrorCode()) { //将一个产品添加到购物车失败
            return ShopMailError.ERROR_ADD_ONE_PRODUCT;

        } else if (code == ShopMailError.ERROR_UPDATE_PRODUCT_NUM.getErrorCode()) { //更新产品数量出现错误
            return ShopMailError.ERROR_UPDATE_PRODUCT_NUM;

        } else if (code == ShopMailError.ERROR_VERIFY_SIGN.getErrorCode()) { //参数签名验证不过
            return ShopMailError.ERROR_VERIFY_SIGN;

        } else {
            return ShopMailError.OTHER_ERROR;

        }
    }
}
