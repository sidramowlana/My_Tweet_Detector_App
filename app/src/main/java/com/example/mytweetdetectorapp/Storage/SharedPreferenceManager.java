package com.example.mytweetdetectorapp.Storage;


import android.content.Context;
import android.content.SharedPreferences;

import com.example.mytweetdetectorapp.Model.User;
import com.google.gson.Gson;

public class SharedPreferenceManager {
    public static String SHARED_PREF_INFO = "SHARED_PREF_INFO";
    public static SharedPreferenceManager sharedPreferenceManagerIntance;
    private Context context;

    public SharedPreferenceManager(Context context) {
        this.context = context;
    }

    public static synchronized SharedPreferenceManager getSharedPreferenceInstance(Context context) {
        if (sharedPreferenceManagerIntance == null) {
            sharedPreferenceManagerIntance = new SharedPreferenceManager(context);
        }
        return sharedPreferenceManagerIntance;
    }

    public void saveUserSharedPref(User loginResponse) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_INFO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", loginResponse.getUsername());
        editor.putString("email", loginResponse.getEmail());
        editor.putString(SHARED_PREF_INFO, new Gson().toJson(loginResponse));
        editor.apply();
    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_INFO, Context.MODE_PRIVATE);
        if (sharedPreferences.getString("username", "") != "") {
            return true;
        }
        return false;
    }

    public User getUser() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_INFO, Context.MODE_PRIVATE);
        System.out.println(sharedPreferences.getAll());
        return new User(
                sharedPreferences.getString("username", null),
                sharedPreferences.getString("email", null)
                );
    }

    public void clear() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_INFO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
