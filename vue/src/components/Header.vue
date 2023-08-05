<template>
<div class="header-container">    
  <h1>Untitled Meal Planning Application</h1>
  <nav class="hamburger">
    <router-link :to="{ name: 'ingredients' }">Build a Recipe</router-link>&nbsp;|&nbsp;   
    <router-link v-bind:to="{ name: 'home' }">Home</router-link>&nbsp;|&nbsp;
    <router-link v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''">Logout</router-link> 
  </nav>
  </div>    
</template>

<script>
export default {

}
</script>

<style>
    .header-container{
        display: flex;
        justify-content: space-between;
        align-items: center;
    }
    .header-container h1 {
        flex-basis: 80%;  
        text-align: center;      
    }
    .header-container nav{
    display: flex;
    flex-basis: 20%;
    justify-content: space-between;
    padding-right: 10px;
    } 
</style>