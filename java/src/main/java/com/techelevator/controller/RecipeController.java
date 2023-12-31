package com.techelevator.controller;

import com.techelevator.dao.IngredientDao;
import com.techelevator.dao.RecipeDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Ingredient;
import com.techelevator.model.Recipe;
import com.techelevator.model.RecipeIngredientListDTO;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value="/recipes")
public class RecipeController {
    private RecipeDao recipeDao;
    private UserDao userDao;
    private IngredientDao ingredientDao;
    public RecipeController(RecipeDao recipeDao, UserDao userDao, IngredientDao ingredientDao){
        this.recipeDao = recipeDao;
        this.userDao = userDao;
        this.ingredientDao = ingredientDao;
    }

    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "", method = RequestMethod.POST)
    public int addRecipe (@RequestBody Recipe recipe, Principal principal){
        int newRecipeId;
        try {
            newRecipeId = recipeDao.addRecipe(recipe, userDao.findIdByUsername(principal.getName()));
        } catch (RuntimeException e){
            throw new RuntimeException("Unable to add recipe!", e);
        }
        return newRecipeId;
    }

    @CrossOrigin
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/user/recipes", method = RequestMethod.GET)
    public List<Recipe> getAllRecipesByUserId(Principal principal) {
        int userId = userDao.findIdByUsername(principal.getName());
        List<Recipe> recipes = new ArrayList<>();
        try {
            recipes = recipeDao.getAllRecipesByUserId(userId);
        } catch (RuntimeException e){
            throw new RuntimeException("Couldn't get recipes!");
        }
        return recipes;
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
            // need to get a list of ingredients here.
        } catch (RuntimeException e){
            throw new RuntimeException("Recipe not found");
        }
        return recipe;
    }
    @RequestMapping(value="/{recipeId}/ingredients", method = RequestMethod.GET)
    public RecipeIngredientListDTO getRecipeIngredients(@PathVariable int recipeId){
        RecipeIngredientListDTO recipeIngredients = new RecipeIngredientListDTO();
        recipeIngredients.setRecipeId(recipeId);
        List<Ingredient> ingredients = new ArrayList<>();
        try{
           ingredients = ingredientDao.getIngredientsForRecipe(recipeId);
        } catch (RuntimeException e){
            throw new RuntimeException("Failed to get recipe ingredients.");
        }
        recipeIngredients.setIngredientList(ingredients);
        return recipeIngredients;
    }

    @RequestMapping(value = "/{id}" ,method = RequestMethod.PUT)
    public Recipe updateRecipe(@PathVariable int id, @RequestBody Recipe recipeToUpdate) {
        if (id < 0) {
            throw new RuntimeException("Recipe not found");
        }
        recipeToUpdate.setId(id);
        try {
            Recipe recipe = recipeDao.updateRecipe(recipeToUpdate);
            // need to take a list of ingredients here.
            return recipe;
        } catch (RuntimeException e) {
            throw new RuntimeException("Recipe not found");
        }
    }


}
