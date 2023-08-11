<template>
<div>
  <Header/>
  <UserRecipeList/>
  </div>
</template>

<script>

import UserRecipeList from '../components/UserRecipeList.vue'
import Header from '@/components/Header.vue';

export default {
    name: 'UserRecipesView',
    components: {
        UserRecipeList,
        Header
    }
}
</script>

<style>

</style>