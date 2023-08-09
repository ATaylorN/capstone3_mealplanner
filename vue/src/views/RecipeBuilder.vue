<template>
<div> 
  <Header/>
  <div class="recipe-builder">
    <!-- 
          list of ingredients on one side
          recipe form on the other, with element to display added ingredients
    -->
    <div class="ingredients-section">
      <span> Search for Ingredients:</span> 
         <div class=search-input-block>
        <span class="search-text"> {{searchInputValue}} </span><span class="autocomplete-suggestion">{{ autoCompleteSuggestion }}</span>        
        </div><button class="search-button" @click="runSearch()">Search</button>
        <input id="search-input" class="search-field" type="text" v-model="searchInputValue" @keyup="suggestSearchTerm()">
    
        <div class="add-ingredient-container">
      <!-- <form v-show="filteredIngredientList.length === 0" class="add-ingredient"> -->
          <input type="text" v-model="newIngredient.name" placeholder="Ingredient Name">
          <button @click.prevent="addIngredient()">Find More Ingredients</button>
          <button @click.prevent="clearIngredients()">Clear Ingredient List</button>
      <!-- </form> -->
    </div>
    <ul class="ingredient-search-results">
        <li v-for="foundIngredient in searchResults" :key="foundIngredient.index">
            <span @click="addNewIngredientToRecipe(foundIngredient)"> {{foundIngredient.name}} </span>
            <img @click="addNewIngredientToRecipe(foundIngredient)" :src="foundIngredient.image" :alt="foundIngredient.name" class="img-rounded">            
        </li>
    </ul>    


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

    </div>
    <div class="recipe-data-section">
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
  </div>
  </div>
</template>

<script>
import ingredientService from '@/services/IngredientService.js';
import recipeService from '@/services/RecipeService.js';
import spoonacularService from '@/services/SpoonacularService.js'
import Header from '@/components/Header.vue'

export default {
  components: {
    Header
  },
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
    
    addIngredient(){
    this.searchIngredients();
    // ingredientService.addIngredient(this.newIngredient)
    //     .catch(error => {
    //         console.error(error);
    //     })
    },
    addRecipe() {
      console.log(this.newRecipe);
      let newRecipeId = null;
      recipeService
        .addRecipe(this.newRecipe)
        .then((response) => {
          if (response.status === 201) {
            newRecipeId = response.data;
            ingredientService.addIngredientsToRecipeIngredientList(this.newRecipeIngredients, newRecipeId)
              .then((response) => {
                if (response.status === 201) {
                  console.log("ingredients added");
                }
              })
              .catch((error) => {
                console.log(error);
              });
            this.newRecipe = {};
            this.newRecipeIngredients = [];
          } 
        })
        .catch((error) => {
          if(error.response){
            console.log(error.response.status);
            console.log(error.response.data);
            console.log(error.response.headers);
                        
          } else if (error.request){
            console.log(error.request);
            
          } else {
            console.log(error.message);
          }
        });
    },
            searchIngredients(){
            // if we don't return a satisfactory response from the database, search. 
            spoonacularService.searchIngredients(this.newIngredient.name,  process.env.VUE_APP_SPOONACULAR_API_KEY)
                .then(response => {
                    // reformat the results, we don't want their poison noodles. 
                    response.data.results.forEach(ingredient => {
                        let newIngredient = {
                            name: ingredient.name, 
                            image: 'https://spoonacular.com/cdn/ingredients_250x250/' + ingredient.image
                        }
                        this.searchResults.unshift(newIngredient);
      
                        // put new ingredient in database. (null constraint shouldn't blow up server)
                        ingredientService.addIngredient(newIngredient)
                            .then(response => {
                                console.log(response.status);
                            })
                            .catch(error => {
                                if (error.response){
                                    console.log("Got a response back for " + error.response.data)
                                }
                                if(error.request){
                                    console.log("Rejected " + error.request.data)
                                }
                                
                            })                                                    
                        //console.log(this.searchResults);  
                                          
                    });
                    // add everything to the database that doesn't collide with a name. 
                    // name collisions should be automatically rejected. hopefully. 
                    // but... should we even try to add them? 
                    // who cares let's just try it


                })
                .catch(error => {
                    if (error.response){
                        console.log(error.response.data);
                        console.log(error.response.status);
                        console.log(error.response.headers);
                    } else if (error.request) {
                        console.log(error.request);

                    } else {
                        console.log('Error', error.message);
                    }
                    
                });
        },

    addNewIngredientToRecipe(ingredient) {
      console.log(ingredient.name);
      this.newRecipeIngredients.push(ingredient);
    },
    clearIngredients(){
      this.ingredients = [];
      this.searchResults = [];
    }
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