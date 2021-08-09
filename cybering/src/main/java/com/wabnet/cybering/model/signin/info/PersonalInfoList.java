package com.wabnet.cybering.model.signin.info;

import java.util.Arrays;

public class PersonalInfoList {
    String firstName;
    String lastName;
    String phone;
    String photo_url;
    String bio;
    String workPosition;
    String workPlace;
    String[] skills;

    public PersonalInfoList() {
        this.firstName = "";
        this.lastName = "";
        this.phone = "";
        this.photo_url = "";
        this.bio = "";
        this.workPlace = "";
        this.workPosition = "";
        this.skills = new String[] {""};
    }

    public PersonalInfoList(String firstName, String lastName, String phone, String photo_url, String bio, String workPosition, String workPlace, String[] skills) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.photo_url = photo_url;
        this.bio = bio;
        this.workPosition = workPosition;
        this.workPlace = workPlace;
        this.skills = skills;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getWorkPosition() {
        return workPosition;
    }

    public void setWorkPosition(String workPosition) {
        this.workPosition = workPosition;
    }

    public String getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }

    public String[] getSkills() {
        return skills;
    }

    public void setSkills(String[] skills) {
        this.skills = skills;
    }

    @Override
    public String toString() {
        return "PersonalInfoList{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", photo_url='" + photo_url + '\'' +
                ", bio='" + bio + '\'' +
                ", workPosition='" + workPosition + '\'' +
                ", workPlace='" + workPlace + '\'' +
                ", skills=" + Arrays.toString(skills) +
                '}';
    }
}
