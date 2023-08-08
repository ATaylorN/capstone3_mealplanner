import axios from 'axios';

export default{
    searchIngredients(q, k){
        return axios.get(`https://api.spoonacular.com/food/ingredients/search?query=${q}&apiKey=${k}`)
    }

}