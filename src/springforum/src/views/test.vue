<template>
  <div>
      <p>{{msg}}</p>
    <div class="container">
    <h2 class="form-signin-heading">Login with OAuth 2.0</h2>
    <button @click="test()">Click here</button>
</div>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  name: 'test',
  data () {
    return {
      msg: 'cool',
      page: null
    }
  },
  methods: {
    getTodos () {
      var num = Math.floor(Math.random() * 90000000) + 10000000
      localStorage.oauthKey = num
      axios.get('http://localhost:8080/api/oauth2/info/' + num, {
      })
        .then(res => {
          console.log(res)
        })
    },
    test () {
      var message = {
        email: '2691642766@qq.com',
        password: 'qq123456'
      }
      axios.post('http://localhost:8080/api/entry/login', message)
        .then(res => {
          console.log(res)
        })
    }
  }
}
</script>
