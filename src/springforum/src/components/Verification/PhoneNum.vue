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
       <h4 class="card-title"><strong>Login with Your Number</strong></h4>
      <b-card-text>Enter your phone number and we will send you a password verified code</b-card-text>
    <b-form  class="form-email" v-if="show">
      <b-form-group
        id="input-group-1"
        label="Phone Number:"
        label-for="input-1"
        description="We'll never share your phone number with anyone else.">
        <b-form-input
          id="input-1"
          v-model="form.phone"
          type="email"
          placeholder="phone number"
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
  name: 'phone',
  data () {
    return {
      show: true,
      form: {
        phone: ''
      },
      counter: 0,
      verifyPhone: '',
      state: ''
    }
  },
  methods: {
    backward () {
      this.$router.push({ name: 'signin' })
    },
    sendCode () {
      const message = { phone: this.form.phone }
      console.log(message)
      const _that = this
      axios({
        method: 'post',
        url: 'http://localhost:8080/api/entry/phonelogin',
        data: message
      }).then(res => {
        _that.verifyPhone = res.data.message
        console.log(res.data.message)
        if (res.data.state === 'OK') {
          _that.form.phone = ''
          localStorage.isPhone = true
          _that.$router.push({ name: 'verifydigit', params: { phone: _that.verifyPhone } })
        }
      })
    }
  }
}
</script>

<style>

</style>
