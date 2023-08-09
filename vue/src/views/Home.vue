<template>
  <div class="home">
    <Header class="header"/>
    <div class="welcome">
    <h1>Welcome</h1>
    <img class="home-Image" src="https://media.istockphoto.com/id/157608865/photo/freedom.jpg?b=1&s=612x612&w=0&k=20&c=ZSgvd7_MN9qB53-EdVWazB5IDHfye48SczCwYbSLxvQ=" alt="">
    <p>This could be you after using our Application</p>
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
  margin: 10px;
  border-style: solid;
  border-color: black;
  text-align: center;
  padding-top: 10px;
  padding-bottom: 10px;
  
}
.home-Image {
  border-radius: 50%;
}


</style>