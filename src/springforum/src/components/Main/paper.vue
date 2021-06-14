<template>
  <div class="blogContainer">
    <div @click="forwardChat(paper.room)" class="chat"><i class="fas fa-comment-dots"></i></div>
    <b-card-group columns>
    <b-card v-if="paper.articleTitle"
      :title="paper.articleTitle"
      :img-src="paper.articleBgi"
      img-alt="Image"
      img-top
      img-height="300"
    >
    <template slot="header">
      <div class="bloginfo">
        <div class="userinfo">Edited by {{ paper.username }}</div>
        <div class="sub">
        <b-badge ref="sub" @click="sub()" pill :class="{ hide : status }" variant="danger">mark</b-badge>
        <b-badge ref="unsub" @click="sub()" :class="{ hide : !status }"  pill variant="qweqw">unmark</b-badge>
        </div>
      </div>
    </template>
      <b-card-text>
     {{ paper.articleContent}}
      </b-card-text>
    </b-card>
    </b-card-group>
    <div class="commentBox">
    <b-list-group style="max-width: 500px;">
      <b-list-group-item class="d-flex align-items-center">
        <b-avatar class="mr-3"></b-avatar>
        <span class="mr-auto">J. Circlehead</span>
        <b-badge>5</b-badge>
      </b-list-group-item>
      <b-list-group-item class="d-flex align-items-center">
        <b-avatar variant="primary" text="BV" class="mr-3"></b-avatar>
        <span class="mr-auto">BootstrapVue</span>
        <b-badge>12</b-badge>
      </b-list-group-item>
      <b-list-group-item class="d-flex align-items-center">
        <b-avatar variant="info" src="https://placekitten.com/300/300" class="mr-3"></b-avatar>
        <span class="mr-auto">Super Kitty</span>
        <b-badge>9</b-badge>
      </b-list-group-item>
      <b-list-group-item class="d-flex align-items-center">
        <b-avatar variant="success" icon="people-fill" class="mr-3"></b-avatar>
        <span class="mr-auto">ACME group</span>
        <b-badge>7</b-badge>
      </b-list-group-item>
        <div class="commentHere">
         feel free to leave your comments below
        <b-form-textarea
        id="textarea-small"
        size="sm"
        v-model="text"
        placeholder="Small textarea"
      ></b-form-textarea>
  <b-button class="button" @click="submit()"  squared variant="primary">Submit</b-button>
  <b-button class="button" @click="textClear()" squared variant="outline-secondary">Clear</b-button>
      </div>
    </b-list-group>
    </div>
  </div>
</template>
<script>
import axios from 'axios'
import { mapActions, mapGetters } from 'vuex'
export default {
  name: 'paper',
  data () {
    return {
      status: false,
      roomNum: 0,
      test: 'test',
      index: 0,
      paper: null,
      text: ''
    }
  },
  methods: {
    ...mapActions([
      'sendUserId'
    ]),
    textClear () {
      this.text = ''
    },
    ...mapGetters([
      'getTrendIndex',
      'getAllArt',
      'getMarks'
    ]),
    submit () {
      console.log(this.paper)
      var message = {
        userNum: this.paper.userNum,
        comments: this.text
      }
      var _that = this
      var headerInfo = {}
      if (localStorage.userType === '2') {
        headerInfo = {
          Oauth2: 'oauth2 ' + localStorage.userNum
        }
      } else {
        headerInfo = {
          Authorization: localStorage.token
        }
      }
      axios.post('http://localhost:8080/api/comment/add/' + this.paper.articleId, message,
        {
          headers: headerInfo
        })
        .then(res => {
          console.log(res)
          _that.text = ''
        })
    },
    sub () {
      var _that = this
      axios.post('http://localhost:8080/api/article/mark/' +
       this.$route.params.aid + '/' + localStorage.uid,
      {
        headers: {
          Authorization: localStorage.token
        }
      }
      )
        .then(res => {
          if (res.data === 'mark') {
            _that.status = true
          } else {
            _that.status = false
          }
        })
    },
    forwardChat (room) {
      var _that = this
      var userNum = this.$store.state.user.userNum
      axios.post('http://localhost:8080/api/article/room/' + room.roomId + '/' + userNum,
        {
          headers: {
            Authorization: localStorage.token
          }
        })
        .then(res => {
          console.log(res)
          _that.$router.push({ path: `/main/chat/${room.roomNum}` })
        })
    },
    getPaper () {
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
      var _that = this
      console.log(this.$route.params.aid)
      axios.get('http://localhost:8080/api/article/getArticles/paper/' + this.$route.params.aid, {
        headers: headerInfo
      })
        .then(res => {
          _that.paper = res.data
          console.log(res.data)
        })
    },
    checkMarks () {
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
      var _that = this
      axios.get(`http://localhost:8080/api/article/checkMark/${this.$route.params.aid}/${localStorage.uid}`,
        {
          headers: headerInfo
        })
        .then(res => {
          if (res.data === 'OK') {
            _that.status = true
          } else {
            _that.status = false
          }
          console.log(res)
        }).catch(err => {
          console.log(err)
        })
    }
  },
  beforeRouteEnter (to, from, next) {
    next(vm => {
      vm.index = localStorage.paperindex
      vm.getPaper()
      vm.checkMarks()
      next(vm)
    })
  }
}
</script>
<style scoped>
.blogContainer
{
  height: 79.5vh;
  background-color: #fff;
  overflow: auto;
}
.blogContainer.card-columns{
  height: 80%;
}
.blogContainer .card-text
{
  font-size: 1rem;
  text-align:left;
  width: 100%;
  text-indent: 3rem;
}
.blogContainer .card
{
  margin-top: -.3rem;
  border: none;
}
.blogContainer .card-body
{
  padding-top: 0;
  border: none;
}
.blogContainer .card-header
{
  padding-top: .1rem;
  padding-bottom: .2rem;
  background-color: #fff;
  border: none;
  width: 100%;
  padding-bottom: .7rem;
}
.blogContainer .card-header .bloginfo
{
  display: flex;
  width: 100%;
  justify-content: space-between;
  margin: auto;
}
.blogContainer .userinfo
{
  font-size: .5rem;
}
.blogContainer .sub
{
  font-size: .7rem;
}
.blogContainer .commentBox p {
  font-size: 1rem;
}
.blogContainer .list-group .list-group-item{
   border: none;
}
.blogContainer .chat {
  position: fixed;
   color: #0275d8;
   z-index: 20;
   bottom: 3.6rem;
   right: .5rem;
   font-size: 4.5rem;
}
.list-group {
  height: 20vh;
}
.commentHere
{
  height: 8vh;
  display: inline-block;
  width: 100%;
}
#textarea-small
{
  height: 100%;
  display: inline-block;
}
button {
  width: 50% !important;
}
.hide
{
  display: none !important;
}
</style>
