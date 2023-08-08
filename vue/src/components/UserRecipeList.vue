<template>
  <div>
      <table>
          <tr>
              <th>Recipe Name</th>
              <th>Image</th>
          </tr>
          <tr v-for="recipe in recipes" :key="recipe.userId">
              <td class="recipe-name">
                  <router-link :to=""></router-link>
              </td>    
          </tr>
      </table>
  </div>
</template>

<script>

import RecipeService from '../services/RecipeService.js'

export default {
    name: 'user-recipe-list',
    components: {
       
    },
    data() {
        return {
            recipes: []
        }
    },

    created() {
        RecipeService.listRecipesByUser().then(response => {
            if(response.data === 200) {
                this.recipes = response.data;
            }
        }).catch (error => {
            console.error(error);
        })
    }

}
</script>

<style>

</style>