package com.techelevator.dao;

import com.techelevator.model.Recipe;
import com.techelevator.model.RecipeIngredientListDTO;

import java.util.List;

public interface RecipeDao {

    int addRecipe (Recipe recipeToAdd, int userId);
    List<Recipe> getAllRecipes();

    List<Recipe> getAllRecipesByUserId(int userId);

    Recipe getRecipeById (int id);

    Recipe updateRecipe (Recipe recipe);



}
