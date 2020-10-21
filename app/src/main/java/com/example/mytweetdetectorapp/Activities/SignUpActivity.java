package com.example.mytweetdetectorapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;

import com.example.mytweetdetectorapp.Callbacks.ResponseCallback;
import com.example.mytweetdetectorapp.Model.User;
import com.example.mytweetdetectorapp.R;
import com.example.mytweetdetectorapp.RetrofitAPIService.AuthenticationService;
import com.example.mytweetdetectorapp.databinding.ActivityLoginBinding;
import com.example.mytweetdetectorapp.databinding.ActivitySignUpBinding;
import com.shashank.sony.fancytoastlib.FancyToast;

import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity implements ResponseCallback {

    private ActivitySignUpBinding activitySignUpBinding;
    private AuthenticationService authenticationService;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySignUpBinding = ActivitySignUpBinding.inflate(getLayoutInflater());
        View view = activitySignUpBinding.getRoot();
        setContentView(view);
        authenticationService = new AuthenticationService();
        activitySignUpBinding.buttonSignupId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnSignUp();
            }
        });
        activitySignUpBinding.imagebuttonLoginId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnSignIn();
            }
        });
    }

    public void onBtnSignUp() {
        intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    public void onBtnSignIn() {
        String username = activitySignUpBinding.edittextUsernameId.getText().toString();
        String email = activitySignUpBinding.edittextEmailId.getText().toString();
        String password = activitySignUpBinding.edittextPasswordId.getText().toString();
        if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
            FancyToast.makeText(getApplicationContext(), "Fields cannot be empty", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            FancyToast.makeText(getApplicationContext(), "Please enter valid email address", FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show();
        } else {
            User user = new User(username, email, password);
            authenticationService.register(user, this);
        }
    }

    @Override
    public void onSuccess(Response response) {
        System.out.println("babes luffy");
        User user = (User) response.body();
        assert user != null;
        System.out.println(user.getUsername());
        if (user.getUsername() != null) {
            intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        } else {
            FancyToast.makeText(getApplicationContext(), "Username already exixst! Try a different username", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
        }
    }

    @Override
    public void onError(String errorMessage) {
        System.out.println("herer babes");
        System.out.println(errorMessage);
        FancyToast.makeText(getApplicationContext(), errorMessage, FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();

    }
}
