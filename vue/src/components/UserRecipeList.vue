<template>
  <div>
          <div v-for="recipe in recipes" :key="recipe.id" class="recipesList" > 
                  <RecipeDetails :recipeId ="recipe.id" />                
                  <router-link :to="{ name: 'recipe-details', params: {id: recipe.id} }" tag="button">View Details</router-link>
                  <router-link :to="{ name: 'recipe-update', params: {id: recipe.id} }" tag="button">Update</router-link>
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
#recipe-image{
    max-height: 350px;
    max-width: 350px;
}

/* .recipesList {
    display: flex;
    flex-direction: column;
    border-style: solid;
    border-color: black;
    background-color: #4a180c;
    color: white;
    padding: 10px;
    text-align: center;
    margin: 50px;
} */

</style>