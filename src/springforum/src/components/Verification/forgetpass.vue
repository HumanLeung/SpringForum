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
       <h4 class="card-title"><strong>Reset password</strong></h4>
      <b-card-text>Enter your user account's verified email address and we will send you a password reset link..</b-card-text>
    <b-form @submit="onSubmit" class="form-email" @reset="onReset" v-if="show">
      <b-form-group
        id="input-group-1"
        label="Email address:"
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
      </b-form>
      <div>
  <b-button block @click="forwardEmail()" variant="primary">Next Step</b-button>
</div>
    </b-card>
      </div>
  </div>
</template>

<script>
export default {
  name: 'forget',
  data () {
    return {
      show: true,
      form: {
        email: ''
      },
      counter: 0
    }
  },
  methods: {
    forwardEmail () {
      this.$router.push({ name: 'checkemail' })
    },
    backward () {
      this.$router.push({ name: 'signin' })
    }
  }

}
</script>

<style>
.reset{
 position: absolute;
 top: 0;
 width: 100vw;
}

.card-text{
    font-size: .5rem;
}
.reset .card-title{
    text-align: left;
}
.reset .card-body {
    text-align: left;
}
.reset .form-email
{
    padding-top: 3rem;
}
.card-header
{
    background-color: white;
    display: flex;
    justify-content: space-between;
    font-size: 1.2rem;
    padding: .5rem 1.1rem;
}
.reset .card {
    height: 100vh;
}
</style>
