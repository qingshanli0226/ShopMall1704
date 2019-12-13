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

import java.util.List;

/**
 * 消息界面
 */
public class MessageActivity extends BaseActivity {

    private TitleBar tbMessage;
    private RecyclerView rvMessage;

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
        MessageItemAdapter messageItemAdapter = new MessageItemAdapter(this);
        messageItemAdapter.reFresh(message);
        rvMessage.setAdapter(messageItemAdapter);

        messageItemAdapter.setLikeliest(new MessageItemAdapter.Likeliest() {
            @Override
            public void getLikeliest(int position) {
                MessageBean messageBean = new MessageBean();
                messageBean.setId((long) position);
                messageBean.setIsMessage(false);
                MessageManager.getMessageManager().updateMessage(messageBean);
            }
        });

    }
}
