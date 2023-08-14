import axios from "axios";

export default {
    getMealPlanIngredients(startDate, endDate){
        console.log(startDate);
        console.log(endDate)
        return axios.get('/grocery-list')
    }
}