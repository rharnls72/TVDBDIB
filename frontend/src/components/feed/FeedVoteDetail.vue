<template>
  <div class="feed-item">
    <div class="top">
      <div class="profile-image" :style="{'background-image': 'url('+defaultProfile+')'}"></div>
      <div class="user-info">
        <div class="user-name">
          <button>SSAFY</button>
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
      <span v-for="tag in tags" :key="tag" class="tag">#{{tag}} </span><br>
    </div>
    <ReplyItem :fno="fno"/>
  </div>
</template>

<script>
import ReplyItem from "@/components/ReplyItem.vue"
import defaultImage from "@/assets/images/img-placeholder.png";
import defaultProfile from "@/assets/images/profile_default.png";
import {mapState} from 'vuex'
import axios from 'axios'
import header from '@/api/header.js'

export default {
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
      comment: null,
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
    }
  },
  props: {
    article: Object,
    fno: Number,
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
  components: {
    ReplyItem,
  },
  methods: {
    totalNumber() {
      let t = 0
      this.vote.foreach(res => {
        t += Number(res.count)
      })
      this.totalNum = t
    },
    touchLikeIcon() {
      this.likeIcon = !this.likeIcon
      if (this.likeIcon) {
        this.like_num ++
      }
      else {
        this.like_num --
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
    },
    makeData() {
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
      axios.put('http://localhost:9000/feed/update', data, header())
          .then(res => console.log(res))
          .catch(err => console.log(err))
    },
    delFeed() {
      axios.delete('http://localhost:9000/feed/delete/'+this.fno, header())
        .then(res => {
          console.log(res)
          this.$router.push('/feed/main')
        })
        .catch(err => console.log(err))
    },
    updateFeed() {
      this.$router.push({ path:'/feed/create/3/'+this.fno })
    }
  },
  updated() {
    this.totalNumber()
  },
  created() {
    if (this.article !== null) {
      this.feedTitle = this.article.content.title
      this.vote = this.article.content.content
      this.tags = this.article.tag
      this.reply = this.article.reply_content
      this.reply_num = this.article.reply_num
      this.thumbnail = this.article.thumbnail
      this.writer_uno = this.article.uno
      this.likeIcon = this.article.prees_like
      this.scrapIcon = this.article.press_dibs
      if (!this.article.dibsNum) {this.scrapNum = 0}
      else {this.scrapNum = this.article.dibsNum}
      this.create_date = this.article.create_date
      if (!this.article.like_num) {this.like_num = 0}
      else {this.like_num = this.article.like_num}
      console.log(this.vote)
    }
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
