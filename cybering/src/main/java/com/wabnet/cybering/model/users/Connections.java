package com.wabnet.cybering.model.users;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.LinkedList;

@Document
public class Connections {
    @Id
    String profid;
    LinkedList<String> list;

    public Connections() {
    }

    public Connections(String profid, LinkedList<String> list) {
        this.profid = profid;
        this.list = list;
    }

    public Connections(String profid) {
        this.profid = profid;
    }

    public String getProfid() {
        return profid;
    }

    public void setProfid(String profid) {
        this.profid = profid;
    }

    public LinkedList<String> getList() {
        return list;
    }

    public void setList(LinkedList<String> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "Connections{" +
                "profid='" + profid + '\'' +
                ", list=" + list +
                '}';
    }
}
