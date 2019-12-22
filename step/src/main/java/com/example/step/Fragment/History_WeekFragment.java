package com.example.step.Fragment;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.framework.base.BaseFragment;
import com.example.framework.bean.ShopStepBean;
import com.example.framework.manager.StepManager;
import com.example.step.Adapter.StepHistoryAdapter;
import com.example.step.R;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;

public class History_WeekFragment extends BaseFragment {
    ColumnChartView columnChartView;

    ArrayList<String> listX=new ArrayList<>();
    ArrayList<Integer> listY=new ArrayList<>();

    @Override
    protected void initData() {
        List<ShopStepBean> real = StepManager.getInstance().getStepHistory();
        for (int w=0;w<real.size();w++){
            String date = real.get(w).getDate();
            String current_step = real.get(w).getCurrent_step();
            List<String> weekDay = StepManager.getInstance().getWeekDay();
            for (int i=0;i<weekDay.size();i++){
                if(date.equals(weekDay.get(i))){

                    listX.add(real.get(w).getDate());
                    listY.add(Integer.parseInt(real.get(w).getCurrent_step()));
                    initColumnChart();
                }

            }

        }
    }

    private void initColumnChart() {
        columnChartView.setZoomEnabled(true);
        columnChartView.setInteractive(true);
        columnChartView.setZoomType(ZoomType.HORIZONTAL);


        Axis axisX = new Axis();
        axisX.setTextSize(12);
        axisX.setTypeface(Typeface.DEFAULT);


        ArrayList<AxisValue> axisValues = new ArrayList<>();
        for (int i=0;i<listX.size();i++){
            axisValues.add(new AxisValue(i).setLabel(listX.get(i)));
        }
        axisX.setValues(axisValues);

        ArrayList<Column> columns = new ArrayList<>();
        ArrayList<SubcolumnValue>subList;
        for (int i=0;i<listY.size();i++){
            subList=new ArrayList<>();
            subList.add(new SubcolumnValue(listY.get(i),ChartUtils.pickColor()));
            Column column = new Column(subList);
            column.setHasLabels(true);
            column.setHasLabelsOnlyForSelected(true);
            columns.add(column);
        }
        ColumnChartData data = new ColumnChartData(columns);
        data.setAxisXBottom(axisX);
        columnChartView.setColumnChartData(data);


    }


    @Override
    protected void initView(View view) {

        columnChartView=view.findViewById(R.id.history_columnWeek);
    }

    @Override
    protected int setLayout() {
        return R.layout.histroy_weekfragment;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }
}
