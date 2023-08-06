<template>
  <div>
      <RecipeList />
  </div>
</template>


<script>
import RecipeList from '@/components/RecipeList.vue';

export default {
    name: "recipe-view",
    components: {
        RecipeList,
    }
}
</script>

<style>

</style>