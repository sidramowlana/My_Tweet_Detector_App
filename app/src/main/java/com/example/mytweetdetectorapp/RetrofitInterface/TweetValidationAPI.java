package com.example.mytweetdetectorapp.RetrofitInterface;

import com.example.mytweetdetectorapp.Model.Tweets;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TweetValidationAPI {

    @GET("api/check-tweet/{username}/{postId}")
    Call<Tweets> validateTweet(@Path("username") String username, @Path("postId") String postId);

    @GET("api/tweets/all/{username}")
    Call<List<Tweets>> getAllUserTweets(@Path("username") String username);

    @GET("/api/getAPost/{postid}")
    Call<Tweets> getATweet(@Path("postid") String postid);
}