package com.example.point.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.common.code.Constant;
import com.example.common.view.MyToolBar;
import com.example.framework.base.BaseNetConnectActivity;
import com.example.point.R;
import com.example.point.adpter.PreAdpter;
import com.example.point.bean.PresenBean;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

public class PresentActivity extends BaseNetConnectActivity {
    private RecyclerView present_re;
    private List<PresenBean> presenBeans;
    private PreAdpter preAdpter;
    private MyToolBar present_tool;

    @Override
    public void init() {
        super.init();

        present_re = findViewById(R.id.present_re);
        present_tool = (MyToolBar) findViewById(R.id.present_tool);
        present_tool.init(Constant.OTHER_STYLE);
        present_tool.getOther_title().setText("礼品兑换");
        present_tool.setBackground(getResources().getDrawable(R.drawable.toolbar_style));
        //返回积分页
        present_tool.getOther_back().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        present_tool.getOther_back().setImageResource(R.drawable.back3);
        present_re.setLayoutManager(new GridLayoutManager(this, 2));
    }

    @Override
    public void initDate() {
        super.initDate();
        presenBeans = new ArrayList<>();

        presenBeans.add(new PresenBean(R.mipmap.qq, "QQ公仔(1000)"));
        presenBeans.add(new PresenBean(R.mipmap.ch, "尖叫鸡(2000)"));
        presenBeans.add(new PresenBean(R.mipmap.ticker, "母老虎(3000)"));
        presenBeans.add(new PresenBean(R.mipmap.tuzi, "兔子萌宠(4000)"));

        preAdpter = new PreAdpter(presenBeans, this);
        present_re.setAdapter(preAdpter);
        preAdpter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.present_btn) {
                    Toast.makeText(PresentActivity.this, "兑换失败，您的积分不足", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getRelativeLayout() {
        return super.getRelativeLayout();
    }

    @Override
    public void onRequestSuccess(Object data) {
        super.onRequestSuccess(data);
    }

    @Override
    public int getLayoutId() {
        return R.layout.present_activity;
    }

    @Override
    protected void onPause() {
        MobclickAgent.onPause(this);
        super.onPause();
    }

    @Override
    protected void onResume() {
        MobclickAgent.onResume(this);
        super.onResume();
    }

}
