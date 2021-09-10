package com.wabnet.cybering.model.privacy;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Document
public class PrivacySettings {

    private Privacy workExperience;
    private Privacy education;
    private Privacy skills;

    public PrivacySettings() {

        this.workExperience = Privacy.PUBLIC;
        this.education = Privacy.PUBLIC;
        this.skills = Privacy.PUBLIC;
    }

    public PrivacySettings(Privacy workExperience, Privacy education, Privacy skills) {

        this.workExperience = workExperience;
        this.education = education;
        this.skills = skills;
    }

    public Privacy getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(Privacy workExperience) {
        this.workExperience = workExperience;
    }

    public Privacy getEducation() {
        return education;
    }

    public void setEducation(Privacy education) {
        this.education = education;
    }

    public Privacy getSkills() {
        return skills;
    }

    public void setSkills(Privacy skills) {
        this.skills = skills;
    }

    @Override
    public String toString() {
        return "PrivacySettings{" +
                "workExperience=" + workExperience +
                ", education=" + education +
                ", skills=" + skills +
                '}';
    }
}
