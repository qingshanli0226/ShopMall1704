package com.example.point.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class StepView extends View {
    //圆弧的宽度
    private float borderWidth=dipToPx(14);
    //步数的数值大小
    private float numberTextSize=0;
    //步数
    private String stepNumber = "10000";
    //圆弧的角度
    private float startAngle=135;
    /**
     * 终点对应的角度和起始点对应的角度的夹角
     */
    private float angleLength = 270;
    /**
     * 所要绘制的当前步数的红色圆弧终点到起点的夹角
     */
    private float currentAngleLength = 0;
    /**
     * 动画时长
     */
    private int animationLength = 3000;

    public StepView(Context context) {
        super(context,null);
    }

    public StepView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs,0);
    }

    public StepView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int cententX = getWidth() / 2;

        //黄色弧形
        RectF rectF = new RectF(0 + borderWidth, borderWidth, 2 * cententX - borderWidth, 2 * cententX - borderWidth);
        Paint paint = new Paint();
        /** 默认画笔颜色，黄色 */
        paint.setColor(Color.rgb(253,156,0));
        /** 结合处为圆弧*/
        paint.setStrokeJoin(Paint.Join.ROUND);
        /** 设置画笔的样式 Paint.Cap.Round ,Cap.SQUARE等分别为圆形、方形*/
        paint.setStrokeCap(Paint.Cap.ROUND);
        /** 设置画笔的填充样式 Paint.Style.FILL  :填充内部;Paint.Style.FILL_AND_STROKE  ：填充内部和描边;  Paint.Style.STROKE  ：仅描边*/
        paint.setStyle(Paint.Style.STROKE);
        /**抗锯齿功能*/
        paint.setAntiAlias(true);
        /**设置画笔宽度*/
        paint.setStrokeWidth(borderWidth);
        canvas.drawArc(rectF, startAngle, angleLength, false, paint);

        //步数
        Paint vTextPaint = new Paint();
        vTextPaint.setTextSize(dipToPx(16));
        vTextPaint.setTextAlign(Paint.Align.CENTER);
        vTextPaint.setAntiAlias(true);//抗锯齿功能
        vTextPaint.setColor(Color.GRAY);
        String stepString = "今日步数";
        Rect bounds = new Rect();
        vTextPaint.getTextBounds(stepString, 0, stepString.length(), bounds);
        canvas.drawText(stepString, cententX, getHeight() / 2 + bounds.height() + getFontHeight(numberTextSize), vTextPaint);

        //已走步数
        Paint bTextPaint = new Paint();
        bTextPaint.setTextAlign(Paint.Align.CENTER);
        bTextPaint.setAntiAlias(true);//抗锯齿功能
        bTextPaint.setTextSize(numberTextSize);
        bTextPaint.setColor(Color.RED);
        Rect bounds_Number = new Rect();
        bTextPaint.getTextBounds(stepNumber, 0, stepNumber.length(), bounds_Number);
        canvas.drawText(stepNumber, cententX, getHeight() / 2 + bounds_Number.height() / 2, bTextPaint);

       //红色进度
        Paint paintCurrent = new Paint();
        paintCurrent.setStrokeJoin(Paint.Join.ROUND);
        paintCurrent.setStrokeCap(Paint.Cap.ROUND);//圆角弧度
        paintCurrent.setStyle(Paint.Style.STROKE);//设置填充样式
        paintCurrent.setAntiAlias(true);//抗锯齿功能
        paintCurrent.setStrokeWidth(borderWidth);//设置画笔宽度
        paintCurrent.setColor(Color.RED);//设置画笔颜色
        canvas.drawArc(rectF, startAngle, currentAngleLength, false, paintCurrent);
    }
    private int dipToPx(float dip) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5f * (dip >= 0 ? 1 : -1));
    }

    /**
     * 所走的步数进度
     *
     * @param totalStepNum  设置的步数
     * @param currentCounts 所走步数
     */
    public void setCurrentCount(int totalStepNum, int currentCounts) {
        /**如果当前走的步数超过总步数则圆弧还是270度，不能成为园*/
        if (currentCounts > totalStepNum) {
            currentCounts = totalStepNum;
        }

        /**上次所走步数占用总共步数的百分比*/
        float scalePrevious = (float) Integer.valueOf(stepNumber) / totalStepNum;
        /**换算成弧度最后要到达的角度的长度-->弧长*/
        float previousAngleLength = scalePrevious * angleLength;
        /**所走步数占用总共步数的百分比*/
        float scale = (float) currentCounts / totalStepNum;
        /**换算成弧度最后要到达的角度的长度-->弧长*/
        currentAngleLength = scale * angleLength;

       stepNumber = String.valueOf(currentCounts);
       postInvalidate();
        setTextSize(currentCounts);
    }
    /**
     * 设置文本大小,防止步数特别大之后放不下，将字体大小动态设置
     *
     * @param num
     */
    public void setTextSize(int num) {
        String s = String.valueOf(num);
        int length = s.length();
        if (length <= 4) {
            numberTextSize = dipToPx(50);
        } else if (length > 4 && length <= 6) {
            numberTextSize = dipToPx(40);
        } else if (length > 6 && length <= 8) {
            numberTextSize = dipToPx(30);
        } else if (length > 8) {
            numberTextSize = dipToPx(25);
        }
    }
    /**
     * 获取当前步数的数字的高度
     *
     * @param fontSize 字体大小
     * @return 字体高度
     */
    public int getFontHeight(float fontSize) {
        Paint paint = new Paint();
        paint.setTextSize(fontSize);
        Rect bounds_Number = new Rect();
        paint.getTextBounds(stepNumber, 0, stepNumber.length(), bounds_Number);
        return bounds_Number.height();
    }


}
