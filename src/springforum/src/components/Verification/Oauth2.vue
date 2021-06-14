<template>
  <div>
    <h2>Redirecting..........</h2>
      <div>{{ getUserPayload.userNum }}</div>
   </div>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'
export default {
  name: 'redirect',
  data () {
    return {
    }
  },
  methods: {
    ...mapActions([
      'loadOauth'
    ])
  },
  computed: {
    ...mapGetters([
      'getUserPayload'
    ])
  },
  beforeRouteEnter (to, from, next) {
    next(vm => {
      vm.loadOauth()
    })
  },
  mounted () {
    setTimeout(() => { this.$router.push({ name: 'home' }) }, 4000)
  }
}
</script>
