<template>
  <div class="header-container">
    <img class="logo" src="@/Images/logo.png" alt="E-1337 Meal Planer">
    <h1>E-1337 MEAL PLANNER</h1>
    <nav>
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
    </nav>
  </div>
</template>

<script>
export default {
  name: "Header"
}

</script>

<style scoped>

.logo {
  margin: auto;
  max-width: 30rem;
  padding: 2rem 2rem 2rem 6rem;
}
.header-container {
  background-color: #4a180c;
  color: white;
  font-family: "Trebuchet MS", "Lucida Sans Unicode", "Lucida Grande",
    "Lucida Sans", Arial, sans-serif;
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
}
</style>