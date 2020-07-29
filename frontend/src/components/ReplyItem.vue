<template>
  <div class="feed-item" style="border-bottom: none;">
    <div class="d-flex justify-align-between align-items-center mt-2">
      <b-form-input type="text" class="m-0 rounded-pill" v-model="content" placeholder="내용 입력!!!"></b-form-input>
      <b-icon icon="plus-circle" class="text-right ml-2" font-scale="1.4"></b-icon>
    </div>
    <div class="mt-2 pl-2 pr-2">
      <div v-for="r in replies" :key="r.id">
        <div>{{r.user}} {{r.contents}}</div>
        <ReReplyItem :replies="reReplies"/>
      </div>
    </div>
  </div>
</template>

<script>
import ReReplyItem from "@/components/ReReplyItem.vue"
import axios from "axios"

export default {
  name: "ReplyItem",
  data() {
    return {
      content: null,
      replies: [
        {id:1, user: 'chsmd', contents:'소통'}, 
        {id:2, user: 'chsmd', contents:'소통'}, 
        {id:3, user: 'chsmd', contents:'소통'}, 
        {id:4, user: 'chsmd', contents:'소통'}, 
        {id:5, user: 'chsmd', contents:'소통'}, 
        {id:6, user: 'chsmd', contents:'소통'}, 
      ],
      reReplies: [
        {id:1, user: 'chsmd', contents:'소통'}, 
        {id:2, user: 'chsmd', contents:'소통'},
      ],
    }
  },
  methods: {
  },
  components: {
    ReReplyItem,
  }
}
</script>

<style>

</style>