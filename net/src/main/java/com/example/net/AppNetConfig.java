package com.example.net;
//网络地址
public class AppNetConfig {
    //服务器IP
    public static final String IPADDRESS = "49.233.93.155";

    public static final String BASE_URL = "http://" + IPADDRESS + ":8080";


    //用户处理接口
    public static final String REGISTER="register";//注册 POST

    public static final String LOGIN="login";//登录 POST

    public static final String AUTOLOGIN="autoLogin";//自动登录接口 POST

    public static final String UPDATEPHONE="updatePhone";//更新用户绑定的电话 POST  请求头需要添加token

    public static final String UPDATEMONEY="updateMoney";//更新现金的接口 POST 请求头需要添加token

    public static final String UPLOAD="upload";//上传头像文件的接口 POST 请求头需要添加token

    public static final String DOWNLOADFILE="downloadFile";//下载文件的接口  GET

    public static final String UPDATEPOINT="updatePoint";//更新积分的接口 POST 请求头需要添加token

    public static final String UPDATEPEMAIL="updateEmail";//更新邮箱的接口 POST 请求头需要添加token

    public static final String UPDATEADDRESS="updateAddress";//更新地址的接口 POST 请求头需要添加token

    public static final String LOGOUT="logout";//退出登录接口 POST 请求头需要添加token

    //业务接口
    public static final String GETRECOMMEND="getRecommend";//获取推荐产品信息的接口 GET

    public static final String SEARCH="search";//搜索数据的接口  GET

    public static final String CHECKONPRODUCTINVENTORY="checkOneProductInventory";//检查服务端一个产品库存情况的接口  POST 请求头需要添加token

    public static final String ADDONEPRODUCT="addOneProduct";//向服务端购物车添加一个产品的接口  POST 请求头需要添加token

    public static final String GETSHORTCARTPRODUCTS="getShortcartProducts";//获取服务端购物车产品信息的接口  POST 请求头需要添加token

    public static final String UPDATEPRODUCTNUM="updateProductNum";//更新服务端购物车产品的数量的接口  POST 请求头需要添加token

    public static final String CHECKINVENTORY="checkInventory";//检查服务端多个产品是否库存充足  POST 请求头需要添加token

    public static final String GETORDERINFO="getOrderInfo";//向服务端下订单接口  POST 请求头需要添加token

    public static final String CONFIRMSERVERPAYRESULT="confirmServerPayResult";//请求服务端，是否支付成功  POST 请求头需要添加token

    public static final String FINDFORPAY="findForPay";//查找待支付的订单  GET 请求头需要添加token

    public static final String FINDFORSEND="findForSend";//查找待发货的订单  GET 请求头需要添加token
}
