<template>
  <b-nav justified class="myheader align-items-center">
    <b-nav-item class="d-flex justify-content-start">
      <button @click="moveback">
        <b-icon-chevron-left class="text-dark"></b-icon-chevron-left>
      </button>
    </b-nav-item>
    <b-nav-item>
      <img class="mylogo mb-0" :src="HeaderLogo" alt="header-logo">
    </b-nav-item>
    <b-nav-item class="d-flex justify-content-end">
      <button @click="submit">
        <b-icon-check-square class="text-dark"></b-icon-check-square>
      </button>
    </b-nav-item>
  </b-nav>
</template>

<script>
import HeaderLogo from '../../../assets/images/custom/header-logo.png'

export default {
  name: "CreateHeader",
  data() {
    return{
      HeaderLogo,
    }
  },
  methods: {
    moveMain() {
      this.$router.go(-1)
    },
    submit() {
      this.$emit('submit')
    },
    moveback() {
      this.$router.go(-1)
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