<template>
  <div class="meal-planner">
    <section class="calendar-container">
      <ul class="mealplancalendar">
        <li class="calendar-square" v-for="calendarSlot in dateSlots" :key="calendarSlot.id">
          <draggable :list="calendarSlot.mealPlans" group="mealplan" draggable=".meal">          
            <span @click="setDates(calendarSlot.date)" slot="header"> {{ calendarSlot.displayDate }} <br></span>
            <span :meal="mealPlan.mealName" class="meal" v-for="(mealPlan, index) in calendarSlot.mealPlans" :key="index"> 
                {{ mealPlan.mealName }}
            </span>
          </draggable>
        </li>
      </ul>
    </section>


      <section class="meal-list">
          <div class="saveBtn" > Drag Meals to Calendar </div>
          
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

      <GroceryList id='groceries' :startDate="startDate" :endDate="endDate" />
  </div>

</template>

<script>
import MealService from "@/services/MealService.js";
import draggable from "vuedraggable";
import moment from "moment";
import GroceryList from '@/components/GroceryList';
export default {
  name: "MealPlanner",
  components: {
    draggable,
    GroceryList
  },

  data() {
    return {
      dateSlots: [],
      mealsToDrag: [],
      mealPlans: [],
      trashmode: [],
      startDate: "",
      endDate: "",
    };
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
                this.mealPlans.forEach(mp => {
                    //console.log("mp: " + mp.dateToCook)
                    //console.log("cal: " + calendarSlot.date)
                    if (mp.dateToCook == calendarSlot.date){
                        let mealNameCheck = this.mealsToDrag.find(meal =>{                            
                            return meal.mealId === mp.mealId;
                        });
                        
                        mp.mealName = mealNameCheck.mealName
                        calendarSlot.mealPlans.push(mp)
                    }
                })
                this.dateSlots.push(calendarSlot)
            }
        },
        setDates(date){
          // if both dates aren't populated, set the start date first. 
          if(!this.startDate && !this.endDate){
            this.startDate = date;
          } else if(date > this.startDate && date < this.endDate){
            // shrink the window
            this.start = date; 
          } else if (date < this.startDate && date < this.endDate){
            //grow the window toward start date. 
            this.startDate = date; 
          } else if (date > this.startDate && date > this.endDate){
            // grow the window toward end date.
            this.endDate = date; 
          }
        },
        readCalendar(){
            let mealPlans = [];
            this.dateSlots.forEach(slot =>{
                if (slot.mealPlans.length > 0){                                        
                    slot.mealPlans.forEach(mealPlan => {
                      // clone the object instead of passing it as a reference? 
                      let planToSend = {
                        dateToCook: slot.date,
                        mealId: mealPlan.mealId,
                        userId: mealPlan.userId                                                
                      }
                      if(mealPlan.id !== undefined){
                        planToSend.id = mealPlan.id;
                      }
                      if(mealPlan.mealType !== undefined){
                        planToSend.mealType = mealPlan.mealType;
                      }
                      mealPlans.push(planToSend);
                    });
                }                
            });
            console.log(mealPlans);
            mealPlans.forEach(mealPlan => {
                MealService.addMealPlan(mealPlan)
                    .then(response => {
                        console.log(response.status);
                        this.trashmode.forEach(mealPlan => {
                          if(mealPlan.id){                           
                            MealService.deleteMealPlans(mealPlan.id)
                              .then( response => {
                                console.log(response.status)
                                })
                              .catch(err => {console.log(err)})
                          }
                        })
                        this.trashmode = [];
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
    },
    
    created(){
        // get meals.
        MealService.getAllUserMeals()
            .then(response => {
                this.mealsToDrag = response.data;
                MealService.getMealPlans()
                    .then(response => {
                        this.mealPlans = response.data; 
                        console.log(this.mealPlans)
                        this.buildCalendar();
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
    },
    beforeDestroy(){
      this.readCalendar();
    }
}
</script>

<style scoped>
/* 
Set the calendar to a grid containing five rows, seven columns.
*/
.meal-planner {
  display: grid;
  grid-template-columns: 1fr 3fr 1fr;
  grid-template-rows: 2fr 1fr;
  row-gap: 5rem;
  column-gap: 5rem;
  grid-template-areas:
    "mid mid groceries"
    "lowerMid trash .";
}

section.calendar-container {
  grid-area: mid;
  background-color: wheat;
  color: black;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 2rem;
}



#groceries{
  grid-area: groceries; 
}

.mealplancalendar {
  list-style: none;
  display: grid;
  grid-template-rows: 11rem 11rem 11rem 11rem;
  grid-template-columns: 11rem 11rem 11rem 11rem 11rem 11rem 11rem;
  gap: 6rem;
  
}

.calendar-square {
  height: 12rem;
  width: 11rem;
  border: 1px solid black;
  border-radius: 3px;
  background-color: tan;
}


.mealplancalendar li span {
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

#groceries{
  background-color: wheat;
}

</style>