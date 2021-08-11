package com.wabnet.cybering.model.articles;

import java.util.Arrays;

public class ArticleResponse {

    String id;

    String title;   // followers if org/business, workposition if user, etc.
    String firstName;
    String lastName;
    String photo_url;
    String timestamp;
    String[] categories;
    String text;
    String[][] comments;
    String[] likes;
    String[][] media;

    public ArticleResponse() {
    }

    @Override
    public String toString() {
        return "ArticleResponse{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", photo_url='" + photo_url + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", categories=" + Arrays.toString(categories) +
                ", text='" + text + '\'' +
                ", comments=" + Arrays.toString(comments) +
                ", likes=" + Arrays.toString(likes) +
                ", media=" + Arrays.toString(media) +
                '}';
    }

    public ArticleResponse(String id, String title, String firstName, String lastName, String photo_url, String timestamp, String[] categories, String text, String[][] comments, String[] likes, String[][] media) {
        this.id = id;
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
        this.photo_url = photo_url;
        this.timestamp = timestamp;
        this.categories = categories;
        this.text = text;
        this.comments = comments;
        this.likes = likes;
        this.media = media;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
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
}
