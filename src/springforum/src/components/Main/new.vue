<template>
  <div class="NewContainer">
      <div>
          <b-form @submit.stop.prevent>
    <label for="text-password">Write your title here</label>
    <b-form-input type="text" id="text-password" v-model="title" aria-describedby="password-help-block"></b-form-input>
    <b-form-text id="password-help-block">
     Feel free to add your post title here below.
    </b-form-text>
   </b-form>
       <b-form-textarea debounce="100"
      id="textarea"
      v-model="text"
      placeholder="Enter something..."
      rows="3"
      max-rows="6"
    ></b-form-textarea>
    <div class="image-group">
    <div>
      <input class="file" type="file" ref="file" @change="onFileChanage()" id="file" accept="image/*">
      <label class="lable-file file1" for="file"><i class="fas fa-images"></i></label>
      <div class="uploadImg">img</div>
    </div>
      <div class="tags">
        <b-form-select v-model="selected" @change="addTags()" :options="options" size="sm" class="mt-3"></b-form-select>
       <div class="tag"><b-badge @click="deleteTag(index)"  v-for="(os, index) in optionlist" :key="index"
        :variant="os.tagColor">{{ os.tagName }}&nbsp;</b-badge></div>
   </div>
   <div>
      <i @click="removeImage" class="fas fa-trash"></i>
   </div>
  </div>
      </div>
        <b-card :header="title">
      <blockquote class="blockquote mb-0">
        <p class="text">{{text}}</p>
        <footer class="blockquote-footer">
          Your article will show here! <cite :title="user">{{this.$store.state.user.username}}</cite>
        </footer>
      </blockquote>
    </b-card>
    <div class="handlerButton">
      <b-button squared @click="save()" variant="info">SAVE</b-button>
       <b-button squared variant="primary">SUBMIT</b-button>
    </div>
    <div class="bg-image">
      <img v-if="image" :src="image">
    </div>
  </div>
</template>
<script>
import axios from 'axios'
export default {
  name: 'new',
  data () {
    return {
      text: '',
      title: '',
      user: 'Kuke',
      file: null,
      image: '',
      selected: null,
      options: [
        { value: null, text: 'Tag', disabled: true },
        { value: { tagName: 'Ruby', tagColor: 'danger' }, text: 'Ruby' },
        { value: { tagName: 'Javascript', tagColor: 'warning' }, text: 'JS' },
        { value: { tagName: 'Vue', tagColor: 'success' }, text: 'Vue' },
        { value: { tagName: 'Python', tagColor: 'info' }, text: 'Python' }
      ],
      optionlist: []
    }
  },
  methods: {
    test () {
      const formData = new FormData()
      formData.append('file', this.file, this.file.name)
      console.log(formData)
      var message = {
        articleTitle: this.title,
        articleContent: this.text,
        tags: this.optionlist
      }
      console.log(message)
    },
    save () {
      console.log(this.optionlist)
      console.log(this.optionlist[0])
      // console.log(this.optionlist[0].tagColor)
      // console.log(this.optionlist[0].tagName)
      var dataBag = []
      if (this.optionlist.length === 0) {
        dataBag = [{ tagName: 'undefined', tagColor: 'secondary' }]
      } else {
        dataBag = this.optionlist
      }
      var message = {
        articleTitle: this.title,
        articleContent: this.text,
        tags: dataBag
      }
      var _that = this
      axios.post('http://localhost:8080/api/article/new?num=' + localStorage.userNum, message, {
        headers: {
          Authorization: localStorage.token
        }
      }).then(res => {
        console.log(res)
        _that.title = ''
        _that.text = ''
        _that.optionlist = []
        _that.onUpload(res.data)
      }).catch(err => {
        console.log(err)
      })
    },
    onFileChanage (e) {
      var files = this.$refs.file.files
      if (!files.length) {
        return
      }
      this.file = files[0]
      this.createImage(files[0])
    },
    onUpload (id) {
      console.log('upload is working')
      const formData = new FormData()
      console.log(this.file)
      formData.append('file', this.file, this.file.name)
      var _that = this
      axios({
        method: 'post',
        url: 'http://localhost:8080/api/article/upload?id=' + id,
        data: formData,
        headers: {
          'Content-Type': 'multipart/form-data',
          Authorization: localStorage.token
        }
      }).then(() => {
        _that.image = ''
        console.log('Success!')
        if (localStorage.userType !== 2) {
          this.$store.dispatch('getUsers')
        } else {
          this.$store.dispatch('getUserByNum')
        }
      }
      ).catch(() => console.log('Failure!'))
      _that.file = ''
    },
    addTags () {
      if (this.optionlist.length < 3) {
        this.optionlist.push(this.selected)
      }
    },
    deleteTag (index) {
      this.optionlist.splice(index, 1)
    },
    createImage (file) {
      // var image = new Image()
      var reader = new FileReader()
      var vm = this

      reader.onload = (e) => {
        vm.image = e.target.result
      }
      reader.readAsDataURL(file)
    },
    removeImage: function (e) {
      this.image = ''
    }

  }
}
</script>

<style>
.NewContainer
{
   height: 100%;
}
.NewContainer .text
{
  font-size: 1rem;
  text-align: left;
  text-indent: 1.1rem;
}
.NewContainer .file[type="file"]{
  display: none;
}
.fas.fa-images{
  font-size: 30px;
  padding-top: 6px;
}
.NewContainer .card-body
{
  position: relative;
  padding-bottom: 0;
}
.blockquote
{
  padding-bottom: 10px;
}
.handlerButton
{
  padding-top: 1rem;
  display: flex;
  justify-content: space-around;
  padding-bottom: 1.5rem;
}
.image-group
{
  display: flex;
  justify-content: space-between;
  position: relative;
}
.lable-file
{
  color :white;
  background: #f5af09;
  width: 2.5rem;
  height: 2.5rem;
}
.file1{
  margin-left: 10px;
  margin-bottom: 0;
}
.file2{
  margin-right: 10px;
}
.uploadBg{
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 6rem;
}
.uploadBg p{
  font-size: .1rem;
  margin: 0;
}
.uploadImg
{
  font-size:.5rem ;
}
.NewContainer .card-header{
   margin: auto;
   background-color: #fff;
   border: none;
   padding-bottom: 0.1rem;
}
.NewContainer .card-body{
  padding-top: 0;
}
.bg-image img {
  width: 70%;
  height: 200px;
}
.fa-trash
{
  padding-top: .8rem;
  padding-right: .5rem;
}
.image-group select
{
   width: 4rem;
}
.tags
{
   position: absolute;
   left: 4rem;
   top: -.5rem;
   display: flex;
}
.image-group select option{
  font-size: 1rem;
  width: 10px;
}
.tags .tag
{
  padding-top: 1.2rem;
}
select::-ms-expand {
    display: none;
}
select {
  /* for Firefox */
  -moz-appearance: none;
  /* for Chrome */
  -webkit-appearance: none !important;
}
</style>
