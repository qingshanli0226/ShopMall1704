package com.example.shopmall.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.common.TitleBar;
import com.example.framework.base.BaseActivity;
import com.example.framework.manager.ShoppingManager;
import com.example.framework.manager.UserManager;
import com.example.shopmall.R;
import com.example.shopmall.adapter.CollectionAdapter;
import com.example.shopmall.bean.GoodsBean;

import java.util.List;
import java.util.Map;

public class CollectionActivity extends BaseActivity implements CollectionAdapter.OnItemClickListener {
    private TitleBar tbCollection;
    private RecyclerView rvCollections;
    private CollectionAdapter collectionAdapter;

    @Override
    protected int setLayout() {
        return R.layout.activity_collection;
    }

    @Override
    public void initView() {
        tbCollection = findViewById(R.id.tb_app_collection);
        rvCollections = findViewById(R.id.rv_app_collections);
    }

    @Override
    public void initData() {
        initTitle();
        initRecycler();
        initData2();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(collectionAdapter!=null){
            initData2();
        }
    }

    private void initData2() {
        boolean loginStatus = UserManager.getInstance().getLoginStatus();
        if(loginStatus){
            List<Map<String, String>> collections = ShoppingManager.getInstance().getCollections(this);
            collectionAdapter.reFresh(collections);
        }else {
            setAlertDialog();
        }
    }

    private void setAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示：");
        builder.setMessage("您还没有登录");
        builder.setPositiveButton("前往登录", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(CollectionActivity.this,LoginActivity.class));
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ShoppingManager.getInstance().setMainitem(0);
                finish();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void initRecycler() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.VERTICAL);
        rvCollections.setLayoutManager(manager);
        collectionAdapter = new CollectionAdapter(this);
        rvCollections.setAdapter(collectionAdapter);
        collectionAdapter.setOnItemClickListener(this);
    }

    private void initTitle() {
        tbCollection.setCenterText("我的收藏",20, Color.WHITE);
        tbCollection.setLeftText("返回",13,Color.WHITE);
        tbCollection.setBackgroundColor(Color.RED);

        tbCollection.setTitleClickLisner(new TitleBar.TitleClickLisner() {
            @Override
            public void LeftClick() {
                ShoppingManager.getInstance().setMainitem(4);
                finish();
            }

            @Override
            public void RightClick() {

            }

            @Override
            public void CenterClick() {

            }
        });
    }

    @Override
    public void onItemClick(int position) {
        List<Map<String, String>> collections = ShoppingManager.getInstance().getCollections(this);
        Map<String, String> map = collections.get(position);
        GoodsBean goodsBean = new GoodsBean();
        goodsBean.setProduct_id(map.get("id"));
        goodsBean.setName(map.get("title"));
        goodsBean.setCover_price(map.get("price"));
        goodsBean.setFigure(map.get("img"));
        goodsBean.setNumber(Integer.parseInt(map.get("num")));
        Intent intent = new Intent(CollectionActivity.this, GoodsInfoActivity.class);
        intent.putExtra("goods_bean",goodsBean);
        startActivity(intent);
    }
}
