package com.hosnydevtest.retrofitapp.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.EditText;

import com.hosnydevtest.retrofitapp.R;
import com.hosnydevtest.retrofitapp.api.RetrofitClient;
import com.hosnydevtest.retrofitapp.model.LoginResponse;
import com.hosnydevtest.retrofitapp.model.UserModel;
import com.hosnydevtest.retrofitapp.storage.SharedPrefManager;
import com.hosnydevtest.retrofitapp.util.CheckInternetConn;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.hosnydevtest.retrofitapp.util.Hide_Keyboard.hideKeyboard;

public class UpdateProfileActivity extends AppCompatActivity {

    //view
    private EditText _email;
    private EditText _name;
    private EditText _school;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        findViewById(R.id.btn_update_profile_back).setOnClickListener(v -> finish());

        _email = findViewById(R.id.et_profile_email);
        _name = findViewById(R.id.et_profile_name);
        _school = findViewById(R.id.et_profile_school);

        findViewById(R.id.btn_update_profile).setOnClickListener(v -> {
            if (new CheckInternetConn(this).isConnection())
                validationData();
            else
                showAlert("No internet connection \nPlease check your internet speed and try again..!");

        });
    }

    private void validationData() {

        String email = _email.getText().toString().trim();
        String name = _name.getText().toString().trim();
        String school = _school.getText().toString().trim();

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showAlert("Email Address is required...!");
            _email.requestFocus();
            return;
        }
        if (name.isEmpty()) {
            showAlert("Name is required...!");
            _name.requestFocus();
            return;
        }


        if (school.isEmpty()) {
            showAlert("School is required...!");
            _school.requestFocus();
            return;
        }


        hideKeyboard(this);

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Wit .");
        progressDialog.setCancelable(false);
        progressDialog.show();

        UserModel userModel = SharedPrefManager.getInstance(this).getUser();

        Call<LoginResponse> call = RetrofitClient.getInstance().getApi().updateUser(
                userModel.getId(),
                email,
                name,
                school
        );

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                progressDialog.dismiss();
                assert response.body() != null;
                if (!response.body().isError()) {
                    SharedPrefManager.getInstance(UpdateProfileActivity.this).saveUser(response.body().getUserModel());
                }
                showAlert(response.body().getMessage());
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressDialog.dismiss();
                showAlert(t.getMessage());
            }
        });

    }

    private void showAlert(String msg) {
        new AlertDialog.Builder(this)
                .setMessage(msg)
                .setPositiveButton("OK", null)
                .create().show();
    }
}