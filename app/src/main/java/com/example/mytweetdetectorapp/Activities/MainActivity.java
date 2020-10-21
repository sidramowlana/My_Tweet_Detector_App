package com.example.mytweetdetectorapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.mytweetdetectorapp.Fragments.HistoryFragment;
import com.example.mytweetdetectorapp.Fragments.ValidateTweetFragment;
import com.example.mytweetdetectorapp.R;
import com.example.mytweetdetectorapp.databinding.ActivityMainBinding;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ActivityMainBinding activityMainBinding;
    ChipNavigationBar chipNavigationBar;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = activityMainBinding.getRoot();
        setContentView(view);

        chipNavigationBar = activityMainBinding.chipnavigationbarNavigationId;
        if (savedInstanceState == null) {
            chipNavigationBar.setItemSelected(R.id.validate, true);
            fragmentManager = getSupportFragmentManager();
            ValidateTweetFragment validateTweetFragment = new ValidateTweetFragment();
            fragmentManager.beginTransaction().replace(R.id.framelayout_container_id, validateTweetFragment).commit();
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_container_id, new ValidateTweetFragment());
        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                Fragment fragment = null;
                switch (i) {
                    case R.id.validate:
                        fragment = new ValidateTweetFragment();
                        break;
                    case R.id.history:
                        fragment = new HistoryFragment();
                        break;
                }
                if (fragment != null) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.framelayout_container_id, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                } else {
                    Log.e(TAG, "Error in creating the fragment");
                }
            }
        });
    }

}
