<template>

  <div class="recipeContainer">
      <h2 class="recipeName"> {{recipe.name}} </h2>
      
      <div class="recipe-body">
          <img id="recipeImage" :src="recipe.image" alt="" class="img-rounded">          
         
          <div class ="ingredients-container"> 
              <h3>Ingredients:</h3>  
              <ul class="ingredientsList">
                  <li class="ingredient" v-for="ingredient in recipe.ingredients.ingredientList" :key="ingredient.id">
                      <span>{{ingredient.name}}</span>
                                                       
                  </li>
              </ul>
          </div>
         
          <div class="instructions">
              <h3>Instructions:</h3>
              <p>{{recipe.instructions}}</p>
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

<style scoped>
.recipeName {
    padding: 4rem 2rem 4rem 2rem;
}
.recipe-body {
    justify-content: space-around;
    display: grid;
    grid-template-rows: .5fr 1fr;
    grid-template-areas: 
        "image instructions"
        "ingredients instructions";
    height: 80vh;
}
.recipeContainer{
    border-radius: 8px;
    align-content: center;
    margin-top: 10rem;
    margin-bottom: 10px;
    margin-right: auto;
    margin-left: auto;
    background-color: #4a180c;
    color: white;
    text-align: center;
    max-width: 1100px;
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
    list-style: none;
    padding: 2rem;
    background-color: white;
    background-image: none;
    max-height: 40rem;
    border-radius: 6px;
    width: 100%;
}

.ingredients-container{
grid-area: ingredients;
display: flex;
flex-direction: column;
width: 45rem;
max-height: 35rem;
overflow: hidden;
}
.ingredients-container:hover{
    overflow-y: auto;
}

.instructions {
    padding: 2rem 2rem 2rem 2rem;
    background-image: none;
    background-color: white;
    color: black;
    grid-area: instructions;
    border-radius: 6px;
    height: 70rem;
    max-width: 50rem;
    max-height: 56rem;
    overflow: hidden;
}


.instructions:hover{
    overflow-y: auto;
}

.instructions h3{
    padding-bottom: 2rem;
}

</style>