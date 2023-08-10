import axios from "axios";

export {
    addMeal(meal){
        return axios.post('/meals', meal);
    },
}