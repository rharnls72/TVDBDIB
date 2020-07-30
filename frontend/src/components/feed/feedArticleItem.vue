<template>
  <div class="feed-item">
    <div class="top">
      <div class="profile-image" :style="{'background-image': 'url('+defaultProfile+')'}"></div>
      <div class="user-info mb-2">
        <div class="user-name">
          <button>{{article.nick_name}}</button>
        </div>
        <p class="date">{{createAfter}} 시간 전</p>
      </div>
      <div class="content d-flex flex-comlumn justify-content-between align-items-center my-2">
        <div>{{feedTitle}}</div>
        <b-icon v-b-toggle.sidebar-1 icon="three-dots-vertical" font-scale="1.3"></b-icon>
        <b-sidebar id="sidebar-1" shadow>
          <div class="ml-3">
            <div @click="updateFeed">수정</div>
            <div @click="delFeed">삭제</div>
          </div>
        </b-sidebar>
      </div>
    </div>
    <div class="feed-card">
      <div class="mythumbnail d-flex justify-content-center align-items-center">
        <!-- <p v-for="tag in article.tag" :key="tag" class="py-5">
          #{{tag}}
        </p> -->
        <div class="px-3 py-5">
          <span v-for="tag in tags" :key="tag">
            #{{tag}}
          </span>
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
        </div>
        <!-- 댓글 -->
        <div class="mr-3">
          <button class="h6 mr-1">
            <b-icon-chat></b-icon-chat>
          </button>
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
      <span class="font-weight-bold">유저이름 </span>
      <span>
        <span>{{content}}</span><br>
        <span v-for="tag in tags" :key="tag" class="tag">#{{tag}} </span><br>
        <span v-if="!isLong" @click="changeIsLong" class="moreView">댓글 {{reply_num}}개</span>
      </span>
      <ReplyItem v-if="isLong" :fno="fno"/>
    </div>
  </div>
</template>

<script>
import ReplyItem from "@/components/ReplyItem.vue"
import defaultImage from "../../assets/images/img-placeholder.png";
import defaultProfile from "../../assets/images/profile_default.png";
import {mapState} from "vuex"
import FeedApi from '@/api/FeedApi'
export default {
  name: 'feedArticleItem',
  data: () => {
    return { 
      defaultImage, defaultProfile,
      content: 'ㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎ',
      comment: null,
      tags: ['소통', '맞팔', '너무', '귀엽당', 'ㅎㅎ','소통', '맞팔', '너무', '귀엽당', 'ㅎㅎ'],
      reply: ['wow', '너무 좋아용 ㅎㅎ'],
      additionReply: "",
      likeIcon: false,
      scrapIcon: false,
      like_num: 12,
      reply_num: 11,
      feedTitle: '제목제목',
      thumbnail: 'asdfasdf',
      writer_uno: 1,
      scrapNum: 12,
      create_date: 'ddddd0',
      isLong: false,
    };
  },
  components: {
    ReplyItem,
  },
  computed: {
    ...mapState([
      'userInfo',
    ]),
    createAfter() {
      const today = new Date()
      return parseInt((today-new Date(this.create_date)) / (1000*60*60))
    }
  },
  props:{
    article: Object,
    fno: Number,
  },
  methods: {
    changeIsLong() {
      this.isLong=true
    },
    touchLikeIcon() {
      this.likeIcon = !this.likeIcon
      if (this.likeIcon) {
        this.like_num ++
        FeedApi.createLike(
          {
            uno: this.$store.state.userInfo.uno,
            tno: this.fno
          },
          res => {},
          error => {
            this.$router.push({name:'Errors', query: {message: error.msg}})
          }
        );
      }
      else {
        this.like_num --
        FeedApi.LikeDelete(
          {
            uno: this.$store.state.userInfo.uno,
            tno: this.fno
          },
          res => {},
          error => {
            this.$router.push({name:'Errors', query: {message: error.msg}})
          }
        );
      }
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
    },
    delFeed() {
      FeedApi.feedDelete(
        { fno: this.fno },
        res => { this.$router.push('/feed/main') },
        error => {
          this.$router.push({name:'Errors', query: {message: error.msg}})
        }
      );
    },
    updateFeed() {
      this.$router.push({ path:'/feed/create/1/'+this.fno })
    }
  },
  created() {
    this.feedTitle = this.article.content.title
    this.content = this.article.content.content
    this.tags = this.article.tag
    this.reply = this.article.reply_content
    this.reply_num = this.article.reply_num
    this.thumbnail = this.article.thumbnail
    this.writer_uno = this.article.uno
    this.likeIcon = this.article.press_like
    this.scrapIcon = this.article.press_dibs
    if (!this.article.dibsNum) {this.scrapNum = 0}
    else {this.scrapNum = this.article.dibsNum}
    this.create_date = this.article.create_date
    if (!this.article.like_num) {this.like_num = 0}
    else {this.like_num = this.article.like_num}
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
