<template>
  <div class="resultContainer">
      <b-card border-variant="light" v-for="(result, index) in getSearch" :key="index" :header="result.articleTitle"
       class="text-center">
        <b-card-text>Lorem ipsum dolor sit amet, consectetur adipiscing elit.</b-card-text>
      </b-card>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
export default {
  name: 'result',
  data () {
    return {
      results: []
    }
  },
  computed: {
    ...mapGetters([
      'getSearch'
    ])
  },
  methods: {
    // search () {
    //   this.results = []
    //   console.log(this.$route.params.item)
    //   var _that = this
    //   axios.get(`http://localhost:8080/api/article/search/${this.$route.params.item}`).then(res => {
    //     console.log(res.data)
    //     _that.results = res.data
    //   })
    // }
  }
  // mounted () {
  //   this.search()
  // },
  // beforeRouteEnter (to, from, next) {
  //   next(vm => {
  //     vm.search()
  //   })
  // }
}
</script>

<style scoped>
.resultContainer
{
    height: 79vh;
}
.card
{
    padding-top: .2rem;
}
.card-header {
    padding: 0;
}

</style>
