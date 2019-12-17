package com.example.buy.activity;

import android.content.Context;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buy.R;
import com.example.buy.databeans.GetSearchBeanOne;
import com.example.buy.databeans.GetSearchBeanTwo;
import com.example.buy.presenter.GetSearchPresenter;
import com.example.common.code.Constant;
import com.example.common.view.MyToolBar;
import com.example.framework.base.BaseNetConnectActivity;
import com.example.framework.base.BaseRecyclerAdapter;
import com.example.framework.base.BaseViewHolder;
import com.example.framework.port.IPresenter;
import com.example.net.AppNetConfig;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class SearchActivity extends BaseNetConnectActivity {
    private MyToolBar myToolBar;
    private RecyclerView hotRecyclerView;
    private RecyclerView childRecyclerView;
    private RecyclerView resultRecyclerView;

    private NestedScrollView nested_scrollView;

    private ArrayList<GetSearchBeanOne.ResultBean.HotProductListBean> hotList=new ArrayList<>();
    private ArrayList<GetSearchBeanOne.ResultBean.ChildBean> childList = new ArrayList<>();
    private ArrayList<GetSearchBeanTwo.ResultBean> resultList = new ArrayList<>();

    private IPresenter searchPresenter;
    @Override
    public void init() {
        super.init();
        myToolBar=findViewById(R.id.myToolBar);
        hotRecyclerView=findViewById(R.id.hotRecyclerView);
        childRecyclerView = findViewById(R.id.child_recyclerView);
        nested_scrollView = findViewById(R.id.nested_scrollView);
        resultRecyclerView = findViewById(R.id.resultRecyclerView);
        myToolBar.init(Constant.SEARCH_STYLE);
    }

    @Override
    public void initDate() {
        super.initDate();
        myToolBar.getSearch_edit().setVisibility(View.VISIBLE);
        myToolBar.getSearch_text().setVisibility(View.VISIBLE);
        myToolBar.getSearch_message().setVisibility(View.GONE);
        //获取焦点
        myToolBar.getSearch_edit().requestFocus();
        //返回按钮
        myToolBar.getScan().setImageResource(R.drawable.back3);
        myToolBar.getScan().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hotList.size()==0&&resultList.size()==0&&childList.size()==0){
                    finishActivity();
                }else {
                    hotList.clear();
                    resultList.clear();
                    childList.clear();
                    resultRecyclerView.setVisibility(View.GONE);
                    childRecyclerView.setVisibility(View.GONE);
                    hotRecyclerView.setVisibility(View.GONE);
                }
            }
        });
        //搜索
        myToolBar.getSearch_edit().setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    doSearch();
                    return true;
                }
                return false;
            }
        });

        myToolBar.getSearch_text().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSearch();
            }
        });
        //分类
        childRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        childRecyclerView.setAdapter(new BaseRecyclerAdapter<GetSearchBeanOne.ResultBean.ChildBean>(R.layout.item_search_child, childList) {
            @Override
            public void onBind(BaseViewHolder holder, int position) {
                holder.getTextView(R.id.child_name, childList.get(position).getName());
                holder.getImageView(R.id.child_image,AppNetConfig.BASE_URl_IMAGE+ childList.get(position).getPic());
            }
        });
        //具体数据
        hotRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        hotRecyclerView.setAdapter(new BaseRecyclerAdapter<GetSearchBeanOne.ResultBean.HotProductListBean>(R.layout.item_search_hot,hotList) {
            @Override
            public void onBind(BaseViewHolder holder, int position) {
                holder.getTextView(R.id.itemSearchTitle,hotList.get(position).getName());
                holder.getTextView(R.id.itemSearchBrief,hotList.get(position).getBrief());
                holder.getTextView(R.id.itemSearchPrice,hotList.get(position).getCover_price());
                holder.getImageView(R.id.itemSearchImg,AppNetConfig.BASE_URl_IMAGE+hotList.get(position).getFigure());
            }});
        //result数据
        resultRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        resultRecyclerView.setAdapter(new BaseRecyclerAdapter<GetSearchBeanTwo.ResultBean>(R.layout.item_search_result,resultList){

            @Override
            public void onBind(BaseViewHolder holder, int position) {
                holder.getTextView(R.id.itemSearchComment,resultList.get(position).getComment_list().toString());
                holder.getTextView(R.id.itemSearchSaying,resultList.get(position).getSaying());
                holder.getTextView(R.id.itemSearchUsername,resultList.get(position).getUsername());
                holder.getImageView(R.id.itemSearchAvatar,AppNetConfig.BASE_URl_IMAGE+resultList.get(position).getAvatar());
                holder.getImageView(R.id.itemSearchFigure,AppNetConfig.BASE_URl_IMAGE+resultList.get(position).getFigure());
            }
        });
    }

    @Override
    public void getSearchDataSuccess(String str) {
        super.getSearchDataSuccess(str);
        try {
            GetSearchBeanOne dateOne = new Gson().fromJson(str, GetSearchBeanOne.class);
            hotList.addAll(dateOne.getResult().get(0).getHot_product_list());
            hotRecyclerView.getAdapter().notifyDataSetChanged();
            childList.addAll(dateOne.getResult().get(0).getChild());
            childRecyclerView.getAdapter().notifyDataSetChanged();

            hotRecyclerView.setVisibility(View.VISIBLE);
            childRecyclerView.setVisibility(View.VISIBLE);
            resultRecyclerView.setVisibility(View.GONE);
        }catch (Exception e){
            GetSearchBeanTwo dateTwo = new Gson().fromJson(str, GetSearchBeanTwo.class);
            resultList.addAll(dateTwo.getResult());
            resultRecyclerView.getAdapter().notifyDataSetChanged();

            resultRecyclerView.setVisibility(View.VISIBLE);
            hotRecyclerView.setVisibility(View.GONE);
            childRecyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (searchPresenter != null) {
            searchPresenter.detachView();
        }
    }

    @Override
    public int getRelativeLayout() {
        return R.id.searchRel;
    }

    private void doSearch(){
        //点击搜索的时候隐藏软键盘
        InputMethodManager manager = (InputMethodManager) myToolBar.getSearch_edit().getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(myToolBar.getSearch_edit().getWindowToken(), 0);

        // 在这里写搜索的操作,一般都是网络请求数据
        String name = myToolBar.getSearch_edit().getText().toString().trim();
        //正则判断
        if (Pattern.matches("[a-zA-Z]+", name)){
            resultList.clear();
            hotList.clear();
            childList.clear();
            searchPresenter=new GetSearchPresenter(name);
            searchPresenter.attachView(SearchActivity.this);
            searchPresenter.doHttpGetRequest();
        }else {
            Toast.makeText(SearchActivity.this,"请输入英文或拼音",Toast.LENGTH_SHORT).show();
        }
    }
}
