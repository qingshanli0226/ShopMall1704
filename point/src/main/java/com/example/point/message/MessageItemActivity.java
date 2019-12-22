package com.example.point.message;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.common.code.Constant;
import com.example.common.view.MyToolBar;
import com.example.framework.base.BaseNetConnectActivity;
import com.example.point.R;
import java.util.ArrayList;
import java.util.List;

public class MessageItemActivity extends BaseNetConnectActivity {

    private MyToolBar messageitem_toolbar;
    private RecyclerView messageitem_re;
    private ImageView messageitem_record;
    private EditText messageitem_edit;
    private ImageButton messageitem_other;

    private LinearLayout messageitem_line;
    private List<MessageBean> beanList;
    private MessageitemAdpter messageitemAdpter;
    @Override
    public int getLayoutId() {
        return R.layout.messageitem_activity;
    }

    @Override
    public void init() {
        super.init();
        MyToolBar messageitem_toolbar = findViewById(R.id.messageitem_toolbar);
        ImageView messageitem_record = findViewById(R.id.messageitem_record);
        final EditText messageitem_edit = findViewById(R.id.messageitem_edit);
        ImageView messageitem_other = findViewById(R.id.messageitem_other);
        messageitem_line = findViewById(R.id.messageitem_line);
        RecyclerView messageitem_re = findViewById(R.id.messageitem_re);
        messageitem_toolbar.setBackground(getResources().getDrawable(R.drawable.toolbar_style));
        messageitem_toolbar.init(Constant.OTHER_STYLE);
        messageitem_toolbar.getOther_back().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Intent intent = getIntent();
        String message_title = intent.getStringExtra("message_title");
        String message_message = intent.getStringExtra("message_message");
        String message_date = intent.getStringExtra("message_date");
        Integer message_img = intent.getIntExtra("message_img", 0);
        messageitem_toolbar.getOther_title().setText("" + message_title);
        beanList=new ArrayList<>();
        beanList.add(new MessageBean(message_img,message_title,message_message,message_date));
        messageitemAdpter=new MessageitemAdpter(this,beanList);
        messageitem_re.setLayoutManager(new LinearLayoutManager(this));
        messageitem_re.setAdapter(messageitemAdpter);
        //为了让系统输入法弹出的时候不挡住输入框
        messageitem_line.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect=new Rect();
                messageitem_line.getWindowVisibleDisplayFrame(rect);//rect为输出参数,因此rect不允许为null
                int mainInvisibleHeight=messageitem_line.getRootView().getHeight()-rect.bottom;
                if (mainInvisibleHeight>100){
                    int[] location=new int[2];
                    messageitem_line.getLocationOnScreen(location);//输入参数必须是一个长度为2的int数组
                    int scrollHeight=(location[1]+messageitem_line.getHeight()-rect.bottom);
                    messageitem_line.scrollTo(0,scrollHeight);
                }else{
                    messageitem_line.scrollTo(0,0);
                }

            }
        });
         //发送按钮
        messageitem_other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = messageitem_edit.getText().toString();
                String CURRENT_DATE = DateFormat.format("MM-dd", System.currentTimeMillis())+"";//今日日期
//                if (AccountManager.getInstance().isLogin()) {
//                    if (AccountManager.getInstance().user.getName() != null) {
//                        if (AccountManager.getInstance().user.getAvatar() != null) {
//                            Log.i("onClick", "onClick: 11");
//                            Integer integer= (Integer) AccountManager.getInstance().user.getAvatar();
//                            beanList.add(new MessageBean(integer,"",message,CURRENT_DATE));
//                            Log.i("onClick", "onClick: 33");
//                            messageitemAdpter.notifyDataSetChanged();
//                            messageitem_edit.setText("");
//                        }
//                    }
//                } else {
                    Integer integer=R.mipmap.wu;
                    Log.i("onClick", "onClick: 22");
                    beanList.add(new MessageBean(integer,"",message,CURRENT_DATE));
                    Log.i("onClick", "onClick: 33");
                    messageitemAdpter.notifyDataSetChanged();
                    messageitem_edit.setText("");
               // }

            }
        });

    }

    @Override
    public void initDate() {
        super.initDate();
    }

    @Override
    public int getRelativeLayout() {
        return super.getRelativeLayout();
    }


}
