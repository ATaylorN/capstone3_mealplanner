package com.techelevator.model;

import java.util.List;

public class Recipe {
    private int id;
    private String name;
    private String image;

    private List<Ingredient> ingredients;

    private String instructions;

    public Recipe() {
    }

    public Recipe(int id, String name, String image, List<Ingredient> ingredients, String instructions){
        this.id = id;
        this.name = name;
        this.image = image;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}

