package com.wabnet.cybering.model.advertisements;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.LinkedList;

@Document
public class AdvertisementFull {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    String id;
    String email;
    String startDate;
    String endDate;
    String title;
    String description;
    LinkedList<String> skills;
    LinkedList<String[]> applicants;

    public AdvertisementFull(String email, String startDate, String endDate, String title, String description, LinkedList<String> skills, LinkedList<String[]> applicants) {
        this.email = email;
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.description = description;
        this.skills = skills;
        this.applicants = applicants;
    }

    public AdvertisementFull() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "AdvertisementFull{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", skills=" + skills +
                ", applicants=" + applicants +
                '}';
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public LinkedList<String> getSkills() {
        return skills;
    }

    public void setSkills(LinkedList<String> skills) {
        this.skills = skills;
    }

    public LinkedList<String[]> getApplicants() {
        return applicants;
    }

    public void setApplicants(LinkedList<String[]> applicants) {
        this.applicants = applicants;
    }

    public AdvertisementFull(String id, String email, String startDate, String endDate, String title, String description, LinkedList<String> skills, LinkedList<String[]> applicants) {
        this.id = id;
        this.email = email;
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.description = description;
        this.skills = skills;
        this.applicants = applicants;
    }
}
