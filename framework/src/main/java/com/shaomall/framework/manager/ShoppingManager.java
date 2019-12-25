package com.shaomall.framework.manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.example.commen.util.ErrorUtil;
import com.example.commen.util.ShopMailError;
import com.example.net.AppNetConfig;
import com.example.net.MVPObserver;
import com.example.net.ResEntity;
import com.example.net.RetrofitCreator;
import com.example.net.sign.SignUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shaomall.framework.bean.ShoppingCartBean;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class ShoppingManager {
    private Context mContext;
    private static ShoppingManager instance;
    private List<ShoppingCartBean> result = new ArrayList<>(); //购物车数据集合
    private LinkedList<ShoppingNumChangeListener> shoppingNumChangeListeners = new LinkedList<>();//购物车商品数量改变监听
    private LinkedList<NotifyUpdatedShoppingDataListener> notifyUpdatedShoppingDataListeners = new LinkedList<>();////商品数量更新通知
    private CompositeDisposable compositeDisposable = new CompositeDisposable(); //避免内存泄漏
    private boolean isChange = false;

    private ShoppingManager() {
    }

    public static ShoppingManager getInstance() {
        if (instance == null) {
            synchronized (ShoppingManager.class) {
                if (instance == null) {
                    instance = new ShoppingManager();
                }
            }
        }
        return instance;
    }

    public void init(final Context context) {
        this.mContext = context;
        getData();
    }

    /**
     * 添加购物车商品
     *
     * @param shoppingCartBean
     */
    public void addShoppingCart(ShoppingCartBean shoppingCartBean) {
        //判断商品是否被添加过
        for (ShoppingCartBean bean : result) {
            if (bean.getProductId().equals(shoppingCartBean.getProductId())) {
                int beanNum = Integer.parseInt(bean.getProductNum());
                int shoppingNum = Integer.parseInt(shoppingCartBean.getProductNum());

                int productNum = beanNum + shoppingNum; //商品数量
                bean.setProductNum(productNum + "");
                bean.setSelect(shoppingCartBean.isSelect()); //商品选中状态
                isChange = true;
                break;
            } else {
                isChange = false;
            }
        }
        if (isChange) {
            isChange = false;
        } else {
            result.add(shoppingCartBean);
            sendShoppingNumChangeListener();//通知商品数量改变了
        }

        //通知商品数据更新了
        notifyUpdatedShoppingData();
    }


    /**
     * 删除选中的数据
     */
    public void removeShoppingCartData() {
        for (int i = 0; i < result.size(); i++) {
            if (result.get(i).isSelect()) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("productId", result.get(i).getProductId());
                jsonObject.put("productNum", result.get(i).getProductNum());
                jsonObject.put("productName", result.get(i).getProductName());
                jsonObject.put("url", result.get(i).getUrl());
                jsonObject.put("productPrice", result.get(i).getProductPrice());
                removeData(jsonObject);
            }
        }
    }

    /**
     * 清空本地商品数据, 适用于退出登录
     */
    public void removeShoppingCartAllData() {
        if (result != null) {
            result.clear();
        }
        //商品数量改变
        sendShoppingNumChangeListener();
        notifyUpdatedShoppingData();
    }

    /**
     * 更新商品购买的数量
     *
     * @param map
     */
    public void upDataGoodsNum(HashMap<String, Integer> map) {
        result.get(map.get("index")).setProductNum(map.get("num") + "");
        notifyUpdatedShoppingData();
    }


    /**
     * 获取购物车商品数量
     *
     * @return
     */
    public int getShoppingNum() {
        return result.size();
    }


    /**
     * 获取商品数据集合
     *
     * @return
     */
    public List<ShoppingCartBean> getShoppingCartData() {
        return result;
    }


    /**
     * 获取选中商品的总价
     *
     * @return
     */
    public float getTheTotalPrice() {
        float totalPrice = 0.0f;
        for (ShoppingCartBean bean : result) {
            if (bean.isSelect()) {
                totalPrice += getShoppingPrice2Num(bean); //计算总价
            }
        }

        //四舍五入保留两位小数
        BigDecimal bigDecimal = new BigDecimal(totalPrice);
        totalPrice = bigDecimal.setScale(2, RoundingMode.HALF_UP).floatValue();//四舍五入
        return totalPrice;
    }

    /**
     * 计算单个商品的总价格
     *
     * @param data
     * @return
     */
    private float getShoppingPrice2Num(ShoppingCartBean data) {
        float price = Float.parseFloat(data.getProductPrice());
        float num = Float.parseFloat(data.getProductNum());
        return price * num;
    }


    /**
     * 获取购物车数据
     */
    public void getData() {
        //进行网络请求
        RetrofitCreator.getNetApiService().getData(new HashMap<String, String>(), AppNetConfig.GET_SHORTCART_PRODUCTS_URL, new HashMap<String, String>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MVPObserver<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        super.onSubscribe(d);
                        compositeDisposable.add(d); //可以避免内存泄漏
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String string = responseBody.string();
                            ResEntity<List<ShoppingCartBean>> listResEntity = new Gson().fromJson(string, new TypeToken<ResEntity<List<ShoppingCartBean>>>() {
                            }.getType());
                            int code = listResEntity.getCode();
                            if (code == ShopMailError.SUCCESS.getErrorCode()) { //请求成功
                                List<ShoppingCartBean> data = listResEntity.getResult();
                                result.clear();
                                result.addAll(data);
                                notifyUpdatedShoppingData(); //通知数量改变
                                sendShoppingNumChangeListener();//通知商品改变
                            } else {
                                Toast.makeText(mContext, ErrorUtil.dataProcessing(code).getErrorMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(mContext, "ShoppingManger:　" + ErrorUtil.handlerError(e), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 删除选中的购物车数据
     */
    private void removeData(JSONObject jsonParam) {

        RequestBody requestBody = null;
        if (jsonParam != null) {
            jsonParam.put("sign", SignUtil.generateJsonSign(jsonParam));
            SignUtil.encryptJsonParamsByBase64(jsonParam);
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonParam.toString());
        }
        RetrofitCreator.getNetApiService().jsonPostData(new HashMap<String, String>(), AppNetConfig.REMOVE_ONE_PRODUCT, requestBody).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MVPObserver<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        super.onSubscribe(d);
                        compositeDisposable.add(d); //可以避免内存泄漏
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        getData(); //重新获取购物车数据
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(mContext, "ShoppingManger:　" + ErrorUtil.handlerError(e), Toast.LENGTH_SHORT).show();
                    }
                });

    }


    /**
     * 商品数据更新了
     */
    public void notifyUpdatedShoppingData() {
        for (NotifyUpdatedShoppingDataListener listener : notifyUpdatedShoppingDataListeners) {
            listener.onNotifyUpdateShoppingData(getShoppingCartData());
        }
    }

    /**
     * 商品数量改变了
     */
    private void sendShoppingNumChangeListener() {
        for (ShoppingNumChangeListener listener : shoppingNumChangeListeners) {
            listener.onShoppingNumChange(getShoppingNum()); //当前商品数量
        }
    }

    /**
     * 获取被选中的数据, 付款时使用
     *
     * @return
     */
    public ArrayList<ShoppingCartBean> getShoppingCartSelectionData() {
        ArrayList<ShoppingCartBean> shoppingCartBeans = new ArrayList<>();
        for (ShoppingCartBean bean : result) {
            if (bean.isSelect()) {
                shoppingCartBeans.add(bean);
            }
        }
        return shoppingCartBeans;
    }


//    //接收广播
//    public BroadcastReceiver mReceiverShoppingManagerState = new BroadcastReceiver() {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//
//        }
//    };


    //设置监听
    public void registerShoppingNumChangeListener(ShoppingNumChangeListener listener) {
        if (!shoppingNumChangeListeners.contains(listener)) {
            shoppingNumChangeListeners.add(listener);
        }
    }

    public void unRegisterShoppingNumChangeListener(ShoppingNumChangeListener listener) {
        shoppingNumChangeListeners.remove(listener);
    }

    //商品数量发生改变监听
    public interface ShoppingNumChangeListener {
        void onShoppingNumChange(int num);
    }

    //设置监听
    public void registerNotifyUpdatedShoppingDataListener(NotifyUpdatedShoppingDataListener listener) {
        if (!notifyUpdatedShoppingDataListeners.contains(listener)) {
            notifyUpdatedShoppingDataListeners.add(listener);
        }
    }//设置监听

    public void unRegisterNotifyUpdatedShoppingDataListener(NotifyUpdatedShoppingDataListener listener) {
        notifyUpdatedShoppingDataListeners.remove(listener);
    }

    //    通知更新的购物数据监听器
    public interface NotifyUpdatedShoppingDataListener {
        void onNotifyUpdateShoppingData(List<ShoppingCartBean> data);
    }

    public void disposeCompositeDisposable() {
        compositeDisposable.dispose();
    }
}
