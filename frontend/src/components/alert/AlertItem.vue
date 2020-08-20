<template>
  <div class="feed-item">
    <ul>
      <li
        v-for="(alert) in alerts"
        v-bind:key="alert.ano"
        style="border-radius: 10px; position: relative;"
        class="shadow"
        v-bind:class="{done: !alert.read}"
        type="button"
        @click="checkAlert(alert)"
      >
        <div class="listItem" style="display:flex">
          <div class="box">
            <img
              v-if="alert.user.profile_pic!=null"
              class="profile"
              :src="alert.user.profile_pic"
              @click.stop.prevent="movePage(alert)"
            />
            <img v-else class="profile" :src="defaultProfile" @click.stop.prevent="movePage(alert)" />
          </div>
          <div class="message" v-if="alert.atype != 4">
            <div v-if="alert.atype == 1" class="inMessage">
              <span @click.stop.prevent="moveArticlePage(alert)">{{alert.user.nick_name}}</span>님이 내
              <span v-if="alert.ctype != 1">댓</span>글에 좋아요를 표시했습니다.
            </div>
            <div v-if="alert.atype == 2" class="inMessage">
              <span @click.stop.prevent="moveArticlePage(alert)">{{alert.user.nick_name}}</span>님이 내
              <span v-if="alert.ctype != 1">댓</span>글에 댓글을 달았습니다.
            </div>
            <div v-if="alert.atype == 3" class="inMessage">
              <span @click.stop.prevent="moveArticlePage(alert)">{{alert.user.nick_name}}</span>님의
              <span v-if="alert.ctype != 1">댓</span>글에서 나를 언급했습니다.
            </div>
          </div>
          <div class="follow" v-else>
            <div class="inMessage">
              <span @click.stop.prevent="movePage(alert)">{{alert.user.nick_name}}</span>님의 팔로우 요청
            </div>
          </div>
          <div class="btnGroup" v-if="alert.atype == 4">
            <div class="inbtnGroup">
              <button @click.stop.prevent="followRequestDelete(alert)" class="followbtn revert">거절</button>
              <button @click.stop.prevent="followAccept(alert)" class="followbtn allow">승인</button>
            </div>
          </div>
        </div>
      </li>
    </ul>
  </div>
</template>
<script>
import defaultProfile from "@/assets/images/profile_default.png";
import http from "@/api/http-common.js";
import header from "@/api/header.js";
import CurationApi from "@/api/CurationApi.js";

export default {
  name: "AlertItem",
  data: () => {
    return {
      defaultProfile,
    };
  },
  props: {
    alerts: Array,
  },
  methods: {
    moveArticlePage(alert) {
      // 알람의 글 타입 가져오기
      let type = alert.ctype;

      // 프로그램 관련
      if(type == 3) {
        this.$router.push("/program/" + alert.cno);
      }
      // 에피소드 관련
      else if(type == 4) {
        // 에피소드 번호로 프로그램 번호, 시즌 번호, 에피소드 번호 가져오기
        console.log("Alert go to Episode Detail!!", alert.cno);
        CurationApi.requestSimpleEpisode(
          alert.cno
          , res => {
            console.log("Alert get the Episode simple info", "" + res.pno + "/" + res.season + "/" + res.episode);
            this.$router.push({path: `/episode/detail/${res.pno}/${res.season}/${res.episode}`})
          }
          , err => {
            this.$router.push({name:'Errors', query: {message: err.msg}})
          }
        )
      }
      // 피드 관련
      else {
        this.$router.push({path: `/feed/detail/${alert.cno}`})
      }
    },
    movePage(alert) {
      this.$router.push("/profile/" + alert.user.nick_name);
    },
    // 클릭한 알림의 상태를 '읽음' 으로 바꾼다
    checkAlert(alert) {
      if (alert.read == 0) {
        http
          .get("/alert/read/" + alert.ano, header())
          .then((res) => {
            // 페이지 리로드하지 않고 누른 아이템의 상태만 바꾸자.
            let delete_index = this.$props.alerts.findIndex(
              (x) => x.ano == alert.ano
            );
            this.$props.alerts[delete_index].read = 1;
            //this.$router.go(this.$route.path); -- 새로고침 아닌줄 알았지만 vuex 데이터 날아감
          })
          .catch((err) => this.makeToast(err, "danger"));
      }

      if (alert.atype == 4) this.movePage(alert);
      
      // Problem Zone ???
      switch (alert.ctype) {
        case 1:
        case 2:
          this.$router.push("/feed/detail/" + alert.cno);
          break;
        case 3:
          this.$router.push("/program/" + alert.cno);
          break;
        case 4: //에피소드는 /:pno/:season/:episode 다필요..
          CurationApi.requestSimpleEpisode(
          alert.cno
          , res => {
            console.log("Alert get the Episode simple info", "" + res.pno + "/" + res.season + "/" + res.episode);
            this.$router.push({path: `/episode/detail/${res.pno}/${res.season}/${res.episode}`})
          }
          , err => {
            this.$router.push({name:'Errors', query: {message: err.msg}})
          }
        )
      }
    },
    // 팔로우 거절
    followRequestDelete(alert) {
      http
        .post(
          "/following/user/cancel",
          {
            follower: alert.subject_no,
            following: this.$store.state.userInfo.uno,
          },
          header()
        )
        .then((res) => {
          this.sendDelete(alert);
          this.makeToast("팔로우 신청을 거절했습니다.", "warning");
        })
        .catch((err) => this.makeToast(err, "danger"));
    },
    // 팔로우 수락
    followAccept(alert) {
      this.sendDelete(alert);
      this.makeToast("팔로우 신청을 승인했습니다.", "primary");
    },

    sendDelete(alert) {
      http
        .delete("/alert/deletefollow/" + alert.ano, header())
        .then((res) => {})
        .catch((err) => this.makeToast(err, "danger"));
    },

    makeToast(message, variant) {
      this.$bvToast.toast(message, {
        title: "알림",
        toaster: "b-toaster-bottom-center",
        variant: variant,
        autoHideDelay: 3000,
        appendToast: false,
      });
    },
  },
};
</script>

<style scoped>
.btnGroup {
  display: table;
  height: 50px;
  width: 100px;
  position: absolute;
  right: 20px;
}
.inbtnGroup {
  display: table-cell;
  vertical-align: middle;
}
.follow {
  width: 45%;
  height: 50px;
  display: table;
}
.message {
  width: 70%;
  height: 50px;
  display: table;
}
.inMessage {
  display: table-cell;
  vertical-align: middle;
}
.listItem {
  margin-bottom: 10px;
  padding: 10px;
}
.done {
  background-color: #f8e8f2;
}

.btn {
  height: 5%;
  width: 70%;
}

.box {
  width: 50px;
  height: 50px;
  border-radius: 70%;
  overflow: hidden;
  margin-left: 7px;
  margin-right: 15px;
}
.profile {
  width: 100%;
  height: 100%;
  object-fit: cover;
}
.followbtn {
  width: 50px;
  height: 30px;
  border-radius: 5px;
  font-size: 13px;
}
.allow {
  border-top-left-radius: 0;
  border-bottom-left-radius: 0;
  background-color: #d8befe;
}
.revert {
  border-top-right-radius: 0;
  border-bottom-right-radius: 0;
  background-color: #f8e8f2;
}
</style>