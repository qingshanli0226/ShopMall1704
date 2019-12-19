package com.example.dimensionleague.find;

import com.example.common.HomeBean;
import com.example.common.code.Constant;
import com.example.framework.base.BaseViewModel;
import com.example.net.AppNetConfig;

public class FindSendViewModel extends BaseViewModel<HomeBean.ResultBean.SeckillInfoBean.ListBean> {
    @Override
    public String getPath() {
        return AppNetConfig.BASE_URL_JSON+AppNetConfig.HOME_URL;
    }
}
