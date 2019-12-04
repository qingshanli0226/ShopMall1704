package com.example.dimensionleague.home;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.dimensionleague.R;
import com.example.dimensionleague.businessbean.HomeBean;
import com.example.framework.base.BaseNetConnectFragment;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseNetConnectFragment {
    private RecyclerView rv;
    private TextView tv;
    private List<HomeBean.ResultBean> list;


    @Override
    public void init(View view) {
        super.init(view);
        rv=view.findViewById(R.id.home_rv);
        tv=view.findViewById(R.id.home_marquee);
        list=new ArrayList<>();
    }

    @Override
    public void initDate() {
        tv.setSelected(true);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }


    @Override
    public int getRelativeLayout() {
        return 0;
    }
}
