package com.workoutwarrior.workoutwarrior;

public class UserInfo {
    private String userId;
    private String name;
    private int age;
    private double weight,height,bmi;
    private String gender;
    private String weightCategory;

    public UserInfo() {
    }

    public UserInfo(String userId, String name, int age, double weight, double height, double bmi, String gender, String weightCategory) {
        this.userId = userId;
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.bmi = bmi;
        this.gender = gender;
        this.weightCategory = weightCategory;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getBmi() {
        return bmi;
    }

    public void setBmi(double bmi) {
        this.bmi = bmi;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getWeightCategory() {
        return weightCategory;
    }

    public void setWeightCategory(String weightCategory) {
        this.weightCategory = weightCategory;
    }
}
