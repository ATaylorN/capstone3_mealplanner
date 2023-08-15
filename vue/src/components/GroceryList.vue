<template>
  <div id="grocery-list">
      <h2>{{getHeaderText}}</h2>
      <button @click="getMealPlanIngredients">Generate Grocery List</button> 
      <button v-if="listShowing" @click="printGroceryList()">Print Grocery List</button>
      <button @click="$emit('clear'), clear()">Clear</button>      
    <div class="ingredients"> 
        <ol> 
            <li v-for="ingredient in mealPlanIngredients" v-bind:key="ingredient.id">{{ingredient.name}} </li>           
        </ol>  
    </div>    
  </div>
</template>

<script>

import GroceryListService from '../services/GroceryListService.js'

export default {
    name: "grocery-list",
    components: {
        
    },
    props: ['startDate', 'endDate', 'mealPlans'],
    data() {
            return {
                mealPlanIngredients: [],       
        }
    },
    computed: {
      getHeaderText(){
          let output = "";
          if(!this.startDate && !this.endDate){
              output = "Select dates to view meal plans."; 
          } else if (!this.startDate) {
              output = "Plans from today til " + this.endDate; 
          } else {
              output = "Plans from " + this.startDate + " to " + this.endDate; 
          }
          return output; 
      }  
    },    
    methods: {
        getMealPlanIngredients(){
            GroceryListService.getMealPlanIngredients(this.startDate, this.endDate).then(response => {
                if (response.status === 200){
                    this.mealPlanIngredients = response.data
                    if(this.mealPlanIngredients.length > 1){
                        this.listShowing = true; 
                    }
                }
            })
            return this.mealPlanIngredients
            },
        clear(){
            this.mealPlanIngredients = [];
            this.listShowing = false;
            
        },
        printGroceryList(){
            console.log('listo printo');
        },
        },


        }


</script>

<style scoped>

</style>