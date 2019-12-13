package com.example.dimensionleague.home;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dimensionleague.CacheManager;
import com.example.dimensionleague.R;
import com.example.common.HomeBean;
import com.example.dimensionleague.home.adapter.HomeAdapter;
import com.example.framework.base.BaseNetConnectFragment;
import com.example.framework.port.IPresenter;

public class HomeFragment extends BaseNetConnectFragment {
    private RecyclerView rv;
    private HomeAdapter adapter;
    private HomeBean.ResultBean list =new HomeBean.ResultBean();
    private IPresenter homePresnter;

    private int type =0;
    public HomeFragment(int i) {
        super();
        this.type=i;
    }

    public HomeFragment() {
        super();
    }

    @Override
    public void init(View view) {
        super.init(view);
        rv=view.findViewById(R.id.home_rv);
        homePresnter=new HomePresenter();
//        判断type值来显示隐藏搜索框
        if (type==1) {
            LinearLayout layout = view.findViewById(R.id.linear);
            layout.setVisibility(View.GONE);
        }
    }

    @Override
    public void initDate() {
       if(CacheManager.getInstance().getHomeBeanData()!=null){
           list=(((HomeBean) CacheManager.getInstance().getHomeBeanData()).getResult() );
       }
        adapter=new HomeAdapter(list,getContext());
        rv.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        rv.setAdapter(adapter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public int getRelativeLayout() {
        return 0;
    }

    @Override
    public void onRequestSuccess(Object data) {
        if (((HomeBean)data).getCode()==200){
            list=((HomeBean)data).getResult();
            rv.getAdapter().notifyDataSetChanged();

            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext()) {
                @Override
                public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                    return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                }
            };
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            rv.setLayoutManager(layoutManager);
        }
    }
}
