<template>
  <div class="col-12 m-0 p-0">
    <div @click="moveDetail(feed.fno)" class="col-12 row m-0 px-2 py-2 align-items-center">
      <!-- {{ feed }} -->
      <div class="col-6 mythumbnail">
        <!-- <FeedImage /> -->
        <div v-if="feed.ctype===1" class="feed-thumbnail-item d-flex flex-column justify-content-center align-items-center text-center">
          <!-- <div>{{feed.content.title}}</div> -->
          <div v-for="(tag, idx) in feed.tag" :key="idx">#{{tag}}</div>
        </div>
        <div v-if="feed.ctype===2" class="feed-thumbnail-item d-flex flex-column justify-content-center align-items-center text-center" style="padding-top:10%; padding-bottom:10%">
          <!-- <div>{{feed.content.title}}</div> -->
          <div>{{feed.content.content.date}}</div>
          <div>{{feed.content.content.time}}</div>
        </div>
        <div v-if="feed.ctype===3" class="feed-thumbnail-item d-flex flex-column justify-content-center align-items-center text-center" style="padding-top:10%; padding-bottom:10%">
          <!-- <div>{{feed.content.title}}</div> -->
          <div>
            <div v-for="(option, idx) in feed.content.content" :key="idx">
              <div v-if="idx < feed.content.content.length-1">{{option.text}}<br> vs</div>
              <div v-else>{{option.text}}</div>
            </div>
          </div>
        </div>
        <div v-if="feed.ctype===4" class="feed-thumbnail-item d-flex flex-column justify-content-center align-items-center text-center" style="padding-top:10%; padding-bottom:10%">
          <!-- <div>{{feed.content.content}}</div> -->
          <div v-for="(tag, idx) in feed.tag" :key="idx">#{{tag}}</div>
        </div>
      </div>
      <div class="col-6">
        <div class="d-flex align-items-center">
          <div v-if="feed.ctype===1">
            <div>{{feed.content.title}}</div>
          </div>
          <div v-if="feed.ctype===2">
            <div>{{feed.content.title}}</div>
          </div>
          <div v-if="feed.ctype===3">
            <div>{{feed.content.title}}</div>
          </div>
          <div v-if="feed.ctype===4">
            <div>{{feed.content.content}}</div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";

// import FeedImage from '../../../components/feed/FeedImage.vue'

export default {
  name: 'WrittenFeed',
  props: {
    feed: null,
  },
  // components: {
  //   FeedImage,
  // },
  methods: {
    moveDetail(key) {
      this.$router.push({path: `/feed/detail/${key}`})
    }
  },
}
</script>

<style scoped>
  .mythumbnail {
    border: 1px solid lightgray;
    border-radius: 0.25rem;
    width: 100%;
    height: auto;
    background-color: #f8e8f2;
  }
  .feed-thumbnail-item {
    font-size: 0.75rem;
    position: relative;
    width: 100%;
    height: 100%;
    padding-top: 20%;
    padding-bottom: 20%;
  }
  .content {
    font-size: 1rem;
  }
</style>