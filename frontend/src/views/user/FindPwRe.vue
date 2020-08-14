<template>
  <div class="user" id="login">
    <LoginHeader />
    <div class="wrapC myfpw d-flex flex-column justify-content-between">
      <div>
        <h1 class="text-center">
          혹시 스팸함을 확인해 보셨나요?
        </h1>
      </div>
      <div>
        <button
          class="mymailbutton mb-3"
          @click="tofindPw"
        >응 없어. 다시 보내</button>
        <button
          class="mybutton"
          @click="toMainPage"
        >찾았어. 로그인 하러 가자</button>
      </div>

    </div>
  </div>
</template>

<script>
import "../../components/css/user.scss";
import UserApi from "../../api/UserApi";
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
    tofindPw() {
        UserApi.requestFindPw(
          {email: this.email},
          res => {
            // 가입 했으면 가입한 정보로 로그인 하라고 하기
            this.$router.push({name:'FindPwComplete', params: {email: this.email}});
          },
          error => {
            this.$router.push({name:'Errors', query: {message: error.msg}})
          }
        );
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