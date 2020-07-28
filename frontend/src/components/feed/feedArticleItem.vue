<template>
  <div class="feed-item">
    <div class="top">
      <div class="profile-image" :style="{'background-image': 'url('+defaultProfile+')'}"></div>
      <div class="user-info mb-2">
        <div class="user-name">
          <button>{{article.nick_name}}</button>
        </div>
        <p class="date">9시간 후</p>
      </div>
      <div class="content">
        <p>{{feedTitle}}</p>
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
          0 
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
      <span v-if="isLong">{{cc.content.slice(0, 10)}}... <span class="moreView" @click="changeIsLong">더 보기</span></span>
      <span v-else>
        <span>{{cc.content}}</span><br>
        <span v-for="tag in tags" :key="tag" class="tag">#{{tag}} </span><br>
        <span class="moreView">댓글 {{reply_num}}개</span>
      </span>
    </div>
  </div>
</template>

<script>
import defaultImage from "../../assets/images/img-placeholder.png";
import defaultProfile from "../../assets/images/profile_default.png";
import {mapState} from "vuex"
import header from "@/api/header.js"
import axios from "axios"
export default {
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
      isLong: false,
      cc: null,
      feedTitle: '제목제목',
    };
  },
  computed: {
    ...mapState([
      'userInfo',
    ])
  },
  props:{
    article: Object,
  },
  methods: {
    changeIsLong() {
      this.isLong=false
    },
    pushReply() {
      axios.post('http://localhost:9000/reply/feed/create',
      {
        no: this.article.fno,
        content: this.additionReply,
        writer_uno: 1,
      }, header())
    },
    touchLikeIcon() {
      this.likeIcon = !this.likeIcon
      if (this.likeIcon) {
        this.likeCount ++
      }
      else {
        this.likeCount --
      }
      // console.log(this.likeIcon)
    },
    touchScrapIcon() {
      this.scrapIcon = !this.scrapIcon
      if (this.scrapIcon) {
        this.scrapCount ++
      }
      else {
        this.scrapCount --
      }
      // console.log(this.scrapIcon)
    },
  },
  created() {
    console.log(this.article.content)
    this.cc = JSON.parse(this.article.content)
    console.log(this.cc)
    if (this.cc.content.length > 10) {this.isLong=true}
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
