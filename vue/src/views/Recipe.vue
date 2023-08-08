<template>
  <div>
      <Header/>
      <RecipeList />
  </div>
</template>


<script>
import RecipeList from '@/components/RecipeList.vue';
import Header from '@/components/Header.vue'

export default {
    name: "recipe-view",
    components: {
        RecipeList,
        Header
    }
}
</script>

<style>

</style>