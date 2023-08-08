<template>
  <div>
    <Header id="header" />
    <div class="container">
      <table class="recipe-table">
        <tr>
          <th>Recipe Name</th>
          <th>Image</th>
        </tr>
        <tr v-for="recipe in recipes" :key="recipe.id">
          <td class="recipe-name">
            <router-link
              :to="{ name: 'recipe-details', params: { id: recipe.id } }"
            >
              {{ recipe.name }}
            </router-link>
          </td>
          <td class="recipe-image-container">
            <img class="recipe-image" :src="recipe.image" alt="" />
          </td>
        </tr>
      </table>
    </div>
  </div>
</template>

<script>
import Header from "@/components/Header.vue";
import RecipeService from "@/services/RecipeService.js";

export default {
  name: "recipe-list",
  components: {
    Header,
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