package com.example.buy;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.buy.databeans.GoodsBean;
import com.example.buy.databeans.OkBean;
import com.example.buy.databeans.ShowGoodsBean;
import com.example.buy.presenter.PostAddCartPresenter;
import com.example.buy.presenter.PostVerifyOnePresenter;
import com.example.common.IntentUtil;
import com.example.framework.base.BaseNetConnectActivity;
import com.example.framework.port.IPresenter;
import com.example.net.AppNetConfig;
import com.google.gson.Gson;

/**
 * 商品详情页
 */

public class GoodsActiviy extends BaseNetConnectActivity implements View.OnClickListener {

    private static final int ADD_CART = 300;
    private static final int VERIFY_ONE = 301;

    private Button goPayBut;
    private Button joinCartBut;
    private ImageView collectBut;
    private ImageView goodsImage;
    private ImageView cartBut;
    private ImageView beiImgage;
    PopupWindow popupWindow;

    //初始位置   控制点  结束位置   x,y
    int[] startLoaction = new int[2];
    int[] controlLoaction = new int[2];
    int[] endLoaction = new int[2];
    //加入购物车请求  库存请求  商品信息请求  立即购买转支付页面
    IPresenter addCartPresenter;
    IPresenter verifyOnePresenter;

    //需要一个商品信息的对象
    GoodsBean goods;
    AnimatorSet animatorSet;

    @Override
    protected void onStart() {
        super.onStart();

//        获取到用户点击的商品     发起请求,获取商品数据
        Intent intent = getIntent();
        String str = intent.getStringExtra(IntentUtil.SHOW_GOOD);
        ShowGoodsBean showGoodsBean = new Gson().fromJson(str, ShowGoodsBean.class);
        if (showGoodsBean==null){
            goods=new GoodsBean(null,0,null,null);
        }else {
            goods = new GoodsBean(
                    showGoodsBean.getProduct_id(),
                    1,
                    showGoodsBean.getName(),
                    showGoodsBean.getFigure());
        }
        verifyNumber();
    }

    @Override
    public void onClick(View v) {
        //加入和购买 两个按钮点击之前,都要进行库存检验

        if (v.getId() == goPayBut.getId()) {
            //携带商品数据跳转到支付页  并结束页面
            startActivity(PayActivity.class, null);
            finish();
        } else if (v.getId() == joinCartBut.getId()) {
            //加入购物车  弹窗
//            setAnimator();
//            verifyNumber();
            popupWindow.showAtLocation(findViewById(R.id.goodsRel), Gravity.BOTTOM, 0, 0);

        } else if (v.getId() == collectBut.getId()) {
            //本地sp存储收藏的商品的信息

        }
    }
    //加入购物车动画
    private void setAnimator() {

        beiImgage.setVisibility(View.VISIBLE);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int height = metrics.heightPixels;
        int width = metrics.widthPixels;
        startLoaction[0]=0;
        startLoaction[1]=0;

        controlLoaction[0]=0;
        controlLoaction[1]=height/2;

        endLoaction[0]=-(beiImgage.getWidth()/2);
        endLoaction[1]=(height-(beiImgage.getHeight()/2));

        animatorSet = new AnimatorSet();

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(beiImgage, "scaleX", 1, 0.1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(beiImgage, "scaleY", 1, 0.1f);


        Path path = new Path();
        //控制初始点
        path.moveTo(startLoaction[0], startLoaction[1]);
        path.quadTo(controlLoaction[0], controlLoaction[1], endLoaction[0], endLoaction[1]);

        ObjectAnimator animator = ObjectAnimator.ofFloat(beiImgage, "translationX", "translationY", path);

        animatorSet.play(scaleX).with(scaleY).before(animator);
        animatorSet.setDuration(400);
        animatorSet.setInterpolator(new AccelerateInterpolator());

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                beiImgage.setVisibility(View.GONE);
                beiImgage.setScaleX(0);
                beiImgage.setScaleY(0);
                beiImgage.setTranslationX(0);
                beiImgage.setTranslationY(0);
                ObjectAnimator car = ObjectAnimator.ofFloat(cartBut, "rotation", 0,30,-30,0);
                car.setDuration(1000);
                car.start();
            }
        });

        animatorSet.start();

    }

    @Override
    public void onRequestSuccess(int requestCode, Object data) {
        super.onRequestSuccess(requestCode, data);
        switch (requestCode) {
            case ADD_CART:

                if (((OkBean) data).getCode().equals(AppNetConfig.CODE_OK)) {
                    //提示用户加入购物车完成
                    Toast.makeText(this,getResources().getString(R.string.app_name),Toast.LENGTH_SHORT).show();
                }
                break;
            case VERIFY_ONE:
                if (Integer.valueOf(((String) data)) == 0) {
                    //库存不足 按钮不能点击
                    goPayBut.setClickable(false);
                    joinCartBut.setClickable(false);
                }
                break;
        }
    }

    //库存检验
    private void verifyNumber() {
        verifyOnePresenter = new PostVerifyOnePresenter(goods.getProductId(), goods.getProductNum());
        verifyOnePresenter.attachView(this);
        verifyOnePresenter.onHttpPostRequest(VERIFY_ONE);
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
    }

    //设置弹窗
    private void setPopuWindow() {
        //弹出窗体
        popupWindow = new PopupWindow(this);

        View view = LayoutInflater.from(this).inflate(R.layout.popuwindow_goods,null);

        Button popuSure = view.findViewById(R.id.popuSure);
        popuSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                animatorSet.start();
                addCartPresenter = new PostAddCartPresenter(goods);
                addCartPresenter.onHttpPostRequest(ADD_CART);
            }
        });
        popupWindow.setContentView(view);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(300);
        //设置点击外部消失
        popupWindow.setOutsideTouchable(true);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        addCartPresenter.detachView();
        verifyOnePresenter.detachView();
    }

}
