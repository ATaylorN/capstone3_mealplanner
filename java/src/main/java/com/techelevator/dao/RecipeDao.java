package com.techelevator.dao;

import com.techelevator.model.Recipe;

import java.util.List;

public interface RecipeDao {

    int addRecipe (Recipe recipeToAdd);
    List<Recipe> getAllRecipes();

    Recipe getRecipeById (int id);


}
