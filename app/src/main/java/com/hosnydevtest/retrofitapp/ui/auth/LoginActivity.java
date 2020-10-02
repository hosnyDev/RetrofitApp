package com.hosnydevtest.retrofitapp.ui.auth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.hosnydevtest.retrofitapp.R;
import com.hosnydevtest.retrofitapp.api.RetrofitClient;
import com.hosnydevtest.retrofitapp.model.LoginResponse;
import com.hosnydevtest.retrofitapp.storage.SharedPrefManager;
import com.hosnydevtest.retrofitapp.ui.MainActivity;
import com.hosnydevtest.retrofitapp.util.CheckInternetConn;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.hosnydevtest.retrofitapp.util.Hide_Keyboard.hideKeyboard;

public class LoginActivity extends AppCompatActivity {

    private EditText _email;
    private EditText _password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        _email = findViewById(R.id.et_login_email);
        _password = findViewById(R.id.et_login_password);

        findViewById(R.id.btn_login_back).setOnClickListener(v -> finish());

        // on click to login user
        findViewById(R.id.btn_login).setOnClickListener(v -> {
            if (new CheckInternetConn(this).isConnection())
                validateData();
            else
                showAlert("No internet connection \nPlease check your internet speed and try again..!");
        });
    }

    private void validateData() {

        String email = _email.getText().toString().trim();
        String password = _password.getText().toString().trim();

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


        hideKeyboard(this);

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Wit .");
        progressDialog.setCancelable(false);
        progressDialog.show();

        Call<LoginResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .SignInUser(email, password);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                LoginResponse loginResponse = response.body();
                assert loginResponse != null;
                if (!loginResponse.isError()) {

                    SharedPrefManager.getInstance(LoginActivity.this).saveUser(loginResponse.getUserModel());
                    startProfileActivity();

                } else
                    showAlert(loginResponse.getMessage());
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.d("onFailure", "onFailure: " + t.getMessage());
                showAlert("onFailure error \n" + t.getMessage());
            }
        });
    }

    private void startProfileActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }



    private void showAlert(String msg) {
        new AlertDialog.Builder(this)
                .setMessage(msg)
                .setPositiveButton("OK", null)
                .create().show();
    }
}