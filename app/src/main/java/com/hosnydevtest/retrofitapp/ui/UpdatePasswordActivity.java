package com.hosnydevtest.retrofitapp.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

import com.hosnydevtest.retrofitapp.R;
import com.hosnydevtest.retrofitapp.api.RetrofitClient;
import com.hosnydevtest.retrofitapp.model.DefaultResponse;
import com.hosnydevtest.retrofitapp.model.UserModel;
import com.hosnydevtest.retrofitapp.storage.SharedPrefManager;
import com.hosnydevtest.retrofitapp.util.CheckInternetConn;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.hosnydevtest.retrofitapp.util.Hide_Keyboard.hideKeyboard;

public class UpdatePasswordActivity extends AppCompatActivity {

    //view
    private EditText _currentPass;
    private EditText _newPass;
    private EditText _confirmNewPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);

        findViewById(R.id.btn_update_password_back).setOnClickListener(v -> finish());

        _currentPass = findViewById(R.id.et_password_current);
        _newPass = findViewById(R.id.et_password_new);
        _confirmNewPass = findViewById(R.id.et_password_confirm);

        findViewById(R.id.btn_update_profile).setOnClickListener(v -> {
            if (new CheckInternetConn(this).isConnection())
                validationData();
            else
                showAlert("No internet connection \nPlease check your internet speed and try again..!");

        });
    }

    private void validationData() {

        UserModel user = SharedPrefManager.getInstance(this).getUser();

        String currentPass = _currentPass.getText().toString().trim();
        String newPass = _newPass.getText().toString().trim();
        String confirmNewPass = _confirmNewPass.getText().toString().trim();


        if (currentPass.isEmpty()) {
            showAlert("current Password is required...!");
            _currentPass.requestFocus();
            return;
        }
        if (currentPass.length() < 6) {
            showAlert("Password length must be 6 char...!");
            _currentPass.requestFocus();
            return;
        }

        if (newPass.isEmpty()) {
            showAlert("new Pass Password is required...!");
            _newPass.requestFocus();
            return;
        }
        if (newPass.length() < 6) {
            showAlert("newPass length must be 6 char...!");
            _newPass.requestFocus();
            return;
        }
        if (confirmNewPass.isEmpty()) {
            showAlert("confirm New Password is required...!");
            _confirmNewPass.requestFocus();
            return;
        }
        if (confirmNewPass.length() < 6) {
            showAlert("confirm New Password length must be 6 char...!");
            _confirmNewPass.requestFocus();
            return;
        }
        if (!newPass.equals(confirmNewPass)) {
            showAlert("New Password is not matching...!");
            return;
        }

        hideKeyboard(this);

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Wit .");
        progressDialog.setCancelable(false);
        progressDialog.show();

        Call<DefaultResponse> call = RetrofitClient.getInstance().getApi().updatePassword(
                currentPass,
                newPass,
                user.getEmail()
        );

        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                progressDialog.dismiss();
                showAlert(response.body().getMessage());
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
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