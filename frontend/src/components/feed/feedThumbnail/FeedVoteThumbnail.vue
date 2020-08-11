<template>
  <div class="feed-card">
    <div class="mythumbnail d-flex flex-column justify-content-center align-items-center">
      <div v-for="content in article.content.content" :key="content.id" style="width: 80%;">
        <div class="my-2">
          <label class="d-flex justify-content-between"><span>{{content.text}}</span><span @click="voteOption(content)" class="moreView">투표하기</span></label>
          <b-progress :value="content.count" :max="totalNum" show-progress></b-progress>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import FeedApi from "@/api/FeedApi.js"

export default {
  name: 'feedVoteThumbnail',
  data() {
    return {
      totalNum: null,
    }
  },
  props: {
    article: Object,
  },
  methods:{
    totalNumber() {
      let t = 0
      for (let i=0; i<this.article.content.content.length; i++) {
        t += this.article.content.content[i].count
      }
      this.totalNum = t
    },
    makeData() {
      var jsonObj = {
        title: this.article.content.title,
        content: this.article.content.content,
      }
      return JSON.stringify(jsonObj)
    },
    voteOption(opt) {
      opt.count++
      let sendData = this.makeData()
      let data = {
        ctype: 3,
        content: sendData,
        tag: JSON.stringify(this.tags),
        fno: this.fno
      };
      FeedApi.updateFeed(
        data
        , res => console.log(res)
        , err => console.log(err)
      )
    },
  },
  updated() {
    this.totalNumber()
  },
  created() {
    console.log(this.article)
    this.totalNumber()
  }
};
</script>

<style scoped>
.mythumbnail {
  background-color: beige;
  width: 100v;
  height: 55v;
}
</style>
