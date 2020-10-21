package com.example.mytweetdetectorapp.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mytweetdetectorapp.Activities.TweetDetail;
import com.example.mytweetdetectorapp.Adapters.TweetAdapter;
import com.example.mytweetdetectorapp.Callbacks.ItemClickCallback;
import com.example.mytweetdetectorapp.Callbacks.ResponseCallback;
import com.example.mytweetdetectorapp.Model.Tweets;
import com.example.mytweetdetectorapp.Model.User;
import com.example.mytweetdetectorapp.R;
import com.example.mytweetdetectorapp.RetrofitAPIService.TweetService;
import com.example.mytweetdetectorapp.Storage.SharedPreferenceManager;
import com.example.mytweetdetectorapp.databinding.FragmentHistoryBinding;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment implements ResponseCallback, ItemClickCallback {

    private FragmentHistoryBinding fragmentHistoryBinding;
    private RecyclerView recyclerView;
    private TweetAdapter tweetAdapter;
    private User userResponse;
    ProgressBar progressBar;
    ImageView imageView;

    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentHistoryBinding = FragmentHistoryBinding.inflate(getLayoutInflater());
        View view = fragmentHistoryBinding.getRoot();
        progressBar = fragmentHistoryBinding.progressBar;
        userResponse = SharedPreferenceManager.getSharedPreferenceInstance(getContext()).getUser();
        TweetService tweetService = new TweetService();
        tweetService.getAllUserTweets(userResponse.getUsername(), this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = fragmentHistoryBinding.commonRecycleviewId;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        tweetAdapter = new TweetAdapter(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onSuccess(Response response) {
        progressBar.setVisibility(View.GONE);
        List<Tweets> tweetList = (List<Tweets>) response.body();
        System.out.println("tweetList");
        System.out.println(tweetList);
        if (tweetList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            fragmentHistoryBinding.emptyNointernetView.setVisibility(View.VISIBLE);
            Picasso.get()
                    .load(R.drawable.empty)
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.error)
                    .into(fragmentHistoryBinding.emptyNointernetView);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            fragmentHistoryBinding.emptyNointernetView.setVisibility(View.GONE);
            tweetAdapter.setAllPostData(tweetList);
            recyclerView.setAdapter(tweetAdapter);
            tweetAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onError(String errorMessage) {
        System.out.println(errorMessage);
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        fragmentHistoryBinding.emptyNointernetView.setVisibility(View.VISIBLE);
        Picasso.get()
                .load(R.drawable.nointernet)
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(fragmentHistoryBinding.emptyNointernetView);
    }

    @Override
    public void onItemClickListener(String id) {
        startActivity(new Intent(getActivity(), TweetDetail.class).putExtra("tweetId", id));
    }
}