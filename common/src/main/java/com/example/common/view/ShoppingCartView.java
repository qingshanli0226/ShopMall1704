package com.example.common.view;

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

    public ShoppingCartView(Context context) {
        super(context);
    }

    public ShoppingCartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initPaint();

    }

    private void initPaint() {
        paintRed = new Paint();
        paintRed.setAntiAlias(true);
        paintRed.setDither(true);
        paintRed.setStyle(Paint.Style.FILL_AND_STROKE);
        paintRed.setColor(Color.RED);
        paintRed.setStrokeWidth(10);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(40,40,10,paintRed);

    }
}
