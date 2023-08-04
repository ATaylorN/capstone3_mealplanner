<template>
  <div class="add-ingredient-container">
      <form class="add-ingredient" @submit="addIngredient()">
          <input type="text" v-model="newIngredient.name" placeholder="Ingredient Name">
          <button>Add Ingredient</button>
      </form>
  </div>
</template>

<script>
import ingredientService from '@/services/IngredientService.js';
export default {
    name: "add-ingredient-form",
    data(){
        return {
            newIngredient: {
                name: "",
            }
        }
    },
    methods:{
        addIngredient(){
            ingredientService.addIngredient(this.newIngredient)
                .catch(error => {
                    console.error(error);
                })
        }
    }
}
</script>

<style>

</style>