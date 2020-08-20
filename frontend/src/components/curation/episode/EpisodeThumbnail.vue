<template>
  <div class="feed-card">
    <div v-if="curation.thumbnail !== null"><img class="mythumbnail" :src="curation.thumbnail" alt="thumbnail-image"></div>
    <img v-else class="mythumbnail" :src="defaultImage" alt="default-image">
    <div class="contentsWrap">
      <div class="d-flex justify-content-between">
        <h4 class="title">{{ curation.pname }} - {{ curation.episode }}화</h4>
        <!-- 에피소드에는 장르가 없다,, -->
        <!-- <p class="date">{{ curation.genre }}</p> -->
      </div>
    </div>
  </div>
</template>

<script>
import defaultImage from "@/assets/images/custom/logo.png"
export default {
  name: 'EpisodeThumbnail',
  data() {
    return {
      defaultImage,
    }
  },
  props: {
    curation: Object,
  },
}
</script>

<style scoped>
  .mythumbnail {
    width: 100%;
    height: auto;
    background-color: #f8e8f2;
  }
</style>