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
template{
  display: grid;
  grid-template-columns: 1fr 1fr 1fr 1fr;
  grid-template-areas: "header header header header"
                        ". welcome welcome ."
                        "footer footer footer footer";
}
header {
  grid-area: header;
}

welcome {
  grid-area: welcome;
  text-align: center;
}

</style>