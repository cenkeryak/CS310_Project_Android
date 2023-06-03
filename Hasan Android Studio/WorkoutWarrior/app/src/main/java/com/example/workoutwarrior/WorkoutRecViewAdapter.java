package com.example.workoutwarrior;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class WorkoutRecViewAdapter extends RecyclerView.Adapter<WorkoutRecViewAdapter.ListHolder> {

    private Context ctx;
    private List<Workout> workouts;

    public WorkoutRecViewAdapter(Context ctx, List<Workout> workouts) {
        this.ctx = ctx;
        this.workouts = workouts;
    }

    public void setWorkouts(List<Workout> workouts) {
        this.workouts = workouts;
    }

    @NonNull
    @Override
    public ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(ctx).inflate(R.layout.workout_row_layout, parent, false);
        ListHolder holder = new ListHolder(root);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListHolder holder, int position) {
        Workout workout = workouts.get(position);
        List<Workout.Exercise> exercises = workout.getExercise();

        // Clear the previous values
        holder.textDate.setText("");
        holder.exerciseRecView.setAdapter(null);

        // Define the input and output date formats
        String inputFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS";
        String outputFormat = "dd/MM/yyyy";

        // Parse the input date string
        SimpleDateFormat inputDateFormat = new SimpleDateFormat(inputFormat, Locale.getDefault());
        Date date = null;
        try {
            date = inputDateFormat.parse(workout.getDate());
        } catch (ParseException e) {
            Log.e("DEV", e.getMessage());
        }

        // Format the date to the desired output format
        SimpleDateFormat outputDateFormat = new SimpleDateFormat(outputFormat, Locale.getDefault());
        String formattedDate = outputDateFormat.format(date);

        // Set the formatted date to the TextView
        holder.textDate.setText(formattedDate);

       // holder.textDate.setText(workout.getDate());

        // Set up the inner RecyclerView for exercises
        ExerciseRecViewAdapter exerciseAdapter = new ExerciseRecViewAdapter(exercises);
        holder.exerciseRecView.setAdapter(exerciseAdapter);
    }

    @Override
    public int getItemCount() {
        return workouts.size();
    }

    class ListHolder extends RecyclerView.ViewHolder {

        ConstraintLayout constraintRow;
        TextView textDate;
        RecyclerView exerciseRecView;

        public ListHolder(@NonNull View itemView) {
            super(itemView);
            constraintRow = itemView.findViewById(R.id.constraintWorkoutRow);
            textDate = itemView.findViewById(R.id.textRowDate);
            exerciseRecView = itemView.findViewById(R.id.recyclerViewExercises);

            // Set up the layout manager for the inner RecyclerView
            exerciseRecView.setLayoutManager(new LinearLayoutManager(ctx));
        }
    }

    class ExerciseRecViewAdapter extends RecyclerView.Adapter<ExerciseRecViewAdapter.ExerciseViewHolder> {

        private List<Workout.Exercise> exercises;

        public ExerciseRecViewAdapter(List<Workout.Exercise> exercises) {
            this.exercises = exercises;
        }

        @NonNull
        @Override
        public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_row_layout, parent, false);
            return new ExerciseViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
            Workout.Exercise exercise = exercises.get(position);

            holder.textExercise.setText(exercise.getName());
            holder.textSetCount.setText("Number of sets: " + String.valueOf(exercise.getSetCount()));
            holder.textRepCount.setText("Number of reps in a set: " + String.valueOf(exercise.getRepCountInASet()));
            if(exercise.getAdditionalNote() != null)
                holder.textAdditionalNote.setText("Additional note: " + exercise.getAdditionalNote());
            else
            {
                holder.textAdditionalNote.setVisibility(View.GONE);
            }
        }

        @Override
        public int getItemCount() {
            return exercises.size();
        }

        class ExerciseViewHolder extends RecyclerView.ViewHolder {

            TextView textExercise;
            TextView textSetCount;
            TextView textRepCount;
            TextView textAdditionalNote;

            public ExerciseViewHolder(@NonNull View itemView) {
                super(itemView);
                textExercise = itemView.findViewById(R.id.textRowExercise);
                textSetCount = itemView.findViewById(R.id.textRowSetCount);
                textRepCount = itemView.findViewById(R.id.textRowRepCount);
                textAdditionalNote = itemView.findViewById(R.id.textRowAdditionalNote);
            }
        }
    }
}
