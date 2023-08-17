<template>
  <div @click="getDog">
      <img :src="imgSrc" alt="">
      <input type="file" @change="loadFile()" ref="fileslot" />
  </div>
</template>

<script>

//import firebase from 'firebase';
import {getStorage, ref, getDownloadURL, uploadBytes} from 'firebase/storage';





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
            const storage = getStorage();
            const dogRef = ref(storage, 'big dog.png');
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
            const storage = getStorage();
            //const dogRef = ref(storage, 'big dog.png');
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