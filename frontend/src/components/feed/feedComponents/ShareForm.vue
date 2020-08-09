<template>
  <div>

    <div>공유 글 작성</div>
    <div class="row">
      <textarea class="col feed-input" type="text" v-model="content" placeholder="공유글을 작성하세요."></textarea>
    </div>

    <div>태그 작성</div>
    <div class="row">
      <textarea class="col feed-input" type="text" v-model="tag" @keydown.space="addTag" 
        placeholder="태그를 작성하세요.
스페이스바를 누르면 태그가 생성됩니다.">
      </textarea>
    </div>
    
    <div class="row px-3">
      <span v-for="(tag, idx) in tags" :key="idx" class="badge badge-secondary mr-1 mb-1">
        {{tag}} <b-icon icon="x" @click="delTag(idx)"></b-icon>
      </span>
    </div>

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
    }
  },
  methods: {
    addTag(event) {
      event.preventDefault()
      this.tag.trim()
      this.tags.push(this.tag)
      this.tag = null
    },
    delTag(idx) {
      this.tags.splice(idx, 1)
    },
  },
  computed: {
    makeData() {

      var jsonObj = {
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
  }
}
</script>

<style scoped>
.feed-input {
  width: 100%;
  height: 10vh;
  resize: none;
}
.row {
  margin-top: 10px;
  margin-bottom: 10px;
}
textarea:focus {
  outline: none;
}
</style>