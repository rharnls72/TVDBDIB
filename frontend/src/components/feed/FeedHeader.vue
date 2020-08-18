<template>
  <b-nav justified class="myheader align-items-center">
    <b-nav-item class="d-flex justify-content-start">
      <button>
        <b-icon-chevron-left @click="goToBack" class="text-dark"></b-icon-chevron-left>
      </button>
    </b-nav-item>
    <b-nav-item class="d-flex justify-content-center">
      <img class="mylogo mb-0\" :src="HeaderLogo" alt="header-logo">
    </b-nav-item>
    <b-nav-item class="d-flex justify-content-end">
    </b-nav-item>
  </b-nav>
</template>

<script>
import HeaderLogo from '../../assets/images/custom/header-logo.png'

export default {
  name: 'FeedHeader',
  data() {
    return{
      HeaderLogo,
    }
  },
  methods: {
    goToBack() {
      this.$router.go(-1)
    },
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