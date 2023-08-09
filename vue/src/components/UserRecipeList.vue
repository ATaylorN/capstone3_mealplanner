<template>
  <div>
      <Header />
      <table>
          <tr>
              <th>Recipe Name</th>
              <th>Image</th>
          </tr>
          <tr v-for="recipe in recipes" :key="recipe.id">
              <td class="recipe-name">
                  <router-link :to="{name: 'user-recipe-details'}">
                      {{recipe.name}}
                  </router-link>
              </td>
              <td class = "recipe-image-container">
                <img class = "recipe-image" :src="recipe.image" alt="">
              </td>
              <td>
                  <router-link :to="{name: 'recipe-details', params: {id: recipe.id}}">View Full Recipe</router-link>
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
.recipe-image{
    max-height: 350px;
    max-width: 350px;
}

</style>