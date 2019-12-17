package com.example.point.message;

import android.content.ComponentName;
import android.content.Intent;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.common.code.Constant;
import com.example.common.view.MyToolBar;
import com.example.framework.base.BaseNetConnectActivity;
import com.example.point.R;
import com.example.point.service.StepBean;
import com.example.point.stepmanager.DaoManager;
import com.example.point.stepmanager.StepPointManager;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends BaseNetConnectActivity {

    private RecyclerView message_re;
    private MyToolBar message_tool;
    private List<MessageBean> messageBeans;
    private MessageAdpter messageAdpter;
    private   List<StepBean> beans;
    private  MessageBean bean;
    @Override
    public void init() {
        super.init();
        message_re = findViewById(R.id.message_re);
        message_tool = (MyToolBar) findViewById(R.id.message_tool);
    }

    @Override
    public void initDate() {
        super.initDate();
        //tool
        message_tool.init(Constant.BUY_MESSAGE_STYLE);
        message_tool.setBackground(getResources().getDrawable(R.drawable.toolbar_style));
        message_re.setLayoutManager(new LinearLayoutManager(this));
        message_tool.getMessage_back().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        message_tool.getMessage_calendar().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.android.calendar", "com.android.calendar.LaunchActivity"));
                startActivity(intent);
            }
        });
        message_tool.getMessage_menu().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MessageActivity.this, "www", Toast.LENGTH_SHORT).show();
            }
        });
        message_tool.getMessage_back().setVisibility(View.VISIBLE);
        message_tool.getMessage_menu().setVisibility(View.VISIBLE);
        message_tool.getMessage_calendar().setVisibility(View.VISIBLE);
        message_tool.getBuy_menu().setVisibility(View.GONE);
        message_tool.getBuy_compile().setVisibility(View.GONE);
        message_tool.getBuy_message_icon().setImageResource(R.mipmap.brush);
        message_tool.getBuy_message_title().setText("消息");

        //消息列表
        messageBeans=new ArrayList<>();
        messageAdpter=new MessageAdpter(R.layout.message_item,messageBeans,this);
        String CURRENT_DATE = DateFormat.format("MM-dd", System.currentTimeMillis())+"";//今日日期
        beans = new DaoManager(this).queryStepBean(CURRENT_DATE);
         bean = new MessageBean(R.mipmap.sport,"次元联盟运动","今天行走了"+beans.get(0).getStep(),beans.get(0).getCurr_date());
        messageBeans.add(bean);
        message_re.setAdapter(messageAdpter);
        //服务按照时间的变动来更新消息步数
        StepPointManager.getInstance(this).addGetStepListener(new StepPointManager.GetStepListener() {
            @Override
            public void onsetStep(int step) {
                bean.setMessage_message("今天行走了"+beans.get(0).getStep());
                messageAdpter.notifyDataSetChanged();
                Log.i("receive", " 时间变动"+step);
            }
        });
    }

    @Override
    public int getRelativeLayout() {
        return super.getRelativeLayout();
    }

    @Override
    public boolean isConnectStatus() {
        return super.isConnectStatus();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_message;
    }
}
