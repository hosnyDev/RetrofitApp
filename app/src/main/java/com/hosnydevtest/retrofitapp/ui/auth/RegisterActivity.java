package com.hosnydevtest.retrofitapp.ui.auth;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.hosnydevtest.retrofitapp.R;
import com.hosnydevtest.retrofitapp.api.RetrofitClient;
import com.hosnydevtest.retrofitapp.model.DefaultResponse;
import com.hosnydevtest.retrofitapp.util.CheckInternetConn;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.hosnydevtest.retrofitapp.util.Hide_Keyboard.hideKeyboard;

public class RegisterActivity extends AppCompatActivity {

    private EditText _email;
    private EditText _password;
    private EditText _name;
    private EditText _school;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        _email = findViewById(R.id.et_register_email);
        _password = findViewById(R.id.et_register_password);
        _name = findViewById(R.id.et_register_name);
        _school = findViewById(R.id.et_register_school);

        findViewById(R.id.btn_register_back).setOnClickListener(v -> finish());

        // on click to register user
        findViewById(R.id.btn_register).setOnClickListener(v -> {
            if (new CheckInternetConn(this).isConnection())
                validateData();
            else
                showAlert("No internet connection \nPlease check your internet speed and try again..!");
        });
    }

    private void validateData() {

        String email = _email.getText().toString().trim();
        String password = _password.getText().toString().trim();
        String name = _name.getText().toString().trim();
        String school = _school.getText().toString().trim();

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showAlert("Email Address is required...!");
            _email.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            showAlert("Password is required...!");
            _password.requestFocus();
            return;
        }
        if (password.length() < 6) {
            showAlert("Password length must be 6 char...!");
            _password.requestFocus();
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

        Call<DefaultResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .CreateUser(email, password, name, school);

        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {

                if (response.code() == 101) {
                    DefaultResponse defaultResponse = response.body();
                    assert defaultResponse != null;
                    showAlert(defaultResponse.getMessage());
                } else if (response.code() == 102 || response.code() == 103) {
                    DefaultResponse defaultResponse = response.body();
                    assert defaultResponse != null;
                    showAlert(defaultResponse.getMessage());
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.d("onFailure", "onFailure: " + t.getMessage());
                showAlert("onFailure error \n" + t.getMessage());
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