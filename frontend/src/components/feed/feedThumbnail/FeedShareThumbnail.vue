<template>
  <div class="feed-card share-item">
    <FeedArticleThumbnail v-if="article.content.article.ctype === 1" :article="article.content.article"/>
    <FeedCountdownThumbnail v-if="article.content.article.ctype === 2" :article="article.content.article"/>
    <FeedVoteThumbnail v-if="article.content.article.ctype === 3" :article="article.content.article"/>
    <div>공유된 글 보러가기...</div>
  </div>
</template>

<script>
import FeedArticleThumbnail from "@/components/feed/feedThumbnail/FeedArticleThumbnail.vue"
import FeedCountdownThumbnail from "@/components/feed/feedThumbnail/FeedCountdownThumbnail.vue"
import FeedVoteThumbnail from "@/components/feed/feedThumbnail/FeedVoteThumbnail.vue"

export default {
  name: 'FeedShareThumbnail',
  props: {
    article: Object,
  },
  components: {
    FeedArticleThumbnail,
    FeedCountdownThumbnail,
    FeedVoteThumbnail,
  },
  mounted() {
    console.log(this.article)
  }
}
</script>

<style>

</style>