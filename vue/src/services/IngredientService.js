import axios from 'axios';


export default{
    addIngredient(ingredient){
      return axios.post('/ingredients', ingredient)
    },
    getAllIngredients(){
        return axios.get('/ingredients');
    }

}