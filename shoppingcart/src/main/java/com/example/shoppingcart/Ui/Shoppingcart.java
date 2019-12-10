package com.example.shoppingcart.Ui;

import android.os.UserManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.commen.ShopMailError;
import com.example.shoppingcart.Adapter.RvAdp;
import com.example.shoppingcart.Base.ShoppingCartBean;
import com.example.shoppingcart.R;
import com.example.shoppingcart.presenter.ShoppingcartPresenter;
import com.shaomall.framework.base.BaseActivity;
import com.shaomall.framework.base.BaseMVPFragment;
import com.shaomall.framework.manager.UserInfoManager;

import java.util.ArrayList;
import java.util.List;

public class Shoppingcart extends BaseMVPFragment<String> {
    private LinearLayout topBar;
    private TextView title;
    private RecyclerView listview;
    ArrayList<ShoppingCartBean> arr=new ArrayList<>();

    private CheckBox allChekbox;
    private TextView tvTotalPrice;
    private TextView tvDelete;
    private TextView tvGoToPay;
    ShoppingcartPresenter presenter;
    RvAdp rvAdp;


    @Override
    public int setLayoutId() {
        return R.layout.activity_shoopingcart;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        topBar = (LinearLayout) view.findViewById(R.id.top_bar);
        title = (TextView) view.findViewById(R.id.title);
        listview = (RecyclerView) view.findViewById(R.id.listview);

        allChekbox = (CheckBox) view.findViewById(R.id.all_chekbox);
        tvTotalPrice = (TextView) view.findViewById(R.id.tv_total_price);
        tvDelete = (TextView) view.findViewById(R.id.tv_delete);
        tvGoToPay = (TextView) view.findViewById(R.id.tv_go_to_pay);
        presenter=new ShoppingcartPresenter();
        presenter.attachView(this);
    }

    @Override
    protected void initData() {
        //网络请求
        presenter.doGetHttpRequest(200);
        rvAdp=new RvAdp(arr,getContext());
        listview.setLayoutManager(new LinearLayoutManager(getContext()));
        listview.setAdapter(rvAdp);

        rvAdp.setHuidiao(new RvAdp.Huidiao() {
            @Override
            public void hui(int i) {

                Toast.makeText(mContext, ""+i, Toast.LENGTH_SHORT).show();

            }
        });


    }

    @Override
    public void onRequestHttpDataFailed(int requestCode, ShopMailError error) {
        Toast.makeText(mContext, ""+error.getErrorMessage(), Toast.LENGTH_SHORT).show();

    }



    @Override
    public void onRequestHttpDataSuccess(int requestCode, String message, String data) {

            Log.d("SSH:",data);
            rvAdp.notifyDataSetChanged();

    }
}
