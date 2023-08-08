<template>
  <div class="header-container">
    <h1>Untitled Meal Planning Application</h1>
    <nav>
      <div class="dropdown">
      <button class="btn btn-default dropdown-toggle" type="button" data-toggle="dropdown"> Menu </button>
        <ul class="dropdown-menu">
          <li> <router-link :to="{ name: 'user-recipe-details' }">View Your Recipes</router-link>&nbsp;|&nbsp;</li>
          <li> <router-link :to="{ name: 'build-recipe' }">Build a Recipe</router-link>&nbsp;|&nbsp; </li>
          <li> <router-link :to="{ name: 'recipes' }">View All Recipes</router-link>&nbsp;|&nbsp;</li>
          <li> <router-link v-bind:to="{ name: 'home' }">Home</router-link>&nbsp;|&nbsp;</li>
          <li> <router-link v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''">Logout</router-link> </li>
        </ul>
        </div>
    </nav>
  </div>
</template>

<script>
export default {
  name: "Header",
  data() {
    return {
      hamburger: false,
    };
  },
};
</script>

<style>
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
  text-align: center;
}
.header-container nav {
  display: flex;
  flex-basis: 20%;
  justify-content: space-between;
  padding-right: 10px;
}
</style>