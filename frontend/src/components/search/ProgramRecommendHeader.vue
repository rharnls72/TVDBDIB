<template>
  <b-nav justified class="myheader align-items-center">
    
    <b-nav-item class="d-flex justify-content-center">
      <img class="mylogo mb-0\" :src="HeaderLogo" alt="header-logo">
    </b-nav-item>
  
  </b-nav>
</template>

<script>
//////////// 테스트용 임포트 //////////////////
import Api from "@/api/FeedApi.js";
//////////////////////////////////////////////
import http from "@/api/http-common.js";
import header from "@/api/header.js"
import HeaderLogo from '@/assets/images/custom/header-logo.png'

export default {
  name: 'ProgramRecommendHeader',
  data() {
    return {
      HeaderLogo,
    }
  },
  methods: {
  },
}
</script>

<style scoped>
  .myheader {
    background-color: #D8BEFE;
    position: fixed;
    width: 100%;
    height: 50px;
    z-index: 0;
  }
  .mylogo {
    height: 35px;
  }
</style>