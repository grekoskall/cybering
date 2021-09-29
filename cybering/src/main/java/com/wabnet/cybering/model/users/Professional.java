package com.wabnet.cybering.model.users;


import com.wabnet.cybering.CyberingApplication;
import com.wabnet.cybering.model.privacy.PrivacySettings;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.Arrays;
import java.util.PrimitiveIterator;


@Document
public class Professional {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
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
    private String password;
    private String lastDiscussionOpen;
    private PrivacySettings privacySettings;

    public Professional() {
    }

    public Professional(String email) {
        this.email = email;
        this.workExperience = new String[0];
        this.education = new String[0];
        this.skills = new String[0];
        this.createPrivacySettings();
    }

    public Professional(String email, String firstName, String lastName, String password) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.workExperience = new String[0];
        this.education = new String[0];
        this.skills = new String[0];
        this.createPrivacySettings();
    }


    public Professional(String email, String firstName, String lastName, String age, String phone, String photo, String workPosition, String workPlace, String bio, String[] workExperience, String[] education, String[] skills, String password) {
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
        this.password = password;
        this.createPrivacySettings();
    }

    public Professional(String email, String firstName, String lastName, String photo, String password) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.photo = photo;
        this.password = password;
        this.workExperience = new String[0];
        this.education = new String[0];
        this.skills = new String[0];
        this.createPrivacySettings();
    }

    public Professional(String email, String firstName, String lastName, String age, String phone, String photo, String workPosition, String workPlace, String bio, String[] workExperience, String[] education, String[] skills, String password, String lastDiscussionOpen) {
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
        this.password = password;
        this.lastDiscussionOpen = lastDiscussionOpen;
        this.createPrivacySettings();
    }

    private void createPrivacySettings() {
        this.privacySettings = new PrivacySettings();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastDiscussionOpen() {
        return lastDiscussionOpen;
    }

    public void setLastDiscussionOpen(String lastDiscussionOpen) {
        this.lastDiscussionOpen = lastDiscussionOpen;
    }

    public PrivacySettings getPrivacySettings() {
        return privacySettings;
    }

    public void setPrivacySettings(PrivacySettings privacySettings) {
        this.privacySettings = privacySettings;
    }

    @Override
    public String toString() {
        return "Professional{" +
                "id='" + id + '\'' +
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
                ", password='" + password + '\'' +
                ", lastDiscussionOpen='" + lastDiscussionOpen + '\'' +
                ", privacySettings=" + privacySettings +
                '}';
    }
}
