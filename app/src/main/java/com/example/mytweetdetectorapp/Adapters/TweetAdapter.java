package com.example.mytweetdetectorapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytweetdetectorapp.Callbacks.ItemClickCallback;
import com.example.mytweetdetectorapp.Model.Tweets;
import com.example.mytweetdetectorapp.R;
import com.example.mytweetdetectorapp.databinding.TweetItemBinding;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import lombok.SneakyThrows;
import lombok.val;

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder> {

    TweetItemBinding tweetItemBinding;
    Context context;
    List<Tweets> tweetList;
    private ItemClickCallback itemClickCallback;

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tweetUrl;
        CardView cardView;
        ProgressBar progressBar;
        TextView progressPercentage;
        TextView date;
        RelativeLayout relativeLayout;
        ImageButton imageButton;

        public ViewHolder(TweetItemBinding itemBinding) {
            super(itemBinding.getRoot());
            imageView = itemBinding.itemImageViewId;
            tweetUrl = itemBinding.itemTextViewUrl;
            date = itemBinding.itemTextViewDate;
            cardView = itemBinding.cardViewId;
            relativeLayout = itemBinding.relativeLayoutValdiateId;
            imageButton = itemBinding.imagebuttonShareId;
//            progressBar =itemBinding.itemProrogressbarId;
//            progressPercentage = itemBinding.itemTextPercentageId;
        }
    }


    public TweetAdapter(ItemClickCallback itemClickCallback) {
        this.itemClickCallback = itemClickCallback;
    }

    public void setAllPostData(List<Tweets> tweetList) {
        this.tweetList = tweetList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        tweetItemBinding = TweetItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(tweetItemBinding);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Tweets tweets = tweetList.get(position);
        if (tweets.getValidation().equals("True")) {
            holder.relativeLayout.setBackgroundColor(Color.parseColor("#008211"));
        } else if (tweets.getValidation().equals("Fake")) {
            holder.relativeLayout.setBackgroundColor(Color.parseColor("#ff0000"));
        }
        Picasso.get()
                .load(tweets.getScaled_image())
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(holder.imageView);
        holder.tweetUrl.setText(tweets.getUrl());
        holder.date.setText(tweets.getDate());
//        holder.progressBar.setProgress((int) tweets.getPercentage());
//        holder.progressPercentage.setText(String.valueOf(tweets.getPercentage()+" %"));
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @SneakyThrows
            @Override
            public void onClick(View v) {
                JSONObject obj = new JSONObject(tweets.get_id().toString());
                String id = obj.getString("$oid");
                itemClickCallback.onItemClickListener(id);
            }
        });
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @SneakyThrows
            @Override
            public void onClick(View view) {
                JSONObject obj = new JSONObject(tweets.get_id().toString());
                String id = obj.getString("$oid");
                itemClickCallback.onItemClickListener(id);
            }
        });
        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShare(tweets);
            }
        });
    }

    public void onShare(Tweets shareTweet) {
        String share = "This tweet for the url " + "\n" + "\n" + shareTweet.getUrl() + "\n" + "is " + shareTweet.getValidation();

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, share);
        sendIntent.setType("text/plain");
        Intent shareIntent = Intent.createChooser(sendIntent, null);
        context.startActivity(shareIntent);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getCurrentDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String currentDate = dtf.format(now);
        System.out.println(dtf.format(now));
        return currentDate;
    }

    @Override
    public int getItemCount() {
        return tweetList.size();
    }
}
