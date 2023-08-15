import axios from "axios";

export default {
    getMealPlansForDateRange(startDate, endDate){
        return axios.get(`/meals/meal-plan?${startDate}&to=${endDate}`)
    },
    getMealPlanIngredients(startDate, endDate){
        return axios.get(`ingredients/groceryList?fromDate=${startDate}&toDate=${endDate}`)
    }
}