package com.techelevator.dao;
import com.techelevator.model.Ingredient;
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

    private Ingredient mapRowToIngredient(SqlRowSet rows){
        Ingredient newIngredient = new Ingredient();
        newIngredient.setId(rows.getInt("ingredient_id"));
        newIngredient.setName(rows.getString("ingredient_name"));
        newIngredient.setImage(rows.getString("ingredient_image"));
        return newIngredient;
    }

}
