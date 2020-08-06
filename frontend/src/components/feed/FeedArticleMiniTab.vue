<template>
  <div>
    <div v-for="feed in feeds" :key="feed.fno" class="feed-thumbnail">
      <div class="feed-thumbnail-item d-flex justify-content-center align-items-center text-center">
        <span v-for="(tag, idx) in feed.tag" :key="idx">#{{tag}}</span>
      </div>
    </div>
  </div>
</template>

<script>
import FeedApi from "@/api/FeedApi.js"
import GetUserApi from "@/api/GetUserApi.js"

export default {
  name: 'FeedArticleMiniTab.vue',
  data() {
    return {
      feeds: null,
    }
  },
  methods: {
    takeFeed() {
      let data = {
        num: 1
      };

      FeedApi.getFeedList(
        data
        , res => {
          console.log(111, res);

          this.feeds = res.list
          for (let i=0; i<res.list.length; i++) {
            this.feeds[i].content = JSON.parse(this.feeds[i].content)
            this.feeds[i].tag = JSON.parse(this.feeds[i].tag)
          }
          this.requestCount++
          console.log(this.feeds)
          setTimeout(()=>{}, 1000)
        }
        , err => {
          console.log(err)
        }
      )
    },
  },
  created() {
    GetUserApi.getUser(res => {
      this.$store.commit('addUserInfo', res.user);
    });
    this.takeFeed();
  }
}
</script>

<style>
.feed-thumbnail {
  width: 25%;
  margin-left: 5%;
  margin-top: 5%;
  position:relative;
}
.feed-thumbnail:after {
  background-color: bisque;
  content:"";
  display:block;
  padding-bottom:100%;
}
.feed-thumbnail-item {
  font-size: 0.1rem;
  position:absolute;
  width:100%;
  height:100%;
}
</style>