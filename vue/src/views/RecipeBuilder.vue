<template>
  <div class="recipe-builder">
    <!-- 
          list of ingredients on one side
          recipe form on the other, with element to display added ingredients
    -->
    <ul class="ingredients">
      <li v-for="ingredient in ingredients" :key="ingredient.id">
        <span @click="addNewIngredientToRecipe(ingredient)">
          {{ ingredient.name }}
        </span>
        <img
          @click="addNewIngredientToRecipe(ingredient)"
          :src="ingredient.image"
          alt=""
        />
      </li>
    </ul>
    <form @submit.prevent="addRecipe()" class="add-recipe">
      <input type="text" v-model="newRecipe.name" placeholder="Recipe Name" />
      <input
        type="text"
        v-model="newRecipe.instructions"
        placeholder="Prep Instructions"
      />
      <input type="text" v-model="newRecipe.image" placeholder="Image Link" />
      <input
        type="text"
        v-model="newRecipe.ingredients"
        placeholder="Ingredients"
      />

      <button>Save Recipe</button>
    </form>
  </div>
</template>

<script>
import ingredientService from '@/services/IngredientService.js';
import recipeService from '@/services/RecipeService.js';
import IngredientList from '@/components/IngredientList.js'
export default {
  name: "recipe-builder",
  data() {
    return {
      ingredients: [],
      newRecipe: {},
      newRecipeIngredients: [],
      searchInputValue: "",
      searchTerm: "",
      autoCompleteSuggestion: "",
      newIngredient: {
          name: "",
      },
      potentialSuggestions: [],
      searched: false,
      searchResults: [],
      newIngredients: [],   
    };
  },
  computed:{
        filteredIngredientList(){
            return this.ingredients.filter(ingredient => {
                return ingredient.name.includes(this.searchTerm); 
            }); 
        },

  },
  methods: {
    runSearch(){
    this.searchTerm = this.searchInputValue + this.autoCompleteSuggestion;
    this.autoCompleteSuggestion = "";
    this.searchInputValue = "";  
    this.searched = true;
    },
    suggestSearchTerm(){
    // when the user types something in the search box, try to auto-complete
    this.potentialSuggestions = this.filteredIngredientList.filter(ingredient => {
            return (ingredient.name.substring(0, this.searchInputValue.length) === this.searchInputValue);
        }); 
      if(this.potentialSuggestions.length > 0 && this.searchInputValue !== ""){
              this.autoCompleteSuggestion = this.potentialSuggestions[0].name.substring(this.searchInputValue.length);
                                          
      } else {
          this.autoCompleteSuggestion = "";
          this.potentialSuggestions = [];
      }
    },
    addRecipe() {
      console.log(this.newRecipe);
      let newRecipeId = null;
      recipeService
        .addRecipe(this.newRecipe)
        .then((response) => {
          if (response.status === 201) {
            newRecipeId = response.data;
            ingredientService
              .addIngredientsToRecipeIngredientList(
                this.newRecipeIngredients,
                newRecipeId
              )
              .then((response) => {
                if (response.status === 201) {
                  console.log("yay it worked");
                }
              })
              .catch((error) => {
                console.log(error);
              });
          } 
        })
        .catch((error) => {
          console.error(error);
        });
    },
    addNewIngredientToRecipe(ingredient) {
      console.log(ingredient.name);
      this.newRecipeIngredients.push(ingredient);
    },
  },
  created() {
    ingredientService
      .getAllIngredients()
      .then((response) => {
        if (response.status === 200) {
          this.ingredients = response.data;
        }
      })
      .catch((error) => {
        console.error(error);
      });
  },
};
</script>

<style>
div.recipe-builder {
  display: grid;
  grid-template-columns: 1fr 1fr;
  grid-template-areas: "ingredients recipe";
}
</style>