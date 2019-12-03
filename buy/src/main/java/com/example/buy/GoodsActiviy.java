package com.example.buy;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.framework.base.BaseActivity;

public class GoodsActiviy extends BaseActivity implements View.OnClickListener {
    private Button goPayBut;
    private Button joinCartBut;
    private Button collectBut;
    private ImageView goodsImage;
    private Button cartBut;
    private ImageView beiImgage;

    @Override
    protected void onStart() {
        super.onStart();
        //获取到用户点击商品     发起请求
//        Intent intent = getIntent();
//        String goods = intent.getStringExtra("goods");
//        intData(goods);
    }

    //设置页面
    private void intData(String goods) {
        //完成设置页面之后,再次发起请求,获取该产品库存,然后判断用户是否能购买/加入购物车
        verifyNumber();
    }
    @Override
    public void onClick(View v) {
        //加入和购买 两个按钮点击之前,都要进行库存检验
        verifyNumber();
        if (v.getId() == goPayBut.getId()) {
            //携带商品数据跳转到支付页  并结束页面
            Intent intent = new Intent(this, PayActivity.class);
            intent.putExtra("money", 123);
            startActivity(intent);
            finish();
        } else if (v.getId() == joinCartBut.getId()) {
            /**
             * 通知服务器  然后,提示用户加入完成
             *
             * 15，“addOneProduct”
             * 说明：向服务端购物车添加一个产品的接口
             * POST
             * 请求参数：
             * 参数格式：application/json, text/json
             * 示例：{"productId":"1512","productNum":1,"productName":"衬衫","url":"http:\/\/www.baidu.com"}
             * 请求头需要添加token
             *
             * 返回值:
             * 返回格式：application/json, text/json
             * 示例：{"code":"200","message":"请求成功","result":"请求成功"}
             * */


            //加入购物车动画
            //获取布局的宽和高
            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            int height= metrics.heightPixels;
            int width= metrics.widthPixels;
            //贝塞尔路径绘制
            Path path = new Path();
            path.moveTo(0,0);
            path.quadTo(width,height/2, 0,height);
            final PathMeasure pathMeasure = new PathMeasure();
            // false表示path路径不闭合
            pathMeasure.setPath(path, false);
            //属性动画加载
            ValueAnimator valueAnimator = ValueAnimator.ofInt(0, (int) pathMeasure.getLength());
            //动画时长设置
            valueAnimator.setDuration(10000);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int value = (int) animation.getAnimatedValue();
                    //临时存储当前控件位置
                    float[] currentPosition = new float[2];
                    pathMeasure.getPosTan(value, currentPosition, null);
                    //设置位置
                    beiImgage.setX(currentPosition[0]);
                    beiImgage.setY(currentPosition[1]);
                }
            });
            valueAnimator.start();

        } else if (v.getId() == collectBut.getId()) {
            //本地sp存储收藏的商品的信息

        }
    }

    //库存检验
    private void verifyNumber() {
        /***
         14，”checkOneProductInventory”
         说明：检查服务端一个产品库存情况的接口
         POST
         请求头需要添加token

         请求参数：
         参数格式：application/x-www-form-urlencoded
         示例：productId=1532&productNum=2
         * */

        //库存不足 按钮不能点击
        goPayBut.setClickable(false);
        joinCartBut.setClickable(false);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_goods;
    }

    @Override
    public void init() {
        //http://49.233.93.155:8080  updateMoney  money=1333

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

    }
}
