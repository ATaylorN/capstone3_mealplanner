import axios from "axios";

export default {
    addMeal(meal){
        return axios.post('/meals', meal);
    },
    getAllUserMeals(){
        return axios.get('/meals'); 
    }
    
}