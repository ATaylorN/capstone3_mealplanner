package com.techelevator.model;

import java.util.List;

public class Recipe {
    private int id;
    private int userId;
    private String name;
    private String image;
    private List<String> ingredientNames;
    private List<String> ingredientNotes;
    private List<String> ingredientQuantities;
    private String ingredients;
    private String instructions;

    public Recipe() {
    }

    public Recipe(int id, int userId, String name, String image, List<String> ingredientNames, List<String> ingredientNotes, List<String> ingredientQuantities, String instructions) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.image = image;
        this.ingredientNames = ingredientNames;
        this.ingredientNotes = ingredientNotes;
        this.ingredientQuantities = ingredientQuantities;
        this.instructions = instructions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<String> getIngredientNames() {
        return ingredientNames;
    }

    public void setIngredientNames(List<String> ingredientNames) {
        this.ingredientNames = ingredientNames;
    }

    public List<String> getIngredientNotes() {
        return ingredientNotes;
    }

    public void setIngredientNotes(List<String> ingredientNotes) {
        this.ingredientNotes = ingredientNotes;
    }

    public List<String> getIngredientQuantities() {
        return ingredientQuantities;
    }

    public void setIngredientQuantities(List<String> ingredientQuantities) {
        this.ingredientQuantities = ingredientQuantities;
    }

    public String getInstructions() {
        return instructions;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}

