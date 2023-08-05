<template>
  <div class="home">
    <Header />
    <h1>Home</h1>
    <p>You must be authenticated to see this</p>
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
