<template>
  <div class="header-container">
    
<nav class="navbar">
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <img src="@/Images/logo.png" alt="E-1337 Meal Planer">
    
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
        <li> <router-link v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''">Logout</router-link> </li>
      </ul>
    </div>
  </div>
</nav>

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

</style>