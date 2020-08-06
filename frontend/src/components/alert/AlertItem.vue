<template>
  <div class="feed-item">
      <ul> <!-- 받아온 데이터로 반복 돌리자 -->
            <!-- done == 'Y' 일때만 done 이라는 클래스를 지정 -->
          <li v-for="(alert) in alerts" v-bind:key="alert.ano" 
          class="shadow" v-bind:class="{done: !alert.read}" type="button" @click="checkAlert(alert)">

        <div class="form-group row">
          <!-- 이미지 바인딩 어떻게...? 잘 안된다 -->
            <img class="my-auto col-3 col-sm-3" src="@/assets/images/profile_default.png" @click="movePage(alert)">
            <span class="alert-content my-auto col-6 col-sm-6">
              <p v-if='alert.atype == 1'>{{alert.subject_name}}님이 내 <span v-if='alert.ctype != 1'>댓</span>글에 좋아요를 표시했습니다.</p>
              <p v-if='alert.atype == 2'>{{alert.subject_name}}님이 내 <span v-if='alert.ctype != 1'>댓</span>글에 댓글을 달았습니다.</p>
              <p v-if='alert.atype == 3'>{{alert.subject_name}}님의 <span v-if='alert.ctype != 1'>댓</span>글에서 나를 언급했습니다.</p>
              <p v-if='alert.atype == 4'>{{alert.subject_name}}님의 팔로우 요청</p>
            </span>
            <span class="float-right my-auto col-3 col-sm-3 removeBtn" type="button">
                <b-icon-box-arrow-in-up-right  @click="movePage(alert)" font-scale="1.5" class="float-right" v-if="alert.atype < 4"></b-icon-box-arrow-in-up-right>
                <button @click="followRequestDelete(alert)" class="col-6 col-sm-6 btn btn-danger btn-sm" v-if="alert.atype == 4">거절</button>
                <button @click="followAccept(alert)" class="float-right col-6 col-sm-6 btn btn-primary btn-sm" v-if="alert.atype == 4">승인</button>
            </span>
        </div>
<!--
            <div class="align-center">
                <img src="@/assets/images/profile_default.png">
                {{alert.content}}
            </div>

             <span class="removeBtn" type="button" @click="deleteTodo(alert.no)">
                <i class="far fa-trash-alt"></i> 쓰레기통 그림 
            </span>
-->
        </li>

      </ul>
  </div>
</template>
<script>
import defaultProfile from "@/assets/images/profile_default.png";
import http from '@/api/http-common.js';
import header from "@/api/header.js"
//import commentcss from "@/components/css/feed/comment-list.scss";
export default {
  name: 'AlertItem',
  data: () => {
    return {
      defaultProfile
    };
  },
  props: {
    alerts: Array
  },
  methods:{
    movePage(alert){
      if (alert.atype == 1){ // 팔로우 요청일 경우 바로 오른쪽의 팔로우 요청 탭으로 이동
        this.$parent.tabClick(2);
      }
      else { // 그 외의 경우 상대방 유저의 프로필페이지로 이동
        this.$router.push('/profile/' + alert.subject_name);
      }
    },
    // 클릭한 알림의 상태를 '읽음' 으로 바꾼다
    checkAlert(alert){
      if (alert.read == 0){
        http.get('/alert/read/' + alert.ano, header())
          .then(res => {
            // 페이지 리로드하지 않고 누른 아이템의 상태만 바꾸자.
              let delete_index = this.$props.alerts.findIndex(x => x.ano == alert.ano);
              this.$props.alerts[delete_index].read = 1;
              //this.$router.go(this.$route.path); -- 새로고침 아닌줄 알았지만 vuex 데이터 날아감
          })
          .catch(err => this.makeToast(err, "danger"))
      }
    },
    // 팔로우 거절
    followRequestDelete(alert){
      http.post('/following/user/cancel', {
            follower: alert.subject_no,
            following: this.$store.state.userInfo.uno
        }, header())
        .then(res => {
          this.sendDelete(alert);
          this.makeToast("팔로우 신청을 거절했습니다.", "warning");
        })
        .catch(err => this.makeToast(err, "danger"))
     },
    // 팔로우 수락
     followAccept(alert){
       this.sendDelete(alert);
        this.makeToast("팔로우 신청을 승인했습니다.", "primary");
     },

     sendDelete(alert){
       console.log(alert.ano);
          http.delete('/alert/deletefollow/' + alert.ano, header())
          .then(res => {
          })
          .catch(err => this.makeToast(err, "danger"))
     },

     makeToast(message, variant){
        this.$bvToast.toast(message, {
          title: '알림',
          toaster: "b-toaster-bottom-right",
          variant: variant,
          autoHideDelay: 3000,
          appendToast: false
        })
     }
    }
  }

</script>

<style scoped>
ul {
  list-style-type: none;
  padding-left: 0px;
  margin-top: 0%;
  text-align: left;
}
li {
  display: flex;
  min-height: 30px;
  height: 70px;
  line-height: 20px;
  margin: 0.5rem 0;
  padding: 0 0.5rem;
  background: white;
  border-radius: 3px;
}
.checkBtn {
  line-height: 45px;
  color: #62acde;
  margin-right: 5px;
}
.removeBtn {
  color: blue;
  padding: 0;
}

.list-item {
  display: inline-block;
  margin-right: 5px;
}
.list-move {
  transition: transform 1s;
}
.list-enter-active,
.list-leave-active {
  transition: all 1s;
}
.list-enter,
.list-leave-to {
  opacity: 0;
  transform: translateY(30px);
}
.done{
  background-color: lightgoldenrodyellow;
}

img{
    margin-right: -5%;
    vertical-align: middle;
}
.btn{
  height: 5%;
  width: 70%;
}

.alert-content{
  padding: 0;
}

p{
  margin: 5%;
}

</style>