<template>
    <div class="Focontainer">
        <ul>
        <router-link to="/main/trend"><li><i @click="changeTitle('Trend')" class="fas fa-poll"></i></li></router-link>
        <router-link to="/main"><li><i @click="changeTitle('Home')" class="fas fa-home"></i></li></router-link>
        <router-link to="/main/new"><li><i @click="changeTitle('New Topic')" class="fas fa-plus-circle"></i></li></router-link>
        <router-link to="/main/edit"><li><i @click="changeTitle('Edit')" class="fas fa-edit"></i></li></router-link>
        <router-link to="/main/discuss"><li><i @click="changeTitle('Disscus')" class="fas fa-comments"></i></li></router-link>
        </ul>
    </div>
</template>

<script>
import { mapActions } from 'vuex'
export default {
  data () {
    return {
      title: 'Trend'
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
<style scoped>
.Focontainer ul, li
{
    margin: 0;
    padding: 0;
}
.Focontainer
{
    position: fixed;
    height: 10vh;
    border: blue 1px solid;
    background-color: black;
    width: 100vw;
    bottom: 0;
}
.Focontainer ul
{
    padding-top: .6rem;
    display: flex;
    justify-content: space-between;
}
.Focontainer ul li
{
    list-style: none;
}
.Focontainer ul li i
{
    font-size: 2.5rem;
    color: white;
}
.fas.fa-poll
{
    padding-left: 1rem;
}
.fas.fa-comments
{
    padding-right: 1rem;
}
</style>
