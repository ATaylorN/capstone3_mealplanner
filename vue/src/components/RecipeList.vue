<template>
  <div>
<Header />
    <table>
        <tr>
            <th>Recipe Name</th>
            <th>Image</th>
        </tr>
        <tr v-for="recipe in recipes" :key="recipe.id">
            <td class="recipe-name">
                <router-link :to="{}"> {{recipe.name}} </router-link> 
            </td>
            <td class="recipe-image-container">
                <img class="recipe-image" :src="recipe.image" alt="">
            </td>
        </tr>

    </table>

    <form @submit="addRecipe()" class="add-recipe">
        <input type="text" v-model="newRecipe.name" placeholder="Recipe Name">
        <input type="text" v-model="newRecipe.instructions" placeholder="Prep Instructions">
        <input type="text" v-model="newRecipe.image" placeholder="Image Link">
        <input type="text" v-model="newRecipe.ingredients" placeholder="Ingredients">
        <button>Save Recipe</button>
    </form>
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