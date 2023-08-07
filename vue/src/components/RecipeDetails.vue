<template>
  <div>
      {{recipe.name}}, {{recipe.image}}, {{recipe.instructions}}, {{recipe.ingredients}}
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