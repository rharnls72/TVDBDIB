

<template>
  <div class="user" id="login">
    <div class="wrapC">
      <h1>
        어쩌면.. <br />스팸함에 들어가 있을지도 몰라요.
      </h1>
      <h1>
        혹시 확인해 보셨나요?
      </h1>
      <button
        class="btn-bottom"
        @click="tofindPw"
      >응 없어. 다시보내</button>
      <button
        class="btn-bottom"
        @click="toMainPage"
      >찾았어. 로그인하러 가자</button>

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
    tofindPw() {
        this.$router.push({name:'FindPwComplete', params: {email: this.email}});
    },
  },
};
</script>