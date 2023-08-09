<template>
  <div>
      <Header />
      <RecipeDetails :recipeId="$route.params.id" />
      
  </div>
</template>

<script>
import RecipeDetails from '@/components/RecipeDetails'; 
import Header from '../components/Header.vue';
export default {
    name: 'RecipeDetailView',         
    components: {
        RecipeDetails,
        Header
    }

}
</script>

<style>

</style>