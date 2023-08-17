<template>
  <div>
      <h1>MEAL PLANS FOR {{ date }} </h1>
        <div v-if="loaded" class="meal-list">
            <div class="meals"  v-for="meal in mealPlans" :key="meal.id">
                <h1>{{meal.mealName}}</h1>
                <div v-for="recipe in meal.recipes" :key="recipe.id">
                  <h2>{{recipe.name}}</h2> 
                  <img :src="recipe.image"/>
                </div>
            </div>
        </div>

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
            date: this.$route.params.date,
            mealPlans: [],
            meals: [],
            loaded: false,
            loadCount: 0,
            recordsToLoad: 0, 

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
        getRelevantMealPlans(){
            GroceryListService.getMealPlansForDateRange(this.date, this.date)
                .then(response => {                    
                    this.mealPlans = response.data;
                    console.log(this.mealPlans);
                    this.recordsToLoad  = this.mealPlans.length; 
                    this.mealPlans.forEach(mp => {
                        MealService.getMealRecipes(mp.mealId)   
                            .then( res => {
                                mp.recipes = res.data;
                            MealService.getMealById(mp.mealId)
                                .then(res => {
                                    mp.mealName = res.data.mealName;                                    
                                }).finally(() => {
                                    this.loadCount += 1; 
                                    if(this.loadCount == this.recordsToLoad){
                                        this.loaded = true; 
                                    }
                            })                                                                                                            
                        })
                    })                                                                 
                })                
                .catch(err => {
                    if(err.response){
                        console.log(err.response.data)
                        console.log(err.response.status)
                    }
                    if(err.request){
                        console.log(err.request)
                    }
                    console.log(err.message)
                })             
            }            
        },
        created(){
            this.getRelevantMealPlans()

        }
    }
    


</script>

<style>
div {
    color: black
}
</style>