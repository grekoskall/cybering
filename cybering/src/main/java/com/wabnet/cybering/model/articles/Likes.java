package com.wabnet.cybering.model.articles;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.LinkedList;
import java.util.List;

@Document
public class Likes {
    @Id
    String email;
    LinkedList<String> article_ids;

    public Likes(String email, LinkedList<String> article_ids) {
        this.email = email;
        this.article_ids = article_ids;
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

    @Override
    public String toString() {
        return "Likes{" +
                "email='" + email + '\'' +
                ", article_ids=" + article_ids +
                '}';
    }
}
