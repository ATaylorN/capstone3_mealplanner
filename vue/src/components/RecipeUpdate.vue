<template>
<div>
  <Header />
  <router-link :to="{name: 'recipe-details', params: {id: this.$route.params.id}}" tag="button">Go Back to Recipe</router-link>
  <form v-on:submit.prevent>
    <div class="field">
      <label for="name">Name</label>
      <input name="name" type="text" v-model="name" />
    </div>
    <div class="field">
      <label for="image">Image</label>
      <input name="image" type="text" v-model="image" />
    </div>
    <div class="field">
      <label for="ingredients">Ingredients</label>
      <input name="ingredients" type="text" v-model="ingredients" />
    </div>
    <div class="field">
      <label for="instructions">Instructions</label>
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
import Header from '@/components/Header.vue';
export default {
  name: "updateRecipe",
  components: {
    Header
  },
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
      console.log('update recipe got called.')
      const recipe = {
        name: this.name,
        image: this.image,
        ingredients: this.ingredients,
        instructions: this.instructions,
      };
      RecipeService.updateRecipe(this.$route.params.id, recipe).then((response) => {
        if (response.status === 200) {
          this.$router.push({ path: `/recipes/${this.$route.params.id}` });
        }
      });
    },
  },
  created(){
    // on create, pull in current recipe data + ingredients for recipe. 
    // this will need a new DAO method that returns the list of ingredients for a recipe.     
    // once we've got all the data on the page, we can make edits and return the object back
    // we'll have to add and/or delete rows from the recipe_ingredients table if modifications are made. 
    
  
  }
};
</script>

<style>
</style>