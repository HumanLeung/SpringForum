<template>
<div class="HomeContainer">
  <div class="btn-group">
    <b-button @click="active = true"  variant="warning">My mark</b-button>
  <b-button @click="active = false"  variant="info">My Own</b-button>
  </div>
  <Subscribe :marks="marks" v-if="active"/>
  <MyArticle v-else/>
</div>
</template>
<script>
import MyArticle from '@/components/Main/Myarticle.vue'
import Subscribe from '@/components/Main/subscribe.vue'
import { mapGetters } from 'vuex'
import axios from 'axios'
export default {
  name: 'home',
  components: {
    MyArticle,
    Subscribe
  },
  methods: {
    init () {
      console.log(this.$route)
    },
    ...mapGetters([
      'getUserPayload'
    ])
  },
  beforeRouteEnter (to, from, next) {
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
    console.log(headerInfo)
    next(vm => {
      vm.$store.dispatch('getUsers')
      vm.userNum = localStorage.userNum
      axios.get('http://localhost:8080/api/article/marks/' + vm.userNum,
        {
          headers: headerInfo
        })
        .then(res => {
          console.log(res.data)
          vm.$store.dispatch('loadMarks', res.data)
          vm.marks = res.data
        })
    })
  },
  data () {
    return {
      active: true,
      userNum: '',
      marks: []
    }
  },
  watch: {
    '$route' (to, from) {
      this.init()
    }
  },
  mounted () {
    this.init()
  }
}
</script>

<style>
.HomeContainer .btn
{
  height: 1.3rem;
  font-size: .2rem;
  margin: 0;
  padding: 0;
  border-radius: 6px;
}
.btn-group
{
  width: 13rem;
}
</style>
