package com.example.remindsteporgan.DIYView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by huangqj on 2017-08-15.
 */

public class MyPassometView extends View {

    public static final String TAG = MyPassometView.class.getName();
    /**
     * view中心坐标
     */
    private int centerX, centerY;
    /**
     * 刻度圆占300度，一个圆是360度
     */
    private float allDegree = 300f;

    /**
     * 每一个刻度占的度数（sweepDegree/maxPercent）
     */
    private float singleDegree;
    /**
     * 刻度的数量
     */
    private int maxPercent = 10000;

    private int strokeWidth = 5;
    /**
     * 刻度的长度
     */
    private float lineLenght = dip(20);
    /**
     * 背景色画笔
     */
    private Paint normalPaint;
    /**
     * 进度画笔
     */
    private Paint progressPaint;
    /**
     * 文字画笔
     */
    private Paint textPaint;
    /**
     * 进度圆点画笔
     */
    private Paint progressPoint;

    /**
     * 要设置的文字
     */
    private int countText = 0;


    public MyPassometView(Context context) {
        super(context);
        init();
    }

    public MyPassometView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyPassometView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        singleDegree = allDegree / maxPercent;
        getNormaPaint();
        getTextPaint();
        getProgressPaint();
        getProgressPoint();
    }

    private void getNormaPaint() {
        normalPaint = getPaint();
        normalPaint.setColor(Color.GRAY);
        normalPaint.setStrokeWidth(10);
    }

    private void getProgressPaint() {
        progressPaint = getPaint();
        progressPaint.setColor(getResources().getColor(android.R.color.holo_blue_bright));
        progressPaint.setStrokeWidth(10);
    }

    private void getProgressPoint() {
        progressPoint = new Paint();
        progressPoint.setColor(Color.GREEN);
        progressPoint.setAntiAlias(true);
        progressPoint.setStrokeWidth(10);
        progressPoint.setStyle(Paint.Style.FILL);
    }

    private void getTextPaint() {
        textPaint = new TextPaint();
        textPaint.setColor(Color.GREEN);
        textPaint.setStrokeWidth(3);
        textPaint.setTextSize(sp(28));
        textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    private Paint getPaint() {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);
        paint.setColor(Color.GRAY);
        paint.setAntiAlias(true);//设置抗锯齿
        return paint;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawNormal(canvas);
        drawProgressBar(canvas);//drawProgressBar和drawProgressPoint一起使用
        drawProgressPoint(canvas);
        drawText(canvas);
    }

    private void drawNormal(Canvas canvas) {
        float paddingLeft = dip(10);
        float cx = dip(280) / 2;
        Log.e(TAG, "cx:" + cx);
        canvas.save();
        canvas.rotate(-60, centerX, centerY);
        for (int i = 0; i < maxPercent; i++) {
            canvas.drawLine(strokeWidth + paddingLeft, centerY, lineLenght + paddingLeft, centerY, normalPaint);
            canvas.rotate(singleDegree, centerX, centerY);
        }
        canvas.restore();//操作完之后（比如选中，平移）取出之前的状态，界面上才看到的是正常的状态
    }

    private void drawProgressBar(Canvas canvas) {
        float paddingLeft = dip(10);
        canvas.save();//操作之前保存当前的状态
        canvas.rotate(-60, centerX, centerY);
        Log.e(TAG, "drawProgressBar:" + countText);
        for (int i = 0; i < countText; i++) {
            canvas.drawLine(strokeWidth + paddingLeft, centerY, lineLenght + paddingLeft, centerY, progressPaint);
            canvas.rotate(singleDegree, centerX, centerY);
        }
        // canvas.restore();//这里不取出之前保存的状态，等drawProgressPoint的时候在取出
    }

    private void drawProgressPoint(Canvas canvas) {
        float paddingLeft = dip(10);
        float ballRadius = dip(8);
        float pointDegree = singleDegree * countText;
        canvas.drawCircle(
                lineLenght + paddingLeft + dip(10) + ballRadius, centerY + ballRadius, ballRadius, progressPoint);
        canvas.rotate(pointDegree, centerX, centerY);
        canvas.restore();//以drawProgressBar的画布状态接着继续操作
    }

    private void drawText(Canvas canvas) {
        String text = getText();
        canvas.drawText(getText(), centerX - textPaint.measureText(text) / 2, centerY, textPaint);
    }


    public void setText(int text) {
        this.countText = text;
        invalidate();
    }

    private String getText() {
        StringBuffer sb = new StringBuffer();
        return sb.append(countText).append("步").toString();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = centerY = w / 2;//相对于当前的布局，设置中心坐标点
        Log.e(TAG, "centerX=centerY:" + centerX);
        Log.e(TAG, "w:" + w);
        Log.e(TAG, "h:" + h);
    }

    private float dip(int dip) {
        return TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, getContext().getResources().getDisplayMetrics());
    }

    private float sp(int sp) {
        return TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getContext().getResources().getDisplayMetrics());
    }
}
