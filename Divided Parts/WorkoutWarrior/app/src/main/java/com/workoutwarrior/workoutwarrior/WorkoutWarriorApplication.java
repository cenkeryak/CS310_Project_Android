package com.workoutwarrior.workoutwarrior;

import android.app.Application;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WorkoutWarriorApplication extends Application {
    ExecutorService srv = Executors.newCachedThreadPool();

}
