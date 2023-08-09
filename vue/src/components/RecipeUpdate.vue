<template>
<div class="recipe-updater">
  <Header id="hdr" />
    <section class="ingredient-data">
      <!-- list of ingredients to pick goes here -->
      <ul>
        <li v-for="ingredient in browseIngredientList" :key="ingredient.id" @click="addNewIngredientToRecipe(ingredient)">
          <span> {{ingredient.name}} </span>
        </li>
      </ul>
    </section>
    <section class="recipe-data">
    <form v-on:submit.prevent class="recipe-inputs">
      <div class="field">
        <label for="name">Recipe Name: </label>
        <input name="name" type="text" v-model="name" />
      </div>
      <div class="field">
        <label for="image">Recipe Image Link: </label>
        <input name="image" type="text" v-model="image" />
      </div>
      <div class="field">
        <label for="instructions">Preparation Instructions: </label>
        <input name="instructions" type="text" v-model="instructions" />
      </div>
      <div class="actions">
        <button type="submit" v-on:click="updateRecipe()">Update</button>
      </div>
    </form>
    <div class="recipe-ingredients">
    <span >List o' Ingredients</span>
    <ul class="ingredients">
      <li v-for="ingredient in ingredientList" :key="ingredient.id" @click="removeIngredientFromRecipe(ingredient)">
        <span> {{ingredient.name}} </span>      
      </li>
    </ul>
    </div>
      </section>
  </div>
</template>

<script>
import RecipeService from "../services/RecipeService";
import IngredientService from '@/services/IngredientService';
import Header from '@/components/Header.vue';
export default {
  name: "updateRecipe",
  components: {
    Header
  },
  data() {
    return {
      name: "",
      image: "",
      ingredientsStr: "",
      instructions: "",
      ingredientList: [],
      browseIngredientList: [],
    };
  },
  methods: {
    updateRecipe() {
      console.log('update recipe got called.')
      const recipe = {
        name: this.name,
        image: this.image,
        ingredients: this.ingredientsStr,
        instructions: this.instructions,
      };
      RecipeService.updateRecipe(this.$route.params.id, recipe)
      .then((response) => {
        if (response.status === 200) {
          IngredientService.updateRecipeIngredients(this.ingredientList, this.$route.params.id)
            .then(response => {
              if (response.status === 200){
                console.log('yay it worked')
              }
            })
          this.$router.push({ path: `/recipes/${this.$route.params.id}` });
        }
      });
    },
    removeIngredientFromRecipe(ing){
      // locate the item in the array and remove it. 
      this.ingredientList = this.ingredientList.filter(ingredient => {
        return ingredient.id != ing.id; 
      })
    },
      addNewIngredientToRecipe(ingredient) {
      if (this.ingredientList.indexOf(ingredient) == -1){
        this.ingredientList.push(ingredient);
      }
      
    },

  },
  created(){
    RecipeService.getRecipeById(this.$route.params.id)
      .then(response =>{
        console.log(response.data);
        this.name = response.data.name;
        this.instructions = response.data.instructions;
        this.image = response.data.image;
        this.ingredientsStr = response.data.ingredients;
        // get ingredient list. 
        RecipeService.getRecipeIngredients(this.$route.params.id)
          .then(response => {
            this.ingredientList = response.data.ingredientList;
          })
          .catch(error => {
            console.error(error);
          });
          
      });
      IngredientService.getAllIngredients()   
        .then(response => {
          this.browseIngredientList = response.data;
        });
    // once we've got all the data on the page, we can make edits and return the object back
    // we'll have to add and/or delete rows from the recipe_ingredients table if modifications are made. 
  }
};
</script>

<style>
.recipe-updater{
  display: grid;
  grid-template-columns: 1fr 1fr;
  grid-template-rows: .2fr .1fr .7fr;
  grid-template-areas: "header header"
                        ". . "
                       "ingredients recipe"; 
  gap: 10px; 

}
#hdr{
  grid-area: header; 
}

section {
  display: flex;
  flex-direction: column;
  border-radius: 20px;
  color: white; 
}
button {
  color: black;
}
form {
  padding: 20px
}

section.recipe-data{
  grid-area: recipe;
  display: grid;
  grid-template-columns: 1fr 1fr;
  grid-template-areas: "form list";
  background-color:#4a180c;
}
section.recipe-data form.recipe-inputs{
  grid-area: form
}


section.recipe-data div.recipe-ingredients{
  grid-area: list;
  display: flex; 
  flex-direction: column; 
  justify-content: flex-start;
  

}
.recipe-ingredients ul {
  list-style: none;
  padding: 0px 0px 5px 0px;
  margin: 0px;  
}
.recipe-ingredients ul li {
  margin: 5px;
}



section.ingredient-data{
  grid-area: ingredients;
  background-color:#4a180c;
}

</style>