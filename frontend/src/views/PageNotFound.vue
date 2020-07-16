<template>
  <div class="wrap components-page">
    <div class="wrapB">
      <h1>Page Not Found</h1>
      <button 
        @click="goMainPage"
        class="btn-bottom"
        >Main Page</button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'PageNotFound',
  methods: {
    goMainPage() {
      // 로그인이 되어 있을 때, 아닐 때로 분기해야 함.
      // 로그인이 되어 있는 경우 feedmain
      // 아닌 경우, 로그인 페이지로
      this.$router.push({name: 'Login'})
    }
  }
}
</script>

<style>

</style>