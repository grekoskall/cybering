package com.wabnet.cybering.model.personalinfo;

import com.wabnet.cybering.model.privacy.PrivacySettings;
import com.wabnet.cybering.model.users.Professional;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Arrays;

@Document
public class PersonalInfo {
    private String workPosition;
    private String workPlace;
    private String bio;
    private String[] workExperience;
    private String[] education;
    private String[] skills;
    private PrivacySettings privacySettings;

    public PersonalInfo() {
    }

    public PersonalInfo(Professional _professional) {
        this.workPosition = _professional.getWorkPosition();
        this.workPlace = _professional.getWorkPlace();
        this.bio = _professional.getBio();
        this.workExperience = _professional.getWorkExperience();
        this.education = _professional.getEducation();
        this.skills = _professional.getSkills();
        this.privacySettings = _professional.getPrivacySettings();
    }

    public PersonalInfo(String workPosition, String workPlace, String bio, String[] workExperience, String[] education, String[] skills, PrivacySettings privacySettings) {
        this.workPosition = workPosition;
        this.workPlace = workPlace;
        this.bio = bio;
        this.workExperience = workExperience;
        this.education = education;
        this.skills = skills;
        this.privacySettings = privacySettings;
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

    public PrivacySettings getPrivacySettings() {
        return privacySettings;
    }

    public void setPrivacySettings(PrivacySettings privacySettings) {
        this.privacySettings = privacySettings;
    }

    @Override
    public String toString() {
        return "PersonalInfo{" +
                "workPosition='" + workPosition + '\'' +
                ", workPlace='" + workPlace + '\'' +
                ", bio='" + bio + '\'' +
                ", workExperience=" + Arrays.toString(workExperience) +
                ", education=" + Arrays.toString(education) +
                ", skills=" + Arrays.toString(skills) +
                ", privacySettings=" + privacySettings +
                '}';
    }
}


