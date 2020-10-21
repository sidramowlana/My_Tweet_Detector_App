package com.example.mytweetdetectorapp.RetrofitAPIService;

import com.example.mytweetdetectorapp.Callbacks.CustomizeCallback;
import com.example.mytweetdetectorapp.Callbacks.ResponseCallback;
import com.example.mytweetdetectorapp.Model.Tweets;
import com.example.mytweetdetectorapp.RetrofitClient.RetrofitClient;
import com.example.mytweetdetectorapp.RetrofitInterface.TweetValidationAPI;

import java.util.List;

import retrofit2.Call;

public class TweetService {
   TweetValidationAPI tweetValidationAPI;
    RetrofitClient retrofitClient;
    public TweetService() {
        this.tweetValidationAPI = retrofitClient.getRetrofitClientInstance().create(TweetValidationAPI.class);
    }

    public void validateTweet(String username, String postId, ResponseCallback callback) {
        Call<Tweets> call = tweetValidationAPI.validateTweet(username,postId);
        call.enqueue(new CustomizeCallback<Tweets>(callback));
    }

    public void getAllUserTweets(String username,ResponseCallback callback){
        Call<List<Tweets>> call = tweetValidationAPI.getAllUserTweets(username);
        call.enqueue(new CustomizeCallback<List<Tweets>>(callback));
    }

    public void getATweet(String tweetId, ResponseCallback callback){
        Call <Tweets> call = tweetValidationAPI.getATweet(tweetId);
        call.enqueue(new CustomizeCallback<Tweets>(callback));
    }

}
























