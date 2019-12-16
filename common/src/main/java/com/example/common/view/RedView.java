package com.example.common.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class RedView extends View {

    //红色圆点
    private Paint paintRed;
    //白色边
    private Paint paintWhite;

    public RedView(Context context) {
        super(context);
    }

    public RedView(Context context, @Nullable AttributeSet attrs) {
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

        paintWhite = new Paint();
        paintWhite.setAntiAlias(true);
        paintWhite.setDither(true);
        paintWhite.setStyle(Paint.Style.FILL_AND_STROKE);
        paintWhite.setColor(Color.WHITE);
        paintWhite.setStrokeWidth(10);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //外白边圆
        canvas.drawCircle(55,30,18,paintWhite);

        //内红色圆
        canvas.drawCircle(55,30,15,paintRed);

    }
}
