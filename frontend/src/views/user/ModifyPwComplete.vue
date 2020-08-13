<template>
  <div class="user" id="login">
    <LoginHeader />
    <div class="wrapC mympw">
      <h1 class="text-center">
        비밀번호가 성공적으로 변경되었습니다!
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
import LoginHeader from '@/components/user/custom/LoginHeader.vue'

export default {
  methods: {
    toMainPage() {
        this.$router.push("/");
    }
  },
  components: {
    LoginHeader,
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