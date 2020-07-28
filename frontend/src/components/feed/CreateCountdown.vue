<template>
  <div class="user join wrapC">
    <div class="d-flex justify-content-between align-items-center">
      <b-icon icon="chevron-left" font-scale="1.5"></b-icon>
      <b-icon icon="check-square" font-scale="1.4"></b-icon>
    </div>
    <div class="container">
      <b-list-group style="border-radius: 20px;">
        
        <b-list-group-item class="p-0 bg-dark"><input id="article-title" type="text" class="m-0 border-0 rounded-pill text-white bg-dark" v-model="title" placeholder="제목은 뭐지??"></b-list-group-item>
        <b-list-group-item data-toggle="modal" data-target="#exampleModal">
          <label for="selectDate" style="font-size:10px;">Select Date</label>
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
              <div class="modal-body d-flex flex-column justify-content-center">
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

        <b-list-group-item><b-form-tags
              input-id="tags-remove-on-delete"
              :input-attrs="{ 'aria-describedby': 'tags-remove-on-delete-help' }"
              v-model="tags"
              separator=" "
              placeholder="태그 입력!!"
              remove-on-delete
              no-add-on-enter
              class="mb-2"
              style="outline:none !important"
            ></b-form-tags></b-list-group-item>

      </b-list-group>
    </div>
  </div>
</template>

<script>

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
    }
  },
  methods: {
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
    }
  },
  created() {

  }
}
</script>

<style scoped>

input[id=article-title]::placeholder {
  color: white;
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

</style>
