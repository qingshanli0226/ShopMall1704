package com.example.net;
//网络地址   测试token   bad9ae9d-7888-4730-b9bb-48f2a082d0acAND1575470247110
public class AppNetConfig {
    //服务器IP
    public static final String IPADDRESS = "49.233.93.155";

    public static final String BASE_URL = "http://" + IPADDRESS + ":8080";

    //请求数据
    public static final String BASE_URL_JSON=BASE_URL+"/atguigu/json/";

    // 请求图片基本URL
    public static final String BASE_URl_IMAGE =BASE_URL+"/atguigu/img";

    //TODO 使用: BASE_URL_JSON
    //首页  GET 无参数
    public static final String HOME_URL="HOME_URL.json";
    //分类Fragment里面的标签Fragment页面数据
    public static final String TAG_URL ="TAG_URL.json";
    //小裙子
    public static final String SKIRT_URL ="SKIRT_URL.json";
    //上衣
    public static final String JACKET_URL ="JACKET_URL.json";
    //下装(裤子)
    public static final String PANTS_URL = "PANTS_URL.json";
    //外套
    public static final String OVERCOAT_URL = "OVERCOAT_URL.json";
    //配件
    public static final String ACCESSORY_URL = "ACCESSORY_URL.json";
    //包包
    public static final String BAG_URL = "BAG_URL.json";
    //装扮
    public static final String DRESS_UP_URL = "DRESS_UP_URL.json";
    //居家宅品
    public static final String HOME_PRODUCTS_URL = "HOME_PRODUCTS_URL.json";
    //办公文具
    public static final String STATIONERY_URL = "STATIONERY_URL.json";
    //数码周边
    public static final String DIGIT_URL = "DIGIT_URL.json";
    //游戏专区
    public static final String GAME_URL =  "GAME_URL.json";

    public static final String NEW_POST_URL =  "NEW_POST_URL.json";
    public static final String HOT_POST_URL ="HOT_POST_URL.json";

    //页面的具体数据的id
    public static final String GOODSINFO_URL = "GOODSINFO_URL.json";

    //服饰
    public static final String CLOSE_STORE =   "CLOSE_STORE.json";
    //游戏
    public static final String GAME_STORE =   "GAME_STORE.json";
    //动漫
    public static final String COMIC_STORE =   "COMIC_STORE.json";
    //cosplay
    public static final String COSPLAY_STORE =   "COSPLAY_STORE.json";
    //古风
    public static final String GUFENG_STORE =  "GUFENG_STORE.json";
    //漫展
    public static final String STICK_STORE =  "STICK_STORE.json";
    //文具
    public static final String WENJU_STORE = "WENJU_STORE.json";
    //零食
    public static final String FOOD_STORE = "FOOD_STORE.json";
    //首饰厂
    public static final String SHOUSHI_STORE ="SHOUSHI_STORE.json";

    //TODO 使用: BASE_URL
    //用户处理接口
    //注册 POST  参数:name=xxx   password=xxx
    public static final String REGISTER="register";

    //登录 POST  参数:name=xxx   password=xxx
    public static final String LOGIN="login";

    //自动登录 POST  参数:token=xxx
    public static final String AUTOLOGIN="autoLogin";

    //更新电话  POST  请求头加token  参数:phone=xxx
    public static final String UPDATEPHONE="updatePhone";

    //更新现金  POST  请求头加token  参数:money=xxx
    public static final String UPDATEMONEY="updateMoney";

    //上传头像 POST 请求头加token   参数:MultipartFile  file
    /**
     *  @Multipart
     *     @POST("upload")
     *     Observable<ResEntity < String>> upload(@Part MultipartBody.Part file);
     * */
    public static final String UPLOAD="upload";

    //下载文件  GET
    //TODO 暂时失效
    public static final String DOWNLOADFILE="downloadFile";

    //更新积分  POST  请求头加token  参数:point=xxx
    public static final String UPDATEPOINT="updatePoint";

    //更新邮箱 POST 请求头加token 参数未知,必定成功
    //TODO 无用
    public static final String UPDATEPEMAIL="updateEmail";

    //更新地址 POST 请求头加token 参数:address=xxx
    public static final String UPDATEADDRESS="updateAddress";

    //退出登录 POST 请求加token  无参数
    public static final String LOGOUT="logout";

    //业务接口
    //推荐产品 GET
    public static final String GETRECOMMEND="getRecommend";

    //搜索数据  GET
    //TODO 暂时失效
    public static final String SEARCH="search";

    //检查库存 POST 请求加token 参数:productId=xxx  productNum=xxx
    public static final String CHECKONPRODUCTINVENTORY="checkOneProductInventory";

    //加入购物车  POST 请求头需要添加token  JSON {"productId":"1512","productNum":1,"productName":"衬衫","url":"http:\/\/www.baidu.com"}
    public static final String ADDONEPRODUCT="addOneProduct";

    //获取购物车  GET  请求头需要添加token  {"code":"200","message":"请求成功","result":"[{\"productId\":\"1512\",\"productNum\":1,\"productName\":\"衬衫\",\"url\":\"http://www.baidu.com\"}]"}
    public static final String GETSHORTCARTPRODUCTS="getShortcartProducts";

    //更新购物车 POST 请求头需要添加token {"productId":"1512","productNum":10,"productName":"衬衫","url":"http:\/\/www.baidu.com"}
    public static final String UPDATEPRODUCTNUM="updateProductNum";

    //检查多个物品库存 POST 加token [{"productId":"1000","productNum":"0","productName":"衬衫","url":"http:\/\/www.baidu.com"},{"productId":"1001","productNum":"1","productName":"衬衫","url":"http:\/\/www.baidu.com"},{"productId":"1002","productNum":"2","productName":"衬衫","url":"http:\/\/www.baidu.com"},{"productId":"1003","productNum":"3","productName":"衬衫","url":"http:\/\/www.baidu.com"},{"productId":"1004","productNum":"4","productName":"衬衫","url":"http:\/\/www.baidu.com"}]
    public static final String CHECKINVENTORY="checkInventory";//检查服务端多个产品是否库存充足  POST 请求头需要添加token

    //下订单  POST 加token  {"subject":"buy","totalPrice":"500","body":[{"productName":"jacket","productId":"1000"},{"productName":"jacket","productId":"1001"},{"productName":"jacket","productId":"1002"},{"productName":"jacket","productId":"1003"},{"productName":"jacket","productId":"1004"}]}
    public static final String GETORDERINFO="getOrderInfo";

    //是否支付成功  POST 加token {"outTradeNo":"112810091743722","result":"{\"alipay_trade_app_pay_response\":{\"code\":\"10000\",\"msg\":\"Success\",\"app_id\":\"2016092200570833\",\"auth_app_id\":\"2016092200570833\",\"charset\":\"utf-8\",\"timestamp\":\"2019-11-28 10:13:39\",\"out_trade_no\":\"112810091743722\",\"total_amount\":\"500.00\",\"trade_no\":\"2019112822001427891000068428\",\"seller_id\":\"2088102176778571\"},\"sign\":\"hDLn0JJ3M2DBu6i9aQCzw7W2SZ+gldu1Y2qDfOMXEdgj81vfVfXuCqOaX25aAi+StMuGfVb3Y5W495LaKPo5ItYo+HerhIBVFfAIyvNzC6XTLXGu7XEWXZumjMhT8EQbIRWFk76f4okzaqFNRqsE1\\\/VN8IwPVtEIjIpMBE\\\/YXBlQvk0k+uP9M6KNlli4UpaBdYu1GeNrd282X+IgDgMJje39yhS2blrvKrsT+pNuhyMsKX3RJlnoMYVjujEYgQZst5fs\\\/9zunNXC\\\/w0axcOIaePYU5eY7kQ7kuFcSy6JEeoycrA9o7hBWQZVKir8mDiMnwWKsE+rn9Ue4XNNSsCDdA==\",\"sign_type\":\"RSA2\"}","clientPayResult":true}
    public static final String CONFIRMSERVERPAYRESULT="confirmServerPayResult";//请求服务端，是否支付成功  POST 请求头需要添加token

    //待支付订单  GET 无参数
    public static final String FINDFORPAY="findForPay";

    //待发货订单  GET 无参数
    public static final String FINDFORSEND="findForSend";
    //TODO 加载头像 GET BASE_URL+用户的avatar
    //TODO 加载图片 GET BASE_URl_IMAGE+请求到的图片url
}
