<template>
  <div class="feed newsfeed">
    <!---->
    <IndexCurationHeader/>
    <div class="wrapB">
      <h1>DETAIL</h1>
      <FeedArticleDetail style="border-bottom: none;" v-if="isTakeFeed === 1" :fno="Number(id)" :article="article"/>
      <FeedCountdownDetail style="border-bottom: none;" v-if="isTakeFeed === 2" :fno="Number(id)" :article="article"/>
      <FeedVoteDetail style="border-bottom: none;" v-if="isTakeFeed === 3" :fno="Number(id)" :article="article"/>
    </div>
    <Footer/>
    <!---->
  </div>
</template>

<script>
import "@/components/css/feed/feed-item.scss";
import "@/components/css/feed/newsfeed.scss";

import IndexCurationHeader from "@/components/curation/IndexCurationHeader.vue"
import Footer from '@/components/common/custom/Footer.vue';

import defaultImage from "@/assets/images/img-placeholder.png"
import defaultProfile from "@/assets/images/profile_default.png"
import FeedArticleDetail from "@/components/feed/FeedArticleDetail.vue"
import FeedCountdownDetail from "@/components/feed/FeedCountdownDetail.vue"
import FeedVoteDetail from "@/components/feed/FeedVoteDetail.vue"

import FeedApi from "@/api/FeedApi"

export default {
  name: 'feedDetail',
  data() {
    return {
      defaultImage,
      defaultProfile,
      isStretch: false,
      likeIcon: true,
      scrapIcon: false,
      id: null,
      isTakeFeed: null,
      article: null
    }
  },
  components: {
    FeedArticleDetail,
    IndexCurationHeader,
    Footer,
    FeedCountdownDetail,
    FeedVoteDetail,
  },
  methods: {
    readMore() {
      this.isStretch = !this.isStretch
    },
    touchLikeIcon() {
      this.likeIcon = !this.likeIcon
      console.log(this.likeIcon)
    },
    touchScrapIcon() {
      this.scrapIcon = !this.scrapIcon
    },
    delDetail() {
      FeedApi.deleteFeed(
          this.id,
          res=> {
            console.log(res)
          },
          err=> console.log(err)
        )
    },
  },
  mounted() {
    this.id = this.$route.params.id
    console.log(this.id)
    FeedApi.feedDetail(
        {id: this.id},
        res => {
          this.article = res.article;
          this.article.content = JSON.parse(this.article.content);
          this.article.tag = JSON.parse(this.article.tag);
          this.article.dibsNum = this.article.dibs_num;
          
          this.isTakeFeed = Number(this.article.ctype)
        },
        error => {
            this.$router.push({name:'Errors', query: {message: error.msg}})
        }
      );
  }
}
</script>

<style scoped>
.more {
  color: lightgray;
}
.thumbnail-color {
  background-color: lightgray;
}
.follow-button {
  height: auto;
  line-height: 10px;
  box-shadow: none;
}
</style>