<template>
  <div class="user mt-0 myuser" id="login">
    <LoginHeader />
    <div class="wrapC myjoin d-flex flex-column justify-content-between">
      <div>
        <h1>
          <!-- 유저 닉네임, 이메일 받아서 들어가야 함 -->
          {{nick_name}}님, 반갑습니다!
        </h1>
        <h3>
          <!-- 이메일 주소 파란색으로 돋보이게 표시 -->
          <p style="color:royalblue">{{email}}</p>으로 회원가입 인증 메일이 발송되었습니다. 
          <br />이메일을 확인해 주세요.
        </h3>
      </div>

      <!-- <div class="d-flex justify-content-end mt-3">
        <a href="javascript:void(0);" @click="onJoinEmail" class="text-dark mylink">인증 메일 재발송</a> -->
        <!-- <router-link to="#" @click="onJoinEmail" class="btn--text">인증 메일 재발송</router-link> -->
      <!-- </div> -->
      <div>
        <button
          class="mymailbutton mb-3"
          @click="onJoinEmail"
        >인증 메일 재발송</button>
        <button
          class="mybutton"
          @click="toMainPage"
        >로그인 하러 가기!</button>
      </div>

    </div>
  </div>
</template>

<script>
import "../../components/css/user.scss";

import LoginHeader from '../../components/user/custom/LoginHeader.vue'

import UserApi from "../../api/UserApi";

export default {
  data() {
    return {
      nick_name: this.$route.params.nick_name,
      email: this.$route.params.email,
    }
  },
  methods: {
    toMainPage() {
        this.$router.push("/");
    },
    onJoinEmail() {
      let { nick_name, email} = this;
        let data = {
          nick_name,
          email
        };
        UserApi.requestJoinEmail(
          data,
          res => {
            this.makeToast("인증메일이 재전송 되었습니다.", "primary");
          },
          error => {
            this.makeToast(error.msg, "danger");
          }
        );
    },
    makeToast(message, variant){
      this.$bvToast.toast(message, {
        title: '알림',
        toaster: "b-toaster-bottom-right",
        variant: variant,
        autoHideDelay: 3000,
        appendToast: false
      })
    },
  },
  components: {
    LoginHeader,
  }
};
</script>

<style scoped>
  .myuser {
    background-color: #f8e8f2;
  }
  .myjoin {
    height: 100%;
    padding-top: 70px;
    padding-bottom: 50px;
    background-color: white;
  }
  .mylink {
    font-weight: 600;
  }
  .mymailbutton {
    width: 100%;
    height: 40px;
    background-color: #f9e9f2;
    box-shadow: none;
    border-radius: 0.25rem;
  }
  .mybutton {
    width: 100%;
    height: 40px;
    background-color: #d8c8f8;
    box-shadow: none;
    border-radius: 0.25rem;
  }
</style>