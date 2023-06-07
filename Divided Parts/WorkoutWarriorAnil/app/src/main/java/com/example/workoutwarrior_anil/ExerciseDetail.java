package com.example.workoutwarrior_anil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

public class ExerciseDetail extends AppCompatActivity {

    TextView exerciseName;
    TextView exerciseMuscle;
    TextView exerciseDifficulty;
    TextView equipmentText;
    TextView exerciseInstructions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_detail);
        exerciseName=findViewById(R.id.exerciseDetailName);
        exerciseMuscle=findViewById(R.id.exerciseDetailMuscle);
        exerciseDifficulty=findViewById(R.id.exerciseDetailDifficulty);
        exerciseInstructions=findViewById(R.id.exerciseDetailInstructions);
        equipmentText=findViewById(R.id.exerciseDetailEquipment);

        Exercise exercise=(Exercise)getIntent().getExtras().get("exercise");
        exerciseName.setText(exercise.getName());
        exerciseMuscle.setText(exercise.getMuscle());
        exerciseDifficulty.setText(exercise.getDifficulty());

        StringBuilder builder= new StringBuilder();
        builder.append("Equipment needed: ");
        builder.append(exercise.getEquipment());
        equipmentText.setText(builder.toString());

        exerciseInstructions.setText(exercise.getInstructions());


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==android.R.id.home){
            onBackPressed();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.i("DEV","BACK");
        finish();

    }
}