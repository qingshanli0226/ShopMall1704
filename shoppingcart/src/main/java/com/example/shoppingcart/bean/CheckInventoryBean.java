package com.example.shoppingcart.bean;

import com.shaomall.framework.bean.ShoppingCartBean;

public class CheckInventoryBean extends ShoppingCartBean {
    private boolean isInventory; //库存是否充足

    public boolean isInventory() {
        return isInventory;
    }

    public void setInventory(boolean inventory) {
        isInventory = inventory;
    }


}
