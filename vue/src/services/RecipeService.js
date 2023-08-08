import axios from "axios";

export default{
    addRecipe(recipe){
        return axios.post('/recipes', recipe);
    },
    listRecipes(){
        return axios.get('/recipes');
    },
    getRecipeById(id){
        return axios.get(`/recipes/${id}`); 
    },

    listRecipesByUser(id){
        return axios.get(`/user/${id}`);
    }
}