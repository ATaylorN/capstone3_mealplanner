<template>
  <div>
    <!-- 
     The meal builder consists of two panels arranged side by side.
     The panel on the left contains a grid of images that represent users' recipes.
     The panel on the right contains a field where users drag recipe images to build a meal. 
     Below the panel are inputs where the user can add a name, and a category for the meal as strings.        
    -->
    <div class="meal-builder-container">

      <draggable class="user-recipes-container" :list="recipes" @start="drag = true" @end="drag = false" group="recipeHolder">
        <figure class="recipe-card" v-for="recipe in recipes" :key="recipe.id">
          <img class="recipe-card-image" :src="recipe.image" :alt="recipe.name"/>
          <span class="recipe-card-title">{{ recipe.name }}</span>
        </figure>
      </draggable>

      <div class="new-meal-editor">
        <h2 class = "meal-header">Drag Recipes to Add to Your Meal:</h2>
        <input type="text" v-model="newMeal.mealName" id="meal-name" placeholder="Name your meal">
        <button class="create-meal-button" @click="createMeal()">Create Meal</button>
        
        <draggable id="add-recipe-box" :list="newMealRecipes" @start="drag = true" @end="drag = false" group="recipeHolder">
          <figure class="recipe-card" v-for="recipe in newMealRecipes" :key="recipe.id">
            <img class="recipe-card-image" :src="recipe.image" :alt="recipe.name"/>
            <span class="recipe-card-title">{{ recipe.name }}</span>
          </figure>
        </draggable>

        <div>
          <draggable id="meal-sortable" class="user-meals">
            <figure class="meal-card" id="user-meal-list" v-for="meal in meals" :key="meal.id">
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
  display: flex;
  justify-content: center;

}
.meal-header{
  grid-area: header;
  justify-self: center;
  padding-top: 2rem;
}
.new-meal-editor {
  max-height: 80vh;
  grid-area: newMeal;
  display: grid;
  grid-template-columns: 100%;
  grid-template-rows: 1fr 2fr 0.5fr;
  grid-template-areas: "header"
                        "addRecipeBox"
                        "userMeals";
  background-color: #4a180c;
  color: white;
  margin: 5rem;
  border-radius: 20px;
}
input{
  color: black;
}
.user-recipes-container {
  max-height: 650px;
  flex-direction: column;
  justify-content: center;
  background-color: #4a180c;
  color: white;
  margin: 5rem 1rem 5rem 5rem;
  border-radius: 20px;
  overflow: scroll;
  overflow-x: hidden;
}
.user-recipes-container::-webkit-scrollbar {
  display: none;
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
  border-radius: 10px;
}
.recipe-card img {
  border: 2 solid white;
  max-width: 190px;
  max-height: auto;
  border-radius: 4px;
}
.recipe-card span {
  max-width: 90%;
  text-align: center;
  padding: 1rem 1rem 1rem 1rem;
  text-overflow: ellipsis;
}
#add-recipe-box {
  grid-area: addRecipeBox;
  width: 90rem;
  height: 24rem;
  border-radius: 2rem;
  border: white 1px solid;
  max-width: 90rem;
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  margin: auto;
  align-items: center;
  overflow: auto;
}

#meal-name {
  margin: 2rem 8rem 2rem 8rem;
  font-size: 18px;
  border-radius: 1rem;
}

.create-meal-button {
    padding: 1rem 2rem 1rem 2rem;
    text-align: center;
    font-size: 18px;
    border-radius: 8px;
    background-color: #86B775;
    color: white;
    font-family: 'Montserrat', sans-serif;
    text-decoration: none;
    margin: 0.2rem 8rem 2rem 8rem;
  /* margin-left: 7rem;
  margin-right: 14rem; */
}
.create-meal-button:hover{
  background-color: darkgreen;
}
.user-meals {
  grid-area: userMeals;
  display: flex;
  flex-wrap: wrap;
  padding: 2rem 2rem 2rem 2rem;
  justify-content: center;
}
.meal-card {
  max-height: 5rem;
}
#meal-sortable {
  display: flex;
  flex-wrap: wrap;
  padding-left: 5rem;
  max-width: 100rem;
  }
</style>