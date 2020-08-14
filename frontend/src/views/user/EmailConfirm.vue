<template>
  <div class="user" id="login">
    <LoginHeader />
    <div class="wrapC myec">
      <h1 class="text-center">
        이메일 인증이 완료되었습니다!
      </h1>

      <button
        class="btn-bottom"
        @click="toMainPage"
      >로그인 하러 가기!</button>
    </div>
  </div>
</template>

<script>
import "../../components/css/user.scss";
import UserApi from "../../api/UserApi";
import LoginHeader from '@/components/user/custom/LoginHeader.vue'

export default {
  methods: {
    toMainPage() {
        this.$router.push("/");
    }
  },
  mounted() {
    this.email= this.$route.params.email;
    UserApi.requestEmailConfirm(
          {email: this.$route.params.email},
          res => {
            //이메일 인증 완료
          },
          error => {
            this.$router.push({name:'Errors', query: {message: error.msg}})
          }
    );
  },
  data: () => {
    return {
      email: ''
    }
  },
  components: {
    LoginHeader,
  }
};
</script>

<style scoped>
  .myec {
    height: 100%;
    padding-top: 70px;
    padding-bottom: 50px;
  }
</style>