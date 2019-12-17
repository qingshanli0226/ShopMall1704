package com.example.common;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;

//自定义底部导航
public class BottomBar extends LinearLayout {

    private Context mContext;

    public BottomBar(Context context) {
        this(context, null);
    }

    public BottomBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    private LinearLayout mBottombar;
    private RadioGroup mRadioGroup;
    private RadioButton mRadioOne;
    private RadioButton mRadioTwo;
    private RadioButton mRadioThree;
    private RadioButton mRadioFour;
    private RadioButton mRadioFive;
    private RadioButton[] radioButtons;

    private void init() {

        View view = LayoutInflater.from(mContext).inflate(R.layout.bottombar_layout, this);
        mBottombar = view.findViewById(R.id.ll_bottombar);
        mRadioGroup = view.findViewById(R.id.rg);
        mRadioOne = view.findViewById(R.id.rb_one);
        mRadioTwo = view.findViewById(R.id.rb_two);
        mRadioThree = view.findViewById(R.id.rb_three);
        mRadioFour = view.findViewById(R.id.rb_four);
        mRadioFive = view.findViewById(R.id.rb_five);
        radioButtons = new RadioButton[]{mRadioOne, mRadioTwo, mRadioThree, mRadioFour, mRadioFive};
        mRadioOne.setChecked(true);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                int indexInt = 0;
                if (checkedId == R.id.rb_one) {
                    indexInt = 0;
                    mRadioOne.setChecked(true);
                } else if (checkedId == R.id.rb_two) {
                    indexInt = 1;
                    mRadioTwo.setChecked(true);
                } else if (checkedId == R.id.rb_three) {
                    indexInt = 2;
                    mRadioThree.setChecked(true);
                } else if (checkedId == R.id.rb_four) {
                    indexInt = 3;
                    mRadioFour.setChecked(true);
                } else if (checkedId == R.id.rb_five) {
                    indexInt = 4;
                    mRadioFive.setChecked(true);
                }
                if (onTapListener != null) {
                    onTapListener.tapItemClick(indexInt);
                }
            }
        });

    }


    //设置底部导航的名字,不能超过五个
    public void setBottombarName(String... msg) {
        for (int i = 0; i < msg.length; i++) {
            radioButtons[i].setText(msg[i]);
        }
    }

    //设置tap的数量 不能超过5个
    public void setTapNum(int num) {
        if (num > 5) {
            Toast.makeText(mContext, "不能超过四个", Toast.LENGTH_SHORT).show();
        }
        for (int i = 0; i < radioButtons.length; i++) {
            if (i >= num) {
                radioButtons[i].setVisibility(View.GONE);
            }
        }
    }

    //设置每个tap的文本
    public void setTapText(String... titles) {
        for (int i = 0; i < titles.length; i++) {
            radioButtons[i].setText(titles[i]);
        }
    }

    //设置每个tap的图标
    public void setTapDrables(@Nullable Drawable... resId) {
        for (int i = 0; i < resId.length; i++) {
            radioButtons[i].setCompoundDrawablesWithIntrinsicBounds(null, resId[i], null, null);
        }
    }


    private OnTapListener onTapListener;

    public void setOnTapListener(OnTapListener onTapListener) {
        this.onTapListener = onTapListener;
    }

    public interface OnTapListener {
        //监听点击下标
        void tapItemClick(int i);
    }

    public void setCheckedItem(int position) {
        radioButtons[position].setChecked(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
