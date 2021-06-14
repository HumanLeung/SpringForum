<template>
  <div id="app">
    <router-view/>
  </div>
</template>
<script>
export default {
  name: 'App',
  mounted () {
    if (localStorage.userType === '2') {
      console.log(localStorage.userType)
      this.$store.dispatch('getUserByNum')
    } else {
      if (localStorage.isPhone === 'true') {
        this.$store.dispatch('phoneUser')
      } else {
        this.$store.dispatch('getUsers')
      }
    }
  }
}
</script>
<style>
#app {
  font-family: 'Montserrat', sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: #42b983;
}
</style>
