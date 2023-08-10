package com.techelevator.model;

import org.springframework.stereotype.Component;

import java.util.List;


public class Meal {

    private int mealId;
    private int userId;
    private String mealName;

    private String mealType;

    public Meal (){};

    public Meal (int mealId, int userId, String mealName, String mealType) {
        this.mealId = mealId;
        this.userId = userId;
        this.mealName = mealName;
        this.mealType = mealType;
    }

    public int getMealId() {
        return mealId;
    }

    public void setMealId(int mealId) {
        this.mealId = mealId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getMealName(){
        return mealName;
    }

    public void setMealName(String mealName){
        this.mealName = mealName;
    }

    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }
}
