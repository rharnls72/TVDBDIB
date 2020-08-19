<template>
  <div class="feed-item">
    <div class="top">
      <div class="box profile-image" style="background: #BDBDBD;">
          <img v-if="article.profile_pic != null" class="profile" :src="article.profile_pic" :alt="article.profile_pic">
          <img v-else class="profile" :src="defaultProfile" alt="">
      </div>
      <!-- <div class="profile-image" :style="{'background-image': 'url('+defaultProfile+')'}"></div> -->
      <div class="user-info mb-2">
        <div class="user-name">
          <button>{{article.nick_name}}</button>
        </div>
        <p v-if="createAfter <= 24" class="date">{{createAfter}} 시간 전</p>
        <p v-else class="date">{{parseInt(createAfter/24)}} 일 전</p>
      </div>
    </div>
    <div class="comment">
      <div>
        <div v-if="article.ctype === 1 || article.ctype === 4"><span>{{article.content.content}}</span><br></div>
        <span v-for="tag in article.tag" :key="tag" class="tag">#{{tag}} </span>
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
          <button class="h6 mr-1" @click="moveDetail">
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
        <!-- 명세에 있는 공유 (url만 복사하면 됨) -->
        <div>
          <button class="h5">
            <b-icon-reply @click="copyUrl"></b-icon-reply>
          </button>
        </div>
      </div>
    </div>
    <div class="d-flex justify-content-between align-items-center">
      <div class="font-weight-bold">좋아요 {{article.like_num}}명</div>
      <div v-if="!!this.$store.state.userInfo && this.$store.state.userInfo.uno === article.uno">
        <span class="moreView" @click="updateFeed">수정 </span>
        <span class="moreView" @click="delFeed">삭제</span>
      </div>
    </div>
    <div v-if="!!article.reply_num && !detail" class="wrap mt-2">
      <span class="moreView">댓글 {{article.reply_num}}개</span><br>
    </div>
    <div v-if="!detail">
      <div v-if="article.reply_user_nick"><span class="font-weight-bold">{{article.reply_user_nick}} </span>{{article.reply_content}}<br></div>
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
.comment {
  margin-top: 60px;
  margin-bottom: 20px;
}
</style>
