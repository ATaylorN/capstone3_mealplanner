<template>
  <div class="list-container">
      <div class="searchInput">                    
        <input type="text" placeholder="Search for Ingredients" v-model="searchInputValue" @keyup="suggestSearchTerm()"> <span>{{ autoCompleteSuggestion }} </span>
      </div>
      <button @click="runSearch()">Search</button>
      <table class="filteredList">
          <th>Image</th> <th>Ingredient Name</th> 
            <tr  v-for="ingredient in filteredIngredientList" :key="ingredient.id" >
              <td> {{ingredient.name}} </td>
              <td> <img :src="ingredient.image" alt=""></td>
          </tr>
      </table>
    <div class="add-ingredient-container">
      <form class="add-ingredient" @submit="addIngredient()">
          <input type="text" v-model="newIngredient.name" placeholder="Ingredient Name">
          <button>Add Ingredient</button>
      </form>
  </div>

  </div>
</template>

<script>
import ingredientService from '@/services/IngredientService.js';
export default {
    name: "ingredient-list",
    data(){
        return {
            // API call results here            
            ingredients: [],
            searchInputValue: "",
            searchTerm: "",
            autoCompleteSuggestion: "",
            newIngredient: {
                name: "",
            },
            potentialSuggestions: []
           
        }
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
            ingredientService.addIngredient(this.newIngredient)
                .catch(error => {
                    console.error(error);
                })
        }
    },
    created(){
        // API call here
        ingredientService.getAllIngredients()
            .then(response => {
                if(response.status === 200){
                    this.ingredients = response.data;
                }
            })
            .catch(error => {
                console.error(error);
            })
    },

    
}
</script>

<style>

</style>