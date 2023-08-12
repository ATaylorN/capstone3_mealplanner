<template>
  <div>
    <Header />
    <div class="recipe-builder">
      <!-- 
          list of ingredients on one side
          recipe form on the other, with element to display added ingredients
    -->
      <div class="ingredients-section">
        <div class="search-input-block">
          <span class="search-text"> {{ searchInputValue }} </span
          ><span class="autocomplete-suggestion">{{
            autoCompleteSuggestion
          }}</span>
          <input
            id="search-input"
            class="search-field"
            type="text"
            placeholder="Search for Ingredients"
            v-model="searchInputValue"
            @keyup="suggestSearchTerm()"
          />
          <button class="search-button" @click="runSearch()">Search</button>
        </div>

        <div class="add-ingredient-container">
          <!-- <form v-show="filteredIngredientList.length === 0" class="add-ingredient"> -->
          <input
            class="ingredientSearch"
            type="text"
            v-model="newIngredient.name"
            placeholder="Ingredient Name"
          />
          <button @click.prevent="listIngredients()">
            Find More Ingredients
          </button>
          <button @click.prevent="clearIngredients()">
            Clear Ingredient List
          </button>
          <!-- </form> -->
        </div>

        <ul class="ingredient-search-results">
          <li
            v-for="foundIngredient in searchResults"
            :key="foundIngredient.index"
          >
            <span @click="addNewIngredientToRecipe(foundIngredient)">
              {{ foundIngredient.name }}
            </span>
            <img
              @click="addNewIngredientToRecipe(foundIngredient)"
              :src="foundIngredient.image"
              :alt="foundIngredient.name"
              class="img-rounded"
            />
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
              class="img-rounded"
            />
          </li>
        </ul>
      </div>
    <div class="recipe-data-section">
    <form @submit.prevent="addRecipe()" class="add-recipe">
      <input type="text" v-model="newRecipe.name" placeholder="Recipe Name" />
      <input type="text" v-model="newRecipe.image" placeholder="Image Link" />
      <input type="text" v-model="newRecipe.ingredients" placeholder="Ingredients"/>
      <textarea name="instructions" id="prep-instructions" v-model="newRecipe.instructions" placeholder="Prep Instructions"/>
      <button class="saveBtn">Save Recipe</button>
    </form>
          <ul class="new-recipe-ingredient-list">
        <li v-for="newIngredient in newRecipeIngredients" :key="newIngredient.id" class="new-recipe-ingredient">
          <span> {{ newIngredient.name }}</span>
          <button @click="removeIngredientFromRecipe(newIngredient)"> Remove </button>
        </li>
      </ul>
    </div>
  </div>
  </div>
</template>

<script>
import ingredientService from "@/services/IngredientService.js";
import recipeService from "@/services/RecipeService.js";
import spoonacularService from "@/services/SpoonacularService.js";
import Header from "@/components/Header.vue";

export default {
  components: {
    Header,
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

<style>
div.recipe-builder {
  display: grid;
  grid-template-columns: 1fr 1fr;
  grid-template-areas: "ingredients recipe";
  gap: 20px;
}

.saveBtn {
  color: black;
  margin-top: 1rem;
  margin-left: 15rem;
  margin-right: 15rem;
}

.new-recipe-ingredient {
  color: black;
}
.add-recipe {
  color: black;
  display: grid;
  margin: 2rem;
  justify-items: center;
}

.add-recipe > input, textarea{
  margin: .5rem;
  border-radius: 5px;
}

.ingredients-section{
  margin-top: 50px;
  margin-left: 10px;
  grid-area: ingredients;
  align-content: center;
  border-style: solid;
  border-color: black;
  background-color: #4a180c;
  color: white;
}

.add-ingredient-container {
  color: black;
}
.search-field {
  color: black;
  font-size: 16px;
}

.ingredientSearch {
  color: black;
  font-size: 16px;
}
.search-button {
  color: black;
}
.ingredients {
  display: flex;
  flex-direction: column;
  list-style-type: none;
  gap: 40px;
}

#prep-instructions{
  height: 30rem;
  width: 30rem;

}

.recipe-data-section{
  margin-top: 50px;
  margin-right: 10px;
  align-content: center;
  border-style: solid;
  border-color: black;
  background-color: #4a180c;
  color: white;
  grid-area: recipe;
}
.new-recipe-ingredient-list {
  list-style: none;
}

.new-recipe-ingredient {
  background: wheat;
}

.recipe-builder img{
  max-width: 10rem;
  max-height: 10rem;
}
</style>