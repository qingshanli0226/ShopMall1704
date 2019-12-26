package com.example.common.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.example.common.R;
import com.example.common.code.Constant;

public class MyToolBar extends Toolbar {

    //TODO 首页风格
    private RelativeLayout home_layout;
    private TextView home_search;
    private ImageView home_camera;
    private TextView home_search_back;
    private TextView home_message;

    //TODO 搜索风格
    private LinearLayout search_layout;
    private ImageView scan;
    private EditText search_edit;
    private ImageView search_camera;
    private ImageView search_message;
    private TextView search_text;

    //TODO 发现风格
    private RelativeLayout find_layout;
    private ImageView find_user;
    private ImageView find_search;
    private ImageView find_message;

    //TODO 购物车和消息页面风格
    private LinearLayout buy_message_layout;
    private ImageView message_back;
    private TextView buy_message_title;
    private ImageView buy_car_back;
    private ImageView buy_message_icon;
    private TextView buy_compile;
    private ImageView buy_menu;
    private ImageView message_calendar;
    private ImageView message_menu;

    //TODO 我的风格
    private RelativeLayout mine_layout;
    private ImageView mine_head;
    private ImageView mine_setting;
    private ImageView mine_message;

    //TODO 其他风格
    private RelativeLayout other_layout;
    private ImageView other_back;
    private TextView other_title;

    //TODO 无网络标题
    private LinearLayout notLayout;
    //TODO toolbar部分
    private RelativeLayout toolbar;

    /**
     * 首页风格用到的控件
     * @return
     */
    //TODO 获取首页搜索框
    public TextView getHome_search() {
        return home_search;
    }

    //TODO 获取首页相机控件
    public ImageView getHome_camera() {
        return home_camera;
    }

    //TODO 获取扫一扫控件
    public ImageView getScan() {
        return scan;
    }

    //TODO 获取首页的扫一扫
    public TextView getHome_search_back(){
        return home_search_back;
    }
    //TODO 获取首页的消息
    public TextView getHome_message(){
        return home_message;
    }


    /**
     * 搜素风格
     * @return
     */
    //TODO 获取搜索页面的EditText
    public TextView getSearch_edit() {
        return search_edit;
    }

    //TODO 获取搜素页面的相机控件
    public ImageView getSearch_camera() {
        return search_camera;
    }

    //TODO 获取搜索风格消息控件
    public ImageView getSearch_message() {
        return search_message;
    }

    //TODO 获取搜素页面的搜索字样控件
    public TextView getSearch_text() {
        return search_text;
    }


    /**
     * 发现风格
     * @return
     */
   //TODO 获取发现的用户控件
    public ImageView getFind_user() {
        return find_user;
    }
    //TODO 获取发现的搜索控件
    public ImageView getFind_search() {
        return find_search;
    }
    //TODO 获取发现的消息控件
    public ImageView getFind_message() {
        return find_message;
    }
    /**
     * 购物车与消息页面的风格
     * @return
     */
    //TODO 获取消息页面返回控件
    public ImageView getMessage_back() {
        return message_back;
    }
    //TODO 获取购物车页面的返回控件
    public ImageView getBuy_Car_Back(){
        return buy_car_back;
    }

    //TODO 获取风格中的文本控件
    public TextView getBuy_message_title() {
        return buy_message_title;
    }
    //TODO 获取文本旁边的ImageView控件
    public ImageView getBuy_message_icon() {
        return buy_message_icon;
    }

    //TODO 获取风格中菜单控件
    public ImageView getBuy_menu() {
        return buy_menu;
    }

    //TODO 获取编辑控件
    public TextView getBuy_compile(){
        return buy_compile;
    }

    //TODO 获取日历控件
    public ImageView getMessage_calendar() {
        return message_calendar;
    }

    //TODO 获取消息菜单
    public ImageView getMessage_menu() {
        return message_menu;
    }

    /**
     * 我的风格
     * @return
     */
    //TODO 获取我的 头像控件
    public ImageView getMine_head(){
        return mine_head;
    }

    //TODO  获取我的 设置控件
    public ImageView getMine_setting() {
        return mine_setting;
    }

    //TODO 获取我的 消息控件
    public ImageView getMine_message() {
        return mine_message;
    }

    public ImageView getBuy_car_back() {
        return buy_car_back;
    }

    /**
     * 其他风格
     * @return
     */
    //TODO 其他风格的返回控件
    public ImageView getOther_back() {
        return other_back;
    }

    //TODO 其他风格  的标题
    public TextView getOther_title() {
        return other_title;
    }


    public MyToolBar(Context context) {
        this(context, null);
    }

    public MyToolBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyToolBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.toolbar_layout, this);
        initView();
    }

    //TODO 挑选风格
    public void init(int type_style) {
        switch (type_style){
            case Constant.HOME_STYLE:
                GoneAll();
                home_layout.setVisibility(VISIBLE);
                break;
            case Constant.SEARCH_STYLE:
                GoneAll();
                search_layout.setVisibility(VISIBLE);
                break;
            case Constant.FIND_STYLE:
                GoneAll();
                find_layout.setVisibility(VISIBLE);
                break;
            case Constant.BUY_MESSAGE_STYLE:
                GoneAll();
                buy_message_layout.setVisibility(VISIBLE);
                break;
            case Constant.MINE_STYLE:
                GoneAll();
                mine_layout.setVisibility(VISIBLE);
                break;
            case Constant.OTHER_STYLE:
                GoneAll();
                other_layout.setVisibility(VISIBLE);
                break;
        }
    }
    //TODO 设置toolbar的背景
    public void setToolbarBackground(Drawable drawable){
        toolbar.setBackground(drawable);
    }

    public void isConnection(boolean flag){
        if(flag){
            notLayout.setVisibility(GONE);
        }else{
            notLayout.setVisibility(VISIBLE);
        }
    }

    public void GoneAll(){
        home_layout.setVisibility(GONE);
        search_layout.setVisibility(GONE);
        find_layout.setVisibility(GONE);
        buy_message_layout.setVisibility(GONE);
        mine_layout.setVisibility(GONE);
        other_layout.setVisibility(GONE);
    }

    private void initView(){
        home_search = findViewById(R.id.home_search);
        home_camera = findViewById(R.id.home_camera);
        home_layout = findViewById(R.id.home_layout);
        home_search_back = findViewById(R.id.home_search_back);
        home_message = findViewById(R.id.home_message);
        scan = findViewById(R.id.scan);
        search_edit= findViewById(R.id.search_edit);
        search_camera= findViewById(R.id.search_camera);
        search_message= findViewById(R.id.search_message);
        search_text= findViewById(R.id.search_text);
        search_layout= findViewById(R.id.search_layout);
        find_user= findViewById(R.id.find_user);
        find_search= findViewById(R.id.find_search);
        find_message= findViewById(R.id.find_message);
        find_layout= findViewById(R.id.find_layout);
        message_back= findViewById(R.id.message_back);
        buy_message_title= findViewById(R.id.buy_message_title);
        buy_message_icon= findViewById(R.id.buy_message_icon);
        buy_menu= findViewById(R.id.buy_menu);
        buy_compile = findViewById(R.id.buy_compile);
        message_calendar= findViewById(R.id.message_calendar);
        message_menu= findViewById(R.id.message_menu);
        buy_message_layout= findViewById(R.id.buy_message_layout);
        mine_head = findViewById(R.id.mine_head);
        mine_setting = findViewById(R.id.mine_setting);
        mine_message= findViewById(R.id.mine_message);
        mine_layout= findViewById(R.id.mine_layout);
        other_back= findViewById(R.id.other_back);
        other_title= findViewById(R.id.other_title);
        other_layout= findViewById(R.id.other_layout);

        notLayout = findViewById(R.id.not_internet);
        toolbar = findViewById(R.id.toolbar);
        buy_car_back = findViewById(R.id.buy_car_back);
    }
}
