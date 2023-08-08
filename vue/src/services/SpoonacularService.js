import axios from 'axios';


export default{
    searchIngredients(queryString){
        return axios.get(`https://api.spoonacular.com/food/ingredients/search?query=${queryString}`)
    }

}