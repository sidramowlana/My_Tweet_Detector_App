package com.example.mytweetdetectorapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.example.mytweetdetectorapp.Model.User;
import com.example.mytweetdetectorapp.Storage.SharedPreferenceManager;
import com.example.mytweetdetectorapp.databinding.ActivitySplashScreenBinding;

public class SplashScreenActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 1000;
    private ActivitySplashScreenBinding activitySplashScreenBinding;
    private User loginResponse;
    boolean isLogged;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySplashScreenBinding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        View view = activitySplashScreenBinding.getRoot();
        setContentView(view);
        loginResponse = SharedPreferenceManager.getSharedPreferenceInstance(getApplicationContext()).getUser();
        isLogged = SharedPreferenceManager.getSharedPreferenceInstance(getApplicationContext()).isLoggedIn();
        if (isLogged == true) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, SPLASH_TIME_OUT);
        }
        else
        {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, SPLASH_TIME_OUT);
        }
    }
}
