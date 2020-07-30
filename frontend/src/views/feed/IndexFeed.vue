<template>
  <div class="feed newsfeed">
    <IndexCurationHeader />
    <div class="wrapB">
      <h1>뉴스피드</h1>
      <div v-for="d in feeds" :key="d.fno">
        <feedArticleItem v-if="d.ctype===1" :article="d" :fno="d.fno"/>
        <feedCountdownItem v-if="d.ctype===2" :article="d" :fno="d.fno"/>
        <feedVoteItem v-if="d.ctype===3" :article="d" :fno="d.fno"/>
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
          console.log(111, res.data);
          for (let i=0; i<res.data.data.length; i++)
          this.feeds.push({
            content: JSON.parse(res.data.data[i].content),
            tag: JSON.parse(res.data.data[i].tag),
            ctype: res.data.data[i].ctype,
            dibsNum: res.data.data[i].dibs_num,
            fno: res.data.data[i].fno,
            press_dibs: res.data.data[i].press_dibs,
            press_like: res.data.data[i].press_like,
            profile_pic: res.data.data[i].profile_pic,
            reply_content: res.data.data[i].reply_content,
            reply_num: res.data.data[i].reply_num,
            thumbnail: res.data.data[i].thumbnail,
            uno: res.data.data[i].uno,
            create_date: res.data.data[i].create_date
          })
          console.log(this.feeds)
          this.isTakeFeed = Number(this.article.ctype)
          console.log(this.isTakeFeed)
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

  mounted() {
    this.takeFeed()
  }
}
</script>

