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

    private static CartManager cartManager=new CartManager();

    private CartManager() {
    }

    public static CartManager getInstance(){
        return cartManager;
    }

    public void registerListener(OnShopCartListener onShopCartListener){
        listeners.add(onShopCartListener);
    }
    public void unregister(OnShopCartListener onShopCartListener){
        listeners.remove(onShopCartListener);
    }

    public int getCartNum(){
        return listGoods.size();
    }

    public void addCheck(boolean selete,String checkId){
        for (CheckGoodsData i:checks){
            if (i.equals(checkId)){
                i.setSelect(selete);
                return;
            }
        }
        checks.add(new CheckGoodsData(selete,checkId));
    }
    public void setCheckSelect(int position,boolean selete){
        if (checks.get(position)!=null){
            CheckGoodsData checkGoodsData = checks.get(position);
            checkGoodsData.setSelect(selete);
            checks.set(position,checkGoodsData);
        }
    }
    public void clearCheck(){
        checks.clear();
    }
    public ArrayList<GoodsBean> getListGoods() {
        return listGoods;
    }

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

    public void notifyChecks(){
        ArrayList<String> littleCheks=new ArrayList<>();
        for (int i=0;i<listGoods.size();i++){
            for (CheckGoodsData good:CartManager.getInstance().getChecks()){
                if (listGoods.get(i).getProductId().equals(good.getId())&&good.isSelect()){
                    littleCheks.add(good.getId());
                    break;
                }
            }
        }
        clearCheck();
        for (int i = 0; i < listGoods.size(); i++) {
            CartManager.getInstance().addCheck(false,listGoods.get(i).getProductId());
            for (String good:littleCheks){
                if (listGoods.get(i).getProductId().equals(good)){
                    CartManager.getInstance().setCheckSelect(i,true);
                    break;
                }
            }
        }
    }
}
