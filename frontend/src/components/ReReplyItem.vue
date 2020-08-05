<template>
  <div class="pl-4 mt-2">
    <div class="d-flex justify-align-between align-items-center">
      <b-form-input type="text" class="m-0 rounded-pill" v-model="content" placeholder="내용 입력!!!"></b-form-input>
            <!-- 댓글 내용이 없으면 -->
      <b-icon v-if="!content" icon="plus-circle" class="text-right ml-2 text-light" font-scale="1.4"></b-icon>
      <!-- 댓글 내용이 있으면 -->
      <b-icon v-else @click="pushReReply" icon="plus-circle" class="text-right ml-2 text-secondary" font-scale="1.4"></b-icon>
    </div>
    <div class="pl-2 my-1" v-for="r in reply" :key="r.no">
      {{r.writer_nick_name}} {{r.content}} <span class="moreView" @click="delReReply(r.no)">삭제</span>
    </div>
  </div>
</template>

<script>
import CurationApi from "@/api/CurationApi.js"
import FeedApi from "@/api/FeedApi.js"
import ReplyApi from "@/api/ReplyApi.js"

export default {
  name: "ReReplyItem",
  data() {
    return {
      content: null,
      reply: [],
      addData: null,
      readfun: null,
    }
  },
  props: {
    section: String,
    parentNo: Number,
    eno: Number,
    fno: Number,
    addfun: Function,
    delfun: Function,
  },
  methods: {
    pushReReply() {

      if (!this.eno===false) {
        this.addData = {
          no: this.eno,
          parent_reply: this.parentNo,
          content: this.content,
        }
      } else if (!this.fno===false) {
        this.addData = {
          no: this.fno,
          parent_reply: this.parentNo,
          content: this.content,
        }
      }
      
      console.log(this.addData)

      ReplyApi.addReply(
        this.addData
      , this.addfun
      , res=>{
        console.log(res)
        this.readfun({
          no: this.parentNo,
          num: 1
        }
        , res=>{
          this.reply = res.data.data
        }
        , err=>console.log(err)
        )
      }
      , err=>console.log(err)
      )

      this.content=null
    },
    delReReply(no) {
      this.delfun(
        {
          no: no
        }
        , res => {
          this.reply = this.reply.filter(res => res.no !== no)
        }
        , err => console.log(err)
      )
    }
  },
  mounted() {
    if (this.section === "episode") {
      CurationApi.readReReply({
        no: this.parentNo,
        num: 1
      }
      , res => {
        console.log(res)
        this.reply = res.data.data
      }
      , err => console.log(err)
      )
      this.readfun = CurationApi.readReReply
    } else if (this.section === "feed") {
      FeedApi.readReReply({
        no: this.parentNo,
        num: 1
      }
      , res => {
        this.reply = res.data.data
      }
      , err => console.log(err)
      )
      this.readfun = FeedApi.readReReply
    }
  }
}
</script>
 
<style>
.moreView {
  color: darkgray; 
}
</style>