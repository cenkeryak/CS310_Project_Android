package com.example.workoutwarrior_anil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

public class ExerciseInputActivity extends AppCompatActivity {

    EditText nameInput;
    EditText typeInput;
    EditText muscleInput;
    EditText difficultyInput;

    Button submitBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_input);

        nameInput=findViewById(R.id.nameInput);
        typeInput=findViewById(R.id.typeInput);
        muscleInput=findViewById(R.id.muscleInput);
        difficultyInput=findViewById(R.id.difficultyInput);

        submitBtn=findViewById(R.id.btnSubmit);

        submitBtn.setOnClickListener(v -> {

            String name= nameInput.getText().toString();
            String type=typeInput.getText().toString();
            String muscle=muscleInput.getText().toString();
            String difficulty=difficultyInput.getText().toString();

            if(name.equals("") && type.equals("") && muscle.equals("") && difficulty.equals("")) {

                Snackbar.make(findViewById(R.id.root), "Please provide at least 1 parameter", Snackbar.LENGTH_SHORT).show();
            }else{
                Intent intent = new Intent(ExerciseInputActivity.this, ExerciseRecyclerActivity.class);
                Exercise exercise= new Exercise();
                exercise.setName(name);
                exercise.setDifficulty(difficulty);
                exercise.setType(type);
                exercise.setMuscle(muscle);

                intent.putExtra("exerciseObj",exercise);
                startActivity(intent);
            }

        });

    }
}