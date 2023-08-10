package com.techelevator.dao;

import com.techelevator.model.Meal;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcMealDaoTests extends BaseDaoTests{
    protected static final Meal MEAL1 = new Meal(1, 1, "Southern dinner", "dinner" );
    protected static final Meal MEAL2 = new Meal(2, 1, "Boss breakfast", "breakfast");
    protected static final Meal MEAL3 = new Meal(3, 2, "Power lunch", "lunch");

    private JdbcMealDao sut;

    @Before
    public void setup(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcMealDao(jdbcTemplate);
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

    @Test void deleted_meal_can_no_longer_be_retrieved() {
        int rowsAffected = sut.deleteMeal(MEAL1.getMealId());
       
    }

    private void assertMealsMatch(Meal expected, Meal actual){
        Assert.assertEquals(expected.getMealId(), actual.getMealId());
        Assert.assertEquals(expected.getUserId(), actual.getUserId());
        Assert.assertEquals(expected.getMealName(), actual.getMealName());
        Assert.assertEquals(expected.getMealType(), actual.getMealType());
    }
}
