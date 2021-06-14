<template>
   <div class="password">
       <b-card-group deck>
     <b-card title="Create new password" header-tag="header">
         <template slot="header">
        <div><i @click="backward()" class="fas fa-long-arrow-alt-left"></i></div>
        <div><i class="far fa-question-circle"></i></div>
         </template>
       <b-card-text>
      Some quick example text to build on the <em>card title</em> and make up the bulk of the card's
      content.
    </b-card-text>
     <b-form @submit="onSubmit" @reset="onReset" v-if="show">
      <b-form-group :status=status  id="input-group-1" label-align="left"
        label="New Password:"
        label-for="input-1"
        description="We'll never share your email with anyone else."
      >
          <template slot="default">
            <div  ref="con2">
              <i @click="closeEye()" class="fas fa-eye-slash"></i>
              <i @click="closeEye()" class="fas fa-eye"></i>
            </div>
           </template>
        <b-form-input ref="con"
          id="input-1"
          v-model="form.password"
          type="password"
          placeholder="Password"
          required
        >
        </b-form-input>
      </b-form-group>

      <b-form-group id="input-group-2" label="Confirm password:" label-align="left"
       label-for="input-2">
        <b-form-input
          id="input-2"
          v-model="form.name"
          placeholder="Confirm Passowrd"
          required
        ></b-form-input>
      </b-form-group>
      </b-form>
      <b-button block @click="forwardSuccess()" variant="primary">Reset password</b-button>
  </b-card>
</b-card-group>
  </div>
</template>

<script>
export default {
  name: 'password',
  data () {
    return {
      show: true,
      status: true,
      entity1: 'password',
      entity2: 'name',
      eye: false,
      form: {
        password: ''
      }
    }
  },
  methods: {
    closeEye () {
      this.$refs.con2.children[0].style = 'display : none'
      this.$refs.con2.children[1].style = 'display : block'
      if (this.$refs.con.$refs.input.form[0].type === 'password') {
        this.$refs.con.$refs.input.form[0].type = 'name'
      } else {
        this.$refs.con.$refs.input.form[0].type = 'password'
        this.$refs.con2.children[1].style = 'display : none'
        this.$refs.con2.children[0].style = 'display : block'
      }
    },
    forwardSuccess () {
      this.$router.push({ path: 'success' })
    },
    backward () {
      this.$router.push({ path: 'checkemail' })
    }
  }
  // mounted () {
  //   console.log(this.$refs.con2.children[0])
  //   console.log(this.$refs.con2.children[1])
  // }
}
</script>

<style>
.password
{
    background-color: #fff;
    height: 100vh;
    position: absolute;
    width: 100vw;
    top: 0;

}
.password .card-title
{
    text-align: left;
}
.password .card-body .card-text
{
    text-align: left;
}
.password .card-header
{
    background-color: #fff;
}
.password .card-body
{
    height: 100vh;
}
.password .form-group
{
    position: relative;
}
.password .fas.fa-eye, .fas.fa-eye-slash
{
    position: absolute;
    right: .3rem;
    bottom: 2.1rem;
    font-size: 1.2rem;
}
</style>
