package com.techelevator.model;

import java.time.LocalDate;

public class MealPlan {
    private int userId;
    private int id;
    private int plannedMealId;
    private LocalDate dateToCook;

    public MealPlan(){}
    public MealPlan(int userId, int id, int plannedMealId, LocalDate dateToCook) {
        this.userId = userId;
        this.id = id;
        this.plannedMealId = plannedMealId;
        this.dateToCook = dateToCook;
    }

    public MealPlan(int plannedMealId, LocalDate dateToCook){
        this.dateToCook = dateToCook;
        this.plannedMealId = plannedMealId;
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

    public int getPlannedMealId() {
        return plannedMealId;
    }

    public void setPlannedMealId(int plannedMealId) {
        this.plannedMealId = plannedMealId;
    }

    public LocalDate getDateToCook() {
        return dateToCook;
    }

    public void setDateToCook(LocalDate dateToCook) {
        this.dateToCook = dateToCook;
    }
}
