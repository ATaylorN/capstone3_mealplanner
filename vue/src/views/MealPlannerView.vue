<template>
  <div>
    <Header />
    <MealPlanner />
  </div>
</template>

<script>
import Header from "@/components/Header.vue";
import MealPlanner from "@/components/MealPlanner";
export default {
  name: "meal-planner-view",
  components: {
    MealPlanner,
    Header
  },
};
</script>

<style>
</style>