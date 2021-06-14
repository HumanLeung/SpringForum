<template>
  <div class="profile">
  <b-card-group deck>
    <b-card img-src="https://picsum.photos/300/300/?image=41" img-alt="Image" img-top>
    <template slot="header">
      <span>Account:</span>
      <span>{{ getUserPayload.userNum }}</span>
    </template>
    <b-avatar variant="info" :src="getUserPayload.avatar" class="profile-img"></b-avatar>
    <input class="file" ref="bgf" @change="handleFileUpload()" type="file" id="file" accept="image/*">
      <label class="lable-file file2" for="file"><i class="fas fa-camera"></i></label>
     <b-form  v-if="show">
      <div class="info-1">
        <b-form-group
        id="input-group-1"
        label-for="input-1"
      >
        <b-form-input
          id="input-1"
          v-model="form.name"
          placeholder="Username"
          required
        ></b-form-input>
      </b-form-group>
      <div class="info-show">{{ getUserPayload.username }}</div>
      </div>
            <div class="info-1">
        <b-form-group
        id="input-group-1"
        label-for="input-1"
      >
        <b-form-input
          id="input-1"
          v-model="form.phone"
          type="email"
          placeholder="phone number"
          required
        ></b-form-input>
      </b-form-group>
      <div class="info-show">{{ getUserPayload.phoneNum }}</div>
      </div>
      </b-form>
      <template #footer>
        <b-button class="btn-red" @click="main()" variant="danger">cancel</b-button>
        <b-button variant="primary" @click="save()">save</b-button>
      </template>
    </b-card>
  </b-card-group>
</div>

</template>

<script>
import axios from 'axios'
import { mapGetters } from 'vuex'
export default {
  name: 'profile',
  data () {
    return {
      show: true,
      form: {
        email: '',
        name: '',
        phone: ''
      },
      file: null
    }
  },
  methods: {
    main () {
      this.$router.push({ name: 'home' })
    },
    handleFileUpload () {
      console.log(this.$refs.bgf.files[0])
      this.file = this.$refs.bgf.files[0]
    },
    onUpload () {
      const formData = new FormData()
      formData.append('file', this.file, this.file.name)
      var _that = this
      axios({
        method: 'post',
        url: 'http://localhost:8080/api/user/upload?num=' + this.$store.state.user.userNum,
        data: formData,
        headers: {
          'Content-Type': 'multipart/form-data',
          Authorization: localStorage.token
        }
      }).then(() => console.log('Success!')).catch(() => console.log('Failure!'))
      _that.file = ''
    },
    save () {
      var message = {
        email: this.form.email,
        username: this.form.name,
        phoneNum: this.form.phone
      }
      var _that = this
      axios.put('http://localhost:8080/api/user/update/' + this.$store.state.user.userNum, message, {
        headers: {
          Authorization: localStorage.token
        }
      }).then(res => {
        _that.$store.dispatch('getUsers')
        _that.onUpload()
        console.log(res)
      }).catch(err => {
        console.log(err)
      })
    }
  },
  computed: {
    ...mapGetters([
      'getUserPayload'
    ])
  }
}
</script>

<style scoped>
.card img
{
  height: 15rem;
}
.card-img-top
{
    position: relative;
}
.profile-img
{
    position: absolute;
    margin: auto;
    left: 40%;
    top: 10%;
    width: 5rem;
    height: 5rem;
}
.card
{
    height: 79vh;
    position: fixed;
    width: 100vw;
}
.card .card-body
{
    padding-top: 0;
}
.profile form
{
    width: 100%;
}
.profile form input
{
    outline: none;
    border: none;
    border-bottom: 1px solid black;
    width: 75%;
}
.profile .info-1
{
    display: flex;
    height: 3.5rem;
    justify-content: space-around;
}
.profile .info-show
{
    padding-top: 1rem;
    font-size: .5rem;
    word-wrap: break-word;
    width: 40%;
}
.profile .fa-camera
{
    color: turquoise;
}
.file
{
    display: none;
}
.file2
{
    background: transparent;
    position: absolute;
    top: 6rem;
    margin: auto;
}
.b-avatar-img
{
    position: relative;
}
.card-footer
{
    padding: 0;
    display: flex;
    justify-content: space-around;
}
.card-header
{
  justify-content: space-around;
  background-color: #fff;
  padding: 0;
  padding-bottom: .3rem;
}
</style>
