package com.example.workoutwarrior;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class WorkoutSaveActivity extends AppCompatActivity {

    private Button btnListAllWorkouts;
    private EditText editTextExerciseName;
    private EditText editTextSetCount;
    private EditText editTextRepCount;
    private EditText editTextAdditionalNote;
    private Button btnSaveWorkout;
    private Button btnPostWorkouts;

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            return true;
        }
    });

    List<Workout.Exercise> exerciseList = new ArrayList<>();

    private WorkoutRepository.Callback postWorkoutCallback = new WorkoutRepository.Callback() {
        @Override
        public void onSuccess() {
            runOnUiThread(() -> {
                // Clear the temporary list
                exerciseList.clear();

                // Display success message
                Toast.makeText(WorkoutSaveActivity.this, "Workout successfully created!", Toast.LENGTH_SHORT).show();
            });
        }

        @Override
        public void onError() {
            runOnUiThread(() -> {
                // Workout creation failed
                Toast.makeText(WorkoutSaveActivity.this, "Failed to create workout. Please try again.", Toast.LENGTH_SHORT).show();
            });
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_screen_layout);

        // Bind views
        btnListAllWorkouts = findViewById(R.id.btnGetAll);
        editTextExerciseName = findViewById(R.id.editTextExerciseName);
        editTextSetCount = findViewById(R.id.editTextSetCount);
        editTextRepCount = findViewById(R.id.editTextRepCount);
        editTextAdditionalNote = findViewById(R.id.editTextAdditionalNote);
        btnSaveWorkout = findViewById(R.id.btnSaveWorkout);
        btnPostWorkouts = findViewById(R.id.btnPostWorkout);

        btnListAllWorkouts.setOnClickListener(v->{
            Intent i = new Intent(this, WorkoutListActivity.class);
            startActivity(i);
        });

        ExecutorService srv = ((WorkoutWarriorApplication)getApplication()).srv;

        btnSaveWorkout.setOnClickListener(v -> {
            String exerciseName = editTextExerciseName.getText().toString().trim();
            String setCountStr = editTextSetCount.getText().toString().trim();
            String repCountStr = editTextRepCount.getText().toString().trim();

            if (exerciseName.isEmpty() || setCountStr.isEmpty() || repCountStr.isEmpty()) {
                Toast.makeText(this, "Please enter all necessary fields", Toast.LENGTH_SHORT).show();
            } else {
                editTextExerciseName.setEnabled(false);
                editTextSetCount.setEnabled(false);
                editTextRepCount.setEnabled(false);
                editTextAdditionalNote.setEnabled(false);
                btnSaveWorkout.setEnabled(false);

                int setCount = Integer.parseInt(setCountStr);
                int repCount = Integer.parseInt(repCountStr);
                String additionalNote = editTextAdditionalNote.getText().toString().trim();

                Workout.Exercise exercise = new Workout.Exercise(exerciseName, setCount, repCount);
                exercise.setAdditionalNote(additionalNote);

                exerciseList.add(exercise);

                // Clear input fields
                editTextExerciseName.setText("");
                editTextSetCount.setText("");
                editTextRepCount.setText("");
                editTextAdditionalNote.setText("");


                // Simulate refreshing effect with a delay
                handler.postDelayed(() -> {
                    // Enable views
                    editTextExerciseName.setEnabled(true);
                    editTextSetCount.setEnabled(true);
                    editTextRepCount.setEnabled(true);
                    editTextAdditionalNote.setEnabled(true);
                    btnSaveWorkout.setEnabled(true);
                }, 100); // Delay in milliseconds (adjust as desired)


                // Display success message
                Toast.makeText(this, "Exercise added to the list!", Toast.LENGTH_SHORT).show();
            }
        });

        btnPostWorkouts.setOnClickListener(v -> {
            if (!exerciseList.isEmpty()) {
                WorkoutRepository repo = new WorkoutRepository();
                repo.postWorkout(srv, handler, "64554b5d9ac08b7256494123", exerciseList, postWorkoutCallback);

                //Following code pieces will make the refreshing effect:
                editTextExerciseName.setEnabled(false);
                editTextSetCount.setEnabled(false);
                editTextRepCount.setEnabled(false);
                editTextAdditionalNote.setEnabled(false);
                btnSaveWorkout.setEnabled(false);
                btnPostWorkouts.setEnabled(false);

                // Clear input fields
                editTextExerciseName.setText("");
                editTextSetCount.setText("");
                editTextRepCount.setText("");
                editTextAdditionalNote.setText("");

                // Simulate refreshing effect with a delay
                handler.postDelayed(() -> {
                    // Enable views
                    editTextExerciseName.setEnabled(true);
                    editTextSetCount.setEnabled(true);
                    editTextRepCount.setEnabled(true);
                    editTextAdditionalNote.setEnabled(true);
                    btnSaveWorkout.setEnabled(true);
                    btnPostWorkouts.setEnabled(true);
                }, 500); // Delay in milliseconds (adjust as desired)

            } else {
                Toast.makeText(this, "Fill the necessary input fields and click + button before saving!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
