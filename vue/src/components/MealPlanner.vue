<template>
  <div class="meal-planner">

    <div class="menuTitle">
      <h1>My Meal Plan</h1>
    </div>

    <section class="calendar-container">      
      <ul class="mealplancalendar">
        <li class="calendar-square" v-for="calendarSlot in dateSlots" :key="calendarSlot.id">
          
          <draggable :list="calendarSlot.mealPlans" group="mealplan" draggable=".meal" class="calendarDrag">          
            <span slot="header" @click="setDates(calendarSlot.date)"> {{ calendarSlot.displayDate }} <br></span>
            
            <span :meal="mealPlan.mealName" class="meal" v-for="(mealPlan, index) in calendarSlot.mealPlans" :key="index"> 
             {{ mealPlan.mealName }} 
            </span>
            
          </draggable>
          <button>View Plan Details</button>
        </li>
      </ul>
    </section>


      <section class="meal-list">

          <button class="saveBtn" @click="readCalendar()"> Save Meal Plan </button>
          
            <draggable :group="{name: 'mealplan', pull: 'clone', put: false}" :list="mealsToDrag" @start="drag=true" @end="drag=false" class="mealCardContainer"> 
              <div class="meal-card" v-for="meal in mealsToDrag" :key="meal.id">
                  <span :mealId="meal.mealId" > {{meal.mealName}} </span>
                  <span></span>
              </div>
            </draggable>      
    </section>


    <draggable class="trash" group="mealplan" :list="trashmode">
      <i class="fa-solid fa-trash fa-2xl" style="color: #000000;"></i>
      <div>trash</div>
    </draggable>
      <!-- 
        Need to figure out how to make a control on the child key off data in the parent
       -->
      <GroceryList id='groceries' @clear="clearDates()" :startDate="startDate" :endDate="endDate" />

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
        clearDates(){
          this.startDate = "";
          this.endDate = "";
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
.menuTitle{
  grid-area: title;
  margin-top: 4rem;
  text-align: center;
  background-color: #4A180C;
  color: white;
  box-shadow: 0px 6px 20px 0px black;
  border-radius: 10px;

}
.meal:hover{
  cursor: pointer;
}
.calendarDrag{
  max-height: 100%;
  overflow:hidden;
}
.calendarDrag:hover{
  overflow-y: auto;
}
section.calendar-container {
  grid-area: mid;
  background-color: wheat;
  color: black;
  padding: 2rem;
  border-radius: 10px;
   box-shadow: 0px 6px 20px 0px black;
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
  border-radius: 1rem;
  background-color: tan;
  box-shadow: 0px 2px 10px 0px black;
}

.meal-planner {
  display: grid;
  grid-template-columns: 1fr 3fr 2fr;
  grid-template-rows: 0.2fr 2fr 0.2fr 1fr;
  column-gap: 5rem;
  grid-template-areas:
    ". title ."
    ". mid groceries"
    ".trash ."
    ". lowerMid .";
}

.mealplancalendar li span {
  padding: 1rem;
}

.trash {
  margin-top: 1rem;
  grid-area: trash;
  color: black;
  height: 5rem;
  width: 10rem;
  border: 1px solid black;
  background-color: rgb(202, 204, 204);
  border-radius: 3px;
  text-align: center;
  padding-top: 1rem;
  box-shadow: 0px 2px 10px 0px black;
}
.meal-list {
  grid-area: lowerMid;
  background-color: wheat;
  height: 30rem;
  border-radius: 10px;
  margin-top: 2rem;
  box-shadow: 0px 6px 20px 0px black;
}
.meal-card {
  color: black;
  padding: 1rem;
  border: 1px solid black;
  border-radius: 1rem;
  margin: 0.5rem;
  background-color: white;
  font-variant-caps: all-small-caps;
  font-weight: 800;
  width: 100px;
  text-align: center;
  box-shadow: 0px 6px 20px 0px black;
}

.meal-card:hover span{
  cursor: pointer;
  font-size: 2rem;
}

.saveBtn {
  margin: 1rem;
  font-size: 16px;
  box-shadow: 0px 2px 10px 0px black;
}
.mealCardContainer{
  display: flex;
  flex-direction: row;
  padding: 2rem;
  flex-wrap: wrap;
}

#groceries{
  background-color: wheat;
}

</style>