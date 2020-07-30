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

import axios from "axios"

import header from "@/api/header.js"

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
      axios.delete(`http://localhost:9000/feed/delete/${this.id}`, header())
        .then(res => console.log(res))
        .catch(err => console.log(err))
    },
  },
  mounted() {
    this.id = this.$route.params.id
    console.log(this.id)
    axios.get('http://localhost:9000/feed/detail/' + this.id, header())
       .then(res => {
         console.log(res)
         this.article = {
           content: JSON.parse(res.data.data.content),
           tag: JSON.parse(res.data.data.tag),
           ctype: res.data.data.ctype,
           dibsNum: res.data.data.dibs_num,
           fno: res.data.data.fno,
           press_dibs: res.data.data.press_dibs,
           press_like: res.data.data.press_like,
           profile_pic: res.data.data.profile_pic,
           reply_content: res.data.data.reply_content,
           reply_num: res.data.data.reply_num,
           thumbnail: res.data.data.thumbnail,
           uno: res.data.data.uno,
           create_date: res.data.data.create_date
         }
         console.log(this.article)
         this.isTakeFeed = Number(this.article.ctype)
         console.log(this.isTakeFeed)
       })
       .catch(err => console.log(err))
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