package com.workoutwarrior.workoutwarrior;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class HomeScreen extends AppCompatActivity {

    Button btnExercise;
    Button btnUserInfo;
    Button buttonWorkout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

        btnExercise=findViewById(R.id.btnExercise);

        btnExercise.setOnClickListener(v -> {

            Intent i = new Intent(this,ExerciseInputActivity.class);

            startActivity(i);

        });

        btnUserInfo=findViewById(R.id.buttonUserProfile);

        btnUserInfo.setOnClickListener(v->{
            Intent i = new Intent(this,UserInfoActivity.class);

            startActivity(i);
        });

        buttonWorkout=findViewById(R.id.buttonWorkout);

        buttonWorkout.setOnClickListener(v -> {
            Intent i = new Intent(this,WorkoutSaveActivity.class);

            startActivity(i);
        });


    }
}