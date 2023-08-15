<template>
  <div>
    <Header />
    <div class="recipe-builder">
      
      <div class="ingredients-section">
        <div class="search-input-block">
          <span class="search-text"> {{ searchInputValue }} </span>
          <span class="autocomplete-suggestion">{{autoCompleteSuggestion}}</span>
          <input id="search-input" class="search-field" type="text" placeholder="Search for Ingredients" v-model="searchInputValue" @keyup="suggestSearchTerm()"/>
          <button class="search-button" @click="runSearch()">Search</button>
        </div>

        <div class="add-ingredient-container">
          <!-- <form v-show="filteredIngredientList.length === 0" class="add-ingredient"> -->
          <input class="ingredientSearch" type="text" v-model="newIngredient.name" placeholder="Ingredient Name"/>
          <button id="find-ingredient" @click.prevent="listIngredients()"> Find Ingredient </button>
          <button id="clear-ingredients" @click.prevent="clearIngredients()"> Clear Ingredient List </button>
          <!-- </form> -->
        </div>

        <div class="all-ingredients">
        <draggable class="ingredient-search-results" :list="searchResults" @start="drag=true" @end="drag=false" group="moveIngredient">
          <figure v-for="foundIngredient in searchResults" :key="foundIngredient.index">
            <span @click="addNewIngredientToRecipe(foundIngredient)"> {{ foundIngredient.name }} </span>
            <img @click="addNewIngredientToRecipe(foundIngredient)" :src="foundIngredient.image" :alt="foundIngredient.name"/>
          </figure>
        </draggable>

        <draggable class="ingredients" :list="ingredients" @start="drag=true" @end="drag=false" group="moveIngredient">
          <figure v-for="ingredient in ingredients" :key="ingredient.id">
            <span @click="addNewIngredientToRecipe(ingredient)"> {{ ingredient.name }} </span>
            <img @click="addNewIngredientToRecipe(ingredient)" :src="ingredient.image"/>
          </figure>
        </draggable>
      </div>
      </div>



    <div class="recipe-data-section">
    <form @submit.prevent="addRecipe()" class="add-recipe">
      <input type="text" v-model="newRecipe.name" placeholder="Recipe Name" />
      <input type="text" v-model="newRecipe.image" placeholder="Image Link" />
      <input type="text" v-model="newRecipe.ingredients" placeholder="Ingredients"/>
      <textarea name="instructions" id="prep-instructions" v-model="newRecipe.instructions" placeholder="Prep Instructions"/>
      <button class="saveBtn">Save Recipe</button>
    </form>

    <div class="newRecipeContainer">
      <draggable id="newRecipe" :list="newRecipeIngredients" @start="drag=true" @end="drag=false" group="moveIngredient">
        <figure v-for="newIngredient in newRecipeIngredients" :key="newIngredient.id">
          <span>{{newIngredient.name}}</span>
          <img :src="newIngredient.image" :alt="newIngredient.name">
        </figure>
      </draggable>
    </div>

    </div>
  </div>
  </div>
</template>

<script>
import ingredientService from "@/services/IngredientService.js";
import recipeService from "@/services/RecipeService.js";
import spoonacularService from "@/services/SpoonacularService.js";
import Header from "@/components/Header.vue";
import draggable from 'vuedraggable';

export default {
  components: {
    Header,
    draggable
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
  computed: {
    filteredIngredientList() {
      return this.ingredients.filter((ingredient) => {
        return ingredient.name.includes(this.searchTerm);
      });
    },
  },
  methods: {
    runSearch() {
      this.searchTerm = this.searchInputValue + this.autoCompleteSuggestion;
      this.autoCompleteSuggestion = "";
      this.searchInputValue = "";
      this.searched = true;
    },

    suggestSearchTerm() {
      // when the user types something in the search box, try to auto-complete
      this.potentialSuggestions = this.filteredIngredientList.filter(
        (ingredient) => {
          return (
            ingredient.name.substring(0, this.searchInputValue.length) ===
            this.searchInputValue
          );
        }
      );
      if (
        this.potentialSuggestions.length > 0 &&
        this.searchInputValue !== ""
      ) {
        this.autoCompleteSuggestion =
          this.potentialSuggestions[0].name.substring(
            this.searchInputValue.length
          );
      } else {
        this.autoCompleteSuggestion = "";
        this.potentialSuggestions = [];
      }
    },

    listIngredients() {
      // Unify the buttons/searching functionality.
      this.searchIngredients();
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
          if (error.response) {
            console.log(error.response.status);
            console.log(error.response.data);
            console.log(error.response.headers);
          } else if (error.request) {
            console.log(error.request);
          } else {
            console.log(error.message);
          }
        });
    },
    searchIngredients() {
      spoonacularService
        .searchIngredients(
          this.newIngredient.name,
          process.env.VUE_APP_SPOONACULAR_API_KEY
        )
        .then((response) => {
          response.data.results.forEach((ingredient) => {
            let newIngredient = {
              name: ingredient.name,
              image:
                "https://spoonacular.com/cdn/ingredients_250x250/" +
                ingredient.image,
            };

            this.searchResults.unshift(newIngredient);

            ingredientService
              .addIngredient(newIngredient)
              .then((response) => {
                console.log(response.status);
                this.searchResults[
                  this.searchResults.indexOf(newIngredient)
                ].id = response.data;
              })
              .catch((error) => {
                if (error.response) {
                  console.log("Got a response back for " + error.response.data);
                }
                if (error.request) {
                  console.log("Rejected " + error.request.data);
                }
              });
            //console.log(this.searchResults);
          });
          // add everything to the database that doesn't collide with a name.
          // name collisions should be automatically rejected. hopefully.
          // but... should we even try to add them?
          // who cares let's just try it
        })
        .catch((error) => {
          if (error.response) {
            console.log(error.response.data);
            console.log(error.response.status);
            console.log(error.response.headers);
          } else if (error.request) {
            console.log(error.request);
          } else {
            console.log("Error", error.message);
          }
        });
    },

    addNewIngredientToRecipe(ingredient) {
      if (this.newRecipeIngredients.indexOf(ingredient) == -1) {
        this.newRecipeIngredients.unshift(ingredient);
      }
    },
    clearIngredients() {
      this.ingredients = [];
      this.searchResults = [];
    },
    removeIngredientFromRecipe(ing) {
      // locate the item in the array and remove it.
      this.newRecipeIngredients = this.newRecipeIngredients.filter(
        (ingredient) => {
          return ingredient.id != ing.id;
        }
      );
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

<style scoped>

input {
  color: black;
}

.recipe-builder{
 display: grid;
  grid-template-columns: 1fr 1fr;
  grid-template-areas: "ingredients recipe";
  gap: 20px;
  margin: 2rem;
}

.recipe-builder figure{
  display: flex;
  flex-direction: column-reverse;
  text-align: center;
  margin: auto;
  background-color: white;
  color: black;
  max-width: 100px;
  border-radius: 2px;
  font-weight: 700;
  font-variant-caps:all-small-caps;
}

.ingredients-section{
  margin-top: 1rem;
  margin-left: 4rem;
  grid-area: ingredients;
  align-content: center;
  background-color: #4a180c;
  box-shadow: 0px 6px 20px 0px black;
  color: white;
  padding-top: 4rem;
  display: flex; 
  flex-direction: column; 
  border-radius: 20px;
}

.search-input-block {
  display: flex;
  flex-direction: column;
  margin-right: 4rem;
  margin-left: 4rem;
  margin-bottom: 2rem;
  border-radius: 2rem;
  font-size: 16px;
}

.add-ingredient-container{
  display: flex;
  flex-direction: column;
  margin-right: 4rem;
  margin-left: 4rem;
  margin-bottom: 2rem;
  border-radius: 2rem;
  font-size: 16px;
}
.ingredients {
  display: flex;
  flex-direction: column;
  list-style-type: none;
  gap: 40px;
  padding-bottom: 1rem;
}

.recipe-data-section{
  margin-top: 1rem;
  margin-right: 4rem;
  grid-area: recipe;
  align-content: center;
  background-color: #4a180c;
  color: white;
  box-shadow: 0px 6px 20px 0px black;
  padding-top: 2rem;
  display: flex; 
  flex-direction: column; 
  border-radius: 20px;
}
.recipe-data-section input{
  margin-bottom: 2rem;
  color: black;
}

.add-recipe{
  display: flex;
  flex-direction: column;
  font-size: 16px;
  margin-right: 2rem;
  margin-left: 2rem;
  margin-bottom: 2rem;
  border-radius: 2rem;
}

#prep-instructions{
  height: 20rem;
  color: black;
}
.recipe-builder img{
  max-width: 10rem;
  max-height: 10rem;
  border-radius: 1rem;
  padding: 0.4rem;
}

.recipe-builder button{
  background-color: wheat;
  color: black;
  font-size: 16px;
  font-weight: 400;
}
.recipe-builder button:hover{
  background-color: darkkhaki;
}
.newRecipeContainer{
gap: 20px;
padding-top: 2rem;
}
.all-ingredients {
  max-height: 400px;
  overflow: scroll;
}

</style>