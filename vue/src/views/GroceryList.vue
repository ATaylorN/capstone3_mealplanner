<template>
  <div @click="getDog">
      <img src="" alt="">
  </div>
</template>

<script>

//import firebase from 'firebase';
import { getStorage, ref } from 'firebase/storage';
export default {
    components: {
        
    },
    methods: {
        getDog() {
            const storage = getStorage();
            const storageRef = ref(storage, 'big dog.png');
            console.log(storageRef)
            //console.log(this.$fireBaseStorage)
        }
    }
}
</script>

<style>

</style>