<template>
    <div class="container">
        <div class="recipeList">
          <div class="recipe" v-for="recipe in recipes" :key="recipe.id">          
          <RecipeDetails v-bind:recipeId='recipe.id'/>

          <router-link :to="{ name: 'recipe-details', params: {id: recipe.id} }" tag="button">View Recipe</router-link>
          </div>      
        </div>
    </div>

</template>

<script>
import RecipeService from "@/services/RecipeService.js";
import RecipeDetails from '@/components/RecipeDetails.vue';
export default {
  name: "recipe-list",
  components: {
    RecipeDetails
  },
  data() {
    return {
      recipes: [],
      newRecipe: {
        name: "",
        ingredients: "",
        instructions: "",
      },
    };
  },

  created() {
    RecipeService.listRecipes()
      .then(response => {
        if (response.status === 200) {
          this.recipes = response.data;
        }
      })
      .catch((error) => {
        console.error(error);
      });
  },
};
</script>

<style>
#header{
    padding-bottom: 20px;
}

.recipe-image{
    max-height: 350px;
    max-width: 350px;
}

</style>