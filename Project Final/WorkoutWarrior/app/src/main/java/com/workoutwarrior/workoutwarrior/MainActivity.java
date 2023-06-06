package com.workoutwarrior.workoutwarrior;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.concurrent.ExecutorService;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    String userId;
    UserInfo userInfo;

    Handler userExistCheckHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            userInfo = (UserInfo) msg.obj;

            // TODO: Check if user exist
            if (userInfo.getUserId() == null || userInfo.getUserId() == "" || userInfo.getUserId() == "null"){
                Log.e("DEV","User is not in database, stored user id will be set to null");
                sharedPreferences.edit().putString("userId","").commit();
                Intent switchWelcomeIntent = new Intent(MainActivity.this, WelcomeActivity.class);
                switchWelcomeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(switchWelcomeIntent);
            }

            return true;
        }
    });



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("userInformation",MODE_PRIVATE);
        userId = sharedPreferences.getString("userId","");

        // Checking if user id exist in database.
        WorkoutwarriorRepo repo = new WorkoutwarriorRepo();
        ExecutorService srv = ((WorkoutWarriorApplication)getApplication()).srv;



        // TODO: add true here if you want to see create user screen always
        if (userId == null || userId == "" ){
            Intent switchWelcomeIntent = new Intent(MainActivity.this, WelcomeActivity.class);
            startActivity(switchWelcomeIntent);
            finish();
        } else{
            // Check if user exist in database (see the handler for details)
            repo.getUserInfo(srv,userExistCheckHandler,userId);

            Intent switchMenuIntent = new Intent(MainActivity.this, HomeScreen.class);
            startActivity(switchMenuIntent);
            finish();
        }





    }
}