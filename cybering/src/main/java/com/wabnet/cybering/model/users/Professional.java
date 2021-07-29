package com.wabnet.cybering.model.users;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.annotation.processing.Generated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;


@Document

public class Professional {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private String id;
    private String firstName;
    private String lastName;
    private Integer age;
    private String email;
    private String password;
    private String photo;
    private String phone;


    public Professional() {
    }

    public Professional(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = 0;
        this.email = email;
        this.password = password;
        this.photo = "default";
        this.phone = "empty";
    }

    public Professional(String firstName, String lastName, Integer age, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.password = password;
        this.photo = "default";
        this.phone = "empty";
    }

    public Professional(String firstName, String lastName, Integer age, String email, String password, String photo, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.password = password;
        this.photo = photo;
        this.phone = phone;
    }

    public Professional(String firstName, String lastName, String email, String password, String photo) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = 0;
        this.email = email;
        this.password = password;
        this.photo = photo;
        this.phone = "empty";
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Professional{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", photo='" + photo + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
