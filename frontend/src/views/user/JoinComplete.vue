

<template>
  <div class="user" id="login">
    <div class="wrapC">
      <h1>
        <!-- 유저 닉네임, 이메일 받아서 들어가야 함 -->
        {{nick_name}}님, 반갑습니다!
      </h1>
      <h3>
        <!-- 이메일 주소 파란색으로 돋보이게 표시 -->
        <p style="color:royalblue">{{email}}</p>으로 회원가입 인증 메일이 발송되었습니다. 
        <br />이메일을 확인해 주세요.
      </h3>

      <br />
      <br />
      
      <div class="add-option">
        <div class="text">
          <p>혹시</p>
          <div class="bar"></div>
        </div>
        <div class="wrap">
          <p>이메일이 발송되지 않았나요?</p>
          <router-link to="#" @click="onJoinEmail" class="btn--text">인증 메일 재발송</router-link>
        </div>
      </div>

      <button
        class="btn-bottom"
        @click="toMainPage"
      >로그인 하러가기!</button>

    </div>
  </div>
</template>

<script>
import "../../components/css/user.scss";
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
      if (this.isSubmit) {
        let { nick_name, email} = this;
        let data = {
          nick_name,
          email
        };

        UserApi.requestJoinEmail(
          data,
          res => {
            // 재전송 완료
          },
          error => {
            this.$router.push({name:'Errors', query: {message: error.msg}})
          }
        );
      }
    }
  },
};
</script>