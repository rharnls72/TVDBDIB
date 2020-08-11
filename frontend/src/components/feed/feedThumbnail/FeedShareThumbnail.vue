<template>
  <div class="feed-card share-item">
    <FeedArticleThumbnail v-if="article.content.article.ctype === 1" :article="article.content.article"/>
    <FeedCountdownThumbnail v-if="article.content.article.ctype === 2" :article="article.content.article"/>
    <FeedVoteThumbnail v-if="article.content.article.ctype === 3" :article="article.content.article"/>
    <EpisodeThumbnail v-if="!!article.content.article.eno" :curation="article.content.article"/>
    <div v-if="article.content.article.ctype">공유된 글 보러가기...</div>
    <div v-if="article.content.article.eno">공유된 에피소드 보러가기...</div>
  </div>
</template>

<script>
import FeedArticleThumbnail from "@/components/feed/feedThumbnail/FeedArticleThumbnail.vue"
import FeedCountdownThumbnail from "@/components/feed/feedThumbnail/FeedCountdownThumbnail.vue"
import FeedVoteThumbnail from "@/components/feed/feedThumbnail/FeedVoteThumbnail.vue"
import EpisodeThumbnail from "@/components/curation/episode/EpisodeThumbnail.vue"

export default {
  name: 'FeedShareThumbnail',
  props: {
    article: Object,
  },
  components: {
    FeedArticleThumbnail,
    FeedCountdownThumbnail,
    FeedVoteThumbnail,
    EpisodeThumbnail,
  },
}
</script>

<style>

</style>