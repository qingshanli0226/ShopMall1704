package com.example.shopmall.activity;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.common.TitleBar;
import com.example.framework.base.BaseActivity;
import com.example.framework.bean.AddressBarBean;
import com.example.shopmall.R;

public class LocationActivity extends BaseActivity {

    private TitleBar tbLocation;
    private EditText etConsignee;
    private EditText etCellPhoneNumber;
    private EditText etLocation;
    private EditText etDetailed;
    private Button btLocationInflate;

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
            }
        });
    }
}