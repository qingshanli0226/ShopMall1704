package com.example.buy;

import android.content.Intent;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.example.buy.activity.GoodsActiviy;
import com.example.buy.activity.PayActivity;
import com.example.buy.databeans.CheckGoodsData;
import com.example.buy.databeans.GetCartBean;
import com.example.buy.databeans.GoodsBean;
import com.example.buy.databeans.OkBean;
import com.example.buy.presenter.PostVerifyOnePresenter;
import com.example.common.code.Constant;
import com.example.common.view.LogoutDialog;
import com.example.common.view.MyToolBar;
import com.example.buy.presenter.PostDelteGoodsPresenter;
import com.example.framework.manager.AccountManager;

import com.example.buy.presenter.GetCartPresenter;
import com.example.buy.presenter.PostUpDateCartPresenter;
import com.example.common.utils.IntentUtil;
import com.example.framework.base.BaseNetConnectFragment;
import com.example.framework.base.BaseRecyclerAdapter;
import com.example.framework.base.BaseViewHolder;
import com.example.framework.port.IPresenter;

import java.util.ArrayList;

import com.example.net.AppNetConfig;

public class ShopCartFragment extends BaseNetConnectFragment{
    //请求购物车  刷新单个   删除
    private final int CART_GOODS = 100;
    private final int UPDATE_GOODS = 101;
    private final int DEL_CODE = 102;
    private final int VERIFY_CODE = 103;

    private Button buyBut;
    private RecyclerView recyclerView;
    private CheckBox checkAll;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView nullGoods;
    private TextView cartMoney;
    //TODO 自定义ToolBar
    private MyToolBar myToolBar;


    //数据和被选择
    private ArrayList<GoodsBean> waitDelList = new ArrayList<>();
    private ArrayList<GoodsBean> list = new ArrayList<>();

    //购物车数据获取  购物车更新
    private IPresenter sendCartPresenter;
    private IPresenter upDatePresenter;
    private IPresenter deleteGoodsPresenter;
    private IPresenter verifyOnePresenter;

    //删除与支付状态   全选状态
    private boolean delStatus = false;
    private boolean checkStatus=false;

    private int index;

    @Override
    public void init(View view) {
        super.init(view);
        buyBut = view.findViewById(R.id.buyBut);
        recyclerView = view.findViewById(R.id.recyclerView);
        checkAll = view.findViewById(R.id.checkAll);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        nullGoods = view.findViewById(R.id.nullGoods);
        cartMoney = view.findViewById(R.id.cartMoney);
        myToolBar = view.findViewById(R.id.my_toolbar);

        myToolBar.init(Constant.BUY_MESSAGE_STYLE);
        myToolBar.setBackground(getResources().getDrawable(R.drawable.toolbar_style));
        //立即购买
        buyBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AccountManager.getInstance().isLogin()){
                    if (v.getId() == buyBut.getId()) {
                        if (delStatus) {
                            //删除
                            for (int i = 0; i < CartManager.getInstance().getChecks().size(); i++) {
                                if (CartManager.getInstance().getChecks().get(i).isSelect()) {
                                    waitDelList.add(list.get(i));
                                }
                            }
                            deleteGoods();
                        } else {
                            //支付跳转
                            ArrayList<GoodsBean> goods = new ArrayList<>();
                            for (int i = 0; i < CartManager.getInstance().getChecks().size(); i++) {
                                if (CartManager.getInstance().getChecks().get(i).isSelect()) {
                                    goods.add(list.get(i));
                                }
                            }
                            Intent intent = new Intent(getContext(), PayActivity.class);
                            intent.putExtra(IntentUtil.ORDERS, goods);
                            startActivity(intent);
                        }
                    }
                }else {
                    Toast.makeText(getContext(),getResources().getString(R.string.buyLoginFirst),Toast.LENGTH_SHORT).show();
                }
            }
        });

        myToolBar.getBuy_compile().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AccountManager.getInstance().isLogin()){
                    //编辑
                    if (delStatus){
                        myToolBar.getBuy_compile().setText("编辑");
                        buyBut.setText( getResources().getString(R.string.buyNow));
                        delStatus=false;
                    }else {
                        myToolBar.getBuy_compile().setText("完成");
                        buyBut.setText( getResources().getString(R.string.buyDelete));
                        delStatus=true;
                    }
                }else {
                    Toast.makeText(getContext(),getResources().getString(R.string.buyLoginFirst),Toast.LENGTH_SHORT).show();
                }
            }
        });

        myToolBar.getBuy_menu().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出消息和分享

            }
        });

        //设置recyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new BaseRecyclerAdapter<GoodsBean>(R.layout.item_goods, list) {
            @Override
            public void onBind(BaseViewHolder holder, int position) {
                holder.getTextView(R.id.itemTitle,list.get(position).getProductName());
                holder.getTextView(R.id.itemPrice,list.get(position).getProductPrice());
                CheckBox itemCheck = (CheckBox) holder.getView(R.id.itemCheck);
                //数量
                holder.getTextView(R.id.itemNum, list.get(position).getProductNum() + "")
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //修改数量对话框
                                LogoutDialog logoutDialog =new LogoutDialog(getContext());
                                logoutDialog.init(R.layout.dialog_num);
                                logoutDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                logoutDialog.setCanceledOnTouchOutside(false);

                                EditText buyDialogNum=logoutDialog.findViewById(R.id.buyDialogNum);
                                buyDialogNum.setText(list.get(position).getProductNum()+"");
                                buyDialogNum.setInputType(InputType.TYPE_CLASS_NUMBER);
                                buyDialogNum.requestFocus();
                                logoutDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                                //取消
                                logoutDialog.findViewById(R.id.buyDialogCancel).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        logoutDialog.dismiss();
                                    }
                                });
                                //确定
                                logoutDialog.findViewById(R.id.buyDialogSure).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        logoutDialog.dismiss();
                                        //数量超出处理
                                        int num=0;
                                        try {
                                            num=Integer.valueOf(buyDialogNum.getText().toString());
                                        }catch (NumberFormatException e){
                                            num=999;
                                        }
                                        upDateNum(position,num);
                                    }
                                });
                                logoutDialog.show();
                            }
                        });
                //点击图片跳转
                holder.getImageView(R.id.itemImg,
                        AppNetConfig.BASE_URl_IMAGE + list.get(position).getUrl())
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getContext(), GoodsActiviy.class);
                                intent.putExtra(IntentUtil.SHOW_GOOD,list.get(position));
                                getContext().startActivity(intent);
                            }
                        });

                //被选中
                itemCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (CartManager.getInstance().getChecks().size() > position) {
                            CartManager.getInstance().getChecks().set(position,new CheckGoodsData(isChecked,list.get(position).getProductId()));
                            CartManager.getInstance().setChecks(CartManager.getInstance().getChecks());
                            //判断是否需要全选
                            judgeCheckAll();
                        }
                    }
                });

                //减按钮
                holder.getView(R.id.itemDelBut).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (list.get(position).getProductNum() > 1) {
                            upDateNum(position, list.get(position).getProductNum() - 1);
                        }
                    }
                });
                //加按钮
                holder.getView(R.id.itemAddBut).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        upDateNum(position, list.get(position).getProductNum() + 1);
                    }
                });
                //必须要放到设置监听后面
                for (int i=0;i<CartManager.getInstance().getChecks().size();i++) {
                    if (i==position){
                        itemCheck.setChecked(CartManager.getInstance().getChecks().get(i).isSelect());
                    }
                }
            }
        });
        //下拉刷新
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (AccountManager.getInstance().isLogin()){
                    //下拉刷新购物车数据
                    sendCartPresenter.doHttpGetRequest(CART_GOODS);
                }else {
                    Toast.makeText(getContext(),getResources().getString(R.string.buyLoginFirst),Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
        //全选监听
        checkAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                /**
                 * 状态true 选true   不动作
                 * 状态false 选true   全部选中
                 * 状态true 选false    全部取消
                 * 状态false 选false   没有被全选那不可能取消全选  不存在
                 * * */
                if (isChecked) {
                    if (!checkStatus) {
                        for (CheckGoodsData i:CartManager.getInstance().getChecks()) {
                            i.setSelect(true);
                        }
                        recyclerView.getAdapter().notifyDataSetChanged();
                    }
                } else {
                    if (checkStatus) {
                        for (CheckGoodsData i:CartManager.getInstance().getChecks()) {
                            i.setSelect(false);
                        }
                        recyclerView.getAdapter().notifyDataSetChanged();
                    }
                }
                //更新价格
                setAllMoney();
            }
        });

        sendCartPresenter = new GetCartPresenter();
        sendCartPresenter.attachView(this);
    }

    @Override
    public void initDate() {
        setAllMoney();
        showBar();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (AccountManager.getInstance().isLogin()){
            CartManager.getInstance().notifyChecks();
            list.clear();
            list.addAll(CartManager.getInstance().getListGoods());
            recyclerView.getAdapter().notifyDataSetChanged();
            sendCartPresenter.doHttpGetRequest(CART_GOODS);
        }else {
            buyBut.setClickable(false);
        }
    }

    @Override
    public void onRequestSuccess(int requestCode, Object data) {
        super.onRequestSuccess(requestCode, data);
        switch (requestCode) {
            case CART_GOODS:
                if (((GetCartBean) data).getCode().equals(AppNetConfig.CODE_OK)) {
                    /**
                     * 请求购物车数据情况:  第一次请求  下拉刷新=onResume刷新  刷新库存
                     *
                     * 判断第一次请求到数据
                     * 第一次请求到数据: manager中数据为空 --> 存入数据
                     *
                     * 刷新数据: 判断物品是否变不变: 本地数据比较 --> n次  此处判断选中 -->数据来源:manager
                     * 物品不变 -->库存更新                                刷新fragment的选中集合
                     * 物品改变 -->物品全变  物品单个变
                     */
                    if (list.isEmpty()){
                        list.addAll(((GetCartBean) data).getResult());
                        CartManager.getInstance().setListGoods(list);
                        if (CartManager.getInstance().getChecks().isEmpty()){
                            for (int i = 0; i < list.size(); i++) {
                                CartManager.getInstance().addCheck(false,list.get(i).getProductId());
                            }
                        }
                    }else {
                        list.clear();
                        list.addAll(((GetCartBean) data).getResult());
                        CartManager.getInstance().setListGoods(list);
                        CartManager.getInstance().notifyChecks();
                    }
                    recyclerView.getAdapter().notifyDataSetChanged();
                    nullGoods.setVisibility(list.isEmpty()?View.VISIBLE:View.GONE);
                    recyclerView.setVisibility(list.isEmpty()?View.GONE:View.VISIBLE);
                    judgeCheckAll();
                }
                break;
            case UPDATE_GOODS:
                    if (((OkBean) data).getCode().equals(AppNetConfig.CODE_OK)) {
                        //更新成功
                        sendCartPresenter.doHttpGetRequest(CART_GOODS);
                    }
                break;
            case DEL_CODE:
                if (((OkBean) data).getCode().equals(AppNetConfig.CODE_OK)) {
                    waitDelList.remove(0);
                    deleteGoods();
                } else {
                    Toast.makeText(getContext(), waitDelList.get(0).getProductName() + "删除失败", Toast.LENGTH_SHORT).show();
                }
                break;
            case VERIFY_CODE:
                if (((OkBean) data).getCode().equals(AppNetConfig.CODE_OK)){
                    //吐司提示
                    //数量超出处理
                    int num=0;
                    try {
                        num=Integer.valueOf(((OkBean)data).getResult());
                    }catch (NumberFormatException e){
                        num=999;
                    }
                    if (list.get(index).getProductNum()>num){
                        //提示库存不足
                        Toast.makeText(getContext(),"库存不足",Toast.LENGTH_SHORT).show();
                    }
                    list.get(index).setProductNum(num);
                    upDatePresenter = new PostUpDateCartPresenter(list.get(index));
                    upDatePresenter.attachView(ShopCartFragment.this);
                    upDatePresenter.doHttpPostJSONRequest(UPDATE_GOODS);
                }
                break;
        }
    }

    //判断全选
    private synchronized void judgeCheckAll() {
         /**  默认状态为全选
          * 每选择一个判断是否全部选中
          *  有一个为false  全选状态为false  取消全选
         **/
         if (list.isEmpty()){
             checkAll.setEnabled(false);
             buyBut.setEnabled(false);
             myToolBar.getBuy_compile().setEnabled(false);
         }else {
             checkAll.setEnabled(true);
             myToolBar.getBuy_compile().setEnabled(true);
             buyBut.setEnabled(true);
         }
        checkStatus = true;
        for (CheckGoodsData i : CartManager.getInstance().getChecks()) {
            if (!i.isSelect()) {
                checkStatus = false;
                break;
            }
        }
        if ( CartManager.getInstance().getChecks().isEmpty()){
            checkStatus=false;
        }
        if (checkAll.isChecked()!=checkStatus){
            checkAll.setChecked(checkStatus);
        }
        setAllMoney();
    }

    //更新单个数量
    private void upDateNum(int goodsPosition, int goodsNum) {
        index =goodsPosition;
        list.get(goodsPosition).setProductNum(goodsNum);
        recyclerView.getAdapter().notifyDataSetChanged();
        verifyOnePresenter=new PostVerifyOnePresenter(list.get(goodsPosition).getProductId(),goodsNum);
        verifyOnePresenter.attachView(this);
        verifyOnePresenter.doHttpPostRequest(VERIFY_CODE);
    }

    //获取总价
    private void setAllMoney() {
        setBuyButStyle();
        float sum = 0;
        for (int i = 0; i < list.size(); i++) {
            if (CartManager.getInstance().getChecks().get(i).isSelect()) {
                sum += (Float.valueOf(list.get(i).getProductPrice()) * list.get(i).getProductNum());
            }
        }
        cartMoney.setText("总计:" + sum + "元");
    }

    //删除单个
    private void deleteGoods() {
        if (waitDelList.size() > 0) {
            deleteGoodsPresenter = new PostDelteGoodsPresenter(waitDelList.get(0));
            deleteGoodsPresenter.attachView(this);
            deleteGoodsPresenter.doHttpPostJSONRequest(DEL_CODE);
        } else {
            buyBut.setText(getResources().getString(R.string.buyNow));
            myToolBar.getBuy_compile().setText("编辑");
            delStatus = false;
            checkAll.setChecked(false);
            sendCartPresenter.doHttpGetRequest(CART_GOODS);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_shopcart;
    }

    @Override
    public int getRelativeLayout() {
        return R.id.shopCartRel;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disPreseter(sendCartPresenter, upDatePresenter,verifyOnePresenter,deleteGoodsPresenter);
    }

    private void disPreseter(IPresenter... iPresenter) {
        for (int i = 0; i < iPresenter.length; i++) {
            if (iPresenter[i] != null) {
                iPresenter[i].detachView();
            }
        }
    }

    private void showBar(){
        myToolBar.getBuy_compile().setVisibility(View.VISIBLE);
        myToolBar.getBuy_menu().setVisibility(View.VISIBLE);
        myToolBar.getBuy_message_icon().setVisibility(View.VISIBLE);
        myToolBar.getBuy_message_title().setVisibility(View.VISIBLE);
        myToolBar.getMessage_menu().setVisibility(View.GONE);
        myToolBar.getMessage_back().setVisibility(View.GONE);
        myToolBar.getMessage_calendar().setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showLoading() {

    }
    //立即购买的点击
    private void setBuyButStyle(){
        for (CheckGoodsData i:CartManager.getInstance().getChecks()){
            if (i.isSelect()){
                buyBut.setEnabled(true);
                return;
            }
        }
        buyBut.setEnabled(false);
    }

    @Override
    public void onConnected() {
        myToolBar.isConnection(true);
    }

    @Override
    public void onDisConnected() {
        myToolBar.isConnection(false);
    }
}
