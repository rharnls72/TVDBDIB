<template>
  <div class="d-flex justify-content-between align-items-center mb-3 myheader">
    <b-icon-chevron-left @click="moveMain" font-scale="1.5" class="ml-3"></b-icon-chevron-left>
    <img class="mylogo mb-0\" :src="HeaderLogo" alt="header-logo">
    <b-icon-check-square @click="submitArticle" font-scale="1.4" class="mr-3"></b-icon-check-square>
  </div>
</template>

<script>
import HeaderLogo from '../../assets/images/custom/header-logo.png'

export default {
  name: 'CreateFeedHeader',
  data() {
    return{
      HeaderLogo,
    }
  },
  methods: {
    moveMain() {
      this.$router.go(-1)
    },
    submitArticle() {
      this.$emit('submitArticle')
    }
  }
}
</script>

<style scoped>
  .myheader {
    background-color: #D8BEFE;
    position: fixed;
    top: 0;
    width: 100%;
    height: 50px;
    z-index: 1;
  }
  .mylogo {
    height: 35px;
  }
</style>