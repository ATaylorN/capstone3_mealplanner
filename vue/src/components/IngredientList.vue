<template>
  <div class="container">
      <Header id="header"/>
      <div class="search-input">
          <!-- 
              Need to make the input box invisible so that the span text matches up with the input text seamlessly. 
              Hide the input box, make it so that clicking the span puts focus in it, then bind the span's value to the input value.
              Need to draw a border around a fixed-length area to simulate an input box. 
            -->
        <span> Search for Ingredients:</span> 
         <div class=search-input-block>
        <span class="search-text"> {{searchInputValue}} </span><span class="autocomplete-suggestion">{{ autoCompleteSuggestion }}</span>        
        </div><button class="search-button" @click="runSearch()">Search</button>
        <input id="search-input" class="search-field" type="text" v-model="searchInputValue" @keyup="suggestSearchTerm()">
        
        
      </div>
    <main class="list-display">    
      <div v-show="searched && filteredIngredientList.length > 0" class="filteredList">
          <div class="ingredient-card" v-for="ingredient in filteredIngredientList" :key="ingredient.id">
              <p class="ingredient-name"> {{ingredient.name}} </p>
              <img id="ingredient-image" :src="ingredient.image" alt="" class="img-rounded">
          </div>
      </div>
   
    </main>  
    <div class="no-ingredients" v-show="filteredIngredientList.length === 0" > 
    <span>No Ingredients Found.</span>
    <div class="add-ingredient-container">
      <!-- <form v-show="filteredIngredientList.length === 0" class="add-ingredient"> -->
          <input type="text" v-model="newIngredient.name" placeholder="Ingredient Name">
          <button @click.prevent="addIngredient()">Find More Ingredients</button>
      <!-- </form> -->
    </div>
    <ul class="ingredient-search-results">
        <li v-for="foundIngredient in searchResults" :key="foundIngredient.index">
            <span> {{foundIngredient.name}} </span>
            <img :src="foundIngredient.image" :alt="foundIngredient.name" class="img-rounded">            
        </li>
    </ul>          
  </div>
  </div>
</template>

<script>
import ingredientService from '@/services/IngredientService.js';
import spoonacularService from '@/services/SpoonacularService.js';
import Header from '@/components/Header';
export default {
    name: "ingredient-list",
    components: {
        Header
    },
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
            potentialSuggestions: [],
            searched: false,
            searchResults: [],
            newIngredients: [],           
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
        // Call Spoonacular API to retrieve ingredient name, ID, image. 
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
                        this.searchResults.push(newIngredient);
      
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
#search-input{
    color: black;
}

.search-input{
    border-style: solid;
    border-color: black;
    color: white;
    background-color: #4a180c;
    text-align: center;
    padding: 10px;

}
.search-button{
    color: black;
}
.container{
    display: grid;
    grid-template-columns: 1fr 1fr 1fr 1fr;
    grid-template-areas: "header header header header"
                         ". search search ."
                         " display display display display"
                         ". nf nf .";                      
    gap: 20px                                                  
}
#header{
    grid-area: header;
}

.search-button{
    display: inline-block;
}

.autocomplete-suggestion {
    color: grey; 
}

div.search-input{
    grid-area: search;
}
.search-field{
    border: none;
}

.ingredient-card{
    display: flex; 
    flex-direction: column-reverse;
    justify-content: flex-start;    
    align-items: center;
    margin: 5rem;
}
#ingredient-image{
    max-height: 200px; 
    max-width: 200px;        
}

.list-display{
    grid-area: display;
}

.filteredList{
    display: flex;     
    flex-wrap: wrap;
    flex-direction: row;
    grid-area: display; 
    border-style: solid;
    border-color: black;
    background-color: #4a180c;
    color: white;
    margin-top: 50px;
}
.no-ingredients{
    grid-area: nf;
}


</style>