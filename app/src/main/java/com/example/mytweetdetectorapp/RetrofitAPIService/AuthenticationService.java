package com.example.mytweetdetectorapp.RetrofitAPIService;

import com.example.mytweetdetectorapp.Callbacks.CustomizeCallback;
import com.example.mytweetdetectorapp.Callbacks.ResponseCallback;
import com.example.mytweetdetectorapp.DTO.LoginRequest;
import com.example.mytweetdetectorapp.Model.User;
import com.example.mytweetdetectorapp.RetrofitClient.RetrofitClient;
import com.example.mytweetdetectorapp.RetrofitInterface.AuthenticationAPI;
import retrofit2.Call;

public class AuthenticationService {
    AuthenticationAPI authenticationApi;
    RetrofitClient retrofitClient;
    public AuthenticationService() {
        this.authenticationApi = retrofitClient.getRetrofitClientInstance().create(AuthenticationAPI.class);
    }

    public void login(LoginRequest loginRequest, ResponseCallback callback) {
        Call<User> loginResponseCall = authenticationApi.loginUser(loginRequest);
        loginResponseCall.enqueue(new CustomizeCallback<User>(callback));
    }

    public void register(User user, ResponseCallback callback) {
        Call<User> userCall = authenticationApi.registerUser(user);
        userCall.enqueue(new CustomizeCallback<User>(callback));
    }
}
