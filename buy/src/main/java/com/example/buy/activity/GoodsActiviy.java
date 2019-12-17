package com.example.buy.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.buy.R;
import com.example.buy.databeans.GetCartBean;
import com.example.buy.databeans.GoodsBean;
import com.example.buy.databeans.OkBean;
import com.example.buy.presenter.GetCartPresenter;
import com.example.framework.listener.OnShopCartListener;
import com.example.framework.manager.AccountManager;
import com.example.buy.CartManager;
import com.example.buy.presenter.PostAddCartPresenter;
import com.example.common.HomeBean;
import com.example.common.IntentUtil;
import com.example.common.TypeBean;
import com.example.framework.base.BaseNetConnectActivity;
import com.example.framework.port.IPresenter;
import com.example.net.AppNetConfig;

import java.util.ArrayList;

/**
 * 商品详情页
 */

public class GoodsActiviy extends BaseNetConnectActivity implements View.OnClickListener {

    public final int ADD_GOODS = 100;
    public final int CART_GOODS = 200;

    private Button goPayBut;
    private Button joinCartBut;
    private ImageView collectBut;
    private ImageView goodsImage;
    private ImageView cartBut;
    private ImageView beiImgage;
    private TextView goodsTitle;
    private WebView webView;
    private TextView goodsOldPrice;
    private TextView goodsNewPrice;
    private TextView redNum;

    //初始位置   控制点  结束位置   x,y
    private int[] startLoaction = new int[2];
    private int[] controlLoaction = new int[2];
    private int[] endLoaction = new int[2];
    //加入购物车请求
    private IPresenter addCartPresenter;
    private IPresenter sendCartPresenter;
    //动画
    private AnimatorSet animatorSet;
    private PopupWindow popupWindow;
    //购物车数量监听
    private OnShopCartListener onShopCartListener;

    //请求对象  商品详情
    private GoodsBean goods;
    private HomeBean.ResultBean.SeckillInfoBean.ListBean goodsBeanOne;
    private TypeBean.ResultBean.HotProductListBean goodsBeanTwo;
    //暂存购物车数量
    private int cartNum;

    @Override
    protected void onStart() {
        super.onStart();
//      展示收到的两种商品数据
        Intent intent = getIntent();
        try {
            goodsBeanOne = intent.getParcelableExtra(IntentUtil.SHOW_GOOD);
            if (goodsBeanOne != null) {
                goods = new GoodsBean(
                        goodsBeanOne.getProduct_id(),
                        1,
                        goodsBeanOne.getName(),
                        goodsBeanOne.getFigure(),
                        goodsBeanOne.getCover_price()
                );

            }
        } catch (Exception e) {
            try {
                goodsBeanTwo = intent.getParcelableExtra(IntentUtil.SHOW_GOOD);
                if (goodsBeanTwo != null) {
                    goods = new GoodsBean(
                            goodsBeanTwo.getProduct_id(),
                            1,
                            goodsBeanTwo.getName(),
                            goodsBeanTwo.getFigure(),
                            goodsBeanTwo.getCover_price()
                    );
                }
            } catch (Exception e2) {
                goods = intent.getParcelableExtra(IntentUtil.SHOW_GOOD);
            }
        }
        goodsTitle.setText(goods.getProductName());
        goodsOldPrice.setText("");
        goodsNewPrice.setText(goods.getProductPrice());
        Glide.with(this)
                .load(AppNetConfig.BASE_URl_IMAGE + goods.getUrl())
                .into(beiImgage);
        Glide.with(this)
                .load(AppNetConfig.BASE_URl_IMAGE + goods.getUrl())
                .into(goodsImage);

        webView.loadUrl("http://www.baidu.com");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return true;
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (AccountManager.getInstance().isLogin()){
            if (v.getId() == goPayBut.getId()) {
                //携带商品数据跳转到支付页  并结束页面
                Intent intent = new Intent(this, PayActivity.class);
                ArrayList<GoodsBean> goodList = new ArrayList<>();
                goodList.add(goods);
                intent.putExtra(IntentUtil.ORDERS, goodList);
                startActivity(intent);
                finish();
            } else if (v.getId() == joinCartBut.getId()) {
                //加入购物车  弹窗
                setBackAlph(0.7f);
                popupWindow.showAtLocation(findViewById(R.id.goodsRel), Gravity.BOTTOM, 0, 0);
            } else if (v.getId() == collectBut.getId()) {
                //本地sp存储收藏的商品的信息
                Toast.makeText(this, "已加入收藏", Toast.LENGTH_SHORT).show();
            } else if (v.getId() == cartBut.getId()) {
                //点击购物车
                Intent intent = new Intent(this, ShoppCartActivity.class);
                intent.putExtra(IntentUtil.GOODS,goods.getProductId());
                startActivity(intent);
            }
        }else {
            Toast.makeText(this,getResources().getString(R.string.buyLoginFirst),Toast.LENGTH_SHORT).show();
        }
    }

    //加入购物车动画
    private void setAnimator() {
        //保证只有一个动画
        if (animatorSet != null) {

        } else {
            animatorSet = new AnimatorSet();

            ObjectAnimator scaleX = ObjectAnimator.ofFloat(beiImgage, "scaleX", 1, 0.1f);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(beiImgage, "scaleY", 1, 0.1f);
            //缩放后加贝塞尔曲线
            animatorSet.play(scaleX).with(scaleY).before(getBeiAnimator());
            animatorSet.setDuration(500);
            animatorSet.setInterpolator(new AccelerateInterpolator());
            //动画开始不能点击加入购物车  完成后才可以点击
            animatorSet.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    joinCartBut.setClickable(false);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    //隐藏,并还原imageview
                    beiImgage.setVisibility(View.GONE);
                    beiImgage.setScaleX(0);
                    beiImgage.setScaleY(0);
                    beiImgage.setTranslationX(0);
                    beiImgage.setTranslationY(0);
                    //更新红点
                    CartManager.getInstance().updataCartNum(cartNum);
                    //购物车图标摇一摇动画
                    ObjectAnimator carAnimator = ObjectAnimator.ofFloat(cartBut, "rotation", 0, 30, -30, 0);
                    carAnimator.setDuration(500);
                    carAnimator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            joinCartBut.setClickable(true);
                            animatorSet = null;
                        }
                    });
                    carAnimator.start();
                }
            });
            animatorSet.start();
        }
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        //请求不论成功 开始执行动画
        popupWindow.dismiss();
    }

    @Override
    public void showLoading() {

    }

    //请求数据成功
    @Override
    public void onRequestSuccess(int requestCode,Object data) {
        super.onRequestSuccess(requestCode,data);
        switch (requestCode){
            case ADD_GOODS:
                if (((OkBean) data).getCode().equals(AppNetConfig.CODE_OK)) {
                    //提示用户加入购物车完成   增加小红点
                    //只能通过再次网络请求
//            setRed(( Integer.valueOf(redNum.getText().toString()) + 1));
                    sendCartPresenter = new GetCartPresenter();
                    sendCartPresenter.attachView(GoodsActiviy.this);
                    sendCartPresenter.doHttpGetRequest(CART_GOODS);
                }
                break;
            case CART_GOODS:
                if (((GetCartBean) data).getCode().equals(AppNetConfig.CODE_OK)) {
                    cartNum=((GetCartBean) data).getResult().size();
                    setAnimator();
                }
                break;
        }
    }

    //设置弹窗
    private void setPopuWindow() {
        //弹出窗体
        popupWindow = new PopupWindow(this);
        //设置点击外部消失
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        //动画样式
        popupWindow.setAnimationStyle(R.style.popAnimation);
        //背景色
        popupWindow.setBackgroundDrawable(new ColorDrawable(0));

        View view = LayoutInflater.from(this).inflate(R.layout.popuwindow_goods, null);

        TextView popuDel = view.findViewById(R.id.popuDel);
        TextView popuAdd = view.findViewById(R.id.popuAdd);
        TextView popuNum = view.findViewById(R.id.popuNum);
        Button popuSure = view.findViewById(R.id.popuSure);
        popuNum.setText(1 + "");
        //增加
        popuAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer num = Integer.valueOf(popuNum.getText().toString());
                popuNum.setText((num + 1) + "");
            }
        });
        //减少
        popuDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer num = Integer.valueOf(popuNum.getText().toString());
                if (num > 1) {
                    popuNum.setText((num - 1) + "");
                }
            }
        });
        //确定按钮
        popuSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AccountManager.getInstance().isLogin()){
                    //强制将Float转为int
                    goods.setProductNum(((int) ((float) Float.valueOf(popuNum.getText().toString()))));
                    addCartPresenter = new PostAddCartPresenter(goods);
                    addCartPresenter.attachView(GoodsActiviy.this);
                    addCartPresenter.doHttpPostJSONRequest(ADD_GOODS);
                }else {
                    Toast.makeText(GoodsActiviy.this,getResources().getString(R.string.buyLoginFirst),Toast.LENGTH_SHORT).show();
                }
            }
        });
        //消失监听
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackAlph(1);
            }
        });

        popupWindow.setContentView(view);
        popupWindow.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(600);

    }

    //设置背景透明度
    private void setBackAlph(float num) {
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.alpha = num;
        getWindow().setAttributes(attributes);
    }

    //返回贝塞尔曲线
    private ObjectAnimator getBeiAnimator() {
        //每次都重新设置起始点 控制点 结束点  并绘制路线
        beiImgage.setVisibility(View.VISIBLE);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int height = metrics.heightPixels;
        int width = metrics.widthPixels;
        startLoaction[0] = 0;
        startLoaction[1] = 0;

        controlLoaction[0] = -(width / 2);
        controlLoaction[1] = height / 2;

        endLoaction[0] = -(beiImgage.getWidth() / 2) + (cartBut.getWidth() * 2);
        endLoaction[1] = (height - (beiImgage.getHeight() / 2));
//        endLoaction[0]=width;
//        endLoaction[1]=height;

        Path path = new Path();
        //控制初始点
        path.moveTo(startLoaction[0], startLoaction[1]);
        path.quadTo(controlLoaction[0], controlLoaction[1], endLoaction[0], endLoaction[1]);
        return ObjectAnimator.ofFloat(beiImgage, "translationX", "translationY", path);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_goods;
    }

    @Override
    public int getRelativeLayout() {
        return R.id.goodsRel;
    }

    @Override
    public void init() {
        super.init();
        goPayBut = findViewById(R.id.goPayBut);
        joinCartBut = findViewById(R.id.joinCartBut);
        collectBut = findViewById(R.id.collectBut);
        cartBut = findViewById(R.id.cartBut);
        goodsImage = findViewById(R.id.goodsImage);
        beiImgage = findViewById(R.id.beiImgage);
        goodsTitle = findViewById(R.id.goodsTitle);
        webView = findViewById(R.id.webView);
        goodsOldPrice = findViewById(R.id.goodsOldPrice);
        goodsNewPrice = findViewById(R.id.goodsNewPrice);
        redNum = findViewById(R.id.redNum);

        collectBut.setOnClickListener(this);
        joinCartBut.setOnClickListener(this);
        goPayBut.setOnClickListener(this);
        goodsImage.setOnClickListener(this);
        cartBut.setOnClickListener(this);
        beiImgage.setOnClickListener(this);
    }

    @Override
    public void initDate() {
        setPopuWindow();
        onShopCartListener = new OnShopCartListener() {
            @Override
            public void shopCartNumChange(int num) {
                cartNum=num;
                setRed(num);
            }
        };
        CartManager.getInstance().registerListener(onShopCartListener);
        setRed(CartManager.getInstance().getCartNum());

    }

    @Override
    protected void onDestroy() {
        if (addCartPresenter != null) {
            addCartPresenter.detachView();
        }
        CartManager.getInstance().unregister(onShopCartListener);
        super.onDestroy();
    }

    private void setRed(int num) {
        redNum.setText(num + "");
        if (Integer.valueOf(redNum.getText().toString()) == 0) {
            redNum.setVisibility(View.GONE);
        } else {
            redNum.setVisibility(View.VISIBLE);
        }
    }

}
