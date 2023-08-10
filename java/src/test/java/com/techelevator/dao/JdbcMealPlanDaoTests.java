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
}
