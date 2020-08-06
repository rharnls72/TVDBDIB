<template>
  <div>
    <feedArticleItem :detail="true"/>
    <feedVoteItem :detail="true"/>
    <feedCountdownItem :detail="true"/>
    <ReplyItem/>
  </div>
</template>

<script>
import feedArticleItem from "@/components/feed/feedArticleItem.vue"
import feedVoteItem from "@/components/feed/feedVoteItem.vue"
import feedCountdownItem from "@/components/feed/feedCountdownItem.vue"
import ReplyItem from "@/components/ReplyItem.vue"

import FeedApi from "@/api/FeedApi.js"
import GetUserApi from "@/api/GetUserApi.js"

export default {
  name: 'FeedDetail',
  components: {
    feedArticleItem,
    feedVoteItem,
    feedCountdownItem,
    ReplyItem
  },
  data() {
    return {
      article: null,
    }
  },
  created() {
    GetUserApi.getUser(res => {
      this.$store.commit('addUserInfo', res.user);
    });
    
  },
  methods: {
  }
}
</script>

<style>

</style>