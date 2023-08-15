package com.techelevator.controller;

import com.techelevator.dao.IngredientDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Ingredient;
import com.techelevator.model.RecipeIngredientListDTO;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/ingredients")
public class IngredientController {
    private IngredientDao ingredientDao;
    private UserDao userDao;

    public IngredientController(IngredientDao ingredientDao, UserDao userDao){
        this.ingredientDao = ingredientDao;
        this.userDao = userDao;
    }
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/recipe-ingredient-list", method = RequestMethod.POST)
    public int addRecipeIngredients(@RequestBody RecipeIngredientListDTO ingredients){
        //System.out.println(ingredients);
        int rowsAdded = 0;
        int rowsToAdd = ingredients.getIngredientList().size();
        // At this point, if we're adding recipe ingredients that were brought in from the API search, we don't have IDs for those ingredients.
        // We have to get each ingredient's ID before we can actually try to add them to the recipe_ingredient table.
        // They've been added to the ingredient table, so the data is there - we just need an intermediate call to a mapper method in the DAO that will put IDs on each thing.
        for (Ingredient ingredient : ingredients.getIngredientList()){
            if(ingredient.getId() == 0){
               ingredient.setId(ingredientDao.getIngredientIdByName(ingredient.getName()));
            }
        }
        try{
            rowsAdded = ingredientDao.addRecipeIngredients(ingredients.getIngredientList(), ingredients.getRecipeId());
        } catch (RuntimeException e){
            throw new RuntimeException("Failed to add ingredient list rows.", e);
        }
        return rowsAdded;
    }
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/groceryList{fromDate}{toDate}", method = RequestMethod.GET)
    public List<Ingredient> groceryList(@RequestParam String fromDate, @RequestParam String toDate, Principal principal) {
        List<Ingredient> ingredients = new ArrayList<>();
        try {
            ingredients = ingredientDao.selectAllIngredientsForMealPlansOnAGivenDate(LocalDate.parse(fromDate), LocalDate.parse(toDate), userDao.findIdByUsername(principal.getName()));
        } catch (RuntimeException e) {
            throw new RuntimeException("Couldn't get ingredients for specified date range");
        }
        return ingredients;
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

    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/recipe-ingredient-list", method = RequestMethod.PUT)
    public void deleteIngredientsByRecipeId(@RequestBody RecipeIngredientListDTO recipeIngredientListDTO){
        int rowsRemoved = ingredientDao.deleteIngredientsByRecipeId(recipeIngredientListDTO);
        if (rowsRemoved > 0){
            ingredientDao.addRecipeIngredients
                    (recipeIngredientListDTO.getIngredientList(), recipeIngredientListDTO.getRecipeId());
        }
    }


}
