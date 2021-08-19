package com.wabnet.cybering.model.advertisements;

import java.util.Arrays;

public class Advertisement {
    String id;
    String firstName;
    String lastName;
    String startDate;
    String endDate;
    String workPlace;
    String workPosition;
    String title;
    String description;
    String[] skills;

    @Override
    public String toString() {
        return "Advertisement{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", workPlace='" + workPlace + '\'' +
                ", workPosition='" + workPosition + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", skills=" + Arrays.toString(skills) +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }

    public String getWorkPosition() {
        return workPosition;
    }

    public void setWorkPosition(String workPosition) {
        this.workPosition = workPosition;
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

    public Advertisement() {
    }

    public Advertisement(String id, String firstName, String lastName, String startDate, String endDate, String workPlace, String workPosition, String title, String description, String[] skills) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.workPlace = workPlace;
        this.workPosition = workPosition;
        this.title = title;
        this.description = description;
        this.skills = skills;
    }
}
