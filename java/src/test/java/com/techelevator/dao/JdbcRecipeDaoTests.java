package com.techelevator.dao;


import com.techelevator.model.Recipe;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcRecipeDaoTests extends BaseDaoTests{

    private  JdbcRecipeDao sut;

    @Before
    public void setup(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcRecipeDao(jdbcTemplate);
    }

    @Test
    public void adding_recipe_adds_a_recipe(){
        Recipe testRecipe = new Recipe(4,"Ham Sandwich","", "testIngredients", "make sandwich");
        Assert.assertEquals(4, sut.addRecipe(testRecipe));
    }

    @Test
    public void getting_recipes_returns_all_recipes() {
        Assert.assertEquals(3, sut.getAllRecipes().size());
    }
}
