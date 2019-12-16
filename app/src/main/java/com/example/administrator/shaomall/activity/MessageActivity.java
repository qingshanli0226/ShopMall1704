package com.example.administrator.shaomall.activity;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.shaomall.R;
import com.example.administrator.shaomall.message.IsReadAdapter;
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
        notReadMessages.add(0, messageBean);
        notReadAdapter.notifyDataSetChanged();
    }

    private NotReadAdapter notReadAdapter;
    private IsReadAdapter isReadAdapter;
    private android.widget.ImageView mTitleScanning;
    private android.widget.ImageView mTitleBlack;
    private android.widget.RelativeLayout mTitleSearch;
    private android.widget.ImageView mTitleCamera;
    private android.widget.ImageView mTitleMessage;
    private android.widget.ListView mNotReadMessageLv;
    private android.widget.ListView mIsReadMessageLv;

    private List<MessageBean> notReadMessages = new ArrayList<>();
    private List<MessageBean> isReadMessages = new ArrayList<>();

    @Override
    protected int setLayoutId() {
        return R.layout.activity_message;
    }

    @Override
    protected void initView() {

        MessageManager.getInstance(this).registerMessageListener(this);
        mTitleScanning = findViewById(R.id.title_scanning);
        mTitleBlack = findViewById(R.id.title_black);
        mTitleSearch = findViewById(R.id.title_search);
        mNotReadMessageLv = findViewById(R.id.message_lv);
        mTitleCamera = findViewById(R.id.title_camera);
        mTitleMessage = findViewById(R.id.title_message);
        mIsReadMessageLv = findViewById(R.id.message_lv_isread);
    }


    //初始化
    @Override
    protected void initData() {
        mTitleScanning.setVisibility(View.INVISIBLE);
        mTitleBlack.setVisibility(View.VISIBLE);
        mTitleBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              animOutActivity();
            }
        });
        mTitleMessage.setVisibility(View.INVISIBLE);
        mTitleSearch.setVisibility(View.INVISIBLE);

        ChangeReadMessageData();
        ChangeNotReadMessageData();
        notReadAdapter = new NotReadAdapter();
        isReadAdapter = new IsReadAdapter(isReadMessages);
        mNotReadMessageLv.setAdapter(notReadAdapter);
        mIsReadMessageLv.setAdapter(isReadAdapter);
    }

    private void ChangeNotReadMessageData() {
        List<MessageBean> list = MessageManager.getInstance(this).qurayNotReadData();
        notReadMessages.addAll(list);
    }

    private void ChangeReadMessageData() {
        List<MessageBean> list = MessageManager.getInstance(this).qurayIsReadData();
        isReadMessages.addAll(list);
    }


    //未读消息适配器
    class NotReadAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return notReadMessages.size();
        }

        @Override
        public Object getItem(int position) {
            return notReadMessages.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            Holder holder;
            if (convertView == null) {
                holder = new Holder();
                convertView = LayoutInflater.from(MessageActivity.this).inflate(R.layout.item_message, parent, false);
                holder.textView = convertView.findViewById(R.id.message_item_tv);
                holder.badge = new QBadgeView(MessageActivity.this).bindTarget(convertView.findViewById(R.id.message_item_tv));
                holder.badge.setBadgeTextSize(12, true);
                holder.badge.setBadgeGravity(Gravity.TOP | Gravity.END);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }
            holder.textView.setText(notReadMessages.get(position).getMessage());
            holder.badge.setBadgeNumber(1);
            holder.badge.setOnDragStateChangedListener(new Badge.OnDragStateChangedListener() {
                @Override
                public void onDragStateChanged(int dragState, Badge badge, View targetView) {
                    if (dragState == STATE_SUCCEED) {
                        MessageManager.getInstance(MessageActivity.this).updateData(notReadMessages.get(position).getMessageId());
                        isReadMessages.add(0, notReadMessages.get(position));
                        isReadAdapter.notifyDataSetChanged();
                        notReadMessages.remove(notReadMessages.get(position));
                        notifyDataSetChanged();
                    }
                }
            });
            return convertView;
        }

        class Holder {
            TextView textView;
            Badge badge;
        }
    }
}
