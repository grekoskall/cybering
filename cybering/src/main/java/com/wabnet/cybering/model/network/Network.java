package com.wabnet.cybering.model.network;


public class Network {
    private String firstName;
    private String lastName;
    private String profid;
    private String photo;
    private String workPlace;
    private String workPosition;

    public Network() {
    }

    public Network(String firstName, String lastName, String profid, String photo, String workPlace, String workPosition) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.profid = profid;
        this.photo = photo;
        this.workPlace = workPlace;
        this.workPosition = workPosition;
    }

    public Network(String firstName, String lastName, String photo, String workPlace, String workPosition) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.photo = photo;
        this.workPlace = workPlace;
        this.workPosition = workPosition;
    }

    public String getFirstName() {
        return firstName;
    }

    @Override
    public String toString() {
        return "Network{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", profid='" + profid + '\'' +
                ", photo='" + photo + '\'' +
                ", workPlace='" + workPlace + '\'' +
                ", workPosition='" + workPosition + '\'' +
                '}';
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

    public String getProfid() {
        return profid;
    }

    public void setProfid(String profid) {
        this.profid = profid;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
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
}
