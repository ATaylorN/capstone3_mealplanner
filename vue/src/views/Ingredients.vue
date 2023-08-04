<template>
  <div>
      <IngredientList />
      <AddIngredient />
  </div>
</template>

<script>
import IngredientList from '@/components/IngredientList';
import AddIngredient from '@/components/AddIngredient';

export default {
    name: "ingredient-view",
    components: {
        IngredientList,
        AddIngredient
    }
}
</script>

<style>

</style>