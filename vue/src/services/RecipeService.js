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

    listRecipesByUser(){
        return axios.get('/recipes/user/recipes');
    },
    updateRecipe(id, recipe){
        return axios.put(`/recipes/${id}`, recipe);
    },
    getRecipeIngredients(recipeId){
        return axios.get(`/recipes/${recipeId}/ingredients`)
    }
}