<template>
  <div class="list-page">
    <div class="recipe-card" v-if="!bigMode">
      <div class="rec-body">
        <img id="rec-image" :src="recipe.image" alt="" class="img-rounded" />
      </div>
      <h2 class="rec-name">{{ recipe.name }}</h2>
    </div>

    <div class="recipeContainer" v-if="bigMode" v-bind:id="recipeId">
      <h2 class="recipeName">{{ recipe.name }}</h2>
      <div class="recipe-body">
        <img id="recipeImage" :src="recipe.image" alt="" class="img-rounded" />
        <div class="ingredients-container">
          <h3>Ingredients:</h3>
          <ul class="ingredientsList">
            <li
              class="ingredient"
              v-for="ingredient in recipe.ingredients.ingredientList"
              :key="ingredient.id"
            >
              <span>{{ ingredient.name }}</span>
            </li>
          </ul>
        </div>
        <button class="recipePrintButton" @click="printRecipe()">print</button>
        <div class="instructions">
          <h3>Instructions:</h3>
          <p>{{ recipe.instructions }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { Printd } from "printd";
import RecipeService from "@/services/RecipeService.js";

export default {
  name: "RecipeDetails",
  props: ["recipeId", "bigMode", "num"],
  data() {
    return {
      recipe: {},
    };
  },
  methods: {
    printRecipe() {
      console.log(document.getElementById(this.recipeId));
      const id = new Printd();
      id.print(document.getElementById(this.recipeId));
      console.log("good job");
    },
    getPrintId() {
      if (this.num) {
        return this.num;
      } else {
        return "print id";
      }
    },
  },
  created() {
    RecipeService.getRecipeById(this.recipeId)
      .then((response) => {
        // add 404 push if recipe not found.
        // shouldn't happen since we're getting ID off thing that exists, but nice to have
        this.recipe = response.data;
        RecipeService.getRecipeIngredients(this.recipe.id)
          .then((response) => {
            this.recipe.ingredients = response.data;
          })
          .catch((error) => {
            console.log(error);
          });
      })
      .catch((error) => {
        console.error(error);
      });
  },
};
</script>

<style scoped>
#rec-image {
  height: 200px;
  width: 200px;
  object-fit: fill;
}
.recipe-card {
  border-radius: 8px;
  margin-right: auto;
  margin-left: auto;
  margin-top: 4rem;
  background-color: #4a180c;
  box-shadow: 0px 6px 20px 0px black;
  color: white;
  display: flex;
  flex-direction: row;
  max-width: 65rem;
}
.rec-body {
  padding: 1rem 2rem;
}
.recipe-card h2 {
  padding: 2rem;
  font-size: 40px;
}

.recipeName {
  padding: 4rem 2rem 4rem 2rem;
  font-size: 40px;
}
.recipe-body {
  display: grid;
  grid-template-rows: 1fr 0.5fr;
  grid-template-columns: 1fr 1fr;
  grid-template-areas:
    "image instructions"
    "ingredients instructions";
  overflow: hidden;
  margin-bottom: 4rem;
}
.recipeContainer {
  border-radius: 8px;
  margin: auto;
  background-color: #4a180c;
  box-shadow: 0px 6px 20px 0px black;
  color: white;
  text-align: center;
  max-width: 1100px;
}
.ingredient {
  color: black;
}
.ingredient span {
  font-size: 14px;
}
#recipeImage {
  max-height: 50rem;
  max-width: 45rem;
  grid-area: image;
  margin-right: auto;
  margin-left: auto;
}
.ingredientsList {
  list-style: none;
  margin-bottom: 2rem;
  padding: 2rem;
  background-color: white;
  background-image: none;
  border-radius: 6px;
  width: 100%;
  overflow: hidden;
}
.ingredientsList:hover {
  overflow: auto;
}
.ingredients-container {
  grid-area: ingredients;
  display: flex;
  flex-direction: column;
  width: 50rem;
  margin-right: auto;
  margin-left: auto;
  margin-top: 2rem;
}
.instructions {
  margin-left: auto;
  margin-right: auto;
  padding: 1rem;
  background-image: none;
  background-color: white;
  color: black;
  grid-area: instructions;
  border-radius: 6px;
  max-width: 50rem;
  margin-bottom: 2rem;
  overflow: hidden;
}
.instructions:hover {
  overflow: auto;
}
.instructions p {
  padding: 2rem;
  font-size: 18px;
}
.instructions h3 {
  padding-bottom: 0.5rem;
  font-size: 24px;
  font-weight: 600;
}
.recipePrintButton {
  margin: 1rem;
  font-size: 16px;
  color: black;
}

@media only screen and (max-width: 1050px) {
  .recipe-body {
    grid-template-columns: 1fr;
    grid-template-areas:
      "image"
      "ingredients"
      " instructions";
  }
}
</style>