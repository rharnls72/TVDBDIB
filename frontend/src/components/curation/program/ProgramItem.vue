<template>
  <div class="feed newsfeed pt-0" v-if="episode.broadcast_date!=null">
    <div class="wrapB">
      <div class="col-12 m-0  p-0">
        <div @click="moveDetail" class="col-12 row m-0 px-0 py-2 align-items-center">
          <!-- {{ feed }} -->
          <img v-if="episode.thumbnail===null" class="col-6" :src="defaultImg" alt="default-image">
          <img v-else class="col-6" :src="episode.thumbnail" :alt="episode.pname">
          <div class="col-6">
            <div>{{episode.episode}} 화</div>
            <div>{{episode.broadcast_date}} 방영</div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import defaultImg from '@/assets/images/custom/logo.png'
export default {
  name: 'ProgramItem',
  data() {
    return {
      defaultImg,
    }
  },
  props: {
    episode: Object,
  },
  methods: {
    moveDetail() {
      this.$router.push({path: `/episode/detail/${this.episode.pno}/${this.episode.season}/${this.episode.episode}`})
    }
  },
};
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
    .mythumbnail {
    border: 1px solid lightgray;
    width: 100%;
    height: auto;
    background-color: bisque;
  }
  .feed-thumbnail-item {
    font-size: 0.75rem;
    position:relative;
    width:100%;
    height:100%;
    padding-top: 20%;
    padding-bottom: 20%;
  }
  .content {
    font-size: 1rem;
  }
</style>