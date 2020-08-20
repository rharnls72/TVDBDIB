<template>
  <div class="feed-item mb-0 pt-3 myei">
    <div class="top">
      <div class="d-flex align-items-center mb-3">
        <div class="box profile-image" style="background: #BDBDBD;">
          <img v-if="curation.poster != null" class="profile" :src="curation.poster" alt="">
          <img v-else class="profile" :src="defaultProfile" alt="">
        </div>
        <div class="user-info pl-3">
          <div class="user-name" @click="goToProgramDetail">
            <button>{{ curation.pname }}</button>
          </div>
          <p class="date mb-0">{{ curation.broadcast_date }} 방송</p>
        </div>
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
    <div @click="moveDetail">
      <EpisodeThumbnail :curation="curation"/>
    </div>
    <!---->
    <div class="btn-group wrap justify-content-between">
      <div>
        <!-- 좋아요 -->
        <div class="mr-3">
          <button class="h6 mr-2" @click="touchLikeIcon">
            <b-icon-heart v-if="!curation.press_like"></b-icon-heart>
            <b-icon-heart-fill v-else variant="danger"></b-icon-heart-fill>
          </button>
          <span class="mynumber">{{ curation.like_num }}</span>
        </div>
        <!-- 댓글 -->
        <div class="mr-3">
          <button class="h6 mr-2">
            <b-icon-chat></b-icon-chat>
          </button>
          <span class="mynumber">{{curation.reply_num}}</span>
        </div>
        <!-- 스크랩 -->
        <div class="mr-3">
          <button class="h6 mr-2" @click="touchScrapIcon">
            <b-icon-bookmark v-if="!curation.press_dibs"></b-icon-bookmark>
            <b-icon-bookmark-fill v-else variant="success"></b-icon-bookmark-fill>
          </button>
          <span class="mynumber">{{ curation.dibs_num }}</span>
        </div>
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
            <b-icon-reply @click="copyUrl"></b-icon-reply>
          </button>
        </div>
      </div>
    </div>
    <!-- 내용 더 보기 (현재 페이지에서 펼치기) -->
    <div v-if="!curation.summary">
      <p>{{ curation.summary }}</p>
    </div>
    <div v-else-if="curation.summary.length <= 25">
      <p>{{ curation.summary }}</p>
    </div>
    <div v-else>
      <div v-if="!isStretch" class="d-flex justify-content-between">
        <p class="mb-0">{{ curation.summary.slice(0, 25) }}</p>
        <button @click="readMore" class="more">더 보기</button>
      </div>
      <div v-else>
        <p class="mb-2">{{ curation.summary }}</p>
      </div>
    </div>
    <div v-if="!detail">
      <p v-if="!!curation.reply_num" class="my-1"><strong class="mr-2">{{curation.reply_user_nick}}</strong><span>{{curation.reply_content}}</span></p>
      <p class="myreply mt-1">
        <span v-if="!!curation.reply_num" class="more" @click="moveDetail">댓글 {{curation.reply_num}}개 모두 보기</span>
        <span v-else class="more" @click="moveDetail">댓글 남기기</span>
      </p>
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
    };
  },
  props: {
    curation: Object,
    detail: Boolean,
    isStretch: Boolean,
  },
  components: {
    EpisodeThumbnail,
  },
  methods: {
    makeToast(message, variant){
      this.$bvToast.toast(message, {
        title: '알림',
        toaster: "b-toaster-bottom-center",
        variant: variant,
        autoHideDelay: 3000,
        appendToast: false
      })
    },
    copyToClipboard(val) {
      var t = document.createElement('textarea')
      document.body.appendChild(t)
      t.value = val
      t.select()
      document.execCommand('copy')
      document.body.removeChild(t)
    },
    copyUrl() {
      this.copyToClipboard(`http://i3a106.p.ssafy.io/episode/detail/${this.curation.pno}/${this.curation.season}/${this.curation.episode}`)
      this.makeToast("경로 복사에 성공했습니다.", "danger");
    },
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
  .box {
    width: 40px;
    height: 40px; 
    border-radius: 70%;
    overflow: hidden;
  }
  .profile {
      width: 100%;
      height: 100%;
      object-fit: cover;
  }
  .myreply {
    font-size: 0.9rem;
  }
  .mynumber {
    font-size: 0.8rem;
  }
</style>