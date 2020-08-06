

<template>
  <div class="user" id="login">
    <div class="wrapC">
      <h1>
        변경링크를 <br />발송했습니다.
      </h1>
      <h1>
        메일함을 <br />확인해주세요.
      </h1>
      <h1>
        {{email}}
      </h1>
      <button
        @click="ReFindPw"
      >메일이 도착하지 않았나요?</button>
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
  data() {
    return {
      email: this.$route.params.email,
    }
  },
  methods: {
    toMainPage() {
        this.$router.push("/");
    },
    ReFindPw() {
        this.$router.push({name:'FindPwRe', params: {email: this.email}});
    },
  },
};
</script>