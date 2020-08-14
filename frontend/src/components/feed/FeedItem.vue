<template>
  <div class="feed-item">
    <div class="top">
      <div class="box profile-image " style="background: #BDBDBD;">
              <img v-if="article.profile_pic != null" class="profile" :src="article.profile_pic" :alt="profile_img">
              <img v-else class="profile" :src="defaultProfile" :alt="profile_img">
          </div>
      <!-- <div class="profile-image" :style="{'background-image': 'url('+defaultProfile+')'}"></div> -->
      <div class="user-info mb-2">
        <div class="user-name">
          <button>{{article.nick_name}}</button>
        </div>
        <p v-if="createAfter <= 60" class="date">{{createAfter}} 시간 전</p>
        <p v-else class="date">{{parseInt(createAfter/60)}} 일 전</p>
      </div>
      <div class="content d-flex flex-comlumn justify-content-between align-items-center my-2">
        <div>{{article.content.title}}</div>
        <div v-if="!!this.$store.state.userInfo && this.$store.state.userInfo.uno === article.uno">
          <span @click="updateFeed">수정</span>   <span @click="delFeed">삭제</span>
        </div>
      </div>
    </div>
    <FeedArticleThumbnail v-if="article.ctype === 1" :article="article"/>
    <FeedCountdownThumbnail v-if="article.ctype === 2" :article="article"/>
    <FeedVoteThumbnail v-if="article.ctype === 3" :article="article"/>
    <FeedShareThumbnail v-if="article.ctype === 4" :article="article"/>
    <!---->
    <div class="btn-group wrap justify-content-between" style="margin: 15px 0 0 0;">
      <div>
        <!-- 좋아요 -->
        <div class="mr-3">
          <button class="h6 mr-1" @click="touchLikeIcon">
            <b-icon-heart v-if="!article.press_like"></b-icon-heart>
            <b-icon-heart-fill v-else variant="danger"></b-icon-heart-fill>
          </button>
          {{article.like_num}}
        </div>
        <!-- 댓글 -->
        <div class="mr-3">
          <button class="h6 mr-1">
            <b-icon-chat></b-icon-chat>
          </button>
          {{article.reply_num}}
        </div>
        <!-- 스크랩 -->
        <div class="mr-3">
          <button class="h6 mr-1" @click="touchScrapIcon">
            <b-icon-bookmark v-if="!article.press_dibs"></b-icon-bookmark>
            <b-icon-bookmark-fill v-else variant="success"></b-icon-bookmark-fill>
          </button>
          {{article.dibs_num}} 
          <!-- 스크랩 카운트 -->
        </div>
        <!---->
      </div>
      <div class="mr-1">
        <div v-if="article.ctype !== 4" class="mr-2">
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
    <div>
      <span class="font-weight-bold">좋아요 {{article.like_num}}명</span>
    </div>
    <div class="wrap mt-2">
      <div v-if="article.ctype===1"><span class="font-weight-bold">{{article.nick_name}} </span>{{article.content.content}}<br></div>
      <div v-if="article.ctype===2"><span class="font-weight-bold">{{article.nick_name}} </span>{{article.content.title}}<br></div>
      <div v-if="article.ctype===3"><span class="font-weight-bold">{{article.nick_name}} </span>{{article.content.title}}<br></div>
      <div v-if="article.ctype===4"><span class="font-weight-bold">{{article.nick_name}} </span>{{article.content.content}}<br></div>
    </div>
    <div class="wrap mt-2">
      <span v-for="tag in article.tag" :key="tag" class="tag">#{{tag}} </span><br>
      <span v-if="!!article.reply_num && !detail" class="moreView">댓글 {{article.reply_num}}개</span><br>
    </div>
    <div v-if="!detail">
      <span class="font-weight-bold">{{article.reply_user_nick}} </span>{{article.reply_content}}<br>
      <span @click="moveDetail" class="moreView">댓글 남기기</span>
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
    console.log(this.article)
  },
  methods: {
    makeToast(message, variant){
      this.$bvToast.toast(message, {
        title: '알림',
        toaster: "b-toaster-bottom-right",
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
      this.makeToast("경로 복사에 성공했습니다.", "primary");
    },
    createShare() {
      this.$router.push({path: `/createShare/0/${this.article.fno}`})
    },
    touchLikeIcon() {
      this.article.press_like = !this.article.press_like
      if (this.article.press_like) {
        this.article.like_num ++
        FeedApi.createFeedLike(
          { tno: this.article.fno }
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
            this.$router.push({path: '/feed/main'})
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
      this.$router.push({path: `/feed/detail/${this.article.fno}`})
    }
  },
  created() {
    if (!this.article.dibs_num) {this.article.dibs_num = 0}
    if (!this.article.like_num) {this.article.like_num = 0}
  }
};
</script>

<style scoped>
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
</style>
