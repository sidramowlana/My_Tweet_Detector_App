package com.example.mytweetdetectorapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mytweetdetectorapp.Callbacks.ResponseCallback;
import com.example.mytweetdetectorapp.DTO.LoginRequest;
import com.example.mytweetdetectorapp.Model.User;
import com.example.mytweetdetectorapp.RetrofitAPIService.AuthenticationService;
import com.example.mytweetdetectorapp.Storage.SharedPreferenceManager;
import com.example.mytweetdetectorapp.databinding.ActivityLoginBinding;
import com.shashank.sony.fancytoastlib.FancyToast;

import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements ResponseCallback {

    private ActivityLoginBinding activityLoginBinding;
    private AuthenticationService authenticationService;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = activityLoginBinding.getRoot();
        setContentView(view);
        authenticationService = new AuthenticationService();
        activityLoginBinding.buttonSignupId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnSignUp();
            }
        });
        activityLoginBinding.imagebuttonLoginId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnSignIn();
            }
        });
    }

    public void onBtnSignUp() {
        intent = new Intent(getApplicationContext(), SignUpActivity.class);
        startActivity(intent);
    }

    public void onBtnSignIn() {
        String username = activityLoginBinding.edittextUsernameId.getText().toString();
        String password = activityLoginBinding.edittextPasswordId.getText().toString();
        if (!username.isEmpty() || !password.isEmpty()) {
            LoginRequest loginRequest = new LoginRequest(username, password);
            authenticationService.login(loginRequest, this);
        } else {
            FancyToast.makeText(getApplicationContext(), "Fields cannot be empty", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
        }
    }


    @Override
    public void onSuccess(Response response) {
        System.out.println(response.body());
        User user = (User) response.body();
        intent = new Intent(getApplicationContext(), MainActivity.class);
        SharedPreferenceManager.getSharedPreferenceInstance(LoginActivity.this).saveUserSharedPref(user);
        startActivity(intent);
        System.out.println(user);
    }

    @Override
    public void onError(String errorMessage) {
        FancyToast.makeText(this, "Invalid Credentials", FancyToast.LENGTH_SHORT, FancyToast.ERROR,
                false).show();
    }
}
