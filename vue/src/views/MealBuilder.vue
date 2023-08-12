<template>
  <div>
      <Header class ="hdr" />
      <MealBuilder />
  </div>
</template>

<script>
import Header from '@/components/Header.vue';
import MealBuilder from '@/components/MealBuilder.vue';
export default {
    name: 'MealBuilderView',
    components: {
        Header,
        MealBuilder
    }

}
</script>

<style>

</style>