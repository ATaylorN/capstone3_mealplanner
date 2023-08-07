package com.techelevator.dao;


import com.techelevator.model.Recipe;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestBody;

public class JdbcRecipeDaoTests extends BaseDaoTests{

    private  JdbcRecipeDao sut;

    @Before
    public void setup(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcRecipeDao(jdbcTemplate);
    }
// TODO: new test for recipe data model change
//    @Test
//    public void adding_recipe_adds_a_recipe(){
//        Recipe testRecipe = new Recipe(4,"Ham Sandwich","", "testIngredients", "make sandwich");
//        Assert.assertEquals(4, sut.addRecipe(testRecipe));
//    }

    @Test
    public void getting_recipes_returns_all_recipes() {
        Assert.assertEquals(3, sut.getAllRecipes().size());
    }

    @Test
    public void updated_recipe_has_expected_results() {
        Recipe recipe = sut.getRecipeById(1);
        recipe.setId(1);
        recipe.setImage("somethingFun");
        recipe.setIngredients("Updated");
        recipe.setInstructions("Instruct");
        Recipe actualRecipe = sut.updateRecipe(recipe);
        assertRecipesMatch(recipe, actualRecipe);
    }

    private void assertRecipesMatch(Recipe expected, Recipe actual){
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getImage(), actual.getImage());
        Assert.assertEquals(expected.getIngredients(), actual.getIngredients());
        Assert.assertEquals(expected.getInstructions(), actual.getInstructions());
    }
}
