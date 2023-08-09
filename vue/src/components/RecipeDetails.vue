<template>
  <div class="recipeContainer">
      <h2 class="recipeName">
          {{recipe.name}}
      </h2>
      <div class="updateBtn">                   
      </div>
      <div>
          <img id="recipeImage" :src="recipe.image" alt="" class="img-rounded">          
          <div > Ingredients: 
              <ul class="ingredientsList">
                  <li class="ingredient" v-for="ingredient in recipe.ingredients.ingredientList" :key="ingredient.id">
                      <span>{{ingredient.name}}</span>
                      <span> ingredient image placeholder: {{ingredient.image}} </span>                                           
                  </li>
              </ul>
          </div>
          <div class="instructions">
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
    max-width: 400px;
}

.ingredientsList{
    list-style: none;
    background-color: white;
    background-image: none;
}

.instructions {
    background-image: none;
    background-color: white;
    color: black
}
</style>