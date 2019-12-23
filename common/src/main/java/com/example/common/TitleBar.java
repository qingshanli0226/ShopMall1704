package com.example.common;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;

//自定义标题栏
public class TitleBar extends LinearLayout {

    private Context context;

    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private TextView mTvCenter;
    private TextView mTvLeft;
    private TextView mTvRight;
    private ImageView mIvCenter;
    private ImageView mIvLeft;
    private ImageView mIvRight;
    private RelativeLayout mTitle;
    private TitleClickLisner titleClickLisner;
    private TextView mTvMessage;

    //初始化
    private void init() {
        View view = LayoutInflater.from(context).inflate(R.layout.titlebar_layout, this);
        mTvCenter = view.findViewById(R.id.tv_center);
        mTvLeft = view.findViewById(R.id.tv_left);
        mTvRight = view.findViewById(R.id.tv_right);
        mIvCenter = view.findViewById(R.id.iv_center);
        mIvLeft = view.findViewById(R.id.iv_left);
        mIvRight = view.findViewById(R.id.iv_right);
        mTitle = view.findViewById(R.id.rl_title);
        mTvMessage = view.findViewById(R.id.tv_red_message);

        mIvRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                titleClickLisner.RightClick();
            }
        });
        mTvRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                titleClickLisner.RightClick();
            }
        });
        mIvLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                titleClickLisner.LeftClick();
            }
        });
        mTvLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                titleClickLisner.LeftClick();
            }
        });
        mIvCenter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                titleClickLisner.CenterClick();
            }
        });
        mTvCenter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                titleClickLisner.CenterClick();
            }
        });
    }

    //设置标题栏监听
    public void setTitleClickLisner(TitleClickLisner titleClickLisner) {
        this.titleClickLisner = titleClickLisner;
    }

    //标题栏显示红色消息
    public void setMessageShow(boolean show) {
        if (show){
            mTvMessage.setVisibility(View.VISIBLE);
        }else {
            mTvMessage.setVisibility(View.GONE);
        }
    }

    //设置标题栏标题内容
    public void setCenterText(String msg) {
        if (msg != null) {
            mTvCenter.setText(msg);
        }
    }

    //设置标题栏标题内容和字体大小
    public void setCenterText(String msg, int size) {
        if (msg != null) {
            mTvCenter.setText(msg);
            mTvCenter.setTextSize(size);
        }
    }

    //设置标题栏标题内容和字体大小和文字颜色
    public void setCenterText(String msg, int size, @ColorInt int color) {
        mTvCenter.setText(msg);
        mTvCenter.setTextSize(size);
        mTvCenter.setTextColor(color);
    }

    //设置标题栏左标题内容
    public void setLeftText(String msg) {
        if (msg != null) {
            mTvLeft.setText(msg);
        }
    }

    //设置标题栏左标题内容和字体大小
    public void setLeftText(String msg, int size) {
        if (msg != null) {
            mTvLeft.setText(msg);
            mTvLeft.setTextSize(size);
        }
    }

    //设置标题栏左标题内容和字体大小和字体颜色
    public void setLeftText(String msg, int size, @ColorInt int color) {
        mTvLeft.setText(msg);
        mTvLeft.setTextSize(size);
        mTvLeft.setTextColor(color);
    }

    //设置标题栏右标题内容
    public void setRightText(String msg) {
        if (msg != null) {
            mTvRight.setText(msg);
        }
    }

    //设置标题栏右标题内容和字体大小
    public void setRightText(String msg, int size) {
        if (msg != null) {
            mTvRight.setText(msg);
            mTvRight.setTextSize(size);
        }
    }

    //设置标题栏右标题内容和字体大小和字体大小和字体颜色
    public void setRightText(String msg, int size, @ColorInt int color) {
        mTvRight.setText(msg);
        mTvRight.setTextSize(size);
        mTvRight.setTextColor(color);
    }

    //设置中间的图片
    public void setCenterImg(@DrawableRes int imgRes) {
        mIvCenter.setImageResource(imgRes);
    }

    //设置左边的图片
    public void setLeftImg(@DrawableRes int imgRes) {
        mIvLeft.setImageResource(imgRes);
    }

    //设置右边的图片
    public void setRightImg(@DrawableRes int imgRes) {
        mIvRight.setImageResource(imgRes);
    }

    //设置标题栏背景
    public void setTitleBacKGround(@ColorInt int colorRes) {
        mTitle.setBackgroundColor(colorRes);
    }

    //标题栏监听
    public interface TitleClickLisner {
        void LeftClick();//左边监听

        void RightClick();

        void CenterClick();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
