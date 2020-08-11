<template>
  <div class="feed-card">
    <div v-if="curation.thumbnail != 'https://image.tmdb.org/t/p/w500'"><img class="mythumbnail" :src="curation.thumbnail" alt="thumbnail-image"></div>
    <div v-else><img class="mythumbnail" :src="defaultImage" alt="default-image"></div>
    <div class="contentsWrap">
      <div class="d-flex justify-content-between">
        <h4 class="title">[{{ curation.pname }}] {{ curation.episode }}화</h4>
        <!-- 에피소드에는 장르가 없다,, -->
        <!-- <p class="date">{{ curation.genre }}</p> -->
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'EpisodeThumbnail',
  props: {
    curation: Object,
  },
}
</script>

<style>
  .mythumbnail {
    width: 100%;
    height: auto;
  }
</style>