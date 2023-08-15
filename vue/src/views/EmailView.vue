<template>
<div>
    <Header />
 <div class="email-form">
  <form ref="form" @submit.prevent="sendEmail">
    <label>Name</label>
    <input type="text" name="user_name">
    <label>Email</label>
    <input type="email" name="user_email">
    <label>Message</label>
    <textarea name="message"></textarea>
    <input type="submit" value="Send">
  </form>
</div>
  </div>
</template>

<script>
import Header from '@/components/Header.vue';
import emailjs from '@emailjs/browser';

export default {
    name: "email-view",
    components: {
        Header
    },
      methods: {
    sendEmail() {
      emailjs.sendForm('service_8vy3gma', 'template_x42f4g7', this.$refs.form, 'GEie82de2ImMbutSn')
        .then((result) => {
            console.log('SUCCESS!', result.text);
        }, (error) => {
            console.log('FAILED...', error.text);
        });
    }
  }
}
</script>

<style scoped>
.email-form .field {
  margin-bottom: 10px;
}

.email-form .field label {
  display: block;
  font-size: 12px;
  color: #777;
}

.email-form .field input {
  display: block;
  min-width: 250px;
  line-height: 1.5;
  font-size: 14px;
}

.email-form input[type="submit"] {
  display: block;
  padding: 6px 30px;
  font-size: 14px;
  background-color: wheat;
  color: black;
}
.email-form input[type="submit"]:hover{
    background-color: darkkhaki;
}
</style>