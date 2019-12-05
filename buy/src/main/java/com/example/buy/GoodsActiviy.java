package com.example.buy;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
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
    private Button collectBut;
    private ImageView goodsImage;
    private Button cartBut;
    private ImageView beiImgage;
    PopupWindow popupWindow;

    //初始位置   控制点  结束位置   x,y
    float[] startLoaction = new float[2];
    float[] controlLoaction = new float[2];
    float[] endLoaction = new float[2];
    //加入购物车请求  库存请求  商品信息请求  立即购买转支付页面
    IPresenter addCartPresenter;
    IPresenter verifyOnePresenter;

    //需要一个商品信息的对象
    GoodsBean goods;
    ValueAnimator valueAnimator;

    @Override
    protected void onStart() {
        super.onStart();
//        获取到用户点击的商品     发起请求,获取商品数据
        Intent intent = getIntent();
        String str = intent.getStringExtra(IntentUtil.SHOW_GOOD);
        ShowGoodsBean showGoodsBean = new Gson().fromJson(str, ShowGoodsBean.class);
        goods = new GoodsBean(
                showGoodsBean.getProduct_id(),
                1,
                showGoodsBean.getName(),
                showGoodsBean.getFigure());
        verifyNumber();
    }

    @Override
    public void onClick(View v) {
        //加入和购买 两个按钮点击之前,都要进行库存检验
        verifyNumber();
        if (v.getId() == goPayBut.getId()) {
            //携带商品数据跳转到支付页  并结束页面
            startActivity(PayActivity.class, null);
            finish();
        } else if (v.getId() == joinCartBut.getId()) {
            //加入购物车  弹窗
            popupWindow.showAtLocation(findViewById(R.id.goodsRel), Gravity.BOTTOM, 0, 0);

        } else if (v.getId() == collectBut.getId()) {
            //本地sp存储收藏的商品的信息

        }
    }

    @Override
    public void onRequestSuccess(int requestCode, Object data) {
        super.onRequestSuccess(requestCode, data);
        switch (requestCode) {
            case ADD_CART:
                if (Integer.valueOf(((OkBean) data).getCode()) == AppNetConfig.CODE_OK) {
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
        setBesselLocation();
    }

    //设置贝塞尔曲线的位置
    private void setBesselLocation() {
        startLoaction[0] = 0;
        startLoaction[1] = 0;

        //获取布局的宽和高
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int height = metrics.heightPixels;
        int width = metrics.widthPixels;
        controlLoaction[0] = height;
        controlLoaction[1] = height / 2;

        endLoaction[0] = 0;
        endLoaction[1] = height;

        setBesselAnimation(beiImgage);
    }

    //加入购物车动画
    private void setBesselAnimation(final View view) {
        //贝塞尔路径绘制
        Path path = new Path();
        //控制初始点
        path.moveTo(startLoaction[0], startLoaction[1]);
        path.quadTo(controlLoaction[0], controlLoaction[1], endLoaction[0], endLoaction[1]);
        final PathMeasure pathMeasure = new PathMeasure();
        // false表示path路径不闭合
        pathMeasure.setPath(path, false);
        //属性动画加载
        valueAnimator = ValueAnimator.ofInt(0, (int) pathMeasure.getLength());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                //临时存储当前控件位置
                float[] currentPosition = new float[2];
                pathMeasure.getPosTan(value, currentPosition, null);
                //设置位置
                view.setX(currentPosition[0]);
                view.setY(currentPosition[1]);
            }
        });
        //动画时长设置
        valueAnimator.setDuration(10000);
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
                valueAnimator.start();
                addCartPresenter = new PostAddCartPresenter(goods);
                addCartPresenter.onHttpPostRequest(ADD_CART);
            }
        });
        popupWindow.setContentView(view);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
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
