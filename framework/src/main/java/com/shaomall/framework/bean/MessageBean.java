package com.shaomall.framework.bean;

public class MessageBean {
    private String messageId;
    private String message;
    private String  isRead;

    public MessageBean(String messageId, String message, String isRead) {
        this.messageId = messageId;
        this.message = message;
        this.isRead = isRead;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }
}
