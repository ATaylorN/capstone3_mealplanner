package com.techelevator.dao;

import com.techelevator.model.Meal;

import java.util.List;

public interface MealDao {

    List<Meal> getUserMeals (int userId);

    Meal getMealById (int mealId);

    int addMeal(Meal meal);

    Meal updateMeal(Meal meal);

    void deleteMeal(int mealId);

    int addRecipesToMeal(List<Integer> recipeIds, int mealId);

}
