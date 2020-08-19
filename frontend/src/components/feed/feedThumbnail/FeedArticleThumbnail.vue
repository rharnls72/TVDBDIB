<template>
  <div class="feed-card">
    <div class="mythumbnail d-flex justify-content-center align-items-center">
      <div class="px-3 py-5">
        <span v-for="tag in article.tag" :key="tag">
          #{{tag}}
        </span>
      </div>
    </div>
  </div>
</template>

<script>
import FeedApi from "@/api/FeedApi.js"

export default {
  name: 'feedArticleThumbnail',
  props:{
    article: Object,
  },
};
</script>

<style scoped>
.mythumbnail {
  background-color: #f8e8f2;
  width: 100%;
  height: 55%;
}
</style>
