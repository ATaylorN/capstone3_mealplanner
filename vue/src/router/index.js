import Vue from 'vue'
import Router from 'vue-router'
import Home from '../views/Home.vue'
import Login from '../views/Login.vue'
import Logout from '../views/Logout.vue'
import Register from '../views/Register.vue'
import store from '../store/index'
import Recipe from '../views/Recipe.vue'
import RecipeDetails from '../views/RecipeDetails.vue';
import RecipeBuilder from '../views/RecipeBuilder.vue';
import UserRecipeDetails from '../views/UserRecipeDetails.vue';
import RecipeUpdate from '../views/RecipeUpdate.vue';
import MealBuilder from '../views/MealBuilder.vue';
import MealPlannerView from '../views/MealPlannerView.vue';
import GroceryList from '../views/GroceryList.vue';
import MealPlanDetails from '../views/MealPlanDetails.vue'
//import { component } from 'vue/types/umd'


Vue.use(Router)

/**
 * The Vue Router is used to "direct" the browser to render a specific view component
 * inside of App.vue depending on the URL.
 *
 * It also is used to detect whether or not a route requires the user to have first authenticated.
 * If the user has not yet authenticated (and needs to) they are redirected to /login
 * If they have (or don't need to) they're allowed to go about their way.
 */

const router = new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/build-recipe",
      name: "build-recipe",
      component: RecipeBuilder,
    },
    {
      path: "/login",
      name: "login",
      component: Login,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/logout",
      name: "logout",
      component: Logout,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/register",
      name: "register",
      component: Register,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: '/recipes',
      name: 'recipes',
      component: Recipe
    },
    {
      path: '/recipes/new-recipe',
      name: 'new-recipe',
      component: RecipeBuilder
    },
    {
      path: '/recipes/:id',
      name: 'recipe-details',
      component: RecipeDetails,
    },
    {
      path: '/user/recipes',
      name: 'user-recipe-details',
      component: UserRecipeDetails,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: '/recipes/:id/update',
      name: 'recipe-update',
      component: RecipeUpdate,
    },
    {
      path: '/meal-builder',
      name: 'meal-builder',
      component: MealBuilder,
      props:{}
    },
    {
      path: '/meal-planner',
      name: 'meal-planner',
      component: MealPlannerView
    },
    {
      path: '/grocery-list',
      name: 'grocery-list',
      component: GroceryList
    },
    {
      path: '/meal-plan-details/:date',
      name: 'meal-plan-details',
      component: MealPlanDetails,
    }

  ]
})

router.beforeEach((to, from, next) => {
  // Determine if the route requires Authentication
  const requiresAuth = to.matched.some(x => x.meta.requiresAuth);

  // If it does and they are not logged in, send the user to "/login"
  if (requiresAuth && store.state.token === '') {
    next("/login");
  } else {
    // Else let them go to their next destination
    next();
  }
});

export default router;
