package com.example.shopmall.fragment;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;

import com.example.common.LoadingPage;
import com.example.common.TitleBar;
import com.example.framework.base.BaseFragment;
import com.example.framework.base.IGetBaseView;
import com.example.framework.base.ILoadView;
import com.example.net.Constant;
import com.example.shopmall.R;
import com.example.shopmall.adapter.ClassifyLeftAdapter;
import com.example.shopmall.adapter.ClassifyRightAdapter;
import com.example.shopmall.bean.ClassifyBean;
import com.example.shopmall.presenter.IntegerPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClassifyFragment extends BaseFragment implements IGetBaseView<ClassifyBean>,ILoadView {

    private TitleBar tbClassify;
    private LoadingPage lpClassifyLoading;
    private LinearLayout llClassify;

    private RecyclerView rvClassifyLeft;
    private RecyclerView rvClassifyRight;
    private ClassifyLeftAdapter classifyLeftAdapter;
    private ClassifyRightAdapter classifyRightAdapter;

    private IntegerPresenter integerPresenter;

    //左边显示到的数据
    private List<String> titles = new ArrayList();

    //右边要显示的数据的地址
    private String[] urls = new String[]{Constant.SKIRT_URL, Constant.JACKET_URL, Constant.PANTS_URL, Constant.OVERCOAT_URL,
            Constant.ACCESSORY_URL, Constant.BAG_URL, Constant.DRESS_UP_URL, Constant.HOME_PRODUCTS_URL, Constant.STATIONERY_URL,
            Constant.DIGIT_URL, Constant.GAME_URL};

    private boolean isFirst = true;

    @Override
    protected void initData() {
        tbClassify.setTitleBacKGround(Color.WHITE);
        tbClassify.setCenterText("分类",18,Color.BLACK);

        tbClassify.setTitleClickLisner(new TitleBar.TitleClickLisner() {
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
            classifyLeftAdapter = new ClassifyLeftAdapter();
            classifyLeftAdapter.reFresh(titles);
            rvClassifyLeft.setAdapter(classifyLeftAdapter);
        }

        getDataPresenter(urls[0]);

        initListener();

    }

    private void initListener() {
        classifyLeftAdapter.setLikeliest(new ClassifyLeftAdapter.Likeliest() {
            @Override
            public void getLikeliest(int position) {
                classifyLeftAdapter.changeSelected(position);
                if (position != 0){
                    isFirst = false;
                }
                getDataPresenter(urls[position]);
                classifyLeftAdapter.notifyDataSetChanged();

                classifyLeftAdapter.changeSelected(position);
            }
        });
    }

    //网络获取数据
    private void getDataPresenter(String url) {
        integerPresenter = new IntegerPresenter(url,ClassifyBean.class);
        integerPresenter.attachGetView(this);
        integerPresenter.attachLoadView(this);
        integerPresenter.getGetData();
    }

    @Override
    protected void initView(View view) {
        tbClassify = view.findViewById(R.id.tb_classify);
        lpClassifyLoading = view.findViewById(R.id.lp_classify_loading);
        llClassify = view.findViewById(R.id.ll_classify);

        rvClassifyLeft = view.findViewById(R.id.rv_classify_left);
        rvClassifyRight = view.findViewById(R.id.rv_classify_right);

        rvClassifyLeft.setLayoutManager(new LinearLayoutManager(getContext()));
        rvClassifyRight.setLayoutManager(new LinearLayoutManager(getContext()));

        titles.add("小裙子");
        titles.add("上衣");
        titles.add("下装");
        titles.add("外套");
        titles.add("配件");
        titles.add("包包");
        titles.add("装扮");
        titles.add("居家宅品");
        titles.add("办公文具");
        titles.add("数码周边");
        titles.add("游戏专区");

    }

    @Override
    protected int setLayout() {
        return R.layout.fragment_classify;
    }

    @Override
    public void onGetDataSucess(ClassifyBean data) {
        classifyRightAdapter = new ClassifyRightAdapter(getContext());
        classifyRightAdapter.reFresh(data.getResult());
        rvClassifyRight.setAdapter(classifyRightAdapter);

    }

    //网络获取数据失败显示loadingPage
    @Override
    public void onGetDataFailed(String ErrorMsg) {
        lpClassifyLoading.start(LoadingPage.LOADING_FAILURE);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        integerPresenter.detachView();
    }

    //开始网络请求加载loading
    @Override
    public void onLoadingPage() {
        lpClassifyLoading.start(LoadingPage.LOADING_SUCCEED);
        lpClassifyLoading.setVisibility(View.VISIBLE);
        llClassify.setVisibility(View.GONE);
    }

    //结束网络请求加载loading
    @Override
    public void onStopLoadingPage() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                lpClassifyLoading.isSucceed();
                llClassify.setVisibility(View.VISIBLE);
            }
        },1000);
    }
}
