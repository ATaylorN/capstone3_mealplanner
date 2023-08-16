<template>
  <div>
          <div v-for="recipe in recipes" :key="recipe.id" class="recipesList" > 
                  <RecipeDetails :bigMode="false" :recipeId ="recipe.id" />        
                  <div class="links">       
                  <router-link :to="{ name: 'recipe-details', params: {id: recipe.id} }" tag="button" class="detailsBtn">View Recipe</router-link>
                  <router-link :to="{ name: 'recipe-update', params: {id: recipe.id} }" tag="button" class="updateBtn">Update</router-link>
                </div> 
          </div>
  </div>
</template>

<script>

import RecipeService from '../services/RecipeService.js';
import RecipeDetails from '@/components/RecipeDetails'; 

export default {
    name: 'user-recipe-list',
    components: {
       RecipeDetails
    },
    data() {
        return {
            recipes: []
        }
    },
    methods:{
        goToRecipe(recipeId){
            this.$router.push({name: 'recipe-details', params:{id: recipeId}})
        }
    },
    created() {
        RecipeService.listRecipesByUser().then(response => {        
            if(response.status === 200) {
                this.recipes = response.data;
            }
        }).catch (error => {
            console.error(error);
        })
    }

}
</script>

<style>
.links {
    margin: 1rem;
    display: flex;
    justify-content: center;
}

.updateBtn{
    padding: 1rem 2rem 1rem 2rem;
    text-align: center;
    font-size: 18px;
    border-radius: 8px;
    background-color: wheat;
    color: black;
    font-family: 'Montserrat', sans-serif;
    text-decoration: none;

}
.updateBtn:hover{
    background-color: darkkhaki;
    cursor: pointer;
}

.detailsBtn{
    padding: 1rem 2rem 1rem 2rem;
    text-align: center;
    font-size: 18px;
    border-radius: 8px;
    background-color: wheat;
    color: black;
    font-family: 'Montserrat', sans-serif;
    text-decoration: none;
    box-shadow: 0px 6px 20px 0px black;
    border: 1px solid darkslategrey;
}
.detailsBtn:hover{
    background-color: darkkhaki;
    cursor: pointer;
}
</style>