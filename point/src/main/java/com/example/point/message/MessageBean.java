package com.example.point.message;

public class MessageBean {
    Integer message_img;
    String message_title;
    String message_message;
    String message_date;

    public MessageBean(Integer message_img, String message_title, String message_message, String message_date) {
        this.message_img = message_img;
        this.message_title = message_title;
        this.message_message = message_message;
        this.message_date = message_date;
    }

    @Override
    public String toString() {
        return "MessageBean{" +
                "message_img=" + message_img +
                ", message_title='" + message_title + '\'' +
                ", message_message='" + message_message + '\'' +
                ", message_date='" + message_date + '\'' +
                '}';
    }

    public Integer getMessage_img() {
        return message_img;
    }

    public void setMessage_img(Integer message_img) {
        this.message_img = message_img;
    }

    public String getMessage_title() {
        return message_title;
    }

    public void setMessage_title(String message_title) {
        this.message_title = message_title;
    }

    public String getMessage_message() {
        return message_message;
    }

    public void setMessage_message(String message_message) {
        this.message_message = message_message;
    }

    public String getMessage_date() {
        return message_date;
    }

    public void setMessage_date(String message_date) {
        this.message_date = message_date;
    }
}
