<template>
  <div class="user join wrapC">
    <h1>Create Vote</h1>
    <div class="container">
      <form v-on:submit.prevent action="action_page.php">
        <div class="row">
          <div class="col-75">
            <input type="text" v-model="title" placeholder="Title">
          </div>
        </div>
        <div v-for="content in contents" :key="content.id" class="row">
          <b-form-textarea
            id="textarea"
            v-model="content.text"
            placeholder="Enter something..."
            rows="3"
            max-rows="6"
          ></b-form-textarea>
        </div>
        <div class="row">
          <div @click="addItem" class="add-item mb-3">+</div>
        </div>
        <div class="row">
          <b-form-tags
            input-id="tags-remove-on-delete"
            :input-attrs="{ 'aria-describedby': 'tags-remove-on-delete-help' }"
            v-model="value"
            separator=" "
            placeholder="Enter new tags separated by space"
            remove-on-delete
            no-add-on-enter
            class="mb-2"
          ></b-form-tags>
        </div>
        <div class="row">
          <div class="col-75 disable-button" v-if="!title || !content || !tags.length">submit</div>
          <button v-else>submit</button>
        </div>
      </form>
    </div>
  </div>
</template>

<script>

export default {
  name: 'CreateVote',
  data() {
    return {
      title: null,
      contents: [
        {id: 0, text: null},
        {id: 1, text: null},
      ],
      value: [],
      length: 2,
    }
  },
  methods: {
    pushItem() {
      console.log(this.content)
    },
    addItem(event) {
      this.length++
      
    },
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
  background-color: black;
  width: 100%;
  color: white;
  padding: 12px 20px;
  border-radius: 4px;
  cursor: pointer;
  float: right;
}

.disable-button {
  background-color: gray;
  text-align: center;
  width: 100%;
  color: rgb(212, 211, 211);
  padding: 12px 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  float: right;
}

.add-item {
  background-color: rgb(223, 213, 213);
  text-align: center;
  color: white;
  width: 100%;
  padding: 5px 5px;
  border-style: dotted;
  border-color: rgb(184, 182, 182);
  border-radius: 4px;
  cursor: pointer;
  float: center;
}

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
