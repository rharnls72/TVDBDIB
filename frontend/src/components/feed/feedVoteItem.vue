<template>
  <div class="feed-item">
    <div class="top">
      <div class="profile-image" :style="{'background-image': 'url('+defaultProfile+')'}"></div>
      <div class="user-info">
        <div class="user-name">
          <button>{{article.nick_name}}</button>
        </div>
        <p class="date">{{createAfter}} 시간 전</p>
      </div>
      <div class="content d-flex flex-comlumn justify-content-between align-items-center my-2">
        <div>{{article.content.title}}</div>
        <div v-if="!!this.$store.state.userInfo && this.$store.state.userInfo.uno === article.uno">
          <span @click="updateFeed">수정</span>   <span @click="delFeed">삭제</span>
        </div>
      </div>
    </div>
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
    <!---->
    <div class="btn-group wrap justify-content-between" style="margin: 15px 0 0 0;">
      <div>
        <!-- 좋아요 -->
        <div class="mr-3">
          <button class="h6 mr-1" @click="touchLikeIcon">
            <b-icon-heart v-if="!article.press_like"></b-icon-heart>
            <b-icon-heart-fill v-else variant="danger"></b-icon-heart-fill>
          </button>
          {{article.like_num}}
        </div>
        <!-- 댓글 -->
        <div class="mr-3">
          <button class="h6 mr-1">
            <b-icon-chat></b-icon-chat>
          </button>
          {{article.reply_num}}
        </div>
        <!-- 스크랩 -->
        <div class="mr-3">
          <button class="h6 mr-1" @click="touchScrapIcon">
            <b-icon-bookmark v-if="!article.press_dibs"></b-icon-bookmark>
            <b-icon-bookmark-fill v-else variant="success"></b-icon-bookmark-fill>
          </button>
          {{article.dibs_num}} 
          <!-- 스크랩 카운트 -->
        </div>
        <!---->
      </div>
      <div class="mr-1">
        <!-- 명세에 있는 공유 (url만 복사하면 됨) -->
        <div>
          <button class="h5">
            <b-icon-reply></b-icon-reply>
          </button>
        </div>
      </div>
    </div>
    <div>
      <span class="font-weight-bold">좋아요 {{article.like_num}}명</span>
    </div>
    <div class="wrap mt-2">
      <span class="font-weight-bold">{{article.nick_name}} </span>
      <span>
        <span v-for="tag in article.tag" :key="tag" class="tag">#{{tag}} </span><br>
        <span v-if="!!article.reply_num" class="moreView">댓글 {{article.reply_num}}개</span>
      </span>
      <span class="font-weight-bold">{{article.reply_user_nick}} </span>{{article.reply_content}}<br>
      <span v-if="!detail" class="moreView">댓글 남기기</span>
    </div>
  </div>
</template>

<script>
import defaultImage from "@/assets/images/img-placeholder.png";
import defaultProfile from "@/assets/images/profile_default.png";

import FeedApi from "@/api/FeedApi.js"

export default {
  name: 'feedVoteItem',
  data: () => {
    return { 
      defaultImage, defaultProfile,
      isLong: true,
    }
  },
  props: {
    article: Object,
    fno: Number,
    detail: Boolean,
  },
  computed: {
    createAfter() {
      const today = new Date()
      return parseInt((today-new Date(this.article.create_date)) / (1000*60*60))
    }
  },
  methods: {
    totalNumber() {
      let t = 0
      for (let i=0; i<this.article.content.content.length; i++) {
        t += this.article.content.content[i].count
      }
      this.totalNum = t
    },
    touchLikeIcon() {
      this.article.press_like = !this.article.press_like
      if (this.article.press_like) {
        this.article.like_num ++
        FeedApi.createFeedLike(
          { tno: this.article.fno }
          , res => console.log(res)
          , err => console.log(err))
      }
      else {
        this.article.like_num --
        FeedApi.deleteFeedLike(
          { tno: this.article.fno }
          , res => console.log(res)
          , err => console.log(err))
      }
    },
    touchScrapIcon() {
      this.article.press_dibs = !this.article.press_dibs
      if (this.article.press_dibs) {
        this.article.dibs_num ++
        FeedApi.createDibs(
          {tno: this.article.fno}
          , res => console.log(res)
          , err => console.log(err)
        );
      }
      else {
        this.article.dibs_num --
        FeedApi.deleteDibs(
          this.article.fno
          , res => console.log(res)
          , err => console.log(err)
        );
      }
      // console.log(this.scrapIcon)
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
    delFeed() {
      FeedApi.deleteFeed(
          this.article.fno,
          res=> {
            console.log(res)
            this.$emit('deleteItem', this.article.fno)
          },
          err=> console.log(err)
        )
    },
    updateFeed() {
      this.$router.push({ path:'/feed/create/3/'+this.article.fno })
    }
  },
  updated() {
    this.totalNumber()
  },
  created() {
    if (!this.article.dibs_num) {this.scrapNum = 0}
    if (!this.article.like_num) {this.like_num = 0}
    this.totalNumber()
  }
};
</script>

<style scoped>
/* .content {
 border-bottom: 1px solid gray;
} */
.moreView {
  color: darkgray; 
}
.tag {
  color:deepskyblue;
}
.mythumbnail {
  background-color: beige;
  width: 100v;
  height: 55v;
}
</style>
