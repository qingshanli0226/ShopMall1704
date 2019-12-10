package com.example.net;

public class AppNetConfig {
    //标记是否打印log
    public static final boolean PRINT_LOG = true;

    //服务端
    public static final String BASE_URL = "http://49.233.93.155:8080/";

    ////////////////////////////////////////////
    //商品接口

    // 1.请求Json数据基本URL
    public static final String BASE_URL_JSON = BASE_URL+"atguigu/json/";

    //2.请求图片基本URL
    public static final String BASE_URl_IMAGE = BASE_URL+"atguigu/img";
//    http://49.233.93.155:8080/atguigu/img  /1478770583834.png

    //3.主页Fragment路径
    public static final String HOME_URL ="atguigu/json/HOME_URL.json";

    //小裙子
    public static final String SKIRT_URL = BASE_URL_JSON + "SKIRT_URL.json";
    //上衣
    public static final String JACKET_URL = BASE_URL_JSON + "JACKET_URL.json";
    //下装(裤子)
    public static final String PANTS_URL = BASE_URL_JSON + "PANTS_URL.json";
    //外套
    public static final String OVERCOAT_URL = BASE_URL_JSON + "OVERCOAT_URL.json";
    //配件
    public static final String ACCESSORY_URL = BASE_URL_JSON + "ACCESSORY_URL.json";
    //包包
    public static final String BAG_URL = BASE_URL_JSON + "BAG_URL.json";
    //装扮
    public static final String DRESS_UP_URL = BASE_URL_JSON + "DRESS_UP_URL.json";
    //居家宅品
    public static final String HOME_PRODUCTS_URL = BASE_URL_JSON + "HOME_PRODUCTS_URL.json";
    //办公文具
    public static final String STATIONERY_URL = BASE_URL_JSON + "STATIONERY_URL.json";
    //数码周边
    public static final String DIGIT_URL = BASE_URL_JSON +  "DIGIT_URL.json";
    //游戏专区
    public static final String GAME_URL = BASE_URL_JSON + "GAME_URL.json";

    //User 处理接口
    //1. 注册接口处理 post
    public static final String REGISTER_URL = "register";

    //2. 登录接口处理 post
    public static final String LOGIN_URL = "login";


    //3. 自动登录接口 post
    public static final String AUTO_LOGIN_URL = "autoLogin";


    //4. 更新用户绑定的电话 post
    public static final String UPDATE_PHONE_URL = "updatePhone";


    //5. 更新现金的接口 post
    public static final String UPDATE_MONEY_URL = "updateMoney";

    //6. 上传头像文件的接口 post
    public static final String UPLOAD_HEAD_ICON_URL = "upload";

    //7. 下载文件的接口 get
    public static final String DOWN_LOAD_FILE_URL = "downloadFile";

    //8. 更新积分的接口 post
    public static final String UPDATE_POINT_URL = "updatePoint";

    //9. 更新邮箱的接口 post
    public static final String UPDATE_EMAIL_URL = "updateEmail";

    //10. 更新地址的接口 post
    public static final String UPDATE_ADDRESS_URL = "updateAddress";

    //11. 退出登录接口 post
    public static final String LOGOUT_URL = "logout";


    //////////////////////////////////////////
    /////////////////////////////////////////
    // 业务接口
    //12. 获取推荐产品信息的接口 get
    public static final String GET_RECOMMEND_URL = "getRecommend";

    //13. 搜索数据的接口 get
    public static final String SEARCH_URL = "search";

    //14. 检查服务端一个产品库存情况的接口 post
    public static final String CHECK_ONE_PRODUCT_INVENTORY_URL = "checkOneProductInventory";

    //15. 向服务端购物车添加一个产品的接口 post
    public static final String ADD_ONE_PRODUCT_URL = "addOneProduct";

    //16. 获取服务端购物车产品信息的接口 Get
    public static final String GET_SHORTCART_PRODUCTS_URL = "getShortcartProducts";

    //17. 更新服务端购物车产品的数量的接口 post
    public static final String UPDATE_PRODUCT_NUM_URL = "updateProductNum";

    //18. 检查服务端多个产品是否库存充足 post
    public static final String CHECK_INVENTORY_URL = "checkInventory";

    //19. 向服务端下订单接口 post
    public static final String GET_ORDER_INFO_URL = "getOrderInfo";

    //20. 请求服务端，是否支付成功 post
    public static final String CONFIRM_SERVER_PAY_RESULT_URL = "confirmServerPayResult";

    //21. 查找待支付的订单 get
    public static final String FIND_FOR_PAY = "findForPay";

    //22. 查找待发货的订单 get
    public static final String FIND_FOR_SEND = "findForSend";




}
