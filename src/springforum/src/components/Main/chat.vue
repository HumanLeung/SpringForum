<template>
  <div class="ChatContainer">
    <div ref="ele" class="contents">
    <div v-for="(message, index) in receives" :key="index" :class="(message.userId === userId ? 'my-msg-panel' : '')" class="msg-panel">
      <div class="user">
       <div class="userdetail"  :class="(message.userId === userId ? 'mydetails' : '')">
       <b-avatar :src="(message.userId === userId ? avatar : '')" :variant="(message.userId === userId ? 'success' : 'primary')" :text="message.sender.substring(0,1)"></b-avatar>
      <div class="username">
          <p>{{ message.sender }}</p>
      </div>
       </div>
     </div>
     <p class="text-msg">{{message.content}}</p>
     </div>
  </div>
       <div class="btn-input">
        <b-form-input
          v-model="contents"
          placeholder="Enter name"
        ></b-form-input>
        <b-button @click="send()" class="btn1" variant="primary">Submit</b-button>
     </div>
</div>
</template>
<script>
// import axios from 'axios'
import SockJS from 'sockjs-client'
import Stomp from 'webstomp-client'
import { mapGetters } from 'vuex'
export default {
  name: 'chat',
  data () {
    return {
      stompClient: null,
      sender: 'human',
      contents: '',
      path: '',
      receives: [],
      userId: '',
      roomNum: 0,
      avatar: ''
      // roomNum: this.$params.roomNum this is not gonna works
    }
  },
  methods: {
    ...mapGetters([
      'getIndex',
      'getUserPayload'
    ]),
    connect () {
      console.log(this.receives)
      console.log(this.contents)
      const socket = new SockJS('http://localhost:8080/webchat')
      this.stompClient = Stomp.over(socket)
      this.stompClient.connect({}, this.onConnection)
    },
    onConnection (frame) {
      this.roomNum = this.$route.params.roomNum
      var num = this.$route.params.roomNum
      console.log(num)
      console.log('Connected: ' + frame)
      this.stompClient.subscribe('/topic/public/' + num, this.onMessageReceived)
    },
    onMessageReceived (payload) {
      console.log(this)
      const message = JSON.parse(payload.body)
      this.receives.push(message)
      console.log(message.content + '  this is message')
    },
    send () {
      // this.userId = this.getUserId()
      console.log(this.getIndex())
      console.log(this.getUserPayload())
      const message = {
        sender: this.sender,
        content: this.contents,
        roomNum: this.roomNum,
        userId: this.userId
      }
      console.log(message)
      this.contents = ''
      this.stompClient.send('/app/chat/' + this.roomNum, JSON.stringify(message), {})
    },
    disconnect () {
      if (this.stompClient !== null) {
        this.stompClient.disconnect()
        console.log('disconnected')
      }
    }
  },
  beforeRouteEnter (to, from, next) {
    next(vm => {
      vm.sender = vm.getUserPayload().username
      vm.userId = vm.getUserPayload().userNum
      vm.avatar = vm.getUserPayload().avatar
      vm.connect()
    })
  },
  beforeRouteLeave (to, from, next) {
    console.log('log')
    if (this.stompClient === null) {
      next(true)
    } else {
      this.disconnect()
      next(true)
    }
  }
}
</script>

<style scoped>
.ChatContainer
{
    background: #edf3f9;
    position: relative;
    height: 80vh;
    border: 1px black solid;
}
.ChatContainer .contents
{
    height: 73vh;
    overflow: auto;
    padding-bottom: .5rem;
}
.ChatContainer .btn-input
{
  height: 6vh;
  background-color: transparent;
  display: flex;
}
.btn-input input
{
  height: 2.5rem;
}
.btn-input .btn1
{
  height: 2.5rem;
}
.msg-panel
{
  background-color: transparent;
  padding-bottom: .2rem;
  display: flex;
  flex-direction: column;
  margin-top: .2rem;
}
.user
{
   background: transparent;
   height: 2.5rem;
}
.user .username
{
    text-align: end;
    height: 2rem;
    padding-top: 1rem;
    padding-left: .6rem;
}
.user .username p{
    font-size: 1rem;
    margin-bottom: 0 !important;
}
.user .userdetail
{
    display: flex;
    padding-bottom: .4rem;
}
.msg-me .user .userdetail
{
   float: right;
}
.my-msg
{
  background-color: #00a5ff;
  color: #fff;
}
.text-msg
{
  word-wrap: break-word;
  background-color: #fff;
  border-radius: 1rem;
  display: inline-block;
  font-size: 1.3rem;
  text-align: left;
  padding-left: .5rem;
  margin-left: 1rem;
  margin-right: 1rem;
  padding-top: .2rem;
  padding-bottom: .2rem;
  padding-right: .5rem;
  margin-bottom: 0;
}
.mytext
{
  padding-right: .6rem;
  background: #00a5ff;
  color: #fff;
}
.my-msg-panel
{
  justify-content: right;
}
.mydetails
{
  float: right;
}
</style>
