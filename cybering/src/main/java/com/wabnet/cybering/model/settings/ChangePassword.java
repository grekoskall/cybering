package com.wabnet.cybering.model.settings;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ChangePassword {
    private String currentPassword;
    private String newPassword;

    public ChangePassword() {
    }

    public ChangePassword(String currentPassword, String newPassword) {
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    public String toString() {
        return "ChangePassword{" +
                "currentPassword='" + currentPassword + '\'' +
                ", newPassword='" + newPassword + '\'' +
                '}';
    }
}
