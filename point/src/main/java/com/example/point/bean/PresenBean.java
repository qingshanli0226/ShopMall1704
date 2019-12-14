package com.example.point.bean;

public class PresenBean {
    Integer pre_img;
    String pre_tv;

    public PresenBean(Integer pre_img, String pre_tv) {
        this.pre_img = pre_img;
        this.pre_tv = pre_tv;
    }

    @Override
    public String toString() {
        return "PresenBean{" +
                "pre_img=" + pre_img +
                ", pre_tv='" + pre_tv + '\'' +
                '}';
    }

    public Integer getPre_img() {
        return pre_img;
    }

    public void setPre_img(Integer pre_img) {
        this.pre_img = pre_img;
    }

    public String getPre_tv() {
        return pre_tv;
    }

    public void setPre_tv(String pre_tv) {
        this.pre_tv = pre_tv;
    }
}
