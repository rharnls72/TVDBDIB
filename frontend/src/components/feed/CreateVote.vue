<template>
  <div class="col-10">
    <b-list-group style="border-radius: 20px;" id="vote-content-area">
      
      <b-list-group-item class="p-0 create-header">
        <input 
          id="article-title" 
          type="text" 
          class="m-0 border-0 rounded-pill" 
          v-model="title" 
          placeholder="제목은 뭐지??">
      </b-list-group-item>
      
      <b-list-group-item v-for="(content, idx) in contents" :key="content.id">
        <div class="row d-flex align-items-center px-3">
          <input type="text" class="m-0 col rounded-pill input-form" v-model="content.text" placeholder="항목 입력!!!">
          <b-icon icon="dash-circle" v-if="idx>1" @click="delItem(content.id)" class="ml-1 text-right" font-scale="1.4"></b-icon>
        </div>
      </b-list-group-item>

      <b-list-group-item v-if="contents.length < 3">
        <div class="row">
          <b-icon icon="plus-circle" class="ml-1 col text-right" @click="addItem" font-scale="1.4"></b-icon>
        </div>
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
import FeedApi from '../../api/FeedApi'
import $ from 'jquery'

export default {
  name: 'CreateVote',
  data() {
    return {
      title: null,
      contents: [
        {id: 1, text: null, count: 0},
        {id: 2, text: null, count: 0},
      ],
      value: [],
      length: 2,
      tag: null,
    }
  },
  props: {
    article: Object,
    fno: Number,
    submit: Boolean,
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
    delItem(delId) {
      this.contents = this.contents.filter(res => {
        return res.id !== delId
      })
    },
    addItem(event) {
      this.contents.push({
        id: this.length + 1,
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
            this.$router.push({path:'/feed/main'})
          }
          // 실패시 수행할 콜백 메서드
          , err => {
            console.log(err);
          } 
        );
      } else {
        
        data.fno = this.fno

        FeedApi.updateFeed(
          data,
          res=> {
            console.log(res)
            this.$router.push({path:'/feed/main'})
          },
          err=> console.log(err)
          )
      }
    }
    , voteFocusOut() {
      let content = "";
      if(this.title && this.title.length > 0) {
        content = this.title;
      }

      for(let item of this.contents) {
        if(item.text && item.text.length > 0) {
          content = content + " " + item.text;
        }
      }

      console.log('Request vote tags: ', content);
      FeedApi.getTags(
        {
          content: content
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
    if (this.article !== null) {
      this.contains = this.article.content.content
      this.title = this.article.content.title
      this.value = this.article.tag
    }
    else {
      this.contains = [
        {id: 1, text: null, count: 0},
        {id: 2, text: null, count: 0},
      ]
      this.title = null
      this.value = []
    }

    $("#vote-content-area").on("focusout", "input", this.voteFocusOut);
  }
}
</script>

<style scoped>

input[id=article-title]::placeholder {
  color: black;
}

input[type=text], select, textarea{
  width: 100%;
  padding: 10px;
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
