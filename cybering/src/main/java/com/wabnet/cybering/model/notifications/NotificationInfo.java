package com.wabnet.cybering.model.notifications;

import java.text.SimpleDateFormat;

public class NotificationInfo {
    String senderProfid;
    NotificationType type;
    String timestamp;
    String info;

    public NotificationInfo() {
    }

    public NotificationInfo(String senderProfid, NotificationType type, String info) {
        this.senderProfid = senderProfid;
        this.type = type;
        this.timestamp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new java.util.Date());
        this.info = info;
    }

    public NotificationInfo(String senderProfid, NotificationType type, String timestamp, String info) {
        this.senderProfid = senderProfid;
        this.type = type;
        this.timestamp = timestamp;
        this.info = info;
    }

    public String getSenderProfid() {
        return senderProfid;
    }

    public void setSenderProfid(String senderProfid) {
        this.senderProfid = senderProfid;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "NotificationInfo{" +
                "senderProfid='" + senderProfid + '\'' +
                ", type=" + type +
                ", timestamp='" + timestamp + '\'' +
                ", info='" + info + '\'' +
                '}';
    }
}
