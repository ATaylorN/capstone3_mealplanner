<template>
  <div>
      <h1>MEAL PLANS FOR {{ date }} </h1>
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
            mealPlans: []
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
                    this.mealPlans.forEach(mp => {
                        MealService.getMealRecipes(mp.mealId)   
                            .then( res => {
                                mp.recipes = res.data
                    })                             
            });
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
            this.getRelevantMealPlans();

            console.log(this.recipes);
        

        }
    }
    


</script>

<style>

</style>