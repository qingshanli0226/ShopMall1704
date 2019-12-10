package com.example.buy.activity;

import android.content.Intent;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buy.R;
import com.example.buy.databeans.GetOrderBean;
import com.example.buy.presenter.GetPayOrderPresenter;
import com.example.buy.presenter.GetWaitOrderPresenter;
import com.example.common.IntentUtil;
import com.example.framework.base.BaseNetConnectActivity;
import com.example.framework.base.BaseRecyclerAdapter;
import com.example.framework.base.BaseViewHolder;
import com.example.framework.listener.OnOrderListener;
import com.example.framework.manager.OrderManager;
import com.example.framework.port.IPresenter;

import java.util.ArrayList;
/**
 * 订单详情页
 * */
public class OrderActivity extends BaseNetConnectActivity {

    public final static String ALL="全部订单";
    public final static String WAIT_PAY="待支付订单";
    public final static String WAIT_SEND="待发货订单";

    //网络请求码 全部  待支付 待发货 第二次
    public final static int CODE_ALL=200;
    public final static int CODE_PAY=201;
    public final static int CODE_WAIT=202;
    public final static int CODE_All_TWO=203;

    private RecyclerView recyclerView;
    private TextView showOrderType;

    ArrayList<GetOrderBean> list = new ArrayList<>();

    IPresenter payOrder;
    IPresenter watiOrder;

    @Override
    public int getLayoutId() {
        return R.layout.activity_showorder;
    }

    @Override
    public int getRelativeLayout() {
        return R.id.orderRel;
    }

    @Override
    public void init() {
        recyclerView=findViewById(R.id.recyclerView);
        showOrderType=findViewById(R.id.showOrderType);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new BaseRecyclerAdapter<GetOrderBean>(R.layout.item_order, list) {
            @Override
            public void onBind(BaseViewHolder holder, int position) {
                ((TextView)holder.getView(R.id.orderTitle)).setText(list.get(position).getSubject());
                ((TextView)holder.getView(R.id.orderNum)).setText(list.get(position).getBody());
                ((TextView)holder.getView(R.id.orderMoney)).setText(list.get(position).getTotalPrice());
            }
        });

    }

    @Override
    public void initDate() {
        payOrder=new GetPayOrderPresenter();
        watiOrder=new GetWaitOrderPresenter();
        //http://49.233.93.155:8080  updateMoney  money=1333
        //获取传递过来的数据,然后进行订单类型的显示

        payOrder.attachView(this);
        watiOrder.attachView(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        //获取到要展示的订单的类型
        Intent intent=getIntent();
        String type = intent.getStringExtra(IntentUtil.ORDER_SHOW);
        showOrderType.setText(type);
        switch (type){
            case ALL:
                //先请求待支付,然后请求待发货
                payOrder.doHttpGetRequest(CODE_ALL);
                break;
            case WAIT_PAY:
                payOrder.doHttpGetRequest(CODE_PAY);
                break;
            case WAIT_SEND:
                watiOrder.doHttpGetRequest(CODE_WAIT);
                break;
        }
    }

    @Override
    public void onRequestSuccess(int requestCode, Object data) {
        super.onRequestSuccess(requestCode, data);
        switch (requestCode){
            case CODE_ALL:
                list.add((GetOrderBean) data);
                watiOrder.doHttpPostRequest(CODE_All_TWO);
                watiOrder.doHttpPostRequest(CODE_ALL);
                break;
            case CODE_PAY:
                list.clear();
                list.add((GetOrderBean) data);
                OrderManager.getInstance().updatePayOrederNum(list.size());
                break;
            case CODE_WAIT:
                list.clear();
                list.add((GetOrderBean) data);
                OrderManager.getInstance().updateWaitOrederNum(list.size());
                break;
            case CODE_All_TWO:
                list.add((GetOrderBean) data);
                OrderManager.getInstance().updateAllOrederNum(list.size());
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disPreseter(payOrder,watiOrder);
    }
    private void disPreseter(IPresenter... iPresenter){
        for (int i=0;i<iPresenter.length;i++){
            if (iPresenter[i]!=null){
                iPresenter[i].detachView();
            }
        }
    }
}
