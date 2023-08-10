package com.techelevator.dao;

import com.techelevator.model.Meal;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcMealDaoTests extends BaseDaoTests{
    protected static final Meal MEAL1 = new Meal(1, 1, "good dinner", "dinner" );
    protected static final Meal MEAL2 = new Meal(2, 1, "decent lunch", "lunch");
    protected static final Meal MEAL3 = new Meal(3, 2, "bad breakfast", "breakfast");

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
}
