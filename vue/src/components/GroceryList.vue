<template>
  <div id="grocery-list">
      <h2 class="grocery-list-header">{{getHeaderText}}</h2>
      <button @click="$emit('save'), getMealPlanIngredients()">Generate Grocery List</button> 
      <button v-if="listShowing" @click="printGroceryList()">Print Grocery List</button>
        <button v-if="listShowing" @click="emailGroceryList(mailTo)">E-mail Grocery List</button>
        <button v-if="listShowing" @click="generateIcsFile()">Generate Calendar File</button>
        <input v-if="listShowing" type="text" v-model="mailTo" placeholder="email address">
        <a v-show="false" ref="fileElement"> </a> 
      <button @click="$emit('clear'), clear()">Clear</button>      
    <div class="ingredients" id="ingredient-list" ref="listo"> 
        <ol> 
            <li v-for="ingredient in mealPlanIngredients" v-bind:key="ingredient.id">{{ingredient.name}} </li>           
        </ol>  
    </div>
        
  </div>
</template>

<script>
import GroceryListService from '../services/GroceryListService.js'; 
import { Printd }  from 'printd'; 
import emailjs from '@emailjs/browser'
import * as ics from 'ics';
// import moment from "moment";

export default {
    name: "grocery-list",
    components: {
        
    },
    props: ['startDate', 'endDate', 'mealPlans'],
    data() {
            return {
                mealPlanIngredients: [],
                listShowing: false,
                mailTo: "",
        }
    },
    computed: {
      getHeaderText(){
          let output = "";
          if(!this.startDate && !this.endDate){
              output = "Click start and end date to get ingredients for grocery list."; 
          } else if (!this.startDate) {
              output = "Groceries from today til " + this.endDate; 
          } else {
              output = "Groceries from " + this.startDate + " to " + this.endDate; 
          }
          return output; 
      }  
    },    
    methods: {
        generateIcsFile(){        
           let events = [];
           this.mealPlans.forEach(mealPlan => {
               let startDate = mealPlan.dateToCook.split('-').map(str => {return parseInt(str)});
               let endDate = mealPlan.dateToCook.split('-').map(str => {return parseInt(str)})
               endDate[2] += 1 
               const event = {
                   start: startDate,
                   end: endDate,
                   title: 'Prepare ' + mealPlan.mealName,
                   description: 'Time to get groceries!',
                   url: 'http://localhost:8080/meal-planner',

               }
              events.push(event);                                    
           });

        let icsData = ics.createEvents(events);
        console.log(icsData.value)
        let icsFileStrings = icsData.value.split('\r\n')
        console.log(icsFileStrings)
        
        this.calendarFile = icsFileStrings
        this.$refs.fileElement.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(icsData.value))  
        this.$refs.fileElement.setAttribute('download', 'mealplan.ics') 
        this.$refs.fileElement.click();             
        },
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
            const d = new Printd();
            d.print(document.getElementById('ingredient-list')) // html element
            console.log('listo printo');
        },
        emailGroceryList(){
            let messageBody = this.$refs.listo.outerHTML; 
            // this.$refs.listo

            console.log(); 
            let params = {
                // to_name: "Sled Jacobs Jr",                
                user_email: this.mailTo,                
                message: messageBody
                }
            console.log(params.message);
            // console.log(emailjs)
            emailjs.send('service_8vy3gma', 'template_x42f4g7', params, 'GEie82de2ImMbutSn')
                .then(res => {
                    console.log(res.text);
                    console.log("great success")
                })
        }
    },

}


</script>

<style scoped>
.grocery-list-header{
    color: white;
}
.ingredients li{
    font-size: 20px;
}
.ingredients{
    margin: 2rem;
}
</style>