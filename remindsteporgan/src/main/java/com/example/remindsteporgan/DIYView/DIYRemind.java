package com.example.remindsteporgan.DIYView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class DIYRemind extends View {
    Paint paint;
    String step="单邵华";
    public DIYRemind(Context context) {
        super(context);
        ssh();
    }

    private void ssh() {
        paint=new Paint();
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(20);
        paint.setStyle(Paint.Style.STROKE);

    }

    public DIYRemind(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        ssh();
    }

    public DIYRemind(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectF rectF=new RectF();
        rectF.left=0;
        rectF.right=getWidth();
        rectF.top=0;
        rectF.bottom=getHeight();
        canvas.drawOval(rectF,paint);

        Rect bounds=new Rect();


        paint.getTextBounds(step,0,step.length(),bounds);
        float offSet=(bounds.top+bounds.bottom)/2;

        //文字
        paint.setTextSize(50);
        paint.setStrokeWidth(10);
        paint.setColor(Color.BLACK);
        //Paint设置水平居中
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(step,getWidth()/2,getHeight()/2-offSet,paint);
    }
}
