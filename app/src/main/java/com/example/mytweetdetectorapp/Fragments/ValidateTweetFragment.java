package com.example.mytweetdetectorapp.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;

import com.example.mytweetdetectorapp.Activities.LoginActivity;
import com.example.mytweetdetectorapp.Callbacks.ResponseCallback;
import com.example.mytweetdetectorapp.Model.User;
import com.example.mytweetdetectorapp.R;
import com.example.mytweetdetectorapp.RetrofitAPIService.TweetService;
import com.example.mytweetdetectorapp.Storage.SharedPreferenceManager;
import com.example.mytweetdetectorapp.databinding.FragmentValidateTweetBinding;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.squareup.picasso.Picasso;

import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ValidateTweetFragment extends Fragment implements ResponseCallback {

    private FragmentValidateTweetBinding fragmentValidateTweetBinding;
    private TweetService tweetService;
    private Intent intent;
    private User userResponse;

    public ValidateTweetFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentValidateTweetBinding = FragmentValidateTweetBinding.inflate(getLayoutInflater());
        View view = fragmentValidateTweetBinding.getRoot();
        userResponse = SharedPreferenceManager.getSharedPreferenceInstance(getContext()).getUser();
        getActivity().setTitle("Validate");
        Picasso.get()
                .load(R.drawable.twittervalidator)
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(fragmentValidateTweetBinding.imageviewIconId);
        tweetService = new TweetService();
        fragmentValidateTweetBinding.buttonValidateId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnValidateTweetURL();
            }
        });
        fragmentValidateTweetBinding.buttonLogoutId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLogout();
            }
        });
        return view;
    }

    public void onLogout() {
        SharedPreferenceManager.getSharedPreferenceInstance(getContext()).clear();
        startActivity(new Intent(getContext(), LoginActivity.class));
        getActivity().finish();
    }

    public void onBtnValidateTweetURL() {
        String tweetUrl = fragmentValidateTweetBinding.edittextTweeturlId.getText().toString();
        System.out.println(tweetUrl);
        boolean isValid = URLUtil.isValidUrl(tweetUrl);
        if (isValid == true) {
            String postId = tweetUrl.split("status/")[1];
            if (!tweetUrl.isEmpty()) {
                tweetService.validateTweet(userResponse.getUsername(), postId, this);
                fragmentValidateTweetBinding.relativelayoutMainId.setVisibility(View.GONE);
                fragmentValidateTweetBinding.linearlayoutLoadingId.setVisibility(View.VISIBLE);
                fragmentValidateTweetBinding.buttonLogoutId.setVisibility(View.GONE);

            } else {
                FancyToast.makeText(getContext(), "Fields cannot be empty", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
            }
        } else {
            FancyToast.makeText(getContext(), "Please give valid url", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onSuccess(Response response) {
        fragmentValidateTweetBinding.edittextTweeturlId.setText("");
        ChipNavigationBar chipNavigationBar = (ChipNavigationBar) getActivity().findViewById(R.id.chipnavigationbar_navigation_id);
        chipNavigationBar.setItemSelected(R.id.history, true);
        Fragment fragment = new HistoryFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.framelayout_container_id, fragment).commit();
    }

    @Override
    public void onError(String errorMessage) {
        fragmentValidateTweetBinding.buttonLogoutId.setVisibility(View.GONE);
        fragmentValidateTweetBinding.relativelayoutMainId.setVisibility(View.VISIBLE);
        fragmentValidateTweetBinding.linearlayoutLoadingId.setVisibility(View.GONE);
        FancyToast.makeText(getContext(), "Something went wrong please try again later", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
    }
}
