package com.techelevator.dao;

import com.techelevator.model.Ingredient;

public interface IngredientDao {
    /**
     * Add an ingredient to the table of available ingredients.
     */
    int addIngredient(Ingredient ingredientToAdd);

}
