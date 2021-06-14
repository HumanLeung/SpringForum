<template>
 <div class="CheckEmail">
      <div class="CheckEmailNav">
      <div>
    <b-card
      header-tag="header"
      footer-tag="footer"
    >
    <template slot="header">
        <div @click="backward()"><i  class="fas fa-long-arrow-alt-left"></i></div>
        <div><i class="far fa-question-circle"></i></div>
    </template>
      <template slot="footer">asdad</template>
        <i class="fas fa-envelope-open-text"></i>
       <h4 class="card-title"><strong>Enter the security code</strong></h4>
      <b-card-text>Enter the code that you have received in your mailbox</b-card-text>
    <b-form @submit="onSubmit" class="form-email" @reset="onReset" v-if="show">
      <b-form-group
        id="input-group-1"
        label="Verification Code"
        label-for="input-1"
        description="We'll never share your email with anyone else."
      >
        <b-form-input
          id="input-1"
          v-model="form.email"
          type="email"
          placeholder="Verification Code"
          required
        ></b-form-input>
      </b-form-group>
      </b-form>
      <div>
  <b-button block @click="forwardPassword()" variant="primary">Next Step</b-button>
  <div class="sub-link2">
      Don't have received ?
  <b-link href="#foo">Resend</b-link>
</div>
</div>
    </b-card>
      </div>
  </div>
  </div>
</template>

<script>
export default {
  name: 'checkemail',
  data () {
    return {
      show: true,
      form: {
        email: ''
      }
    }
  },
  methods: {
    forwardPassword () {
      this.$router.push({ path: 'password' })
    },
    backward () {
      this.$router.push({ path: 'forget' })
    }
  }
}
</script>

<style>
.CheckEmail
{
    position: absolute;
    top: 0;
}
.CheckEmail .card-body
{
    height: 84vh;
}
.CheckEmail .sub-link2
{
    font-size: 1rem;
}
.CheckEmail .fas.fa-envelope-open-text
{
    font-size: 3rem;
    padding-bottom: 5rem;
    color: #0275d8;
}
</style>
