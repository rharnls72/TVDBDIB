<template>
  <div class="feed-item">
    <div class="top">
      <!-- 추후에 poster url 가져오면 img 태그로 바꿔줄 것 -->
      <!-- 에피소드에는 포스터가 없다,, -->
      <div class="profile-image" :style="{'background-image': 'url('+defaultProfile+')'}"></div>
      <div class="user-info mb-2">
        <div class="user-name" @click="goToProgramDetail">
          <button>[{{ curation.pname }}]</button>
        </div>
        <p class="date">{{ curation.broadcast_date }} 방송</p>
      </div>
    </div>
    <!-- <div class="feed-card">
      <div v-if="curation.thumbnail != 'https://image.tmdb.org/t/p/w500'"><img class="mythumbnail" :src="curation.thumbnail" alt="thumbnail-image"></div>
      <div v-else><img class="mythumbnail" :src="defaultImage" alt="default-image"></div>
      <div class="contentsWrap">
        <div class="d-flex justify-content-between">
          <h4 class="title">[{{ curation.pname }}] {{ curation.episode }}화</h4>
        </div>
      </div>
    </div> -->
    <EpisodeThumbnail :curation="curation"/>
    <!---->
    <div class="btn-group wrap justify-content-between">
      <div>
        <!-- 좋아요 -->
        <div class="mr-3">
          <button class="h6 mr-1" @click="touchLikeIcon">
            <b-icon-heart v-if="!curation.press_like"></b-icon-heart>
            <b-icon-heart-fill v-else variant="danger"></b-icon-heart-fill>
          </button>
          {{ curation.like_num }}
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
            <b-icon-bookmark v-if="!curation.press_dibs"></b-icon-bookmark>
            <b-icon-bookmark-fill v-else variant="success"></b-icon-bookmark-fill>
          </button>
          {{ curation.dibs_num }}
        </div>
        <!---->
      </div>
      <div class="mr-1">
        <!-- 우리가 생각한 공유 (해당 게시물에 대한 글 바로 작성) -->
        <div class="mr-2">
          <button class="h5">
            <b-icon-pencil @click="createShare"></b-icon-pencil>
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
    <div v-if="!detail" class="content">
      <p><span style="text-decoration: bold;">{{curation.reply_user_nick}} </span> <span>{{curation.reply_content}}</span></p>
      <p><span v-if="!!curation.reply_num" class="more">댓글 {{curation.reply_num}} 개</span></p>
      <!-- 추후에 댓글 연결!~ -->
      <p><span v-if="!detail" class="more" @click="moveDetail">댓글 남기기</span></p>
    </div>
  </div>
</template>

<script>
import defaultImage from "../../../assets/images/img-placeholder.png";
import defaultProfile from "../../../assets/images/profile_default.png";
import CurationApi from '@/api/CurationApi.js'
import EpisodeThumbnail from '@/components/curation/episode/EpisodeThumbnail.vue'

export default {
  name: 'EpisodeItem',
  data: () => {
    return {
      defaultImage,
      defaultProfile,
      isStretch: false,
    };
  },
  props: {
    curation: Object,
    detail: Boolean,
  },
  components: {
    EpisodeThumbnail,
  },
  methods: {
    goToProgramDetail() {
      this.$router.push({path:`/program/${this.curation.pno}`})
    },
    createShare() {
      this.$router.push({path: `/createShare/1/${this.curation.episode}/${this.curation.pno}/${this.curation.season}`})
    },
    readMore() {
      this.isStretch = !this.isStretch
    },
    touchLikeIcon() {
      this.curation.press_like = !this.curation.press_like
      if (this.curation.press_like) {
        this.curation.like_num ++
        CurationApi.createEpisodeLike(
          {
            tno: this.curation.eno,
          }
          , res => console.log(res)
          , err => console.log(err)
        )
      }
      else {
        this.curation.like_num --
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
      this.curation.press_dibs = !this.curation.press_dibs
      if (this.curation.press_dibs) {
        this.curation.dibs_num ++;

        CurationApi.createEpisodeDibs(
          {tno: this.curation.eno}
          , res => console.log(res)
          , err => console.log(err)
        );
      }
      else {
        this.curation.dibs_num --;

        CurationApi.deleteEpisodeDibs(
          this.curation.eno
          , res => console.log(res)
          , err => console.log(err)
        );
      }
      // console.log(this.scrapIcon)
    },
    addReplyCount() {this.curation.reply_num++},
    moveDetail() {
      this.$router.push({path:`/episode/detail/${this.curation.pno}/${this.curation.season}/${this.curation.episode}`})
    }
  },
  mounted() {
    this.likeIcon = this.curation.press_like;
    this.scrapIcon = this.curation.press_dibs;
    if (!this.curation.dibs_num) {this.curation.dibs_num = 0}
    if (!this.curation.like_num) {this.curation.like_num = 0}
    if (this.curation.summery<=30) {this.isStretch=true}
  },
};
</script>

<style scoped>
  .more {
    color: lightgray;
  }
</style>