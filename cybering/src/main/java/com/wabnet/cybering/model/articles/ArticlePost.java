package com.wabnet.cybering.model.articles;

public class ArticlePost {
    private String articleText;
    private String articleMedia;
    private String articleUrl;
    private String articleCategories;

    public ArticlePost(String articleText, String articleMedia, String articleUrl) {
        this.articleText = articleText;
        this.articleMedia = articleMedia;
        this.articleUrl = articleUrl;
    }

    public ArticlePost(String articleText, String articleMedia, String articleUrl, String articleCategories) {
        this.articleText = articleText;
        this.articleMedia = articleMedia;
        this.articleUrl = articleUrl;
        this.articleCategories = articleCategories;
    }

    @Override
    public String toString() {
        return "ArticlePost{" +
                "articleText='" + articleText + '\'' +
                ", articleMedia='" + articleMedia + '\'' +
                ", articleUrl='" + articleUrl + '\'' +
                ", articleCategories='" + articleCategories + '\'' +
                '}';
    }

    public String getArticleCategories() {
        return articleCategories;
    }

    public void setArticleCategories(String articleCategories) {
        this.articleCategories = articleCategories;
    }

    public ArticlePost() {
    }

    public String getArticleText() {
        return articleText;
    }

    public void setArticleText(String articleText) {
        this.articleText = articleText;
    }

    public String getArticleMedia() {
        return articleMedia;
    }

    public void setArticleMedia(String articleMedia) {
        this.articleMedia = articleMedia;
    }

    public String getArticleUrl() {
        return articleUrl;
    }

    public void setArticleUrl(String articleUrl) {
        this.articleUrl = articleUrl;
    }

}

