<template>
  <div class="success">
      <i class="fas fa-check-circle"></i>
       <h1>Successfully</h1>
   <div>
  <b-button @click="login()" variant="outline-primary">Login Now</b-button>
  <b-button @click="forwordSign()" variant="outline-success">Login Page</b-button>
   </div>
  </div>
</template>

<script>
import axios from 'axios'
import { mapActions } from 'vuex'
export default {
  name: 'success',
  data () {
    return {
      bodies: null
    }
  },
  methods: {
    forwordSign () {
      localStorage.clear()
      this.$router.push({ path: '/main/signin' })
    },
    ...mapActions([
      'loadUser'
    ]),
    loadData (email) {
      var _that = this
      axios({
        method: 'get',
        url: 'http://localhost:8080/api/user/load/' + email,
        header: {
          Authorization: localStorage.token
        }
      }).then(res => {
        if (res.status === 200) {
          _that.loadUser(res.data.user)
        }
      })
    },
    login () {
      console.log(localStorage.isPhone)
      if (localStorage.isPhone === 'true') {
        console.log(localStorage.isPhone)
        this.$store.dispatch('phoneUser')
        this.$router.push({ name: 'home' })
      } else {
        this.normal()
        console.log('asd')
      }
    },
    normal () {
      this.bodies = this.$route.params.bodies
      var message = {
        email: this.bodies.emailAddress,
        password: this.bodies.password
      }
      var _that = this
      axios({
        method: 'post',
        url: 'http://localhost:8080/api/entry/login',
        data: message
      }).then(res => {
        if (res.status === 200) {
          localStorage.token = res.headers.authorization
          _that.loadData(_that.bodies.emailAddress)
          _that.$router.push({ name: 'home' })
        }
      }).catch(err => {
        if (err.response.status) {
          _that.$router.push({ name: 'signin' })
        }
      })
    }
  }
}
</script>

<style>
.success
{
    height: 100vh;
    background: #fff;
    position: absolute;
    top: 0;
    width: 100vw;
    display: flex;
    flex-direction: column;
    justify-content: space-around;
}
.success h1
{
    padding-bottom: 6rem;
}
.success .fas.fa-check-circle
{
    font-size: 6rem;
    color: darkseagreen;
}
</style>
