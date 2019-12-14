package com.example.common.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class RedView extends View {

    private Paint paintRed;

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
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(15,15,10,paintRed);

    }
}
