package com.wabnet.cybering.model.articles;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ArticleReply {
    @Id
    private String aid;
    private String reply;

    public ArticleReply(String aid, String reply) {
        this.aid = aid;
        this.reply = reply;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    @Override
    public String toString() {
        return "ArticleReply{" +
                "aid='" + aid + '\'' +
                ", reply='" + reply + '\'' +
                '}';
    }


}
