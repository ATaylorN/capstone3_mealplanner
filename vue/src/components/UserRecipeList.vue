<template>
  <div>
      <Header />
      <table>
          <tr>
              <th>Recipe Name</th>
              <th>Image</th>
          </tr>
          <tr v-for="recipe in recipes" :key="recipe.id" class="recipesList">
              <td class="recipe-name">
                  <router-link :to="{name: 'recipe-details', params: {id: recipe.id}}">
                      {{recipe.name}}
                  </router-link>
              </td>
              <td class = "recipe-image-container">
                <img id = "recipe-image" :src="recipe.image" alt="" class="img-rounded">
              </td>
              <td>
                  <button>
                  <router-link :recipeId="recipe.id" :to="{name: 'recipe-details', params: {id: recipe.id} }">View Full Recipe</router-link>
                  </button>
              </td>
          </tr>
      </table>
  </div>
</template>

<script>

import RecipeService from '../services/RecipeService.js';
import Header from '../components/Header.vue'

export default {
    name: 'user-recipe-list',
    components: {
       Header
    },
    data() {
        return {
            recipes: []
        }
    },

    created() {
        RecipeService.listRecipesByUser().then(response => {        
            if(response.status === 200) {
                this.recipes = response.data;
            }
        }).catch (error => {
            console.error(error);
        })
    }

}
</script>

<style>
#recipe-image{
    max-height: 350px;
    max-width: 350px;
}

.recipesList {
    display: flex;
    flex-direction: column;
    border-style: solid;
    border-color: black;
    background-color: #4a180c;
    color: white;
    padding: 10px;
    text-align: center;
    margin: 50px;
}

</style>