package com.example.shopmall.fragment;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.common.LoadingPage;
import com.example.common.TitleBar;
import com.example.framework.base.BaseFragment;
import com.example.framework.base.IBaseView;
import com.example.net.Constant;
import com.example.shopmall.R;
import com.example.shopmall.adapter.ClassifyLeftAdapter;
import com.example.shopmall.adapter.ClassifyRightAdapter;
import com.example.shopmall.bean.ClassifyBean;
import com.example.shopmall.presenter.IntegerPresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClassifyFragment extends BaseFragment implements IBaseView<ClassifyBean> {

    TitleBar tb_classify;
    LoadingPage lp_classify_loading;

    ListView lv_left;
    RecyclerView rv_right;
    ClassifyLeftAdapter classifyLeftAdapter;
    ClassifyRightAdapter classifyRightAdapter;

    IntegerPresenter integerPresenter;

    private String[] urls = new String[]{Constant.SKIRT_URL, Constant.JACKET_URL, Constant.PANTS_URL, Constant.OVERCOAT_URL,
            Constant.ACCESSORY_URL, Constant.BAG_URL, Constant.DRESS_UP_URL, Constant.HOME_PRODUCTS_URL, Constant.STATIONERY_URL,
            Constant.DIGIT_URL, Constant.GAME_URL};

    private boolean isFirst = true;

    @Override
    protected void initData() {
        tb_classify.setTitleBacKGround(Color.RED);
        tb_classify.setCenterText("分类",18,Color.WHITE);

        tb_classify.setTitleClickLisner(new TitleBar.TitleClickLisner() {
            @Override
            public void LeftClick() {

            }

            @Override
            public void RightClick() {

            }

            @Override
            public void CenterClick() {

            }
        });

        if (isFirst){
            classifyLeftAdapter = new ClassifyLeftAdapter(getContext());
            lv_left.setAdapter(classifyLeftAdapter);
        }

        getDataPresenter(urls[0]);

        initListener(classifyLeftAdapter);

    }

    private void initListener(final ClassifyLeftAdapter adapter) {
        lv_left.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                adapter.changeSelected(i);
                if (i != 0){
                    isFirst = false;
                }
                getDataPresenter(urls[i]);
                classifyLeftAdapter.notifyDataSetChanged();
            }
        });

        lv_left.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                classifyLeftAdapter.changeSelected(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getDataPresenter(String url) {
        integerPresenter = new IntegerPresenter(url,ClassifyBean.class);
        integerPresenter.attachView(this);
        integerPresenter.getGetData();
    }

    @Override
    protected void initView(View view) {
        tb_classify = view.findViewById(R.id.tb_classify);;
        LoadingPage lp_classify_loading = view.findViewById(R.id.lp_classify_loading);

        lv_left = view.findViewById(R.id.lv_left);
        rv_right = view.findViewById(R.id.rv_right);

        rv_right.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    @Override
    protected int setLayout() {
        return R.layout.fragment_classify;
    }

    @Override
    public void onGetDataSucess(ClassifyBean data) {
        classifyRightAdapter = new ClassifyRightAdapter(getContext(), data.getResult());
        rv_right.setAdapter(classifyRightAdapter);

    }

    @Override
    public void onPostDataSucess(ClassifyBean data) {

    }

    @Override
    public void onGetDataFailed(String ErrorMsg) {

    }

    @Override
    public void onLoadingPage() {
//        lp_classify_loading.start(LoadingPage.LOADING_FAILURE);
//        lp_classify_loading.setVisibility(View.VISIBLE);
//        rv_right.setVisibility(View.GONE);
    }

    @Override
    public void onStopLoadingPage() {
//        lp_classify_loading.start(LoadingPage.LOADING_FAILURE);
//        lp_classify_loading.isSucceed();
//        lp_classify_loading.setVisibility(View.GONE);
//        rv_right.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        integerPresenter.detachView();

    }
}
