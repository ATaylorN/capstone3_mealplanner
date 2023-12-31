package com.techelevator.controller;

import com.techelevator.dao.MealDao;
import com.techelevator.dao.MealPlanDao;
import com.techelevator.dao.RecipeDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Meal;
import com.techelevator.model.MealPlan;
import com.techelevator.model.MealRecipeListDTO;
import com.techelevator.model.Recipe;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
@CrossOrigin
@RequestMapping(value="/meals")
public class MealPlanController {

    private RecipeDao recipeDao;
    private UserDao userDao;
    private MealDao mealDao;
    private MealPlanDao mealPlanDao;
//TODO: Verify that MealPlanDaos and the associated methods are named this way. Double-check that your methods still make sense

    public MealPlanController(RecipeDao recipeDao, UserDao userDao, MealDao mealDao, MealPlanDao mealPlanDao){
        this.recipeDao = recipeDao;
        this.userDao = userDao;
        this.mealDao = mealDao;
        this.mealPlanDao = mealPlanDao;
    }
    @RequestMapping(value="", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public int addMeal(@RequestBody Meal meal, Principal principal){
        meal.setUserId(userDao.findIdByUsername(principal.getName()));
        int newMealId;
        try{
            newMealId = mealDao.addMeal(meal).getMealId();

        } catch (RuntimeException e){
            throw new RuntimeException("Unable to add meal!", e);
        }
        return newMealId;
    }


    @RequestMapping(value="/recipe-list", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public int addRecipesToMeal(@RequestBody MealRecipeListDTO mealRecipes){
        int rowsToAdd = 0;
        try{
            rowsToAdd = mealDao.addRecipesToMeal(mealRecipes.getRecipeIds(), mealRecipes.getMealId());
        } catch (RuntimeException e){
            throw new RuntimeException("Unable to add recipes to meal!", e);
        }
        return rowsToAdd;
    }



    @RequestMapping(value="/meal-plan", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public int addMealPlan(@RequestBody MealPlan mealPlan, Principal principal){
        mealPlan.setUserId(userDao.findIdByUsername(principal.getName()));
        if (mealPlan.getId() != 0){
            try{
                mealPlanDao.updateMealPlan(mealPlan);
            } catch (RuntimeException e){
                throw new RuntimeException("Couldn't update meal plan");
            }
            return mealPlan.getId();
        }
        int newMealPlanId;
        try{

            newMealPlanId = mealPlanDao.addMealPlan(mealPlan);

        } catch (RuntimeException e){
            throw new RuntimeException("Unable to add meal plan!", e);
        }
        return newMealPlanId;
    }
    @RequestMapping(value="/meal-plans{fromDate}{toDate}", method = RequestMethod.GET)
    public List<MealPlan>getUserMealPlansForDateRange(@RequestParam String fromDate, @RequestParam String toDate, Principal principal){
        List<MealPlan> mealPlans = new ArrayList<>();
        try{
            mealPlans = mealPlanDao.getUserMealPlansForDateRange(LocalDate.parse(fromDate), LocalDate.parse(toDate));
        }catch (RuntimeException e){
            throw new RuntimeException("Couldn't get meal plans for specified date range!");
        }
        return mealPlans;
    }
    @RequestMapping(value="/meal/{mealId}", method = RequestMethod.GET)
    public Meal getMealById(@PathVariable int mealId){
        Meal meal = null;
        try {
            meal = mealDao.getMealById(mealId);
        } catch (RuntimeException e){
            throw new RuntimeException("Could not find meal.");
        }
        return meal;
    }


    @RequestMapping(value="", method = RequestMethod.GET)
    public List<Meal> getUserMeals(Principal principal){
        int userId = userDao.findIdByUsername(principal.getName());
        List<Meal> meals = new ArrayList<>();
        try {
            meals = mealDao.getUserMeals(userId);
        }catch(RuntimeException e){
            throw new RuntimeException("Couldn't get meals!");
        }
        return meals;
    }

    @RequestMapping(value="/meal-plan", method = RequestMethod.GET)
    public List<MealPlan>getAllUserMealPlans(Principal principal){
        int userId = userDao.findIdByUsername(principal.getName());
        List<MealPlan> mealPlans = new ArrayList<>();
        try {
            mealPlans = mealPlanDao.getAllUserMealPlans(userId);
        }catch(RuntimeException e){
            throw new RuntimeException("Couldn't get meal plans!");
        }
        return mealPlans;
    }
    @RequestMapping(value = "/{id}/recipes", method = RequestMethod.GET)
    public List<Recipe> getRecipesByMealId(@PathVariable int id, Principal principal) {
        List<Recipe> mealRecipes = new ArrayList<>();
        int userId = userDao.findIdByUsername(principal.getName());
        try{
            mealRecipes = mealDao.getRecipesByMealId(id, userId);
        } catch (RuntimeException e){
            throw new RuntimeException("Couldn't get the recipes for the given meal.");
        }
        return mealRecipes;
    }

    @RequestMapping(value="/delete-meal-plan/{id}", method = RequestMethod.DELETE)
    public void deleteMealPlan(@PathVariable int id, Principal principal){
        try{
            if( mealPlanDao.getMealPlanById(id).getUserId() == userDao.findIdByUsername(principal.getName())){
                mealPlanDao.deleteMealPlan(id);
            } else {
                throw new RuntimeException("User does not have permission to delete this plan.");
            }
        } catch (RuntimeException e){
            throw new RuntimeException("Unable to delete meal plan.");
        }
    }



    @RequestMapping(value="/meal-plan/{id}", method = RequestMethod.PUT)
    public MealPlan updateMealPlan(@PathVariable int id, @RequestBody MealPlan mealPlanToUpdate){
            if(id < 0){
                throw new RuntimeException("Meal plan not found");
            }
            mealPlanToUpdate.setId(id);
            try{
                MealPlan mealPlan = mealPlanDao.updateMealPlan(mealPlanToUpdate);
                return mealPlan;
            } catch (RuntimeException e ) {
                throw new RuntimeException("Meal plan not found!");
            }
    }



    /*
    We have an updateMealPlan method in the controller, but no updateMealPlan method
    MealPlanDAO. It wasn't shown that we need it in the plan Ben drew out, but do
    we need this?
     */

    @RequestMapping(value="/meal/{id}", method = RequestMethod.PUT)
    public Meal updateMeal(@RequestBody Meal mealToUpdate, Principal principal){
        return null;
    }


}

