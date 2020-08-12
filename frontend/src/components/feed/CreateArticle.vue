<template>
  <div class="col-10">
    <b-list-group style="border-radius: 20px;">
      
      <b-list-group-item class="p-0 bg-dark"><input id="article-title" type="text" class="m-0 border-0 rounded-pill text-white bg-dark" v-model="title" placeholder="제목은 뭐지??"></b-list-group-item>
      <b-list-group-item>
        <b-form-textarea
          id="textarea"
          v-model="content"
          placeholder="내용 입력"
          rows="3"
        ></b-form-textarea>
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
</template>

<script>
import {mapState} from 'vuex'
import FeedApi from '../../api/FeedApi'

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
    fno: Number,
    submit: Boolean,
  },
  computed: {
    ...mapState([
      'userInfo',
      'loginEmail'
    ])
  },
  methods: {
    moveMain() {
      this.$router.go(-1)
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

      // createFeed 요청에 줄 데이터 목록
      // uno 는 토큰을 통해 사용하기위해 제거
      let Data = {
        ctype: 1,
        content: sendData,
        tag: JSON.stringify(this.value)
      };

      // Axios 요청
      if (this.fno === null) {
        FeedApi.createFeed(
          // 요청에 쓸 데이터 전달
          Data
          // 성공시 수행할 콜백 메서드
          , res => {
            console.log('createFeed Success: ' + res);
            this.$router.push({path: '/feed/main'})
          }
          // 실패시 수행할 콜백 메서드
          , err => {
            console.log('createFeed Error: ' + err);
          } 
        )
      } else {
        Data.fno = this.fno

        FeedApi.updateFeed(
          Data,
          res=> {
            console.log(res)
            this.$router.push({path:'/feed/main'})
          },
          err=> console.log(err)
          )
      }
    }
  },
  watch: {
    submit: function(n, o) {
      this.submitArticle()
    }
  },
  mounted() {
    console.log(this.fno)
    if (this.article !== null) {
      console.log(this.article.content)
      const data = this.article
      console.log(data)
      this.title = data.content.title
      this.content = data.content.content
      this.value = this.article.tag
      console.log(this.title, this.content, this.value)
    } else {
      this.title = null
      this.content = null
      this.value = []
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
