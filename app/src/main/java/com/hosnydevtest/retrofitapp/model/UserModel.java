package com.hosnydevtest.retrofitapp.model;

public class UserModel {

    private int id;
    private String email;
    private String name;
    private String school;

    public UserModel(int id, String email, String name, String school) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.school = school;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getSchool() {
        return school;
    }
}
