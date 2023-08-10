<template>
  <div>
   <!-- 
     The meal builder consists of two panels arranged side by side.
     The panel on the left contains a grid of images that represent users' recipes.
     The panel on the right contains a field where users drag recipe images to build a meal. 
     Below the panel are inputs where the user can add a name, and a category for the meal as strings.        
    -->
    <div class ="meal-builder-container">
      
        <div class = "user-recipes-container">
          <draggable v-model="recipes" @start="drag=true" @end="drag=false" :click="handleMove">                           
          <figure class="recipe-card" v-for="recipe in recipes" :key="recipe.id">
            <img class="recipe-card-image" :src="recipe.image" :alt="recipe.name">
            <span class="recipe-card-title">{{recipe.name}}</span>
          </figure>
          </draggable>
        </div>
        <div class ="new-meal-editor">
          <div id="add-recipe-box"></div>

        </div>

    </div>
  </div>
</template>

<script>

import RecipeService from "@/services/RecipeService.js";
import MealService from "@/services/MealService.js";
import draggable from 'vuedraggable';

export default {
  name: "meal-builder",
  components: {
    draggable
  },
  data() {
    return {
      newMeal: {},
      recipes: [],
      meals: [], 
    };
  },
  methods: {
    handleMove(event){
      console.log(event.draggedContext.element.name);
    }
  },
  created(){
    // get the user's recipes and meals 
    RecipeService.listRecipesByUser()
      .then(response => {
        this.recipes = response.data; 
        MealService.getAllUserMeals()
          .then(response => {
            this.meals = response.data;             
          })
          .catch( error => {
            console.log("no meals yet probably"); 
            console.log(error);
          })
      })
      .catch(error => {
        if(error.response){
          console.log(error.response.status);
          console.log(error.response.data);          
        }
        if (error.request){
          console.log(error.request.headers);
          console.log(error.request)
        }
        console.log(error.message);
      })
  }
};
</script>

<style>
.meal-builder-container{
    display: grid;
    grid-template-columns: 1fr 1fr;
    grid-template-areas: "userRecipes newMeal"
                          "userRecipes newMeal";
    gap: 20px; 
}
.new-meal-editor{
  grid-area: newMeal;
  background-color: #4a180c;
  display: flex; 
  justify-content: space-between;
  flex-wrap: wrap;  
  background-color: #4a180c;
  color: white;
  margin: 5rem;
  border-radius: 5rem;
}

.user-recipes-container{
  grid-area: userRecipes;
  display: flex; 
  justify-content: space-between;
  flex-wrap: wrap;  
  background-color: #4a180c;
  color: white;
  margin: 5rem;
  border-radius: 5rem;
  
}
.user-recipes-container h3{
  justify-self: center;
}
.user-recipes-container figure{
  display: flex; 
  flex-direction: column;
  border: white solid 6px; 
  align-items: center;  
  color: white;
  margin: 10px;
  height: 20rem;
  width: 20rem; 
  border-radius: 5rem;
}
.user-recipes-container figure img{
  height: 15rem; 
  width: 15rem;
  border-radius: 10px;  
  
}


</style>