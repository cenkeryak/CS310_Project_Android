package com.example.workoutwarrior;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnGoWorkout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGoWorkout = findViewById(R.id.btnGoWorkout);

        btnGoWorkout.setOnClickListener(v->{
            Intent i = new Intent(this, WorkoutSaveActivity.class);
            startActivity(i);
        });

    }

}