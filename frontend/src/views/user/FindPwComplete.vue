<template>
  <div class="user" id="login">
    <LoginHeader />
    <div class="wrapC myfpw d-flex flex-column justify-content-between">
      <div>
        <h1 class="text-center">
          변경 링크를 발송했습니다.
        </h1>
        <h1 class="text-center">
          이메일을 확인해주세요.
        </h1>
        <h2 class="text-center mymail mb-3">
          {{email}}
        </h2>
      </div>
      <div>
        <button
          class="mymailbutton mb-3"
          @click="ReFindPw"
        >이메일 재전송</button>
        <button
          class="mybutton"
          @click="toMainPage"
        >로그인 하러 가기!</button>
      </div>
    </div>
  </div>
</template>

<script>
import "../../components/css/user.scss";
import LoginHeader from '@/components/user/custom/LoginHeader.vue'

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
  .mymail {
    color: royalblue;
  }
  .mymailbutton {
    width: 100%;
    height: 40px;
    background-color: #f9e9f2;
    box-shadow: none;
    border-radius: 0.25rem;
  }
  .mybutton {
    width: 100%;
    height: 40px;
    background-color: #d8c8f8;
    box-shadow: none;
    border-radius: 0.25rem;
  }
</style>