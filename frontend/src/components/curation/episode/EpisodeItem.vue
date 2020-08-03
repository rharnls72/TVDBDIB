<template>
  <div class="feed-item">
    <div class="top">
      <!-- 추후에 poster url 가져오면 img 태그로 바꿔줄 것 -->
      <!-- 에피소드에는 포스터가 없다,, -->
      <div class="profile-image" :style="{'background-image': 'url('+defaultProfile+')'}"></div>
      <div class="user-info mb-2">
        <div class="user-name">
          <button>[{{ curation.pname }}]</button>
        </div>
        <p class="date">{{ curation.broadcast_date }} 방송</p>
      </div>
    </div>
    <div class="feed-card">
      <div v-if="curation.thumbnail != 'https://image.tmdb.org/t/p/w500'"><img class="mythumbnail" :src="curation.thumbnail" alt="thumbnail-image"></div>
      <div v-else><img class="mythumbnail" :src="defaultImage" alt="default-image"></div>
      <div class="contentsWrap">
        <div class="d-flex justify-content-between">
          <h4 class="title">[{{ curation.pname }}] {{ curation.episode }}화</h4>
          <!-- 에피소드에는 장르가 없다,, -->
          <!-- <p class="date">{{ curation.genre }}</p> -->
        </div>
      </div>
    </div>
    <!---->
    <div class="btn-group wrap justify-content-between">
      <div>
        <!-- 좋아요 -->
        <div class="mr-3">
          <button class="h6 mr-1" @click="touchLikeIcon">
            <b-icon-heart v-if="!likeIcon"></b-icon-heart>
            <b-icon-heart-fill v-else variant="danger"></b-icon-heart-fill>
          </button>
          {{ likeCount }}
        </div>
        <!-- 댓글 -->
        <div class="mr-3">
          <button class="h6 mr-1">
            <b-icon-chat></b-icon-chat>
          </button>
          {{curation.reply_num}}
        </div>
        <!-- 스크랩 -->
        <div class="mr-3">
          <button class="h6 mr-1" @click="touchScrapIcon">
            <b-icon-bookmark v-if="!scrapIcon"></b-icon-bookmark>
            <b-icon-bookmark-fill v-else variant="success"></b-icon-bookmark-fill>
          </button>
          {{ scrapCount }}
        </div>
        <!---->
      </div>
      <div class="mr-1">
        <!-- 우리가 생각한 공유 (해당 게시물에 대한 글 바로 작성) -->
        <!-- <div class="mr-2">
          <button class="h5">
            <b-icon-pencil></b-icon-pencil>
          </button>
        </div> -->
        <!-- 명세에 있는 공유 (url만 복사하면 됨) -->
        <div>
          <button class="h5">
            <b-icon-reply></b-icon-reply>
          </button>
        </div>
      </div>
    </div>
    <!-- 내용 더 보기 (현재 페이지에서 펼치기) -->
    <div v-if="!curation.summary" class="content">
      <p>{{ curation.summary }}</p>
    </div>
    <div v-else-if="curation.summary.length <= 30" class="content">
      <p>{{ curation.summary }}</p>
    </div>
    <div v-else class="content">
      <div v-if="!isStretch" class="d-flex justify-content-between">
        <p>{{ curation.summary.slice(0, 30) }}</p>
        <button @click="readMore" class="more">더 보기</button>
      </div>
      <div v-else>
        <p>{{ curation.summary }}</p>
      </div>
    </div>
    <p><span style="text-decoration: bold;">{{curation.reply_user_nick}} </span> <span>{{curation.reply_content}}</span></p>
    <!-- 추후에 댓글 연결!~ -->
    <ReplyItem :eno="curation.eno" @addReply="addReplyCount"/>
    <!-- <div class="content">
      <p>댓글이야 댓글 댓글!~</p>
      <p class="more">댓글 1개</p>
    </div> -->
    <!---->
    <!---->
  </div>
</template>

<script>
import defaultImage from "../../../assets/images/img-placeholder.png";
import defaultProfile from "../../../assets/images/profile_default.png";
import ReplyItem from '../../ReplyItem.vue'
import CurationApi from '@/api/CurationApi.js'

export default {
  name: 'EpisodeItem',
  data: () => {
    return {
      defaultImage,
      defaultProfile,
      isStretch: false,
      likeIcon: false,
      scrapIcon: false,
      likeCount: 0,
      scrapCount: 0,
      reply_num: null,
    };
  },
  props: {
    curation: Object,
  },
  components: {
    ReplyItem,
  },
  methods: {
    readMore() {
      this.isStretch = !this.isStretch
    },
    touchLikeIcon() {
      this.likeIcon = !this.likeIcon
      if (this.likeIcon) {
        this.likeCount ++
        CurationApi.createEpisodeLike(
          {
            tno: this.curation.eno,
          }
          , res => console.log(res)
          , err => console.log(err)
        )
      }
      else {
        this.likeCount --
        CurationApi.deleteEpisodeLike(
          {
            tno: this.curation.eno,
          }
          , res => console.log(res)
          , err => console.log(err)
        )
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
    addReplyCount() {this.curation.reply_num++}
  },
  created() {
    this.likeIcon = this.curation.press_like
    if (!this.curation.dibs_num) {this.scrapCount = 0}
    else {this.scrapCount = this.dibs_num.dibs_num}
    if (!this.curation.like_num) {this.likeCount = 0}
    else {this.likeCount = this.curation.like_num}
    if (this.curation.summery<=30) {this.isStretch=true}
  },
};
</script>

<style scoped>
  .more {
    color: lightgray;
  }
  .mythumbnail {
    width: 100%;
    height: auto;
  }
</style>