package com.wabnet.cybering.model.bases;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class SimpleString {
    @Id
    public String data;

    public SimpleString(String data) {
        this.data = data;
    }

    public SimpleString() {
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
