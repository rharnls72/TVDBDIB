<template>
  <div class="feed-item pb-0 mb-0" style="border-bottom: none;">
    <div class="d-flex justify-align-between align-items-center mt-2">
      <b-form-input type="text" class="m-0 rounded-pill" v-model="content" placeholder="내용 입력!!!"></b-form-input>
      <!-- 댓글 내용이 없으면 -->
      <b-icon v-if="!content" icon="plus-circle" class="text-right ml-2 text-light" font-scale="1.4"></b-icon>
      <!-- 댓글 내용이 있으면 -->
      <b-icon v-else @click="pushReply" icon="plus-circle" class="text-right ml-2 text-secondary" font-scale="1.4"></b-icon>
    </div>
    <div class="mt-2 pl-2 pr-2">
      <div v-for="r in replies" :key="r.no">
        <div>{{r.writer_nick_name}} {{r.content}}
          <!-- <span class="moreView" @click="delReply(r.no)">삭제</span> -->
        </div>
        <ReReplyItem :parentNo="r.no" :eno="eno" :addfun="addfun" :delfun="delfun" :section="'episode'"/>
      </div>
    </div>
  </div>
</template>

<script>
import ReReplyItem from "@/components/ReReplyItem.vue"
import GetUserApi from "@/api/GetUserApi.js"
import FeedApi from "@/api/FeedApi.js"
import CurationApi from "@/api/CurationApi.js"

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
    }
  },
  props: {
    fno: Number,
    eno: Number,
  },
  methods: {
    pushReply() {
      this.replies.push({
        id: this.k,
        writer_nick_name: this.$store.state.userInfo.nick_name,
        content: String(this.content)
      })

      this.$emit("addReply")

      if (!this.fno === false) {
        FeedApi.createReply(
          {
            no: this.fno,
            content: this.content
          }
          , res => {
            console.log(res.data);
          }
          , err => {
            console.log(err.msg);
          })
      } else if (!this.eno === false) {
        CurationApi.createEpisodeReply(
          {
            no: this.eno,
            content: this.content
          }
          , res => console.log(res)
          , err => console.log(err)
        )
      }
      this.k++
      this.content=null
    },
    delReply(id) {
      this.replies = this.replies.filter(res => res.id !== id)
    }
  },
  components: {
    ReReplyItem,
  },
  created() {
    GetUserApi.getUser(res => {
      this.$store.commit('addUserInfo', res.user);
    });
    if (!this.fno === false) {
      FeedApi.readReply(
        { 
          no: this.fno,
          num: 1
        }
        , res => {
          this.replies = res.data.data;
        }
        , err => console.log(err)
      )
      this.addfun = FeedApi.createReply
      this.delfun = FeedApi.deleteReply
    } else if (!this.eno === false) {
      CurationApi.readReply(
        { 
          no: this.eno,
          num: 1
        }
        , res => {
          this.replies = res.data.data;
        }
        , err => console.log(err)
      )
      this.addfun = CurationApi.createEpisodeReply
      this.delfun = CurationApi.deleteEpisodeReply
    }
  }
}
</script>

<style>
.moreView {
  color: darkgray; 
}
</style>