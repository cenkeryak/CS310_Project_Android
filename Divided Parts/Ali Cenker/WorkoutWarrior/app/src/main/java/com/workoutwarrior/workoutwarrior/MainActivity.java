package com.workoutwarrior.workoutwarrior;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("userInformation",MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId","");

        if (userId == null || userId == ""){
            Intent switchWelcomeIntent = new Intent(this, WelcomeActivity.class);
            startActivity(switchWelcomeIntent);
            finish();
        } else{
            // TODO main menu activity will be started instead of userinfo
            Intent switchMenuIntent = new Intent(this, UserInfoActivity.class);
            startActivity(switchMenuIntent);
        }


    }
}