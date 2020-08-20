<template>
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
</template>

<script>
import FeedApi from "@/api/FeedApi.js"

export default {
    name: 'feedCountdownThumbnail',
    data() {
      return {
        Value: {
          date: null,
          hour: null,
          min: null,
          sec: null,
        },
      }
    },
    props: {
      article: Object,
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

        if(countdown <= 0) {
          this.Value.sec = "00";
          this.Value.min = "00";
          this.Value.hour = "00";
          this.Value.date = "00";
          return;
        }

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
    },
    mounted() {
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
.mythumbnail {
  background-color: #f8e8f2;
  width: 100v;
  height: 55v;
}
</style>
