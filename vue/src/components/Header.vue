<template>
  <div class="header-container">
    <!-- <img class="logo" src="@/Images/logo.png" alt="E-1337 Meal Planer">
    <h1>E-1337 MEAL PLANNER</h1> -->
<nav class="navbar">
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <img src="@/Images/logo.png" alt="E-1337 Meal Planer">
      <!-- <a class="navbar-brand" href="#">E-1337 Meal Planner</a> -->
    </div>
    <div class="collapse navbar-collapse" id="myNavbar">
      <ul class="nav navbar-nav">
        <li class="active"><a href="http://localhost:8080/">Home</a></li>
        <li><a href="http://localhost:8080/user/recipes">My Recipes</a></li>
        <li><a href="http://localhost:8080/recipes">All Recipes</a></li>
        <li><a href="http://localhost:8080/build-recipe">Build a Recipe</a></li>
        <li><a href="http://localhost:8080/meal-builder">Build a Meal</a></li>
        <li><a href="http://localhost:8080/meal-planner">Build a Meal Plan</a></li>
      </ul>
      <ul class="nav navbar-nav navbar-right">
        <li><a href="http://localhost:8080/register"><span class="glyphicon glyphicon-user"></span> Sign Up</a></li>
        <li> <router-link v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''">Logout</router-link> </li>
      </ul>
    </div>
  </div>
</nav>

    <!-- <nav>
      <div class="dropdown">
      <button class="btn btn-default dropdown-toggle" type="button" data-toggle="dropdown"> Menu </button>
        <ul class="dropdown-menu">
          <li> <router-link v-bind:to="{ name: 'home' }">Home</router-link></li>
          <li> <router-link :to="{ name: 'user-recipe-details' }">My Recipes</router-link></li>
          <li> <router-link :to="{ name: 'recipes' }">All Recipes</router-link></li>
          <li> <router-link :to="{ name: 'build-recipe' }">Build Recipe</router-link></li>
          <li> <router-link :to="{ name: 'meal-builder'}">Build Meal</router-link></li>
          <li> <router-link :to="{ name: 'meal-planner'}">Create Meal Plan</router-link></li>
          <li> <router-link v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''">Logout</router-link> </li>
        </ul>
        </div>
    </nav> -->
  </div>
</template>

<script>
export default {
  name: "Header"
}

</script>

<style scoped>
.navbar-header img{
  max-width: 100px;
  margin-right: 2rem;
  margin-left: 2rem;
}
.header-container {
  background-color: #4a180c;
  color: white;
  font-size: 16px;
  justify-content: center;
}
.header-container a {
  color: white;
}
.header-container a:hover{
  color:black;
}
button span{
  background-color: white;
}
.navbar{
  margin: 0px;
}
/* 
.logo {
  margin: auto;
  max-width: 30rem;
  padding: 2rem 2rem 2rem 6rem;
}
.header-container {
  background-color: #4a180c;
  color: white;
  font-family: 'Montserrat', sans-serif;
}
.header-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.header-container h1 {
  flex-basis: 80%;
  
}
.header-container nav {
  display: flex;
  flex-basis: 20%;
  justify-content: space-between;
  padding-right: 10px;
} */
</style>