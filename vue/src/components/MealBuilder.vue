<template>
  <div>
    <!-- 
     The meal builder consists of two panels arranged side by side.
     The panel on the left contains a grid of images that represent users' recipes.
     The panel on the right contains a field where users drag recipe images to build a meal. 
     Below the panel are inputs where the user can add a name, and a category for the meal as strings.        
    -->
    <div class="meal-builder-container">
      <draggable
        class="user-recipes-container"
        :list="recipes"
        @start="drag = true"
        @end="drag = false"
        group="recipeHolder"
      >
        <figure class="recipe-card" v-for="recipe in recipes" :key="recipe.id">
          <img
            class="recipe-card-image"
            :src="recipe.image"
            :alt="recipe.name"
          />
          <span class="recipe-card-title">{{ recipe.name }}</span>
        </figure>
      </draggable>

      <div class="new-meal-editor">
        <h2 class = "meal-header">Drag recipes for your meal here</h2>
        <input type="text" v-model="newMeal.mealName" id="meal-name" placeholder="Name your meal">
        <button class="create-meal-button" @click="createMeal()">
          Create Meal
        </button>
        <draggable
          id="add-recipe-box"
          :list="newMealRecipes"
          @start="drag = true"
          @end="drag = false"
          group="recipeHolder"
        >
          <figure v-for="recipe in newMealRecipes" :key="recipe.id">
            <img
              class="recipe-card-image"
              :src="recipe.image"
              :alt="recipe.name"
            />
            <span class="recipe-card-title">{{ recipe.name }}</span>
          </figure>
        </draggable>
        <div>
          <draggable id="meal-sortable" class="user-meals">
            <figure
              class="meal-card"
              id="user-meal-list"
              v-for="meal in meals"
              :key="meal.id"
            >
              <span class="recipe-card-title">{{ meal.mealName }}</span>
            </figure>
          </draggable>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import RecipeService from "@/services/RecipeService.js";
import MealService from "@/services/MealService.js";
import draggable from "vuedraggable";

export default {
  name: "meal-builder",
  components: {
    draggable,
  },
  data() {
    return {
      newMeal: {
        mealName: "",
      },
      newMealRecipes: [],
      recipes: [],
      meals: [],
    };
  },
  methods: {
    createMeal() {
      MealService.addMeal(this.newMeal).then((response) => {
        if (response.status === 201) {
          this.newMeal.id = response.data;
          let mealDto = {
            id: this.newMeal.id,            
            recipes: this.newMealRecipes,
          };
          MealService.addRecipesToMeal(mealDto)
            .then((response) => {
              console.log(response.status);
              console.log(response.data);
              this.newMealRecipes = [];
              this.newMeal = {};
              MealService.getAllUserMeals()
                .then(response => {
                  this.meals = response.data; 
                })
            })
            .catch((error) => {
              console.log(error.message);
              if (error.response) {
                console.log(error.response.data);
              }
              if (error.request) {
                console.log(error.request);
              }
            });
        }
      });
    },
    editMeal() {
      /*
      If the item we drag and drop into the box is a meal, here's what needs to happen: 
      - The interface must let the user know that they're changing a meal rather than making a new one. 
      - We need to break the meal down into its constituent recipies. 
      - We need some kind of way to cancel the operation intuitively. 
      Would be cool to like, show the recipies shlorping back into a meal. 
      */
      if (this.newMeal.mealName !== undefined) {
        // get constituent recipes from API and display them
        MealService.getRecipesForMeal();
      }
    },
  },
  created() {
    // get the user's recipes and meals
    RecipeService.listRecipesByUser()
      .then((response) => {
        this.recipes = response.data;
        MealService.getAllUserMeals()
          .then((response) => {
            this.meals = response.data;
          })
          .catch((error) => {
            if (error.response) {
              console.log(error.response.status);
              console.log(error.response.data);
            }
            if (error.request) {
              console.log(error.request.headers);
              console.log(error.request);
            }
            console.log(error.message);
          });
      })
      .catch((error) => {
        if (error.response) {
          console.log(error.response.status);
          console.log(error.response.data);
        }
        if (error.request) {
          console.log(error.request.headers);
          console.log(error.request);
        }
        console.log(error.message);
      });
  },
};
</script>

<style scoped>
.meal-builder-container {
  display: grid;
  grid-template-columns: 0.5fr 1.5fr;
  grid-template-areas: "userRecipes newMeal";
  gap: 20px;
}
.meal-header{
  grid-area: header;
  justify-self: center;
  padding-top: 5rem;
}
.new-meal-editor {
  grid-area: newMeal;
  display: grid;
  grid-template-columns: 100%;
  grid-template-rows: 1fr 2fr;
  grid-template-areas:
    "header" "addRecipeBox"
    "userMeals";
  background-color: #4a180c;

  color: white;
  margin: 5rem;
  border-radius: 5rem;
}
input{
  color: black;
}
.user-recipes-container {
  grid-area: userRecipes;
  min-width: 22rem;
  max-height: 80%;
  display: flex;
  justify-content: center;
  flex-wrap: wrap;
  background-color: #4a180c;
  color: white;
  margin: 5rem;
  border-radius: 5rem;
}
.user-recipes-container h3 {
  justify-self: center;
}
figure {
  display: flex;
  flex-direction: column;
  border: white solid 6px;
  align-items: center;
  color: white;
  height: 20rem;
  margin: 1rem;
  width: 20rem;
  border-radius: 5rem;
}
figure img {
  height: 15rem;
  width: 15rem;
  border-radius: 10px;
}
h2 {
  justify-self: flex-start;
  order: -1;
  flex-basis: 10%;
}
figure span {
  width: 90%;
  text-align: center;
  text-overflow: ellipsis;
}
#add-recipe-box {
  grid-area: addRecipeBox;
  width: 90%;
  min-height: 22rem;
  max-height: 24rem;
  border-radius: 5rem;
  border: white 1px solid;
  margin: 3rem;
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  align-items: center;
}
.new-meal-editor input{
  border-radius: 5px;
  margin-left: 7rem;
  margin-right: 14rem;
}
.create-meal-button {
  height: 50px;
  border-radius: 5px;
  margin-left: 7rem;
  margin-right: 14rem;
}
.user-meals {
  grid-area: userMeals;
  display: flex;
}
.meal-card {
  max-height: 5rem;
}
#meal-sortable {
  display: flex;
  flex-wrap: wrap;
  padding-left: 5rem;
  }
</style>