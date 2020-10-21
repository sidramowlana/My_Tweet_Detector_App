package com.example.mytweetdetectorapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.mytweetdetectorapp.Callbacks.ResponseCallback;
import com.example.mytweetdetectorapp.Model.Tweets;
import com.example.mytweetdetectorapp.Model.User;
import com.example.mytweetdetectorapp.R;
import com.example.mytweetdetectorapp.RetrofitAPIService.TweetService;
import com.example.mytweetdetectorapp.Storage.SharedPreferenceManager;
import com.example.mytweetdetectorapp.databinding.ActivityTweetDetailBinding;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.squareup.picasso.Picasso;

import retrofit2.Response;

public class TweetDetail extends AppCompatActivity implements ResponseCallback {

    private ActivityTweetDetailBinding activityTweetDetailBinding;
    private User userResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTweetDetailBinding = ActivityTweetDetailBinding.inflate(getLayoutInflater());
        View view = activityTweetDetailBinding.getRoot();
        setContentView(view);
        TweetService tweetService = new TweetService();
        userResponse = SharedPreferenceManager.getSharedPreferenceInstance(getApplicationContext()).getUser();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            final String tweetId = bundle.getString("tweetId");
            tweetService.getATweet(tweetId, this);
        }
    }

    @Override
    public void onSuccess(Response response) {
        Tweets tweets = (Tweets) response.body();
        if (tweets.getValidation().equals("True")) {
            Picasso.get()
                    .load(R.drawable.validatetrue)
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.error)
                    .into(activityTweetDetailBinding.imageviewValidateId);
        } else if(tweets.getValidation().equals("False")) {
            Picasso.get()
                    .load(R.drawable.validatefalse)
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.error)
                    .into(activityTweetDetailBinding.imageviewValidateId);        }
        Picasso.get()
                .load(tweets.getScaled_image())
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(activityTweetDetailBinding.detailImageViewId);
//        activityTweetDetailBinding.textviewValidationId.setText(tweets.getValidation());
        activityTweetDetailBinding.textviewDateId.setText(tweets.getDate());
        activityTweetDetailBinding.textviewTweeturlId.setText(tweets.getUrl());
        activityTweetDetailBinding.textviewTweettextId.setText(tweets.getText());
        activityTweetDetailBinding.textviewNooflikesId.setText(String.valueOf(tweets.getLikes()));
        activityTweetDetailBinding.textviewUsernameId.setText(tweets.getScreen_name());
        activityTweetDetailBinding.textviewFollowersId.setText(String.valueOf(tweets.getFollowers()));
        activityTweetDetailBinding.textviewFollowingsId.setText(String.valueOf(tweets.getFollowings()));
    }

    @Override
    public void onError(String errorMessage) {
        System.out.println("error here babes");
        FancyToast.makeText(getApplicationContext(), "Error in tweet details activity", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();

    }

}
