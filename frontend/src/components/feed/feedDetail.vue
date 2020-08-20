<template>
  <div>
    <div v-if="article != null" class="feed newsfeed">
      <FeedHeader />
      <div class="wrapB">
        <div class="myfeed">
          <FeedItem :article="article" :detail="true" :isStretch="isStretch" class="myfi"/>
          <ReplyItem @addReply="addReplyCount" @delReReply="delReReply" @delReply="res=>delReply(res)" :eno="article.eno != 0 ? article.eno : null" :fno="article.fno"/>
        </div>
      </div>
      <Footer />
    </div>
    <LoadingItem v-else />
  </div>
</template>

<script>
import FeedItem from "@/components/feed/FeedItem.vue"

import ReplyItem from "@/components/ReplyItem.vue"
import Footer from '@/components/common/custom/Footer.vue';
import FeedHeader from '@/components/feed/FeedHeader.vue'
// import LoadingItem from '@/components/common/custom/LoadingItem.vue'

import FeedApi from "@/api/FeedApi.js"
import GetUserApi from "@/api/GetUserApi.js"

export default {
  name: 'FeedDetail',
  components: {
    FeedItem,
    ReplyItem,
    FeedHeader,
    Footer,
    // LoadingItem
  },
  data() {
    return {
      article: null,
    }
  },
  methods: {
    delReply(res) {
      console.log(res)
      this.article.reply_num -= res
    },
    delReReply() {
      this.article.reply_num --
    },
    addReplyCount() {
      this.article.reply_num ++
    }
  },
  created() {
    GetUserApi.getUser(res => {
      this.$store.commit('addUserInfo', res.user);
    });
    
    FeedApi.feedDetail(
      this.$route.params.fno
    , res => {
      console.log(res)
      this.article = res.feed
      this.article.tag = JSON.parse(this.article.tag)
      this.article.content = JSON.parse(this.article.content)
    }
    , err => console.log(err)
    )
  },
}
</script>

<style scoped>
  .myfeed {
    padding-top: 50px;
    border-bottom: none;
  }
  .myfi {
    border-bottom: none;
  }
</style>