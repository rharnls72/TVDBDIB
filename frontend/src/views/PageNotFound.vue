<template>
  <div class="wrap text-center">
    <LoginHeader />
    <div class="wrapB mypnf">
      <h1>Page Not Found</h1>
      <h2>존재하지 않는 주소입니다.</h2>
      <button 
        @click="goMainPage"
        class="btn-bottom"
        >Main Page</button>
    </div>
  </div>
</template>

<script>
import LoginHeader from '@/components/user/custom/LoginHeader.vue'

export default {
  name: 'PageNotFound',
  methods: {
    goMainPage() {
      // 로그인이 되어 있을 때, 아닐 때로 분기해야 함.
      // 로그인이 되어 있는 경우 feedmain
      // 아닌 경우, 로그인 페이지로
      this.$router.push({name: 'Login'})
    }
  },
  components: {
    LoginHeader,
  },
}
</script>

<style scoped>
  .mypnf {
    height: 100%;
    padding-top: 70px;
    padding-bottom: 50px;
  }
</style>