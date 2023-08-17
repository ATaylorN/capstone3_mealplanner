import axios from "axios";

export default {
    
    addMeal(meal){
        return axios.post('/meals', meal);
    },
    getAllUserMeals(){
        return axios.get('/meals'); 
    }, 
    addRecipesToMeal(meal){
       let messageBody = {
            mealId: meal.id,               
            recipes: meal.recipes,
        }   
        return axios.post("/meals/recipe-list", messageBody)
    },
    getMealRecipes(id){
        return axios.get(`/meals/${id}/recipes`)
    },
    addMealPlan(mealPlan){        
        return axios.post("/meals/meal-plan", mealPlan)
    },
    getMealPlans(){
        return axios.get("/meals/meal-plan/")
    },
    deleteMealPlans(id){
        return axios.delete(`/meals/delete-meal-plan/${id}`)
    },
    getMealById(id){
        return axios.get(`/meals/meal/${id}`)
    }

}