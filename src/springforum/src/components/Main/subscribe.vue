<template>
  <div class="ChContainer">
     <div class="card-group">
       <div v-for="(mark, index) in getMarks" :key="index" class="Card">
         <b-card bg-variant="light" :title="mark.articleTitle">
           <hr>
           <div class="card-content">
              <div class="user-info">
                <b-img class="img" rounded="circle" left :src="mark.avatar" alt="Left image"></b-img>
                 <div class="username">{{ mark.username }}</div>
              </div>
                   <div @click="goPaper(mark.articleId, mark.userId); changeTitle('Description')" class="card-text">
                {{ mark.articleContent.substring(0, 70) }}
              </div>
           </div>
           <div class="subcontent">
                <div class="badge-group">
                <b-badge v-for="(tag, index) in mark.tags" :key="index" :variant="tag.tagColor">&nbsp;{{ tag.tagName }}</b-badge>&nbsp;
              </div>
              <div class="comments">
                <i class="fas fa-comment-dots"></i>
                <div class="count">{{ mark.commentsCount }} comments</div>
              </div>
       </div>
  </b-card>
</div>
     </div>
  </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
export default {
  props: ['marks'],
  data () {
    return {
      userNum: ''
    }
  },
  methods: {
    ...mapActions([
      'changeTag'
    ]),
    goPaper (aid, uid) {
      this.$router.push({ path: `/main/paper/${aid}/${uid}` })
    },
    changeTitle (n) {
      console.log('works')
      this.changeTag(n)
    },
    ...mapGetters([
      'getUserPayload'
    ])
  },
  computed: {
    ...mapGetters([
      'getMarks'
    ])
  },
  beforeRouteEnter (to, from, next) {
    next(vm => {
      vm.userNum = localStorage.userNum
      console.log(vm.userNum)
    })
  }
}
</script>

<style>
p{
  padding: 0;
  margin: 0;
}
.ChContainer.Card
{
  margin-bottom: 5px;
  padding: 1px;
  z-index: 0;
  border-radius: 20px  !important;
}
.ChContainer .Card .card-body
{
  z-index: 0;
  position: relative;
  padding: .2rem;
  border-radius: 30px;
}
.ChContainer .img
{
  width: 4rem;
  height: 4rem;
}

.ChContainer .card-title
{
   position: absolute;
   left: 10px;
   top: 0;
   margin: 0;
   font-size: 1rem;
}
.ChContainer .card-text
{
  text-align: left;
  flex: 2;
  width: 120px;
  text-indent: 2rem;
  padding-left: 5px;
  height: 6rem;
  flex: 85%;
  padding-left: 3rem;
}
.ChContainer .card-content
{
  display: flex;
  justify-content: space-between;
  width: 100%;
}
.ChContainer .badge-group .badge
{
  font-size: .5rem;
  margin-right: .2rem;
}
.ChContainer .badge-group
{
  display: flex;
  padding-top: .7rem;
}
.subcontent
{
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  position: relative;
}
.comments
{
  align-self: center;
  padding-bottom: .3rem;
}
.comments .fa-comment-dots
{
     font-size: 1rem;
}
.count
{
  font-size: .1rem;
  margin: 0;
  padding: 0;
  position: absolute;
  top: 16.5px;
  right: 4px;
  color: grey;
  padding-bottom: .2rem;
}
.user-info
{
  display: flex;
  flex-direction: column;
  align-items: center;
  flex: 5%;
}
.img div
{
  word-wrap: break-word;
}
.user-info .username
{
  width: 6rem;
}
</style>
