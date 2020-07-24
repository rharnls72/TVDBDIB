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
          <b-form-textarea
            id="textarea"
            v-model="content"
            placeholder="Enter something..."
            rows="3"
            max-rows="6"
          ></b-form-textarea>

          <pre class="mt-3 mb-0">{{ text }}</pre>
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
      value: [],
    }
  },
  methods: {
    
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
