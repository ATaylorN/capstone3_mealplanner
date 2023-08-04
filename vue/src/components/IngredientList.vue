<template>
  <div class="list-container">
      <ul class="ingredient-list">
          <li v-for="ingredient in ingredients" :key="ingredient.id" > {{ingredient.name}} </li>
      </ul>
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
        }
    },
    created(){
        // API call here
        ingredientService.getAllIngredients()
            .then(response => {
                if (response.status === 200){
                    this.ingredients = response.data;
                }
            })
            .catch(error => {
                console.error(error);
            })
    }
}
</script>

<style>

</style>