<template>
  <div>
    <!-- <div v-if="show"> -->
    <div>
      <div class="feed newsfeed">
        <IndexCurationHeader />
        <div class="wrapB">
          <div class="myfeed">
            <FeedItem v-for="(feed, idx) in feeds" :key="idx" :article="feed" :fno="feed.fno" @deleteItem="removeFeed(feed.fno)"/>
            <infinite-loading v-if="!feedNull" @infinite="infiniteHandler"></infinite-loading>
            <!-- <div v-if="noCuration" style="text-align: center; margin-top: 50px;"> 팔로우 중인 유저가 없습니다<br/>
              소통할 유저를 찾으러 가볼까요?<br/>
            <button type="button" class="shadow moveSearch" @click="moveSearch">찾으러 가자!</button>
            </div> -->
          </div>
        </div>
        <Footer />
      </div>
    </div>
    <!-- <LoadingItem v-else /> -->
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
// import LoadingItem from '@/components/common/custom/LoadingItem.vue'

export default {
  data() {
    return {
      feeds: [],
      requestCount: 1,
      feedNull: false,
    }
  },

  components: {
    FeedItem,
    IndexCurationHeader,
    Footer,
    // LoadingItem,
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
    padding-top: 50px;
  }
  .moveSearch{
    margin-top: 30px;
    width: 150px;
    height: 40px;
    border-radius: 10px;
    background-color: #f8e8f2;
    color: rgb(84, 78, 88);
  }
</style>