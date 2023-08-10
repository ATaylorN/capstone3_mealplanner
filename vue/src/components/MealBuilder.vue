<template>
  <div>
   <!-- 
     The meal builder consists of two panels arranged side by side.
     The panel on the left contains a grid of images that represent users' recipes.
     The panel on the right contains a field where users drag recipe images to build a meal. 
     Below the panel are inputs where the user can add a name, and a category for the meal as strings.        
    -->
    <div class ="meal-builder-container">      
          <draggable class="user-recipes-container" :list="recipes" @start="drag=true" @end="drag=false" group="recipeHolder" >                           
          <figure class="recipe-card" v-for="recipe in recipes" :key="recipe.id">
            <img class="recipe-card-image" :src="recipe.image" :alt="recipe.name">
            <span class="recipe-card-title">{{recipe.name}}</span>
          </figure>
          </draggable>
        <div class ="new-meal-editor">

          <draggable id="add-recipe-box"  :list="newMeal.newMealRecipes"  @start="drag=true" @end="drag=false" group="recipeHolder">
            <figure v-for="recipe in newMeal.newMealRecipes" :key="recipe.id">
              <img class="recipe-card-image" :src="recipe.image" :alt="recipe.name">
              <span class="recipe-card-title">{{recipe.name}}</span>
            </figure >
          </draggable>
          
          <draggable id="meal-sortable">
            <figure id="user-meal-list" v-for="meal in meals" :key="meal.id">
              <img class="recipe-card-image" :src="recipe.image" :alt="recipe.name">
              <span class="recipe-card-title">{{recipe.name}}</span>
            </figure>
          </draggable>
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
      newMeal: {
        newMealRecipes: []
      },
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
    grid-template-columns: .5fr 1.5fr;
    grid-template-areas: "userRecipes newMeal"
                        "userRecipes newMeal";
    gap: 20px; 
}
.new-meal-editor{
  grid-area: newMeal;
  background-color: #4a180c;
  display: flex; 
  justify-content: center;
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
 figure{
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
figure img{
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
#add-recipe-box{
  justify-self: flex-start;
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

</style>