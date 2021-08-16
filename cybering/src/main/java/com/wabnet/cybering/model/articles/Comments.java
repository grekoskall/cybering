package com.wabnet.cybering.model.articles;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.LinkedList;

@Document
public class Comments {
    @Id
    String email;
    LinkedList<String> article_ids;

    public Comments(String email, LinkedList<String> article_ids) {
        this.email = email;
        this.article_ids = article_ids;
    }

    @Override
    public String toString() {
        return "Comments{" +
                "email='" + email + '\'' +
                ", article_ids=" + article_ids +
                '}';
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LinkedList<String> getArticle_ids() {
        return article_ids;
    }

    public void setArticle_ids(LinkedList<String> article_ids) {
        this.article_ids = article_ids;
    }
}
