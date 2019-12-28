package com.example.administrator.shaomall.type;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;

import com.example.administrator.shaomall.R;
import com.example.administrator.shaomall.message.MessageActivity;
import com.example.administrator.shaomall.search.SearchActivity;
import com.example.administrator.shaomall.type.adapter.TypeLeftAdapter;
import com.example.administrator.shaomall.type.adapter.TypeRightAdapter;
import com.example.commen.Constants;
import com.example.commen.LoadingPageConfig;
import com.example.commen.util.PageUtil;
import com.example.commen.custom.ErrorPageCustom;
import com.example.commen.network.NetType;
import com.example.net.AppNetConfig;
import com.shaomall.framework.base.BaseMVPFragment;
import com.shaomall.framework.bean.MessageBean;
import com.shaomall.framework.bean.TypeBean;
import com.shaomall.framework.manager.MessageManager;

import java.util.List;

import q.rorbin.badgeview.QBadgeView;

public class TypeFragment extends BaseMVPFragment<TypeBean> implements MessageManager.MessageListener {
    private android.widget.ListView mTypeLeftLv;
    private android.support.v7.widget.RecyclerView mTypeRightRv;
    private TypePresenter typePresenter;
    private android.widget.EditText titleSearchTv;
    private QBadgeView qBadgeView;
    private android.widget.ImageView titleMessage;

    private PageUtil pageUtil;
    private RelativeLayout typeRelaTiveLayout;

    private ErrorPageCustom mErrorPage;


    @Override
    public int setLayoutId() {
        return R.layout.fragment_type;
    }

    private String[] urls = new String[]{AppNetConfig.SKIRT_URL, AppNetConfig.JACKET_URL, AppNetConfig.PANTS_URL, AppNetConfig.OVERCOAT_URL,
            AppNetConfig.ACCESSORY_URL, AppNetConfig.BAG_URL, AppNetConfig.DRESS_UP_URL, AppNetConfig.HOME_PRODUCTS_URL, AppNetConfig.STATIONERY_URL,
            AppNetConfig.DIGIT_URL, AppNetConfig.GAME_URL};
    private TypeLeftAdapter typeLeftAdapter;
    private boolean isFirst = true;

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        mErrorPage = view.findViewById(R.id.error_type_page);

        mTypeLeftLv = view.findViewById(R.id.type_left_lv);
        mTypeRightRv = view.findViewById(R.id.type_right_rv);

        titleSearchTv = view.findViewById(R.id.title_search_tv);
        titleSearchTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toClass(SearchActivity.class);
            }
        });
        titleMessage = view.findViewById(R.id.title_message);
        qBadgeView = new QBadgeView(getContext());
        qBadgeView.bindTarget(titleMessage)
                .setBadgeTextSize(10f, true)
                .setBadgeGravity(Gravity.START | Gravity.TOP)
                .setBadgeBackgroundColor(Color.BLUE);

        typeRelaTiveLayout = view.findViewById(R.id.typeRelaTiveLayout);
        pageUtil = new PageUtil(getContext());
        pageUtil.setReview(typeRelaTiveLayout);
        pageUtil.init();
    }

    @Override
    protected void initData() {
        getDataFormNet(urls[0]);

        titleMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toClass(MessageActivity.class);
            }
        });
    }

    @Override
    public void onConnected(NetType type) {
        initData();
        mErrorPage.hideView();
    }

    @Override
    public void onDisConnected() {
        mErrorPage.showView();
    }

    private void initListener(final TypeLeftAdapter adapter) {
        //选中监听
        mTypeLeftLv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                adapter.changeSelected(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //点击监听
        mTypeLeftLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.changeSelected(position);
                if (position != 0) {
                    isFirst = false;
                }
                getDataFormNet(urls[position]);
                typeLeftAdapter.notifyDataSetChanged();
            }
        });
    }

    private void getDataFormNet(String url) {
        typePresenter = new TypePresenter();
        typePresenter.setPath(url);
        typePresenter.attachView(this);
        typePresenter.doGetHttpRequest(Constants.TYPE_DATA_CODE);

    }

    //加载动画
    @Override
    public void loadingPage(int requestCode, int code) {
        if (code == LoadingPageConfig.STATE_LOADING_CODE) {
            pageUtil.showLoad();
        } else if (code == LoadingPageConfig.STATE_SUCCESS_CODE) {
            pageUtil.hideload();
        }
    }

    @Override
    public void onRequestHttpDataListSuccess(int requestCode, String message, List<TypeBean> data) {
        super.onRequestHttpDataListSuccess(requestCode, message, data);
        if (requestCode == Constants.TYPE_DATA_CODE) {

            if (isFirst) {
                typeLeftAdapter = new TypeLeftAdapter(getContext());
                mTypeLeftLv.setAdapter(typeLeftAdapter);
            }

            initListener(typeLeftAdapter);
            TypeRightAdapter typeRightAdapter = new TypeRightAdapter(getContext(), data);
            mTypeRightRv.setAdapter(typeRightAdapter);
            GridLayoutManager manager = new GridLayoutManager(getActivity(), 3);
            //spanCount即列数。这里GridLayoutManager的第二个参数就是spanCount
            //SpanSize为多少，表示占用几个item
            manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (position == 0) {
                        return 3;
                    } else {
                        return 1;
                    }
                }
            });
            mTypeRightRv.setLayoutManager(manager);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (typePresenter != null) {
            typePresenter.detachView();
            typePresenter = null;
        }
    }

    @Override
    public void getMessage(MessageBean messageBean, int messageNum) {
        qBadgeView.setBadgeNumber(messageNum);
    }

    @Override
    public void onResume() {
        super.onResume();
        int i = MessageManager.getInstance().gitNotReadNum();
        qBadgeView.setBadgeNumber(i);
    }
}
