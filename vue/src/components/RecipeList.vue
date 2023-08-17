<template>
  <div>
    <div class="recipeList" v-for="recipe in recipes" :key="recipe.id">
      <RecipeDetails v-bind:recipeId="recipe.id" :bigMode="false" v-bind:num="recipe.id" />
      <div class="links">
        <router-link :to="{ name: 'recipe-details', params: { id: recipe.id } }" tag="button" class="detailsBtn">View Recipe</router-link>
      </div>
    </div>
  </div>
</template>

<script>
import RecipeService from "@/services/RecipeService.js";
import RecipeDetails from "@/components/RecipeDetails.vue";

export default {
  name: "recipe-list",
  components: {
    RecipeDetails,
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

<style scoped>
.links {
    margin: 1rem;
    display: flex;
    justify-content: center;
}
.detailsBtn{
    padding: 1rem 2rem 1rem 2rem;
    text-align: center;
    font-size: 18px;
    border-radius: 8px;
    background-color: wheat;
    color: black;
    font-family: 'Montserrat', sans-serif;
    text-decoration: none;
}
.detailsBtn:hover{
    background-color: darkkhaki;
}

#recipe-image{
    max-height: 350px;
    max-width: 350px;
}
</style>