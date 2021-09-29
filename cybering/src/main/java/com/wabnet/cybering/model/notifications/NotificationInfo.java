package com.wabnet.cybering.model.notifications;

import com.wabnet.cybering.model.users.Professional;

import java.text.SimpleDateFormat;

public class NotificationInfo {
    String senderProfid;
    String fullName;
    NotificationType type;
    String timestamp;
    String info;

    public NotificationInfo() {
    }

    public NotificationInfo(String senderProfid, String fullName, NotificationType type, String info) {
        this.senderProfid = senderProfid;
        this.fullName = fullName;
        this.type = type;
        this.timestamp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new java.util.Date());
        this.info = info;
    }

    public NotificationInfo(String senderProfid, String fullName, NotificationType type, String timestamp, String info) {
        this.senderProfid = senderProfid;
        this.fullName = fullName;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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
                ", fullName='" + fullName + '\'' +
                ", type=" + type +
                ", timestamp='" + timestamp + '\'' +
                ", info='" + info + '\'' +
                '}';
    }
}
