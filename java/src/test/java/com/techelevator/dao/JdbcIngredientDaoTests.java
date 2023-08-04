package com.techelevator.dao;

import com.techelevator.model.Ingredient;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcIngredientDaoTests extends BaseDaoTests{
    protected static final Ingredient BANANA = new Ingredient(1, "Banana");
    protected static final Ingredient EGGS = new Ingredient(2, "Eggs");
    protected static final Ingredient YOGURT = new Ingredient(3, "Yogurt");

    private JdbcIngredientDao sut;

    @Before
    public void setup(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcIngredientDao(jdbcTemplate);
    }

    @Test
    public void adding_ingredient_adds_ingredient() {
        Ingredient testIngredient = new Ingredient("Butter");
        Assert.assertEquals(4, sut.addIngredient(testIngredient));

    }

    @Test
    public void getting_ingredients_returns_all_ingredients() {
        Assert.assertEquals("Discrepancy in list size.",3, sut.getAllIngredients().size());
    }
}
