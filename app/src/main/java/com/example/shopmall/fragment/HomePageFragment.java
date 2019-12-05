package com.example.shopmall.fragment;

import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;

import com.example.base.BaseFragment;
import com.example.common.TitleBar;
import com.example.shopmall.R;
import com.example.shopmall.StepManager;
import com.example.shopmall.activity.MessageActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomePageFragment extends BaseFragment {

    //http://49.233.93.155:8080/application/json
    //http://www.qubaobei.com/ios/cf/dish_list.php?stage_id=1&limit=10&page=1

    TitleBar tb_homepage;

    @Override
    protected void initData() {
        tb_homepage.setTitleBacKGround(Color.RED);
        tb_homepage.setCenterText("首页",18,Color.WHITE);
        tb_homepage.setRightImg(R.mipmap.new_message_icon);

        tb_homepage.setTitleClickLisner(new TitleBar.TitleClickLisner() {
            @Override
            public void LeftClick() {
                Log.e("####", "左边");
            }

            @Override
            public void RightClick() {
                Log.e("####", "右边");
                startActivity(new Intent(getContext(), MessageActivity.class));
            }

            @Override
            public void CenterClick() {
                Log.e("####", "中间");
            }
        });



        StepManager.getInstance().registerListener(new StepManager.StepManagerListener() {
            @Override
            public void onStepChange(int count) {
                Log.e("##Step",count+"");
            }
        });
    }

    @Override
    protected void initView(View view) {
        tb_homepage = view.findViewById(R.id.tb_homepage);
    }

    @Override
    protected int setLayout() {
        return R.layout.fragment_home_page;
    }
}
