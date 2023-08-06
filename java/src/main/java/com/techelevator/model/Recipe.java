package com.techelevator.model;

import javax.lang.model.element.NestingKind;
import java.util.List;

public class Recipe {
    private int id;
    private String name;
    private String image;

    private String ingredients;

    private String instructions;

    public Recipe() {
    }

    public Recipe(int id, String name, String image, String ingredients, String instructions){
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

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}

