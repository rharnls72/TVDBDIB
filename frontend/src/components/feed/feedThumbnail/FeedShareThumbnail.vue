<template>
  <div class="feed-card share-item">
    <!-- <FeedArticleThumbnail v-if="article.content.article.ctype === 1" :article="article.content.article"/>
    <FeedCountdownThumbnail v-if="article.content.article.ctype === 2" :article="article.content.article"/>
    <FeedVoteThumbnail v-if="article.content.article.ctype === 3" :article="article.content.article"/> -->
    <WrittenFeed v-if="article.content.article.ctype" :feed="article.content.article"/>
    <FeedShareEpisodeThumbnail v-if="!!article.content.article.eno" :curation="article.content.article"/>
    <FeedProgramThumbnail v-else :program="article.content.article"/>
  </div>
</template>

<script>
// import FeedArticleThumbnail from "@/components/feed/feedThumbnail/FeedArticleThumbnail.vue"
// import FeedCountdownThumbnail from "@/components/feed/feedThumbnail/FeedCountdownThumbnail.vue"
// import FeedVoteThumbnail from "@/components/feed/feedThumbnail/FeedVoteThumbnail.vue"
import FeedShareEpisodeThumbnail from "@/components/feed/feedThumbnail/FeedShareEpisodeThumbnail.vue"
import FeedProgramThumbnail from "@/components/feed/feedThumbnail/FeedProgramThumbnail.vue"
import WrittenFeed from "@/components/account/mine/WrittenFeed.vue"

export default {
  name: 'FeedShareThumbnail',
  props: {
    article: Object,
  },
  components: {
    // FeedArticleThumbnail,
    // FeedCountdownThumbnail,
    // FeedVoteThumbnail,
    FeedProgramThumbnail,
    FeedShareEpisodeThumbnail,
    WrittenFeed,
  },
}
</script>

<style>

</style>