<template>
  <div>
    <!-- 
     The meal builder consists of two panels arranged side by side.
     The panel on the left contains a grid of images that represent users' recipes.
     The panel on the right contains a field where users drag recipe images to build a meal. 
     Below the panel are inputs where the user can add a name, and a category for the meal as strings.        
    -->
    <div class="meal-builder-container">

      <draggable class="user-recipes-container" :list="recipes" @start="drag = true" @end="drag = false" :group="{name: 'recipeHolder', pull: 'clone'}">
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

        <div class="meals-container">
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
.recipe-card:hover{
  cursor: pointer;
  border: 2px solid black;
  transform: scale(1.20); 
  transition-duration: 0.5s;
}
.meal-builder-container {
  display: grid;
  grid-template-columns: 1fr 3fr;
  grid-template-areas: "usermeals newmeals";
  gap: 2rem;
  margin: 4rem 20rem;

}
.meal-header{
  grid-area: header;
  justify-self: center;
  padding-top: 2rem;
}
.new-meal-editor {
  grid-area: newmeals;
  display: grid;
  grid-template-rows: 1fr 2fr 0.5fr;
  grid-template-areas: "header"
                        "addRecipeBox"
                        "userMeals";
  background-color: #4a180c;
  color: white;
  box-shadow: 0px 6px 20px 0px black;
  margin-top: 1rem;
  margin-right: auto;
  margin-left: auto;
  align-content: center;
  padding-top: 2rem;
  border-radius: 20px;
  width: 50vw;
}
input{
  color: black;
}
.user-recipes-container {
  grid-area: usermeals;
  max-height: 60rem;
  flex-direction: column;
  justify-content: center;
  background-color: #4a180c;
  color: white;
  box-shadow: 0px 6px 20px 0px black;
  margin-left: auto;
  margin-right: auto;
  margin-top: 1rem;
  padding: 2rem;
  border-radius: 20px;
  overflow: hidden;
}
.user-recipes-container:hover {
  overflow: auto;
}
.meal-builder-container figure {
  display: flex;
  flex-direction: column;
  text-align: center;
  margin-left: auto;
  margin-right: auto;
  margin-bottom: 2rem;
  background-color: white;
  color: black;
  border-radius: 4px;
  font-weight: 900;
  font-variant-caps:all-small-caps;
  padding: 1rem;
}
.recipe-card img {
  border: 2 solid white;
  height: 200px;
  width: 200px;
  object-fit: fill;
  border-radius: 4px;
  padding: 0.5rem;
}
.recipe-card span {
  max-width: 90%;
  text-align: center;
  padding: 1rem;
  text-overflow: ellipsis;
}
#add-recipe-box {
  grid-area: addRecipeBox;
  width: 40vw;
  height: 24rem;
  border-radius: 2rem;
  border: white 1px solid;
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  margin: auto;
  overflow: auto;
}

#meal-name {
  margin-right: auto;
  margin-left: auto;
  font-size: 18px;
  border-radius: 1rem;
  width: 60rem;
}

.create-meal-button {
    padding: 1rem 1rem 1rem 1rem;
    text-align: center;
    font-size: 18px;
    border-radius: 8px;
    background-color:wheat;
    color: black;
    font-family: 'Montserrat', sans-serif;
    text-decoration: none;
    margin: 0.2rem 1rem 2rem 1rem;
    width: 60rem;
    margin-right: auto;
    margin-left: auto;
}
.create-meal-button:hover{
  background-color: darkkhaki;
  cursor: pointer;
}
.meals-container{
  overflow: hidden;
}
.meals-container:hover{
  overflow: auto;
}
.user-meals {
  grid-area: userMeals;
  display: flex;
  flex-wrap: wrap;
  margin: 2rem;
  justify-content: center;
}
.meal-card{
  width: 30rem;
  height: 4rem;
  font-size: 18px;
  justify-content: center;
}

@media screen and (max-width: 1380px){
  .meal-builder-container {
  grid-template-columns: 1fr;
  grid-template-areas: "usermeals"
                        "newmeals";
  gap: 1rem; 
  /* margin: 4rem 20rem; */
}
.user-recipes-container {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  
}
#meal-name {
  width: 30rem;
}
.create-meal-button {
  width: 30rem;
}
.user-recipes-container {
  max-height: none;
}
}

</style>