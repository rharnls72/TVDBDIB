<template>
  <div class="feed-item">
    <div class="top">
      <!-- 추후에 poster url 가져오면 img 태그로 바꿔줄 것 -->
      <div class="profile-image" :style="{'background-image': 'url('+defaultProfile+')'}"></div>
      <div class="user-info mb-2">
        <div class="user-name">
          <button>[프로그램] {{ curation.name }}</button>
        </div>
        <p class="date"><button class="btn btn-info btn-sm p-1 follow-button">팔로우</button></p>
      </div>
    </div>
    <div class="feed-card">
      <!-- 추후에 thumbnail url 가져오면 img 태그로 바꿔줄 것 -->
      <div v-if="curation.thumbnail" class="img thumbnail-color">{{ curation.thumbnail }}</div>
      <div v-else class="img" :style="{'background-image': 'url('+defaultImage+')'}"></div>
      <div class="contentsWrap">
        <div class="d-flex justify-content-between">
          <h4 class="title">[{{ curation.name }}]</h4>
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
        <div class="like mr-3">
          <button class="h6" @click="touchLikeIcon">
            <b-icon-emoji-smile v-if="!likeIcon"></b-icon-emoji-smile>
            <b-icon-emoji-smile v-else class="bg-warning" variant="light"></b-icon-emoji-smile>
          </button>
          <!-- <button class="h6" @click="touchLikeIcon">
            <b-icon-heart v-if="!likeIcon" variant="danger"></b-icon-heart>
            <b-icon-heart-fill v-else variant="danger"></b-icon-heart-fill>
          </button> -->
          0
        </div>
        <div class="comment mr-3">
          <button class="h6">
            <b-icon-chat></b-icon-chat>
          </button>
          0
        </div>
        <!---->
      </div>
            <div class="mr-1">
        <div class="share">
          <button class="h6">
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
  name: 'ProgramItem',
  data: () => {
    return {
      defaultImage,
      defaultProfile,
      isStretch: false,
    };
  },
  props: {
    curation: Object,
  },
  methods: {
    readMore() {
      this.isStretch = !this.isStretch
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
  .follow-button {
    height: auto;
    line-height: 10px;
    box-shadow: none;
  }
</style>