<template>
  <div class="mpdetails">
      <h1 class="date-title">MEAL PLANS FOR {{ date }} </h1>
        <div v-if="loaded" class="meal-list">
            <div class="meals"  v-for="meal in mealPlans" :key="meal.id">
                <h1 class="mealname">{{meal.mealName}}</h1>
                <div class="recipes" v-for="recipe in meal.recipes" :key="recipe.id">
                  <h2 class="recipename">{{recipe.name}}</h2> 
                  <img :src="recipe.image" class="img-rounded"/>
                  <router-link :to="{ name: 'recipe-details', params: { id: recipe.id } }" tag="button" class="detailsBtn">View Recipe</router-link>
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

<style scoped>
.mpdetails{
    justify-content: center;
    display: grid;
    grid-template-rows: 0.2fr 0.5fr 2fr;
    grid-template-areas: "title"
                        "mealname"
                        "recipe";  
}
.date-title {
    box-shadow: 0px 6px 20px 0px black;
    grid-area: title;
    text-align: center;
    background-color: #4A180C;
    color: white;
    margin-top: 4rem;
    padding: 6rem;
}

.meals{
    grid-area: mealname;
    text-align: center;
    box-shadow: 0px 6px 20px 0px black;
}
.meals h1{
    background-color: #4A180C;
    color: white;
    padding: 2rem;
}
.recipes{
    grid-area: recipe;
    display: flex;
    flex-direction: column;
    background-color: #4A180C;
    color: white;
    align-items: center;
    padding: 2rem;
    box-shadow: 0px 6px 20px 0px black;
}
.recipes img{
    width: 400px;
    height: 400px;
    object-fit: fill;
    margin: 1rem;
}

</style>