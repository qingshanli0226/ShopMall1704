package com.example.common;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * 购物车数量圆点
 */
public class ShoppingCartView extends View {

    //购物车显示数量圆点
    private Paint paintRed;
    //购物车显示数量数字
    private Paint paintText;
    private int mWidth;
    private int mHeight;
    private String num = 10 + "";

    public ShoppingCartView(Context context) {
        this(context, null);
    }

    public ShoppingCartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    private void initPaint() {
        paintRed = new Paint();
        paintRed.setAntiAlias(true);
        paintRed.setDither(true);
        paintRed.setStyle(Paint.Style.FILL);
        paintRed.setColor(Color.RED);
        paintRed.setStrokeWidth(10);

        paintText = new Paint();
        paintText.setAntiAlias(true);
        paintText.setDither(true);
        paintText.setStyle(Paint.Style.FILL);
        paintText.setColor(Color.WHITE);
        paintText.setStrokeWidth(10);
        paintText.setTextSize(20);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(20, 20, 20, paintRed);
        canvas.drawText(num,20-paintText.measureText(num)/2,25,paintText);

    }

    public void setNum(int num) {

    }
}