package com.example.framework.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class MessageBean {
    @Id(autoincrement = true)
    private Long id; //必须使用Long类型，long 或者 Integer int都不行
    @NotNull
    Integer message_img;
    @NotNull
    String message_title;
    @NotNull
    String message_message;
    @NotNull
    String message_date;
    @Generated(hash = 1175200578)
    public MessageBean(Long id, @NotNull Integer message_img,
            @NotNull String message_title, @NotNull String message_message,
            @NotNull String message_date) {
        this.id = id;
        this.message_img = message_img;
        this.message_title = message_title;
        this.message_message = message_message;
        this.message_date = message_date;
    }
    @Generated(hash = 1588632019)
    public MessageBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Integer getMessage_img() {
        return this.message_img;
    }
    public void setMessage_img(Integer message_img) {
        this.message_img = message_img;
    }
    public String getMessage_title() {
        return this.message_title;
    }
    public void setMessage_title(String message_title) {
        this.message_title = message_title;
    }
    public String getMessage_message() {
        return this.message_message;
    }
    public void setMessage_message(String message_message) {
        this.message_message = message_message;
    }
    public String getMessage_date() {
        return this.message_date;
    }
    public void setMessage_date(String message_date) {
        this.message_date = message_date;
    }

}
