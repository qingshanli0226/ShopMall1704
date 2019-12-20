package com.example.step.Fragment;

import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.example.framework.base.BaseFragment;
import com.example.framework.bean.ShopStepBean;
import com.example.framework.manager.StepManager;
import com.example.step.Adapter.StepHistoryAdapter;
import com.example.step.R;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;

public class History_WeekFragment extends BaseFragment {
    ColumnChartView columnChartView;
    List<AxisValue> axisValues=new ArrayList<>();

    @Override
    protected void initData() {
        List<ShopStepBean> real = StepManager.getInstance().getStepHistory();
        axisValues.clear();
        for (int w=0;w<real.size();w++){
            String date = real.get(w).getDate();
            List<String> weekDay = StepManager.getInstance().getWeekDay();
            for (int i=0;i<weekDay.size();i++){
                if(date.equals(weekDay.get(i))){
                    axisValues.add(new AxisValue(w).setLabel(real.get(w).getDate()));
                    setColumnWeek(1,axisValues.size(),real.get(w).getCurrent_step());
                }

            }

        }
    }

    private void setColumnWeek(int subColoumn, int column, String current_step) {
        List<Column> columnList=new ArrayList<>();
        List<SubcolumnValue> values;
        for (int i=0;i<column;i++){
            values=new ArrayList<>();
            for (int j=0;j<subColoumn;j++){
                int parseInt = Integer.parseInt(current_step);
                values.add(new SubcolumnValue(parseInt,ChartUtils.pickColor()));
            }
            Column column1 = new Column(values);
            column1.setHasLabels(true);
            columnList.add(column1);
        }
        ColumnChartData columnChartData = new ColumnChartData(columnList);
        columnChartData.setStacked(false);
        Axis axisX = new Axis();
        axisX.setName("一周步数");
        axisX.setValues(axisValues);
        Axis axisY = new Axis();
        axisY.setName("");

        columnChartData.setAxisXBottom(axisX);
//        columnChartData.setAxisYLeft(axisY);

        columnChartData.setFillRatio(0.2f);
        columnChartView.setColumnChartData(columnChartData);


    }

    @Override
    protected void initView(View view) {

        columnChartView=view.findViewById(R.id.history_columnWeek);
    }

    @Override
    protected int setLayout() {
        return R.layout.histroy_weekfragment;
    }
}
