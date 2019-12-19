package com.example.dimensionleague.address;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.example.buy.databeans.OkBean;
import com.example.common.utils.GetAssetsJson;
import com.example.dimensionleague.R;
import com.example.framework.base.BaseNetConnectActivity;
import com.example.framework.port.IPresenter;
import com.example.net.AppNetConfig;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

public class AddressActivity extends BaseNetConnectActivity {
    private Button addressSure;
    private OptionsPickerView<AddressBean.CityBean.AreaBean> pvOptions;

    private IPresenter updateAddressPresenter;

    List<AddressBean.CityBean.AreaBean> provinceList=new ArrayList<>();
    List<List<AddressBean.CityBean.AreaBean>> cityList=new ArrayList<>();
    List<List<List<AddressBean.CityBean.AreaBean>>> areaList=new ArrayList<>();

    String address;
    @Override
    public void init() {
        super.init();
        addressSure = findViewById(R.id.addressSure);

        addressSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pvOptions.show();
            }
        });
    }

    @Override
    public void onRequestSuccess(Object data) {
        super.onRequestSuccess(data);
        if (((OkBean) data).getCode().equals(AppNetConfig.CODE_OK)) {
            Toast.makeText(this, "地址设置成功", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void initDate() {
        super.initDate();
        if (provinceList.isEmpty()) {
            AddressBean[] addressBean = new Gson().fromJson(GetAssetsJson.getJsonString(AddressActivity.this,
                    "city_code.json"),
                    AddressBean[].class);
            for (int i=0;i<addressBean.length;i++){
                provinceList.add(new AddressBean.CityBean.AreaBean(addressBean[i].getName(),addressBean[i].getCode()));

                List<AddressBean.CityBean.AreaBean> provinceCityList=new ArrayList<>();
                List<List<AddressBean.CityBean.AreaBean>> provinceAreaList=new ArrayList<>();

                if (addressBean[i].getCity()==null){
                    provinceCityList.add(new AddressBean.CityBean.AreaBean("",""));
                    List<AddressBean.CityBean.AreaBean> nullList=new ArrayList<>();
                    nullList.add(new AddressBean.CityBean.AreaBean("",""));
                    provinceAreaList.add(nullList);
                }else {
                    for (int j=0; j<addressBean[i].getCity().size();j++) {
                        provinceCityList.add(new AddressBean.CityBean.AreaBean(addressBean[i].getCity().get(j).getName()
                                ,addressBean[i].getCity().get(j).getCode()));
                        provinceAreaList.add(addressBean[i].getCity().get(j).getArea());
                    }
                }
                    cityList.add(provinceCityList);
                    areaList.add(provinceAreaList);

            }
        }

        pvOptions = new OptionsPickerBuilder(AddressActivity.this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                address = provinceList.get(options1).getName()
                        + cityList.get(options1).get(options2)
                        + areaList.get(options1).get(options2).get(options3).getName();
                Toast.makeText(AddressActivity.this,"位置:"+address,Toast.LENGTH_SHORT).show();
                //选中则网络请求
                updateAddressPresenter = new UpdateAddressPresenter(address);
                updateAddressPresenter.attachView(AddressActivity.this);
                updateAddressPresenter.doHttpPostRequest();

            }}).setSubmitText("确定")//确定按钮文字
                .setCancelText("取消")//取消按钮文字
                .setTitleText("城市选择")//标题
                .setSubCalSize(18)//确定和取消文字大小
                .setTitleSize(20)//标题文字大小
                .setTitleColor(Color.BLUE)//标题文字颜色
                .setSubmitColor(getResources().getColor(R.color.color_red))//确定按钮文字颜色
                .setCancelColor(getResources().getColor(R.color.buyPink))//取消按钮文字颜色
                .setTitleBgColor(getResources().getColor(R.color.buyGray))//标题背景颜色 Night mode
                .setBgColor(getResources().getColor(R.color.buyLittlePink))//滚轮背景颜色 Night mode
                .setContentTextSize(20)//滚轮文字大小
//                .setLabels("省", "市", "区")//设置选择的三级单位
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setCyclic(false, false, false)//循环与否
                .setOutSideCancelable(false)//点击外部dismiss default true
                .isDialog(true)//是否显示为对话框样式
                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .build();
        pvOptions.setPicker(provinceList,cityList,areaList);
    }

    @Override
    public int getRelativeLayout() {
        return R.id.addressRel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_address;
    }
}
