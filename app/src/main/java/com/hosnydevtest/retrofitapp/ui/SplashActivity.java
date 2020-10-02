package com.hosnydevtest.retrofitapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.hosnydevtest.retrofitapp.R;
import com.hosnydevtest.retrofitapp.ui.auth.ChooseAuthActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, ChooseAuthActivity.class));
            finish();
        }, 1000);
    }
}