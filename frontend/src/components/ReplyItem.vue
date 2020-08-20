<template>
  <div class="feed-item myreplyfeed">
    <div class="d-flex justify-align-between align-items-center mt-2">
      <b-form-input type="text" class="m-0 rounded-pill myreply" v-model="content" placeholder="댓글을 입력하세요 :D"></b-form-input>
      <!-- 댓글 내용이 없으면 -->
      <b-icon v-if="!content" icon="plus-circle" class="text-right ml-2 text-light" font-scale="1.4"></b-icon>
      <!-- 댓글 내용이 있으면 -->
      <b-icon v-else @click="pushReply" icon="plus-circle" class="text-right ml-2 text-secondary" font-scale="1.4"></b-icon>
    </div>
    <div class="mt-2 pl-2 pr-2">
      <div v-for="(re, idx) in replies" :key="re.no">
        <div class="d-flex justify-content-between">
          <div>
            <strong @click="moveAccount(re)" class="mr-2">{{re.writer_nick_name}}</strong>
            <span>{{re.content}}</span>
          </div>
          <div>
            <span @click="changeIsStretch(idx)" v-if="!re.isStretch"><b-icon-chat></b-icon-chat></span>

            <span @click="touchLike(re)" v-if="!re.press_like" class="ml-2"><b-icon-heart></b-icon-heart> <small>{{re.like_num}}</small></span> 
            <span @click="touchLike(re)" v-else class="ml-2 text-danger"><b-icon-heart-fill></b-icon-heart-fill> <small>{{re.like_num}}</small></span> 

            <span class="ml-2" v-if="re.writer_uno === $store.state.userInfo.uno" @click="delReply(re)"><b-icon-trash></b-icon-trash></span>
          </div>
        </div>
        <ReReplyItem 
        @addReply="addCount" 
        @completeReply="changeIsStretch(idx)" 
        @delReReply="delReReply(idx)" 
        :isStretch="re.isStretch" 
        :parentNo="re.no" 
        :fno="fno" :eno="eno" :pno="pno" :auno="re.writer_uno"
        :addfun="addfun" :delfun="delfun" :section="section"
        :addlike="addlike" :dellike="dellike"
        />
      </div>
    </div>
  </div>
</template>

<script>
import ReReplyItem from "@/components/ReReplyItem.vue"
import GetUserApi from "@/api/GetUserApi.js"
import FeedApi from "@/api/FeedApi.js"
import CurationApi from "@/api/CurationApi.js"
import ReplyApi from "@/api/ReplyApi.js"

export default {
  name: "ReplyItem",
  data() {
    return {
      content: null,
      replies: [],
      num: null,
      addfun: null,
      delfun: null,
      readfun: null,
      section: null,
      uno: null,
    }
  },
  props: {
    fno: Number,
    eno: Number,
    pno: Number,
    auno: Number
  },
  methods: {
    touchLike(reply) {
      reply.press_like = !reply.press_like

      let ano = 0;
      if(!this.fno === false) {
        ano = this.fno;
      } else if(!this.eno === false) {
        ano = this.eno;
      } else {
        ano = this.pno;
      }

      if (reply.press_like) {
        reply.like_num++;
        this.addlike({
          tno: reply.no
          , uno: reply.writer_uno
          , ano: ano
        }
        , res => console.log(res)
        , err => console.log(err)
        )
      } else {
        reply.like_num--;
        this.dellike({
          tno: reply.no
        }
        , res => console.log(res)
        , err => console.log(err)
        )
      }
    },
    moveAccount(re) {
      if (re.writer_uno === this.$store.state.userInfo.uno) {
        this.$router.push({path: `/mypage/main`})
      } else {
        this.$router.push({path:`/profile/${re.writer_nick_name}`})
      }
    },
    addCount() {
      this.$emit('addReply')
    },
    pushReply() {

      if (!this.fno===false) {
        this.addData = {
          no: this.fno,
          content: this.content,
          writer_uno: this.auno
        }
      } else if (!this.eno===false) {
        this.addData = {
          no: this.eno,
          content: this.content,
          writer_uno: this.auno
        }
      } else {
        this.addData = {
          no: this.pno,
          content: this.content,
          writer_uno: this.auno
        }
      }

      ReplyApi.addReply(
        this.addData
      , this.addfun
      , res=>{
        console.log(res)
        this.readfun({
          num: 1
        }
        , res=>{
          delete this.addData.content
          this.addData.num = 1
          this.readfun( 
            this.addData
            , res => {
              this.replies = res.data.data
              for (let i=0; i<this.replies.length; i++) {
                this.replies[i].isStretch = false
              }
            }
            , err=>console.log(err)
          )
          this.addCount()
        }
        , err=>console.log(err)
        )
      }
      , err=>console.log(err)
      )

      this.content=null
    },
    delReply(re) {
      
      console.log('dd', re)
      const temp = re.reply_num

      this.delfun(
        {
          no: re.no
        }
        , res => {
          this.$emit("delReply", Number(temp)+1)
          this.replies = this.replies.filter(res => res.no !== re.no)
          this.replies = this.replies.foreach(res => res.isStretch = false)
        }
        , err => console.log(err)
      )
    },
    addReReply(idx) {
      this.replies[idx].reply_num ++
    },
    changeIsStretch(idx) {
      const temp = this.replies[idx]
      temp.isStretch = !temp.isStretch
      this.replies.splice(idx, 1, temp)
      console.log(this.replies)
      if (!this.replies[idx].isStretch) {
        this.addReReply(idx)
      }
    },
    delReReply(idx) {
      this.replies[idx].reply_num--
      this.$emit("delReReply")
    }
  },
  components: {
    ReReplyItem,
  },
  watch: {
    replies: function(e, n) {
      console.log(this.replies)
    }
  },
  mounted() {
    if (!this.fno === false) {
      FeedApi.readReply(
        { 
          no: this.fno,
          num: 1
        }
        , res => {
          this.replies = res.data.data;
          for (let i=0; i<this.replies.length; i++) {
            this.replies[i].isStretch = false
          }
          console.log(this.replies)
        }
        , err => console.log(err)
      )
      this.addfun = FeedApi.createReply
      this.delfun = FeedApi.deleteReply
      this.readfun = FeedApi.readReply
      this.addlike = FeedApi.createReplyLike
      this.dellike = FeedApi.deleteReplyLike
      this.num = this.fno
      this.section = "feed"
    } else if (!this.eno === false) {
      CurationApi.readReply(
        { 
          no: this.eno,
          num: 1
        }
        , res => {
          this.replies = res.data.data;
          for (let i=0; i<this.replies.length; i++) {
            this.replies[i].isStretch = false
          }
          console.log(this.replies)
        }
        , err => console.log(err)
      )
      this.addfun = CurationApi.createEpisodeReply
      this.delfun = CurationApi.deleteEpisodeReply
      this.readfun = CurationApi.readReply
      this.addlike = CurationApi.createEpisodeReplyLike
      this.dellike = CurationApi.deleteEpisodeReplyLike
      this.num = this.eno
      this.section = "episode"
    } else {
      CurationApi.programReplyRead(
        { 
          no: this.pno,
          num: 1
        }
        , res => {
          this.replies = res.data.data;
          for (let i=0; i<this.replies.length; i++) {
            this.replies[i].isStretch = false
          }
          console.log(this.replies)
        }
        , err => console.log(err)
      )
      this.addfun = CurationApi.programReplyCreate
      this.delfun = CurationApi.programReplyDelete
      this.readfun = CurationApi.programReplyRead
      this.addlike = CurationApi.createProgramReplyLike
      this.dellike = CurationApi.deleteProgramReplyLike
      this.num = this.pno
      this.section = "program"
    }

  }
}
</script>

<style scoped>
  .myreplyfeed {
    padding-bottom: 50px;
    border-bottom: none;
  }
  .myreply {
    font-size: 0.9rem;
  }
  .myreplylike {
    color: black;
  }
</style>