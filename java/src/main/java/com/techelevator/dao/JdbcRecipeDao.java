package com.techelevator.dao;

import com.techelevator.model.Recipe;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JdbcRecipeDao implements RecipeDao{
    private final JdbcTemplate jdbcTemplate;

    public JdbcRecipeDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int addRecipe(Recipe recipeToAdd) {
        return 0;
    }

    @Override
    public List<Recipe> getAllRecipes() {
        return null;
    }

    private Recipe mapRowToRecipe(SqlRowSet rows){
        Recipe newRecipe = new Recipe();
        newRecipe.setId(rows.getInt("recipe_id"));
        newRecipe.setName(rows.getString("recipe_name"));
        newRecipe.setImage(rows.getString("recipe_image"));
        //TODO: write the piece for setting the Ingredients List
        //newRecipe.setIngredients list isn't set here. I tried using getObject, but couldn't get it to work
        newRecipe.setInstructions(rows.getString("instructions"));
        return newRecipe;
    }
}
