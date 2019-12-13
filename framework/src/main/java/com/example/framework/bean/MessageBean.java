package com.example.framework.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class MessageBean {

    @Id(autoincrement = true)
    private Long id;

    //消息是否读取
    private boolean isMessage;

    //名称
    private String nameMessage;

    //内容
    private String contentMessage;

    public MessageBean() {
    }

    @Generated(hash = 1939778456)
    public MessageBean(Long id, boolean isMessage, String nameMessage,
            String contentMessage) {
        this.id = id;
        this.isMessage = isMessage;
        this.nameMessage = nameMessage;
        this.contentMessage = contentMessage;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean getIsMessage() {
        return this.isMessage;
    }

    public void setIsMessage(boolean isMessage) {
        this.isMessage = isMessage;
    }

    public String getNameMessage() {
        return this.nameMessage;
    }

    public void setNameMessage(String nameMessage) {
        this.nameMessage = nameMessage;
    }

    public String getContentMessage() {
        return this.contentMessage;
    }

    public void setContentMessage(String contentMessage) {
        this.contentMessage = contentMessage;
    }
}