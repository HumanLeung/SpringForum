<template>
  <div class="NavContainer">
       <SideBar/>
    <nav>
      <div class="menu"><b-button  variant="none" v-b-toggle.sidebar-no-header><i class="fas fa-bars"></i>
      </b-button></div>
      <div class="brand">{{ this.$route.name }}</div>
      <div @click="active = true" class="search"><i class="fas fa-search"></i></div>
     </nav>
    <div v-if="active" class="search-panel">
      <input v-model="text" class="input" type="text" placeholder="Search heare">
      <div @click="active = false">
           <i  class="fas fa-times-circle"></i>
      </div>
        <div class="result">
          <b-button @click="search()" variant="outline-dark">Search</b-button>
        </div>
    </div>
</div>
</template>

<script>
import { mapGetters } from 'vuex'
import SideBar from '@/components/Main/sidebar.vue'
export default {
  data () {
    return {
      active: false,
      text: ''
    }
  },
  computed: {
    ...mapGetters([
      'getTitle'
    ])
  },
  methods: {
    search () {
      this.active = false
      this.$store.dispatch('search', this.text)
      this.$router.push({ path: `/main/result/${this.text}` })
    }
  },
  components: {
    SideBar
  }
}
</script>

<style>
.NavContainer
{
  position: fixed;
  width: 100vw;
  height: 4rem;
  border: black 1px solid;
  background-color: black;
  z-index: 10;
}
.NavContainer nav{
  display: flex;
  justify-content: space-between;
  width: 99%;
  padding-top: 10px;
  align-items: center;
  margin: 0;
}
.NavContainer nav .brand
{
  color: white;
  font-size: 1.5rem;
}
nav i{
  color: white;
  font-size: 1.5rem;
}
.link a{
  color: white;
}
.search-panel
{
  width: 100vw;
  height: 100vh;
  opacity: 80%;
  background-color: black;
  position: relative;
  z-index: 20;
}
.search-panel input
{
  border-bottom: 1px white solid;
  border: none;
  background-color: transparent;
  outline: none;
  position: absolute;
  top: 4rem;
  left: 50%;
   transform: translate(-50%);
}
.search-panel input::placeholder
{
  color: #fff;
}

.search-panel input[type=text]
{
   border-bottom: 1px white solid;
   font-size: 20px;
   color: white;
}
.fas.fa-times-circle
{
  font-size: 2rem;
  color: #fff;
  z-index: 10;
}
.menu{
 display: flex;
 justify-content: space-between;
}
.menu .fas.fa-bell{
  padding-left: .4rem;
}
.search-panel .result
{
  color: #fff;
  font-size: 1.5rem;
  position: absolute;
  top: 6.5rem;
  right: 10rem;
}
.result p {
  font-size: 1.5rem;
}
</style>
