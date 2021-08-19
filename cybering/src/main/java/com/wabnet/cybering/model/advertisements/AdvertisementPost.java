package com.wabnet.cybering.model.advertisements;

public class AdvertisementPost {
    String title;
    String description;
    String skills;
    String endDate;

    public AdvertisementPost() {
    }

    @Override
    public String toString() {
        return "AdvertisementPost{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", skills='" + skills + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public AdvertisementPost(String title, String description, String skills, String endDate) {
        this.title = title;
        this.description = description;
        this.skills = skills;
        this.endDate = endDate;
    }
}
