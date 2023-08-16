<template>
  <div class="home">
    <Header class="header"/>

    <div class="top-section">
      <div class="next-recipe">
        <h1>UP NEXT</h1>
        <img src="@/Images/recipePlaceHolder.png" alt="">
        <p>INSERT RECIPE TITLE HERE</p>
      </div>

      <div class="my-plan">
        <h1>MY PLAN</h1>
        <img @click="sendToMealPlan()" src="@/Images/calendarPlaceholder.png" alt="My Meal Plan">
      </div>

    </div>

    <div class="featured">
      <h1>FEATURED RECIPES</h1>
      <div class="card-container">
      <a href="http://localhost:8080/recipes/5"><img src="https://images.pexels.com/photos/1657343/pexels-photo-1657343.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1">
      <p>Basic Muffins</p></a>
      <a href="http://localhost:8080/recipes/2"><img src="https://images.pexels.com/photos/4110541/pexels-photo-4110541.jpeg">
      <p>Peanut Butter Cookies</p></a>
      <a href="http://localhost:8080/recipes/4"><img src="https://www.naschkatze.me/wp-content/uploads/2019/12/Schokolis-mit-Haselnuss-1-768x578.jpg">
      <p>Hazelnut Bites</p></a>
      <a href="http://localhost:8080/recipes/6"><img src="https://images.pexels.com/photos/15850680/pexels-photo-15850680/free-photo-of-close-up-of-delicious-chocolate-cake-with-fudge.jpeg">
      <p>Fudge</p></a>
    </div>
    </div>

  </div>
</template>

<script>
import Header from '@/components/Header';


export default {
  name: "home",
  components:{
    Header,
  },
  data(){
    return {
      nextMeal: {}
    }
  },
  methods: {
    sendToMealPlan() {
      return this.$router.push({name: 'meal-planner'})
    }
  }
};
</script>

<style>

*{
  font-family: 'Montserrat', sans-serif;;
}

body{
background-image: url('@/Images/BackgroundImage.png');
background-attachment: scroll;
height: 100%;
background-size: cover;
}

.home {
  display: grid;
  gap: 5rem;
  grid-template-columns: 1fr 1fr 1fr;
  grid-template-areas: "header header header"
                        "top top top"
                        "featured featured featured";
}

.header {
  grid-area: header;
}
.top-section {
  grid-area: top;
}
.top-section h1{
  font-family: 'Montserrat', sans-serif;
}

.top-section{
  display: grid;
  justify-content: space-evenly;
  padding-right: 10rem;
}

.next-recipe{
  padding: 1rem;
  margin-left: 2rem;
}
.my-plan{
  padding: 1rem;
  margin-right: 2rem;
}

.featured {
  display: flex;
  flex-direction: column;
  text-align: center;
  padding-top: 2rem;
  grid-area: featured;
  gap: 5rem;
  background-color: #4A180C;
  box-shadow: 0px 6px 20px 0px black;
  color: white;
  margin-bottom: 10rem;
}
.featured h1{
  font-family: 'Montserrat', sans-serif;
}
.featured a{
  font-family: 'Montserrat', sans-serif;
  text-decoration: none;
  color: white;
}
.card-container{
  display: flex;
  gap: 2rem;
  flex-wrap: wrap;
  justify-content: center;
}

.featured img {
  border-radius: 8px;
  max-width: 100%;
  max-height: 20rem;
}
.featured img:hover{
  cursor: pointer;
  border: 5px solid yellowgreen;
}

p{
  padding-top: 1rem;
}

.next-recipe p{
  text-align: center;
  font-family: 'Montserrat', sans-serif;
}
.top-section{
  display: flex;
  gap: 5rem;
  background-color: #4A180C;
  color: white;
  box-shadow: 0px 6px 20px 0px black;
}

.top-section img {
  border-radius: 8px;
  max-width: 30rem;
  max-height: auto;
}

.top-section img:hover{
  cursor: pointer;
  border: 5px solid yellowgreen;
}
</style>