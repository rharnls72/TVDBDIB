<template>
  <div>
    <div v-if="goBack">
      <div class="user" id="login">
        <LoginHeader />
        <div class="wrapC mygoogle">
          <h1 class="text-center">
            Google Login
          </h1>
          <p>
            Error: {{errorMessage}}
          </p>

          <!-- Social Login 실패시 로그인 페이지로 돌아가는 버튼 활성화 -->
          <button
            class="btn-bottom"
            @click="goLoginPage">
            로그인 페이지로 돌아가기
          </button>
        </div>
      </div>
    </div>
    <LoadingItem v-else />
  </div>
</template>

<script>
import "../../components/css/user.scss";
import axios from 'axios';
import UserApi from '@/api/UserApi.js';
import LoginHeader from '@/components/user/custom/LoginHeader.vue'
import LoadingItem from '@/components/common/custom/LoadingItem.vue'

export default {
  methods: {
    loginWithGoogleInfo(info) {
      // Email exist
      if(info.email && info.email.length > 0) {
        // Check if user
        this.msg = "구글 정보로 이전에 가입한 회원 인지 확인 중 ......";
        UserApi.requestFindEmail(
          {email: info.email}
          , res => {
            // User exists => Login
            if(res.isEmail==true) {
              this.msg = "구글 정보로 가입된 회원으로 확인";
              this.doLogin(info);
            }
            // User not exists => Join
            else {
              this.msg = "구글 정보로 가입된 정보 없음";
              this.doJoin(info);
            }
          }
          , err => {
            console.log('requestFindEmail 실패!');
            this.msg = "구글 정보로 가입되었는지 여부 확인 요청 실패";
            
            this.errorMessage = "구글 계정 정보로 회원 여부 확인 실패";
            this.goBack = true;
          }
        );
      }
      // Do not join(or login)
      else {
        // Go to error page(Require email)
        // And go main(login) page, not the back page
        console.log('이메일이 존재하지 않습니다 => 로그인 불가능');
        this.msg = "구글 계정 정보에 이메일 미포함 => 로그인 불가능";
        
        this.errorMessage = "구글 계정 정보에 이메일이 존재하지 않아 로그인 불가능";
        this.goBack = true;
      }
    }
    , doLogin(info) {
      this.msg = "구글 이메일 정보로 로그인 시도 중 ....";
      UserApi.loginWithSocial(
        info.email
        , res => {
          this.msg = "구글 로그인 성공!";
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
          this.msg = "구글 정보로 로그인 실패";
          
          this.errorMessage = "구글 계정 정보로 로그인 실패";
          this.goBack = true;
        }
      )
    }
    , doJoin(info) {
      if(info.nickname && info.nickname.length > 0) {
        let data = {
          nick_name: info.nickname + '@google',
          email: info.email
        };
  
        this.msg = "구글 계정 정보로 회원가입 중 .....";
        UserApi.joinWithSocial(
          data
          , res => {
              this.msg = "구글 정보로 회원 가입 성공";
              this.doLogin(info);
          }
          , error => {
            console.log('joinWithSocial 실패!');
            this.msg = "구글 정보로 회원가입 실패";

            this.errorMessage = "구글 계정 정보로 회원가입 실패: " + error.msg;
            this.goBack = true;
          }
        )
      } else {
        console.log('구글 정보에 닉네임이 없습니다. => 회원가입 불가능');
        this.msg = "구글 정보에 닉네임 미포함 => 회원가입 불가능";
        
        this.errorMessage = "구글 계정 정보에 닉네임이 미포함되어 회원가입 불가능";
        this.goBack = true;
      }
    }
    , goLoginPage() {
      this.$router.push({name: "Login"});
    }
  },
  mounted() {
    let info = this.$route.query.info;

    this.msg = '정보 확인 중 .....'
    // info 없이 접근 => url 로 직접?
    if(!info) {
      console.log('잘못된 접근');
      this.msg = '잘못된 접근';
      
      this.errorMessage = "잘못된 접근";
      this.goBack = true;
    }
    // 구글 소셜 로그인을 통해
    // 구글 계정 정보를 가져왔거나 오류 발생
    else {
      info = JSON.parse(info);
      this.msg = info.msg;
      this.errorMessage = info.errorMessage;
      this.goBack = info.goBack;

      if(!this.goBack) {
        this.loginWithGoogleInfo(info);
      }
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
    LoadingItem
  }
};
</script>

<style scoped>
  .mygoogle {
    height: 100%;
    padding-top: 70px;
    padding-bottom: 50px;
  }
</style>