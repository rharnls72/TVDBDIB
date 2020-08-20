<template>
  <div class="pl-3 mt-1">

    <div v-if="isStretch" class="d-flex justify-align-between align-items-center">
      <b-form-input type="text" class="m-0 rounded-pill myreply" v-model="content" placeholder="댓글을 입력하세요 :D"></b-form-input>
            <!-- 댓글 내용이 없으면 -->
      <b-icon v-if="!content" icon="plus-circle" class="text-right ml-2 text-light" font-scale="1.4"></b-icon>
            <!-- 댓글 내용이 있으면 -->
      <b-icon v-else @click="pushReReply" icon="plus-circle" class="text-right ml-2 text-secondary" font-scale="1.4"></b-icon>
    </div>

    <div v-if="!moreView && !!reply.length" class="d-flex mb-2">
      <span @click="changeMoreView" class="moreView myreply">댓글 {{reply.length}}개 더 보기</span>
    </div>

    <div v-else class="pl-2 my-1" v-for="r in reply" :key="r.no">
      <div class="d-flex justify-content-between">
        <div>
          <strong @click="moveAccount(r)" class="mr-2">{{r.writer_nick_name}}</strong>
          <span>{{r.content}}</span>
        </div>
        <div class="d-flex align-items-center">
          <span @click="touchLike(r)" v-if="!r.press_like" class="ml-2"><b-icon-heart></b-icon-heart> <small>0</small></span> 
          <span @click="touchLike(r)" v-else class="ml-2 text-danger"><b-icon-heart-fill></b-icon-heart-fill> <small>0</small></span>
          <span class="ml-2" v-if="r.writer_uno === $store.state.userInfo.uno" @click="delReReply(r.no)"><b-icon-trash></b-icon-trash></span>
        </div>
      </div>
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
      moreView: false,
      num: null,
    }
  },
  props: {
    section: String,
    parentNo: Number,
    eno: Number,
    fno: Number,
    pno: Number,
    addfun: Function,
    delfun: Function,
    isStretch: Boolean,
    addlike: Function,
    dellike: Function,
  },
  methods: {
    moveAccount(re) {
      if (re.writer_uno === this.$store.state.userInfo.uno) {
        this.$router.push({path: `/mypage/main`})
      } else {
        this.$router.push({path:`/profile/${re.writer_nick_name}`})
      }
    },
    touchLike(reply) {
      reply.press_like = !reply.press_like
      if (reply.press_like) {
        this.addlike({
          tno: reply.no
        }
        , res => console.log(res)
        , err => console.log(err)
        )
      } else {
        this.dellike({
          tno: reply.no
        }
        , res => console.log(res)
        , err => console.log(err)
        )
      }
    },
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
      } else {
        this.addData = {
          no: this.pno,
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
        this.$emit('addReply')
      }
      , err=>console.log(err)
      )

      this.content=null
      this.$emit("completeReply")
    },
    delReReply(no) {
      this.delfun(
        {
          no: no
        }
        , res => {
          this.reply = this.reply.filter(res => res.no !== no)
          this.$emit("delReReply")
        }
        , err => console.log(err)
      )
    },
    changeMoreView() {
      this.moreView = true
    }
  },
  watch: {
    isStretch: function(e, n) {
      console.log(this.isStretch)
    }
  },
  created() {
    if (this.section === "episode") {
      CurationApi.readReReply({
        no: this.parentNo,
        num: 1
      }
      , res => {
        this.reply = res.data.data
      }
      , err => console.log(err)
      )
      this.readfun = CurationApi.readReReply
      this.num = this.eno
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
      this.num = this.fno
    } else {
      CurationApi.programReReplyRead({
        no: this.parentNo,
        num: 1
      }
      , res => {
        this.reply = res.data.data
      }
      , err => console.log(err)
      )
      this.readfun = CurationApi.programReReplyRead
      this.num = this.pno
    }
  },
  updated() {
    console.log(this.isStretch)
  }
}
</script>
 
<style>
  .moreView {
    color: darkgray; 
  }
  .tap {
    white-space: pre;
  }
  .myreply {
    font-size: 0.9rem;
  }
</style>