<template>

  <div class="recipeContainer">
      <h2 class="recipeName"> {{recipe.name}} </h2>
      
      <div class="container">
          <img id="recipeImage" :src="recipe.image" alt="" class="img-rounded">          
         
          <div class ="ingredients-container"> 
              <h3>Ingredients:</h3>  
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
.recipeName {
    padding: 4rem 2rem 4rem 2rem;
}
.container {
    justify-content: space-around;
    display: grid;
    grid-template-rows: 1fr 1fr;
    grid-template-areas: 
        "image instructions"
        "ingredients instructions";
    height: 100vh;
}
.recipeContainer{
    border-radius: 8px;
    align-content: center;
    border-style: solid;
    border-color: black;
    margin-top: 10rem;
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
    margin-bottom: 0px;
}

.ingredientsList{
    /* border: 5px solid yellow; */
    list-style: none;
    padding: 2rem;
    background-color: white;
    background-image: none;
    height: 50vh;
    border-radius: 6px;
    width: 100%;
}

.ingredients-container{
    /* border: 5px solid yellow; */
grid-area: ingredients;
display: flex;
flex-direction: column;
width: 45rem;
height: 30rem;
}

.instructions {
    padding: 2rem 4rem 2rem 4rem;
    background-image: none;
    background-color: white;
    color: black;
    grid-area: instructions;
    border-radius: 6px;
    height: 90vh;
}

.instructions h3{
    padding-bottom: 2rem;
}

</style>