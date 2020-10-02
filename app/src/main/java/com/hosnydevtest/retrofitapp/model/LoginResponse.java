package com.hosnydevtest.retrofitapp.model;

public class LoginResponse {

    private boolean error;
    private String message;
    private UserModel userModel;

    public LoginResponse(boolean error, String message, UserModel userModel) {
        this.error = error;
        this.message = message;
        this.userModel = userModel;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public UserModel getUserModel() {
        return userModel;
    }
}
