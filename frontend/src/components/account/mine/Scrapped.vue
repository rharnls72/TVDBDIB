<template>
  <div class="col-12 m-0 p-0">
    <div class="col-12 row m-0 px-0 py-2 align-items-center">
      <!-- {{ feed }} -->
      <div class="col-6 mythumbnail">
        <!-- <FeedImage /> -->
        <h1 class="text-center m-0">썸네일</h1>
        <h1 class="text-center m-0">이미지</h1>
      </div>
      <div class="col-6">
        <p class="m-0">제목</p>
        <p class="m-0">내용</p>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";

// import FeedImage from '../../../components/feed/FeedImage.vue'

export default {
  name: 'Scrapped',
  // props: {
  //   feed: Object,
  // },
  // components: {
  //   FeedImage,
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