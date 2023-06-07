package com.example.workoutwarrior_anil;

import android.os.Parcelable;

import java.io.Serializable;

public class Exercise implements Serializable {





        private String name;
        private String type;
        private String muscle;
        private String equipment;
        private String difficulty;
        private String instructions;




        public Exercise() {
            super();
        }

        public Exercise(String name, String type, String muscle, String equipment, String difficulty, String instructions) {
            super();
            this.name = name;
            this.type = type;
            this.muscle = muscle;
            this.equipment = equipment;
            this.difficulty = difficulty;
            this.instructions = instructions;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getMuscle() {
            return muscle;
        }

        public void setMuscle(String muscle) {
            this.muscle = muscle;
        }

        public String getEquipment() {
            return equipment;
        }

        public void setEquipment(String equipment) {
            this.equipment = equipment;
        }

        public String getDifficulty() {
            return difficulty;
        }

        public void setDifficulty(String difficulty) {
            this.difficulty = difficulty;
        }

        public String getInstructions() {
            return instructions;
        }

        public void setInstructions(String instructions) {
            this.instructions = instructions;
        }

        @Override
        public String toString() {
            return "Exercise [name=" + name + ", type=" + type + ", muscle=" + muscle + ", equipment=" + equipment
                    + ", difficulty=" + difficulty + ", instructions=" + instructions + "]";
        }







}
