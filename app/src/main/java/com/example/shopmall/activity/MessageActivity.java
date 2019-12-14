package com.example.shopmall.activity;

import android.graphics.Color;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.common.LoadingPage;
import com.example.common.TitleBar;
import com.example.framework.base.BaseActivity;
import com.example.framework.bean.MessageBean;
import com.example.framework.manager.MessageManager;
import com.example.shopmall.R;
import com.example.shopmall.adapter.MessageItemAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息界面
 */
public class MessageActivity extends BaseActivity {

    private TitleBar tbMessage;
    private RecyclerView rvMessage;

    private int sum = 0;

    @Override
    protected int setLayout() {
        return R.layout.activity_message;
    }

    @Override
    public void initView() {
        tbMessage = findViewById(R.id.tb_message);
        rvMessage = findViewById(R.id.rv_message);

        rvMessage.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void initData() {
        tbMessage.setBackgroundColor(Color.RED);
        tbMessage.setLeftImg(R.drawable.left);
        tbMessage.setCenterText("消息中心",18,Color.WHITE);

        tbMessage.setTitleClickLisner(new TitleBar.TitleClickLisner() {
            @Override
            public void LeftClick() {
                finish();
            }

            @Override
            public void RightClick() {

            }

            @Override
            public void CenterClick() {

            }
        });

        //数据库获取数据，添加到消息界面
        List<MessageBean> message = MessageManager.getMessageManager().getMessage();
//        MessageBean messageBean = new MessageBean();
//        messageBean.setIsMessage(true);
//        messageBean.setNameMessage("接收到数据");
//        messageBean.setContentMessage("数据内容");
//        List<MessageBean> message = new ArrayList<>();
//        message.add(messageBean);
        MessageItemAdapter messageItemAdapter = new MessageItemAdapter(this);
        messageItemAdapter.reFresh(message);
        rvMessage.setAdapter(messageItemAdapter);

        for (int i = 0; i < message.size(); i++) {
            if (!message.get(i).getIsMessage()){
                sum++;
            }
        }

    }
}
