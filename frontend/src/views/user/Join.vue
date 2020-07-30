
<!--
    가입하기는 기본적인 폼만 제공됩니다
    기능명세에 따라 개발을 진행하세요.
    Sub PJT I에서는 UX, 디자인 등을 포함하여 백엔드를 제외하여 개발합니다.
 -->
<template>
  <div class="user join wrapC">
    <h1>가입하기</h1>
    <div class="form-wrap">
      <div class="input-with-label">
        <input
          v-model="nick_name"
          v-bind:class="{error : error.nick_name, complete:!error.nick_name&&nick_name.length!==0}"
          id="nick_name"
          placeholder="닉네임을 입력하세요."
          type="text" />
        <label for="nick_name">닉네임</label>
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
import PV from "password-validator";
import * as EmailValidator from "email-validator";

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
      this.checkForm();
    },
    password: function(v) {
      this.checkForm();
    },
    passwordConfirm: function(v) {
      this.checkForm();
    },
    email: function(v) {
      this.checkForm();
    },
    // isTerm: function(v) {
    //   this.checkForm();
    // }
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

      if(this.password != this.passwordConfirm)
        this.error.passwordConfirm = "비밀번호가 일치하지 않습니다.";
      else this.error.passwordConfirm = false;

      // 빠르게 문자열의 바이트 수를 구하는 함수래요
      function getByteLength(s){
        let b, i, c;
        for(b=i=0;(c=s.charCodeAt(i++)) > 0;b+=c>>11?3:c>>7?2:1);
          return b;
      }

      if(getByteLength(this.nick_name) > 20)
        this.error.nick_name = "닉네임은 20Byte를 넘지 않아야 합니다."
      else this.error.nick_name = false;

      // this.error.term = !this.isTerm;

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
        email: false,
        password: false,
        passwordConfirm: false,
        nick_name: false,
        term: false
      },

      isSubmit: false,

      passwordType: "password",
      passwordConfirmType: "password",

      termPopup: false
    };
  }
};
</script>


