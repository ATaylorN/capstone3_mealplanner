<template>
  <div>
   <!-- 
     The meal builder consists of two panels arranged side by side.
     The panel on the left contains a grid of images that represent users' recipes.
     The panel on the right contains a field where users drag recipe images to build a meal. 
     Below the panel are inputs where the user can add a name, and a category for the meal as strings.        
    -->
    <div class = "meal-builder-container">
        <div class = "user-recipes-container">
          
        </div>
        <div class = "new-meal-editor">

        </div>

    </div>
  </div>
</template>

<script>

import RecipeService from "@/services/RecipeService.js";
import MealService from "@/services/MealService.js";

export default {
  name: "meal-builder",
  components: {
    MealService,
    RecipeService,
  },
  data() {
    return {
      newMeal: {},
    };
  },
  methods: {
    addMeal() {
      let newMealId = null;
      MealService.addMeal(this.newMeal).then((response) => {
        if (response.status === 200) {
          newMealId = response.data;
        }
      });
    },
  },
};
</script>

<style>
.meal-builder-container{
    display: grid;
    grid-template-columns: 1fr 1fr;
    grid-template-areas:
        
        "userRecipes newMeal"
        "userRecipes newMeal"
}


</style>