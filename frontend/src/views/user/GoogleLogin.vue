

<template>
  <div class="user" id="login">
    <div class="wrapC">
      <h1>
        Google Login........
      </h1>

      <h2>
        {{msg}}
      </h2>

      <button
        class="btn-bottom">
        Go back
    </button>

    </div>
  </div>
</template>

<script>
import "../../components/css/user.scss";
import axios from 'axios';
import UserApi from '@/api/UserApi.js';

export default {
  methods: {
    loginWithGoogleInfo(info) {
      // If exists email and nickname => Login or Join
      if(info.email && info.nick_name) {
        // Check if user
        UserApi.requestFindEmail(
          {email: info.email}
          , res => {
            // User exists => Login
            if(res.isEmail==true) {
              this.doLogin(info);
            }
            // User not exists => Join
            else {
              this.doJoin(info);
            }
          }
          , err => {
            this.msg = err.msg;
          }
        );
      }
      // Else error(Invalid information)
      else {
          this.msg = "email or name not exists";
      }
    }
    , doLogin(info) {
      this.msg = "Do Login ...";
      console.log('Do login: ', info.email);
      UserApi.loginWithSocial(
        info.email
        , res => {
          // 로그인 완료 시 세션 저장소에 받은 토큰 정보 저장
          sessionStorage.setItem('jwt-token', res.jwtToken);
          // 로그인 정보를 vuex 에 저장
          this.$store.commit('addUserInfo', res.userInfo);

          // curation/main 페이지로 이동
          this.$router.push({path:"/curation/main"});
        }
        , error => {
          this.$router.push({name:'Errors', query: {message: error.msg}});
        }
      )
    }
    , doJoin(info) {
      this.msg = "Do Join ...";

      let data = {
        nick_name: info.nickname,
        email: info.email
      };

      UserApi.joinWithSocial(
        data
        , res => {
            this.doLogin(info);
        }
        , error => {
          this.$router.push({name:'Errors', query: {message: error.msg}});
        }
      )
    }
  },
  mounted() {
    
  },
  data: () => {
    return {
      msg: ''
    }
  }
};
</script>