<template>
  <div class="recipe-updater">
    <Header class="hdr" />
    <section class="ingredient-data">
      <!-- list of ingredients to pick goes here -->
      <draggable
        class="ingredientsList"
        :list="browseIngredientList"
        @start="drag = true"
        @end="drag = false"
        group="newIngredients"
      >
        <figure class="imagecard"
          v-for="ingredient in browseIngredientList"
          :key="ingredient.id"
          @click="addNewIngredientToRecipe(ingredient)"
        >
          <span> {{ ingredient.name }} </span>
          <img :src="ingredient.image" alt="" />
        </figure>
      </draggable>
    </section>

    <section class="recipe-data">
      <form v-on:submit.prevent class="recipe-inputs" id="updateform">
        <div class="field">
          <label for="name">Recipe Name: </label>
          <input name="name" type="text" v-model="name" />
        </div>

        <div class="field">
          <label for="image">Recipe Image Link: </label>
          <input name="image" type="text" v-model="imageStr" />
        </div>

        <div class="field">
          <label for="image">Upload an image: </label>
          <input
            name="imagefile"
            @change="preUpload()"
            type="file"
            ref="image"
          />
        </div>

        <div class="field">
          <label for="instructions">Preparation Instructions: </label>
          <input
            id="prep-instructions"
            name="instructions"
            type="text"
            v-model="instructions"
          />
        </div>

        <div class="actions">
          <button type="submit" v-on:click="updateRecipe()">Update</button>
        </div>
      </form>

      <div class="recipe-ingredients">
        <span class="yours">Your Ingredients</span>
        <draggable
          class="ingredients"
          :list="ingredientList"
          @start="drag = true"
          @end="drag = false"
          group="newIngredients"
        >
          <figure class="imagecard"
            v-for="ingredient in ingredientList"
            :key="ingredient.id"
            @click="removeIngredientFromRecipe(ingredient)"
          >
            <span> {{ ingredient.name }} </span>
            <img :src="ingredient.image" alt="" />
          </figure>
        </draggable>
      </div>
    </section>
  </div>
</template>

<script>
import RecipeService from "../services/RecipeService";
import IngredientService from '@/services/IngredientService';
import Header from '@/components/Header.vue';
import draggable from 'vuedraggable';
import {getStorage, ref, getDownloadURL, uploadBytes} from 'firebase/storage';

export default {
  name: "updateRecipe",
  components: {
    Header,
    draggable
  },
  data() {
    return {
      name: "",
      imageStr: "",
      ingredientsStr: "",
      instructions: "",
      ingredientList: [],
      browseIngredientList: [],
    };
  },
  methods: {
    preUpload(){
      this.image = this.$refs.image.files[0];
      const storage = getStorage();
      const imgRef = ref(storage, '/recipeImgs/' + this.image.name)
      this.uploadRef = imgRef; 
    },
    updateRecipe() {      
      if (this.$refs.image.files[0]){
        uploadBytes(this.uploadRef, this.image)
              .then(response => {
                console.log('great success');
                console.log(response);
                getDownloadURL(this.uploadRef)
                  .then(url => {
                    const recipe = {
                    name: this.name,
                    image: url,
                    ingredients: this.ingredientsStr,
                    instructions: this.instructions
                    };
              
              RecipeService.updateRecipe(this.$route.params.id, recipe)
                .then((response) => {
                  if (response.status === 200) {
                  IngredientService.updateRecipeIngredients(this.ingredientList, this.$route.params.id)
                    .then(response => {
                    if (response.status === 200){
                      console.log('yay it worked')
                    }
                });
          this.$router.push({ path: `/recipes/${this.$route.params.id}` });
        }
      });
      });
      });

    
      } else {
        const recipe = {
                    name: this.name,
                    image: this.imageStr,
                    ingredients: this.ingredientsStr,
                    instructions: this.instructions
                    };
              
              RecipeService.updateRecipe(this.$route.params.id, recipe)
                .then((response) => {
                  if (response.status === 200) {
                  IngredientService.updateRecipeIngredients(this.ingredientList, this.$route.params.id)
                    .then(response => {
                    if (response.status === 200){
                      console.log('yay it worked')
                    }
                });
          this.$router.push({ path: `/recipes/${this.$route.params.id}` });
        }
      });  
      }          
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
        this.imageStr = response.data.image;
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

<style scoped>

.recipe-updater img {
  height: 150px;
  width: 150px;
  object-fit: fill;
  border-radius: 4px;
  padding: 0.5rem;
  border: 2 solid white;
}
.recipe-updater {
  display: grid;
  grid-template-rows: 0.5fr 3fr;
  grid-template-columns: 1fr 2fr;
  grid-template-areas:
    "header header"
    "ingredients recipe";
  gap: 4rem;
  font-size: 16px;
  margin-bottom: 2rem;
}
.hdr {
  grid-area: header;
}
section.ingredient-data {
  border-radius: 20px;
  grid-area: ingredients;
  background-color: #4a180c;
  color: white;
  padding: 4rem;
  margin-left: 4rem;
  max-height: 700px;
  overflow: hidden;
}
section.ingredient-data:hover {
  overflow: auto;
}
.recipe-data {
  margin-right: 4rem;
  border-radius: 20px;
  grid-area: recipe;
  display: grid;
  grid-template-columns: 1fr 1fr 1fr;
  grid-template-rows: 1fr 1fr;
  grid-template-areas:
    "info info info"
    "yours yours yours";
  padding: 4rem 8rem 0rem 8rem;
  background-color: #4a180c;
  color: white;
  gap: 2rem;
  max-height: 700px;
}
.recipe-data input {
  color: black;
  margin-bottom: 1rem;
}
.recipe-data form {
  grid-area: info;
  color: white;
}
.recipe-data button {
  color: black;
  background-color: wheat;
}
.recipe-data button:hover {
  background-color: darkkhaki;
}
.recipe-inputs div {
  display: flex;
  flex-direction: column;
  color: white;
}
.recipe-inputs input {
  height: 4rem;
}
.recipe-ingredients {
  grid-area: yours;
  border: 2px solid white;
  padding: 1rem;
  border-radius: 10px;
  text-align: center;
  margin-bottom: 2rem;
}
.recipe-ingredients span{
  color:white;
}
.ingredients {
  display: flex;
  flex-wrap: wrap;
  max-height: 300px;
  overflow: hidden;
}
.ingredients:hover {
  overflow: auto;
}
.imagecard{
  display: flex;
  flex-direction: column-reverse;
  text-align: center;
  margin-bottom: 2rem;
  margin-left: auto;
  margin-right: auto;
  margin-top: 2rem;
  background-color: white;
  color: black;
  width: 15rem;
  border-radius: 2px;
  font-weight: 900;
  font-variant-caps: all-small-caps;
}
</style>