package com.example.buy.databeans;

public class CheckGoodsData {
    private boolean select;
    private String id;

    public CheckGoodsData(boolean select, String id) {
        this.select = select;
        this.id = id;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "CheckGoodsData{" +
                "select=" + select +
                ", id='" + id + '\'' +
                '}';
    }
}
