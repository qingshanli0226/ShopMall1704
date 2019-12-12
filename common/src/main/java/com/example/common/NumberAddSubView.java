package com.example.common;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.TintTypedArray;

public class NumberAddSubView extends LinearLayout implements View.OnClickListener {
    private ImageView btn_sub;
    private ImageView btn_add;
    private TextView tv_count;
    String mPrice = "";
    private int value = 1;
    private int minValue = 1;
    boolean ischecked = false;
    private TextView tv_price;
    private int maxValue = 999999;
    private int position = 0;

    public int getValue() {
        String countStr = tv_count.getText().toString().trim();//文本内容
        if (countStr != null) {
            value = Integer.valueOf(countStr);
        }
        return value;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public NumberAddSubView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //把布局和当前类形成整体
        View.inflate(context, R.layout.number_add_sub_layout, this);
        btn_sub = (ImageView) findViewById(R.id.btn_sub);
        btn_add = (ImageView) findViewById(R.id.btn_add);
        tv_count = (TextView) findViewById(R.id.tv_count);
        tv_price = findViewById(R.id.tv_price_gov);

        getValue();

        //设置点击事件
        btn_add.setOnClickListener(this);
        btn_sub.setOnClickListener(this);

        if (attrs != null) {
            //取出属性
            TintTypedArray tintTypedArray = TintTypedArray.obtainStyledAttributes(context, attrs, R.styleable.NumberAddSubView);
            int value = tintTypedArray.getInt(R.styleable.NumberAddSubView_value, 0);
            if (value > 0) {
                setValue(value);
            }
            int minValue = tintTypedArray.getInt(R.styleable.NumberAddSubView_minValue, 0);
            if (value > 0) {
                setMinValue(minValue);
            }
            int maxValue = tintTypedArray.getInt(R.styleable.NumberAddSubView_maxValue, 0);
            if (value > 0) {
                setMaxValue(maxValue);
            }
            Drawable addDrawable = tintTypedArray.getDrawable(R.styleable.NumberAddSubView_numberAddBackground);
            if (addDrawable != null) {
                btn_add.setImageDrawable(addDrawable);
            }
            Drawable subDrawable = tintTypedArray.getDrawable(R.styleable.NumberAddSubView_numberSubBackground);
            if (subDrawable != null) {
                btn_sub.setImageDrawable(subDrawable);
            }
        }
    }

    public void setPostion(int positon) {
        this.position = positon;
    }

    public void setValue(int value) {
        this.value = value;
        tv_count.setText(String.valueOf(value));
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public NumberAddSubView(Context context) {
        this(context, null);
    }

    public NumberAddSubView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public void setChecked(boolean ischecked) {
        this.ischecked = ischecked;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_add) {
            //加
            if (value < maxValue) {
                addNumber();
                if (onNumberChangeListener != null) {
                    onNumberChangeListener.addNumber(v, value, mPrice, ischecked, position);
                }
            }
        } else {
            //减
            if (value > minValue) {
                subNumber();
                if (onNumberChangeListener != null) {
                    onNumberChangeListener.subNumner(v, value, mPrice, ischecked, position);
                }
            }

        }
    }

    public String getPrice() {
        return mPrice;
    }

    public void setPrice(String price) {
        mPrice = price;
        tv_price.setText("￥" + price);
    }

    private void subNumber() {
        value -= 1;
        setValue(value);
    }

    private void addNumber() {
        value += 1;
        setValue(value);
    }

    public interface OnNumberChangeListener {
        //当按钮被点击的时候回调
        void addNumber(View view, int value, String price, boolean ischecked, int postion);

        void subNumner(View view, int value, String price, boolean ischecked, int postion);
    }

    private OnNumberChangeListener onNumberChangeListener;

    public void setOnNumberChangeListener(OnNumberChangeListener onNumberChangeListener) {
        this.onNumberChangeListener = onNumberChangeListener;
    }
}
