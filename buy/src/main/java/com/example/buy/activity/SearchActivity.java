package com.example.buy.activity;


import android.content.Context;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.buy.R;
import com.example.buy.databeans.GetSearchBeanOne;
import com.example.buy.databeans.GetSearchBeanTwo;
import com.example.buy.databinding.ActivitySearchBinding;
import com.example.buy.databinding.ItemSearchChildBinding;
import com.example.buy.databinding.ItemSearchHotBinding;
import com.example.buy.databinding.ItemSearchResultBinding;
import com.example.buy.viewmodel.SearchViewModel;
import com.example.common.code.Constant;
import com.example.framework.base.BaseBindActivity;
import com.example.framework.base.BaseRVAdapter;
import com.example.net.AppNetConfig;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;



public class SearchActivity extends BaseBindActivity<ActivitySearchBinding> {
    private ArrayList<GetSearchBeanOne.ResultBean.HotProductListBean> hotList = new ArrayList<>();
    private ArrayList<GetSearchBeanOne.ResultBean.ChildBean> childList = new ArrayList<>();
    private ArrayList<GetSearchBeanTwo.ResultBean> resultList = new ArrayList<>();

    private ActivitySearchBinding activitySearchBinding;

    SearchViewModel searchViewModel;
    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView(ActivitySearchBinding bindView) {
        activitySearchBinding = bindView;
        //TODO 初始化控件
        activitySearchBinding.myToolBar.init(Constant.SEARCH_STYLE);

        //TODO 挑选toolbar风格
        activitySearchBinding.myToolBar.init(Constant.SEARCH_STYLE);
        activitySearchBinding.myToolBar.getSearch_edit().setVisibility(View.VISIBLE);
        activitySearchBinding.myToolBar.getSearch_text().setVisibility(View.VISIBLE);
        activitySearchBinding.myToolBar.getSearch_message().setVisibility(View.GONE);
        //TODO 获取焦点
        activitySearchBinding.myToolBar.getSearch_edit().requestFocus();
        //TODO 返回按钮
        activitySearchBinding.myToolBar.getScan().setImageResource(R.drawable.back3);
        //TODO 初始化RecyclerView
        activitySearchBinding.hotRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        activitySearchBinding.resultRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        activitySearchBinding.childRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        activitySearchBinding.hotRecyclerView.setAdapter(new BaseRVAdapter<GetSearchBeanOne.ResultBean.HotProductListBean, ItemSearchHotBinding>(hotList, R.layout.item_search_hot) {

            @Override
            public void onBind(BindViewHolder holder, int position) {
                hotList.get(position).setFigure(AppNetConfig.BASE_URl_IMAGE + hotList.get(position).getFigure());
                holder.bindView.setBean(hotList.get(position));
                holder.bindView.setRvAdapter((BaseRVAdapter) activitySearchBinding.hotRecyclerView.getAdapter());
            }
        });

        activitySearchBinding.childRecyclerView.setAdapter(new BaseRVAdapter<GetSearchBeanOne.ResultBean.ChildBean, ItemSearchChildBinding>(childList, R.layout.item_search_child) {

            @Override
            public void onBind(BindViewHolder holder, int position) {
                childList.get(position).setPic(AppNetConfig.BASE_URl_IMAGE + childList.get(position).getPic());
                holder.bindView.setBean(childList.get(position));
                holder.bindView.setRvAdapter((BaseRVAdapter) activitySearchBinding.childRecyclerView.getAdapter());
            }
        });

        activitySearchBinding.resultRecyclerView.setAdapter(new BaseRVAdapter<GetSearchBeanTwo.ResultBean, ItemSearchResultBinding>(resultList, R.layout.item_search_result) {

            @Override
            public void onBind(BindViewHolder holder, int position) {
                resultList.get(position).setAvatar(AppNetConfig.BASE_URl_IMAGE + resultList.get(position).getAvatar());
                resultList.get(position).setFigure(AppNetConfig.BASE_URl_IMAGE + resultList.get(position).getFigure());
                holder.bindView.setBean(resultList.get(position));
                holder.bindView.setRvAdapter((BaseRVAdapter) activitySearchBinding.resultRecyclerView.getAdapter());
            }
        });
    }

    @Override
    protected void initDate() {
        activitySearchBinding.myToolBar.getScan().setOnClickListener(v -> {
            if (hotList.size() == 0 && resultList.size() == 0 && childList.size() == 0) {
                finishActivity();
            } else {
                hotList.clear();
                resultList.clear();
                childList.clear();
                activitySearchBinding.resultRecyclerView.setVisibility(View.GONE);
                activitySearchBinding.childRecyclerView.setVisibility(View.GONE);
                activitySearchBinding.hotRecyclerView.setVisibility(View.GONE);
            }
        });
        //搜索
        activitySearchBinding.myToolBar.getSearch_edit().setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                doSearch();
                return true;
            }
            return false;
        });

        activitySearchBinding.myToolBar.getSearch_text().setOnClickListener(v -> doSearch());
        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        searchViewModel.getLiveData().observe(this, responseBody -> {
            try {
                String str = responseBody.string();
                try {
                    GetSearchBeanOne dateOne = new Gson().fromJson(str, GetSearchBeanOne.class);
                    hotList.addAll(dateOne.getResult().get(0).getHot_product_list());
                    activitySearchBinding.hotRecyclerView.getAdapter().notifyDataSetChanged();
                    childList.addAll(dateOne.getResult().get(0).getChild());
                    activitySearchBinding.childRecyclerView.getAdapter().notifyDataSetChanged();

                    activitySearchBinding.hotRecyclerView.setVisibility(View.VISIBLE);
                    activitySearchBinding.childRecyclerView.setVisibility(View.VISIBLE);
                    activitySearchBinding.resultRecyclerView.setVisibility(View.GONE);
                } catch (Exception e) {
                    try {
                        GetSearchBeanTwo dateTwo = new Gson().fromJson(str, GetSearchBeanTwo.class);
                        resultList.addAll(dateTwo.getResult());
                        activitySearchBinding.resultRecyclerView.getAdapter().notifyDataSetChanged();

                        activitySearchBinding.resultRecyclerView.setVisibility(View.VISIBLE);
                        activitySearchBinding.hotRecyclerView.setVisibility(View.GONE);
                        activitySearchBinding.childRecyclerView.setVisibility(View.GONE);
                    } catch (Exception e1) {
                        Toast.makeText(SearchActivity.this, "这个数据太长了,而且拿不到bean", Toast.LENGTH_SHORT).show();
                        activitySearchBinding.resultRecyclerView.setVisibility(View.GONE);
                        activitySearchBinding.hotRecyclerView.setVisibility(View.GONE);
                        activitySearchBinding.childRecyclerView.setVisibility(View.GONE);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void doSearch() {
        //点击搜索的时候隐藏软键盘
        InputMethodManager manager = (InputMethodManager) activitySearchBinding.myToolBar.getSearch_edit().getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(activitySearchBinding.myToolBar.getSearch_edit().getWindowToken(), 0);

        // 在这里写搜索的操作,一般都是网络请求数据
        String name = activitySearchBinding.myToolBar.getSearch_edit().getText().toString().trim();
        //正则判断
        if (Pattern.matches("[a-zA-Z]+", name)) {
            resultList.clear();
            hotList.clear();
            childList.clear();
            searchViewModel.setName(name);
            searchViewModel.doGetData();
        } else {
            Toast.makeText(SearchActivity.this, "请输入英文或拼音", Toast.LENGTH_SHORT).show();
        }
    }
}
