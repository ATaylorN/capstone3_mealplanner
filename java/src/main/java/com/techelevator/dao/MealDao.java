package com.techelevator.dao;

import com.techelevator.model.Meal;
import com.techelevator.model.Recipe;

import java.util.List;

public interface MealDao {

    List<Meal> getUserMeals (int userId);

    Meal getMealById (int mealId);

    Meal addMeal(Meal meal);

    Meal updateMeal(Meal meal);

    int deleteMeal(int mealId);
    List<Recipe> getRecipesByMealId(int mealId, int userId);

    int addRecipesToMeal(List<Integer> recipeIds, int mealId);

}
