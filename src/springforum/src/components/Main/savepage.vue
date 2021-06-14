<template>
  <div class="EditContainer">
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
        <input class="file" type="file" id="file" accept="image/*">
    <label class="lable-file file1" for="file"><i class="fas fa-images"></i></label>
 <div class="uploadBg">
      <div class="BgImage">
      <input class="file" type="file" id="file" accept="image/*">
      <label class="lable-file file2" for="file"><i class="fas fa-images"></i></label>
      </div>
      <p>change your BGI &nbsp;&nbsp; </p>
 </div>
  </div>
      </div>
        <b-card :header="title">
      <blockquote class="blockquote mb-0">
        <p class="text">{{text}}</p>
        <footer class="blockquote-footer">
          Your article will show here! <cite :title="user">{{user}}</cite>
        </footer>
      </blockquote>
    </b-card>
    <div class="handlerButton">
      <b-button squared variant="info">SAVE</b-button>
       <b-button squared variant="primary">SUBMIT</b-button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'savepage',
  data () {
    return {
      text: 'Good to see you here',
      title: '',
      user: 'Kuke'
    }
  }
}
</script>

<style>
.EditContainer
{
   height: 80vh;
}
.EditContainer .text
{
  font-size: 1rem;
  text-align: left;
  text-indent: 1.1rem;
}
.EditContainer .file[type="file"]{
  display: none;
}
.fas.fa-images{
  font-size: 30px;
  padding-top: 6px;
}
.EditContainer .card-body
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
}
.image-group
{
  display: flex;
  justify-content: space-between;
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
</style>
