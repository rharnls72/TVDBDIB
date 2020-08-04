<template>
  <div class="col-12 m-0 p-0">
    <div v-for="feed in feeds" :key="feed.fno" class="col-12 row m-0 px-0 py-2 align-items-center">
      <!-- {{ feed }} -->
      <!-- <feedArticleItem v-if="feed.ctype===1" :article="feed" :fno="feed.fno"/>
      <feedCountdownItem v-if="feed.ctype===2" :article="feed" :fno="feed.fno"/>
      <feedVoteItem v-if="feed.ctype===3" :article="feed" :fno="feed.fno"/> -->
      <div class="col-6 mythumbnail">
        <!-- <FeedImage /> -->
        <h1 class="text-center m-0">썸네일</h1>
        <h1 class="text-center m-0">이미지</h1>
      </div>
      <div class="col-6">
        <p class="m-0">{{ feed.content.title }}</p>
        <p class="m-0">{{ feed.content.content }}</p>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";

// import FeedImage from '../../../components/feed/FeedImage.vue'

// import feedArticleItem from '@/components/feed/feedArticleItem.vue'
// import feedCountdownItem from '@/components/feed/feedCountdownItem.vue'
// import feedVoteItem from '@/components/feed/feedVoteItem.vue'

export default {
  name: 'WrittenFeed',
  // components: {
  //   FeedImage,
  // },
  props: {
    feeds: Array,
  },
  // components: { 
  //   feedArticleItem, 
  //   feedCountdownItem, 
  //   feedVoteItem,
  // },
}
</script>

<style scoped>
  .mythumbnail {
    border: 1px solid lightgray;
    width: 100%;
    height: auto;
  }
</style>