<template>
  <div>
    <div class="ingredients" v-for="ingredient in mealPlanIngredients" v-bind:key="ingredient.id">{{ingredient.name}}</div>
  </div>
</template>

<script>

import GroceryListService from '../services/GroceryListService.js'
export default {
    name: "grocery-list",
    components: {
        
    },
    props: ['startDate', 'endDate'],
    data() {
        return {
        mealPlanIngredients: [],
        
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