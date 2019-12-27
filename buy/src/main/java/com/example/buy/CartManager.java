package com.example.buy;

import com.example.buy.databeans.CheckGoodsData;
import com.example.buy.databeans.GoodsBean;
import com.example.framework.listener.OnShopCartListener;

import java.util.ArrayList;
import java.util.LinkedList;

public class CartManager {
    private LinkedList<OnShopCartListener> listeners=new LinkedList<>();

    //数据和被选择
    private ArrayList<GoodsBean> listGoods = new ArrayList<>();
    private ArrayList<CheckGoodsData> checks = new ArrayList<>();
    //单例
    private static CartManager cartManager=new CartManager();

    private CartManager() {
    }

    public static CartManager getInstance(){
        return cartManager;
    }
    //注册监听
    public void registerListener(OnShopCartListener onShopCartListener){
        listeners.add(onShopCartListener);
    }
    //解除监听
    public void unregister(OnShopCartListener onShopCartListener){
        listeners.remove(onShopCartListener);
    }
    //主动获取数目
    public int getCartNum(){
        return listGoods.size();
    }
    //增加选中队列
    public void addCheck(boolean selete,String checkId){
        for (CheckGoodsData i:checks){
            if (i.equals(checkId)){
                i.setSelect(selete);
                return;
            }
        }
        checks.add(new CheckGoodsData(selete,checkId));
    }
    //更改选中
    public void setCheckSelect(int position,boolean selete){
        if (checks.get(position)!=null){
            CheckGoodsData checkGoodsData = checks.get(position);
            checkGoodsData.setSelect(selete);
            checks.set(position,checkGoodsData);
        }
    }
    //清空选中
    public void clearCheck(){
        checks.clear();
    }
    //返回数据
    public ArrayList<GoodsBean> getListGoods() {
        return listGoods;
    }
    //存储数据
    public void setListGoods(ArrayList<GoodsBean> listGoods) {
        this.listGoods = listGoods;
        for (int i=0;i<listeners.size();i++){
            listeners.get(i).shopCartNumChange(listGoods.size());
        }
    }
    public ArrayList<CheckGoodsData> getChecks() {
        return checks;
    }

    public void setChecks(ArrayList<CheckGoodsData> checks) {
        this.checks = checks;
    }
    //更新选中队列
    public void notifyChecks(){
        ArrayList<String> littleChecks=new ArrayList<>();
        for (int i=0;i<listGoods.size();i++){
            for (CheckGoodsData good:CartManager.getInstance().getChecks()){
                if (listGoods.get(i).getProductId().equals(good.getId())&&good.isSelect()){
                    littleChecks.add(good.getId());
                    break;
                }
            }
        }
        clearCheck();
        for (int i = 0; i < listGoods.size(); i++) {
            checks.add(new CheckGoodsData(false,listGoods.get(i).getProductId()));
            for (String good:littleChecks){
                if (listGoods.get(i).getProductId().equals(good)){
                    checks.set(i,new CheckGoodsData(true,good));
                    break;
                }
            }
        }
    }
}
