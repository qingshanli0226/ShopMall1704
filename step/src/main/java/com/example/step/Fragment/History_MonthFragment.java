package com.example.step.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.framework.base.BaseFragment;
import com.example.framework.bean.ShopStepBean;
import com.example.framework.manager.StepManager;
import com.example.step.MyLineChartRender;
import com.example.step.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;


public class History_MonthFragment extends BaseFragment {

    LineChartView monthLineChat;
    List<PointValue> mPointValues=new ArrayList<>();
    List<AxisValue>mAxisValues=new ArrayList<>();

    List<String> ss=new ArrayList<>();
    float[] dataY={0,1000,3000,5000,6000,7000,10000};
    private float mFirstX;
    private float mLastX;


    @Override
    protected void initData() {

        mPointValues.clear();
        mAxisValues.clear();
        List<ShopStepBean> shopStepTimeRealBeans = StepManager.getInstance().getStepHistory();
        Calendar instance = Calendar.getInstance();
        int NowMonth = instance.get(Calendar.MONTH )+1;
        for (int m=0;m<shopStepTimeRealBeans.size();m++){
            String date = shopStepTimeRealBeans.get(m).getDate();
            String[] split = date.split("-");
            //获取每月的第一天和最后一天
            int firstDayMonth = StepManager.getInstance().getFirstDayMonth(2);
            int lastDayMonth = StepManager.getInstance().getLastDayMonth(2);
            //是本月
            if(NowMonth==Integer.parseInt(split[1])){
                //在本月的日期中
                if(Integer.parseInt(split[2])>=firstDayMonth && Integer.parseInt(split[2])<=lastDayMonth){


                    ss.add(shopStepTimeRealBeans.get(m).getDate());
                    mAxisValues.add(new AxisValue(m).setLabel(shopStepTimeRealBeans.get(m).getDate()));
                    String current_step = shopStepTimeRealBeans.get(m).getCurrent_step();
                    int i = Integer.parseInt(current_step);
                    mPointValues.add(new PointValue(m,i));
                    initLineChat();
                }
            }

        }
    }

    private void initLineChat() {




        Line line = new Line(mPointValues).setColor(ChartUtils.pickColor());
        List<Line> lines=new ArrayList<>();
        line.setShape(ValueShape.CIRCLE);
        line.setCubic(false);
        line.setFilled(false);
        line.setHasLabels(true);
        line.setHasLines(true);
        line.setHasPoints(true);
        lines.add(line);
        LineChartData lineChartData = new LineChartData();
        lineChartData.setLines(lines);

        Axis axisX = new Axis();
        axisX.setTextColor(Color.BLACK);
        axisX.setHasTiltedLabels(true);

        axisX.setName("一月以内步数,(须有两天以上记录)");
        axisX.setTextSize(10);
        axisX.setMaxLabelChars(8);
        axisX.setValues(mAxisValues);
        axisX.setHasLines(true);
        axisX.setHasSeparationLine(false);
        axisX.setLineColor(Color.YELLOW);

        lineChartData.setAxisXBottom(axisX);


        Axis axisY = new Axis();
        axisY.setName("");
        axisY.setTextSize(8);
        axisY.setHasLines(true);
        axisY.setMaxLabelChars(6);
        ArrayList<AxisValue> values = new ArrayList<>();
        for (int i=0;i<dataY.length;i++){
            AxisValue value=new AxisValue(dataY[i]);
            values.add(value);
        }
        axisY.setValues(values);
        lineChartData.setAxisYLeft(axisY);


        monthLineChat.setInteractive(true);
        monthLineChat.setZoomType(ZoomType.HORIZONTAL);
        monthLineChat.setMaxZoom((float)2);
        monthLineChat.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        monthLineChat.setLineChartData(lineChartData);


        monthLineChat.setVisibility(View.VISIBLE);

        final Viewport v = new Viewport(monthLineChat.getMaximumViewport());
        v.left=0;
        v.right=7;
        v.bottom=0;
        monthLineChat.setCurrentViewport(v);






    }

    @Override
    protected void initView(View view) {
        monthLineChat=view.findViewById(R.id.history_monthChart);
    }

    @Override
    protected int setLayout() {
        return R.layout.history_monthfragment;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mAxisValues.clear();
        mPointValues.clear();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mAxisValues.clear();
        mPointValues.clear();
    }
}
