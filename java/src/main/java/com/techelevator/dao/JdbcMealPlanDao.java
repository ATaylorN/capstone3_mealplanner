package com.techelevator.dao;

import com.techelevator.model.Ingredient;
import com.techelevator.model.MealPlan;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcMealPlanDao implements MealPlanDAO {

    private final JdbcTemplate jdbcTemplate;

    public JdbcMealPlanDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<MealPlan> getAllUserMealPlans(int userid) {
        List<MealPlan> mealPlans = new ArrayList<>();
        String sql = "SELECT meal_plan_id, user_id, meal_id, plan_date " +
                "FROM meal_plans " +
                "WHERE user_id = ?;";
        try {
            SqlRowSet rows = jdbcTemplate.queryForRowSet(sql, userid);
            while (rows.next()){
                mealPlans.add(mapRowToMealPlan(rows));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new RuntimeException("Unable to connect to the database.", e);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Action would violate data integrity.", e);
        } catch (BadSqlGrammarException e) {
            throw new RuntimeException("Invalid syntax.", e);
        }
        return mealPlans;
    }

    @Override
    public List<MealPlan> getFutureUserMealPlans(int userid) {
        return null;
    }

    @Override
    public MealPlan getMealPlanById(int mealPlanId) {
        return null;
    }

    @Override
    public List<MealPlan> getUserMealPlansForDateRange(LocalDate startDate, LocalDate endDate) {
        return null;
    }

    @Override
    public int addMealPlan(MealPlan mealPlan) {
        return 0;
    }

    @Override
    public int getIngredientsByMealPlan(List<Ingredient> ingredients) {
        return 0;
    }

    @Override
    public void deleteMealPlan() {

    }

    private MealPlan mapRowToMealPlan(SqlRowSet rows){
        MealPlan newMealPlan = new MealPlan();
        newMealPlan.setId(rows.getInt("meal_plan_id"));
        newMealPlan.setUserId(rows.getInt("user_id"));
        newMealPlan.setPlannedMealId(rows.getInt("meal_id"));
        newMealPlan.setDateToCook(rows.getDate("plan_date").toLocalDate());
        return newMealPlan;
    }
}
