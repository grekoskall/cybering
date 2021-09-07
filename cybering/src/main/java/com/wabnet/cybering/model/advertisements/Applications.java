package com.wabnet.cybering.model.advertisements;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.LinkedList;

@Document
public class Applications {
    @Id
    String profid;
    LinkedList<String> adIds;

    public Applications(String profid, LinkedList<String> adIds) {
        this.profid = profid;
        this.adIds = adIds;
    }

    public LinkedList<String> getAdIds() {
        return adIds;
    }

    public void setAdIds(LinkedList<String> adIds) {
        this.adIds = adIds;
    }

    public Applications() {
    }

    public String getProfid() {
        return profid;
    }


    public void setProfid(String profid) {
        this.profid = profid;
    }

}
