<template>
  <div class="user join wrapC">
    <div class="d-flex justify-content-between align-items-center mb-3">
      <b-icon @click="moveMain" icon="chevron-left" font-scale="1.5"></b-icon>
      <b-icon @click="submitArticle" icon="check-square" font-scale="1.4"></b-icon>
    </div>
    <div class="wrapB container">
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
import FeedApi from '../../api/FeedApi'
import header from '@/api/header.js'

export default {
  name: 'CreateArticle',
  data() {
    return {
      title: null,
      content: null,
      value: [],
    }
  },
  props: {
    article: Object,
  },
  computed: {
    ...mapState([
      'userInfo',
      'loginEmail'
    ])
  },
  methods: {
    moveMain() {
      console.log(1)
      this.$router.push('/feed/main')
    },
    makeData() {
      var jsonObj = {
        title: this.title,
        content: this.content,
      }
      return JSON.stringify(jsonObj)
    },
    submitArticle() {
      let sendData = this.makeData();

      console.log(this.$store.state.isUser)
      // createFeed 요청에 줄 데이터 목록
      // uno 는 토큰을 통해 사용하기위해 제거
      let Data = {
        ctype: 1,
        content: sendData,
        tag: JSON.stringify(this.value)
      };

      // // Axios 요청
      // FeedApi.createFeed(
      //   // 요청에 쓸 데이터 전달
      //   data
      //   // 성공시 수행할 콜백 메서드
      //   , res => {
      //     console.log(res);
      //     this.$router.push({path: '/feed/main'})
      //   }
      //   // 실패시 수행할 콜백 메서드
      //   , err => {
      //     console.log(err);
      //   } 
      // );

      axios.post('http://localhost:9000/feed/create', Data, header())
        .then(res => console.log(res))
        .catch(err => console.log(err))
    }
  },
  mounted() {
    if (this.article !== null) {
      console.log(this.article.content)
      const data = JSON.parse(this.article.content)
      this.title = data.title
      this.content = data.content
      this.value = this.article.tag
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
