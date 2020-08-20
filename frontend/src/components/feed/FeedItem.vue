<template>
  <div class="feed-item mb-0 pt-3">
    <div class="top">
      <div class="d-flex align-items-center mb-3">
        <div class="box profile-image" style="background: #BDBDBD;" @click="goUserProfile">
          <img v-if="article.profile_pic != null" class="profile" :src="article.profile_pic" :alt="article.profile_pic">
          <img v-else class="profile" :src="defaultProfile" alt="">
        </div>
        <div class="user-info pl-3">
          <div class="user-name" @click="goUserProfile">
            <button>{{ article.nick_name }}</button>
          </div>
          <p v-if="createAfter<=24" class="date mb-0">{{ createAfter }}시간 전</p>
          <p v-else class="date mb-0">{{ parseInt(createAfter/24) }}일 전</p>
        </div>
      </div>
    </div>
    <div v-if="article.ctype === 1" @click="moveDetail">
      <FeedArticleThumbnail :article="article"/>
    </div>
    <div v-if="article.ctype === 2" @click="moveDetail">
      <FeedCountdownThumbnail :article="article"/>
    </div>
    <div v-if="article.ctype === 3">
      <FeedVoteThumbnail :article="article"/>
    </div>
    <div v-if="article.ctype === 4" @click="moveDetail">
      <FeedShareThumbnail :article="article"/>
    </div>
    <div class="btn-group wrap justify-content-between" style="margin: 15px 0 0 0;">
      <div>
        <!-- 좋아요 -->
        <div class="mr-3">
          <button class="h6 mr-2" @click="touchLikeIcon">
            <b-icon-heart v-if="!article.press_like"></b-icon-heart>
            <b-icon-heart-fill v-else variant="danger"></b-icon-heart-fill>
          </button>
          <span class="mynumber">{{ article.like_num }}</span>
        </div>
        <!-- 댓글 -->
        <div class="mr-3">
          <button class="h6 mr-2" @click="moveDetail">
            <b-icon-chat></b-icon-chat>
          </button>
          <span class="mynumber">{{article.reply_num}}</span>
        </div>
        <!-- 스크랩 -->
        <div class="mr-3">
          <button class="h6 mr-2" @click="touchScrapIcon">
            <b-icon-bookmark v-if="!article.press_dibs"></b-icon-bookmark>
            <b-icon-bookmark-fill v-else variant="success"></b-icon-bookmark-fill>
          </button>
          <span class="mynumber">{{ article.dibs_num }}</span>
        </div>
        <!---->
      </div>
      <div class="mr-1">
        <!-- 명세에 있는 공유 (url만 복사하면 됨) -->
        <div>
          <button class="h5">
            <b-icon-reply @click="copyUrl"></b-icon-reply>
          </button>
        </div>
      </div>
    </div>
    <div class="d-flex justify-content-between align-items-center mb-2">
      <div v-if="article.content.title" class="font-weight-bold">{{ article.content.title }}</div>
      <div v-else class="font-weight-bold">{{ article.content.content }}</div>
      <div v-if="!!this.$store.state.userInfo && this.$store.state.userInfo.uno === article.uno">
        <span @click="updateFeed"><b-icon-wrench></b-icon-wrench></span>
        <span @click="delFeed" class="ml-2"><b-icon-trash></b-icon-trash></span>
      </div>
    </div>
    <div>
      <div class="my-2" v-if="article.ctype === 1"><span>{{article.content.content}}</span><br></div>
      <span v-for="tag in article.tag" :key="tag" class="tag">#{{tag}} </span>
    </div>
    <div v-if="!detail">
      <p v-if="!!article.reply_num" class="my-1"><strong class="mr-2">{{article.reply_user_nick}}</strong><span>{{article.reply_content}}</span></p>
      <p class="myreply mt-1">
        <span v-if="!!article.reply_num" class="more" @click="moveDetail">댓글 {{article.reply_num}}개 모두 보기</span>
        <span v-else class="more" @click="moveDetail">댓글 남기기</span>
      </p>
    </div>
  </div>
</template>

<script>
import defaultImage from "../../assets/images/img-placeholder.png";
import defaultProfile from "../../assets/images/profile_default.png";

import FeedApi from "@/api/FeedApi.js"

import FeedArticleThumbnail from "@/components/feed/feedThumbnail/FeedArticleThumbnail.vue"
import FeedCountdownThumbnail from "@/components/feed/feedThumbnail/FeedCountdownThumbnail.vue"
import FeedVoteThumbnail from "@/components/feed/feedThumbnail/FeedVoteThumbnail.vue"
import FeedShareThumbnail from "@/components/feed/feedThumbnail/FeedShareThumbnail.vue"

export default {
  name: 'FeedItem',
  data: () => {
    return { 
      defaultImage, defaultProfile,
      isLong: true,
    };
  },
  components: {
    FeedArticleThumbnail,
    FeedCountdownThumbnail,
    FeedVoteThumbnail,
    FeedShareThumbnail
  },
  computed: {
    createAfter() {
      const today = new Date()
      return parseInt((today-new Date(this.article.create_date)) / (1000*60*60))
    }
  },
  props:{
    article: Object,
    fno: Number,
    detail: Boolean,
  },
  mounted() {
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
      this.copyToClipboard(`http://i3a106.p.ssafy.io/feed/detail/${this.article.fno}`)
      this.makeToast("경로 복사에 성공했습니다.", "danger");
    },
    createShare() {
      this.$router.push({path: `/createShare/0/${this.article.fno}`})
    },
    touchLikeIcon() {
      this.article.press_like = !this.article.press_like
      if (this.article.press_like) {
        this.article.like_num ++
        FeedApi.createFeedLike(
          { tno: this.article.fno, uno: this.article.uno }
          , res => console.log(res)
          , err => console.log(err))
      }
      else {
        this.article.like_num --
        FeedApi.deleteFeedLike(
          { tno: this.article.fno }
          , res => console.log(res)
          , err => console.log(err))
      }
    },
    touchScrapIcon() {
      this.article.press_dibs = !this.article.press_dibs
      if (this.article.press_dibs) {
        this.article.dibs_num ++
        FeedApi.createDibs(
          {tno: this.article.fno}
          , res => console.log(res)
          , err => console.log(err)
        );
      }
      else {
        this.article.dibs_num --
        FeedApi.deleteDibs(
          this.article.fno
          , res => console.log(res)
          , err => console.log(err)
        );
      }
    },
    delFeed() {
      FeedApi.deleteFeed(
          this.article.fno,
          res=> {
            console.log(res)

            let currentPath = this.$router.currentRoute.path;
            if(currentPath.indexOf('/feed/main') == -1) {
              this.$router.push({path: '/feed/main'})
            } else {
              console.log('Current path: ' + currentPath);
              this.$emit('deleteItem');
            }
          },
          err=> console.log(err)
        )
    },
    updateFeed() {
      if (this.article.ctype < 4) {
        this.$router.push({ path:`/feed/create/${this.article.ctype}/${this.article.fno}` })
      } else if (this.article.ctype === 4) {
        this.$router.push({ path:`/feed/edit/${this.article.fno}`})
      }
    },
    moveDetail() {
      let currentPath = this.$router.currentRoute.path;
      if(currentPath.indexOf('/feed/detail') == -1) {
        this.$router.push({path: `/feed/detail/${this.article.fno}`})
      } else {
        console.log('Current path: ' + currentPath);
      }
    },
    goUserProfile() {
      this.$router.push("/profile/" + this.article.nick_name);
    }
  },
  created() {
    if (!this.article.dibs_num) {this.article.dibs_num = 0}
    if (!this.article.like_num) {this.article.like_num = 0}
  }
};
</script>

<style scoped>
  .more {
    color: lightgray;
  }
  .tag {
    color: rgb(110, 144, 253);
    font-weight: bold;
  }
  .mythumbnail {
    background-color: #f8e8f2;
    width: 100v;
    height: 55v;
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
  .mynumber {
    font-size: 0.8rem;
  }
  .myreply {
    font-size: 0.9rem;
  }
</style>
