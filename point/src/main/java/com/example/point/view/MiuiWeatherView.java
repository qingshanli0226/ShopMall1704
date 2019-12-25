package com.example.point.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Scroller;

import com.example.framework.bean.StepBean;
import com.example.point.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ccy on 2017-07-28.
 */

public class MiuiWeatherView extends View {

    private static int DEFAULT_BULE = 0XFF00BFFF;
    private static int DEFAULT_GRAY = Color.GRAY;

    private int backgroundColor;
    private int minViewHeight; //控件的最低高度
    private int minPointHeight;//折线最低点的高度
    private int lineInterval; //折线线段长度
    private float pointRadius; //折线点的半径
    private float textSize; //字体大小
    private float pointGap; //折线单位高度差
    private int defaultPadding; //折线坐标图四周留出来的偏移量
    private int viewHeight;
    private int viewWidth;
    private int screenWidth;
    private int screenHeight;

    private Paint linePaint; //线画笔
    private Paint textPaint; //文字画笔
    private Paint circlePaint; //圆点画笔

    private List<StepBean> data = new ArrayList<>(); //元数据
    private List<Pair<Integer, String>> weatherDatas = new ArrayList<>();  //对元数据中天气分组后的集合
    private List<Float> dashDatas = new ArrayList<>(); //不同天气之间虚线的x坐标集合
    private List<PointF> points = new ArrayList<>(); //折线拐点的集合
    private int maxTemperature;//元数据中的最高和最低温度
    private int minTemperature;

    private VelocityTracker velocityTracker;
    private Scroller scroller;
    private ViewConfiguration viewConfiguration;


    public MiuiWeatherView(Context context) {
        this(context, null);
    }

    public MiuiWeatherView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MiuiWeatherView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        scroller = new Scroller(context);
        viewConfiguration = ViewConfiguration.get(context);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MiuiWeatherView);
        minPointHeight = (int) ta.getDimension(R.styleable.MiuiWeatherView_min_point_height, dp2pxF(context, 60));
        lineInterval = (int) ta.getDimension(R.styleable.MiuiWeatherView_line_interval, dp2pxF(context, 60));
        backgroundColor = ta.getColor(R.styleable.MiuiWeatherView_background_color, Color.WHITE);
        ta.recycle();

        setBackgroundColor(backgroundColor);

        initSize(context);

        initPaint(context);
    }

    /**
     * 初始化默认数据
     */
    private void initSize(Context c) {
        screenWidth = getResources().getDisplayMetrics().widthPixels;
        screenHeight = getResources().getDisplayMetrics().heightPixels;

        minViewHeight = 3 * minPointHeight;  //默认3倍
        pointRadius = dp2pxF(c, 2.5f);
        textSize = sp2pxF(c, 10);
        defaultPadding = (int) (0.5 * minPointHeight);  //默认0.5倍
    }

    /**
     * 计算折线单位高度差
     */
    private void calculatePontGap() {
        int lastMaxTem = -Integer.MAX_VALUE;
        int lastMinTem = Integer.MAX_VALUE;
        for (StepBean bean : data) {
            if (bean.getStep() > lastMaxTem) {
                maxTemperature = bean.getStep();
                lastMaxTem = bean.getStep();
            }
            if (bean.getStep() < lastMinTem) {
                minTemperature = bean.getStep();
                lastMinTem = bean.getStep();
            }
        }
        float gap = (maxTemperature - minTemperature) * 1.0f;
        gap = (gap == 0.0f ? 1.0f : gap);  //保证分母不为0
        pointGap = (viewHeight - minPointHeight - 2 * defaultPadding) / gap;
    }

    private void initPaint(Context c) {
        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setStrokeWidth(dp2px(c, 1));

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(textSize);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextAlign(Paint.Align.CENTER);

        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setStrokeWidth(dp2pxF(c, 1));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        initSize(getContext());
        calculatePontGap();
    }

    /**
     * 公开方法，用于设置元数据
     *
     * @param data
     */
    public void setData(List<StepBean> data) {
        if (data == null) {
            return;
        }
        this.data = data;
        notifyDataSetChanged();
    }

    public List<StepBean> getData(){
        return data;
    }

    public void notifyDataSetChanged(){
        if(data == null){
            return;
        }
        weatherDatas.clear();
        points.clear();
        dashDatas.clear();

        initWeatherMap(); //初始化相邻的相同天气分组
        requestLayout();
        invalidate();
    }


    /**
     * 根据元数据中连续相同的天气数做分组,
     * pair中的first值为连续相同天气的数量，second值为对应天气
     */
    private void initWeatherMap() {
        weatherDatas.clear();
        String lastWeather = "";
        int count = 0;
        for (int i = 0; i < data.size(); i++) {
            StepBean bean = data.get(i);
            if (i == 0) {
                lastWeather = bean.getStep()+"";
            }
            if (bean.getStep()+"" != lastWeather) {
                Pair<Integer, String> pair = new Pair<>(count, lastWeather);
                weatherDatas.add(pair);
                count = 1;
            } else {
                count++;
            }
            lastWeather = bean.getStep()+"";

            if (i == data.size() - 1) {
                Pair<Integer, String> pair = new Pair<>(count, lastWeather);
                weatherDatas.add(pair);
            }
        }

        for (int i = 0; i < weatherDatas.size(); i++) {
            int c = weatherDatas.get(i).first;
            String w = weatherDatas.get(i).second;
            Log.d("ccy", "weatherMap i =" + i + ";count = " + c + ";weather = " + w);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (heightMode == MeasureSpec.EXACTLY) {
            viewHeight = Math.max(heightSize, minViewHeight);
        } else {
            viewHeight = minViewHeight;
        }

        int totalWidth = 0;
        if (data.size() > 1) {
            totalWidth = 2 * defaultPadding + lineInterval * (data.size() - 1);
        }
        viewWidth = Math.max(screenWidth, totalWidth);  //默认控件最小宽度为屏幕宽度

        setMeasuredDimension(viewWidth, viewHeight);
        calculatePontGap();
        Log.d("ccy", "viewHeight = " + viewHeight + ";viewWidth = " + viewWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (data.isEmpty()) {
            return;
        }
        drawAxis(canvas);

        drawLinesAndPoints(canvas);

        drawTemperature(canvas);

        drawWeatherDash(canvas);
    }

    /**
     * 画时间轴
     *
     * @param canvas
     */
    private void drawAxis(Canvas canvas) {
        canvas.save();
        linePaint.setColor(DEFAULT_GRAY);
        linePaint.setStrokeWidth(dp2px(getContext(), 1));

        canvas.drawLine(defaultPadding,
                viewHeight - defaultPadding,
                viewWidth - defaultPadding,
                viewHeight - defaultPadding,
                linePaint);

        float centerY = viewHeight - defaultPadding + dp2pxF(getContext(), 15);
        float centerX;
        for (int i = 0; i < data.size(); i++) {
            String text = data.get(i).getCurr_date();
            centerX = defaultPadding + i * lineInterval;
            Paint.FontMetrics m = textPaint.getFontMetrics();
            canvas.drawText(text, 0, text.length(), centerX, centerY - (m.ascent + m.descent) / 2, textPaint);
        }
        canvas.restore();
    }

    /**
     * 画折线和它拐点的园
     *
     * @param canvas
     */
    private void drawLinesAndPoints(Canvas canvas) {
        canvas.save();
        linePaint.setColor(DEFAULT_BULE);
        linePaint.setStrokeWidth(dp2pxF(getContext(), 1));
        linePaint.setStyle(Paint.Style.STROKE);

        Path linePath = new Path(); //用于绘制折线
        points.clear();
        int baseHeight = defaultPadding + minPointHeight;
        float centerX;
        float centerY;
        for (int i = 0; i < data.size(); i++) {
            int tem = data.get(i).getStep();
            tem = tem - minTemperature;
            centerY = (int) (viewHeight - (baseHeight + tem * pointGap));
            centerX = defaultPadding + i * lineInterval;
            points.add(new PointF(centerX, centerY));
            if (i == 0) {
                linePath.moveTo(centerX, centerY);
            } else {
                linePath.lineTo(centerX, centerY);
            }
        }
        canvas.drawPath(linePath, linePaint); //画出折线

        //接下来画折线拐点的园
        float x, y;
        for (int i = 0; i < points.size(); i++) {
            x = points.get(i).x;
            y = points.get(i).y;

            //先画一个颜色为背景颜色的实心园覆盖掉折线拐角
            circlePaint.setStyle(Paint.Style.FILL_AND_STROKE);
            circlePaint.setColor(backgroundColor);
            canvas.drawCircle(x, y,
                    pointRadius + dp2pxF(getContext(), 1),
                    circlePaint);
            //再画出正常的空心园
            circlePaint.setStyle(Paint.Style.STROKE);
            circlePaint.setColor(DEFAULT_BULE);
            canvas.drawCircle(x, y,
                    pointRadius,
                    circlePaint);
        }
        canvas.restore();
    }

    /**
     * 画温度描述值
     *
     * @param canvas
     */
    private void drawTemperature(Canvas canvas) {
        canvas.save();

        textPaint.setTextSize(1.2f * textSize); //字体放大一丢丢
        float centerX;
        float centerY;
        String text;
        for (int i = 0; i < points.size(); i++) {
            text = data.get(i).getStep()+"步";
            centerX = points.get(i).x;
            centerY = points.get(i).y - dp2pxF(getContext(), 13);
            Paint.FontMetrics metrics = textPaint.getFontMetrics();
            canvas.drawText(text,
                    centerX,
                    centerY - (metrics.ascent + metrics.descent)/2,
                    textPaint);
        }
        textPaint.setTextSize(textSize);
        canvas.restore();
    }

    /**
     * 画不同天气之间的虚线
     *
     * @param canvas
     */
    private void drawWeatherDash(Canvas canvas) {
        canvas.save();
        linePaint.setColor(DEFAULT_GRAY);
        linePaint.setStrokeWidth(dp2pxF(getContext(), 0.5f));
        linePaint.setAlpha(0xcc);

        //设置画笔画出虚线
        float[] f = {dp2pxF(getContext(), 5), dp2pxF(getContext(), 1)};  //两个值分别为循环的实线长度、空白长度
        PathEffect pathEffect = new DashPathEffect(f, 0);
        linePaint.setPathEffect(pathEffect);

        dashDatas.clear();
        int interval = 0;
        float startX, startY, endX, endY;
        endY = viewHeight - defaultPadding;

        //0坐标点的虚线手动画上
        canvas.drawLine(defaultPadding,
                points.get(0).y + pointRadius + dp2pxF(getContext(), 2),
                defaultPadding,
                endY,
                linePaint);
        dashDatas.add((float) defaultPadding);

        for (int i = 0; i < weatherDatas.size(); i++) {
            interval += weatherDatas.get(i).first;
            if(interval > points.size()-1){
                interval = points.size()-1;
            }
            startX = endX = defaultPadding + interval * lineInterval;
            startY = points.get(interval).y + pointRadius + dp2pxF(getContext(), 2);
            dashDatas.add(startX);
            canvas.drawLine(startX, startY, endX, endY, linePaint);
        }

        //这里注意一下，当最后一组的连续天气数为1时，是不需要计入虚线集合的，否则会多画一个天气图标
        //若不理解，可尝试去掉下面这块代码并观察运行效果
        if(weatherDatas.get(weatherDatas.size()-1).first == 1
                && dashDatas.size() > 1){
            dashDatas.remove(dashDatas.get(dashDatas.size()-1));
        }

        linePaint.setPathEffect(null);
        linePaint.setAlpha(0xff);
        canvas.restore();
    }


    private float lastX = 0;
    private float x = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain();
        }
        velocityTracker.addMovement(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!scroller.isFinished()) {  //fling还没结束
                    scroller.abortAnimation();
                }
                lastX = x = event.getX();
                return true;
            case MotionEvent.ACTION_MOVE:
                x = event.getX();
                int deltaX = (int) (lastX - x);
                if (getScrollX() + deltaX < 0) {    //越界恢复
                    scrollTo(0, 0);
                    return true;
                } else if (getScrollX() + deltaX > viewWidth - screenWidth) {
                    scrollTo(viewWidth - screenWidth, 0);
                    return true;
                }
                scrollBy(deltaX, 0);
                lastX = x;
                break;
            case MotionEvent.ACTION_UP:
                x = event.getX();
                velocityTracker.computeCurrentVelocity(1000);  //计算1秒内滑动过多少像素
                int xVelocity = (int) velocityTracker.getXVelocity();
                if (Math.abs(xVelocity) > viewConfiguration.getScaledMinimumFlingVelocity()) {  //滑动速度可被判定为抛动
                    scroller.fling(getScrollX(), 0, -xVelocity, 0, 0, viewWidth - screenWidth, 0, 0);
                    invalidate();
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            invalidate();
        }
    }

    //工具类
    public static int dp2px(Context c, float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, c.getResources().getDisplayMetrics());
    }

    public static int sp2px(Context c, float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, c.getResources().getDisplayMetrics());
    }

    public static float dp2pxF(Context c, float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, c.getResources().getDisplayMetrics());
    }

    public static float sp2pxF(Context c, float sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, c.getResources().getDisplayMetrics());
    }
}
