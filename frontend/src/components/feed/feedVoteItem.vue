<template>
  <div class="feed-item">
    <div class="top">
      <div class="profile-image" :style="{'background-image': 'url('+defaultProfile+')'}"></div>
      <div class="user-info">
        <div class="user-name">
          <button>{{nick_name}}</button>
        </div>
        <p class="date">{{createAfter}} 시간 전</p>
      </div>
      <div class="content d-flex flex-comlumn justify-content-between align-items-center my-2">
        <div>{{feedTitle}}</div>
        <div v-if="!!this.$store.state.userInfo && this.$store.state.userInfo.uno === writer_uno">
          <span @click="updateFeed">수정</span>   <span @click="delFeed">삭제</span>
        </div>
      </div>
    </div>
    <div class="feed-card">
      <div class="mythumbnail d-flex flex-column justify-content-center align-items-center">
        <div v-for="content in vote" :key="content.id" style="width: 80%;">
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
            <b-icon-heart v-if="!likeIcon"></b-icon-heart>
            <b-icon-heart-fill v-else variant="danger"></b-icon-heart-fill>
          </button>
          {{like_num}}
        </div>
        <!-- 댓글 -->
        <div class="mr-3">
          <button class="h6 mr-1">
            <b-icon-chat></b-icon-chat>
          </button>
          {{reply_num}}
        </div>
        <!-- 스크랩 -->
        <div class="mr-3">
          <button class="h6 mr-1" @click="touchScrapIcon">
            <b-icon-bookmark v-if="!scrapIcon"></b-icon-bookmark>
            <b-icon-bookmark-fill v-else variant="success"></b-icon-bookmark-fill>
          </button>
          {{scrapNum}} 
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
      <span class="font-weight-bold">좋아요 {{like_num}}명</span>
    </div>
    <div class="wrap mt-2">
      <span class="font-weight-bold">{{nick_name}} </span>
      <span>
        <span v-for="tag in tags" :key="tag" class="tag">#{{tag}} </span><br>
        <span v-if="!isLong" @click="changeIsLong" class="moreView">댓글 {{reply_num}}개</span>
      </span>
      <span class="font-weight-bold">{{reply_user_nick}} </span>{{reply_content}}
      <ReplyItem v-if="isLong" :fno="fno"/>
    </div>
  </div>
</template>

<script>
import ReplyItem from "@/components/ReplyItem.vue"
import defaultImage from "@/assets/images/img-placeholder.png";
import defaultProfile from "@/assets/images/profile_default.png";

import FeedApi from "@/api/FeedApi.js"

export default {
  name: 'feedVoteItem',
  data: () => {
    return { 
      defaultImage, defaultProfile,
      feedTitle: '투표투표',
      vote: [
        {id: 0, content:"항목 1", count:10},
        {id: 1, content:"항목 2", count:40},
        {id: 2, content:"항목 3", count:8},
      ],
      totalNum: null,
      tags: ['소통', '맞팔', '너무', '귀엽당', 'ㅎㅎ','소통', '맞팔', '너무', '귀엽당', 'ㅎㅎ'],
      reply: ['wow', '너무 좋아용 ㅎㅎ'],
      additionReply: "",
      likeIcon: false,
      scrapIcon: false,
      like_num: 12,
      reply_num: 11,
      thumbnail: 'asdfasdf',
      writer_uno: 1,
      scrapNum: 12,
      create_date: 'ddddd0',
      isLong: false,
      reply_user_nick: [],
      nick_name: null,
      reply_content: null,
    }
  },
  props: {
    article: Object,
    fno: Number,
  },
  computed: {
    createAfter() {
      const today = new Date()
      return parseInt((today-new Date(this.create_date)) / (1000*60*60))
    }
  },
  components: {
    ReplyItem,
  },
  methods: {
    totalNumber() {
      let t = 0
      for (let i=0; i<this.vote.length; i++) {
        t += this.vote[i].count
      }
      this.totalNum = t
    },
    changeIsLong() {
      this.isLong=true
    },
    touchLikeIcon() {
      this.likeIcon = !this.likeIcon
      if (this.likeIcon) {
        this.like_num ++
        FeedApi.createFeedLike(
          { tno: this.fno }
          , res => console.log(res)
          , err => console.log(err))
      }
      else {
        this.like_num --
        FeedApi.deleteFeedLike(
          { tno: this.fno }
          , res => console.log(res)
          , err => console.log(err))
      }
      // console.log(this.likeIcon)
    },
    touchScrapIcon() {
      this.scrapIcon = !this.scrapIcon
      if (this.scrapIcon) {
        this.scrapNum ++
      }
      else {
        this.scrapNum --
      }
      // console.log(this.scrapIcon)
    },makeData() {
      var jsonObj = {
        title: this.feedTitle,
        content: this.vote,
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
          this.fno,
          res=> {
            console.log(res)
            this.$emit('deleteItem', this.fno)
          },
          err=> console.log(err)
        )
    },
    updateFeed() {
      this.$router.push({ path:'/feed/create/3/'+this.fno })
    }
  },
  updated() {
    this.totalNumber()
  },
  created() {
    this.feedTitle = this.article.content.title
    this.vote = this.article.content.content
    this.tags = this.article.tag
    this.reply = this.article.reply_content
    this.reply_num = this.article.reply_num
    this.thumbnail = this.article.thumbnail
    this.writer_uno = this.article.uno
    this.likeIcon = this.article.press_like
    this.scrapIcon = this.article.press_dibs
    this.reply_user_nick = this.article.reply_user_nick
    this.nick_name = this.article.nick_name
    this.reply_content = this.article.reply_content
    if (!this.article.dibsNum) {this.scrapNum = 0}
    else {this.scrapNum = this.article.dibsNum}
    this.create_date = this.article.create_date
    if (!this.article.like_num) {this.like_num = 0}
    else {this.like_num = this.article.like_num}
    console.log(this.vote)
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
