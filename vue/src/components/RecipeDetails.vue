<template>
  <div class="recipeContainer">
      <h2 class="recipeName">
          {{recipe.name}}
      </h2>
      <div class="updateBtn">                   
      </div>
      <div class="container">
          <img id="recipeImage" :src="recipe.image" alt="" class="img-rounded">          
          <div > <h3>Ingredients:</h3>  
              <ul class="ingredientsList">
                  <li class="ingredient" v-for="ingredient in recipe.ingredients.ingredientList" :key="ingredient.id">
                      <span>{{ingredient.name}}</span>
                      <span> ingredient image placeholder: {{ingredient.image}} </span>                                           
                  </li>
              </ul>
          </div>
          <div class="instructions">
              <h3>Instructions:</h3>
              {{recipe.instructions}}
          </div>
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
            RecipeService.getRecipeIngredients(this.recipe.id)
                .then(response => {
                    this.recipe.ingredients = response.data;
                })
                .catch(error => {
                    console.log(error);
                })

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

.ingredient{
    color: black;
}

#recipeImage {
    max-height: 400px;
    max-width: 460px;
    grid-area: image;
}

.ingredientsList{
    list-style: none;
    background-color: white;
    background-image: none;
    grid-area: ingredients;
    border-radius: 6px;
}

.instructions {
    background-image: none;
    background-color: white;
    color: black;
    grid-area: instructions;
    border-radius: 6px;
}

.container {
    display: grid;
    grid-template-columns: 1fr 1fr;
    grid-template-areas: 
        "image instructions"
        "ingredients instructions"
}
</style>