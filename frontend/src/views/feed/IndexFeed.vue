<template>
  <div class="feed newsfeed">
    <div class="wrapB">
      <h1>뉴스피드</h1>
      <router-link to="/user/modifyPw" class="btn--text">비밀번호변경</router-link>
      <div v-for="d in feeds" :key="d.fno">
        <feedArticleItem v-if="d.ctype===1" :article="d.fno"/>
        <feedCountdownItem v-else-if="d.ctype===2" :countdown="d.fno"/>
        <feedVoteItem v-else :vote="d.fno"/>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
import "../../components/css/feed/feed-item.scss";
import "../../components/css/feed/newsfeed.scss";
import feedArticleItem from "@/components/feed/feedArticleItem.vue";
import feedCountdownItem from "@/components/feed/feedCountdownItem.vue";
import feedVoteItem from "@/components/feed/feedVoteItem.vue";
import header from "@/api/header.js"
import axios from "axios"

export default {
  data() {
    return {
      feeds: null,
      requestCount: 1,
    }
  },
  
  props: ["keyword"],

  components: { feedArticleItem, feedCountdownItem, feedVoteItem },

  methods: {
    takeFeed() {
      axios.post('http://localhost:9000/feed/list',{num: this.requestCount}, header())
        .then(res => {
          console.log(res.data)
          this.feeds = res.data.data
          this.requestCount++
        })
        .catch(err => console.log(err))
    }
  },
  created() {
    this.takeFeed()
  }
};
</script>

