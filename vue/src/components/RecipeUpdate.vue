<template>
<div>
  <Header />
  <form v-on:submit.prevent>
    <div class="field">
      <label for="name">Name</label>
      <input name="name" type="text" v-model="name" />
    </div>
    <div class="field">
      <label for="image">image</label>
      <input name="image" type="text" v-model="image" />
    </div>
    <div class="field">
      <label for="ingredients">ingredients</label>
      <input name="ingredients" type="text" v-model="ingredients" />
    </div>
    <div class="field">
      <label for="instructions">instructions</label>
      <input name="instructions" type="text" v-model="instructions" />
    </div>
    <div class="actions">
      <button type="submit" v-on:click="updateRecipe()">Update</button>
    </div>
  </form>
  </div>
</template>

<script>
import RecipeService from "../services/RecipeService";
export default {
  name: "updateRecipe",
  data() {
    return {
      name: "",
      image: "",
      ingredients: "",
      instructions: "",
    };
  },
  methods: {
    updateRecipe() {
      const recipe = {
        name: this.name,
        image: this.image,
        ingredients: this.ingredients,
        instructions: this.instructions,
      };
      RecipeService.updateRecipe(recipe.id, recipe).then((response) => {
        if (response.status === 200) {
          this.$router.push(`/${recipe.id}`);
        }
      });
    },
  },
  created() {
    RecipeService.updateRecipe(this.$route.params.id, this.recipe)
      .then((response) => {
        this.recipe = response.data;
      })
      .catch((error) => {
        console.error(error);
      });
  },
};
</script>

<style>
</style>