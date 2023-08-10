package com.techelevator.dao;

import com.techelevator.model.Ingredient;
import com.techelevator.model.MealPlan;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JdbcMealPlanDao implements MealPlanDao {

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
        List<MealPlan> mealPlans = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();
        String sql = "SELECT meal_plan_id, user_id, meal_id, plan_date " +
                "FROM meal_plans " +
                "WHERE user_id = ? " +
                "AND plan_date >= ?;";
        try {
            SqlRowSet rows = jdbcTemplate.queryForRowSet(sql, userid, currentDate);
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
    public MealPlan getMealPlanById(int mealPlanId) {
        MealPlan mealPlan = null;
        String sql = "SELECT meal_plan_id, user_id, meal_id, plan_date " +
                "FROM meal_plans " +
                "WHERE meal_plan_id = ?;";
        try {
            SqlRowSet rows = jdbcTemplate.queryForRowSet(sql, mealPlanId);
            if (rows.next()) {
                mealPlan = mapRowToMealPlan(rows);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new RuntimeException("Unable to connect to the database.", e);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Action would violate data integrity.", e);
        } catch (BadSqlGrammarException e) {
            throw new RuntimeException("Invalid syntax.", e);
        }
        return mealPlan;
    }

    @Override
    public List<MealPlan> getUserMealPlansForDateRange(LocalDate startDate, LocalDate endDate) {
        List<MealPlan> mealPlans = new ArrayList<>();
        String sql = "SELECT meal_plan_id, user_id, meal_id, plan_date " +
                "FROM meal_plans " +
                "WHERE plan_date >= ? AND plan_date <= ?";
        try {
            SqlRowSet rows = jdbcTemplate.queryForRowSet(sql, startDate, endDate);
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
    public int addMealPlan(MealPlan mealPlan) {
        String sql = "INSERT INTO INSERT INTO meal_plans (user_id, meal_id, plan_date) " +
                "VALUES (?, ?, ?) RETURNING meal_plan_id";
        int mealPlanId;
        try {
            mealPlanId = jdbcTemplate.queryForObject(sql, int.class, mealPlan.getUserId(), mealPlan.getPlannedMealId(), mealPlan.getDateToCook());
            mealPlan.setId(mealPlanId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new RuntimeException("Unable to connect to the database.", e);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Action would violate data integrity.", e);
        } catch (BadSqlGrammarException e) {
            throw new RuntimeException("Invalid syntax.", e);
        }
        return mealPlanId;
    }

    @Override
    public int getIngredientsByMealPlan(List<Ingredient> ingredients) {
        return 0;
    }

    @Override
    public void deleteMealPlan(MealPlan mealPlan) {
        String sql = "DELETE FROM meal_plans WHERE meal_plan_id = ?";
        try {
            jdbcTemplate.update(sql, mealPlan.getId());
        } catch (CannotGetJdbcConnectionException e) {
            throw new RuntimeException("Unable to connect to the database.", e);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Action would violate data integrity.", e);
        } catch (BadSqlGrammarException e) {
            throw new RuntimeException("Invalid syntax.", e);
        }
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
