<template>
  <div class="col-10">
    <b-list-group style="border-radius: 20px;">
      
      <b-list-group-item class="p-0 create-header">
        <input
          id="article-title"
          type="text"
          class="m-0 border-0 rounded-pill"
          v-model="title"
          placeholder="제목은 뭐지??">
      </b-list-group-item>
      <b-list-group-item data-toggle="modal" data-target="#exampleModal">
        <label for="selectDate">Select Date</label>
        <div id="selectDate" class="d-flex justify-content-center py-3">
          <div>
            <div class="time">{{value.date}}</div>
          </div>
          <div>
            <div class="time">{{value.hour}}</div>
          </div>
          <div>
            <div class="time">{{value.min}}</div>
          </div>
          <div>
            <div class="time">{{value.sec}}</div>
          </div>
        </div>
      </b-list-group-item>

      <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-body d-flex flex-column justify-content-center align-items-center">
              <div v-if="isCalendar">
                <b-calendar v-model="date" @context="onContext" locale="en-US"></b-calendar>
                <b-row class="mt-3 d-flex justify-content-between">
                  <b-col class="text-right" @click="moveTime">시간 선택</b-col>
                </b-row>
              </div>
              <div v-else class="d-flex flex-column justify-content-center">
                <b-time class="border rounded p-2" v-model="Time" locale="en"></b-time>
                <b-row class="mt-3 d-flex justify-content-between">
                  <b-col class="text-left" @click="moveCalender">날짜 선택</b-col>
                  <b-col class="text-right" @click="submitDateTime" data-dismiss="modal">완료</b-col>
                </b-row>
              </div>
            </div>
          </div>
        </div>
      </div>

      <b-list-group-item>
        <textarea
        class="tag-input"
        v-model="tag"
        placeholder="태그를 입력하세요.
스페이스 바를 누르면 자동으로 태그가 입력됩니다."
        row="20"
        @keydown.space="inputTag"
        >
        </textarea>
        <b-badge v-for="(Tag, idx) in tags" :key="idx">{{Tag}}<b-icon @click="delTag(Tag)" icon="x" scale="0.75"></b-icon></b-badge>
      </b-list-group-item>

    </b-list-group>
  </div>
</template>

<script>
import FeedApi from '../../api/FeedApi'

export default {
  name: 'CreateCountdown',
  data() {
    return {
      title: null,
      tags: [],
      value: {
        date: "00",
        hour: "00",
        min: "00",
        sec: "00"
      },
      date: null,
      Time: null,
      isCalendar: true,
      cc: null,
      tag: null,
    }
  },
  props: {
    article: Object,
    fno: Number,
    submit: Boolean,
  },
  methods: {
    moveMain() {
      this.$router.go(-1)
    },
    onContext(ctx) {
      this.context = ctx
    },
    changeString(str) {
      str = String(str)
      if (str.length === 1) {
        return '0'+str
      }
      return str
    },
    inputTag(event) {
      event.preventDefault()
      if (this.tags.filter(res=>res=== this.tag).length === 0) {
        this.length++
        this.tags.push(this.tag)
        this.tag = null
      } else {
        console.log('태그가 중복됩니다.')
        this.tag = null
      }
    },
    delTag(t) {
      this.tags = this.tags.filter(res=>res !== t)
    },
    timer() {
      let today = new Date()
      let selectDate = new Date(this.date+'T'+this.Time)

      let countdown = parseInt((selectDate - today) / 1000)

      this.value.sec = this.changeString(countdown % 60)
      countdown = parseInt(countdown / 60)
      this.value.min = this.changeString(countdown % 60)
      countdown = parseInt(countdown / 60)
      this.value.hour = this.changeString(countdown % 24)
      this.value.date = this.changeString(parseInt(countdown / 24))
    },
    submitDateTime() {
      this.timer()
      setInterval(this.timer, 1000)
    },
    moveTime() {
      this.isCalendar = false
    },
    moveCalender() {
      this.isCalendar = true
    },
    makeData() {
      var jsonObj = {
        title: this.title,
        content: {
          date: this.date,
          time: this.Time,
        }
      }
      return JSON.stringify(jsonObj)
    },
    catchTags() {console.log(this.tags)}
    ,
    submitArticle() {
      let submitState = true;
      if(!(this.title && this.title.length > 0)) {
        this.makeToast("제목을 입력해주세요.", "danger");
        submitState = false;
      }
      if(!this.date || !this.Time) {
        this.makeToast("날짜와 시간을 설정해주세요.", "danger");
        submitState = false;
      }
      if(!(this.tags && this.tags.length > 0)) {
        this.makeToast("적어도 하나 이상의 태그가 필요합니다.", "danger");
        submitState = false;
      }

      if(!submitState) {
        return;
      }

      let sendData = this.makeData();

      // createFeed 요청에 줄 데이터 목록
      // uno 는 토큰을 통해 사용하기위해 제거
      let data = {
        ctype: 2,
        content: sendData,
        tag: JSON.stringify(this.tags)
      };
      
      if (this.fno === null) {
        // Axios 요청
        FeedApi.createFeed(
          // 요청에 쓸 데이터 전달
          data
          // 성공시 수행할 콜백 메서드
          , res => {
            console.log(res);
            // this.$router.push({path:'/feed/feedDetail/'+this.fno})
            // 투표랑 article 은 피드 메인으로 보내는데 왜 이건 피드 디테일로 보내?
            // 피드 목록 줄때 각 피드별로 필요한 정보는 모두 가져와서 피드 비동기 요청은 따로 필요가 없음
            this.$router.push({path: '/feed/main'})
          }
          // 실패시 수행할 콜백 메서드
          , err => {
            console.log(err);
          } 
        )
      } else {
        
        data.fno = this.fno

        FeedApi.updateFeed(
          data,
          res=> {
            console.log(res)
            this.$router.push({path:'/feed/main'})
          },
          err=> console.log(err)
          )
      }
    }
    , titleFocusOut() {
      console.log('titleFocusOut() called!!!');
      FeedApi.getTags(
        {
          content: this.title
          , tags: JSON.stringify(this.tags)
        }
        , res => {
          console.log(res);

          // 받아온 태그 목록 중 현재 태그 목록에 없는거만 적용
          this.tags = JSON.parse(res.data.data);
        }
        , err => {
          console.log('getTags Error: ' + err.msg);
        }
      )
    }
    , makeToast(message, variant){
        this.$bvToast.toast(message, {
          title: '알림',
          toaster: "b-toaster-bottom-right",
          variant: variant,
          autoHideDelay: 3000,
          appendToast: false
        })
     }
  },
  watch: {
    tags: function(n, o) {this.catchTags},
    submit: function(n, o) {
      this.submitArticle()
    }
  },
  mounted() {
    if(this.article !== null) {
      this.title = this.article.content.content.title
      this.date = this.article.content.content.date
      this.Time = this.article.content.content.time
      this.tags = this.article.tag
      this.submitDateTime()
    }

    document.getElementById("article-title").addEventListener("focusout", this.titleFocusOut);
  }
}
</script>

<style scoped>

input[id=article-title]::placeholder {
  color: black;
}

input[type=text], select, textarea{
  width: 100%;
  padding: 12px;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
  resize: vertical;
  margin-top: 10px;
  margin-bottom: 10px;
}

button {
  background-color: #4CAF50;
  width: 100%;
  color: white;
  padding: 12px 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  float: right;
}

.disable-button {
  background-color: gray;
  text-align: center;
  width: 100%;
  color: black;
  padding: 12px 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  float: right;
}

.col-25 {
  float: left;
  width: 25%;
  margin-top: 6px;
}

.col-75 {
  float: left;
  width: 75%;
  margin-top: 6px;
}

.row:after {
  content: "";
  display: table;
  clear: both;
}

span {
  margin: 1px;
}

.time {
  width: 40px;
  margin-right: 10px;
  margin-left: 10px;
  border-bottom: 1px solid black;
  text-align: center;
  font-weight: 700;
}

@media screen and (max-width: 600px) {
  .col-25, .col-75, input[type=submit] {
    width: 100%;
    margin-top: 0;
  }
}

.create-header {
  background-color: #D8BEFE;
}

.tag-input {
  height: 100px;
  resize: none;
}

.tag-input:focus {
  outline: none;
}

.input-form:focus {
  outline: none;
}

</style>
