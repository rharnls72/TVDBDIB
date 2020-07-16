<template>
  <div class="wrap components-page">
    <div class="wrapB">
      <h1>{{message}}</h1>
      <button 
        @click="goBackpage"
        class="btn-bottom"
        >backpage</button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Error',
  data() {
    return {
      message: this.$route.query.message,
    }
  },
  methods: {
    goBackpage() {
      this.$router.go(-1)
    }
  }
}
</script>

<style>

</style>