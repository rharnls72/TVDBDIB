<template>
  <div class="pl-4 mt-2">
    <div class="d-flex justify-align-between align-items-center">
      <b-form-input type="text" class="m-0 rounded-pill" v-model="content" placeholder="내용 입력!!!"></b-form-input>
            <!-- 댓글 내용이 없으면 -->
      <b-icon v-if="!content" icon="plus-circle" class="text-right ml-2 text-light" font-scale="1.4"></b-icon>
      <!-- 댓글 내용이 있으면 -->
      <b-icon v-else @click="pushReReply" icon="plus-circle" class="text-right ml-2 text-secondary" font-scale="1.4"></b-icon>
    </div>
    <div class="pl-2 my-1" v-for="r in reply" :key="r.id">
      {{r.writer_nick_name}} {{r.content}} <span class="moreView" @click="delReReply(r.id)">삭제</span>
    </div>
  </div>
</template>

<script>
import CurationApi from "@/api/CurationApi.js"
import FeedApi from "@/api/FeedApi.js"

export default {
  name: "ReReplyItem",
  data() {
    return {
      content: null,
      reply: [],
      k: 1
    }
  },
  props: {
    section: String,
    parentNo: Number,
    eno: Number,
  },
  methods: {
    pushReReply() {
      this.reply.push({
        id: this.k,
        user: this.$store.state.userInfo.nick_name,
        contents: String(this.content)
      })

      CurationApi.createEpisodeReply({
        no: this.eno,
        parent_reply: this.parentNo,
        content: this.content,
      }
      , res => console.log(res)
      , err => console.log(err)
      )

      this.k++
      this.content=null
    },
    delReReply(id) {
      this.reply = this.reply.filter(res => res.id !== id)
    }
  },
  mounted() {
    if (this.section === "episode") {
      CurationApi.readReReply({
        no: this.parentNo,
        num: 1
      }
      , res => {
        console.log("대대수", res)
        this.reply = res.data.data
      }
      , err => console.log(err)
      )
    }
  }
}
</script>

<style>
.moreView {
  color: darkgray; 
}
</style>