<template>
  <div class="feed-item">
    <div class="top">
      <div class="profile-image" :style="{'background-image': 'url('+defaultProfile+')'}"></div>
      <div class="user-info">
        <div class="user-name">
          <button>{{nick_name}}</button>
        </div>
        <p class="date">{{createAfter}} 시간 전</p>
      </div>
      <div class="content d-flex flex-comlumn justify-content-between align-items-center my-2">
        <div>{{feedTitle}}</div>
        <div v-if="!!this.$store.state.userInfo && this.$store.state.userInfo.uno === writer_uno">
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
            <b-icon-heart v-if="!likeIcon"></b-icon-heart>
            <b-icon-heart-fill v-else variant="danger"></b-icon-heart-fill>
          </button>
          {{like_num}}
        </div>
        <!-- 댓글 -->
        <div class="mr-3">
          <button class="h6 mr-1">
            <b-icon-chat></b-icon-chat>
          </button>
          {{reply_num}}
        </div>
        <!-- 스크랩 -->
        <div class="mr-3">
          <button class="h6 mr-1" @click="touchScrapIcon">
            <b-icon-bookmark v-if="!scrapIcon"></b-icon-bookmark>
            <b-icon-bookmark-fill v-else variant="success"></b-icon-bookmark-fill>
          </button>
          {{scrapNum}}
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
      <span class="font-weight-bold">좋아요 {{like_num}}명</span>
    </div>
    <div class="wrap mt-2">
      <span v-for="tag in tags" :key="tag" class="tag">#{{tag}} </span><br>
      <span v-if="!isStretch" class="moreView" @click="turnStretch">댓글 {{reply_num}}개</span>
    </div>
    <span class="font-weight-bold">{{reply_user_nick}} </span>{{reply_content}}
    <ReplyItem v-if="isStretch" :fno="fno" :uno="writer_uno"/>
  </div>
    <!---->
    <!---->
</template>

<script>
import ReplyItem from "@/components/ReplyItem.vue"
import defaultImage from "../../assets/images/img-placeholder.png";
import defaultProfile from "../../assets/images/profile_default.png";

import FeedApi from "@/api/FeedApi.js"

export default {
    name: 'feedCountdownItem',
    data() {
      return {
        defaultImage, defaultProfile,
        feedTitle: "카운트다운!!",
        Value: {
          date: "00",
          hour: "00",
          min: "00",
          sec: "00"
        },
        date: "2020-08-31",
        Time: "21:35:30",
        tags: ['소통', '맞팔', '너무', '귀엽당', 'ㅎㅎ'],
        reply: ['wow', '너무 좋아용 ㅎㅎ'],
        like_num: 12,
        reply_num: 11,
        likeIcon: false,
        scrapIcon: false,
        isStretch: false,
        create_date: null,
        reply_content: null,
        reply_user_nick: [],
        nick_name: null,
        writer_uno: null,
      }
    },
    props: {
      article: Object,
      fno: Number,
    },
    components: {
      ReplyItem,
    },
    computed: {
      createAfter() {
        const today = new Date()
        return parseInt((today-new Date(this.create_date)) / (1000*60*60))
      }
    },
    methods: {
      turnStretch() {
        this.isStretch = true
      },
      changeString(str) {
        str = String(str)
        if (str.length === 1) {
          return '0'+str
        }
        return str
      },
      timer() {
        let today = new Date()
        let selectDate = new Date(this.date+'T'+this.Time)

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
      touchLikeIcon() {
        this.likeIcon = !this.likeIcon
        if (this.likeIcon) {
          this.like_num ++
          FeedApi.createFeedLike(
          { tno: this.fno }
          , res => console.log(res)
          , err => console.log(err))
        }
        else {
          this.like_num --
          FeedApi.deleteFeedLike(
          { tno: this.fno }
          , res => console.log(res)
          , err => console.log(err))
        }
      },
      touchScrapIcon() {
        this.scrapIcon = !this.scrapIcon
        if (this.scrapIcon) {
          this.scrapNum ++
        }
        else {
          this.scrapNum --
        }
        // console.log(this.scrapIcon)
      },
      delFeed() {
        FeedApi.deleteFeed(
          this.fno,
          res=> {
            console.log(res)
            this.$emit('deleteItem', this.fno)
          },
          err=> console.log(err)
        )
      },
      updateFeed() {
        this.$router.push({ path:'/feed/create/2/'+this.fno })
      }
    },
    watch: {
      date: function(n, o) {
        console.log(this.date)
        this.submitDateTime()
      }
    },
    created() {
      console.log(111)
      this.feedTitle = this.article.content.content.title
      this.date = this.article.content.content.date
      this.Time = this.article.content.content.time
      this.tags = this.article.tag
      this.reply = this.article.reply_content
      this.reply_num = this.article.reply_num
      this.thumbnail = this.article.thumbnail
      this.writer_uno = this.article.uno
      this.likeIcon = this.article.press_like
      this.scrapIcon = this.article.press_dibs
      this.reply_content = this.article.reply_content
      this.reply_user_nick = this.article.reply_user_nick
      this.nick_name = this.article.nick_name
      if (!this.article.dibsNum) {this.scrapNum = 0}
      else {this.scrapNum = this.article.dibsNum}
      this.create_date = this.article.create_date
      if (!this.article.like_num) {this.like_num = 0}
      else {this.like_num = this.article.like_num}
    }
  }
</script>

<style scoped>
/* .content {
 border-bottom: 1px solid gray;
} */

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
</style>
