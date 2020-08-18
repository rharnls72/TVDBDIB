<template>

  <div class="col-10">
    <b-list-group style="border-radius: 20px;">
      
      <b-list-group-item class="p-0 create-header"><input id="article-title" type="text" class="m-0 border-0 rounded-pill create-header-input" v-model="title" placeholder="제목은 뭐지??"></b-list-group-item>
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
        <b-badge v-for="(Tag, idx) in tags" :key="idx">{{Tag}}<b-icon @click="delTag(Tag)" icon="x" scale="0.75"></b-icon></b-badge>
      </b-list-group-item>

    </b-list-group>
  </div>

</template>

<script>

export default {
  name: 'ShareForm',
  data() {
    return {
      content: null,
      tags: [],
      tag: null,
      title: null,
    }
  },
  props: {
    article: Object,
  },
  methods: {
    inputTag(event) {
      event.preventDefault()
      if (this.tags.filter(res=>res=== this.tag).length === 0) {
        this.length++
        this.tags.push(this.tag)
        this.tag = null
      } else {
        console.log('태그가 중복됩니다.')
        this.tag = null
      }
    },
    delTag(t) {
      this.tags = this.tags.filter(res=>res !== t)
    },
  },
  computed: {
    makeData() {

      var jsonObj = {
        title: this.title,
        content: this.content,
      }
     
      let Data = {
        ctype: 4,
        content: jsonObj,
        tag: JSON.stringify(this.tags)
      }

      return Data
    },
  },
  updated() {
    this.$emit('CreateArticle', this.makeData)
  },
  mounted() {
    if (this.article !== null) {
      this.content = this.article.content.content
      this.tags = this.article.tag
    }
  }
}
</script>

<style scoped>

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