<template>
  <div>
   
    <div class = "container">
        <div class = "userRecipes">

        </div>
        <div class = "newMeal">

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
.container{
    display: grid;
    grid-template-columns: 1fr 1fr;
    grid-template-areas:
        "userRecipes newMeal"
        "userRecipes newMeal"
}


</style>