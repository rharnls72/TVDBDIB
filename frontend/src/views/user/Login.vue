<template>
  <div class="user mt-0 myuser" id="login">
    <LoginHeader />
    <div class="wrapC myfeed">
      <div class="myfeedcard">
        <img class="mythumbnail mb-2" :src="logo" alt="logo">
      </div>
      <div class="input-with-label mt-3">
        <input
          v-model="email"
          v-bind:class="{error : error.email, complete:!error.email&&email.length!==0}"
          @keyup.enter="Login"
          id="email"
          placeholder="이메일을 입력하세요."
          type="text"
          autocapitalize="off"
        />
        <label for="email">이메일</label>
        <div class="error-text" v-if="error.email">{{error.email}}</div>
      </div>

      <div class="input-with-label">
        <input
          v-model="password"
          type="password"
          v-bind:class="{error : error.password, complete:!error.password&&password.length!==0}"
          id="password"
          @keyup.enter="Login"
          placeholder="비밀번호를 입력하세요."
          autocapitalize="off"
        />
        <label for="password">비밀번호</label>
        <div class="error-text" v-if="error.password">{{error.password}}</div>
      </div>

      <div>
        <label>
          <input v-model="isSave" type="checkbox" id="save" />
          <span>로그인 유지</span>
        </label>
      </div>

      <!-- <button
        class="btn btn--back btn--login"
        @click="onLogin"
        :disabled="!isSubmit"
        :class="{disabled : !isSubmit}"
      >로그인</button> -->

      <button @click="onLogin" :disabled="!isSubmit" :class="{disabled : !isSubmit}" class="btn mybutton mt-2 d-flex justify-content-center align-items-center">
       <p class="d-inline m-0">로그인</p>
      </button>

      <button @click="doKakaoLogin" class="btn mykakaobutton mt-2 d-flex justify-content-center align-items-center">
        <img class="d-inline mylogo pr-2" :src="kakaoLogo" alt="kakao-logo">
       <p class="d-inline m-0">카카오 로그인</p>
      </button>

      <!-- <div class="sns-login">
        <div class="text">
          <p>SNS 로그인</p>
          <div class="bar"></div>
        </div>

        <button type="button" @click="doKakaoLogin">
          <img :src="kakaoButton" alt="Kakao login button"/>
        </button> -->
        <!-- <GoogleLogin :component="component" /> -->
        <!-- <GoogleLogin
          :params="params"
          :renderParams="renderParams"
          :onSuccess="onSuccess"
          :onFailure="onFailure"></GoogleLogin> -->
      <!-- </div> -->

      <div class="d-flex justify-content-end mt-3">
        <router-link to="/user/findPw" class="text-dark mylink mr-3">비밀번호 찾기</router-link>
        <router-link to="/user/join" class="text-dark mylink">가입하기</router-link>
      </div>

      <!-- <div class="add-option mt-3">
        <div class="text">
          <p>혹시</p>
          <div class="bar"></div>
        </div>
        <div class="wrap m-0">
          <p>비밀번호를 잊으셨나요?</p>
          <router-link to="/user/findPw" class="btn--text">비밀번호 찾기</router-link>
        </div>
        <div class="wrap m-0">
          <p>아직 회원이 아니신가요?</p>
          <router-link to="/user/join" class="btn--text">가입하기</router-link>
        </div>
      </div> -->
    </div>
  </div>
</template>

<script>
import "../../components/css/user.scss";
import PV from "password-validator";
import * as EmailValidator from "email-validator";
// import KakaoLogin from "../../components/user/snsLogin/Kakao.vue";
// import GoogleLogin from "../../components/user/snsLogin/Google.vue";
// import GoogleLogin from "vue-google-login";
import UserApi from "../../api/UserApi";
import LoginHeader from '../../components/user/custom/LoginHeader.vue'
import logo from '../../assets/images/custom/logo.png'
import kakaoLogo from '../../assets/images/custom/kakao-logo.png'
import kakaoButton from '@/assets/images/kakao_login_medium_narrow.png';

import GetUserApi from "@/api/GetUserApi"
import KakaoApi from "@/api/KakaoApi.js";

export default {
  name: 'Login',
  components: {
    // KakaoLogin,
    // GoogleLogin,
    LoginHeader,
  },
  created() {
    GetUserApi.getUser(res => {
      this.$store.commit('addUserInfo', res.user);
      // 비동기 요청이 완료되었을 때 store 에 유저 정보가 있는지 확인해야함(여기에 위치해야함)
      if(res.user && this.$store.state.userInfo.isAutoLogin) {
        
        // Get token(Simple login without password)
        UserApi.loginWithSocial(
          res.user.email
          , res => {
            // 로그인 완료 시 세션 저장소에 받은 토큰 정보 저장
            sessionStorage.setItem('jwt-token', res.jwtToken);

            // curation/main 페이지로 이동
            this.$router.push({path:"/curation/main"});
          }
          , error => {
            this.$router.push({name:'Errors', query: {message: error.msg}});
          }
        );
      }
        
    });
      
    this.component = this;
  
    this.passwordSchema
      .is()
      .min(8)
      .is()
      .max(100)
      .has()
      .digits()
      .has()
      .letters();
      
    this.email = this.$store.state.loginEmail;
  },
  watch: {
    password: function(v) {
      this.checkForm();
    },
    email: function(v) {
      this.checkForm();
    }
  },
  methods: {
    checkForm() {
      if (this.email.length >= 0 && !EmailValidator.validate(this.email))
        this.error.email = "이메일 형식이 아닙니다.";
      else this.error.email = false;

      if (
        this.password.length >= 0 &&
        !this.passwordSchema.validate(this.password)
      )
        this.error.password = "비밀번호는 영문, 숫자 포함 8자리 이상이어야 합니다.";
      else this.error.password = false;

      let isSubmit = true;
      Object.values(this.error).map(v => {
        if (v) isSubmit = false;
      });
      this.isSubmit = isSubmit;
    },
    onLogin() {
      if (this.isSubmit) {
        let { email, password } = this;
        let data = {
          email,
          password
        };

        //요청 후에는 버튼 비활성화
        this.isSubmit = false;

        // 로그인 하려는 이메일을 vuex 에 저장
        // 오류 난 후 다시 돌아왔을때 다시 채워놓기 위함
        this.$store.commit('addLoginEmail', this.email);

        UserApi.requestLogin(
          data,
          // 로그인 성공 시 호출 될 함수
          res => {
            
            //통신을 통해 전달받은 값 콘솔에 출력
            //console.log(res);

            //요청이 끝나면 버튼 활성화
            this.isSubmit = true;

            // 로그인 완료 시 세션 저장소에 받은 토큰 정보 저장
            sessionStorage.setItem('jwt-token', res.jwtToken);
            
            
            // 유저 정보 저장 선택 시 로컬 저장소에 유저 정보 저장
            if(this.isSave){
              res.userInfo.isAutoLogin = true;
            } else {
              res.userInfo.isAutoLogin = false;
            }

            // Save the logged in user info into a local storage
            localStorage.setItem('tvility', JSON.stringify(res.userInfo));

            // 로그인 정보를 vuex 에 저장
            this.$store.commit('addUserInfo', res.userInfo);

            // curation/main 페이지로 이동
            this.$router.push({path:"/curation/main"});
          },
          // 로그인 실패 시 호출 될 함수
          error => {
            
            // console.log(this.$router)
            this.$router.push({name:'Errors', query: {message: error.msg}})

            //요청이 끝나면 버튼 활성화
            this.isSubmit = true;
          }
        );
      }
    }
    , doKakaoLogin() {
      console.log('Kakao login start');
      this.$store.commit('addUserInfo', {isAutoLogin: this.isSave});
      KakaoApi.Login();
    }
  },
  data: () => {
    return {
      email: "",
      password: "",
      passwordSchema: new PV(),
      isSave: false,
      error: {
        email: false,
        passowrd: false
      },
      isSubmit: false,
      component: this,
      logo,
      kakaoLogo,
      kakaoButton
    };
  }
};
</script>

<style scoped>
  .myuser {
    background-color: #f8e8f2;
  }
  .myfeed {
    padding-top: 70px;
    padding-bottom: 50px;
    background-color: white;
  }
  .myfeedcard {
    width: 100%;
    height: auto;
  }
  .mythumbnail {
    width: 100%;
    height: auto;
  }
  .mybutton {
    width: 100%;
    height: 40px;
    background-color: #d8c8f8;
    box-shadow: none;
  }
  .mykakaobutton {
    width: 100%;
    height: 40px;
    background-color: #f7e600;
    box-shadow: none;
  }
  .mylogo {
    height: 100%;
    width: 33px
  }
  .mylink {
    font-weight: 600;
  }
</style>