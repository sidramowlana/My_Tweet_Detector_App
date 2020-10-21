package com.example.mytweetdetectorapp.RetrofitInterface;

import com.example.mytweetdetectorapp.DTO.LoginRequest;
import com.example.mytweetdetectorapp.Model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthenticationAPI {
    @POST("api/login")
    Call<User> loginUser(@Body LoginRequest loginRequest);

    @POST("api/register")
    Call<User> registerUser(@Body User user);
}
