package com.example.shopmall.activity;

import android.graphics.Color;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.common.TitleBar;
import com.example.framework.base.BaseActivity;
import com.example.framework.bean.MessageBean;
import com.example.shopmall.R;
import com.example.shopmall.adapter.MessageItemAdapter;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenu;
import com.yanzhenjie.recyclerview.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeMenuItem;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息界面
 */
public class MessageActivity extends BaseActivity {

    private TitleBar tbMessage;
    private SwipeRecyclerView srvMessage;

    private int sum = 0;

    @Override
    protected int setLayout() {
        return R.layout.activity_message;
    }

    @Override
    public void initView() {
        tbMessage = findViewById(R.id.tb_message);
        srvMessage = findViewById(R.id.srv_message);

        srvMessage.setLayoutManager(new LinearLayoutManager(this));
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

        SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
            @Override
            public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int position) {
                SwipeMenuItem menuItem = new SwipeMenuItem(MessageActivity.this);
                menuItem.setWidth(100);
                menuItem.setText("删除");
                rightMenu.addMenuItem(menuItem);
            }
        };
        srvMessage.setSwipeMenuCreator(swipeMenuCreator);

        OnItemMenuClickListener onItemMenuClickListener = new OnItemMenuClickListener() {
            @Override
            public void onItemClick(SwipeMenuBridge menuBridge, int adapterPosition) {
                menuBridge.closeMenu();

                Log.d("####", "onItemClick: " + adapterPosition);

            }
        };

        srvMessage.setOnItemMenuClickListener(onItemMenuClickListener);


        //数据库获取数据，添加到消息界面
//        List<MessageBean> messages = MessageManager.getAddressBarManager().getMessage();
        final MessageBean messageBean = new MessageBean();
        messageBean.setIsMessage(false);
        messageBean.setNameMessage("接收到数据");
        messageBean.setContentMessage("数据内容");
        final List<MessageBean> message = new ArrayList<>();
        message.add(messageBean);
        final MessageItemAdapter messageItemAdapter = new MessageItemAdapter(this);
        messageItemAdapter.reFresh(message);
        srvMessage.setAdapter(messageItemAdapter);

        messageItemAdapter.setLikeliest(new MessageItemAdapter.Likeliest() {
            @Override
            public void getLikeliest(int position) {
                if (!message.get(position).getIsMessage()){
                    messageBean.setIsMessage(true);
                    message.remove(position);
                    message.add(position,messageBean);
                    messageItemAdapter.reFresh(message);
                    srvMessage.setAdapter(messageItemAdapter);
                    messageItemAdapter.notifyDataSetChanged();
                }
            }
        });

        for (int i = 0; i < message.size(); i++) {
            if (!message.get(i).getIsMessage()){
                sum++;
                if (sum == 0){

                }
            }
        }

    }
}
