package com.example.administrator.shaomall.home;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.shaomall.AnimationNestedScrollView;
import com.example.administrator.shaomall.CommonUtil;
import com.example.administrator.shaomall.R;
import com.shaomall.framework.base.BaseFragment;

public class HomeFragment extends BaseFragment{
    private AnimationNestedScrollView sv_view;
    private LinearLayout ll_search;
    private TextView tv_title;
    private float LL_SEARCH_MIN_TOP_MARGIN, LL_SEARCH_MAX_TOP_MARGIN, LL_SEARCH_MAX_WIDTH, LL_SEARCH_MIN_WIDTH, TV_TITLE_MAX_TOP_MARGIN;
    private ViewGroup.MarginLayoutParams searchLayoutParams, titleLayoutParams;




    @Override
    protected void initView(View view, Bundle savedInstanceState) {

        sv_view = view.findViewById(R.id.search_sv_view);
        ll_search =  view.findViewById(R.id.search_ll_search);
        tv_title =  view.findViewById(R.id.search_tv_title);
        searchLayoutParams = (ViewGroup.MarginLayoutParams) ll_search.getLayoutParams();
        titleLayoutParams = (ViewGroup.MarginLayoutParams) tv_title.getLayoutParams();


        setTitle();
    }

    @Override
    protected void initData() {

    }

    private void setTitle() {
        LL_SEARCH_MIN_TOP_MARGIN = CommonUtil.dp2px(getContext(), 4.5f);//布局关闭时顶部距离
        LL_SEARCH_MAX_TOP_MARGIN = CommonUtil.dp2px(getContext(), 49f);//布局默认展开时顶部距离
        LL_SEARCH_MAX_WIDTH = CommonUtil.getScreenWidth(getContext()) - CommonUtil.dp2px(getContext(), 30f);//布局默认展开时的宽度
       // LL_SEARCH_MIN_WIDTH = CommonUtil.getScreenWidth(getContext()) - CommonUtil.dp2px(getContext(), 82f);//布局关闭时的宽度
        LL_SEARCH_MIN_WIDTH = CommonUtil.getScreenWidth(getContext()) - CommonUtil.dp2px(getContext(), 90f);//布局关闭时的宽度
        TV_TITLE_MAX_TOP_MARGIN = CommonUtil.dp2px(getContext(), 11.5f);

        sv_view.setOnAnimationScrollListener(new AnimationNestedScrollView.OnAnimationScrollChangeListener() {
            @Override
            public void onScrollChanged(float dy) {
                float searchLayoutNewTopMargin = LL_SEARCH_MAX_TOP_MARGIN - dy;
               // float searchLayoutNewWidth = LL_SEARCH_MAX_WIDTH - dy * 1.3f;//此处 * 1.3f 可以设置搜索框宽度缩放的速率
                float searchLayoutNewWidth = LL_SEARCH_MAX_WIDTH - dy * 3.0f;//此处 * 1.3f 可以设置搜索框宽度缩放的速率

                float titleNewTopMargin = (float) (TV_TITLE_MAX_TOP_MARGIN - dy * 0.5);

                //处理布局的边界问题
                searchLayoutNewWidth = searchLayoutNewWidth < LL_SEARCH_MIN_WIDTH ? LL_SEARCH_MIN_WIDTH : searchLayoutNewWidth;

                if (searchLayoutNewTopMargin < LL_SEARCH_MIN_TOP_MARGIN) {
                    searchLayoutNewTopMargin = LL_SEARCH_MIN_TOP_MARGIN;
                }

                if (searchLayoutNewWidth < LL_SEARCH_MIN_WIDTH) {
                    searchLayoutNewWidth = LL_SEARCH_MIN_WIDTH;
                }

                float titleAlpha = 255 * titleNewTopMargin / TV_TITLE_MAX_TOP_MARGIN;
                if (titleAlpha < 0) {
                    titleAlpha = 0;
                }

                //设置相关控件的LayoutParams  此处使用的是MarginLayoutParams，便于设置params的topMargin属性
                tv_title.setTextColor(tv_title.getTextColors().withAlpha((int) titleAlpha));
                titleLayoutParams.topMargin = (int) titleNewTopMargin;
                tv_title.setLayoutParams(titleLayoutParams);

                searchLayoutParams.topMargin = (int) searchLayoutNewTopMargin;
                searchLayoutParams.width = (int) searchLayoutNewWidth;
                ll_search.setLayoutParams(searchLayoutParams);
            }
        });
    }



    @Override
    public int setLayoutId() {
        return R.layout.fragment_home;
    }
}
