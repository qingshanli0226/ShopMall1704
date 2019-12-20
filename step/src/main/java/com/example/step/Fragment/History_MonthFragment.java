package com.example.step.Fragment;

import android.graphics.Color;
import android.util.Log;
import android.view.View;

import com.example.framework.base.BaseFragment;
import com.example.framework.bean.ShopStepBean;
import com.example.framework.manager.StepManager;
import com.example.step.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
                    mAxisValues.add(new AxisValue(m).setLabel(shopStepTimeRealBeans.get(m).getDate()));
                    mPointValues.add(new PointValue(m,500));
                    initLineChat();
                    Log.e("Month",shopStepTimeRealBeans.get(m).getDate()+"--"+shopStepTimeRealBeans.get(m).getCurrent_step());
                }
            }

        }
    }

    private void initLineChat() {

        Line line = new Line(mPointValues).setColor(ChartUtils.pickColor());
        List<Line> lines=new ArrayList<>();
        line.setShape(ValueShape.CIRCLE);
        line.setCubic(true);
        line.setFilled(false);
        line.setHasLabels(true);
        line.setHasLines(true);
        line.setHasPoints(true);
        lines.add(line);
        LineChartData lineChartData = new LineChartData();
        lines.add(line);
        lineChartData.setLines(lines);

        Axis axisX = new Axis();
//        Axis axisY = new Axis();
        axisX.setHasTiltedLabels(true);
        axisX.setTextColor(Color.GRAY);

        axisX.setName("一月以内步数,(须有两天以上记录)");
        axisX.setTextSize(11);
        axisX.setMaxLabelChars(7);
        axisX.setValues(mAxisValues);
        axisX.setHasLines(true);
        lineChartData.setAxisXBottom(axisX);

//        axisY.setName("");
//        axisY.setTextSize(11);
//        lineChartData.setAxisYLeft(axisY);

        monthLineChat.setInteractive(true);
        monthLineChat.setZoomType(ZoomType.HORIZONTAL);
        monthLineChat.setMaxZoom((float)3);
        monthLineChat.setLineChartData(lineChartData);
        monthLineChat.setVisibility(View.VISIBLE);

        Viewport v = new Viewport(monthLineChat.getMaximumViewport());
        v.left=0;
        v.right=7;
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
}
