package com.example.administrator.shaomall.message;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.shaomall.R;
import com.example.commen.custom.ToolbarCustom;
import com.shaomall.framework.base.BaseActivity;
import com.shaomall.framework.bean.MessageBean;
import com.shaomall.framework.manager.MessageManager;

import java.util.ArrayList;
import java.util.List;

import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;


public class MessageActivity extends BaseActivity implements MessageManager.MessageListener {
    @Override
    public void getMessage(MessageBean messageBean, int messageNum) {
        Log.i("LWW", "getMessage: " + messageBean.getMessage());
        messages.add(0, messageBean);
        messageAdapter.notifyDataSetChanged();
    }

    private MessageAdapter messageAdapter;
    private android.widget.ListView mNotReadMessageLv;
    private List<MessageBean> messages = new ArrayList<>();

    @Override
    protected int setLayoutId() {
        return R.layout.activity_message;
    }

    @Override
    protected void initView() {
        MessageManager.getInstance().registerMessageListener(this);

        mNotReadMessageLv = findViewById(R.id.message_lv);
        ToolbarCustom mTbCustomTop = findViewById(R.id.tb_top);
        mTbCustomTop.setTbLeftIVOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animOutActivity(MessageActivity.this);
            }
        });

    }


    //初始化
    @Override
    protected void initData() {
        changeMessageDatas();
        messageAdapter = new MessageAdapter();
        mNotReadMessageLv.setAdapter(messageAdapter);
    }

    private void changeMessageDatas() {
        List<MessageBean> messageBeans = MessageManager.getInstance().selectAll();
        messages = messageBeans;
    }


    //未读消息适配器
    class MessageAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return messages.size();
        }

        @Override
        public Object getItem(int position) {
            return messages.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @SuppressLint("CutPasteId")
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            Holder holder;
            if (convertView == null) {
                holder = new Holder();
                if (messages.get(position).getIsRead().equals("yes")) {
                    convertView = LayoutInflater.from(MessageActivity.this).inflate(R.layout.item_message, parent, false);
                } else if (messages.get(position).getIsRead().equals("no")) {
                    convertView = LayoutInflater.from(MessageActivity.this).inflate(R.layout.item_message2, parent, false);
                }
                holder.image = convertView.findViewById(R.id.message_item_iv);
                holder.badge = new QBadgeView(MessageActivity.this).bindTarget(convertView.findViewById(R.id.message_item_tv));
                holder.badge.setBadgeTextSize(12, true);
                holder.badge.setBadgeGravity(Gravity.TOP | Gravity.END);
                holder.message = convertView.findViewById(R.id.message_item_tv);
                holder.title = convertView.findViewById(R.id.message_item_title);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }
            holder.message.setText(messages.get(position).getMessage());
            if (messages.get(position).getIsRead().equals("no")) {
                holder.badge.setBadgeText("未读");
                holder.badge.setOnDragStateChangedListener(new Badge.OnDragStateChangedListener() {
                    @Override
                    public void onDragStateChanged(int dragState, Badge badge, View targetView) {
                        if (dragState == STATE_SUCCEED) {
                            MessageManager.getInstance().updateData(messages.get(position).getMessageId());
                            messages.get(position).setIsRead("yes");
                        }
                    }
                });
            } else if (messages.get(position).getIsRead().equals("yes")) {

            }
            return convertView;
        }

        class Holder {
            TextView message;
            TextView title;
            ImageView image;
            Badge badge;
        }
    }

    @Override
    protected void onDestroy() {
        MessageManager.getInstance().unRegisterMessageListener(this);
        super.onDestroy();
    }
}
