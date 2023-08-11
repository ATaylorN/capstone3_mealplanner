package com.techelevator.dao;

import com.techelevator.model.MealPlan;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.util.List;

public class JdbcMealPlanDaoTests extends BaseDaoTests{
    private JdbcMealPlanDao sut;

    protected static final MealPlan testPlan1 = new MealPlan(1, 1, 1, LocalDate.of(2024, 01, 01));
    protected static final MealPlan testPlan2 = new MealPlan(1, 2, 2, LocalDate.of(2024, 02, 02));
    protected static final MealPlan testPlan3 = new MealPlan(2, 1, 1, LocalDate.of(2024, 01, 01));

    @Before
    public void setup(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcMealPlanDao(jdbcTemplate);
    }

    @Test
    public void getAllUserMealPlans_returns_list_of_all_user_mealPlans() {
        List<MealPlan> mealPlans = sut.getAllUserMealPlans(1);
        Assert.assertEquals(2, mealPlans.size());
    }

    @Test
    public void updated_meal_plan_has_expected_results() {
        MealPlan mealPlan = sut.getMealPlanById(2);
        mealPlan.setId(2);
        mealPlan.setDateToCook(LocalDate.of(2025,10,10));
        mealPlan.setUserId(1);
        mealPlan.setPlannedMealId(3);
        MealPlan actualMealPlan = sut.updateMealPlan(mealPlan);
        assertMealPlansMatch(mealPlan, actualMealPlan);

    }
    @Test
    public void add_meal_plan_has_expected_results() {
        MealPlan mealPlan = new MealPlan(1, 0, 1, LocalDate.of(2023, 12, 1));
        int actualMealPlanId = sut.addMealPlan(mealPlan);
        MealPlan actualMealPlan = sut.getMealPlanById(actualMealPlanId);
        MealPlan createdMealPlan = sut.getMealPlanById(mealPlan.getId());
        assertMealPlansMatch(createdMealPlan, actualMealPlan);
    }



    private void assertMealPlansMatch(MealPlan expected, MealPlan actual) {
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getDateToCook(), actual.getDateToCook());
        Assert.assertEquals(expected.getPlannedMealId(), actual.getPlannedMealId());
        Assert.assertEquals(expected.getUserId(), actual.getUserId());
    }
}
