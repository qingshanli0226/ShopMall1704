package com.example.administrator.shaomall.login.diyview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class Headportrait extends View {
    Paint paint,paint2;
    public Headportrait(Context context) {
        super(context);
        init();
    }

    private void init() {
        paint=new Paint();
        paint.setColor(Color.parseColor("#00ced1"));
        paint.setStrokeWidth(3);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint2=new Paint();
        paint2.setColor(Color.parseColor("#00ced1"));
        paint2.setAntiAlias(true);
        paint2.setTextSize(100);
        paint2.setStrokeWidth(4);

    }

    public Headportrait(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Headportrait(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectF rectF=new RectF();
        rectF.left=0+10;
        rectF.right=getWidth()-10;
        rectF.top=0+10;
        rectF.bottom=getHeight()-10;
        canvas.drawOval(rectF,paint);

        canvas.drawText("+",getWidth()/2-26,getHeight()/2+34,paint2);

        
    }
}
