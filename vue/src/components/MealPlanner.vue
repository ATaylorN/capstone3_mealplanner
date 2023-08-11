<template>
  <div class="meal-planner">
      <section class="calendar-container">
          
            <ul>             
                <li class="calendar-square" v-for="(calendarSlot, index) in dateSlots" :key="index">
                    <span class="date"> {{calendarSlot.displayDate}} </span>
                    <draggable :group="mealplan" :list="dateSlots" >
                        <div class="meal-plan-card-holder">         
                            <span> meals go here once we got em</span>                   
                        </div>                        
                    </draggable>
                </li>
            </ul>
          
      </section>

      <section class="meal-list">
          <ul class="meals">
            <draggable :group="{name: 'mealplan', pull: 'clone' }"> 
              <li class="meal-card" v-for="meal in meals" :key="meal.id">
                  <span> meal name here</span>
              </li>
            </draggable>
          </ul>
      </section>
    

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
    // when I drag a meal to a date, create a meal plan with the date of the slot that was given. 
    // if I drag a meal out of the calendar, destroy the corresponding meal plan.
    // if I drag a meal from one date slot to another, change the date instead of creating a new entry. 


    data(){
        return {
            dateSlots: [],
            meals: [],
            mealPlans: [],
        }
    },
    methods: {
        buildCalendar(){
            let d = moment();
            for (let i = 0; i < 28; i++ ) {
                let calendarSlot = {
                    date: d.add(1, 'days').format('YYYY/MM/DD'),
                    displayDate: d.add(1, 'days').format('M/D'),
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

<style scoped>


/* 
Set the calendar to a grid containing five rows, seven columns.
*/
section.calendar-container {
    grid-area: mid;
    background-color: wheat;
    color: black;
    /* display: flex;
    justify-content: center;
    align-items: center; */
    padding: 2rem;
}
ul{
    list-style: none; 
    display: grid;
    grid-template-rows: 11rem 11rem 11rem 11rem;
    grid-template-columns: 11rem 11rem 11rem 11rem 11rem 11rem 11rem;
    gap: 6rem;
    padding: 0;
}

ul li.calendar-square:first-child{
    background-color: lightblue;
}

li {
    height: 15rem;
    width: 15rem; 
    border: 1px solid black;
}
.meal-planner{
    display: grid; 
    grid-template-columns: 1fr 3fr 1fr;
    grid-template-rows: 1fr 1fr;
    row-gap: 5rem;
    grid-template-areas: ". mid ."
                         ". lowerMid ."
}

li span{
margin: 2px;

}
.meal-list{
    grid-area: lowerMid;
    background-color: wheat;
}

</style>