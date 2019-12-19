package com.example.shopmall.activity;

import android.graphics.Color;
import android.util.Log;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.common.TitleBar;
import com.example.framework.base.BaseActivity;
import com.example.framework.bean.MessageBean;
import com.example.framework.manager.MessageManager;
import com.example.shopmall.R;
import com.example.shopmall.adapter.MessageItemAdapter;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenu;
import com.yanzhenjie.recyclerview.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeMenuItem;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.List;

/**
 * 消息界面
 */
public class MessageActivity extends BaseActivity {

    private TitleBar tbMessage;
    private SwipeRecyclerView srvMessage;
    private MessageItemAdapter messageItemAdapter;

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
        tbMessage.setBackgroundColor(Color.WHITE);
        tbMessage.setLeftImg(R.drawable.left);
        tbMessage.setCenterText("消息中心",18,Color.BLACK);

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

        //侧滑删除item
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

        //侧滑点击事件
        srvMessage.setOnItemMenuClickListener(onItemMenuClickListener);


        //数据库获取数据，添加到消息界面
        final List<MessageBean> messages = MessageManager.getMessageManager().getMessage();
        if (messages.size() > 0){
            messageItemAdapter = new MessageItemAdapter(this);
            messageItemAdapter.reFresh(messages);
            srvMessage.setAdapter(messageItemAdapter);

            messageItemAdapter.setLikeliest(new MessageItemAdapter.Likeliest() {
                @Override
                public void getLikeliest(int position) {
                    if (!messages.get(position).getIsMessage()){
                        Long id = messages.get(position).getId();
                        messages.get(position).setIsMessage(true);
                        MessageBean messageBean = new MessageBean();
                        messageBean.setId(id);
                        messageBean.setIsMessage(true);
                        MessageManager.getMessageManager().updateMessage(messageBean);
                        messageItemAdapter.reFresh(messages);
                        srvMessage.setAdapter(messageItemAdapter);
                        messageItemAdapter.notifyDataSetChanged();
                    }
                }
            });

        }else {
            Toast.makeText(this, "现在没有消息", Toast.LENGTH_SHORT).show();
        }
    }
}
