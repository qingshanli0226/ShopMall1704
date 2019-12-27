package com.example.shopmall.activity;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.common.TitleBar;
import com.example.framework.base.BaseActivity;
import com.example.framework.base.IPostBaseView;
import com.example.shopmall.R;
import com.example.shopmall.bean.AddressBean;
import com.example.shopmall.presenter.AddressPresenter;
import com.example.shopmall.presenter.LocationPresenter;

/**
 * 新建收货地址
 */
public class LocationActivity extends BaseActivity implements IPostBaseView<AddressBean> {

    //头部导航
    private TitleBar tbLocation;
    //收货人
    private EditText etConsignee;
    //手机号
    private EditText etCellPhoneNumber;
    //所在地区
    private EditText etLocation;
    //详细地址
    private EditText etDetailed;
    //保存
    private Button btLocationInflate;

    //登录用户token
    private String token;
    //上传手机号的presenter
    private AddressPresenter addressPresenter;
    //上传地址的presenter
    private LocationPresenter locationPresenter;

    @Override
    protected int setLayout() {
        return R.layout.activity_location;
    }

    @Override
    public void initView() {
        tbLocation = findViewById(R.id.tb_location);
        etConsignee = findViewById(R.id.et_consignee);
        etCellPhoneNumber = findViewById(R.id.et_cell_phone_number);
        etLocation = findViewById(R.id.et_location);
        etDetailed = findViewById(R.id.et_detailed);
        btLocationInflate = findViewById(R.id.bt_location_inflate);
    }

    @Override
    public void initData() {

        Intent intent = getIntent();
        token = intent.getStringExtra("token");

        tbLocation.setTitleBacKGround(Color.WHITE);
        tbLocation.setCenterText("新建收货地址",18,Color.BLACK);
        tbLocation.setLeftImg(R.drawable.left);
        tbLocation.setTitleClickLisner(new TitleBar.TitleClickLisner() {
            @Override
            public void LeftClick() {
                finish();
            }

            @Override
            public void RightClick() {

            }

            @Override
            public void CenterClick() {

            }
        });

        //新建地址上传到服务器
        btLocationInflate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Consignee = etConsignee.getText().toString().trim();
                String CellPhoneNumber = etCellPhoneNumber.getText().toString().trim();
                String Location = etLocation.getText().toString().trim();
                String Detailed = etDetailed.getText().toString().trim();

                addressPresenter = new AddressPresenter(CellPhoneNumber,"updatePhone",token);
                addressPresenter.attachPostView(LocationActivity.this);
                addressPresenter.getCipherTextData();

                locationPresenter = new LocationPresenter(Location + Detailed,"updateAddress",token);
                locationPresenter.attachPostView(LocationActivity.this);
                locationPresenter.getCipherTextData();

            }
        });
    }

    @Override
    public void onPostDataSucess(AddressBean data) {
        if (data.getCode().equals("200")){
            etConsignee.setText("");
            etCellPhoneNumber.setText("");
            etLocation.setText("");
            etDetailed.setText("");
            Toast.makeText(this, "新建成功", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void onPostDataFailed(String ErrorMsg) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (addressPresenter != null && locationPresenter != null){
            addressPresenter.detachView();
            locationPresenter.detachView();
        }
    }
}