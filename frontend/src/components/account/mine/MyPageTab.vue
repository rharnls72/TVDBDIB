<template>
  <div class="p-0">
    <b-tabs class="mytabs" active-nav-item-class="font-weight-bold text-dark" content-class="mt-3" justified>
      <b-tab title="내가 쓴" title-link-class="text-secondary" active>
        <WrittenFeed v-for="feed in writtenFeeds" :key="feed.fno" :feed="feed" class="col-12 row" />
      </b-tab>
      <b-tab title="태그된" title-link-class="text-secondary">
        <TaggedFeed v-for="writtenFeed in writtenFeeds" :key="writtenFeed.fno" :feed="writtenFeed" class="col-12 row"/>
        <!-- 아직 사람 태그 기능이 없어서 writtenFeeds 로직으로 채워놓음 -->
        <!-- <TaggedFeed v-for="taggedFeed in taggedFeeds" :key="taggedFeed.fno" :feed="taggedFeed" class="col-12 row"/> -->
      </b-tab>
    </b-tabs>
  </div>
</template>

<script>
import WrittenFeed from '../mine/WrittenFeed.vue'
import TaggedFeed from '../mine/TaggedFeed.vue'

import FeedApi from "@/api/FeedApi.js";

export default {
  name: 'MyPageTab',
  data() {
    return {
      writtenFeeds: [],
      taggedFeeds: [],
      requestCount: 1,
    }
  },
  methods: {
    takeFeed() {
      let data = {
        num: this.requestCount,
        target_uno: this.info.uno
      };

      console.log(this.info)
      console.log(data)

      FeedApi.getFeedList(
        data
        , res => {
          console.log(111, res);

          this.writtenFeeds = res.list
          for (let i=0; i<res.list.length; i++) {
            this.writtenFeeds[i].content = JSON.parse(this.writtenFeeds[i].content)
            this.writtenFeeds[i].tag = JSON.parse(this.writtenFeeds[i].tag)
          }
          this.requestCount++
          console.log(this.writtenFeeds)
          setTimeout(()=>{}, 1000)
        }
        , err => {
          console.log(err)
        }
      )
    },
  },
  components: {
    WrittenFeed,
    TaggedFeed,
  },
  props: {
    info: Object,
  },
  watch: {
    info() {
      this.takeFeed()
    }
  },
}
</script>