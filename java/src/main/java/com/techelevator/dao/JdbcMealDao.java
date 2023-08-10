package com.techelevator.dao;

import com.techelevator.model.Meal;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.ArrayList;
import java.util.List;

public class JdbcMealDao implements MealDao {

    JdbcTemplate jdbcTemplate;

    public JdbcMealDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Meal> getUserMeals(int userId) {
        List<Meal> meals = new ArrayList<>();
        String sql = "SELECT meal_id, meal_name, user_id, meal_type FROM meals WHERE user_id = ?;";
        try {
            SqlRowSet rows = jdbcTemplate.queryForRowSet(sql, userId);
            while (rows.next()){
                meals.add(mapRowToMeal(rows));
            }
        } catch (RuntimeException e){
            throw new RuntimeException("Could not find your meals.");
        }
        return meals;
    }

    @Override
    public Meal getMealById(int mealId) {
        return null;
    }

    @Override
    public int addMeal(Meal meal) {
        return 0;
    }

    @Override
    public Meal updateMeal(Meal meal) {
        return null;
    }

    @Override
    public void deleteMeal(int mealId) {

    }

    @Override
    public int addRecipesToMeal(List<Integer> recipeIds, int mealId) {
        return 0;
    }

    private Meal mapRowToMeal(SqlRowSet rows){
        Meal newMeal = new Meal();
        newMeal.setMealId(rows.getInt("meal_id"));
        newMeal.setMealName(rows.getString("meal_name"));
        newMeal.setUserId(rows.getInt("user_id"));
        newMeal.setMealType(rows.getString("meal_type"));
        return newMeal;
    }
}
