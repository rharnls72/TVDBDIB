<template>
  <div class="feed-item">
    <div class="top">
      <div class="box profile-image" style="background: #BDBDBD;">
              <img v-if="article.profile_pic != null" class="profile" :src="article.profile_pic" alt="">
              <img v-else class="profile" :src="defaultProfile" alt="">
          </div>
      <div class="user-info">
        <div class="user-name">
          <button>{{article.nick_name}}</button>
        </div>
        <p class="date">{{createAfter}} 시간 전</p>
      </div>
      <div class="content d-flex flex-comlumn justify-content-between align-items-center my-2">
        <div>{{article.content.content.title}}</div>
        <div v-if="!!this.$store.state.userInfo && this.$store.state.userInfo.uno === article.uno">
          <span @click="updateFeed">수정</span>   <span @click="delFeed">삭제</span>
        </div>
      </div>
    </div>
    <div class="feed-card">
      <div class="mythumbnail d-flex flex-column justify-content-center align-items-center">
        <div class="py-5 d-flex justify-content-center py-3">
          <div>
            <div class="time">{{Value.date}}</div>
            <p class="text-center">일</p>
          </div>
          <div>
            <div class="time">{{Value.hour}}</div>
            <p class="text-center">시간</p>
          </div>
          <div>
            <div class="time">{{Value.min}}</div>
            <p class="text-center">분</p>
          </div>
          <div>
            <div class="time">{{Value.sec}}</div>
            <p class="text-center">초</p>
          </div>
        </div>  
      </div>
    </div>
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
        <!-- 명세에 있는 공유 (url만 복사하면 됨) -->
        <div>
          <button class="h5">
            <b-icon-reply></b-icon-reply>
          </button>
        </div>
      </div>
    </div>
    <div>
      <span class="font-weight-bold">좋아요 {{article.like_num}}명</span>
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
    <!---->
    <!---->
</template>

<script>
import defaultImage from "../../assets/images/img-placeholder.png";
import defaultProfile from "../../assets/images/profile_default.png";

import FeedApi from "@/api/FeedApi.js"

export default {
    name: 'feedCountdownItem',
    data() {
      return {
        defaultImage, defaultProfile,
        Value: {
          date: "00",
          hour: "00",
          min: "00",
          sec: "00"
        },
      }
    },
    props: {
      article: Object,
      fno: Number,
      detail: Boolean,
    },
    computed: {
      createAfter() {
        const today = new Date()
        return parseInt((today-new Date(this.article.create_date)) / (1000*60*60))
      }
    },
    methods: {
      changeString(str) {
        str = String(str)
        if (str.length === 1) {
          return '0'+str
        }
        return str
      },
      timer() {
        let today = new Date()
        let selectDate = new Date(this.article.content.content.date+'T'+this.article.content.content.time)

        let countdown = parseInt((selectDate - today) / 1000)

        this.Value.sec = this.changeString(countdown % 60)
        countdown = parseInt(countdown / 60)
        this.Value.min = this.changeString(countdown % 60)
        countdown = parseInt(countdown / 60)
        this.Value.hour = this.changeString(countdown % 24)
        this.Value.date = this.changeString(parseInt(countdown / 24))
      },
      submitDateTime() {
        this.timer()
        setInterval(this.timer, 1000)
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
      delFeed() {
        FeedApi.deleteFeed(
          this.article.fno,
          res=> {
            console.log(res)
            this.$emit('deleteItem', this.article.fno)
          },
          err=> console.log(err)
        )
      },
      updateFeed() {
        this.$router.push({ path:'/feed/create/2/'+this.article.fno })
      },
      moveDetail() {
        this.$router.push({path: `/feed/detail/${this.article.fno}`})
      }
    },
    created() {
      if (!this.article.dibs_num) {this.scrapNum = 0}
      if (!this.article.like_num) {this.like_num = 0}
    },
    mounted() {
      console.log(this.article)
      this.submitDateTime()
    }
  }
</script>

<style scoped>

.time {
  width: 40px;
  margin-right: 10px;
  margin-left: 10px;
  border-bottom: 1px solid black;
  text-align: center;
  font-weight: 700;
}
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
