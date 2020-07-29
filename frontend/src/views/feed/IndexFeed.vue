<template>
  <div class="feed newsfeed">
    <IndexCurationHeader />
    <div class="wrapB">
      <h1>뉴스피드</h1>
      <div v-for="d in feeds" :key="d.fno">
        <feedArticleItem v-if="d.ctype===1" :article="d"/>
      </div>
      <div>
        <feedCountdownItem />
      </div>
      <div>
        <feedVoteItem />
      </div>
    </div>
    <Footer/>
  </div>
</template>

<script>
import { mapState } from "vuex";

import "../../components/css/feed/feed-item.scss";
import "../../components/css/feed/newsfeed.scss";

import feedArticleItem from "@/components/feed/feedArticleItem.vue";
import feedCountdownItem from "@/components/feed/feedCountdownItem.vue";
import feedVoteItem from "@/components/feed/feedVoteItem.vue";

import FeedApi from "@/api/FeedApi.js";

import IndexCurationHeader from "@/components/curation/IndexCurationHeader.vue";
import Footer from '@/components/common/custom/Footer.vue';

export default {
  data() {
    return {
      feeds: [],
      requestCount: 1,
    }
  },
  
  props: ["keyword"],

  components: { 
    feedArticleItem, 
    feedCountdownItem, 
    feedVoteItem,
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
          console.log(res.data);
          this.feeds = this.feeds.concat(res.data.data);
          this.requestCount++;
        }
        , err => {
          console.log(err)
        }
      )
      /*
      axios.post('http://localhost:9000/feed/list' ,{num: this.requestCount}, header())
        .then(res => {
          console.log(res.data)
          this.feeds = this.feeds.concat(res.data.data)
          this.requestCount++
        })
        .catch(err => console.log(err))
      */
    }
  },
  created() {
    this.takeFeed()
  }
};
</script>

