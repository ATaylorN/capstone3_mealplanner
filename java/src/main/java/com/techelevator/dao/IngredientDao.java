package com.techelevator.dao;

import com.techelevator.model.Ingredient;
import com.techelevator.model.RecipeIngredientListDTO;

import java.util.List;

public interface IngredientDao {
    /**
     * Add an ingredient to the table of available ingredients.
     * @return
     */
    int addIngredient(Ingredient ingredientToAdd);

    /**
     * Retrieves all ingredients from the database in a list.
     * @return
     */
    List<Ingredient> getAllIngredients();

    int addRecipeIngredients(List<Ingredient> ingredients, int recipeId);

    int getIngredientIdByName(String ingredientName);

    List<Ingredient> getIngredientsForRecipe(int recipeId);

    int deleteIngredientsByRecipeId (RecipeIngredientListDTO recipeIngredientListDTO);

}
