<template>
  <div>
    <Navbar/>
  <div class="main-container">
  <div class="content">
    <keep-alive>
    <router-view/>
    </keep-alive>
     </div>
    </div>
    <Footer/>
  </div>
</template>

<script>
import Footer from '../components/Main/Footer.vue'
import Navbar from '../components/Main/Navbar.vue'
export default {
  name: 'main',
  components: {
    Navbar,
    Footer
  },
  mounted () {
    if (localStorage.getItem('token') === null && localStorage.oauthID === null) {
      console.log(localStorage.token)
      this.$router.push({ name: 'signin' })
    }
  }
}
</script>

<style>
.main-container
{
background-color: #d7e5f3;
  height: 100vh;

}
.main-container .content
{
  padding-top: 4.1rem;
  padding-bottom: 3.5rem;
}
</style>
