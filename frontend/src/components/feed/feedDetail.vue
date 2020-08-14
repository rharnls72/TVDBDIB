<template>
  <div class="feed newsfeed">
    <FeedHeader />
    <div class="wrapB">
      <div class="myfeed">
        <!-- <feedArticleItem v-if="article.ctype === 1" :article="article" :detail="true"/>
        <feedVoteItem v-if="article.ctype === 3" :article="article" :detail="true"/>
        <feedCountdownItem v-if="article.ctype === 2" :article="article" :detail="true"/> -->
        <FeedItem :article="article" :detail="true"/>
        <ReplyItem @addReply="addReplyCount" @delReReply="delReReply" @delReply="res=>delReply(res)" :eno="article.eno" :fno="article.fno"/>
      </div>
    </div>
    <Footer />
  </div>
</template>

<script>
// import feedArticleItem from "@/components/feed/feedArticleItem.vue"
// import feedVoteItem from "@/components/feed/feedVoteItem.vue"
// import feedCountdownItem from "@/components/feed/feedCountdownItem.vue"

import FeedItem from "@/components/feed/FeedItem.vue"

import ReplyItem from "@/components/ReplyItem.vue"
import Footer from '@/components/common/custom/Footer.vue';
import FeedHeader from '@/components/feed/FeedHeader.vue'

import FeedApi from "@/api/FeedApi.js"
import GetUserApi from "@/api/GetUserApi.js"

export default {
  name: 'FeedDetail',
  components: {
    // feedArticleItem,
    // feedVoteItem,
    // feedCountdownItem,
    FeedItem,
    ReplyItem,
    FeedHeader,
    Footer
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
  .feed-item {
    border-bottom: none;
    padding-bottom: 0;
    margin-bottom: 0;
  }
  .newsfeed {
    margin-bottom: 100px;
  }
</style>