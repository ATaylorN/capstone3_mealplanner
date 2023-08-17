import Vue from 'vue'
import App from './App.vue'
import router from './router/index'
import store from './store/index'
import axios from 'axios'
import { initializeApp } from 'firebase/app';

const firebaseConfig = {
  apiKey: "AIzaSyB-fKR0FaaVGQhCubKkpMQZxM7mbntHB4w",
  authDomain: "capstone-meal.firebaseapp.com",
  projectId: "capstone-meal",
  messagingSenderId: "873102642159",
  appId: "1:873102642159:web:2423cdd3c8bfef7414841a",
  measurementId: "G-RPT693PW5Y",
  storageBucket: 'gs://capstone-meal.appspot.com'
};

initializeApp(firebaseConfig);


Vue.config.productionTip = false

axios.defaults.baseURL = process.env.VUE_APP_REMOTE_API;



new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')

