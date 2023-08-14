<template>
  <div>
    <Header />
  </div>
</template>

<script>
import Header from './Header.vue'
import GroceryListService from '../services/GroceryListService.js'
export default {
    name: "grocery-list",
    components: {
        Header
    },
    data() {
        return {
        mealPlanIngredients: '',
        startDate: '',
        endDate: ''
    }
    },
    methods: {
        getMealPlanIngredients(){
            GroceryListService.getMealPlanIngredients(this.startDate, this.endDate).then(response => {
                if (response.status === 200){
                    this.mealPlanIngredients = response.data
                }
            })
            return this.mealPlanIngredients
            }
        }
        }


</script>

<style>
</style>