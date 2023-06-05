package com.workoutwarrior.workoutwarrior;

import java.io.Serializable;
import java.util.List;

public class Workout implements Serializable {

    private String id;
    private String date;
    private List<Exercise> exercise;

    public Workout(){}

    public Workout(String id, String date, List<Exercise> exercise) {
        this.id = id;
        this.date = date;
        this.exercise = exercise;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Exercise> getExercise() {
        return exercise;
    }

    public void setExercise(List<Exercise> exercise) {
        this.exercise = exercise;
    }

    public static class Exercise{
        private String name;
        private int setCount;
        private int repCountInASet;       //Rep count in a set

        private String additionalNote;

        public Exercise(){}

        public Exercise(String name, int setCount, int repCountInASet) {
            this.name = name;
            this.setCount = setCount;
            this.repCountInASet = repCountInASet;
        }

        public Exercise(String name, int setCount, int repCountInASet, String additionalNote) {
            this.name = name;
            this.setCount = setCount;
            this.repCountInASet = repCountInASet;
            this.additionalNote = additionalNote;
        }

        public String getAdditionalNote() {
            return additionalNote;
        }

        public void setAdditionalNote(String additionalNote) {
            this.additionalNote = additionalNote;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getSetCount() {
            return setCount;
        }

        public void setSetCount(int setCount) {
            this.setCount = setCount;
        }

        public int getRepCountInASet() {
            return repCountInASet;
        }

        public void setRepCountInASet(int repCountInASet) {
            this.repCountInASet = repCountInASet;
        }


    }
}
