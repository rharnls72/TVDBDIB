<template>
  <div class="wrap text-center">
    <LoginHeader />
    <div class="wrapB myerror">
      <h1>{{message}}</h1>
      <button 
        @click="goHome"
        class="btn-bottom"
        >홈으로 가기</button>
    </div>
  </div>
</template>

<script>
import LoginHeader from '@/components/user/custom/LoginHeader.vue'

export default {
  name: 'Errors',
  data() {
    return {
      message: this.$route.query.message,
    }
  },
  methods: {
    goHome() {
      this.$router.replace({name: 'IndexCuration'})
    }
  },
  components: {
    LoginHeader,
  }
}
</script>

<style scoped>
  .myerror {
    height: 100%;
    padding-top: 70px;
    padding-bottom: 50px;
  }
</style>