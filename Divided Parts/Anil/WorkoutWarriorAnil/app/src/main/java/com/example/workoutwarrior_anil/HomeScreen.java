package com.example.workoutwarrior_anil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class HomeScreen extends AppCompatActivity {

    Button btnExercise;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

        btnExercise=findViewById(R.id.btnExercise);

        btnExercise.setOnClickListener(v -> {

            Intent i = new Intent(this,ExerciseInputActivity.class);

            startActivity(i);

        });
    }
}