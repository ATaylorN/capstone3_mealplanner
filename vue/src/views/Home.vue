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
        <router-link :to="{ name: 'meal-planner'}" tag="button" class="mealbtn">
          <img src="@/Images/calendarPlaceholder.png" alt="">
        </router-link>
      </div>

    </div>

    <div class="featured">
      <h1>FEATURED RECIPES</h1>
      <div class=card-container>
      <div class="card"><img src="@/Images/recipePlaceHolder.png" alt=""><p>INSERT TITLE HERE</p></div>
      <div class="card"><img src="@/Images/recipePlaceHolder.png" alt=""><p>INSERT TITLE HERE</p></div>
      <div class="card"><img src="@/Images/recipePlaceHolder.png" alt=""><p>INSERT TITLE HERE</p></div>
      <div class="card"><img src="@/Images/recipePlaceHolder.png" alt=""><p>INSERT TITLE HERE</p></div>
      <div class="card"><img src="@/Images/recipePlaceHolder.png" alt=""><p>INSERT TITLE HERE</p></div>
      <div class="card"><img src="@/Images/recipePlaceHolder.png" alt=""><p>INSERT TITLE HERE</p></div>
    </div>
    </div>

  </div>
</template>

<script>
import Header from '@/components/Header';
export default {
  name: "home",
  components:{
    Header
  }
};
</script>

<style>



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

.top-section{
  display: grid;
  justify-content: space-around;
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
  color: white;
  margin-bottom: 10rem;
}
.card-container{
  display: flex;
  gap: 2rem;
  flex-wrap: wrap;
  justify-content: center;
}

.featured img {
  border-radius: 8px;
  max-width: 20rem;
  max-height: auto;
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
}
.top-section{
  display: flex;
  gap: 5rem;
  background-color: #4A180C;
  color: white;
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