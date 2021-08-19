package com.wabnet.cybering.model.advertisements;

import java.util.Arrays;

public class AdvertisementApplication {
    String id;
    String title;
    String description;
    String[] skills;
    String startDate;
    String endDate;
    String[][] applicants;

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "AdvertisementApplication{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", skills=" + Arrays.toString(skills) +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", applicants=" + Arrays.toString(applicants) +
                '}';
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getSkills() {
        return skills;
    }

    public void setSkills(String[] skills) {
        this.skills = skills;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String[][] getApplicants() {
        return applicants;
    }

    public void setApplicants(String[][] applicants) {
        this.applicants = applicants;
    }

    public AdvertisementApplication(String id, String title, String description, String[] skills, String startDate, String endDate, String[][] applicants) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.skills = skills;
        this.startDate = startDate;
        this.endDate = endDate;
        this.applicants = applicants;
    }

    public AdvertisementApplication() {
    }
}
