<template>
      <form @submit="addRecipe()" class="add-recipe">
        <input type="text" v-model="newRecipe.name" placeholder="Recipe Name">
        <input type="text" v-model="newRecipe.instructions" placeholder="Prep Instructions">
        <input type="text" v-model="newRecipe.image" placeholder="Image Link">
        <input type="text" v-model="newRecipe.ingredients" placeholder="Ingredients">
        <button>Save Recipe</button>
    </form>
</template>

<script>
import RecipeService from '@/services/RecipeService.js';
export default {


}
</script>

<style>

</style>