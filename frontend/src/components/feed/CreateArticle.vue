<template>
  <div class="user join wrapC">
    <div class="d-flex justify-content-between align-items-center mb-3">
      <b-icon icon="chevron-left" font-scale="1.5"></b-icon>
      <b-icon @click="submitArticle" icon="check-square" font-scale="1.4"></b-icon>
    </div>
    <div class="container">
      <b-list-group style="border-radius: 20px;">
        
        <b-list-group-item class="p-0 bg-dark"><input id="article-title" type="text" class="m-0 border-0 rounded-pill text-white bg-dark" v-model="title" placeholder="제목은 뭐지??"></b-list-group-item>
        <b-list-group-item><b-form-input type="text" class="m-0 rounded-pill" v-model="content" placeholder="내용 입력!!!"></b-form-input></b-list-group-item>
        
        <b-list-group-item><b-form-tags
              input-id="tags-remove-on-delete"
              :input-attrs="{ 'aria-describedby': 'tags-remove-on-delete-help' }"
              v-model="value"
              separator=" "
              placeholder="태그 입력!!"
              remove-on-delete
              no-add-on-enter
              class="mb-2"
              style="outline:none !important"
            ></b-form-tags></b-list-group-item>

      </b-list-group>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import {mapState} from 'vuex'

const SERVER = 'http://localhost:9000/'

export default {
  name: 'CreateArticle',
  data() {
    return {
      title: null,
      content: null,
      value: [],
    }
  },
  computed: {
    ...mapState([
      'userInfo',
      'loginEmail'
    ])
  },
  methods: {
    makeData() {
      var jsonObj = {
        content: this.content,
      }
      return JSON.stringify(jsonObj)
    },
    submitArticle() {
      let sendData = this.makeData()
      console.log(sendData)
      axios.post(SERVER+'feed/create', {
        uno: this.userInfo.uno,
        ctype: 1,
        content: sendData,
        tag: JSON.stringify(this.value),
      })
        .then(res => {
          console.log(res)
        })
        .catch(err => console.log(err))
      console.log(JSON.parse(sendData))
    }
  }
}
</script>

<style scoped>

input[id=article-title]::placeholder {
  color: white;
}

input[type=text], select, textarea{
  width: 100%;
  padding: 12px;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
  resize: vertical;
  margin-top: 10px;
  margin-bottom: 10px;
}

button {
  background-color: #4CAF50;
  width: 100%;
  color: white;
  padding: 12px 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  float: right;
}

.disable-button {
  background-color: gray;
  text-align: center;
  width: 100%;
  color: black;
  padding: 12px 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  float: right;
}

/* .container {
  border-radius: 5px;
  background-color: #f2f2f2;
  padding: 20px;
} */

.col-25 {
  float: left;
  width: 25%;
  margin-top: 6px;
}

.col-75 {
  float: left;
  width: 75%;
  margin-top: 6px;
}

.row:after {
  content: "";
  display: table;
  clear: both;
}

span {
  margin: 1px;
}

@media screen and (max-width: 600px) {
  .col-25, .col-75, input[type=submit] {
    width: 100%;
    margin-top: 0;
  }
}

</style>
