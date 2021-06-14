<template>
  <div class="SignUpPage">
       <b-card-group deck>
    <b-card
      header-tag="header"
    >
    <template slot="header">
    <h2 class="signupTitle"><strong>Sign Up</strong></h2>
    </template>
       <b-form  v-if="show">
    <b-form-group id="input-group-2" label="Your Name:" label-text-align="left"
    description="Please enter your username above"
     label-for="input-2">
        <b-form-input
          id="input-2"
          v-model="name"
          placeholder="Enter name"
          required
        ></b-form-input>
      </b-form-group>
      <b-form-group
        id="input-group-1"
        label-for="input-1"
        description="Please enter your email address above"
        class="description"
      >
        <b-form-input
          id="input-1"
          v-model="email"
          type="email"
          placeholder="Enter email"
          required
        ></b-form-input>
      </b-form-group>

      <b-form-group id="input-group-2" label-for="input-2"
      description="Please enter your password above"
      >
        <b-form-input
          id="input-2"
          v-model="password"
          placeholder="Enter Password"
          type="password"
          required
        ></b-form-input>
      </b-form-group>
       </b-form>
      <b-button href="#" @click.prevent="registers()" variant="primary" block>Sign Up</b-button>
      <div class="third-party">
        <i class="fab fa-weixin"></i>
        <i class="fab fa-github"></i>
        <i class="fab fa-google"></i>
      </div>
        <div class="fps-2">
        <span>already have an account</span>&nbsp;<span><b-link @click="forwardSignIn()" href="#">Sign In</b-link></span>
      </div>
    </b-card>
  </b-card-group>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  name: 'signup',
  data () {
    return {
      show: true,
      email: '',
      password: '',
      name: '',
      phone: '',
      verifyEmail: '',
      state: ''
    }
  },
  methods: {
    forwardForget () {
      this.$router.push({ name: 'forget' })
    },
    forwardSignIn () {
      this.$router.push({ name: 'signin' })
    },
    registers () {
      const message = {
        username: this.name,
        emailAddress: this.email,
        password: this.password
      }
      console.log(message)
      const _that = this
      axios({
        method: 'post',
        url: 'http://localhost:8080/api/entry/register',
        data: message
      })
        .then((res) => {
          _that.state = res.data.state
          _that.verifyEmail = res.data.message
          console.log(_that.verifyEmail)
          if (_that.state === 'OK') {
            _that.$router.push({ name: 'verifycode', params: { test: _that.verifyEmail } })
          }
        })
        .catch(err => console.log(err))
      _that.name = ''
      _that.email = ''
      _that.password = ''
    }
  }
}
</script>

<style>
.signupTitle{
  color: #0275d8;
  margin: auto;
}
.SignUpPage .card-header{
   background: white;
   padding-bottom: 2rem;
   padding-top: 1rem;
   border: none;
   height: 20vh;
}
.SignUpPage {
  position: absolute;
  width: 100vw;
  height: 80vh;
  top: 0;
}
.SignUpPage .card-body{
  border-radius: 0;
}
.LoginPage .card
{
  height: 100vh;
  border-radius: 0;
}
.third-party
{
  display: flex;
  justify-content: space-between;
  width: 10rem;
  margin: auto;
  padding-top: 3rem;
  padding-bottom: 10vh;
}
.third-party .fab
{
  font-size: 2rem;
}
.fab.fa-weixin {
  color: #09b83e;
}
.fab.fa-github{
  color: #131418;
}
.fab.fa-google {
  color: #dd4b39;
}
.LoginPage .fps-2
{
    display: flex;
    justify-content: space-around;
}
.LoginPage .form-text{
   text-align: left !important;
   float: left;
}
.SignUpPage .d-block
{
  text-align: left;
}
</style>
