<template>
  <div class="feed-item">
    <div class="top">
      <div class="profile-image" :style="{'background-image': 'url('+defaultProfile+')'}"></div>
      <div class="user-info">
        <div class="user-name">
          <button>SSAFY</button>
        </div>
        <p class="date">9시간 후</p>
      </div>
      <div class="content">
        <p>{{feedTitle}}</p>
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
        </div>
        <!-- 댓글 -->
        <div class="mr-3">
          <button class="h6 mr-1">
            <b-icon-chat></b-icon-chat>
          </button>
        </div>
        <!-- 스크랩 -->
        <div class="mr-3">
          <button class="h6 mr-1" @click="touchScrapIcon">
            <b-icon-bookmark v-if="!scrapIcon"></b-icon-bookmark>
            <b-icon-bookmark-fill v-else variant="success"></b-icon-bookmark-fill>
          </button>
          0 
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
      <span class="moreView">댓글 {{reply_num}}개</span>
    </div>
  </div>
    <!---->
    <!---->
</template>

<script>
import defaultImage from "../../assets/images/img-placeholder.png";
import defaultProfile from "../../assets/images/profile_default.png";
import axios from "axios"
import header from "@/api/header.js"

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
        comment: null,
        tags: ['소통', '맞팔', '너무', '귀엽당', 'ㅎㅎ'],
        reply: ['wow', '너무 좋아용 ㅎㅎ'],
        like_num: 12,
        reply_num: 11,
        likeIcon: false,
        scrapIcon: false,
      }
    },
    props: {
      article: Object,
      fno: Number,
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
      pushReply() {
        axios.post('http://localhost:9000/reply/feed/create',
        {
          no: this.article.fno,
          content: this.additionReply,
          writer_uno: 1,
        }, header())
      },
      touchLikeIcon() {
        this.likeIcon = !this.likeIcon
        if (this.likeIcon) {
          this.likeCount ++
        }
        else {
          this.likeCount --
        }
        // console.log(this.likeIcon)
      },
      touchScrapIcon() {
        this.scrapIcon = !this.scrapIcon
        if (this.scrapIcon) {
          this.scrapCount ++
        }
        else {
          this.scrapCount --
        }
        // console.log(this.scrapIcon)
      },
    },
    created() {
      this.submitDateTime()
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
