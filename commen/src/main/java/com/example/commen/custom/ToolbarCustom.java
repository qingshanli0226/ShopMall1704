package com.example.commen.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.TintTypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.commen.R;

public class ToolbarCustom extends LinearLayout {
    private final Context mContext;
    private LinearLayout mTbLl;
    private TextView mTbTitleTV;
    private ImageView mTbLeftIV;
    private TextView mTbRightTv;
    private ImageView mTbRightIv;
    private TintTypedArray tintTypedArray;
    private TextView mTbSearch;

    public ToolbarCustom(Context context) {
        this(context, null);
    }

    public ToolbarCustom(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @SuppressLint("RestrictedApi")
    public ToolbarCustom(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        LayoutInflater.from(mContext).inflate(R.layout.toolbar, this);

        if (attrs != null) {
            tintTypedArray = TintTypedArray.obtainStyledAttributes(mContext, attrs, R.styleable.ToolbarCustom, defStyleAttr, 0);
        }
        initView();
    }

    @SuppressLint("RestrictedApi")
    private void initView() {
        mTbLl = findViewById(R.id.ll_toolbar);
        mTbTitleTV = findViewById(R.id.tv_toolbar_title);
        mTbLeftIV = findViewById(R.id.iv_toolbar_left);
        mTbRightTv = findViewById(R.id.tv_toolbar_right);
        mTbRightIv = findViewById(R.id.iv_toolbar_right);
        mTbSearch = findViewById(R.id.tv_toolbar_search);

        //获取状态栏高度
        int height = getStatusBarHeight(mContext);
        mTbLl.setPadding(0, height, 0, 0); //设置高度


        if (tintTypedArray != null) {
            //标题
            String title = tintTypedArray.getString(R.styleable.ToolbarCustom_tb_title_text);
            if (title != null)
                mTbTitleTV.setText(title); //设置标题
            boolean isShowTitle = tintTypedArray.getBoolean(R.styleable.ToolbarCustom_tb_title_isShow, false);
            mTbTitleTV.setVisibility(isShowTitle ? View.VISIBLE : View.GONE); //设置是否隐藏
            float titleSize = tintTypedArray.getDimension(R.styleable.ToolbarCustom_tb_title_size, getResources().getDimension(R.dimen.toolbar_text_size_sp_22));
            mTbTitleTV.setTextSize(px2sp(titleSize));//文字大小
            int titleColor = tintTypedArray.getColor(R.styleable.ToolbarCustom_tb_title_color, getResources().getColor(R.color.toolbar_title_color_white));
            mTbTitleTV.setTextColor(titleColor); //文字颜色


            //左图标
            Drawable leftIcon = tintTypedArray.getDrawable(R.styleable.ToolbarCustom_tb_left_iv_icon);
            mTbLeftIV.setImageDrawable((leftIcon != null) ? leftIcon : getResources().getDrawable(R.drawable.ic_action_back_left_white));
            boolean isShowLeftImageView = tintTypedArray.getBoolean(R.styleable.ToolbarCustom_tb_left_iv_isShow, false);
            mTbLeftIV.setVisibility(isShowLeftImageView ? View.VISIBLE : View.GONE);


            //右图标
            Drawable rightIcon = tintTypedArray.getDrawable(R.styleable.ToolbarCustom_tb_right_iv_icon);
            if (rightIcon != null) {
                mTbRightIv.setImageDrawable(rightIcon);
            }
            boolean isShowRightImageView = tintTypedArray.getBoolean(R.styleable.ToolbarCustom_tb_right_iv_isShow, false);
            mTbRightIv.setVisibility(isShowRightImageView ? View.VISIBLE : View.GONE);


            //右文本
            String rightText = tintTypedArray.getString(R.styleable.ToolbarCustom_tb_right_tv_text);
            if (rightIcon != null)
                mTbRightTv.setText(rightText); //设置内容
            boolean isShowRightTextView = tintTypedArray.getBoolean(R.styleable.ToolbarCustom_tb_right_tv_isShow, false);
            mTbRightTv.setVisibility(isShowRightTextView ? View.VISIBLE : View.GONE); //设置是否隐藏
            float rightTextSize = tintTypedArray.getDimension(R.styleable.ToolbarCustom_tb_right_tv_size, getResources().getDimension(R.dimen.toolbar_text_size_sp_16));
            mTbRightTv.setTextSize(px2sp(rightTextSize));//文字大小
            int rightTextColor = tintTypedArray.getColor(R.styleable.ToolbarCustom_tb_right_tv_color, getResources().getColor(R.color.toolbar_title_color_white));
            mTbRightTv.setTextColor(rightTextColor); //文字颜色

            //搜索
            boolean isShowSearch = tintTypedArray.getBoolean(R.styleable.ToolbarCustom_tb_search_isShow, false);
            mTbSearch.setVisibility(isShowSearch ? View.VISIBLE : View.INVISIBLE);
        }
    }

    //设置标题内容
    public void setTitle(String title) {
        mTbTitleTV.setText(title);
    }

    //获取标题内容
    public String getTitle() {
        return mTbTitleTV.getText().toString();
    }

    //设置标题大小
    public void setTitleSize(float size) {
        mTbTitleTV.setTextSize(size);
    }


    //点击事件监听
    //标题
    public void setTbTitleTVOnClickListener(OnClickListener listener) {
        mTbTitleTV.setOnClickListener(listener);
    }

    //右图
    public void setTbRightIvOnClickListener(OnClickListener listener) {
        mTbRightIv.setOnClickListener(listener);
    }

    //左图
    public void setTbLeftIVOnClickListener(OnClickListener listener) {
        mTbLeftIV.setOnClickListener(listener);
    }

    //右文本
    public void setTbRightTvOnClickListener(OnClickListener listener) {
        mTbRightTv.setOnClickListener(listener);
    }

    //搜索
    public void setTbSearchOnClickListener(OnClickListener listener) {
        mTbSearch.setOnClickListener(listener);
    }


    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }

    public TextView getTbTitleTV() {
        return mTbTitleTV;
    }

    public ImageView getTbLeftIV() {
        return mTbLeftIV;
    }

    public TextView getTbRightTv() {
        return mTbRightTv;
    }

    public ImageView getTbRightIv() {
        return mTbRightIv;
    }

    public TextView getTbSearch() {
        return mTbSearch;
    }

    //将dp转化为px
    public int dp2px(float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, getContext().getResources().getDisplayMetrics());
    }

    //将sp转px
    public int sp2px(float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, getContext().getResources().getDisplayMetrics());
    }

    public float px2dp(float pxVal) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }

    public float px2sp(float pxVal) {
        return (pxVal / getContext().getResources().getDisplayMetrics().scaledDensity);
    }
}
