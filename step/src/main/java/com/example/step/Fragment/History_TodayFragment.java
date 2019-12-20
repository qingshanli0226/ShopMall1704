package com.example.step.Fragment;

import android.util.Log;
import android.view.View;

import com.example.framework.base.BaseFragment;
import com.example.framework.bean.ShopStepBean;
import com.example.framework.manager.StepManager;
import com.example.step.Adapter.StepHistoryAdapter;
import com.example.step.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;

public class History_TodayFragment extends BaseFragment {
    ColumnChartView columnChartView;


    List<String> mlist=new ArrayList<>();
    List<AxisValue> axisValues=new ArrayList<>();

    int counts=0;
    @Override
    protected void initData() {




        List<ShopStepBean> stepHistory = StepManager.getInstance().getStepHistory();
        List<ShopStepBean> todayList=new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DATE);
        for (int i=0;i<stepHistory.size();i++){
            String date = stepHistory.get(i).getDate();
            String[] split = date.split("-");

            if(Integer.parseInt(split[1])==month){

                if(Integer.parseInt(split[2])==day){

                    axisValues.add(new AxisValue(0).setLabel(stepHistory.get(i).getCurrent_step()));
                    Log.e("History",stepHistory.get(i).getCurrent_step());
                    int i1 = Integer.parseInt(stepHistory.get(i).getCurrent_step());
                    setColumnData(1,i1,false,false);
                }
            }
        }





    }

    private void setColumnData(int subColumn, int column, boolean b, boolean isNegative) {

        List<Column> columnList=new ArrayList<>();

        List<SubcolumnValue>    subcolumnValueList=new ArrayList<>();
                subcolumnValueList.add(new SubcolumnValue(column, ChartUtils.pickColor()));
            Column column1 = new Column(subcolumnValueList);
            column1.setHasLabels(false);
            column1.setHasLabelsOnlyForSelected(true);
            columnList.add(column1);
        ColumnChartData columnChartData = new ColumnChartData(columnList);
        columnChartData.setStacked(false);
        Axis axisX = new Axis();
//        Axis axisY = new Axis().setHasLines(true);
        axisX.setValues(axisValues);
        axisX.setName("今日记录计步");
//        axisY.setName("");
        columnChartData.setAxisXBottom(axisX);
//        columnChartData.setAxisYLeft(axisY);
        columnChartData.setFillRatio(0.2f);

        columnChartView.setColumnChartData(columnChartData);
    }

    private int getNegtiveSign(boolean isNegative) {
        if(isNegative){
            int[] sign=new int[]{-1,1};
            return sign[Math.round((float) Math.random())];
        }
        return 1;
    }

    @Override
    protected void initView(View view) {
        columnChartView=view.findViewById(R.id.history_barCharts);
    }
    @Override
    protected int setLayout() {
        return R.layout.history_todayfragment;
    }
}
