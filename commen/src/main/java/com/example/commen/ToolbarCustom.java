package com.example.commen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.TintTypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ToolbarCustom extends LinearLayout {
    private final Context mContext;
    private LinearLayout mLlToolbar;
    private TextView mTvToolbarTitle;
    private ImageView mIvToolbarBackLeft;
    private TextView mTvToolbarSetting;
    private ImageView mIvToolbarMessageRight;
    private TintTypedArray tintTypedArray;

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

        //        //获取状态栏高度
        //        int height = getStatusBarHeight(mContext);
        //        //设置Margin高度
        //        MarginLayoutParams marginLayoutParams = new MarginLayoutParams(mContext, attrs);
        //        marginLayoutParams.topMargin = height;
        //        setLayoutParams(marginLayoutParams);
        //        //        setPadding(0, height, 0, 0);


        if (attrs != null) {
            tintTypedArray = TintTypedArray.obtainStyledAttributes(mContext, attrs, R.styleable.ToolbarCustom, defStyleAttr, 0);
        }
        initView();
    }

    @SuppressLint("RestrictedApi")
    private void initView() {
        mLlToolbar = findViewById(R.id.ll_toolbar);
        mTvToolbarTitle = findViewById(R.id.tv_toolbar_title);
        mIvToolbarBackLeft = findViewById(R.id.iv_toolbar_back_left);
        mTvToolbarSetting = findViewById(R.id.tv_toolbar_setting);
        mIvToolbarMessageRight = findViewById(R.id.iv_toolbar_message_right);

        //获取状态栏高度
        int height = getStatusBarHeight(mContext);
        //        //设置Margin高度
        //        MarginLayoutParams marginLayoutParams = new MarginLayoutParams(mContext, attrs);
        //        marginLayoutParams.topMargin = height;
        //        setLayoutParams(marginLayoutParams);
        mLlToolbar.setPadding(0, height, 0, 0);


        if (tintTypedArray != null) {
            //设置左图标
            Drawable leftIcon = tintTypedArray.getDrawable(R.styleable.ToolbarCustom_leftImageViewIcon);
            if (leftIcon != null) {
                mIvToolbarBackLeft.setImageDrawable(leftIcon);
            }

            //是否显示
            boolean isShowLeftImageView = tintTypedArray.getBoolean(R.styleable.ToolbarCustom_isShowLeftImageView, true);
            mIvToolbarBackLeft.setVisibility(isShowLeftImageView ? View.VISIBLE : View.GONE);


            //设置右图标
            Drawable rightIcon = tintTypedArray.getDrawable(R.styleable.ToolbarCustom_rightImageViewIcon);
            if (rightIcon != null) {
                mIvToolbarMessageRight.setImageDrawable(rightIcon);
            }

            //是否显示
            boolean isShowRightImageView = tintTypedArray.getBoolean(R.styleable.ToolbarCustom_isShowRightImageView, true);
            mIvToolbarMessageRight.setVisibility(isShowRightImageView ? View.VISIBLE : View.GONE);
            //标题文字大小
            float textSize = mTvToolbarTitle.getTextSize() / 3;
            float titleSize = tintTypedArray.getDimension(R.styleable.ToolbarCustom_titleSize, textSize);
            mTvToolbarTitle.setTextSize(titleSize);

            //标题内容
            String string = tintTypedArray.getString(R.styleable.ToolbarCustom_title);
            if (string != null) {
                mTvToolbarTitle.setText(string);
            }


            //字体颜色
            int titleColor = tintTypedArray.getColor(R.styleable.ToolbarCustom_titleColor, Color.argb(255, 74, 74, 74));
            mTvToolbarTitle.setTextColor(titleColor);


            //标题是否显示
            boolean isShowTitleTextView = tintTypedArray.getBoolean(R.styleable.ToolbarCustom_isShowTitleTextView, true);
            mTvToolbarTitle.setVisibility(isShowTitleTextView ? View.VISIBLE : View.GONE);

            //设置按钮显示
            boolean isShowSettingTextView = tintTypedArray.getBoolean(R.styleable.ToolbarCustom_isShowSettingTextView, true);
            mTvToolbarSetting.setVisibility(isShowSettingTextView ? View.VISIBLE : View.GONE);

        }
    }

    //设置标题内容
    public void setTitle(String title) {
        mTvToolbarTitle.setText(title);
    }

    //获取标题内容
    public String getTitle() {
        return mTvToolbarTitle.getText().toString();
    }

    //设置标题大小
    public void setTitleSize(float size) {
        mTvToolbarTitle.setTextSize(size);
    }


    //点击事件监听
    //消息
    public void setRightImageViewOnClickListener(OnClickListener listener) {
        mIvToolbarMessageRight.setOnClickListener(listener);
    }

    //返回
    public void setLeftBackImageViewOnClickListener(OnClickListener listener) {
        mIvToolbarBackLeft.setOnClickListener(listener);
    }

    //设置
    public void setSettingTextViewOnClickListener(OnClickListener listener) {
        mTvToolbarSetting.setOnClickListener(listener);
    }

    //标题
    public void setTitleTextViewOnClickListener(OnClickListener listener) {
        mTvToolbarTitle.setOnClickListener(listener);
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
}
