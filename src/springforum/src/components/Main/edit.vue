<template>
  <div class="EditContainer">
     <b-badge pill variant="info">Your article is saved to below</b-badge>
  </div>
</template>

<script>
import { mapActions } from 'vuex'
export default {
  name: 'edit',
  data () {
    return {
      title: 'Trend',
      msg: ''
    }
  },
  methods: {
    ...mapActions([
      'changeTag'
    ]),
    changeTitle (n) {
      console.log('works')
      this.changeTag(n)
    }
  }
}
</script>

<style>
.EditContainer
{
  height: 70vh;
}
</style>
