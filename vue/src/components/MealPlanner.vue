<template>
  <div class="meal-planner">
    <section class="calendar-container">
      <ul>
        <li
          class="calendar-square"
          v-for="calendarSlot in dateSlots"
          :key="calendarSlot.id"
        >
          <draggable
            :list="calendarSlot.mealPlans"
            group="mealplan"
            draggable=".meal"
            @change="setDate($event, calendarSlot.date)"
          >
            <span slot="header"> {{ calendarSlot.displayDate }} </span>
            <span
              class="meal"
              v-for="mealPlan in calendarSlot.mealPlans"
              :key="mealPlan.mealId"
            >
              {{ mealPlan.mealName }}</span
            >
          </draggable>
        </li>
      </ul>
    </section>

      <section class="meal-list">
          <div @click="readCalendar()"> save save go go </div>
          
            <draggable :group="{name: 'mealplan', pull: 'clone', put: false}" :list="mealsToDrag" @start="drag=true" @end="drag=false"> 
              <div class="meal-card" v-for="meal in mealsToDrag" :key="meal.id">
                  <span :mealId="meal.mealId" > {{meal.mealName}} </span>
                  <span></span>
              </div>
            </draggable>
            

           
          
    </section>
    <draggable class="trash" group="mealplan" :list="trashmode">
      <div>trash</div>
    </draggable>
  </div>
</template>

<script>
import MealService from "@/services/MealService.js";
import draggable from "vuedraggable";
import moment from "moment";
export default {
  name: "MealPlanner",
  components: {
    draggable,
  },
  // when I drag a meal to a date, create a meal plan with the date of the slot that was given.
  // if I drag a meal out of the calendar, destroy the corresponding meal plan.
  // if I drag a meal from one date slot to another, change the date instead of creating a new entry.

  data() {
    return {
      dateSlots: [],
      mealsToDrag: [],
      mealPlans: [],
      trashmode: [],
    };
  },
  computed: {
    getMealPlanForDate() {
      let string = "";
      let plansForDate = this.mealPlans.filter((mealPlan) => {
        console.log(mealPlan);
        return mealPlan.dateToCook == this.calendarSlot.date;
      });
      plansForDate.forEach((plan) => {
        string += plan.mealName + "\n";
      });
      console.log(string);
      return string;
    },
  },
    methods: {
        buildCalendar(){
            let d = moment();
            for (let i = 0; i < 28; i++ ) {
                let calendarSlot = {
                    id: i, 
                    date: d.add(1, 'days').format('YYYY-MM-DD'),
                    displayDate: d.format('M/D'),
                    mealPlans: []
                }
                this.dateSlots.push(calendarSlot)
            }
        },
        readCalendar(){
            let mealPlans = [];
            this.dateSlots.forEach(slot =>{
                if (slot.mealPlans.length > 0){                    
                    slot.mealPlans.forEach(mealPlan => { 
                        mealPlans.push( {plannedMealId: Number(mealPlan.mealId), dateToCook: mealPlan.dateToCook}); 
                    });
                }
            console.log(mealPlans);
            });
            mealPlans.forEach(mealPlan => {
                MealService.addMealPlan(mealPlan)
                    .then(response => {
                        console.log(response.status)
                    })
                    .catch(error => {
                        if(error.response){
                        console.log(error.response)
                        }
                        if(error.request){
                        console.log(error.request)
                        }
                    });
            })
        },

        setDate(event, date){                                                         
                // let mealDate = event.to.firstChild.getAttribute("date");
                // let mealPlanToEdit = event.item.firstChild.getAttribute("mealId");
                // console.log(event);
                // console.log(date)
                // console.log(event.added.element)
                if(event.added){
                event.added.element.dateToCook = date; 
                } else if (event.moved){
                    event.moved.element.dateToCook = date; 
                }
                
                console.log(this.mealPlans);
            },

    },
    
    created(){
        // get meals.
        MealService.getAllUserMeals()
            .then(response => {
                this.mealsToDrag = response.data;                 
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
ul {
  list-style: none;
  display: grid;
  grid-template-rows: 11rem 11rem 11rem 11rem;
  grid-template-columns: 11rem 11rem 11rem 11rem 11rem 11rem 11rem;
  gap: 6rem;
  padding: 0;
}

.calendar-square {
  height: 12rem;
  width: 11rem;
  border: 1px solid black;
  border-radius: 3px;
  background-color: tan;
}

.meal-planner {
  display: grid;
  grid-template-columns: 1fr 3fr 1fr;
  grid-template-rows: 1fr 1fr;
  row-gap: 5rem;
  grid-template-areas:
    ". mid ."
    ". lowerMid trash";
}

li span {
  margin: 2px;
}

.trash {
  grid-area: trash;
  color: black;
  height: 5rem;
  width: 5rem;
  border: 1px solid black;
  background-color: rgb(42, 165, 149);
  border-radius: 3px;
}

.meal-list {
  grid-area: lowerMid;
  background-color: wheat;
  height: 40vh;
}

.meal-card {
  color: black;
  padding-left: 3rem;
}

</style>