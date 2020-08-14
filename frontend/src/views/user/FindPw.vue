<template>
  <div class="user" id="findPw">
    <LoginHeader />
    <div class="wrapC myfpw">
      <h1 class="text-center">
        가입할 때 사용하신
      </h1>
      <h1 class="text-center">
        이메일을 입력해주세요.
      </h1>
      <div class="input-with-label">
        <input
          v-model="email"
          v-bind:class="{error : error.email, complete:!error.email&&email.length!==0}"
          id="email"
          placeholder="이메일을 입력하세요."
          type="text" 
          autocapitalize="off"
          />
        <label for="email">이메일</label>
        <div class="error-text" v-if="error.email">{{error.email}}</div>
      </div>


      <button
      class="btn-bottom"
      @click="onFindPw"
      :disabled="!isSubmit"
      :class="{disabled : !isSubmit}">
      변경 링크 발송
    </button>

    </div>
  </div>
</template>

<script>
import "../../components/css/user.scss";
import UserApi from "../../api/UserApi";
import * as EmailValidator from "email-validator";
import LoginHeader from '@/components/user/custom/LoginHeader.vue'

export default {
  watch: {
    email: function(v) {
      this.checkForm();
    }
  },
  methods: {
    checkForm() {
      if (this.email.length >= 0 && !EmailValidator.validate(this.email))
        this.error.email = "이메일 형식이 아닙니다.";
      else {
        UserApi.requestFindEmail(
          {email: this.email},
          res => {
            if(res.isEmail==true){
              this.error.email = false;
            }else{
              this.error.email = "등록되지 않은 이메일 주소입니다.";
            }
            console.log(res.isEmail);
            this.checkSubmit();
          },
          error => {
            this.$router.push({name:'Errors', query: {message: error.msg}})
          }
        );
      }
      this.checkSubmit();
    },
    checkSubmit() {
      let isSubmit = true;
      Object.values(this.error).map(v => {
        if (v) isSubmit = false;
      });
      this.isSubmit = isSubmit;
    },
    onFindPw() {
      if (this.isSubmit) {
        //요청 후에는 버튼 비활성화
        this.isSubmit = false;

        UserApi.requestFindPw(
          {email: this.email},
          res => {
            //요청이 끝나면 버튼 활성화
            this.isSubmit = true;
            // 가입 했으면 가입한 정보로 로그인 하라고 하기
            this.$router.push({name:'FindPwComplete', params: {email: this.email}});
          },
          error => {
            this.$router.push({name:'Errors', query: {message: error.msg}})
            //요청이 끝나면 버튼 활성화
            this.isSubmit = true;
          }
        );
      }
    }
  },
  data: () => {
    return {
      email : "",
      isLoading: false,
      error: {
        email: false
      },
      isSubmit: false
    };
  },
  components: {
    LoginHeader,
  }
};
</script>

<style scoped>
  .myfpw {
    height: 100%;
    padding-top: 70px;
    padding-bottom: 50px;
  }
</style>