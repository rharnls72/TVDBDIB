<template>
  <div>
    <div v-for="feed in feeds" :key="feed.fno" class="d-flex feed-thumnail-list">
      <!-- 여기가 썸네일 부분 feed-thumbnail 에서 width로 크기 조절 가능 %로 단위 설정해야함 -->
      <div class="feed-thumbnail d-flex flex-column justify-content-between">
        <div v-if="feed.ctype===1" class="feed-thumbnail-item d-flex flex-column justify-content-center align-items-center text-center">
          <div>{{feed.content.title}}</div>
          <div v-for="(tag, idx) in feed.tag" :key="idx">#{{tag}}</div>
        </div>
        <div v-if="feed.ctype===2" class="feed-thumbnail-item d-flex flex-column justify-content-center align-items-center text-center">
          <div>{{feed.content.content.title}}</div>
          <div>{{feed.content.content.date}}</div>
          <div>{{feed.content.content.time}}</div>
        </div>
        <div v-if="feed.ctype===3" class="feed-thumbnail-item d-flex justify-content-center align-items-center text-center">
          <div></div>
        </div>
      </div>
      <!-- 여기가 썸네일 설명 부분 content 에서 css 손보기 가능 -->
      <div class="content d-flex justify-content-center align-items-center">
        <div v-if="feed.ctype===1">
          <div>Article</div>
          <div>{{feed.content.title}}</div>
        </div>
        <div v-if="feed.ctype===2">
          <div>Countdown</div>
          <div>{{feed.content.content.title}}</div>
        </div>
        <div v-if="feed.ctype===3">
          <div>Vote</div>
          <div>{{feed.content.content.title}}</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import FeedApi from "@/api/FeedApi.js"
import GetUserApi from "@/api/GetUserApi.js"

export default {
  name: 'FeedMiniTab.vue',
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

<style scoped>
.feed-thumbnail-list {
  width: 100%;
  border-bottom: 1px solid black;
}
.feed-thumbnail {
  width: 25%;
  margin: 5%;
  position:relative;
}
.feed-thumbnail:after {
  background-color: bisque;
  content:"";
  display:block;
  padding-bottom:100%;
}
.feed-thumbnail-item {
  font-size: 0.75rem;
  position:absolute;
  width:100%;
  height:100%;
};
.content {
  margin-top: 5%;
  margin-left: 5%;
  font-size: 1rem;
  width: 100%;
  /* position:absolute;
  right: 0; */
}
</style>