package com.techelevator.model;

import java.time.LocalDate;

public class MealPlan {
    private int userId;
    private int id;
    private int mealId;
    private LocalDate dateToCook;

    public MealPlan(){}
    public MealPlan(int userId, int id, int mealId, LocalDate dateToCook) {
        this.userId = userId;
        this.id = id;
        this.mealId = mealId;
        this.dateToCook = dateToCook;
    }

    public MealPlan(int mealId, LocalDate dateToCook){
        this.dateToCook = dateToCook;
        this.mealId = mealId;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMealId() {
        return mealId;
    }

    public void setMealId(int mealId) {
        this.mealId = mealId;
    }

    public LocalDate getDateToCook() {
        return dateToCook;
    }

    public void setDateToCook(LocalDate dateToCook) {
        this.dateToCook = dateToCook;
    }
}
