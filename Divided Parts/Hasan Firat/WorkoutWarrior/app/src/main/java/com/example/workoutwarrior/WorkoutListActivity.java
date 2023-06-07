package com.example.workoutwarrior;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class WorkoutListActivity extends AppCompatActivity {


    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            if(msg.obj != null)
                data = (List<Workout>) msg.obj;
            List<Workout> inverseData = new ArrayList<>();
            for(int i = 0; i < data.size();i++)
            {
                inverseData.add(i, data.get(data.size()-1-i));
            }
            WorkoutRecViewAdapter adp = new WorkoutRecViewAdapter(WorkoutListActivity.this, inverseData);
            recView.setAdapter(adp);

            return true;
        }
    });

    List<Workout> data;
    RecyclerView recView;

    ItemTouchHelper itemTouchHelper;

    private ItemTouchHelper.SimpleCallback itemTouchHelperCallback(ExecutorService srv, WorkoutRepository repo) {
        return new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            private Drawable background;

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                // Get the position of the swiped item
                int position = viewHolder.getAdapterPosition();

                // Get the workout ID associated with the swiped item
                String workoutId = data.get(data.size()-1-position).getId();

                // Change the background color of the swiped item
                viewHolder.itemView.setBackgroundColor(Color.RED);

                // Call the backend endpoint to delete the workout
                repo.deleteWorkoutFromBackend(srv, handler, workoutId);

                // Remove the item from the data source
                data.remove(data.size()-1-position);

                // Notify the adapter of the item removal
                WorkoutRecViewAdapter adp = (WorkoutRecViewAdapter) recView.getAdapter();
                adp.setWorkouts(data);      //NEWLY ADDED TO TEST WHETHER IT CONTINUES TO CRASH OR NOT (STILL CRASHES)
                adp.notifyItemRemoved(position);

            }

            // Override onChildDraw to clear the background color when the item is not being swiped
            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    // Initialize the background drawable only once
                    if (background == null) {
                        background = new ColorDrawable(Color.RED); // Change the color as desired
                    }
                    background.setBounds(viewHolder.itemView.getRight() + (int) dX, viewHolder.itemView.getTop(), viewHolder.itemView.getRight(), viewHolder.itemView.getBottom());
                    background.draw(c);
                } else {
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                }
            }
        };

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_list_layout);

        recView = findViewById(R.id.recViewWorkoutList);
        recView.setLayoutManager(new LinearLayoutManager(this));

        // Set an initial empty adapter to the RecyclerView
        WorkoutRecViewAdapter emptyAdapter = new WorkoutRecViewAdapter(WorkoutListActivity.this, new ArrayList<>());
        recView.setAdapter(emptyAdapter);


        ExecutorService srv = ((WorkoutWarriorApplication)getApplication()).srv;
        WorkoutRepository repo = new WorkoutRepository();

        // Create an instance of ItemTouchHelper with your itemTouchHelperCallback
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchHelperCallback(srv, repo));
        // Attach the ItemTouchHelper to your RecyclerView
        itemTouchHelper.attachToRecyclerView(recView);

        repo.getAllWorkouts(srv, handler, "64554b5d9ac08b7256494123");
        //userId will be predefined, modify the code afterwards!
    }
}
