package com.example.point.activity;
import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.common.code.Constant;
import com.example.common.view.MyToolBar;
import com.example.framework.base.BaseNetConnectActivity;
import com.example.framework.bean.PointBean;
import com.example.framework.manager.DaoManager;
import com.example.point.R;
import com.example.point.adpter.ConversionAdpter;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConversionActivity extends BaseNetConnectActivity {
    private MyToolBar conversion_toolbar;
    private RecyclerView conversion_re;
    private TextView conversion_tv;
    private ConversionAdpter conversionAdpter;
    private  List<PointBean> pointBeans;
    @Override
    public int getLayoutId() {
        return R.layout.conversion_activity;
    }

    @Override
    public void init() {
        super.init();
        conversion_toolbar=findViewById(R.id.conversion_toolbar);
        conversion_re=findViewById(R.id.conversion_re);
        conversion_tv=findViewById(R.id.conversion_tv);
        conversion_toolbar.init(Constant.OTHER_STYLE);
        conversion_toolbar.getOther_title().setText("兑换记录");
        conversion_toolbar.setBackground(getResources().getDrawable(R.drawable.toolbar_style));
        //返回
        conversion_toolbar.getOther_back().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishActivity();
            }
        });
        conversion_re.setLayoutManager(new LinearLayoutManager(this));
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(new Runnable() {
            @Override
            public void run() {
                pointBeans = DaoManager.Companion.getInstance(ConversionActivity.this).loadPointBean();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (pointBeans.size()==0){
                            conversion_tv.setVisibility(View.VISIBLE);
                            conversion_re.setVisibility(View.GONE);
                        }else {
                            conversion_tv.setVisibility(View.GONE);
                            conversion_re.setVisibility(View.VISIBLE);
                            conversionAdpter=new ConversionAdpter(R.layout.conversion_item,pointBeans);
                            conversion_re.setAdapter(conversionAdpter);
                        }
                    }
                });
            }
        });
    }

    @Override
    public void initDate() {
        super.initDate();
    }
}
