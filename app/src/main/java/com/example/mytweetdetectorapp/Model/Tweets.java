package com.example.mytweetdetectorapp.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tweets {

    private Object _id;
//    public String postId;
//    public String url;
//    public String text;
//    public String scaled_image;
//    public String validation;
//    public String date;
//    public String username;
//    public String screen_name;
//    public String created_date;
//    public int followings;
//    public int followers;
//    public int likes;


     public String created_date;
    public String date;
    public int followers;
    public int followings;
    public int likes;
    public String postId;
    public String scaled_image;
    public String screen_name;
    public String text;
    public String username;
    public String url;
    public String validation;
}