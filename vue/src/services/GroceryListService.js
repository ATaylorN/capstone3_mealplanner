import axios from "axios";

export default {
    getMealPlanIngredients(startDate, endDate){
        return axios.get(`/meals/meal-plan?${startDate}&to=${endDate}`)
    }
}