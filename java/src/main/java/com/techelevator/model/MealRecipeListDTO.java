package com.techelevator.model;

import java.util.ArrayList;
import java.util.List;

public class MealRecipeListDTO {
    private int mealId;
    private List<Recipe> recipes;

    public MealRecipeListDTO(int mealId, List<Recipe> recipes) {
        this.mealId = mealId;
        this.recipes = recipes;
    }

    public int getMealId() {
        return mealId;
    }

    public void setMealId(int mealId) {
        this.mealId = mealId;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }
    public List<Integer> getRecipeIds(){
        List<Integer> recipeIds = new ArrayList<>();
        for(Recipe recipe : this.recipes){
            recipeIds.add(recipe.getId());
        }
        return recipeIds;
    }
}
