package com.techelevator.model;

import java.util.List;

public class RecipeIngredientListDTO {
    private List<Ingredient> ingredientList;
    private int recipeId;

    public RecipeIngredientListDTO() {
    }

    public RecipeIngredientListDTO(List<Ingredient> ingredientList, int recipeId) {
        this.ingredientList = ingredientList;
        this.recipeId = recipeId;
    }

    public List<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }
}
