<template>
    <div class="container">
        <h1>Recipes</h1>
        <div class="recipeList">
          <RecipeDetails v-for="recipe in recipes" :key="recipe.id"/>

        </div>


      <!-- <table class="recipe-table">
       <th>Recipe</th>
        <tr v-for="recipe in recipes" :key="recipe.id">
          <tr class="recipe-name">
            <router-link :to="{ name: 'recipe-details', params: { id: recipe.id } }"> 
              {{ recipe.name }}
            </router-link> </tr>
           <tr> <img class="recipe-image" :src="recipe.image" alt="" />
        </tr>
      </table> -->
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
      .then((response) => {
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