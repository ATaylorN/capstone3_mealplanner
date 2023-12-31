package com.techelevator.dao;

import com.techelevator.model.Ingredient;
import com.techelevator.model.MealPlan;

import java.time.LocalDate;
import java.util.List;

public interface MealPlanDao {

    List<MealPlan> getAllUserMealPlans(int userid);
    List<MealPlan> getFutureUserMealPlans(int userid);
    MealPlan getMealPlanById(int mealPlanId);
    List<MealPlan>getUserMealPlansForDateRange(LocalDate startDate, LocalDate endDate);
    int addMealPlan(MealPlan mealPlan);
    int getIngredientsByMealPlan(List<Ingredient> ingredients);
    void deleteMealPlan(int mealPlanId);
    MealPlan updateMealPlan(MealPlan mealPlan);
    List <MealPlan> getMealPlansByDate(LocalDate date);


}
