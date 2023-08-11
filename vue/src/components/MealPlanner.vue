<template>
  <div>
      <section class="calendar-container">
          <ul class="calendar-square" >
              <li v-for="(calendarSlot, index) in dateSlots" :key="index">
              <span class="date"> {{calendarSlot.date}} </span>
              </li>
          </ul>
      </section>
      <draggable> </draggable>
      <!-- <section class="user-meals-container">
          <draggable class="meal-item">
            <figure class="recipe-card" v-for="meal in meals" :key="meal.id">
              <img class="recipe-card-image" :src="meal.image" :alt="meal.mealName">
              <span class="recipe-card-title">{{meal.mealName}}</span>
            </figure>
          </draggable>
      </section> -->

  </div>
</template>

<script>
import MealService from '@/services/MealService.js';
import draggable from 'vuedraggable';
import moment from 'moment';
export default {
    name: "MealPlanner",
    components: {
        draggable
    },
    data(){
        return {
            dateSlots: [],
            meals: []
        }
    },
    methods: {
        buildCalendar(){
            let d = moment();
            for (let i = 0; i < 35; i++ ) {
                let calendarSlot = {
                    date: d.add(1, 'days').format('MM/D'),
                    meals: []          
                }
                this.dateSlots.push(calendarSlot)
            }
        }        
    },
    
    created(){
        // get meals.
        MealService.getAllUserMeals()
            .then(response => {
                this.meals = response.data;                 
            })
            .catch(error => {
                console.log(error.message);
                if(error.response){
                  console.log(error.response.data);                  
                }
                if (error.request){
                  console.log(error.request)
                }
            })
        this.buildCalendar(); 
    }
}
</script>

<style>
/* 
Set the calendar to a grid containing five rows, seven columns.
*/
section {
    background-color: wheat;
    color: black;
}
ul{
    list-style: none; 
}
li {

}

</style>