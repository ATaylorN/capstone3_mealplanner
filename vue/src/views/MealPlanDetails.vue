<template>
  <!-- send meal plan list to MealPlanDetails via router prop -->
  <div>
    <Header />
    <MealPlanDetails />
  </div>
</template>

<script>
import Header from "../components/Header.vue";
import MealPlanDetails from "../components/MealPlanDetails.vue";
export default {
  components: {
    MealPlanDetails,
    Header,
  },
};
</script>

<style>
</style>