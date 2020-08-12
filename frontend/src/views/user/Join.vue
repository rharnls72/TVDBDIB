
<!--
    가입하기는 기본적인 폼만 제공됩니다
    기능명세에 따라 개발을 진행하세요.
    Sub PJT I에서는 UX, 디자인 등을 포함하여 백엔드를 제외하여 개발합니다.
 -->
<template>
  <div class="user join m-0">
    <LoginHeader />
    <div class="form-wrap wrapC myjoin">
      <div class="input-with-label">
        <input
          v-model="nick_name"
          v-bind:class="{error : error.nick_name, complete:!error.nick_name&&nick_name.length!==0}"
          id="nick_name"
          placeholder="닉네임을 입력하세요."
          type="text"
        />
        <label for="nick_name" class="m-0">닉네임</label>
        <div class="error-text" v-if="error.nick_name">{{error.nick_name}}</div>
      </div>

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

      <div class="input-with-label">
        <input
          v-model="password"
          v-bind:class="{error : error.password, complete:!error.password&&password.length!==0}"
          id="password"
          :type="passwordType"
          placeholder="비밀번호를 입력하세요." />
        <label for="password">비밀번호</label>
        <div class="error-text" v-if="error.password">{{error.password}}</div>
      </div>

      <div class="input-with-label">
        <input
          v-model="passwordConfirm"
          v-bind:class="{error : error.passwordConfirm, complete:!error.passwordConfirm&&passwordConfirm.length!==0}"
          :type="passwordConfirmType"
          id="password-confirm"
          placeholder="비밀번호를 다시한번 입력하세요."
        />
        <label for="password-confirm">비밀번호 확인</label>
        <div class="error-text" v-if="error.passwordConfirm">{{error.passwordConfirm}}</div>
      </div>
    </div>

    <!-- <label>
      <input v-model="isTerm" type="checkbox" id="term" />
      <span>약관을 동의합니다.</span>
    </label> -->

    <!-- go-term CSS 적용 -->
    <!-- <span class="go-term" @click="termPopup=true">약관보기</span> -->

    <button
      class="btn-bottom"
      @click="onJoin"
      :disabled="!isSubmit"
      :class="{disabled : !isSubmit}">
      가입하기
    </button>
  </div>
</template>

<script>
import "../../components/css/user.scss";
import UserApi from "../../api/UserApi";
import AccountApi from "@/api/AccountApi";
import PV from "password-validator";
import * as EmailValidator from "email-validator";
import LoginHeader from '../../components/user/custom/LoginHeader.vue'

export default {
  created() {
    this.passwordSchema
      .is()
      .min(8)
      .is()
      .max(100)
      .has()
      .digits()
      .has()
      .letters();
  },
  watch: {
    nick_name: function(v) {
      this.checkNick();
    },
    password: function(v) {
      this.checkPasswd();
    },
    passwordConfirm: function(v) {
      this.checkPasswdConfirm();
    },
    email: function(v) {
      this.checkEmail();
    },
    // isTerm: function(v) {
    //   this.checkForm();
    // }
  },
  methods: {
    checkEmail() {
      if (this.email.length >= 0 && !EmailValidator.validate(this.email))
        this.error.email = "이메일 형식이 아닙니다.";
      else this.error.email = false;

      if(this.error.email == false) {
        UserApi.requestFindEmail(
          {email: this.email},
          res => {
            if(res.isEmail) {
              // Invalid
              this.error.email = "중복된 이메일 입니다.";
            } else {
              // Valid
              this.error.email = false;
            }

            this.checkForm();
          },
          error => {
            this.$router.push({ name: "Errors", query: { message: error.msg } });
          }
        );
      } else {
        this.checkForm();
      }
    },
    checkNick() {
      // 빠르게 문자열의 바이트 수를 구하는 함수래요
      function getByteLength(s){
        let b, i, c;
        for(b=i=0;(c=s.charCodeAt(i++)) > 0;b+=c>>11?3:c>>7?2:1);
          return b;
      }

      if(getByteLength(this.nick_name) > 20)
        this.error.nick_name = "닉네임은 20Byte를 넘지 않아야 합니다.";
      else this.error.nick_name = false;

      if(this.nick_name.length > 0 && this.error.nick_name == false) {
        AccountApi.requestFindNick(
          {
            nick_name: "ThisIsNickNameForTestVeryVeryLongNickName",
            new_nick_name: this.nick_name,
          },
          (res) => {
            if (res.isNick) {
              this.error.nick_name = false;
            } else {
              this.error.nick_name = "사용 할 수 없는 닉네임 입니다.(중복)";
            }
            
            this.checkForm();
          },
          (error) => {
            this.$router.push({ name: "Errors", query: { message: error.msg } });
          }
        );
      } else if(this.nick_name.length == 0) {
        this.error.nick_name = "가입하기 위해 닉네임은 꼭 필요합니다.";
        this.checkForm();
      } else {
        this.checkForm();
      }
    },
    checkPasswd() {
      if (
        this.password.length >= 0 &&
        !this.passwordSchema.validate(this.password)
      )
        this.error.password = "비밀번호는 영문, 숫자 포함 8자리 이상이어야 합니다.";
      else this.error.password = false;

      this.checkForm();
    },
    checkPasswdConfirm() {
      if(this.password != this.passwordConfirm)
        this.error.passwordConfirm = "비밀번호가 일치하지 않습니다.";
      else this.error.passwordConfirm = false;

      this.checkForm();
    },
    checkForm() {
      let isSubmit = true;
      Object.values(this.error).map(v => {
        if (v) isSubmit = false;
      });
      this.isSubmit = isSubmit;
    },
    onJoin() {
      if (this.isSubmit) {
        let { nick_name, email, password } = this;
        let data = {
          nick_name,
          email,
          password
        };

        //요청 후에는 버튼 비활성화
        this.isSubmit = false;

        UserApi.requestJoin(
          data,
          res => {
            //요청이 끝나면 버튼 활성화
            this.isSubmit = true;
            // 가입 성공했으면 가입 완료 페이지로 이동
            this.$router.push({name: "JoinComplete", params: {nick_name: nick_name, email: email}});
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
      email: "",
      password: "",
      passwordConfirm: "",
      nick_name: "",

      isTerm: false,

      isLoading: false,

      passwordSchema: new PV(),

      error: {
        email: "이메일을 입력해주세요",
        password: "비밀번호를 입력해주세요",
        passwordConfirm: "비밀번호를 확인해주세요",
        nick_name: "닉네임을 입력해주세요",
        term: false
      },

      isSubmit: false,

      passwordType: "password",
      passwordConfirmType: "password",

      termPopup: false
    };
  },
  components: {
    LoginHeader,
  },
};
</script>

<style scoped>
  .myjoin {
    padding-top: 70px;
  }
</style>