package com.techelevator.dao;

import com.techelevator.model.Meal;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JdbcMealDaoTests extends BaseDaoTests{
    protected static final Meal MEAL1 = new Meal(1, 1, "Southern dinner", "dinner" );
    protected static final Meal MEAL2 = new Meal(2, 1, "Boss breakfast", "breakfast");
    protected static final Meal MEAL3 = new Meal(3, 2, "Power lunch", "lunch");
    protected static final Meal MEAL4 = new Meal(4, 2, "Big breakfast", "breakfast");

    private JdbcMealDao sut;
    JdbcRecipeDao recipeDao;

    @Before
    public void setup(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcMealDao(jdbcTemplate, recipeDao);
    }

    @Test
    public void getting_user_meals_returns_all_meals() {
        Assert.assertEquals("Wrong size for list",2, sut.getUserMeals(1).size());
    }

    @Test
    public void getting_meal_by_id_returns_correct_meal() {
        Meal actual = sut.getMealById(1);
        assertMealsMatch(MEAL1, actual);
    }

    @Test
    public void adding_meal_adds_meal_with_correct_values() {
        Meal meal = new Meal(4, 1, "Breakfast when sick", "breakfast");
        Meal actualMeal = sut.addMeal(meal);
        Meal expectedMeal = sut.getMealById(actualMeal.getMealId());

        assertMealsMatch(expectedMeal, actualMeal);
    }

    @Test
    public void add_recipes_to_meal_returns_correct_num_of_rows() {

    }

    @Test
    public void deleted_meal_can_no_longer_be_retrieved() {
        int rowsAffected = sut.deleteMeal(MEAL4.getMealId());
        Assert.assertEquals(1, rowsAffected);
        Meal retrievedMeal = getMealByIdForTestVerification(MEAL4.getMealId());
        Assert.assertNull("Meal wasn't deleted from database.", retrievedMeal);
    }

    private Meal getMealByIdForTestVerification(int id) {
        Meal meal = null;
        String sql = "SELECT meal_id, meal_name, user_id, meal_type FROM meals WHERE meal_id = ?";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
        if (results.next()) {
            Meal mappedMeal = new Meal();
            mappedMeal.setMealId(results.getInt("meal_id"));
            mappedMeal.setUserId(results.getInt("user_id"));
            mappedMeal.setMealName(results.getString("meal_name"));
            mappedMeal.setMealType(results.getString("meal_type"));
            meal = mappedMeal;
        }
        return meal;
    }

    private void assertMealsMatch(Meal expected, Meal actual){
        Assert.assertEquals(expected.getMealId(), actual.getMealId());
        Assert.assertEquals(expected.getUserId(), actual.getUserId());
        Assert.assertEquals(expected.getMealName(), actual.getMealName());
        Assert.assertEquals(expected.getMealType(), actual.getMealType());
    }
}
