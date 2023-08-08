<template>
    <div class="container">
      <table class="recipe-table">
       
        <main class="list-display">
        <div v-for="recipe in recipes" :key="recipe.id">
          <p class="recipe-name">
            <router-link :to="{ name: 'recipe-details', params: { id: recipe.id } }"> 
              {{ recipe.name }}
            </router-link> </p>
            <img class="recipe-image" :src="recipe.image" alt="" />
        </div>
        </main>
      </table>
    </div>
</template>

<script>
import RecipeService from "@/services/RecipeService.js";

export default {
  name: "recipe-list",
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