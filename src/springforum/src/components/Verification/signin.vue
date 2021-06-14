<template>
  <div class="LoginPage">
       <b-card-group deck>
    <b-card
      header-tag="header"
    >
    <template slot="header">
    <h2 class="SignInTitle"><strong>Sign In</strong></h2>
    </template>
       <b-form  v-if="show">
      <b-form-group
        id="input-group-1"
        label-for="input-1"
        description="We'll never share your email with anyone else."
      >
        <b-form-input
          id="input-1"
          v-model="form.email"
          type="email"
          placeholder="Enter email"
          required
        ></b-form-input>
      </b-form-group>

      <b-form-group id="input-group-2" label-for="input-2">
        <b-form-input
          id="input-2"
          v-model="form.password"
          placeholder="Enter Password"
          type="password"
          required
        ></b-form-input>
      </b-form-group>
       </b-form>
      <b-button @click="login()" variant="primary" block>Sign In</b-button>
      <div class="fps">
        <span>forget password ?</span>&nbsp;<span><b-link @click="forwardForget()">Click Here</b-link></span>
      </div>
      <div class="third-party">
        <i class="fab fa-weixin"></i>
        <a @click="oauthLogin()" href="http://localhost:8080/oauth2/authorization/github"><i class="fab fa-github"></i></a>
        <a @click="oauthLogin()" href="http://localhost:8080/oauth2/authorization/google"><i class="fab fa-google"></i></a>
        <i @click="loginPhone()" class="fas fa-mobile-alt"></i>
      </div>
        <div class="fps-2">
        <span>don't have an account?</span>&nbsp;<span><b-link @click="forwardSignUp()">Sign up here</b-link></span>
      </div>
    </b-card>
  </b-card-group>
  </div>
</template>
<script>
import { mapActions } from 'vuex'
import axios from 'axios'
export default {
  name: 'signin',
  data () {
    return {
      show: true,
      form: {
        email: '',
        password: ''
      }
    }
  },
  methods: {
    forwardForget () {
      this.$router.push({ name: 'forget' })
    },
    forwardSignUp () {
      this.$router.push({ name: 'signup' })
    },
    oauthLogin () {
      var num = Math.floor(Math.random() * 90000000) + 10000000
      localStorage.oauthKey = num
      axios.get('http://localhost:8080/api/oauth2/info/' + num, {
      })
        .then(res => {
          console.log(res)
        })
    },
    login () {
      var _that = this
      var message = {
        email: this.form.email,
        password: this.form.password
      }
      console.log(message)
      axios({
        method: 'post',
        url: 'http://localhost:8080/api/entry/login',
        data: message
      }).then(res => {
        console.log(res)
        if (res.status === 200) {
          console.log(res.status)
          localStorage.token = res.headers.authorization
          console.log(res)
          _that.loadData(_that.form.email)
          _that.form.email = ''
          _that.form.password = ''
        }
      }).catch(err => {
        console.log(err)
      })
      console.log('login')
    },
    loadData (email) {
      var headerInfo
      if (localStorage.userType === '2') {
        headerInfo = {
          Oauth2: 'oauth2 ' + localStorage.userNum,
          Authorization: 'null'
        }
      } else {
        headerInfo = {
          Authorization: localStorage.token
        }
      }
      console.log(email)
      var _that = this
      axios({
        method: 'get',
        url: 'http://localhost:8080/api/user/load/' + email,
        headers: headerInfo
      }).then(res => {
        console.log(res.data.user.userNum)
        localStorage.userNum = res.data.user.userNum
        console.log(res.data.user)
        localStorage.uid = res.data.user.id
        _that.loadUser(res.data.user)
        _that.$router.push({ name: 'home' })
      }).catch(err => {
        console.log(err)
        _that.$router.push({ name: 'signin' })
      })
    },
    loginPhone () {
      this.$router.push({ name: 'phone' })
    },
    requestEmail () {
      var _that = this
      axios({
        method: 'get',
        url: 'http://localhost:8080/api/entry/freelogin',
        headers: {
          Authorization: localStorage.token
        }
      }).then(res => {
        _that.loadData(res.headers.check)
        _that.$router.push({ name: 'home' })
      })
    },
    ...mapActions([
      'changeBG',
      'loadUser',
      'getUserByNum'
    ])
  },
  mounted () {
    if (localStorage.token != null) {
      this.requestEmail()
    }
    if (localStorage.oauthKey != null) {
      this.getUserByNum()
      this.$router.push({ name: 'home' })
    }
  }
}
</script>

<style>
.SignInTitle{
  color: #0275d8;
  margin: auto;
}
.LoginPage .card-header{
   background: white;
   padding-bottom: 7rem;
   padding-top: 5rem;
   border: none;
}
.LoginPage {
  position: absolute;
  width: 100vw;
  height: 100vh;
  top: 0;
}
.LoginPage .card-body{
  padding-bottom: 13rem;
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
  width: 15rem;
  margin: auto;
  padding-top: 3rem;
  padding-bottom: 10vh;
}
.third-party .fab, .fas
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
.fas.fa-mobile-alt.fa-mobile-alt
{
  color: #0077b5;
}
.LoginPage .fps-2
{
    display: flex;
    justify-content: space-around;
}
</style>
