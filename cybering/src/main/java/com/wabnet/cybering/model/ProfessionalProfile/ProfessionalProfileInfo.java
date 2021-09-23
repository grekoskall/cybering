package com.wabnet.cybering.model.ProfessionalProfile;

import com.wabnet.cybering.model.privacy.PrivacySettings;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Arrays;

@Document
public class ProfessionalProfileInfo {
    private String profid;
    private String email;
    private String firstName;
    private String lastName;
    private String age;
    private String phone;
    private String photo;
    private String workPosition;
    private String workPlace;
    private String bio;
    private String[] workExperience;
    private String[] education;
    private String[] skills;
    private UserStatus userStatus;

    public ProfessionalProfileInfo() {
    }

    public ProfessionalProfileInfo(String profid, String email, String firstName, String lastName, String age, String phone, String photo, String workPosition, String workPlace, String bio, String[] workExperience, String[] education, String[] skills, UserStatus userStatus) {
        this.profid = profid;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.phone = phone;
        this.photo = photo;
        this.workPosition = workPosition;
        this.workPlace = workPlace;
        this.bio = bio;
        this.workExperience = workExperience;
        this.education = education;
        this.skills = skills;
        this.userStatus = userStatus;
    }

    public String getProfid() {
        return profid;
    }

    public void setProfid(String profid) {
        this.profid = profid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
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

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String[] getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(String[] workExperience) {
        this.workExperience = workExperience;
    }

    public String[] getEducation() {
        return education;
    }

    public void setEducation(String[] education) {
        this.education = education;
    }

    public String[] getSkills() {
        return skills;
    }

    public void setSkills(String[] skills) {
        this.skills = skills;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    @Override
    public String toString() {
        return "ProfessionalProfileInfo{" +
                "profid='" + profid + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age='" + age + '\'' +
                ", phone='" + phone + '\'' +
                ", photo='" + photo + '\'' +
                ", workPosition='" + workPosition + '\'' +
                ", workPlace='" + workPlace + '\'' +
                ", bio='" + bio + '\'' +
                ", workExperience=" + Arrays.toString(workExperience) +
                ", education=" + Arrays.toString(education) +
                ", skills=" + Arrays.toString(skills) +
                ", userStatus=" + userStatus +
                '}';
    }
}
