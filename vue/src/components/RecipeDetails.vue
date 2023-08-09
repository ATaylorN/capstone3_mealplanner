<template>
  <div>
      <h2>
          {{recipe.name}}
      </h2>
      <div>
          <router-link :to="{ name: 'recipe-update', params: {id: recipe.id} }" tag="button">Update
          </router-link>
      </div>
      <div>
          <img class="recipe-image" :src="recipe.image" alt="">
          <body>
              {{recipe.ingredients}}
          </body>
          <body>
              {{recipe.instructions}}
          </body>
      </div>
  </div>
</template>

<script>
import RecipeService from '@/services/RecipeService.js';

export default {
    name: "RecipeDetails",
    data(){
       return {
           recipe: {}           
       }
    },
    created(){
        RecipeService.getRecipeById(this.$route.params.id)
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

</style>