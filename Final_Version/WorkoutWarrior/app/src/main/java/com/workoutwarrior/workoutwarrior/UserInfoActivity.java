package com.workoutwarrior.workoutwarrior;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutorService;

public class UserInfoActivity extends AppCompatActivity {

    TextView nameField, ageField, genderField, heightField, weightField, weightCategoryField, bmiField;

    Button editUserButton;

    SharedPreferences sharedPreferences;

    Handler userInfoHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {

            UserInfo userInfo = (UserInfo) msg.obj;
            if (userInfo.getUserId() != "" && userInfo.getUserId() != null && userInfo.getUserId() != "null" ){
                nameField.setText(userInfo.getName());
                ageField.setText("Age: "+String.valueOf(userInfo.getAge()));
                genderField.setText("Gender: "+userInfo.getGender());
                heightField.setText("Height: "+String.valueOf(userInfo.getHeight())+ " m");
                weightField.setText("Weight: "+String.valueOf(userInfo.getWeight()) + " kg");
                weightCategoryField.setText(userInfo.getWeightCategory());

                bmiField.setText("BMI: "+String.format("%.3f", userInfo.getBmi()));

            } else {
                // TODO: CHECK1 , Check is it working
                Log.e("DEV","User is not found");
            }
            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        nameField = (TextView) findViewById(R.id.userInfoName);
        ageField = (TextView) findViewById(R.id.userInfoAge);
        genderField = (TextView) findViewById(R.id.userInfoGender);
        heightField = (TextView) findViewById(R.id.userInfoHeight);
        weightField = (TextView) findViewById(R.id.userInfoWeight);
        weightCategoryField = (TextView) findViewById(R.id.userInfoWeightCategory);
        bmiField = (TextView) findViewById(R.id.userInfoBmi);
        editUserButton = (Button) findViewById(R.id.editUserButton);

        sharedPreferences = getSharedPreferences("userInformation",MODE_PRIVATE);


        editUserButton.setOnClickListener(v -> {
            Intent editUserIntent = new Intent(this,UpdateUserActivity.class);
            startActivity(editUserIntent);
        });


    }

    @Override
    protected void onResume() {
        super.onResume();


        String userId = sharedPreferences.getString("userId","");
        WorkoutwarriorRepo repo = new WorkoutwarriorRepo();
        ExecutorService srv = ((WorkoutWarriorApplication)getApplication()).srv;

        repo.getUserInfo(srv,userInfoHandler,userId);
    }
}