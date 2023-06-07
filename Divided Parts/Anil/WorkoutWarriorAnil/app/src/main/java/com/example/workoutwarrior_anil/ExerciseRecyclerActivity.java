package com.example.workoutwarrior_anil;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.window.OnBackInvokedCallback;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;


public class ExerciseRecyclerActivity extends AppCompatActivity {

    RecyclerView recView;


    Exercise exercise;
    Handler handler= new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            List<Exercise> exerciseList= (ArrayList<Exercise>)msg.obj;
            if(exerciseList.size()==0){
                Snackbar.make(findViewById(R.id.recylerview),"No matching exercises", Snackbar.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(ExerciseRecyclerActivity.this, ExerciseInputActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }, 1000);
                //Intent intent=new Intent(ExerciseRecyclerActivity.this,ExerciseInputActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                //startActivity(intent);
            }else{
                ExerciseRecyclerAdapter adapter= new ExerciseRecyclerAdapter(ExerciseRecyclerActivity.this,exerciseList,(obj)->{
                    Intent intent= new Intent(ExerciseRecyclerActivity.this, ExerciseDetail.class);
                    intent.putExtra("exercise",obj);
                    startActivity(intent);
                });
                recView.setAdapter(adapter);
            }

            return true;
        }
    });




    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_recycler);



        Exercise exercise= (Exercise)getIntent().getExtras().get("exerciseObj");




        recView=findViewById(R.id.recylerview);
        recView.setLayoutManager(new LinearLayoutManager(this));

        WorkoutwarriorRepo repo= new WorkoutwarriorRepo();

        ExecutorService srv= ((WorkoutWarriorApplication)getApplication()).srv;
        repo.getExercises(srv,handler,exercise);


    }


}