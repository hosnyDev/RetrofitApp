package com.hosnydevtest.retrofitapp.ui.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.hosnydevtest.retrofitapp.R;
import com.hosnydevtest.retrofitapp.storage.SharedPrefManager;
import com.hosnydevtest.retrofitapp.ui.MainActivity;

public class ChooseAuthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_auth);
        findViewById(R.id.btn_choose_login).setOnClickListener(v -> startActivity(new Intent(ChooseAuthActivity.this, LoginActivity.class)));
        findViewById(R.id.btn_choose_register).setOnClickListener(v -> startActivity(new Intent(ChooseAuthActivity.this, RegisterActivity.class)));
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (SharedPrefManager.getInstance(this).isLogin()) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}