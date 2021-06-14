<template>
  <div class="DiscussContainer">
    <div class="Bar-group">
          <b-list-group>
      <b-list-group-item @click="forwardChat(re.roomNum)"  v-for="(re, index) in getUserPayload.rooms" :key="index" class="d-flex align-items-center">
        <b-avatar variant="success" icon="people-fill" class="mr-3"></b-avatar>
        <span class="mr-auto">No. {{ re.roomNum }}</span>
        <b-badge>You have joined</b-badge>
      </b-list-group-item>
    </b-list-group>
    </div>
  </div>
</template>

<script>
// import axios from 'axios'
import { mapGetters } from 'vuex'
export default {
  name: 'discuss',
  data () {
    return {
    }
  },
  methods: {
    forwardChat (room) {
      this.$router.push({ path: `/main/chat/${room}` })
    }
  },
  computed: {
    ...mapGetters([
      'getUserPayload'
    ])
  }
}
</script>

<style>
</style>
