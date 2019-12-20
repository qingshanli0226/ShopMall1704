package com.example.point.message;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.example.common.code.Constant;
import com.example.common.view.MyToolBar;
import com.example.framework.base.BaseNetConnectActivity;
import com.example.point.R;
import com.umeng.analytics.MobclickAgent;

public class MessageItemActivity extends BaseNetConnectActivity {
    private MyToolBar messageitem_toolbar;
    private RecyclerView messageitem_re;
    private ImageView messageitem_record;
    private EditText messageitem_edit;
    private ImageView messageitem_other;
    private LinearLayout messageitem_line;

    @Override
    public int getLayoutId() {
        return R.layout.messageitem_activity;
    }

    @Override
    public void init() {
        super.init();
        messageitem_toolbar = findViewById(R.id.messageitem_toolbar);
        messageitem_record = findViewById(R.id.messageitem_record);
        messageitem_edit = findViewById(R.id.messageitem_edit);
        messageitem_other = findViewById(R.id.messageitem_other);
        messageitem_line = findViewById(R.id.messageitem_line);
        messageitem_re = findViewById(R.id.messageitem_re);
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

    }

    @Override
    public void initDate() {
        super.initDate();
    }

    @Override
    public int getRelativeLayout() {
        return super.getRelativeLayout();
    }
    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) and run LayoutCreator again
    }
}
