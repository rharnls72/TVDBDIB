<template>
  <div class="user" id="login">
    <LoginHeader />
    <div class="wrapC mykakao">
      <!-- 이 자리에 Kakao Login 대신 로딩 화면 띄운다면! -->
      <h1 class="text-center">
        Kakao Login........
      </h1>
      <p class="text-center">
        progress: {{msg}}
      </p>
      <p v-if="goBack">
        Error: {{errorMessage}}
      </p>

      <!-- 카카오 로그인 실패시 로그인 페이지로 돌아가는 버튼 활성화 -->
      <button
        class="btn-bottom"
        v-if="goBack"
        @click="goLoginPage">
        로그인 페이지로 돌아가기
      </button>
    </div>
  </div>
</template>

<script>
import "../../components/css/user.scss";
import axios from 'axios';
import UserApi from '@/api/UserApi.js';
import LoginHeader from '@/components/user/custom/LoginHeader.vue'

export default {
  methods: {
    loginWithKakaoInfo(info) {
      // Email exist
      if(info.email && info.email.length > 0) {
        // Check if user
        this.msg = "카카오 정보로 이전에 가입한 회원 인지 확인 중 ......";
        UserApi.requestFindEmail(
          {email: info.email}
          , res => {
            // User exists => Login
            if(res.isEmail==true) {
              this.msg = "카카오 정보로 가입된 회원으로 확인";
              this.doLogin(info);
            }
            // User not exists => Join
            else {
              this.msg = "카카오 정보로 가입된 정보 없음";
              this.doJoin(info);
            }
          }
          , err => {
            console.log('requestFindEmail 실패!');
            this.msg = "카카오 정보로 가입되었는지 여부 확인 요청 실패";

            this.unlinkKakao();
            
            this.errorMessage = "카카오 계정 정보로 회원 여부 확인 실패";
            this.goBack = true;
          }
        );
      }
      // Do not join(or login)
      else {
        // Go to error page(Require email)
        // And go main(login) page, not the back page
        console.log('이메일이 존재하지 않습니다 => 로그인 불가능');
        this.msg = "카카오 계정 정보에 이메일 미포함 => 로그인 불가능";

        this.unlinkKakao();
        
        this.errorMessage = "카카오 계정 정보에 이메일이 존재하지 않아 로그인 불가능";
        this.goBack = true;
      }
    }
    , doLogin(info) {
      this.msg = "카카오 이메일 정보로 로그인 시도 중 ....";
      UserApi.loginWithSocial(
        info.email
        , res => {
          this.msg = "카카오 로그인 성공!";
          // 로그인 완료 시 세션 저장소에 받은 토큰 정보 저장
          sessionStorage.setItem('jwt-token', res.jwtToken);

          let isAuto = this.$store.state.userInfo.isAutoLogin;
          res.userInfo.isAutoLogin = isAuto;

          // 로그인 정보를 vuex 에 저장
          this.$store.commit('addUserInfo', res.userInfo);

          // Save it also a local storage
          localStorage.setItem('tvility', JSON.stringify(res.userInfo));

          // curation/main 페이지로 이동
          this.$router.push({path:"/curation/main"});
        }
        , error => {
          console.log('loginWithSocial 실패!');
          this.msg = "카카오 정보로 로그인 실패";

          this.unlinkKakao();
          
          this.errorMessage = "카카오 계정 정보로 로그인 실패";
          this.goBack = true;
        }
      )
    }
    , doJoin(info) {
      if(info.nickname && info.nickname.length > 0) {
        let data = {
          nick_name: info.nickname,
          email: info.email
        };
  
        this.msg = "카카오 계정 정보로 회원가입 중 .....";
        UserApi.joinWithSocial(
          data
          , res => {
              this.msg = "카카오 정보로 회원 가입 성공";
              this.doLogin(info);
          }
          , error => {
            console.log('joinWithSocial 실패!');
            this.msg = "카카오 정보로 회원가입 실패";

            this.unlinkKakao();

            this.errorMessage = "카카오 계정 정보로 회원가입 실패";
            this.goBack = true;
          }
        )
      } else {
        console.log('카카오 정보에 닉네임이 없습니다. => 회원가입 불가능');
        this.msg = "카카오 정보에 닉네임 미포함 => 회원가입 불가능";

        this.unlinkKakao();
        
        this.errorMessage = "카카오 계정 정보에 닉네임이 미포함되어 회원가입 불가능";
        this.goBack = true;
      }
    }
    , unlinkKakao() {
      this.msg = "카카오 계정 연결 취소 중 ....";
      window.Kakao.API.request({
        url: '/v1/user/unlink',
        success: function(response) {
          this.msg = "카카오 계정 연결 취소 완료";
        },
        fail: function(error) {
          console.log('unlinkKakao() 실패');
          this.msg = "카카오 계정 연결 취소 실패";
        },
      });
    }
    , goLoginPage() {
      this.$router.push({name: "Login"});
    }
  },
  mounted() {
    let code = this.$route.query.code;

    this.msg = '코드 확인 중 .....'
    // Error
    if(!code) {
      console.log('카카오 코드 받기 실패');
      this.msg = '카카오 소셜로그인 인증 코드 받기 실패';
      
      this.errorMessage = "카카오 로그인을 위한 코드 받기 실패";
      this.goBack = true;
    }
    // Get kakao authorize code
    else {
      this.msg = '카카오 소셜로그인 인증 코드 받기 성공!';

      const qs = require('qs');

      let accessToken = null;

      const parameter = {
        grant_type: 'authorization_code',
        client_id: '72d5297d8562a4a38d4878b5b111dc9c',
        code: code
      };

      this.msg = '카카오 계정 인증 토큰 받는 중 .......';
      axios.post('https://kauth.kakao.com/oauth/token',
                  qs.stringify(parameter), 
                  {
                    headers: {
                      'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8'
                    }
                  }
        )
        .then((res) => {
          this.msg = "카카오 계정 인증 토큰 받기 성공";

          accessToken = res.data.access_token;
          
          // regist token to kakao sdk
          this.msg = "카카오 계정 인증 토큰 설정 중 ......";
          window.Kakao.Auth.setAccessToken(accessToken);
          this.msg = "카카오 계정 인증 토큰 설정 완료";

          // Get User info
          this.msg = "카카오 계정 정보 요청 중 .......";
          let this_vue = this;
          window.Kakao.API.request({
            url: '/v2/user/me',
            success: function(res) {
              let email = res.kakao_account.email;
              let nickname = res.kakao_account.profile.nickname;
              
              this_vue.msg = '카카오 계정 정보 조회 완료 : ' + email + " / " + nickname;

              // Join or Login
              this_vue.loginWithKakaoInfo({email, nickname});
            },
            fail: function(error) {
                console.log('카카오 유저 정보 받기 실패');
                this_vue.msg = '카카오 유저 정보 조회 실패';

                this_vue.unlinkKakao();
                this.errorMessage = "카카오 유저 정보 조회 실패";
                this.goBack = true;
            }
          });
        })
        .catch(err => {
          console.log('카카오 토큰 받기 실패');
          this.errorMessage = "카카오 로그인을 위한 토큰 받기 실패";
          this.goBack = true;
        });
    }
  },
  data: () => {
    return {
      msg: ''
      , errorMessage: ''
      , goBack: false
    }
  },
  components: {
    LoginHeader,
  }
};
</script>

<style scoped>
  .mykakao {
    height: 100%;
    padding-top: 70px;
    padding-bottom: 50px;
  }
</style>