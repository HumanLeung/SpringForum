<template>
    <div class="reset">
      <div class="resetnav">
      </div>
      <div>
    <b-card
      header-tag="header"
      footer="Card Footer"
      footer-tag="footer"
    >
    <template slot="header">
        <div><i @click="backward()" class="fas fa-long-arrow-alt-left"></i></div>
        <div><i class="far fa-question-circle"></i></div>
    </template>
       <h4 class="card-title"><strong>Enter Your Code</strong></h4>
      <b-card-text>Enter your user account's verified code and we will verify it for you..</b-card-text>
    <b-form class="form-email" v-if="show">
      <b-form-group
        id="input-group-1"
        label="Security Code:"
        label-for="input-1"
        description="never share your code with anyone else."
      >
        <b-form-input
          id="input-1"
          v-model="form.code"
          type="email"
          placeholder="Security Code"
          required
        ></b-form-input>
      </b-form-group>
      </b-form>
      <div>
  <b-button block @click="sendCode()" variant="primary">Next Step</b-button>
</div>
    </b-card>
      </div>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  name: 'verifydigit',
  data () {
    return {
      show: true,
      form: {
        code: ''
      },
      counter: 0,
      num: ''
    }
  },
  methods: {
    backward () {
      this.$router.push({ name: 'phone' })
    },
    sendCode () {
      this.num = this.$route.params.phone
      console.log(this.num)
      const message = {
        code: this.form.code,
        key: this.num
      }
      axios({
        method: 'post',
        url: 'http://localhost:8080/api/entry/verifynum',
        data: message
      }).then(res => {
        localStorage.phoneJwt = res.data.jwt
        if (res.data.state === 'OK') {
          this.$router.push({ path: 'success' })
        }
      })
    }
  }
}
</script>

<style>

</style>
