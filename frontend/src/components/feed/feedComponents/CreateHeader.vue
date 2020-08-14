<template>
  <div>
    <div class="col d-flex justify-content-between align-items-center mb-5 myheader">
      <b-icon @click="moveback" icon="chevron-left" font-scale="1.5"></b-icon>
      <img class="mylogo mb-0\" :src="HeaderLogo" alt="header-logo">\
      <b-icon @click="submit" icon="check-square" font-scale="1.4"></b-icon>
    </div>
  </div>
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
    width: 100%;
    height: 50px;
    z-index: 1;
  }
  .mylogo {
    height: 35px;
  }
</style>