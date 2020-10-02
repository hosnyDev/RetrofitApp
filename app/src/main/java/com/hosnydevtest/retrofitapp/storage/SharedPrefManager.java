package com.hosnydevtest.retrofitapp.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.hosnydevtest.retrofitapp.model.UserModel;

public class SharedPrefManager {

    private static final String SHARED_PREF_NAME = "userShared";
    private static SharedPrefManager mInstance;
    private Context context;

    private SharedPrefManager(Context context) {
        this.context = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null)
            mInstance = new SharedPrefManager(context);
        return mInstance;
    }

    public void saveUser(UserModel userModel) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("id", userModel.getId());
        editor.putString("email", userModel.getEmail());
        editor.putString("name", userModel.getName());
        editor.putString("school", userModel.getSchool());
        editor.apply();
    }

    public boolean isLogin() {
        return context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
                .getInt("id", -1) != -1;
    }

    public UserModel getUser() {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new UserModel(
                preferences.getInt("id", -1),
                preferences.getString("email", null),
                preferences.getString("name", null),
                preferences.getString("school", null)
        );
    }

    public void logOut() {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }

}
