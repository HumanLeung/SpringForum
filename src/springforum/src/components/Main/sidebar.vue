<template>
    <div class="SideBarTool">
    <b-sidebar  id="sidebar-no-header" class="BarParent" bg-variant="dark"
    aria-labelledby="sidebar-no-header-title" no-header shadow>
      <template #default="{ hide }">
          <b-list-group-item class=" nav-info align-items-center">
        <b-avatar variant="success" :src="getUserPayload.avatar" text="BV" class="mr-3"></b-avatar>
        <div class="mr-auto">{{ getUserPayload.username }}</div>
        <div class="mr-auto">No. {{ getUserPayload.userNum }}</div>
      </b-list-group-item>
        <div class="p-2">
            <i  @click="hide" class="fas fa-times"></i>
          <nav @click="hide" class="sidebar-nav">
            <b-nav vertical>
              <b-nav-item active @click="edit()">Edit Profile</b-nav-item>
              <b-nav-item href="#link-1" @click="hide">Share</b-nav-item>
              <b-nav-item href="#link-2" @click="hide">About us</b-nav-item>
            </b-nav>
          </nav>
          <b-button variant="primary" @click="logout()" class="SignOut" block>Sign Out</b-button>
        </div>
      </template>
    </b-sidebar>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
export default {
  name: 'sidebar',
  methods: {
    backword () {
      this.$router.push({ name: 'signin' })
    },
    edit () {
      this.$router.push({ name: 'profile' })
    },
    logout () {
      localStorage.removeItem('token')
      localStorage.clear()
      this.$store.dispatch('clear')
      this.backword()
    }
  },
  computed: {
    ...mapGetters([
      'getUserPayload'
    ])
  }
}
</script>

<style scoped>
.SideBarTool
{
    position: fixed;
}
.menu button
{
    padding: 0;
    margin: 0;
}
.b-sidebar-body h4
{
    text-align: left;
    color: whitesmoke;
    padding-top: 4rem;
}
#sidebar-no-header
{
    width: 76vw;
}
.SideBarTool .sidebar-nav
{
    margin: 0;
}
 .SideBarTool .sidebar-nav .nav-item a
 {
     padding: 0;
     padding-bottom: .7rem;
     text-align: left;
     font-size: 1.5rem;
     color: whitesmoke;
 }
 .BarParent
 {
     position: relative;
 }
.SignOut
 {
     position: absolute;
     bottom: 1rem;
     left: 1rem;
     width: 250px !important;
 }
 .SideBarTool .fas.fa-times
 {
     position: absolute;
     right: 1rem;
     color: whitesmoke;
     font-size: 2rem;
 }
 .SideBarTool .nav-info
 {
   width: 60vw;
   background-color: rgb(52, 58, 64);
    position: absolute;
   padding: 0;
   padding-top: .5rem;
   padding-right: 6rem;
 }
 .SideBarTool .mr-auto
 {
   color: whitesmoke;
 }
 .SideBarTool .sidebar-nav
 {
   padding-top: 8rem;
   padding-left: 1rem;
 }
</style>
