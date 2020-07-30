<template>
  <div class="user join wrapC">
    <div class="d-flex justify-content-between align-items-center mb-3">
      <b-icon @click="moveMain" icon="chevron-left" font-scale="1.5"></b-icon>
      <b-icon icon="check-square" @click="submitArticle" font-scale="1.4"></b-icon>
    </div>
    <div class="wrapB">
      <b-list-group class="pt-5" style="border-radius: 20px;">
        
        <b-list-group-item class="p-0 bg-dark"><input id="article-title" type="text" class="m-0 border-0 rounded-pill text-white bg-dark" v-model="title" placeholder="제목은 뭐지??"></b-list-group-item>
        
        <b-list-group-item v-for="content in contents" :key="content.id">
          <div class="row d-flex align-items-center px-3">
            <b-form-input type="text" class="m-0 col rounded-pill" v-model="content.text" placeholder="항목 입력!!!">
            </b-form-input>
            <b-icon icon="dash-circle" v-if="content.id>1" @click="delItem(content.id)" class="ml-1 text-right" font-scale="1.4"></b-icon>
          </div>
        </b-list-group-item>

        <b-list-group-item>
          <div class="row">
            <b-icon icon="plus-circle" class="ml-1 col text-right" @click="addItem" font-scale="1.4"></b-icon>
          </div>
        </b-list-group-item>

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
import FeedApi from '../../api/FeedApi'
import axios from 'axios'
import header from '@/api/header.js'

export default {
  name: 'CreateVote',
  data() {
    return {
      title: null,
      contents: [
        {id: 0, text: null, count: 0},
        {id: 1, text: null, count: 0},
      ],
      value: [],
      length: 2,
    }
  },
  props: {
    article: Object,
    fno: Number,
  },
  methods: {
    moveMain() {
      console.log(1)
      this.$router.push('/feed/main')
    },
    delItem(delId) {
      this.contents = this.contents.filter(res => {
        return res.id !== delId
      })
    },
    addItem(event) {
      this.contents.push({
        id: this.length,
        text: null,
        count: 0,
      })
      this.length++
    },
    makeData() {
      var jsonObj = {
        title: this.title,
        content: this.contents,
      }
      return JSON.stringify(jsonObj)
    },
    submitArticle() {
      let sendData = this.makeData();

      // createFeed 요청에 줄 데이터 목록
      // uno 는 토큰을 통해 사용하기위해 제거
      let data = {
        ctype: 3,
        content: sendData,
        tag: JSON.stringify(this.value)
      };

      if (this.fno === null) {
        // Axios 요청
        FeedApi.createFeed(
          // 요청에 쓸 데이터 전달
          data
          // 성공시 수행할 콜백 메서드
          , res => {
            console.log(res);
            this.$router.push('/feed/main')
          }
          // 실패시 수행할 콜백 메서드
          , err => {
            console.log(err);
          } 
        );
      } else {
        data.fno = this.fno
        axios.put('http://localhost:9000/feed/update', data, header())
          .then(res => {
            console.log(res)
            this.$router.push({path:'/feed/feedDetail/'+this.fno})
          })
          .catch(err => console.log(err))
      }
    }
  },
  mounted() {
    this.contains = this.article.content.content
    this.title = this.article.content.title
    this.value = this.article.tag
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
