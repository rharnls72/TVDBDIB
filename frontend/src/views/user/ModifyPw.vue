<template>
  <div class="user" id="login">
    <JoinHeader />
    <div class="wrapC mympw">
      <h1 class="text-center">
        비밀번호를 변경하세요.
      </h1>

      <div class="input-with-label">
        <input
          v-model="password"
          v-bind:class="{error : error.password, complete:!error.password&&password.length!==0}"
          id="password"
          :type="passwordType"
          placeholder="기존 비밀번호를 입력하세요." />
        <label for="password">기존 비밀번호</label>
        <div class="error-text" v-if="error.password">{{error.password}}</div>
      </div>

      <div class="input-with-label">
        <input
          v-model="newPassword"
          v-bind:class="{error : error.newPassword, complete:!error.newPassword&&newPassword.length!==0}"
          id="newPassword"
          :type="passwordType"
          placeholder="새로운 비밀번호를 입력하세요." />
        <label for="newPassword">새로운 비밀번호</label>
        <div class="error-text" v-if="error.newPassword">{{error.newPassword}}</div>
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

      <button
      class="btn-bottom"
      @click="onModifyPw"
      :disabled="!isSubmit"
      :class="{disabled : !isSubmit}">
      비밀번호 변경
    </button>

    </div>
  </div>
</template>

<script>
import "../../components/css/user.scss";
import UserApi from "../../api/UserApi";
import PV from "password-validator";
import GetUserApi from "@/api/GetUserApi"
import JoinHeader from '@/components/user/custom/JoinHeader.vue'

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
    GetUserApi.getUser(res => {
      this.$store.commit('addUserInfo', res.user);
    });
  },
  watch: {
    password: function(v) {
      this.checkForm();
    },
    newPassword: function(v) {
      this.checkForm();
    },
    passwordConfirm: function(v) {
      this.checkForm();
    }
  },
  methods: {
    checkForm() {
      if (
        this.password.length >= 0 &&
        !this.passwordSchema.validate(this.password)
      )
        this.error.password = "영문,숫자 포함 8 자리이상이어야 합니다.";
      else this.error.password = false;

      if (
        this.newPassword.length >= 0 &&
        !this.passwordSchema.validate(this.newPassword)
      )
        this.error.newPassword = "영문,숫자 포함 8 자리이상이어야 합니다.";
      else if(this.newPassword==this.password)
        this.error.newPassword = "기존 비밀번호와 동일합니다.";
      else this.error.newPassword = false;

      if(this.newPassword != this.passwordConfirm)
        this.error.passwordConfirm = "비밀번호가 일치하지 않습니다.";
      else this.error.passwordConfirm = false;

      let isSubmit = true;
      Object.values(this.error).map(v => {
        if (v) isSubmit = false;
      });
      this.isSubmit = isSubmit;
    },
    onModifyPw() {
      if (this.isSubmit) {
        let { newPassword, password } = this;
        let data = {
          newPassword,
          password
        };
        data.email = this.$store.state.userInfo.email;

        //요청 후에는 버튼 비활성화
        this.isSubmit = false;

        UserApi.requestModifyPw(
          data,
          res => {
            //요청이 끝나면 버튼 활성화
            this.isSubmit = true;

            // 가입 했으면 가입한 정보로 로그인 하라고 하기
            this.$router.push({name:'ModifyPwComplete'});
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
      password: "",
      newPassword: "",
      passwordConfirm: "",
      
      isLoading: false,

      passwordSchema: new PV(),

      error: {
        newPassword: false,
        password: false,
        passwordConfirm: false
      },

      isSubmit: false,

      passwordType: "password",
      passwordConfirmType: "password",

    };
  },
  components: {
    JoinHeader,
  }
};
</script>

<style scoped>
  .mympw {
    height: 100%;
    padding-top: 70px;
    padding-bottom: 50px;
  }
</style>