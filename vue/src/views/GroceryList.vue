<template>
  <div @click="getDog">
      <img :src="imgSrc" alt="">
      <input type="file" @change="loadFile()" ref="fileslot" />
  </div>
</template>

<script>

//import firebase from 'firebase';
import { initializeApp } from 'firebase/app';
import {getStorage, ref, getDownloadURL, uploadBytes} from 'firebase/storage';

const firebaseConfig = {
  apiKey: "AIzaSyB-fKR0FaaVGQhCubKkpMQZxM7mbntHB4w",
  authDomain: "capstone-meal.firebaseapp.com",
  projectId: "capstone-meal",
  messagingSenderId: "873102642159",
  appId: "1:873102642159:web:2423cdd3c8bfef7414841a",
  measurementId: "G-RPT693PW5Y",
  storageBucket: 'gs://capstone-meal.appspot.com'
};

const fbApp = initializeApp(firebaseConfig); 
const storage = getStorage(fbApp);
const dogRef = ref(storage, 'big dog.png');



//import firebase from 'firebase/app';

export default {
    components: {
        
    },
    data(){
        return {
            imgSrc: "",
            image: null,
        }
    },
    methods: {
        getDog() {
            getDownloadURL(dogRef)
                .then(url => {
                    // const xhr = new XMLHttpRequest();
                    // xhr.responseType = 'blob';
                    // xhr.onload = (event) => {
                    //     const blob = xhr.response; 
                    // }; 
                    // xhr.open('GET', url);
                    // xhr.send();
                    this.imgSrc = url; 
                })
                 
            //console.log(dogSrc);
            
        }, 
        loadFile(){
            this.image = this.$refs.fileslot.files[0];
            const imagesFolder = ref(storage, '/recipeImgs/' + this.image.name)
            // upload takes a reference, and then a file or blob object 
            uploadBytes(imagesFolder, this.image)
                .then(response => {
                    console.log('great success?')
                    console.log(response)
                })
        }
    }
}
</script>

<style>

</style>