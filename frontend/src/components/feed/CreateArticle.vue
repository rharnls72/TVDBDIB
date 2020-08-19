<template>
  <div class="col-10">
    <b-list-group style="border-radius: 20px;">
      
      <b-list-group-item class="p-0 create-header">
        <input 
          id="article-title" 
          type="text" 
          class="m-0 border-0 rounded-pill" 
          v-model="title" 
          placeholder="제목은 뭐지??">
      </b-list-group-item>
      <b-list-group-item>
        <b-form-textarea
          id="textarea"
          v-model="content"
          placeholder="내용 입력"
          rows="3"
        ></b-form-textarea>
      </b-list-group-item>
      
      <b-list-group-item>
        <textarea
        class="tag-input"
        v-model="tag"
        placeholder="태그를 입력하세요.
스페이스 바를 누르면 자동으로 태그가 입력됩니다."
        row="20"
        @keydown.space="inputTag"
        >
        </textarea>
        <b-badge v-for="(Tag, idx) in value" :key="idx">{{Tag}}<b-icon @click="delTag(Tag)" icon="x" scale="0.75"></b-icon></b-badge>
      </b-list-group-item>

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
      tag: null,
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
    inputTag(event) {
      event.preventDefault()
      if (this.value.filter(res=>res=== this.tag).length === 0) {
        this.length++
        this.value.push(this.tag)
        this.tag = null
      } else {
        console.log('태그가 중복됩니다.')
        this.tag = null
      }
    },
    delTag(t) {
      this.value = this.value.filter(res=>res !== t)
    },
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
          err=> console.log('updateFeed Error: ' + err.msg)
          )
      }
    }
    , checkInput(event) {
      let keyCode = event.hasOwnProperty('which') ? event.which : event.keyCode;

      // 스페이스바나 엔터가 눌리면 태그 추출하기
      if(keyCode == 13 || keyCode == 32) {
        FeedApi.getTags(
          {
            content: this.content
            , tags: JSON.stringify(this.value)
          }
          , res => {
            console.log(res);

            // 받아온 태그 목록 중 현재 태그 목록에 없는거만 적용
            this.value = JSON.parse(res.data.data);
          }
          , err => {
            console.log('getTags Error: ' + err.msg);
          }
        )
      }
    }
    , contentFocusOut() {
      console.log('contentFocusOut() called!!!');
      FeedApi.getTags(
        {
          content: this.content
          , tags: JSON.stringify(this.value)
        }
        , res => {
          console.log(res);

          // 받아온 태그 목록 중 현재 태그 목록에 없는거만 적용
          this.value = JSON.parse(res.data.data);
        }
        , err => {
          console.log('getTags Error: ' + err.msg);
        }
      )
    }
    , titleFocusOut() {
      console.log('titleFocusOut() called!!!');
      FeedApi.getTags(
        {
          content: this.title
          , tags: JSON.stringify(this.value)
        }
        , res => {
          console.log(res);

          // 받아온 태그 목록 중 현재 태그 목록에 없는거만 적용
          this.value = JSON.parse(res.data.data);
        }
        , err => {
          console.log('getTags Error: ' + err.msg);
        }
      )
    }
  },
  watch: {
    submit: function(n, o) {
      this.submitArticle()
    }
  },
  mounted() {
    console.log('mounted() fno: ', this.fno)
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

    document.getElementById("textarea").addEventListener("keypress", this.checkInput);
    document.getElementById("textarea").addEventListener("focusout", this.contentFocusOut);
    document.getElementById("article-title").addEventListener("focusout", this.titleFocusOut);
  }
}
</script>

<style scoped>

input[id=article-title]::placeholder {
  color: black;
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

.create-header {
  background-color: #D8BEFE;
}

#textarea {
  resize: none;
}

.tag-input {
  height: 100px;
  resize: none;
}

.tag-input:focus {
  outline: none;
}

.input-form:focus {
  outline: none;
}

</style>
