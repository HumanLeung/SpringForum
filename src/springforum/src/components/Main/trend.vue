<template>
<div class="trend-container">
 <PullTo :bottom-load-method="refresh">
   <template slot="bottom-block">loading...</template>
    <b-card-group deck>
     <b-card bg-variant="info"  v-for="(article ,index) in getAllArts" :key="index" text-variant="white"
      :header="article.articleTitle" class="text-center">
        <div  @click="paper(article.articleId, index, article.userId)" class="card-content">
          <div class="avatar">
            <b-avatar :src="article.avatar" size="3.5rem"></b-avatar>
          </div>
          <b-card-text  v-if="article.articleContent">{{ shortString(article.articleContent) }}</b-card-text>
        </div>
        <div class="card-footer">
          <div class="badget">
          <b-badge v-for="(tag, i) in article.tags" :key="i" :variant="tag.tagColor">{{ tag.tagName}}</b-badge>
          </div>
          <div class="visit">{{ article.lookCount }}  &nbsp;<i class="fas fa-eye"></i></div>
        </div>
      </b-card>
    </b-card-group>
 </PullTo>
</div>
</template>

<script>
import { mapGetters } from 'vuex'
import PullTo from 'vue-pull-to'
import axios from 'axios'
export default {
  name: 'trend',
  data () {
    return {
      count: 0,
      articleList: [],
      temp: 0
    }
  },
  components: {
    PullTo
  },
  methods: {
    refresh (loaded) {
      this.temp = this.count
      this.count = this.count + 1
      console.log(this.count)
      var _that = this
      console.log('refresh')
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
      axios.get('http://localhost:8080/api/article/getArticles/' + this.count, {
        headers: headerInfo
      }).then(res => {
        console.log(res.data)
        if (res.data.length > 0) {
          _that.$store.dispatch('updateArticle', res.data)
        } else {
          _that.count = _that.temp
        }
        loaded('done')
      }).catch(err => {
        console.log(err)
      })
    },
    shortString (target) {
      return target.substring(0, 50)
    },
    ...mapGetters([
      'getAllArts'
    ]),
    paper (aid, i, uid) {
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
      axios.put(`http://localhost:8080/api/article/updateCount/${aid}`,
        {
          headers: headerInfo
        })
        .then(res => {
          console.log(res)
          // _that.$store.dispatch('updateIndex', i)
          localStorage.paperindex = i
          _that.$router.push({ path: `/main/paper/${aid}/${uid}` })
        }).catch(err => {
          console.log(err)
        })
    }
  },
  mounted () {
    // do {
    //   console.log('work')
    // } while (localStorage.userNum === null)
    this.$store.dispatch('getArticles', 0)
    if (localStorage.token === null || localStorage.oauthID === null) {
      console.log(localStorage.token)
      this.$router.push({ name: 'signin' })
    }
    console.log('this')
  },
  computed: {
    ...mapGetters([
      'getAllArts'
    ])
  }
}
</script>

<style>
p{
  font-size: 3rem;
}
.trend-container
{
  width: 97vw;
  margin: auto;
}
.trend-container .card-title
{
  font-size: 1.2rem;
  text-indent: 1rem;
}
.trend-container .card-text{
  font-size: 1rem;
  text-align: center;
}
.trend-container .card-body
{
  padding: 0;
}
.trend-container .card-header
{
  padding: .2rem;
  height: 2.5rem;
  font-size: 1rem;
}
.trend-container .card-deck .card
{
  margin-bottom: 5px;
}
.trend-container .card-footer
{
  justify-content: space-between;
  display: flex;
  padding-bottom: 0;
}
.trend-container .card-content
{
  display: flex;
  justify-content: space-between;
}
.trend-container .fas.fa-eye{
  font-size: 1rem;
}
</style>
