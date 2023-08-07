package com.techelevator.controller;

import com.techelevator.dao.IngredientDao;
import com.techelevator.model.Ingredient;
import com.techelevator.model.RecipeIngredientListDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/ingredients")
public class IngredientController {
    private IngredientDao ingredientDao;

    public IngredientController(IngredientDao ingredientDao){
        this.ingredientDao = ingredientDao;
    }
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/recipe-ingredient-list", method = RequestMethod.POST)
    public int addRecipeIngredients(@RequestBody RecipeIngredientListDTO ingredients){
        //System.out.println(ingredients);
        int rowsAdded = 0;
        int rowsToAdd = ingredients.getIngredientList().size();
        try{
            rowsAdded = ingredientDao.addRecipeIngredients(ingredients.getIngredientList(), ingredients.getRecipeId());
        } catch (RuntimeException e){
            throw new RuntimeException("Failed to add ingredient list rows.", e);
        }
        return rowsAdded;
    }
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "", method = RequestMethod.POST)
    public int addIngredient (@RequestBody Ingredient ingredient){
        int newIngredientId;
        try {
            newIngredientId = ingredientDao.addIngredient(ingredient);
        } catch (RuntimeException e){
            throw new RuntimeException("Unable to add ingredient!", e);
        }
        return newIngredientId;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Ingredient> getAllIngredients(){
        List<Ingredient> ingredients = new ArrayList<Ingredient>();
        try{
            ingredients = ingredientDao.getAllIngredients();
        } catch (RuntimeException e){
            throw new RuntimeException("Couldn't get ingredients!");
        }
        return ingredients;
    }

}
