<template>
  <div class="recipeContainer">
      <h2 class="recipeName">
          {{recipe.name}}
      </h2>
      <div class="updateBtn">
          <router-link :to="{ name: 'recipe-update', params: {id: recipe.id} }" tag="button">Update
          </router-link>
      </div>
      <div>
          <img id="recipeImage" :src="recipe.image" alt="" class="img-rounded">
          <body class="ingredientsList">
              {{recipe.ingredients}}
          </body>
          <body class="instructions">
              {{recipe.instructions}}
          </body>
      </div>
  </div>
</template>

<script>
import RecipeService from '@/services/RecipeService.js';

export default {
    name: "RecipeDetails",
    props: ['recipeId'],
    data(){
       return {
           recipe: {}           
       }
    },
    created(){
        RecipeService.getRecipeById(this.recipeId)
        .then(response => {   
            // add 404 push if recipe not found.
            // shouldn't happen since we're getting ID off thing that exists, but nice to have         
            this.recipe = response.data;             
        })
        .catch(error =>{
            console.error(error)
        })
    }
}
</script>

<style>
.recipeContainer{
    align-content: center;
    border-style: solid;
    border-color: black;
    margin-top: 50px;
    background-color: #4a180c;
    color: white;
    text-align: center;
}
.updateBtn{
    color: black;
}

#recipeImage {
    max-height: 400px;
    max-width: 400px;
}

.ingredientsList{
    background-color: white;
    background-image: none;
}

.instructions {
    background-image: none;
    background-color: white;
}
</style>