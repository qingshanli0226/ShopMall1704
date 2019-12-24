package com.example.step.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.framework.base.BaseFragment;
import com.example.framework.bean.HourBean;
import com.example.framework.manager.StepManager;
import com.example.step.Bean.DateLine;
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

public class History_MinutesFragment extends BaseFragment  {
    LineChartView lineChartView;


    private List<PointValue> mPointValues = new ArrayList<PointValue>();
    private List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();
    ArrayList<DateLine> list = new ArrayList<>();;
    @Override
    protected void initData() {


        mPointValues.clear();
        mAxisXValues.clear();
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        List<HourBean> real = StepManager.getInstance().findHour();
        for (int i=0;i<real.size();i++) {
            String realTime = real.get(i).getTime();
            String[] split = realTime.split(":");
            //之前小时
            int beforeHour = Integer.parseInt(split[0]);
            int beforeMinute = Integer.parseInt(split[1]);
            //当前小时
            int thrreHour = calendar.get(Calendar.HOUR_OF_DAY) - 3;

            //是否是当天
            if(StepManager.getInstance().isToday(real.get(i).getDate())){

                //如果是在三小时之内显示数据
                if (beforeHour >= thrreHour && beforeHour <= hour) {
                    boolean currentTimeRange = StepManager.getInstance().isCurrentTimeRange(beforeHour, beforeMinute, hour, minute);
                    if (currentTimeRange == true) {


//                        list.add(new DateLine(real.get(i).getTime(),real.get(i).getCurrentStep()));

//                        Log.e("##Time",real.get(i).toString());
                        mAxisXValues.add(new AxisValue(i).setLabel(real.get(i).getTime()));
                        mPointValues.add(new PointValue(i,real.get(i).getCurrentStep()));
                        initLineChart();//初始化



//                        setKlin(list);

                    }
                }

            }

        }




    }

    private void setKlin(ArrayList<DateLine> list) {

        for (int i=0;i<list.size();i++){
            String dateTime = list.get(i).getDateTime();
            mAxisXValues.add(new AxisValue(i).setLabel(dateTime));
        }
        for (int j=0;j<list.size();j++){
            int currents = list.get(j).getCurrents();
            mPointValues.add(new PointValue(j,currents));
        }
        Line chartLine = new Line();
        chartLine.setValues(mPointValues);
        chartLine.setColor(ChartUtils.pickColor());
        chartLine.setShape(ValueShape.CIRCLE);
        chartLine.setPointRadius(2);
        chartLine.setCubic(true);
        chartLine.setFilled(false);
        chartLine.setHasLabels(true);
        chartLine.setStrokeWidth(2);
        List<Line> lines=new ArrayList<>();
        lines.add(chartLine);
        LineChartData lineChartData = new LineChartData();
        lineChartData.setLines(lines);

        Axis axisX = new Axis();
//        Axis axisY = new Axis();

        axisX.setValues(mAxisXValues).setHasLines(true).setTextColor(Color.BLACK).setLineColor(Color.WHITE).setTextSize(12);
//        axisY.setHasLines(true).setTe
        lineChartData.setAxisXBottom(axisX);

        lineChartData.setValueLabelBackgroundColor(Color.TRANSPARENT);
        lineChartData.setValueLabelBackgroundEnabled(false);

        lineChartView.setLineChartData(lineChartData);

        lineChartView.setInteractive(true);
        lineChartView.setZoomEnabled(true);
        lineChartView.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        Viewport viewport = new Viewport(lineChartView.getMaximumViewport());


    }

    private void initLineChart() {
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
        lines.add(line);
        lineChartData.setLines(lines);

        Axis axisX = new Axis();
        Axis axisY = new Axis();
        axisX.setHasTiltedLabels(true);
        axisX.setTextColor(Color.GRAY);

        axisX.setName("三小时以内步数");
        axisX.setTextSize(11);
        axisX.setMaxLabelChars(7);
        axisX.setValues(mAxisXValues);
        axisX.setHasLines(true);
        lineChartData.setAxisXBottom(axisX);



        lineChartView.setInteractive(true);
        lineChartView.setZoomType(ZoomType.HORIZONTAL);
        lineChartView.setMaxZoom((float)3);
        lineChartView.setLineChartData(lineChartData);
        lineChartView.setVisibility(View.VISIBLE);

        Viewport v = new Viewport(lineChartView.getMaximumViewport());
        v.left=0;
        v.right=7;
        lineChartView.setCurrentViewport(v);


    }


    @Override
    protected void initView(View view) {
        lineChartView = view.findViewById(R.id.Minutes_lineChart);
    }

    @Override
    protected int setLayout() {
        return R.layout.fragment_minutes;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAxisXValues.clear();
        mPointValues.clear();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mAxisXValues.clear();
        mPointValues.clear();
    }
}
