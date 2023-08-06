<template>
  <div>
<Header />
  </div>
</template>

<script>
import Header from '@/components/Header.vue';
import RecipeService from '@/services/RecipeService.js';

export default {
    name:'recipe-list',
    components: {
        Header
    },
    data() {
        return {
            recipes: [],
            newRecipe: {
                name: "",
                ingredients: "",
                instructions: ""
            }
        }
    },
    methods: {
        addRecipe() {
            RecipeService.addRecipe(this.newRecipe)
            .catch(error => {
                console.error(error);
            });
        }
    },
    created(){
        RecipeService.listRecipes().then(response => {
            if(response.status === 200){
                this.recipes = response.data;
            }
        })
        .catch (error => {
            console.error(error);
        })
    }
}
</script>

<style>

</style>