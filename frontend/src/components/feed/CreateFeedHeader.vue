<template>
  <div class="d-flex justify-content-between align-items-center mb-3 myheader">
    <b-icon @click="moveMain" icon="chevron-left" font-scale="1.5"></b-icon>
    <img class="mylogo mb-0\" :src="HeaderLogo" alt="header-logo">
    <b-icon @click="submitArticle" icon="check-square" font-scale="1.4"></b-icon>
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