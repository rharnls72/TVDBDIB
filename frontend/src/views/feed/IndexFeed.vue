<template>
  <div class="feed newsfeed">
    <IndexCurationHeader />
    <div class="wrapB">
      <div class="myfeed" v-for="(feed, idx) in feeds" :key="idx">
        <!-- <feedArticleItem v-if="d.ctype===1" :article="d" :fno="d.fno" @deleteItem="removeFeed"/>
        <feedCountdownItem v-if="d.ctype===2" :article="d" :fno="d.fno" @deleteItem="removeFeed"/>
        <feedVoteItem v-if="d.ctype===3" :article="d" :fno="d.fno" @deleteItem="removeFeed"/> -->
        <FeedItem :article="feed" :fno="feed.fno" @deleteItem="removeFeed(feed.fno)"/>
      </div>
      <infinite-loading v-if="!feedNull" @infinite="infiniteHandler"></infinite-loading>

      <div v-if="feedNull">
        <h1>No more results...</h1>
        <div style="height:50px;"></div>
      </div>
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

import InfiniteLoading from 'vue-infinite-loading'

export default {
  data() {
    return {
      feeds: [],
      requestCount: 1,
      feedNull: false,
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
          if (res.list.length > 0) {
            for (let i=0; i<res.list.length; i++) {
              res.list[i].content = JSON.parse(res.list[i].content)
              res.list[i].tag = JSON.parse(res.list[i].tag)
            }
            this.feeds = this.feeds.concat(res.list)
            this.requestCount++
            setTimeout(()=>{}, 300)
          } else {
            this.feedNull = !this.feedNull
          }
        }
        , err => {
          console.log(err)
        }
      )
    },
    infiniteHandler($state) {
      setTimeout(() => {
        if (!this.feedNull){
          this.takeFeed()
        }
        $state.loaded();
      }, 300);
    },
    removeFeed(fno) {this.feeds = this.feeds.filter(res => res.fno!==fno)}
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
    /* padding-bottom: 50px; */
  }
</style>