<template>
  <div class="feed newsfeed">
    <!---->
    <IndexCurationHeader/>
    <div class="wrapB">
      <h1>DETAIL</h1>
      <feedArticleItem style="border-bottom: none;" v-if="isTakeFeed" :article="feeds"/>
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
import feedArticleItem from "@/components/feed/feedArticleItem.vue"

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
      feeds: [],
      id: null,
      isTakeFeed: false,
    }
  },
  components: {
    feedArticleItem,
    IndexCurationHeader,
    Footer,
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
  created() {
    this.id = this.$route.params.id
    console.log(this.id)
    axios.get(`http://localhost:9000/feed/detail/${this.id}`, header())
      .then(res => {
        this.feeds = res.data.data
        console.log(this.feeds)
        this.isTakeFeed = true
      })
      .catch(err => console.error(err))
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