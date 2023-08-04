package com.techelevator.controller;

import com.techelevator.dao.IngredientDao;
import com.techelevator.model.Ingredient;
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

    @RequestMapping(value = "", method = RequestMethod.POST)
    public void addIngredient (@RequestBody Ingredient ingredient){
        try {
            ingredientDao.addIngredient(ingredient);
        } catch (RuntimeException e){
            throw new RuntimeException("Unable to add ingredient!");
        }
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
