<template>
  <b-nav justified class="myheader d-flex justify-content-center align-items-center">
    <b-nav-item class="d-flex justify-content-start">
      <button @click="backpage()">
        <b-icon-chevron-left class="text-dark"></b-icon-chevron-left>
      </button>
    </b-nav-item>
    <b-nav-item>
      <img class="mylogo mb-0" :src="HeaderLogo" alt="header-logo">
    </b-nav-item>
    <b-nav-item>
    </b-nav-item>
  </b-nav>
</template>

<script>
import HeaderLogo from '../../../assets/images/custom/header-logo.png'

export default {
  name: 'JoinHeader',
  data() {
    return {
      HeaderLogo,
    }
  },
  methods: {
    backpage(){
      this.$router.go(-1);
    },
  },
}
</script>

<style scoped>
  .myheader {
    background-color: #D8BEFE;
    position: fixed;
    top: 0;
    width: 100%;
    z-index: 1;
  }
  .mylogo {
    height: 35px;
  }
</style>