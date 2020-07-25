<template>
  <div class="feed-item">
    <div class="top">
      <!-- 추후에 poster url 가져오면 img 태그로 바꿔줄 것 -->
      <div class="profile-image" :style="{'background-image': 'url('+defaultProfile+')'}"></div>
      <div class="user-info mb-2">
        <div class="user-name">
          <button>[{{ curation.name }}] OO화</button>
        </div>
        <p class="date">{{ curation.broadcast_time }} 방송 예정</p>
      </div>
    </div>
    <div class="feed-card">
      <!-- 추후에 thumbnail url 가져오면 img 태그로 바꿔줄 것 -->
      <div v-if="curation.thumbnail" class="img thumbnail-color">{{ curation.thumbnail }}</div>
      <div v-else class="img" :style="{'background-image': 'url('+defaultImage+')'}"></div>
      <div class="contentsWrap">
        <div class="d-flex justify-content-between">
          <h4 class="title">[{{ curation.name }}] OO화</h4>
          <p class="date">{{ curation.genre }}</p>
        </div>
          <!-- <div class="url">
            <a href="https://brunch.co.kr/@@63JW/25">https://brunch.co.kr/@@63JW/25</a>
          </div>
          <p class="date">2020.06.18</p> -->
      </div>
    </div>
    <!---->
    <div class="btn-group wrap justify-content-between">
      <div>
        <!-- 좋아요 -->
        <div class="mr-3">
          <!-- 1. 스마일 -->
          <button class="h6 mr-1" @click="touchLikeIcon">
            <b-icon-emoji-smile v-if="!likeIcon"></b-icon-emoji-smile>
            <b-icon-emoji-smile v-else class="rounded-circle bg-warning" variant="light"></b-icon-emoji-smile>
          </button>
          <!-- 2. 하트 -->
          <!-- <button class="h6" @click="touchLikeIcon">
            <b-icon-heart v-if="!likeIcon" variant="danger"></b-icon-heart>
            <b-icon-heart-fill v-else variant="danger"></b-icon-heart-fill>
          </button> -->
          0
        </div>
        <!-- 댓글 -->
        <div class="mr-3">
          <button class="h6 mr-1">
            <b-icon-chat></b-icon-chat>
          </button>
          0
        </div>
        <!-- 스크랩 -->
        <div class="mr-3">
          <button class="h6 mr-1" @click="touchScrapIcon">
            <b-icon-bookmark v-if="!scrapIcon"></b-icon-bookmark>
            <b-icon-bookmark-fill v-else variant="success"></b-icon-bookmark-fill>
          </button>
          0
        </div>
        <!---->
      </div>
      <div class="mr-1">
        <!-- 우리가 생각한 공유 (해당 게시물에 대한 글 바로 작성) -->
        <div class="mr-2">
          <button class="h5">
            <b-icon-pencil></b-icon-pencil>
          </button>
        </div>
        <!-- 명세에 있는 공유 (url만 복사하면 됨) -->
        <div>
          <button class="h5">
            <b-icon-reply></b-icon-reply>
          </button>
        </div>
      </div>
    </div>
    <!-- 내용 더 보기 (현재 페이지에서 펼치기) -->
    <div v-if="!curation.description" class="content">
      <p>{{ curation.description }}</p>
    </div>
    <div v-else-if="curation.description.length <= 18" class="content">
      <p>{{ curation.description }}</p>
    </div>
    <div v-else class="content">
      <div v-if="!isStretch" class="d-flex justify-content-between">
        <p>{{ curation.description.slice(0, 18) }}</p>
        <button @click="readMore" class="more">더 보기</button>
      </div>
      <div v-else>
        <p>{{ curation.description }}</p>
      </div>
    </div>
    <!---->
    <!---->
  </div>
</template>

<script>
import defaultImage from "../../../assets/images/img-placeholder.png";
import defaultProfile from "../../../assets/images/profile_default.png";

export default {
  name: 'EpisodeItem',
  data: () => {
    return {
      defaultImage,
      defaultProfile,
      isStretch: false,
      likeIcon: false,
      scrapIcon: false,
    };
  },
  props: {
    curation: Object,
  },
  methods: {
    readMore() {
      this.isStretch = !this.isStretch
    },
    touchLikeIcon() {
      this.likeIcon = !this.likeIcon
      console.log(this.likeIcon)
    },
    touchScrapIcon() {
      this.scrapIcon = !this.scrapIcon
      console.log(this.scrapIcon)
    },
  },
};
</script>

<style scoped>
  .more {
    color: lightgray;
  }
  .thumbnail-color {
    background-color: lightgray;
  }
</style>