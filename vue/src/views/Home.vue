<template>
  <div class="home">
    <Header class="header"/>
    <div class="welcome">
    <h1>Home</h1>
    <p>You must be authenticated to see this</p>
  </div>
  </div>
</template>

<script>
import Header from '@/components/Header';
export default {
  name: "home",
  components:{
    Header
  }
};
</script>

<style>
body{
background-image: url('@/Images/BackgroundImage.png');
background-repeat: no-repeat;
height: 100%;
background-size: cover;
}
.welcome {
  background-color:#4a180c;
  color: white;
  margin: 150px;
  border-style: solid;
  border-color: black;
  text-align: center;
  padding-top: 50px;
  padding-bottom: 50px;
  
}


</style>