package com.example.dimensionleague.mine;

public class MineBean {
    private Integer img;
    private String title;

    @Override
    public String toString() {
        return "MineBean{" +
                "img=" + img +
                ", title='" + title + '\'' +
                '}';
    }

    public Integer getImg() {
        return img;
    }

    public void setImg(Integer img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public MineBean(Integer img, String title) {
        this.img = img;
        this.title = title;
    }
}
