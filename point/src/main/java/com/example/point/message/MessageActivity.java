package com.example.point.message;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.common.code.Constant;
import com.example.common.view.MyToolBar;
import com.example.framework.base.BaseNetConnectActivity;
import com.example.framework.bean.MessageBean;
import com.example.framework.manager.DaoManager;
import com.example.point.R;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MessageActivity extends BaseNetConnectActivity {

    private RecyclerView message_re;

    private MyToolBar message_tool;
    private MessageAdpter messageAdpter;
    private MessageBean bean;
    private List<MessageBean> messageBeans;
    private String CURRENT_DATE;
    @Override
    public int getLayoutId() {
        return R.layout.activity_message;
    }

    @Override
    public void init() {
        super.init();
        message_re = findViewById(R.id.message_re);
        message_tool =  findViewById(R.id.message_tool);
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
                finishActivity();
            }
        });
        message_tool.getMessage_calendar().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MessageActivity.this,"小王正在拼命赶功能",Toast.LENGTH_SHORT).show();
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

         CURRENT_DATE = DateFormat.format("MM-dd", System.currentTimeMillis())+"";//今日日期

        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(new Runnable() {
            @Override
            public void run() {
                messageBeans= DaoManager.Companion.getInstance(MessageActivity.this).queryMessageBean(CURRENT_DATE);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        messageAdpter = new MessageAdpter(R.layout.message_item, messageBeans, MessageActivity.this);
                        if (messageBeans.size()!=0) {
                            message_re.setAdapter(messageAdpter);
                        }


                        //消息列表添加点击事件
                        messageAdpter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                Intent intent = new Intent(MessageActivity.this, MessageItemActivity.class);
                                Integer message_img = messageBeans.get(position).getMessage_img();
                                String message_title = messageBeans.get(position).getMessage_title();
                                String message_message = messageBeans.get(position).getMessage_message();
                                String message_date = messageBeans.get(position).getMessage_date();
                                Bundle bundle = new Bundle();
                                bundle.putString("message_title", message_title);
                                bundle.putString("message_message", message_message);
                                bundle.putString("message_date", message_date);
                                bundle.putInt("message_img", message_img);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        });
                    }
                });
            }
        });


    }
}