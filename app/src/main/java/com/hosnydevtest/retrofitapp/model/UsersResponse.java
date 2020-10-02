package com.hosnydevtest.retrofitapp.model;

import java.util.List;

public class UsersResponse {

    private boolean error;
    private List<UserModel > users;

    public UsersResponse(boolean error, List<UserModel> users) {
        this.error = error;
        this.users = users;
    }

    public boolean isError() {
        return error;
    }

    public List<UserModel> getUsers() {
        return users;
    }
}
