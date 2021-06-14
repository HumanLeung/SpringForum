<template>
  <div class="MyOwn">
           <b-card-group ref="delete" deck>
           <b-card  v-for="(arts, index) in getUserPayload.articles" :key="index" bg-variant="light"
            class="text-center">
            <b-button @click="cut(arts.articleId, index)"   variant="danger">&nbsp; Delete &nbsp;</b-button>
            <b-button @click="cancel(index)" class="cancel" variant="primary">&nbsp; Cancel &nbsp;</b-button>
               <i @click="remove(index)" class="fas fa-ellipsis-h"></i>
               <div  class="card-header">{{ arts.articleTitle }}</div>
        <b-card-text @click="mypaper(arts.articleId , index)">{{arts.articleContent.substring(0, 50)}}</b-card-text>
           </b-card>
      </b-card-group>
  </div>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'
import axios from 'axios'
export default {
  name: 'myarticle',
  data () {
    return {
      text: ''
    }
  },
  methods: {
    ...mapActions([
      'syncIndex'
    ]),
    mypaper (aid, index) {
      this.syncIndex(index)
      this.$router.push({ path: `/main/mypaper/${aid}` })
    },
    remove (index) {
      console.log(this.$refs.delete.children[index])
      this.$refs.delete.children[index].children[0].childNodes[2].style = 'display: block'
      this.$refs.delete.children[index].children[0].childNodes[3].style = 'display: block'
    },
    cancel (index) {
      this.$refs.delete.children[index].children[0].childNodes[2].style = 'display: none'
      this.$refs.delete.children[index].children[0].childNodes[3].style = 'display: none'
    },
    cut (aid, index) {
      console.log(aid)
      if (confirm('are you sure ?')) {
        axios.delete('http://localhost:8080/api/article/delete/' + aid,
          {
            headers: {
              Authorization: localStorage.token
            }
          })
          .then(res => {
            console.log(res)
            this.$refs.delete.children[index].remove()
          })
      }
    }
  },
  computed: {
    ...mapGetters(
      ['getUserPayload']
    )
  }
}
</script>

<style>
.MyOwn .card-text
{
   font-size: 1rem;
}
.MyOwn .card-deck
{
    padding-top: .7rem;
}
.MyOwn .card-header
{
    padding: .3rem;
    background:transparent;
    justify-content:center;
}
.MyOwn .card-body
{
  padding: .5rem;
}
.fa-ellipsis-h
{
  position: absolute;
  right: 1rem;
  font-size: .5rem;
}
.MyOwn .card-body button
{
  position: absolute;
  right: .7rem;
  top: 2rem;
  width: 3.5rem;
  height: 1.6rem;
  display: none;
}
.MyOwn .card-body .cancel
{
  position: absolute;
  right: .7rem;
  top: 4rem;
  width: 3.5rem;
  height: 1.6rem;
  display: none;
}
</style>
