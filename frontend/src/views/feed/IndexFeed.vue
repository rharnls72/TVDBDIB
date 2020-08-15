<template>
  <div class="feed newsfeed">
    <IndexCurationHeader />
    <div class="wrapB">
      <div class="myfeed" v-for="feed in feeds" :key="feed.fno">
        <!-- <feedArticleItem v-if="d.ctype===1" :article="d" :fno="d.fno" @deleteItem="removeFeed"/>
        <feedCountdownItem v-if="d.ctype===2" :article="d" :fno="d.fno" @deleteItem="removeFeed"/>
        <feedVoteItem v-if="d.ctype===3" :article="d" :fno="d.fno" @deleteItem="removeFeed"/> -->
        <FeedItem :article="feed" :fno="feed.fno" @deleteItem="removeFeed"/>
      </div>
      <div class="myfeed"></div>
    </div>
    <Footer/>
  </div>
</template>

<script>
import { mapState } from "vuex";

import "../../components/css/feed/feed-item.scss";
import "../../components/css/feed/newsfeed.scss";

// import feedArticleItem from "@/components/feed/feedArticleItem.vue";
// import feedCountdownItem from "@/components/feed/feedCountdownItem.vue";
// import feedVoteItem from "@/components/feed/feedVoteItem.vue";

import FeedItem from "@/components/feed/FeedItem.vue"

import FeedApi from "@/api/FeedApi.js";

import IndexCurationHeader from "@/components/curation/IndexCurationHeader.vue";
import Footer from '@/components/common/custom/Footer.vue';
import GetUserApi from "@/api/GetUserApi"

export default {
  data() {
    return {
      feeds: [],
      requestCount: 1,
    }
  },

  components: { 
    // feedArticleItem, 
    // feedCountdownItem, 
    // feedVoteItem,
    FeedItem,
    IndexCurationHeader,
    Footer,
  },

  methods: {
    takeFeed() {
      let data = {
        num: this.requestCount
      };

      FeedApi.getFeedList(
        data
        , res => {

          this.feeds = res.list
          for (let i=0; i<res.list.length; i++) {
            this.feeds[i].content = JSON.parse(this.feeds[i].content)
            this.feeds[i].tag = JSON.parse(this.feeds[i].tag)
          }
          this.requestCount++
          setTimeout(()=>{}, 1000)
        }
        , err => {
          console.log(err)
        }
      )
    },
    removeFeed(fno) {this.feeds = this.feeds.filter(res => res.fno!==fno)}
  },

  mounted() {
    this.takeFeed()
  },
  created() {
    GetUserApi.getUser(res => {
      this.$store.commit('addUserInfo', res.user);
    });
    this.takeFeed();
  }
}
</script>

<style scoped>
  .myfeed {
    padding-top: 70px;
    margin-bottom: 10px;
  }
</style>