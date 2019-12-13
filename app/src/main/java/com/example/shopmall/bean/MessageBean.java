package com.example.shopmall.bean;

public class MessageBean {

    //消息是否读取
    private boolean isMessage;

    //所购买成功的商品的名称/提示今日步数
    private String nameMessage;

    //购买成功提示/今日步数显示
    private String contentMessage;

    public MessageBean() {
    }

    public MessageBean(boolean isMessage, String nameMessage, String contentMessage) {
        this.isMessage = isMessage;
        this.nameMessage = nameMessage;
        this.contentMessage = contentMessage;
    }

    public boolean isMessage() {
        return isMessage;
    }

    public void setMessage(boolean message) {
        isMessage = message;
    }

    public String getNameMessage() {
        return nameMessage;
    }

    public void setNameMessage(String nameMessage) {
        this.nameMessage = nameMessage;
    }

    public String getContentMessage() {
        return contentMessage;
    }

    public void setContentMessage(String contentMessage) {
        this.contentMessage = contentMessage;
    }

    @Override
    public String toString() {
        return "MessageBean{" +
                "isMessage=" + isMessage +
                ", nameMessage='" + nameMessage + '\'' +
                ", contentMessage='" + contentMessage + '\'' +
                '}';
    }
}