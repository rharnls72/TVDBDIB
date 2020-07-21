<template>
  <div class="user join wrapC">
    <div class="container">
      <h1>Create Article</h1>
      <form v-on:submit.prevent action="action_page.php">
        <div class="row">
          <div class="col-75">
            <input type="text" v-model="title" placeholder="Title">
          </div>
        </div>
        <div class="row">
          <div class="col-75">
            <textarea placeholder="Write Content" v-model="content" style="height:200px;"></textarea>
          </div>
        </div>
        <div>
          <h6><span v-for="t in tags" @click="delTag(t)" :key="t.content" class="badge badge-success">{{t.content}}</span></h6>
        </div>
        <div class="row">
          <div class="col-75">
            <input @keypress.enter="pushTag" v-model="tag" type="text" placeholder="Tags">
          </div>
        </div>
        <div class="row">
          <div class="disable-button" v-if="!title || !content || !tags.length">submit</div>
          <button v-else>submit</button>
        </div>
      </form>
    </div>
  </div>
</template>

<script>

export default {
  name: 'CreateArticle',
  data() {
    return {
      title: null,
      content: null,
      tags: [],
      tag: null,
    }
  },
  methods: {
    pushTag() {
      if (!this.tag) { 
        alert('빈 값은 허용되지 않습니다.')
        return 
      }
      if (!this.tags) { return }
      else {
        if (this.tags.filter(res => res.content === this.tag).length) {
          alert('존재하는 태그입니다.')
        } else {
          this.tags.push({
            content: this.tag,
            isdelete: false,
          })
          this.tag = null
        }
      }
    },
    delTag(tag) {
      tag.isdelete = true
      this.tags = this.tags.filter(res => {
        return !res.isdelete
      })
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

</style>
