package com.example.shopmall.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.common.TitleBar;
import com.example.framework.base.BaseActivity;
import com.example.framework.base.IPostBaseView;
import com.example.framework.manager.ShoppingManager;
import com.example.framework.manager.UserManager;
import com.example.shopmall.R;
import com.example.shopmall.bean.AddressBean;
import com.example.shopmall.presenter.AddressPresenter;
import com.example.shopmall.presenter.LocationPresenter;

public class LocationActivity extends BaseActivity implements IPostBaseView<AddressBean> {

    private TitleBar tbLocation;
    private EditText etConsignee;
    private EditText etCellPhoneNumber;
    private EditText etLocation;
    private EditText etDetailed;
    private Button btLocationInflate;

    private String token;

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

        btLocationInflate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Consignee = etConsignee.getText().toString().trim();
                String CellPhoneNumber = etCellPhoneNumber.getText().toString().trim();
                String Location = etLocation.getText().toString().trim();
                String Detailed = etDetailed.getText().toString().trim();

                AddressPresenter addressPresenter = new AddressPresenter(CellPhoneNumber,"updatePhone",token);
                addressPresenter.attachPostView(LocationActivity.this);
                addressPresenter.getCipherTextData();

                LocationPresenter locationPresenter = new LocationPresenter(Location + Detailed,"updateAddress",token);
                locationPresenter.attachPostView(LocationActivity.this);
                locationPresenter.getCipherTextData();

            }
        });
    }

    @Override
    public void onPostDataSucess(AddressBean data) {
        if (data.getCode().equals("200")){
            Toast.makeText(this, "新建成功", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void onPostDataFailed(String ErrorMsg) {

    }
}