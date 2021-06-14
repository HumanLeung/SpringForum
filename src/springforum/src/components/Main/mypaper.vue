<template>
    <div class="blogContainer">
    <div @click="forwardChat()" class="chat"><i class="fas fa-comment-dots"></i></div>
    <b-card-group columns>
    <b-card
      :title="getUserPayload.articles[index].articleTitle"
      :img-src="getUserPayload.articles[index].articleBgi"
      img-alt="Image"
      img-top
      img-height="300"
    >
    <template slot="header">
      <div class="bloginfo">
        <div class="userinfo">Edited by {{ getUserPayload.username }}</div>
        <!-- <div class="sub">
        <b-badge ref="sub" @click="sub()" pill variant="danger">mark</b-badge>
        <b-badge ref="unsub" @click="sub()" style="display : none"  pill variant="qweqw">unmark</b-badge>
        </div> -->
      </div>
    </template>
      <b-card-text>
        {{getUserPayload.articles[index].articleContent}}
      </b-card-text>
    </b-card>
    </b-card-group>
     <b-button block variant="outline-secondary">Block Level Button</b-button>
  </div>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'
export default {
  name: 'mypaper',
  data () {
    return {
      index: 0,
      aid: ''
    }
  },
  methods: {
    ...mapGetters([
      'getIndex'
    ]),
    forwardChat () {
      var roomNum = this.getUserPayload.articles[this.index].room.roomNum
      this.sendUserId(this.getUserPayload.userNum)
      this.$router.push({ path: `/main/chat/${roomNum}` })
    },
    ...mapActions([
      'sendUserId'
    ])
  },
  computed: {
    ...mapGetters(
      ['getUserPayload']
    )
  },
  beforeRouteEnter (to, from, next) {
    next(vm => {
      vm.aid = vm.$route.params.aid
      vm.index = vm.getIndex()
    })
  },
  mounted () {
    console.log(this.index)
  }
}
</script>

<style scoped>
.blogContainer
{
  height: 80vh;
  background-color: #fff;
}
.card-columns{
   height: 93%;
}
.card-text
{
  font-size: 1rem;
  text-align:left;
  width: 100%;
  text-indent: 3rem;
  overflow: auto;
  padding: 1rem;
}
.card
{
  margin-top: -.3rem;
  border: none;
  height: 100%;
  overflow: auto;
}
.card-body
{
  padding-top: 0;
  border: none;
  padding-bottom: 0;
  height: 60vh;
}
.card-header
{
  padding-top: .1rem;
  padding-bottom: .2rem;
  background-color: #fff;
  border: none;
  width: 100%;
  padding-bottom: .7rem;
}
.bloginfo
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
   bottom: 5rem;
   right: .5rem;
   font-size: 4.5rem;
}
.button
{
    width: 100%;
}
</style>
