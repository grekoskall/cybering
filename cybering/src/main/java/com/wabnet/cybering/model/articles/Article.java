package com.wabnet.cybering.model.articles;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.Arrays;

@Document
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    String id;

    String title;   // followers if org/business, workposition if user, etc.
    String profid;
    String photo_url;
    String timestamp;
    String[] categories;
    String text;
    String[][] comments;
    String[] likes;
    String[][] media;

    @Override
    public String toString() {
        return "Article{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", profid='" + profid + '\'' +
                ", photo_url='" + photo_url + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", categories=" + Arrays.toString(categories) +
                ", text='" + text + '\'' +
                ", comments=" + Arrays.toString(comments) +
                ", likes=" + Arrays.toString(likes) +
                ", media=" + Arrays.toString(media) +
                '}';
    }

    public Article(String id, String title, String profid, String photo_url, String timestamp, String[] categories, String text, String[][] comments, String[] likes, String[][] media) {
        this.id = id;
        this.title = title;
        this.profid = profid;
        this.photo_url = photo_url;
        this.timestamp = timestamp;
        this.categories = categories;
        this.text = text;
        this.comments = comments;
        this.likes = likes;
        this.media = media;
    }

    public Article() {
    }

    public Article(String title, String profid, String photo_url, String timestamp, String[] categories, String text, String[][] comments, String[] likes, String[][] media) {
        this.title = title;
        this.profid = profid;
        this.photo_url = photo_url;
        this.timestamp = timestamp;
        this.categories = categories;
        this.text = text;
        this.comments = comments;
        this.likes = likes;
        this.media = media;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProfid() {
        return profid;
    }

    public void setProfid(String profid) {
        this.profid = profid;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String[] getCategories() {
        return categories;
    }

    public void setCategories(String[] categories) {
        this.categories = categories;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String[][] getComments() {
        return comments;
    }

    public void setComments(String[][] comments) {
        this.comments = comments;
    }

    public String[] getLikes() {
        return likes;
    }

    public void setLikes(String[] likes) {
        this.likes = likes;
    }

    public String[][] getMedia() {
        return media;
    }

    public void setMedia(String[][] media) {
        this.media = media;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
