package com.techelevator.dao;

import com.techelevator.model.Meal;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
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
        Meal meal = new Meal();
        String sql = "SELECT meal_id, meal_name, user_id, meal_type FROM meals WHERE meal_id = ?";
        try {
            SqlRowSet row = jdbcTemplate.queryForRowSet(sql, mealId);
            if (row.next()){
                meal = mapRowToMeal(row);
            }
        } catch (RuntimeException e) {
            throw new RuntimeException("Could not find this meal.");
        }
        return meal;
    }

    @Override
    public Meal addMeal(Meal meal) {
        Meal newMeal = null;
        String sql = "INSERT INTO meals(meal_name, user_id, meal_type) VALUES (?, ?, ?) RETURNING meal_id;";
        try {
            int mealId = jdbcTemplate.queryForObject(sql, int.class, meal.getMealName(), meal.getUserId(), meal.getMealType());
            newMeal = getMealById(mealId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new RuntimeException("Unable to connect to the database.", e);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Action would violate data integrity.", e);
        } catch (BadSqlGrammarException e) {
            throw new RuntimeException("Invalid syntax.", e);
        }

        return newMeal;
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
