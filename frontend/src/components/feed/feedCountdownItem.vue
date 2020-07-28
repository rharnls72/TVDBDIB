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
    <div class="wrap">
      <div class="p-2 pt-3 d-flex flex-column justify-content-center align-items-center">
        <div class="d-flex justify-content-center py-3">
            <div>
              <div class="time">{{Value.date}}</div>
            </div>
            <div>
              <div class="time">{{Value.hour}}</div>
            </div>
            <div>
              <div class="time">{{Value.min}}</div>
            </div>
            <div>
              <div class="time">{{Value.sec}}</div>
            </div>
          </div>
      </div>
    </div>
    <!---->
    <div class="wrap d-flex justify-content-between">
      <div class="text-align-left">
        <b-icon class="mr-2" icon="heart" font-scale="1.5"></b-icon>
        <b-icon class="mr-2" icon="chat-square" font-scale="1.5"></b-icon>
        <b-icon icon="cursor" font-scale="1.5"></b-icon>
      </div>
      <div>
        <b-icon class="text-align-right" icon="bookmark" font-scale="1.5"></b-icon>
      </div>
    </div>
    <div class="wrap mt-2">
      <span class="font-weight-bold">좋아요 {{like_num}}명</span>
    </div>
    <div class="wrap mt-2">
      <span class="font-weight-bold">유저이름 </span>
      <span v-if="isLong">... <span class="moreView" @click="changeIsLong">더 보기</span></span>
      <span v-else>
        <span>{{content}}</span><br>
        <span v-for="tag in tags" :key="tag" class="tag">#{{tag}} </span>
      </span>
    </div>
    <div v-if="!isLong" class="wrap mt-2">
      <div class="row d-flex align-items-center px-3">
        <b-form-input style="border:none;" type="text" class="m-0 col rounded-pill" v-model="comment" placeholder="댓글 입력!!!">
        </b-form-input>
        <b-icon icon="plus-circle" class="ml-1 text-right" font-scale="1.4"></b-icon>
      </div>
    </div>
  </div>
    <!---->
    <!---->
</template>

<script>
import defaultImage from "../../assets/images/img-placeholder.png";
import defaultProfile from "../../assets/images/profile_default.png";
export default {
    name: 'feedCountdown',
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
        isLong: true,
      }
    },
    props: {
      countdown: Object,
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
      changeIsLong() {
        this.isLong=false
      }
    },
    created() {
      this.submitDateTime()
      console.log(this.countdown)
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
</style>
