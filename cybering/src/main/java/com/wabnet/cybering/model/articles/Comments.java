package com.wabnet.cybering.model.articles;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.LinkedList;

@Document
public class Comments {
    @Id
    String profid;
    LinkedList<String> article_ids;

    public Comments(String profid, LinkedList<String> article_ids) {
        this.profid = profid;
        this.article_ids = article_ids;
    }

    public String getProfid() {
        return profid;
    }

    @Override
    public String toString() {
        return "Comments{" +
                "profid='" + profid + '\'' +
                ", article_ids=" + article_ids +
                '}';
    }

    public void setProfid(String profid) {
        this.profid = profid;
    }

    public LinkedList<String> getArticle_ids() {
        return article_ids;
    }

    public void setArticle_ids(LinkedList<String> article_ids) {
        this.article_ids = article_ids;
    }
}
