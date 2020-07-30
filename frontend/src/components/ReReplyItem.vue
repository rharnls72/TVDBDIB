<template>
  <div class="pl-4 mt-2">
    <div class="d-flex justify-align-between align-items-center">
      <b-form-input type="text" class="m-0 rounded-pill" v-model="content" placeholder="내용 입력!!!"></b-form-input>
            <!-- 댓글 내용이 없으면 -->
      <b-icon v-if="!content" icon="plus-circle" class="text-right ml-2 text-light" font-scale="1.4"></b-icon>
      <!-- 댓글 내용이 있으면 -->
      <b-icon v-else @click="pushReReply" icon="plus-circle" class="text-right ml-2 text-secondary" font-scale="1.4"></b-icon>
    </div>
    <div class="pl-2 my-1" v-for="r in replies" :key="r.id">
      {{r.user}} {{r.contents}} <span class="moreView" @click="delReReply(r.id)">삭제</span>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import header from '@/api/header.js'
import {mapState} from 'vuex'

export default {
  name: "ReReplyItem",
  props: {
    replies: Array,
    fno: Number,
  },
  data() {
    return {
      content: null,
      reply: null,
      k: 21
    }
  },
  computed: {
    ...mapState(['userInfo'])
  },
  methods: {
    pushReReply() {
      this.reply.push({
        id: this.k,
        user: 'chsmd',
        contents: String(this.content)
      })
      this.k++
      this.content=null
    },
    delReReply(id) {
      this.reply = this.reply.filter(res => res.id !== id)
    }
  },
  mounted() {
    this.reply = JSON.parse(JSON.stringify(this.replies))
  }
}
</script>

<style>
.moreView {
  color: darkgray; 
}
</style>