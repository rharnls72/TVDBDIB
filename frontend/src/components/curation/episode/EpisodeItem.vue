<template>
  <div class="feed-item">
    <div class="top">
      <!-- 추후에 poster url 가져오면 img 태그로 바꿔줄 것 -->
      <div class="profile-image" :style="{'background-image': 'url('+defaultProfile+')'}"></div>
      <div class="user-info mb-2">
        <div class="user-name">
          <button>[{{ curation.name }}]</button>
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
        <div class="like likeScrap mr-3">
          <button v-if="likeIcon" @click="touchLikeIcon">
            <i class="fas fa-heart icon" style="color: red;"></i>
          </button>
          <button v-else @click="touchLikeIcon">
            <i class="fas fa-heart icon"></i>
          </button>
          0
        </div>
        <div class="comment mr-3">
          <svg
            class="svg-inline--fa fa-comment-alt fa-w-16 icon"
            aria-hidden="true"
            data-prefix="far"
            data-icon="comment-alt"
            role="img"
            xmlns="http://www.w3.org/2000/svg"
            viewBox="0 0 512 512"
            data-fa-i2svg
          >
            <path
              fill="currentColor"
              d="M448 0H64C28.7 0 0 28.7 0 64v288c0 35.3 28.7 64 64 64h96v84c0 7.1 5.8 12 12 12 2.4 0 4.9-.7 7.1-2.4L304 416h144c35.3 0 64-28.7 64-64V64c0-35.3-28.7-64-64-64zm16 352c0 8.8-7.2 16-16 16H288l-12.8 9.6L208 428v-60H64c-8.8 0-16-7.2-16-16V64c0-8.8 7.2-16 16-16h384c8.8 0 16 7.2 16 16v288z"
            />
          </svg>
          <!-- <i class="far fa-comment-alt icon"></i> -->
          0
        </div>
        <div class="comment mr-3">
          <!-- 스크랩(찜)을 누르지 않으면 -->
          <i v-if="!scrapIcon" @click="touchScrapIcon" class="far fa-bookmark icon empty"></i>
          <!-- 스크랩(찜)을 누르면 -->
          <i v-else @click="touchScrapIcon" class="fas fa-bookmark icon full"></i>
          0
        </div>
        <!---->
      </div>
      <div class="mr-1">
        <div class="share">
          <svg
            class="svg-inline--fa fa-share-alt fa-w-14 icon"
            aria-hidden="true"
            data-prefix="fas"
            data-icon="share-alt"
            role="img"
            xmlns="http://www.w3.org/2000/svg"
            viewBox="0 0 448 512"
            data-fa-i2svg
          >
            <path
              fill="currentColor"
              d="M352 320c-22.608 0-43.387 7.819-59.79 20.895l-102.486-64.054a96.551 96.551 0 0 0 0-41.683l102.486-64.054C308.613 184.181 329.392 192 352 192c53.019 0 96-42.981 96-96S405.019 0 352 0s-96 42.981-96 96c0 7.158.79 14.13 2.276 20.841L155.79 180.895C139.387 167.819 118.608 160 96 160c-53.019 0-96 42.981-96 96s42.981 96 96 96c22.608 0 43.387-7.819 59.79-20.895l102.486 64.054A96.301 96.301 0 0 0 256 416c0 53.019 42.981 96 96 96s96-42.981 96-96-42.981-96-96-96z"
            />
          </svg>
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
      likeIcon: true,
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