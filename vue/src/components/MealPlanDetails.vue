<template>
  <div>
      
  </div>
</template>

<script>
import GroceryListService from '../services/GroceryListService.js'
import MealService from '../services/MealService.js'
export default {
    name: "MealPlanDetails",
    data() {
        return {
            ingredients: [],
            recipes: []
        }
    },
    components: {
        
    },
    methods: {
        getIngredients() {
            GroceryListService.getMealPlanIngredients(this.startDate, this.endDate).then(response => {
                if (response.status === 200) {
                    response.data = this.ingredients;
                }
                return this.ingredients
            })
        },
        getMealRecipes(){
            MealService.getMealRecipes(this.id).then(response => {
                if (response.status === 200) {
                    response.data = this.recipes;
                }
            })
        }
    }

}
</script>

<style>

</style>