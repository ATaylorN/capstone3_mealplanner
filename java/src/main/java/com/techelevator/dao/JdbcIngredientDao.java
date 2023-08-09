package com.techelevator.dao;
import com.techelevator.model.Ingredient;
import com.techelevator.model.RecipeIngredientListDTO;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcIngredientDao implements IngredientDao{
    private final JdbcTemplate jdbcTemplate;

    public JdbcIngredientDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
    public int addIngredient(Ingredient ingredientToAdd){
        String sql = "INSERT INTO ingredients (ingredient_name, ingredient_image) VALUES (?, ?) RETURNING ingredient_id;";
        int ingredientId;
        if (ingredientToAdd.getImage() == null){
            ingredientToAdd.setImage("");
        }
        try {
            ingredientId = jdbcTemplate.queryForObject(sql, int.class, ingredientToAdd.getName(), ingredientToAdd.getImage());
            // TODO: Make specific exception types for DAO-related issues.
            // TODO: Think about what kind of exceptions we want to build.
        } catch (CannotGetJdbcConnectionException e) {
            throw new RuntimeException("Unable to connect to the database.", e);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Action would violate data integrity.", e);
        } catch (BadSqlGrammarException e) {
            throw new RuntimeException("Invalid syntax.", e);
        }

        return ingredientId;
    }
    public int addRecipeIngredients(List<Ingredient> ingredients, int recipeId){
        // Once we get our recipe ID back from the addRecipe call, we'll feed it to this method along with our ingredient list.
        int ingredientsToAdd = ingredients.size();
        int ingredientsAdded = 0;
        String sql = "INSERT INTO recipe_ingredients(recipe_id, ingredient_id) VALUES (?, ?)";
        try {
            for (Ingredient ingredient : ingredients){
                jdbcTemplate.update(sql, recipeId, ingredient.getId());
                ingredientsAdded++;
            }
            if(ingredientsToAdd != ingredientsAdded){
                throw new RuntimeException("Didn't create the expected number of rows.");
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new RuntimeException("Unable to connect to the database.", e);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Action would violate data integrity.", e);
        } catch (BadSqlGrammarException e) {
            throw new RuntimeException("Invalid syntax.", e);
        }

        return ingredientsAdded;
    }
    public int getIngredientIdByName(String ingredientName){
        int ingredientId;
        String sql = "SELECT ingredient_id FROM ingredients WHERE ingredient_name = ?;";
        try {
            ingredientId = jdbcTemplate.queryForObject(sql, int.class, ingredientName);
        } catch (CannotGetJdbcConnectionException e) {
            throw new RuntimeException("Unable to connect to the database.", e);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Action would violate data integrity.", e);
        } catch (BadSqlGrammarException e) {
            throw new RuntimeException("Invalid syntax.", e);
        }
        return ingredientId;
    }
    public List<Ingredient> getAllIngredients(){
        List<Ingredient> ingredients = new ArrayList<Ingredient>();
        String sql = "SELECT ingredient_id, ingredient_name, ingredient_image FROM ingredients;";
        try {
            SqlRowSet rows = jdbcTemplate.queryForRowSet(sql);
            while(rows.next()){
                ingredients.add(mapRowToIngredient(rows));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new RuntimeException("Unable to connect to the database.", e);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Action would violate data integrity.", e);
        } catch (BadSqlGrammarException e) {
            throw new RuntimeException("Invalid syntax.", e);
        }

        return ingredients;
    }

    public List<Ingredient> getIngredientsForRecipe(int recipeId){
        List<Ingredient> recipeIngredients = new ArrayList<>();
        String sql = "SELECT ingredient_id, ingredient_name, ingredient_image FROM ingredients WHERE ingredient_id IN (SELECT DISTINCT ingredient_id FROM recipe_ingredients WHERE recipe_id = ?)";
        try{
            SqlRowSet rows = jdbcTemplate.queryForRowSet(sql, recipeId);
            while (rows.next()){
                recipeIngredients.add(mapRowToIngredient(rows));
            }
        } catch (RuntimeException e){
            throw new RuntimeException("Failed to get recipe's ingredients");
        }
        return recipeIngredients;
    }

    @Override
    public int deleteIngredientsByRecipeId(RecipeIngredientListDTO recipeIngredientListDTO) {
        int recipeId = recipeIngredientListDTO.getRecipeId();
        String sql = "delete from recipe_ingredients where recipe_id = ?;";
        int rowsRemoved = jdbcTemplate.update(sql, recipeId);
        return rowsRemoved;
    }

    private Ingredient mapRowToIngredient(SqlRowSet rows){
        Ingredient newIngredient = new Ingredient();
        newIngredient.setId(rows.getInt("ingredient_id"));
        newIngredient.setName(rows.getString("ingredient_name"));
        newIngredient.setImage(rows.getString("ingredient_image"));
        return newIngredient;
    }

}
