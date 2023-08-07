package com.techelevator.controller;

import com.techelevator.dao.IngredientDao;
import com.techelevator.dao.RecipeDao;
import com.techelevator.model.Ingredient;
import com.techelevator.model.Recipe;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value="/recipes")
public class RecipeController {
    private RecipeDao recipeDao;

    public RecipeController(RecipeDao recipeDao){
        this.recipeDao = recipeDao;
    }
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "", method = RequestMethod.POST)
    public int addRecipe (@RequestBody Recipe recipe){
        int newRecipeId;
        try {
            newRecipeId = recipeDao.addRecipe(recipe);
        } catch (RuntimeException e){
            throw new RuntimeException("Unable to add recipe!", e);
        }
        return newRecipeId;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Recipe> getAllRecipes(){
        List<Recipe> recipes = new ArrayList<Recipe>();
        try{
            recipes = recipeDao.getAllRecipes();
        } catch (RuntimeException e){
            throw new RuntimeException("Couldn't get recipes!");
        }
        return recipes;
    }
    @RequestMapping(value = "/{id}"  ,method = RequestMethod.GET)
    public Recipe getRecipeById(@PathVariable int id){
        // should 404 if the id does not exist.
        Recipe recipe = null;
        try{
            recipe = recipeDao.getRecipeById(id);
        } catch (RuntimeException e){
            throw new RuntimeException("Recipe not found");
        }
        return recipe;
    }

}
