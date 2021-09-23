package com.wabnet.cybering.model.notifications;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.LinkedList;

@Document
public class ConnectionRequests {
    @Id
    String profid;
    LinkedList<String> requestsList;

    public ConnectionRequests() {
    }

    public ConnectionRequests(String profid) {
        this.profid = profid;
        this.requestsList = new LinkedList<>();
    }

    public String getProfid() {
        return profid;
    }

    public void setProfid(String profid) {
        this.profid = profid;
    }

    public LinkedList<String> getRequestsList() {
        return requestsList;
    }

    public void setRequestsList(LinkedList<String> requestsList) {
        this.requestsList = requestsList;
    }

    @Override
    public String toString() {
        return "ConnectionRequests{" +
                "profid='" + profid + '\'' +
                ", requestsList=" + requestsList +
                '}';
    }
}
