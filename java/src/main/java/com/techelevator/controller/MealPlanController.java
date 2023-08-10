package com.techelevator.controller;

import com.techelevator.dao.MealDao;
import com.techelevator.dao.MealPlanDao;
import com.techelevator.dao.RecipeDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Meal;
import com.techelevator.model.MealPlan;
import com.techelevator.model.MealRecipeListDTO;
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
        mealPlan.setId(userDao.findIdByUsername(principal.getName()));
        int newMealPlanId;
        try{
            newMealPlanId = mealPlanDao.addMealPlan(mealPlan);
        } catch (RuntimeException e){
            throw new RuntimeException("Unable to add meal plan!", e);
        }
        return newMealPlanId;
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

    @RequestMapping(value="/meal-plan", method = RequestMethod.DELETE)
    public void deleteMealPlan(int mealPlanId, Principal principal){

    }

    @RequestMapping(value="/meal-plan&from={fromDate}&to={toDate}", method = RequestMethod.GET)
    public List<MealPlan>getUserMealPlansForDateRange(@PathVariable LocalDate fromDate, @PathVariable LocalDate toDate, Principal principal){
        return null;
    }

    @RequestMapping(value="/meal-plan/{id}", method = RequestMethod.PUT)
    public MealPlan updateMealPlan(MealPlan mealPlan, Principal principal){
        return null;
    }

    @RequestMapping(value="/meal/{id}", method = RequestMethod.PUT)
    public Meal updateMeal(@RequestBody Meal mealToUpdate, Principal principal){
        return null;
    }
}

