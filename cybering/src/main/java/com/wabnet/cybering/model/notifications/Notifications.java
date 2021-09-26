package com.wabnet.cybering.model.notifications;

import org.springframework.data.annotation.Id;

import java.util.LinkedList;

public class Notifications {
    @Id
    String profid;
    LinkedList<NotificationInfo> notificationsList;

    public Notifications() {
    }

    public Notifications(String profid) {
        this.profid = profid;
        this.notificationsList = new LinkedList<NotificationInfo>();
    }

    public Notifications(String profid, LinkedList<NotificationInfo> notificationsList) {
        this.profid = profid;
        this.notificationsList = notificationsList;
    }

    public String getProfid() {
        return profid;
    }

    public void setProfid(String profid) {
        this.profid = profid;
    }

    public LinkedList<NotificationInfo> getNotificationsList() {
        return notificationsList;
    }

    public void setNotificationsList(LinkedList<NotificationInfo> notificationsList) {
        this.notificationsList = notificationsList;
    }

    @Override
    public String toString() {
        return "Notifications{" +
                "profid='" + profid + '\'' +
                ", notificationsList=" + notificationsList +
                '}';
    }
}
