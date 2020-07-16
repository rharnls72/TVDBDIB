

<template>
  <div class="user" id="login">
    <div class="wrapC">
      <h1>
        비밀번호가 성공적으로 변경되었습니다!
      </h1>

      <button
        class="btn-bottom"
        @click="toMainPage"
      >로그인 하러가기!</button>

    </div>
  </div>
</template>

<script>
import "../../components/css/user.scss";

export default {
  methods: {
    toMainPage() {
        this.$router.push("/");
    }
  },
};
</script>